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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.ByteChannel;
import java.nio.channels.FileChannel;

public class FileHelper {

    long chunckSizeInBytes;
    boolean verbose;

    public FileHelper() {
        this.chunckSizeInBytes = 1024 * 1024; //Standard: Buffer 1MB
        this.verbose = false; //Statistics about Copy Process
    }

    public FileHelper(boolean verbose) {
        this.chunckSizeInBytes = 1024 * 1024; //Standard: Buffer 1MB
        this.verbose = verbose; //Statistics about Copy Process
    }

    public FileHelper(long chunckSizeInBytes) {
        this.chunckSizeInBytes = chunckSizeInBytes; //Custom Buffer (Bytes)
        this.verbose = false; //Statistics about Copy Process
    }

    public FileHelper(long chunckSizeInBytes, boolean verbose) {
        this.chunckSizeInBytes = chunckSizeInBytes; //Custom Buffer (Bytes)
        this.verbose = verbose; //Statistics about Copy Process
    }

    public void copy(File source, File destination) {
        try {
            FileInputStream fileInputStream = new FileInputStream(source);
            FileOutputStream fileOutputStream = new FileOutputStream(destination);
            FileChannel inputChannel = fileInputStream.getChannel();
            FileChannel outputChannel = fileOutputStream.getChannel();
            transfer(inputChannel, outputChannel, source.length(), false);
            fileInputStream.close();
            fileOutputStream.close();
            destination.setLastModified(source.lastModified());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void transfer(FileChannel fileChannel, ByteChannel byteChannel, long lengthInBytes, boolean verbose) throws IOException {
        long overallBytesTransfered = 0L;
        long time = -System.currentTimeMillis();
        while (overallBytesTransfered < lengthInBytes) {
            long bytesTransfered = 0L;
            bytesTransfered = fileChannel.transferTo(overallBytesTransfered, Math.min(chunckSizeInBytes, lengthInBytes - overallBytesTransfered), byteChannel);
            overallBytesTransfered += bytesTransfered;
            if (verbose) {
                System.out.println("overall bytes transfered: " + overallBytesTransfered + " progress " + (Math.round(overallBytesTransfered / ((double) lengthInBytes) * 100.0)) + "%");
            }
        }
        time += System.currentTimeMillis();
        if (verbose) {
            System.out.println("Transfered: " + overallBytesTransfered + " bytes in: " + (time / 1000) + " s -> " + (overallBytesTransfered / 1024.0) / (time / 1000.0) + " kbytes/s");
        }
    }

    public static String readFileAsString(String filePath) throws java.io.IOException{
        byte[] buffer = new byte[(int) new File(filePath).length()];
        BufferedInputStream f = new BufferedInputStream(new FileInputStream(filePath));
        f.read(buffer);
        return new String(buffer);
    }

}
