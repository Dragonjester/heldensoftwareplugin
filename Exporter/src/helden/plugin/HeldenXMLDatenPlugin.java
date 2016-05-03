/**
 * (C) Andreas Sch�nknecht 2006
 */
package helden.plugin;

import javax.swing.JFrame;
import java.util.ArrayList;
import helden.plugin.datenxmlplugin.DatenAustauschImpl;

/**
 * Lifert ein Interface, das von allen Plugins 
 * implementiert werden muss. 
 * @author Schiefer-Gehrke
 * @since 4.7.2
 */
public interface HeldenXMLDatenPlugin extends HeldenPlugin {

    /**
     * Konstante f�r Simple Execution Inteface
     */
    String DATEN = "DatenXMLPlugin";
    
    /**
     * Liefert die Namen der Menuunterpunkte zurueck
     * @return Liste der Namen der Spielb�gen
     */
    ArrayList<String> getUntermenus();
    
    /**
     * Wird aufgerufen wenn der Menueintrag aufgerufen wird.
     * @param frame der HeldenFrame mit den Helden
     * @param dai DatenAustauschImpl
     * @param menuIdx Gibt an, welcher Menupunkt ausgew�hlt wurde.
     * @since 4.7.4
     */
    void doWork(JFrame frame, Integer menuIdx, DatenAustauschImpl dai);

}
