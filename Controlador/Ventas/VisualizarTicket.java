package Controlador.Ventas;

import Modelo.Ventas.Ticket;
import Modelo.Contenedores.ContenedorProductos;
import Modelo.Contenedores.ContenedorTickets;
import Modelo.Contenedores.ContenedorClientes;

import Controlador.Clientes.BuscarUnCliente;

import java.awt.*;
import javax.swing.*;

/**
 * Clase cuyo cometido es visualizar los tickets por pantalla
 * 
 * @author Yeray Rodríguez Martín
 * @version 1.0
 */
public class VisualizarTicket extends InformacionTicket
{  
    private final Color COLOR = Color.cyan.darker();
    private final String OPCION_1 = "Buscar ticket por su codigo";
    private final String OPCION_2 = "Buscar todos los tickets asociados a un cliente";
    private final String OPCION_3 = "Mostrar todos los tickets";
    
    /**
     * Este método preguntará al usuario como quiere buscar el ticket que desea visualizar.
     * Las opciones serán: Visualizar todos los tickets, por codigo del ticket, por el código del cliente o por el
     * nombre del cliente
     * 
     * @param frame El frame padre
     * 
     * @return  la opcion seleccionada
     */
    public String preguntarPorAtributo(JFrame frame)
    {
        Object seleccion = JOptionPane.showInputDialog(
        frame,
        "¿Como desea buscar el ticket que quiere visualizar?\n",
        "Ver Ticket",
        JOptionPane.QUESTION_MESSAGE,
        null,  // null para icono defecto
        new String[] { OPCION_3, OPCION_1, OPCION_2 }, 
        OPCION_3);
        
        return (String)seleccion;
    }
    
    /**
     * Nos permite encontrar aquellos ticket que concuerden con los datos introducidos por el usuario y el atributo seleccionado
     * 
     * @param   frame       El frame padre
     * @param   archivador  El objeto ContendorTickets donde estarán contenidos todos los tickets
     * @param   seleccion   El atributo en el que queremos buscar coincidencias
     * 
     * @return  ContendorTickets conn todos los tickets que concuerden con los datos introducidos por el usuario, vacio
     *          si se cancela la introducción de datos o null se si introducen los datos pero no hay ninguna coincidencia
     */
    public ContenedorTickets buscarPorAtributo(JFrame frame, ContenedorClientes listaClientes, ContenedorTickets archivador, String seleccion)
    { 
        ContenedorTickets resultados = new ContenedorTickets();
        if(seleccion.equals(OPCION_1)){
            String eleccion = JOptionPane.showInputDialog(frame, "Introduzca el código del ticket que desea visualizar\n", JOptionPane.QUESTION_MESSAGE);
            if(eleccion == null){
                //Se ha presionado cancelar o se ha cerrado el JOptionPane. No hacer nada
                return resultados;  
            }else{
                if(archivador.existeAlgunTicket(eleccion)){
                    resultados.insertarTicket(archivador.buscarTicket(eleccion));
                    return resultados;
                }else{
                    return null;  
                }
            }
        }else if(seleccion.equals(OPCION_2)){
            BuscarUnCliente preguntarCliente = new BuscarUnCliente();
            preguntarCliente.preguntarPorCliente(frame, listaClientes);
            if(preguntarCliente.getCliente() == null){
                //No se ha seleccionado a ningún cliente
                return resultados;
            }else{
                String codigoCliente = preguntarCliente.getCliente().getCodigo();
                return archivador.buscarTicketClientes(codigoCliente);
            }
        }else{
            if(archivador.estaVacio()){
                JOptionPane.showMessageDialog(frame, "No existen tickets en el sistema.\n\n","Busqueda fallida", JOptionPane.INFORMATION_MESSAGE);
            }
            return archivador;
        }
    }
    
    /**
     * Pregunta al usuario por que atributo desea buscar coincidencias en los productos y los muestra por pantalla
     *  
     * @param   frame       El JFrame padre
     * @param   archivador  Contiene todos los tickets almacenados en el sistema
     * @param   panel       El JPanel donde se mostrarán los tickets
     */
    public void imprimirResultados(JFrame frame, ContenedorClientes listaClientes, ContenedorTickets archivador, JPanel panel)
    {
        String eleccion = preguntarPorAtributo(frame);
        if(eleccion == null){
            //Se ha presionado cancelar, no hacer nada
        }else{
            ContenedorTickets encontrados = buscarPorAtributo(frame, listaClientes, archivador, eleccion);
            if(encontrados == null){
                JOptionPane.showMessageDialog(frame, "No existen tickets en el archivador que correspondan \n con los criterios de busqueda seleccionados\n",
                                                        "Busqueda fallida", JOptionPane.ERROR_MESSAGE);
                                                       
            }else if(encontrados.estaVacio()){  //Se ha canelado la introduccion de datos
                //No hacer nada
            }else{
                imprimirTickets(panel, encontrados, true);
            }
        }
    }
    
    /**
     * Esté metodo mostrará los tickets en la interfaz gráfica.
     * 
     * La información que se mostrará será el código identificativo del ticket, el cliente al que corresponde,
     * la fecha de la venta y los productos vendidos
     * 
     * @param panel el panel donde se mostrará el ticket
     * @param ticket el ticket a mostrar
     * @param infoCompleta si es false solo se mostrara el cliente y su codigo, si es true se añadirán la fecha del ticket y su nº identificativo
     */
    public void imprimirTickets(JPanel panel, ContenedorTickets ticketsAImprimir, boolean infoCompleta)
    {
        panel.removeAll();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        for(Ticket ticket : ticketsAImprimir.getArchivador()){
            JPanel panelProductos = new JPanel(new SpringLayout());
            
            JPanel panelInformacion = new JPanel();
            panelInformacion.setLayout(new BoxLayout(panelInformacion, BoxLayout.Y_AXIS));

            panelInformacion.setAlignmentY(Component.TOP_ALIGNMENT);
            panelInformacion.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            panelProductos.setAlignmentY(Component.TOP_ALIGNMENT);
            panelProductos.setAlignmentX(Component.LEFT_ALIGNMENT);
 
            panelProductos.setBorder(BorderFactory.createCompoundBorder(
                   BorderFactory.createLineBorder(Color.lightGray),
                   panelProductos.getBorder())); 
                   
            
            //Accedemos el cliente del ticket y obtenemos su nombre y codigo
            String nombreCliente = ticket.getCliente().getNombre();
            String codigoCliente= ticket.getCliente().getCodigo();
            //Accedemos al ticket y obtenemos su  código y fecha
            String codigoTicket = ticket.getFecha(Ticket.CODIGO);
            String fechaTicket = ticket.getFecha(Ticket.FECHA);
            
            JLabel codigo = new JLabel("Nº de Ticket: "+codigoTicket);
            JLabel cliente = new JLabel("Cliente: "+nombreCliente+" ("+codigoCliente+")");
            JLabel fecha = new JLabel("Fecha: "+fechaTicket);
            codigo.setForeground(COLOR);
            cliente.setForeground(COLOR);
            fecha.setForeground(COLOR);
            if(infoCompleta){
                panelInformacion.add(codigo);
            }
            panelInformacion.add(cliente);
            if(infoCompleta){
                panelInformacion.add(fecha);
            }
            
            //Accedemos al ticket y obtenemos los productos
            ContenedorProductos productosEnTicket = ticket.getProductos();
            
            actualizarTicket(panelProductos, productosEnTicket);
            
            panel.add(panelInformacion);
            panel.add(Box.createRigidArea(new Dimension(0,10)));
            panel.add(panelProductos);
            panel.add(Box.createRigidArea(new Dimension(0,25)));
        }
        panel.updateUI();
    }
}
