package Vista.PanelesGenerales;

import Controlador.Productos.VentanaProducto;
import Controlador.Clientes.*;
import Controlador.Ventas.*;

import Modelo.Productos.*;
import Modelo.Contenedores.*;
import Modelo.Clientes.Cliente;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
 * Este será el JPanel que irá situado en el oeste del JPanel del contenedor del JFrame, el cual tendrá un gestor de disposición BorderLayout.
 * 
 * Contendrá los botones: "Nuevo Producto"
 *                        "Modificar Producto"
 *                        "Eliminar Producto"
 *                        "Nuevo Cliente"
 *                        "Modificar Cliente"
 *                        "Nuevo Ticket"
 *                        "Nueva Factura"
 *                        
 * Cada boton tendrá la acción correspondiente, según indique su nombre.
 * 
 * Este JPanel tendrá un gestor de disposición GridLayout con una sola columna.
 * 
 * @author Yeray Rodríguez Martín 
 * @version 1.0
 */
public class PanelOeste extends JPanel
{
    private final Icon IMAGEN = new ImageIcon(getClass().getResource("tpv.png"));
    private VentanaProducto formularioProducto;
    private VentanaCliente formularioCliente;
    private NuevoTicket ticket;
    private NuevaFactura factura;
    
    /**
     * El contructor crea un JPanel completamete funcional. Creará los botones, los cuales poseeran cada uno su acción particular.
     * 
     * Todo el trabajo recae sobre el método crearPanelOeste(....)
     * 
     * @param frame el frame padre
     * @param inventario el inventario donde se almacenan/almacenarán todos los productos
     * @param lista la lista de cliente que contiene/contendrá todos los clientes dados de alta en el sistema
     * @param archivador el archivador de tickets que contiene/contendrá todos los tickets
     * @param archivadorFacturas el archivador que contiene/contendrá todas las facturas de nuestro sistema
     * @param panel el panel donde se visualizará la información de productos, clientes, tickets o facturas. En nuestro
     *              caso será el panel cental del contendor del JFrame. Recordemos que usa un gestor BorderLayout
     */
    public PanelOeste(JFrame frame, ContenedorProductos inventario, ContenedorClientes lista, ContenedorTickets archivador, ContenedorFacturas archivadorFacturas, JPanel panel)
    {
        crearPanelOeste(frame, inventario, lista, archivador, archivadorFacturas, panel);
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
    public void crearPanelOeste(JFrame frame, ContenedorProductos inventario, ContenedorClientes lista, ContenedorTickets archivador, ContenedorFacturas archivadorFacturas, JPanel panel)
    {
        setLayout(new GridLayout(10,1,1,1));
        
        JLabel etiqueta = new JLabel(IMAGEN);
        add(etiqueta);
        
        JButton nuevoProducto = new JButton("Nuevo Producto");
        nuevoProducto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                formularioProducto = new VentanaProducto(frame, inventario,null);
            }
        });
        add(nuevoProducto);
        
        JButton modificarProducto = new JButton("Modificar Producto");
        modificarProducto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                String seleccion = JOptionPane.showInputDialog(frame, "Introduzca el código del producto que desea modificar", JOptionPane.QUESTION_MESSAGE);
            
                if(seleccion == null){
                    //Se ha presionado cancelar, no hacer nada
                }else if(inventario.existeProducto(seleccion)){
                    ProductoBase producto = inventario.buscarCodigo(seleccion);
                    formularioProducto = new VentanaProducto(frame, inventario,producto);
                }else{
                    JOptionPane.showMessageDialog(frame, "El código introducido no corresponde con ningun producto", "Producto no encontrado", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(modificarProducto);
        
        JButton eliminarProducto = new JButton("Eliminar Producto");
        eliminarProducto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){

                String seleccion = JOptionPane.showInputDialog(frame, "Introduzca el código del producto que desea eliminar", JOptionPane.QUESTION_MESSAGE);
                
                if(seleccion == null){
                    //Se ha presionado cancelar, no hacer nada
                }else if(inventario.existeProducto(seleccion)){
                    inventario.eliminarProducto(inventario.buscarCodigo(seleccion));
                    JOptionPane.showMessageDialog(frame,"El producto asociado al código " +seleccion+ " se ha eliminado correctamente del inventario",
                                                        "Operación aceptada", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(frame, "El código introducido no corresponde con ningun producto", "Producto no encontrado", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(eliminarProducto);
        
        JButton nuevoCliente = new JButton("Nuevo Cliente");
        nuevoCliente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                formularioCliente = new VentanaCliente(frame, lista, archivador, null);
            }
        });
        add(nuevoCliente);
        
        JButton modificarCliente = new JButton("Modificar Cliente");
        modificarCliente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                BuscarUnCliente buscarCliente = new BuscarUnCliente();
                buscarCliente.preguntarPorCliente(frame, lista);
                Cliente cliente = buscarCliente.getCliente();
                if(cliente == null){
                    //Se ha cancelado la eleccion del cliente, no hacer nada
                }else{
                    formularioCliente = new VentanaCliente(frame, lista, archivador, cliente);
                }
                /*
                String seleccion = JOptionPane.showInputDialog(frame, "Introduzca el código del cliente que desea modificar", JOptionPane.QUESTION_MESSAGE);
            
                if(seleccion == null){
                    //Se ha presionado cancelar, no hacer nada
                }else if(lista.existeCliente(seleccion)){
                    Cliente cliente = lista.buscarPorCodigo(seleccion);
                    formularioCliente = new VentanaCliente(frame, lista, archivador, cliente);
                }else{
                    JOptionPane.showMessageDialog(frame, "El código introducido no corresponde con ningun cliente", "Cliente no encontrado", JOptionPane.ERROR_MESSAGE);
                }*/
            }
        });
        add(modificarCliente);
        
        JButton eliminarCliente = new JButton("Eliminar Cliente");
        eliminarCliente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                String seleccion = JOptionPane.showInputDialog(frame, "Introduzca el código del cliente que desea eliminar", JOptionPane.QUESTION_MESSAGE);
                
                if(seleccion == null){
                    //No hacer nada
                }else if(lista.existeCliente(seleccion)){
                    lista.eliminarCliente(lista.buscarPorCodigo(seleccion));
                    JOptionPane.showMessageDialog(frame,"El cliente asociado al código " +seleccion+ " se ha eliminado correctamente",
                                                        "Operación aceptada", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(frame, "El código introducido no corresponde con ningun cliente", "Cliente no encontrado", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(eliminarCliente);
        
        JButton nuevoTicket = new JButton("Nuevo Ticket");
        nuevoTicket.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                ticket = new NuevoTicket(frame, inventario, lista, archivador, panel);
            }
        });
        add(nuevoTicket);
        
        JButton nuevaFactura = new JButton("Nueva Factura");
        nuevaFactura.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                factura = new NuevaFactura(frame, lista, archivador, archivadorFacturas);
            }
        });
        add(nuevaFactura);      
    }
}
