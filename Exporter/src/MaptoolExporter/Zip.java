package MaptoolExporter;

/*
 * Copyright (c) 2010 Lukas David Jacobs
 *
 * Hiermit wird unentgeltlich, jeder Person, die eine Kopie der Software und der zugehÃ¶rigen
 * Dokumentationen (die "Software") erhÃ¤lt, die Erlaubnis erteilt, uneingeschrÃ¤nkt zu benutzen,
 * inklusive und ohne Ausnahme, dem Recht, sie zu verwenden, kopieren, Ã¤ndern, fusionieren,
 * verlegen, verbreiten, unterlizenzieren und/oder zu verkaufen, und Personen, die diese Software
 * erhalten, diese Rechte zu geben, unter den folgenden Bedingungen:
 *
 * Der obige Urheberrechtsvermerk und dieser Erlaubnisvermerk sind in allen Kopien oder Teilkopien
 * der Software beizulegen.
 *
 * DIE SOFTWARE WIRD OHNE JEDE AUSDRÃœCKLICHE ODER IMPLIZIERTE GARANTIE
 * BEREITGESTELLT, EINSCHLIESSLICH DER GARANTIE ZUR BENUTZUNG FÃœR DEN
 * VORGESEHENEN ODER EINEM BESTIMMTEN ZWECK SOWIE JEGLICHER
 * RECHTSVERLETZUNG, JEDOCH NICHT DARAUF BESCHRÃ„NKT. IN KEINEM FALL SIND DIE
 * AUTOREN ODER COPYRIGHTINHABER FÃœR JEGLICHEN SCHADEN ODER SONSTIGE
 * ANSPRÃœCHE HAFTBAR ZU MACHEN, OB INFOLGE DER ERFÃœLLUNG EINES VERTRAGES,
 * EINES DELIKTES ODER ANDERS IM ZUSAMMENHANG MIT DER SOFTWARE ODER
 * SONSTIGER VERWENDUNG DER SOFTWARE ENTSTANDEN.
 */


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author Lukas
 */
public class Zip {

    public static Vector extractArchive(File archive, File destDir) throws Exception {
        if (!destDir.exists()) {
            destDir.mkdir();
        }

        Vector filesList = new Vector();

        ZipFile zipFile = new ZipFile(archive);
        Enumeration entries = zipFile.entries();

        byte[] buffer = new byte[131072];
        int len;
        while (entries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) entries.nextElement();

            String entryFileName = entry.getName();

            File dir = dir = buildDirectoryHierarchyFor(entryFileName, destDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            if (!entry.isDirectory()) {
                File file = new File(destDir, entryFileName);
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                filesList.add(file);

                BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));

                while ((len = bis.read(buffer)) > 0) {
                    bos.write(buffer, 0, len);
                }

                bos.flush();
                bos.close();
                bis.close();
            }
        }

        return filesList;
    }

    public static void packArchive(File archive, Vector files) throws IOException {
        byte[] data = new byte[131072];
        ZipOutputStream zipStream = new ZipOutputStream(new FileOutputStream(archive));
        zipStream.setMethod(ZipOutputStream.DEFLATED);

        for (int curFile = 0; curFile < files.size(); curFile++) {
            File file = (File) files.get(curFile);
            ZipEntry entry;
            if (file.getName().equals("thumbnail") || file.getName().equals("properties.xml") || file.getName().equals("content.xml")) {
                entry = new ZipEntry(file.getName());
            } else {
                entry = new ZipEntry("assets/" + file.getName());
            }
            FileInputStream inStream = new FileInputStream(file);

            zipStream.putNextEntry(entry);
            zipStream.write(data, 0, inStream.read(data, 0, 131072));
            zipStream.closeEntry();
            inStream.close();
        }

        zipStream.close();
    }

    private static File buildDirectoryHierarchyFor(String entryName, File destDir) {
        int lastIndex = entryName.lastIndexOf('/');
        String internalPathToEntry = entryName.substring(0, lastIndex + 1);
        return new File(destDir, internalPathToEntry);
    }

    public static void modifyZipEntry(File zipFile, File[] newFiles) throws Exception{
        //Wir brauchen was temporÃ¤res
        File tempFile = File.createTempFile(zipFile.getName(), null);
        //Und lÃ¶schen es gleich wieder, damit wir unsere existierende zip so nennen kÃ¶nnen
        tempFile.delete();

        FileHelper copy = new FileHelper();
        copy.copy(zipFile, tempFile);
        zipFile.delete();

	byte[] buf = new byte[131072];

	ZipInputStream zipInStream = new ZipInputStream(new FileInputStream(tempFile));
	ZipOutputStream zipOutStream = new ZipOutputStream(new FileOutputStream(zipFile));

	ZipEntry entry = zipInStream.getNextEntry();

        while (entry != null) {
            String name = entry.getName();
            boolean notInFiles = true;
            for (File f : newFiles) {
                if (f.getName().equals(name)) {
                    notInFiles = false;
                    break;
		}
            }

            if (notInFiles) {
                //Entry zum outputstream hinzufÃ¼gen
                zipOutStream.putNextEntry(new ZipEntry(name));

                //Und alles byte fÃ¼r byte rÃ¼ber schieben
		int len;
		while ((len = zipInStream.read(buf)) > 0) {
                    zipOutStream.write(buf, 0, len);
		}
            }

            entry = zipInStream.getNextEntry();
        }

        //Stream dichtmachen
        zipInStream.close();

	//Und verpacken
        for (int a = 0; a < newFiles.length; a++) {
            InputStream inStream = new FileInputStream(newFiles[a]);

            //Entry zum outputstream hinzufÃ¼gen
            zipOutStream.putNextEntry(new ZipEntry(newFiles[a].getName()));

            //Und alles byte fÃ¼r byte in die Zipdatei reinschieben
            int len;
            while ((len = inStream.read(buf)) > 0) {
                zipOutStream.write(buf, 0, len);
            }

            //Den Eintrag schlieÃŸen
            zipOutStream.closeEntry();
            inStream.close();
        }

        //Und zum schluss die zip dichtmachen
        zipOutStream.close();
        tempFile.delete();
    }

    public static File getFileInZip(File zipFile, String fileName) throws Exception {
        File tempDir = new File("temp");
        tempDir.mkdir();

        extractArchive(zipFile, tempDir);

        return new File("temp/" + fileName);
    }
}

