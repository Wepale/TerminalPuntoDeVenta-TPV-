package Controlador.Ventas;

import Modelo.Clientes.Cliente;
import Modelo.Ventas.Factura;
import Modelo.Contenedores.ContenedorClientes;
import Modelo.Contenedores.ContenedorTickets;
import Modelo.Contenedores.ContenedorFacturas;

import Controlador.Clientes.BuscarUnCliente;
import Controlador.Clientes.SeleccionarCliente;

import java.awt.*;
import javax.swing.*;


/**
 * Esta clase será la encargada de crear las facturas. Proporcionará al usuario toda la interfaz gráfica para poder realizarla.
 * Pedirá toda la información necesaria: Preguntará por el cliente al que se le desea realizar la factura, el año fiscal correspondiente
 * y los tickets a facturar. Finalmente creará la factura y la introducirá en el lugar donde se almacenen las facturas
 * 
 * @author Yeray Rodríguez Martín
 * @version 1.0
 */
public class NuevaFactura
{
    /**
     * El cliente al que se le realizará la factura
     */
    private Cliente cliente;
    /**
     * El año fiscal para introducir tickets que correspondan a este año
     */
    private int anyoFiscal;
    
    private BuscarUnCliente preguntarCliente;
    private SeleccionarTicket elegirTicket;
    private final String OPCION_1 = "Buscar por código";
    private final String OPCION_2 = "Buscar por nombre";
    private final String OPCION_3 = "Mostrar todos los clientes";
 
    /**
     * Crea toda la interfaz gráfica necesaria para crear una factura. Permitirá elegir al cliente, introducir el año fiscal deseado, seleccionar
     * los tickets a facturar y crear la factura
     * 
     * @param frame el frame padre
     * @param listaClientes todos los clientes dados de alta en el sistema
     * @param archivador todos las ventas/tickets realizadas/os hasta el momento
     * @param archivadorFacturas el lugar donde se almacenan las facturas
     */
    public NuevaFactura(JFrame frame, ContenedorClientes listaClientes, ContenedorTickets archivador, ContenedorFacturas archivadorFacturas)
    {
        preguntarCliente = new BuscarUnCliente();
        cliente = null;
        anyoFiscal = 0;
        realizarFactura(frame, listaClientes, archivador, archivadorFacturas);
    }

    /**
     * Creará toda la interfaz gráfica que permitirá al usuario introducir el año fiscal en elq ue se desea buscar las/los vantas/tickets.
     * Comprobará que los datos introducidos sean validos
     *  
     * @param frame el frame padre
     * @param listaClientes todos los clientes dados de alta en el sistema
     * @param archivador todos las ventas/tickets realizadas/os hasta el momento
     * @param archivadorFacturas el lugar donde se almacenan las facturas
     */
    public void preguntarAnyoFiscal(JFrame frame, ContenedorClientes listaClientes, ContenedorTickets archivador, ContenedorFacturas archivadorFacturas)
    {
        boolean tryActivado = false;
        String fiscal = JOptionPane.showInputDialog(frame,"Introduzca el año fiscal que desea facturar\n\n", JOptionPane.QUESTION_MESSAGE);
        if(fiscal == null){
            //Se ha cancelado o cerra el JOptionPane, se vuelve a preguntar el cliente
            realizarFactura(frame, listaClientes, archivador, archivadorFacturas);
        }else if((fiscal.length() > 0 && fiscal.length() != 4) || fiscal.equals("")){
            JOptionPane.showMessageDialog(frame, "Debe introducir un número entero de 4 dígitos\n\n", "Dato no válido", JOptionPane.ERROR_MESSAGE);
            preguntarAnyoFiscal(frame, listaClientes, archivador, archivadorFacturas);
        }else{
            try{
                int anyoFiscal = Integer.parseInt(fiscal.trim());
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(frame, "Debe introducir un número entero\n\n", "Dato no válido", JOptionPane.ERROR_MESSAGE);
                preguntarAnyoFiscal(frame, listaClientes, archivador, archivadorFacturas);
                tryActivado = true;
            }
            if(tryActivado){
                //Para que no continue con la ejecucion despues de que la llamada recursiva haya acabado
            }else{
                this.anyoFiscal = Integer.parseInt(fiscal.trim());
                preguntarTickets(frame, listaClientes, archivador, archivadorFacturas);
            }
        }
    }
    
    /**
     * Permitirá al usuario seleccionar los ticket que desea añadir a la factura
     * 
     * @param frame el frame padre
     * @param listaClientes todos los clientes dados de alta en el sistema
     * @param archivador todos las ventas/tickets realizadas/os hasta el momento
     * @param archivadorFacturas el lugar donde se almacenan las facturas
     */
    public void preguntarTickets(JFrame frame, ContenedorClientes listaClientes, ContenedorTickets archivador, ContenedorFacturas archivadorFacturas)
    {
        ContenedorTickets ticketsLimitados = archivador.buscarTicket(cliente.getCodigo(), anyoFiscal); // Se utilizará para guardar los tickets correspondientes a un cliente y aun año fiscal
        if(ticketsLimitados == null){
            //No exiten tickets para este cliente en el año fiscal introducido. Preguntamos de nuevo por el año fiscal
            JOptionPane.showMessageDialog(frame,"No existen ventas realizas al Cliente:\n\n \tNombre: "+cliente.getNombre()+"\n \tCódigo "+cliente.getCodigo()+"\n\nen el año "+
                                                "fiscal "+anyoFiscal, "Operacion cancelada", JOptionPane.INFORMATION_MESSAGE);
            preguntarAnyoFiscal(frame, listaClientes, archivador, archivadorFacturas);
        }else{
            elegirTicket = new SeleccionarTicket(frame, ticketsLimitados);   //JDialog para seleccionar los tickets
            if(elegirTicket.getTicketsSeleccionados() == null){
                //Se ha cancelado o cerrado el JDialog. Se vuelve al paso anterior (pedir el año fiscal)
                preguntarAnyoFiscal(frame, listaClientes, archivador, archivadorFacturas);
            }else{
                Factura factura = new Factura(elegirTicket.getTicketsSeleccionados(), cliente);
                archivadorFacturas.insertarFactura(factura);
                JOptionPane.showMessageDialog(frame,"La factura se ha creado correctamente\n\n", "Operación Aceptada", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    /**
     * Hara uso de todos los métodos anteriores y creará toda la interfaz gráfica necesaría para poder crear una factura.
     * 
     * @param frame el frame padre
     * @param listaClientes todos los clientes dados de alta en el sistema
     * @param archivador todos las ventas/tickets realizadas/os hasta el momento
     * @param archivadorFacturas el lugar donde se almacenan las facturas
     */
    public void realizarFactura(JFrame frame, ContenedorClientes listaClientes, ContenedorTickets archivador, ContenedorFacturas archivadorFacturas)
    {
        preguntarCliente.preguntarPorCliente(frame, listaClientes);
        cliente = preguntarCliente.getCliente();
        if(cliente == null){
            //No se ha llegado a seleccionar ningun cliente, se cancela la factura
        }else{
            preguntarAnyoFiscal(frame, listaClientes, archivador, archivadorFacturas);
        }
    }
}