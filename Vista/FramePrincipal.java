package Vista;
import Vista.PanelesGenerales.BarraDeMenu;

import Modelo.Clientes.Cliente;
import Modelo.Productos.*;
import Modelo.Contenedores.*;
import Modelo.Ventas.Factura;

import Controlador.Ventas.PedirDatosVendedor;

import java.awt.*;
import javax.swing.*;


/**
 * Creará el frame principal de nuestra aplicación.
 * 
 */
public class FramePrincipal
{
    /**
     * El JFrame de nuestra aplicación
     */
    private JFrame frame;
    /**
     * El JPanel de tipo Panel que contiene todos los JPanel que componen la aplicacion
     */
    private Panel panel;
    /**
     * Nuestro inventario. Todos los productos se alamacenarán aquí
     */
    private ContenedorProductos inventario;
    /**
     * Nuestra lista de clientes. Todos los clientes dados de alta en el sistema se almacenarán aquí
     */
    private ContenedorClientes listaClientes;
    /**
     * Nuestro archivador de tickets. Todos los tickets que cree el sistema se alamacenarán aquí
     */
    private ContenedorTickets archivador;
    /**
     * Nuestro archivador de facturas. Todas las facturas que cree el sistema se alamcenaran aquí
     */
    private ContenedorFacturas archivadorFacturas;
    
    /**
     * La ruta donde se guardaran/cargaran los productos
     */
    private String archivoInventario = "archivoInventario.dat";
    /**
     * La ruta donde se guardaran/cargaran los clientes
     */
    private String archivoClientes = "archivoClientes.dat";
    /**
     * La ruta donde se guardaran/cargaran los tickets
     */
    private String archivoTickets = "archivoTickets.dat";
    /**
     * La ruta donde se guardaran/cargaran las facturas
     */
    private String archivoFacturas = "archivoFacturas.dat";
    
    private Cliente cliente = new Cliente(Cliente.getAutoCodigo(), Cliente.getAutoNombre(), Cliente.getAutoNif(), Cliente.getAutoDomicilio());
    
    /**
     * Constructor principal de nuestra aplicación. Creará nuestro TPV completamente funcional
     */
    public FramePrincipal()
    {
        frame = new JFrame("TPV");
        inventario = new ContenedorProductos();
        listaClientes = new ContenedorClientes();
        archivador = new ContenedorTickets();
        archivadorFacturas = new ContenedorFacturas();
        listaClientes.insertarCliente(cliente);
        frame.setJMenuBar(new BarraDeMenu(this, frame, inventario, listaClientes,archivador, archivadorFacturas, archivoInventario, archivoClientes, archivoTickets, archivoFacturas));
        panel = new Panel(this, frame, inventario, listaClientes, archivador, archivadorFacturas, archivoInventario, archivoClientes, archivoTickets, archivoFacturas );
        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(frame.MAXIMIZED_BOTH);
        frame.pack();
        frame.setVisible(true);
        new PedirDatosVendedor(frame);
    }
    
    /**
     * Permite el acceso a nuestro inventario
     */
    public ContenedorProductos getInventario()
    {
        return inventario;
    }
    
    /**
     * Permite el acceso a nuestra lista de clientes
     */
    public ContenedorClientes getListaClientes()
    {
        return listaClientes;
    }
    
    /**
     * Permite el acceso a nuestro archivador de tickets
     */
    public ContenedorTickets getArchivador()
    {
        return archivador;
    }
    
    /**
     * Permite el acceso a nuestro arhivador de facturas
     */
    public ContenedorFacturas getArchivadorFacturas()
    {
        return archivadorFacturas;
    }
    
    /**
     * Este método actualiza el inventario. Se implementa para usarlo justo despues de cargar los productos serializados y así actualizar el alamacen
     * 
     * @param nuevoInventerio el nuevo inventario a cargar
     */
    public void actualizarInventario(ContenedorProductos nuevoInventario)
    {
        if(nuevoInventario != null){
            inventario = new ContenedorProductos(nuevoInventario);
            panel = new Panel(this, frame, inventario, listaClientes, archivador, archivadorFacturas,
                              archivoInventario, archivoClientes, archivoTickets, archivoFacturas);
            actualizarFrame(frame);
            JOptionPane.showMessageDialog(frame, "Los productos de han cargado correctamente.\n\n",
                                                 "Operación Aceptada", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(frame, "No existen ningun archivo con el inventario de sesiones anteriores.\n\n",
                                                 "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Este método actualiza el la lista de  clientes. Se implementa para usarlo justo despues de cargar los clientes serializados y así
     * actualizar la lista de clientes
     * 
     * @param nuevaListaClientes los nuevos clientes a cargar
     */
    public void actualizarListaClientes(ContenedorClientes nuevaListaClientes)
    {
        if(nuevaListaClientes != null){
            listaClientes = new ContenedorClientes(nuevaListaClientes);
            panel = new Panel(this, frame, inventario, listaClientes, archivador, archivadorFacturas,
                              archivoInventario, archivoClientes, archivoTickets, archivoFacturas);
            actualizarFrame(frame);
            JOptionPane.showMessageDialog(frame, "Los clientes de han cargado correctamente.\n\n",
                                                 "Operación Aceptada", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(frame, "No existen ningun archivo con clientes de sesiones anteriores.\n\n",
                                                 "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Este método actualiza el archivador de tickets. Se implementa para usarlo justo despues de cargar los productos serializados y así
     * actualizar el archivador de tickets
     * 
     * @param nuevoInventerio el nuevo archivador de tickets a cargar
     */
    public void actualizarArchivador(ContenedorTickets nuevoArchivador)
    {
        if(nuevoArchivador != null){
            archivador = new ContenedorTickets(nuevoArchivador);
            panel = new Panel(this, frame, inventario, listaClientes, archivador, archivadorFacturas,
                              archivoInventario, archivoClientes, archivoTickets, archivoFacturas);
            actualizarFrame(frame);
            JOptionPane.showMessageDialog(frame, "Los tickets de han cargado correctamente.\n\n",
                                                 "Operación Aceptada", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(frame, "No existen ningun archivo con tickets de sesiones anteriores.\n\n",
                                                 "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Este método actualiza el archivador de facturas. Se implementa para usarlo justo despues de cargar los productos serializados y así
     * actualizar el archivador de facturas
     * 
     * @param nuevoInventerio el nuevo inventario
     */
    public void actualizarArchivadorFacturas(ContenedorFacturas nuevoArchivadorFacturas)
    {
        if(nuevoArchivadorFacturas != null){
            archivadorFacturas = new ContenedorFacturas(nuevoArchivadorFacturas);
            frame.getContentPane().removeAll();
            panel = new Panel(this, frame, inventario, listaClientes, archivador, archivadorFacturas,
                              archivoInventario, archivoClientes, archivoTickets, archivoFacturas);
            frame.getContentPane().add(panel);
            SwingUtilities.updateComponentTreeUI(frame);
            frame.validate();
            //actualizarFrame(frame);
            JOptionPane.showMessageDialog(frame, "Las facturas de han cargado correctamente.\n\n",
                                                 "Operación Aceptada", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(frame, "No existen ningun archivo con facturas de sesiones anteriores.\n\n",
                                                 "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Este método refresca el panel contenedor del JFrame sin parpadeos.
     * 
     * @param frame el frame a actualizar
     */
    private void actualizarFrame(JFrame frame)
    {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        SwingUtilities.updateComponentTreeUI(frame);
        frame.validate(); 
    }
}