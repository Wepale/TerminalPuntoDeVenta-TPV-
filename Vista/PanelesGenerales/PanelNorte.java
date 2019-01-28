package Vista.PanelesGenerales;
import Modelo.Contenedores.*;
import Controlador.Productos.*;
import Controlador.Clientes.*;
import Controlador.Ventas.*;
import Controlador.Listados.MostrarListado;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Este será el JPanel que irá situado en el norte del JPanel de la frame, el cual tendrá un gestor de disposición BorderLayout.
 * 
 * Contendrá los botones: "Ver Inventario"
 *                        "Ver Clientes"
 *                        "Ver Tickets"
 *                        "Ver Facturas"
 *                        "Ver Listado"
 *                        
 * Cada boton tendrá la acción correspondiente, según indica su nombre.
 * 
 * Este JPanel tendrá un gestor de disposición FlowLayout.
 * 
 */
public class PanelNorte extends JPanel
{
    private MostrarProducto resultadoProducto;
    private MostrarCliente resultadoCliente;
    private VisualizarTicket resultadoTicket;
    private VisualizarFactura resultadoFactura;
    private MostrarListado resultadoListado;
    
    /**
     * El contructor crea un JPanel completamete funcional. Creará los botones, los cuales poseeran cada uno su acción particular.
     * 
     * Todo el trabajo recae sobre el método crearPanelNorte(....)
     * 
     * @param frame el frame padre
     * @param inventario el inventario donde se almacenan/almacenarán todos los productos
     * @param lista la lista de cliente que contiene/contendrá todos los clientes dados de alta en el sistema
     * @param archivador el archivador de tickets que contiene/contendrá todos los tickets
     * @param archivadorFacturas el archivador que contiene/contendrá todas las facturas de nuestro sistema
     * @param panel el panel donde se visualizará la información de productos, clientes, tickets o facturas. En nuestro
     *              caso será el panel cental del contendor del JFrame. Recordemos que usa un gestor BorderLayout
     */
    public PanelNorte(JFrame frame, ContenedorProductos inventario, ContenedorClientes lista,
                      ContenedorTickets archivador, ContenedorFacturas archivadorFacturas, JPanel panel)
    {
        resultadoProducto = new MostrarProducto();
        resultadoCliente = new MostrarCliente();
        resultadoTicket = new VisualizarTicket();
        resultadoFactura = new VisualizarFactura();
        crearPanelNorte(frame, inventario, lista, archivador, archivadorFacturas, panel);
    }
    
    /**
     * Crea todos los JButton y les asigna sus acciones. Inicializa el JPanel e introduce los JButton en él.
     * 
     * @param frame el frame padre
     * @param inventario el inventario donde se almacenan/almacenarán todos los productos
     * @param lista la lista de cliente que contiene/contendrá todos los clientes dados de alta en el sistema
     * @param archivador el archivador de tickets que contiene/contendrá todos los tickets
     * @param archivadorFacturas el archivador que contiene/contendrá todas las facturas de nuestro sistema
     * @param panel el panel donde se visualizará la información de productos, clientes, tickets o facturas. En nuestro
     *              caso será el panel cental del contendor del JFrame. Recordemos que usa un gestor BorderLayout
     */
    public void crearPanelNorte(JFrame frame, ContenedorProductos inventario, ContenedorClientes lista,
                                ContenedorTickets archivador, ContenedorFacturas archivadorFacturas, JPanel panel)
    {
        setLayout(new FlowLayout());
        
        JButton botonProductos = new JButton("Ver Inventario");
        botonProductos.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                resultadoProducto.imprimirResultados(frame, inventario, panel);
            }
        });
        add(botonProductos);
        
        JButton botonClientes = new JButton("Ver Clientes");
        botonClientes.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                resultadoCliente.imprimirResultados(frame, lista, panel);
            }
        });
        add(botonClientes);
        
        JButton botonTickets = new JButton("Ver tickets");
        botonTickets.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                resultadoTicket.imprimirResultados(frame, lista, archivador, panel);
            }
        });
        add(botonTickets);
        
        JButton botonFacturas = new JButton("Ver Facturas");
        botonFacturas.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                resultadoFactura.imprimirResultados(frame, lista, archivadorFacturas, panel);
            }
        });
        add(botonFacturas);
        
        JButton botonListados = new JButton("Ver Listados");
        botonListados.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                resultadoListado = new MostrarListado(frame, panel, lista, archivador);
            }
        });
        add(botonListados);    
    }
}                  

