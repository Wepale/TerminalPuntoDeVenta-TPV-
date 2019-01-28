package Vista.PanelesGenerales;

import java.awt.*;
import javax.swing.*;

/**
 * Write a description of class PanelSur here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PanelScrollSur extends JScrollPane
{
    
    public PanelScrollSur(JPanel panel, int barraVertical, int barraHorizontal)
    {
        super(panel, barraVertical, barraHorizontal);
    } 
    
    /**
     * Sobreescribimos el método de la superclase para configurar su tamaño preferido
     * 
     * @return el tamaño preferido de este panel
     */
    @Override
    public Dimension getPreferredSize()
    {
       return new Dimension(100,100);   
    }

    /**
     * Sobreescribimos el método de la superclase para configurar su tamaño máximo
     * 
     * @return el tamaño máximo de este panel
     */
    @Override
    public Dimension getMaximumSize()
    {
       return new Dimension(100,100);   
    }
}
