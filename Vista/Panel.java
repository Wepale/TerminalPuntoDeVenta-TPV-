package Vista;
import Vista.PanelesGenerales.*;
import Vista.FramePrincipal.*;
import Modelo.Contenedores.*;
import java.awt.*;
import javax.swing.*;
/**
 * Este será el panel principal de la aplicación. Contendrá a todos los demas paneles.
 * Tendrá un gestor de disposición BorderLayout y será al JPanel que se añadirá al contenedor del JFrame.
 * 
 * En la zona norte tendrá un panel de tipo PanelNorte, en la zona oeste un panel de tipo PanelOeste, en la zona central un JScrollPane con una
 * simple etiqueta y un JScrollPanel en la zona central donde se visualizará toda la información.
 * 
 * @author Yeray Rodríguez
 * @version 1.0
 */
public class Panel extends JPanel
{
    /**
     * El JScrollPane que ocupará la zona sur
     */
    private PanelScrollSur scrollSur;
    /**
     * El JPanel que irá dentro del PanelScrollSur
     */
    private JPanel sur;
    /**
     * El JPanel que irá situado en la zona central
     */
    private JPanel centro;
    /**
     * El JPanel que ocupará la zona oeste
     */
    private PanelOeste oeste;
    /**
     * El JPanel que ocupará la zona norte
     */
    private PanelNorte norte;
    
    /**
     * Crea el JPanel con todos los paneles ya creados y complementes funcionales es sus respectivas posiciones
     * 
     * @param frame el frame padre
     * @param inventario el inventario donde se almacenan/almacenarán todos los productos
     * @param lista la lista de cliente que contiene/contendrá todos los clientes dados de alta en el sistema
     * @param archivador el archivador de tickets que contiene/contendrá todos los tickets
     * @param archivadorFacturas el archivador que contiene/contendrá todas las facturas de nuestro sistema
     */
    public Panel(FramePrincipal framePrincipal, JFrame frame, ContenedorProductos contenedor, ContenedorClientes lista, ContenedorTickets archivador, ContenedorFacturas archivadorFacturas,
                 String archivoProductos, String archivoClientes, String archivoTickets, String archivoFacturas)
    {
        crearPanelPrincipal(framePrincipal, frame, contenedor, lista, archivador, archivadorFacturas, archivoProductos, archivoClientes, archivoTickets, archivoFacturas);
    }
    
    /**
     * Creará el JScrollPane que irá situado en la zona sur. Simplemente tendrá una etiqueta indicando la versión del TPV
     * 
     * @return JScrollPane el JScrollPane que ocpuará la zona sur
     */
    public JScrollPane crearPanelSur()
    {
        sur = new JPanel();
        sur.setBorder(BorderFactory.createEtchedBorder());
        JLabel etiqueta = new JLabel("TPV version 1");
        sur.add(etiqueta);
        scrollSur = new PanelScrollSur(sur, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        return scrollSur;
    }
    /**
     * Creará el JScrollPane que ocupará la zona central. Será el JPanel usado para mostrar toda la información por pantalla.
     * 
     * @return JScrollPane el JScrollPane que ocupará la zona central
     */
    public JScrollPane crearPanelCentral()
    {
        centro = new JPanel();
        centro.setBorder(BorderFactory.createEtchedBorder());
        JLabel etiqueta = new JLabel("Eliga una opción para continuar");
        centro.add(etiqueta);
        JScrollPane scroll = new JScrollPane(centro, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        return scroll;
    }
    
    /**
     * Crea la barra de menu y la añade el JFrame principal
     * 
     * @param framePrincipal la calse que crea toda la aplicacion
     * @param frame el frame principal
     * @param productos el inventario donde almacenamos todos nuestros productos
     * @param listaClientes la lista de cliente donde se encuentran todos los clientes dados de alta en el sistema
     * @param archivador al archivador donde se alamacenan todos los ticket creados
     * @param archivadorFacturas el archivador donde se alamacenan todas las facturas creadas
     * @param archivoProductos la ruta donde se serializaran los productos
     * @param archivoClientes la ruta donde se serializaran los clientes
     * @param archivoTickets la ruta donde se serializaran los tickets
     * @param archivoFacturas la ruta donde se serializaran las facturas
     */
    public void crearBarraMenu(FramePrincipal framePrincipal, JFrame frame, ContenedorProductos contenedor, ContenedorClientes lista, 
                                ContenedorTickets archivador, ContenedorFacturas archivadorFacturas, String archivoProductos, String archivoClientes,
                                String archivoTickets, String archivoFacturas)
    {
        frame.setJMenuBar(new BarraDeMenu(framePrincipal, frame, contenedor, lista, archivador, archivadorFacturas, archivoProductos, archivoClientes, archivoTickets, archivoFacturas));
    }
    
    /**
     * Método principal de esta clase. Crea todos los JPanel e introduce cada uno en su lugar correspondiente.
     * 
     * @param frame el frame padre
     * @param inventario el inventario donde se almacenan/almacenarán todos los productos
     * @param lista la lista de cliente que contiene/contendrá todos los clientes dados de alta en el sistema
     * @param archivador el archivador de tickets que contiene/contendrá todos los tickets
     * @param archivadorFacturas el archivador que contiene/contendrá todas las facturas de nuestro sistema
     */
    public void crearPanelPrincipal(FramePrincipal framePrincipal, JFrame frame, ContenedorProductos contenedor, ContenedorClientes lista, ContenedorTickets archivador, ContenedorFacturas archivadorFacturas,
                                     String archivoProductos, String archivoClientes,String archivoTickets, String archivoFacturas)
    {
        setLayout(new BorderLayout(15,10));
        
        add(crearPanelSur(), BorderLayout.SOUTH);
        
        add(crearPanelCentral(), BorderLayout.CENTER);
        
        oeste = new PanelOeste(frame, contenedor, lista, archivador, archivadorFacturas, centro);
        add(oeste, BorderLayout.WEST);
        
        norte = new PanelNorte(frame, contenedor, lista, archivador, archivadorFacturas, centro);
        add(norte, BorderLayout.NORTH);
        
        add(new JPanel(), BorderLayout.EAST);
        
        crearBarraMenu(framePrincipal, frame, contenedor, lista, archivador, archivadorFacturas, archivoProductos, archivoClientes, archivoTickets, archivoFacturas);
    }
}
