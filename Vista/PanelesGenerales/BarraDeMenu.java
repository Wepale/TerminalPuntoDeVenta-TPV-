package Vista.PanelesGenerales;

import Modelo.Contenedores.*;
import Modelo.Serializacion.Serializar;

import Vista.FramePrincipal;

import java.awt.event.*;
import javax.swing.*;

/**
 * Esta clase será la barra de menu de nuestra aplicación. Contendrá dos menus: "Guardar" y "Cargar".
 * Dentro de cada uno habrá varios JMenuItem, cuya finalidad será guardar/cargar los productos, clientes, tickets y facturas.
 * 
 * 
 * @author Yeray Rodríguez Martín 
 * @version 1.0
 */
public class BarraDeMenu extends JMenuBar
{
    private Serializar serializar;
    //JMenuItems que cuando sean presionados guardaran los componentes del sistema en un archivo
    private JMenuItem guardarProductos;
    private JMenuItem guardarClientes;
    private JMenuItem guardarTickets;
    private JMenuItem guardarFacturas;
    //JMenuItems que cuando sean presionados guardaran los componentes del sistema en un archivo
    private JMenuItem cargarProductos;
    private JMenuItem cargarClientes;
    private JMenuItem cargarTickets;
    private JMenuItem cargarFacturas;
    
    /**
     * Creará la barra de menu completamente funcional
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
    public BarraDeMenu(FramePrincipal framePrincipal, JFrame frame, ContenedorProductos productos, ContenedorClientes listaClientes, ContenedorTickets archivador, ContenedorFacturas archivadorFacturas,
                                   String archivoProductos, String archivoClientes, String archivoTickets, String archivoFacturas)
    {
        super();
        serializar = new Serializar();
        //*******************************************************
        guardarProductos = new JMenuItem("Guardar Productos");
        guardarClientes = new JMenuItem("Guardar Clientes");
        guardarTickets = new JMenuItem("Guardar Tickets");
        guardarFacturas = new JMenuItem("Guardar Facturas");
        //*******************************************************
        cargarProductos = new JMenuItem("Cargar Productos");
        cargarClientes = new JMenuItem("Cargar Clientes");
        cargarTickets = new JMenuItem("Cargar Tickets");
        cargarFacturas = new JMenuItem("Cargar Facturas");
        //*******************************************************
        rellenarBarraMenu();
        crearAcciones(framePrincipal, frame, productos, listaClientes, archivador, archivadorFacturas, archivoProductos, archivoClientes, archivoTickets, archivoFacturas);
    }
    
    /**
     * Rellena la barra de menu.
     * 
     * Creará dos JMenu: "Cargar" y "Guardar" y los JMenuItem para guardar/cargar los productos, clientes, tickets y facturas
     * 
     */
    public void rellenarBarraMenu()
    {
        //Creamos los elementos del menu
        JMenu menuGuardar = new JMenu("Guardar");
        JMenu menuCargar = new JMenu("Cargar");
        add(menuGuardar);
        add(menuCargar);
        //Añadimos los JMenuItems a su JMenu correspondiente
        menuGuardar.add(guardarProductos);
        menuGuardar.add(guardarClientes);
        menuGuardar.add(guardarTickets);
        menuGuardar.add(guardarFacturas);
    
        menuCargar.add(cargarProductos);
        menuCargar.add(cargarClientes);
        menuCargar.add(cargarTickets);
        menuCargar.add(cargarFacturas);
    }
    
    /**
     * Crearemos las acciones relacionadas a cada JMenuItem.
     * 
     * Cada JMenuItem tendrá su propio ActionListenner para que cuando sean pulsados realizen el guardado/cargado de los
     * productos, clientes, ticketes y facturas
     * 
     * @param productos el inventario donde almacenamos todos nuestros productos
     * @param listaClientes la lista de cliente donde se encuentran todos los clientes dados de alta en el sistema
     * @param archivador al archivador donde se alamacenan todos los ticket creados
     * @param archivadorFacturas el archivador donde se alamacenan todas las facturas creadas
     * @param archivoProductos la ruta donde se serializaran los productos
     * @param archivoClientes la ruta donde se serializaran los clientes
     * @param archivoTickets la ruta donde se serializaran los tickets
     * @param archivoFacturas la ruta donde se serializaran las facturas
     */
    public void crearAcciones(FramePrincipal framePrincipal, JFrame frame, ContenedorProductos productos, ContenedorClientes listaClientes, ContenedorTickets archivador, ContenedorFacturas archivadorFacturas, 
                              String archivoProductos, String archivoClientes, String archivoTickets, String archivoFacturas)
    {
        //********************************Acciones para guardar**************************************
        guardarProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                serializar.guardarEnFichero(archivoProductos, new ContenedorProductos(productos));
                JOptionPane.showMessageDialog(frame, "Los productos se han guardado correctamente\n",
                                                        "Operación Aceptada", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        guardarClientes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                serializar.guardarEnFichero(archivoClientes, new ContenedorClientes(listaClientes));
                JOptionPane.showMessageDialog(frame, "Los clientes se han guardado correctamente\n",
                                                         "Operación Aceptada", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        guardarTickets.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                serializar.guardarEnFichero(archivoTickets, new ContenedorTickets(archivador));
                JOptionPane.showMessageDialog(frame, "Los tickets se han guardado correctamente\n",
                                                         "Operación Aceptada", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        guardarFacturas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                serializar.guardarEnFichero(archivoFacturas, new ContenedorFacturas(archivadorFacturas));
                JOptionPane.showMessageDialog(frame, "Las facturas se han guardado correctamente\n",
                                                         "Operación Aceptada", JOptionPane.INFORMATION_MESSAGE);
               
            }
        });
        
        //********************************Acciones para Cargar***************************************
        
        cargarProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                ContenedorProductos aux = (ContenedorProductos)serializar.leerDeFichero(archivoProductos);
                framePrincipal.actualizarInventario(aux);
            }
        });
        
        cargarClientes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                ContenedorClientes aux = (ContenedorClientes)serializar.leerDeFichero(archivoClientes);
                framePrincipal.actualizarListaClientes(aux);
            }
        });
        
        cargarTickets.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                ContenedorTickets aux = (ContenedorTickets)serializar.leerDeFichero(archivoTickets);
                framePrincipal.actualizarArchivador(aux);
            }
        });
        
        cargarFacturas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                ContenedorFacturas aux = (ContenedorFacturas)serializar.leerDeFichero(archivoFacturas);
                framePrincipal.actualizarArchivadorFacturas(aux);
            }
        });
        
    }
}