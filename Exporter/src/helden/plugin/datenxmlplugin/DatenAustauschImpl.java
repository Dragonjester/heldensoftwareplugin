/**
 * (C) Andreas Schönknecht 2006
 */
package helden.plugin.datenxmlplugin;
import org.w3c.dom.Document;



/**
 * Klasse über die die Kommunikation 
 * zwischen Heldenprogramm und dem Plugin erfolgt
 * 
 * @author sven
 *
 */
public interface DatenAustauschImpl {

    /**
     * Führt eine Aktion aus und liefert das Ergebnis zurück
     * @param d (XML-kodiert)
     * @return Ergebnis der Aktion 
     */
    Object exec(Document d);
}
