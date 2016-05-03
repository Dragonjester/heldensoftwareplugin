package helden.plugin;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * Lifert ein Interface, das von allen Plugins 
 * implementiert werden muss. 
 * @author Schiefer-Gehrke
 * @since 4.7.0
 */
public interface HeldenPlugin {
    /**
     * Konstante für Simple Execution Inteface
     */
    String SIMPLE = "simple execute";
    
    /**
     * Gibt den Namen unter dem das Plugin 
     * im Menu eingetragen wird
     * Bitte kurz halten.
     * @return Name
     * @since 4.7.0
     */
    String getMenuName();

    /**
     * Gibt ein Kurzerklärung der als ToolTip angezeigt wird,
     * Wenn der Mauszeiger über dem Button steht. 
     * @return kruz Erklärung oder null
     * @since 4.7.0
     */
    String getToolTipText();

    /**
     * Das Icon das einheblendet werden soll
     * @return ImageIcon oder null
     * @since 4.7.0
     */
    ImageIcon getIcon();
    
    /**
     * Wird aufgerufen wenn der Menueintrag aufgerufen wird.
     * @param frame der HeldenFrame mit den Helden
     * @since 4.7.0
     */
    void doWork(JFrame frame);
    
    /**
     * Gibt den Type des Interfaces zurück
     * @return Konstante
     */
    String getType();

}
