package Controlador.Listados;

import java.awt.*;
import javax.swing.*;
import java.util.Calendar;

import Controlador.Listados.VentanaListados;
import Controlador.Clientes.BuscarUnCliente;
import Controlador.Ventas.VisualizarTicket;

import Modelo.Productos.*;
import Modelo.Clientes.Cliente;
import Modelo.Listados.Listado;
import Modelo.Ventas.Ticket;
import Modelo.Contenedores.ContenedorProductos;
import Modelo.Contenedores.ContenedorTickets;
import Modelo.Contenedores.ContenedorClientes;

import Controlador.UtilidadesSpring;

/**
 * Esta clase mostrará los listados por pantalla. Tendrá todos los métodos necesarios para mostrar cualquiera de los 3 listados en pantalla.
 * 
 * @author Yeray Rodríguez Martín 
 * @version 1.0
 */
public class MostrarListado
{
    private Listado listado;
    private VentanaListados elegirListado;
    private BuscarUnCliente buscarUnCliente;
    private final Color COLOR = Color.red.darker();
    
    /**
     * Creará toda la interfaz de usuario para que el cliente puede elegir el listado y fechas deseadas, así como la visualizacion de dicho listado en
     * un JPanel
     * 
     * @param frame el JFrame padre
     * @param panel el panel en el que se mostrará el listado
     * @param archivador el archivador donde se encuentran todas las ventas realizadas
     * @param listaClientes la lista de clientes donde se encuentran todos los clientes dados de alta en el sistema
     */
    public MostrarListado(JFrame frame, JPanel panel, ContenedorClientes listaClientes, ContenedorTickets archivador)
    {
        elegirListado = new VentanaListados(frame);
        listado = new Listado();
        buscarUnCliente = new BuscarUnCliente();
        mostrarListado(frame, panel, listaClientes, archivador);
    }
    
    /**
     * Este método realizará una busqueda en las ventas en un intervalo dado y se encargará de mostrar los productos en orden de más a menos vendidos
     * 
     * @param frame el JFrame padre
     * @param panel el panel en el que se mostrarán el listado
     * @param archivador el archivador donde se encuentran todas las ventas realizadas
     * @param inicio la fecha de inicio
     * @param fin la fecha final 
     */
    private void mostrarMasVendidos(JFrame frame, JPanel panel, ContenedorTickets archivador, Calendar inicio, Calendar fin)
    {
        panel.removeAll();
        panel.setLayout(new SpringLayout());
        ContenedorProductos masVendidos = listado.masVendidos(inicio, fin, archivador); //Esto devolverá los productos más vendidos ya ordenados
        if(masVendidos == null){
            JOptionPane.showMessageDialog(frame, "No se ha realizado ninguna venta en el perido especificado",
                                                 "No se han encontrado resultados", JOptionPane.INFORMATION_MESSAGE);
        }else{
            int posicion = 1;   //Indice que indicará la posición de los productos en este listado
            for(ProductoBase producto : masVendidos.getContenedor()){
                //Extraemos los datos del producto
                String nombre = producto.getNombre();
                String codigo = producto.getCodigo();
                String vendidos = String.valueOf(producto.getVendido());
                //Creamos JLabel con la informacion anterior
                JLabel jPosicion = new JLabel(posicion+".");
                jPosicion.setForeground(COLOR);
                JLabel jNombre = new JLabel(nombre+" ("+codigo+")");
                JLabel jUnidades = new JLabel("Unidades vendidas:");
                JLabel jVendidos = new JLabel(vendidos);
                jVendidos.setForeground(COLOR);
                //Añadimos los JLabel al JPanel
                panel.add(jPosicion);
                panel.add(jNombre);
                panel.add(jUnidades);
                panel.add(jVendidos);
                //Aumentamos la posición para el siguiente producto
                posicion++;
            }
            int filas = masVendidos.getContenedor().size();
            //Los cuadriculamos
            UtilidadesSpring.hacerCuadricula(panel,
                filas, 4,       //filas, columnas
                10, 10,    //Espacio inicial en X, espacio inicial en Y
                10, 10);    //separacion de los componentes en x, separacion de los componentes en Y
                
            panel.updateUI();
        }
    }
    
    /**
     * Este método realizará una busqueda en las ventas en un intervalo dado y se encargará de mostrar las ventas que han realizado cada cliente
     * 
     * @param frame el JFrame padre
     * @param panel el panel en el que se mostrará el listado
     * @param archivador el archivador donde se encuentran todas las ventas realizadas
     * @param inicio la fecha de inicio
     * @param fin la fecha final 
     */
    private void mostrarVentasPorCliente(JFrame frame, JPanel panel, ContenedorTickets archivador, Calendar inicio, Calendar fin)
    {
        VisualizarTicket verVentas = new VisualizarTicket();
        //Pedimos las ventas agrupadas por clientes en el intervalo dado
        ContenedorTickets ventasPorCliente = listado.ventasPorCliente(inicio, fin, archivador);
        if(ventasPorCliente == null){
            JOptionPane.showMessageDialog(frame, "No se ha realizado ninguna venta en el perido especificado",
                                                 "No se han encontrado resultados", JOptionPane.INFORMATION_MESSAGE);
        }else{
            verVentas.imprimirTickets(panel, ventasPorCliente, false);
        }
    }
    
    /**
     * Este método realizará una busqueda en las ventas en un intervalo dado y se encargará de mostrar las ventas que han realizado a un cliente dado
     * Solo será posible generar este tipo de listado para los clientes que estan dados de alta en el sistema.
     * 
     * @param frame el JFrame padre
     * @param panel el panel en el que se mostrará el listado
     * @param archivador el archivador donde se encuentran todas las ventas realizadas
     * @param inicio la fecha de inicio
     * @param fin la fecha final
     * @param codigoCliente el codigo del cliente del cual se quieren ver las ventas realizadas
     */
    private void mostrarVentasACliente(JFrame frame, JPanel panel, ContenedorTickets archivador, Calendar inicio, Calendar fin, String codigoCliente)
    {
        VisualizarTicket verVentas = new VisualizarTicket();
        //Pedimos las ventas del cliente dado en el intervalo dado
        ContenedorTickets ventasACliente = listado.ventasACliente(inicio, fin, archivador, codigoCliente);
        if(ventasACliente == null){
            JOptionPane.showMessageDialog(frame, "No se ha realizado ninguna venta a dicho cliente en el perido especificado",
                                                 "No se han encontrado resultados", JOptionPane.INFORMATION_MESSAGE);
        }else{
            verVentas.imprimirTickets(panel, ventasACliente, false);
        }
    }
    
    /**
     * Hará uso de todos los métodos aneriores cuando sea necesario, es decir, esté método elegirá que listado se muestra en cada momento
     * dependiendo de la seleccion de usuario
     * 
     * @param frame el JFrame padre
     * @param panel el panel en el que se mostrará el listado
     * @param archivador el archivador donde se encuentran todas las ventas realizadas
     * @param listaClientes la lista de clientes donde se encuentran todos los clientes dados de alta en el sistema
     */
    private void mostrarListado(JFrame frame, JPanel panel, ContenedorClientes listaClientes, ContenedorTickets archivador)
    {
        String eleccion = elegirListado.getEleccionUsuario();
        if(eleccion == null || eleccion.equals("")){
            //Se ha cerrado el JDialog y no se han recogido los datos. No hacer nada
        }else{
            Calendar inicio = elegirListado.getFechaInicio();
            Calendar fin = elegirListado.getFechaFinal();
            if(eleccion.equals(elegirListado.OPCION_1)){ //Más vendidos
                mostrarMasVendidos(frame, panel, archivador, inicio, fin);
            }else if(eleccion.equals(elegirListado.OPCION_2)){  //Por cliente
                mostrarVentasPorCliente(frame, panel, archivador, inicio, fin);
            }else{  //A un solo cliente
                buscarUnCliente.preguntarPorCliente(frame, listaClientes);  //Toda la interfaz gráfica necesaria para que el usuario seleccione al cliente
                Cliente cliente = buscarUnCliente.getCliente();
                if(cliente == null){
                    //No se ha llegado a seleccionar ningún cliente. No hacer nada
                }else{
                    mostrarVentasACliente(frame, panel, archivador, inicio, fin, cliente.getCodigo());
                }
            }
        }
    }
}
