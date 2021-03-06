package Modelo.Contenedores;
import Modelo.Ventas.Ticket;
import Modelo.Clientes.Cliente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Esta clase hara la función de archivador de tickets. Sera el "lugar" en el que se almacenen los tickets.
 * 
 * @author Yeray Rodriguez Martin 
 * @version 1.0
 */
public class ContenedorTickets implements Serializable
{
    /**
     * Este ArrayList será el lugar en el que se almacenen los tickets creados
     */
    private ArrayList<Ticket> archivador;
    
    /**
     * Crea un archivador vacio en el que almacenar los tickets.
     */
    public ContenedorTickets()
    {
        archivador = new ArrayList<Ticket>();
    }
    
    /**
     * Crea un nuevo ContenedorTickets para almacenar tickets copiando los tickets que contiene otro ContendorTickets
     * 
     * @return Una instancia de ContenedorTickets completamente nueva, incluyendo los tickets, los cuales serán instancias nuevas.
     */
    public ContenedorTickets(ContenedorTickets otroArchivador)
    {
        archivador = new ArrayList<Ticket>();
        for(Ticket ticket : otroArchivador.archivador){
            archivador.add(new Ticket(ticket));
        }
    }
    
    /**
     * Método para acceder al archivador
     * 
     * @return      El campo archivador
     */
    public ArrayList<Ticket> getArchivador()
    {
        return archivador;
    }
    
    /**
     * Método para saber si el archivador está vacio
     * 
     * @return true si esta vacio, false en caso contrario
     */
    public boolean estaVacio()
    {
        return archivador.isEmpty();
    }
    
    /**
     * Método para añadir un ticket al archivador
     * 
     * @param   Ticket ticket   El ticket a añadir
     */
    public void insertarTicket(Ticket ticket)
    {
        archivador.add(ticket);
    }
    
    /**
     * Método para buscar un ticket por su codigo
     * 
     * @param   String codigo  El código del ticket a buscar
     * 
     * @return Ticket el ticket correspondiente al código introducido, null si no existe
     */
    public Ticket buscarTicket(String codigo)
    {
        String cod= AyudaBusqueda.modCadena(codigo);
        for(Ticket ticket : archivador){
            if(ticket.getFecha(Ticket.CODIGO).equals(cod)){
                return ticket;
            }
        }
        return null;
    }
    
    /**
     * Método para saber si existe un ticket mediante su codigo
     * 
     * @param codigoTicket el codigo que se quiere buscar
     * 
     * @return true si en el archivador existe un ticket con dicho codigo, false en caso contrario
     */
    public boolean existeAlgunTicket(String codigoTicket)
    {
        for(Ticket ticket : archivador){
            String codigo2=ticket.getFecha(Ticket.CODIGO);
            if(codigoTicket.equals(codigo2)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Metodo para saber si existe un ticket asociado a un Cliente
     * 
     * @param codigoCliente El código del cliente
     * 
     * @return true si existe un ticket asociado a dicho cliente, false en caso contrario
     */
    public boolean existeTicket(String codigoCliente)
    {
        for(Ticket ticket : archivador){
            String codigo2=ticket.getCliente().getCodigo();
            if(codigoCliente.equals(codigo2)){
                return true;
            }
        }
        return false;    //No hay ningun ticket de dicho cliente
    }
    
    /**
     * Método para buscar todos los tickets que correspondan a un cliente
     * 
     * @param codigoCliente   El codigo del cliente 
     * 
     * @return  ContendorTickets Todos los tickets asociados a un cliente
     */
    public ContenedorTickets buscarTicketClientes(String codigoCliente)
    {
        ContenedorTickets archivadorAux = new ContenedorTickets();
        for(Ticket ticket : archivador){
                String codigo2=ticket.getCliente().getCodigo();    //El codigo del cliente que aparece en el ticket
                if(codigoCliente.equals(codigo2)){    //Comparamos
                    archivadorAux.archivador.add(ticket);
                }
        }
        if(archivador.isEmpty()){       //No se ha encontrado ningun ticket asociado a este cliente
            return null;
        }else{
            return archivadorAux;
        }
    }
    
    /**
     * Método para buscar todos los tickets mediante el nombre de un cliente
     * 
     * @param nombreCliente El nombre del que se desea encontrar resultados
     * 
     * @return ContenedorTickets con todos los tickets con coincidencias
     */    
    public ContenedorTickets buscarNombre(String nombreCliente)
    {
        ContenedorTickets resultado = new ContenedorTickets();
        
        //Pasamos la cadena a buscar a minúscula, quitamos los espacios en blanco extras y la dividimos por sus palabaras
        String[] encontrar= AyudaBusqueda.separar(nombreCliente);
        
        //Recorremos el archivador
        for(Ticket ticket : archivador){
            String nombre= AyudaBusqueda.modCadena(ticket.getCliente().getNombre());
            if(AyudaBusqueda.coincideTodo(nombre,encontrar)){
                resultado.insertarTicket(ticket);
            }
        }
        if(resultado.estaVacio()){
            return null;
        }else{
            return resultado;
        }
    }
    
    /**
     * Método para buscar todos los tickets que correspondan a un cliente en un determinado año fiscal
     * 
     * @param codigoCliente   El codigo del cliente 
     * @param anyoFiscal      El año fiscal
     * 
     * @return  ContendorTickets Todos los tickets asociados a un cliente en un año fiscal dado
     */
    public ContenedorTickets buscarTicket(String codigoCliente, int anyoFiscal)
    {
        ContenedorTickets archivadorAux = new ContenedorTickets();
        for(Ticket ticket : archivador){
                int year=ticket.getFecha().get(Calendar.YEAR);      //El año en el que se creo el ticket
                String codigo2=ticket.getCliente().getCodigo();     //El codigo del cliente que aparece en el ticket
                if(codigoCliente.equals(codigo2) && year == anyoFiscal){    //Comparamos
                    archivadorAux.archivador.add(ticket);
                }
        }
        if(archivadorAux.estaVacio()){       //No se ha encontrado ningun ticket asociado a este cliente en el año fiscal indicado
            return null;
        }else{
            return archivadorAux;
        }
    }
    
    /**
     * Método para buscar el primer ticket que pertenezca a un cliente
     * 
     * @param   codigoCliente   El código del cliente que se desea buscar
     * 
     * @return  Ticket El primer que se encuentre y que pertenezca el cliente buscado
     */
    public Ticket buscarPrimerTicket(String codigoCliente)
    {
        for(Ticket ticket : archivador){
            String codigo2=ticket.getCliente().getCodigo();
            if(codigoCliente.equals(codigo2)){
                return ticket;
            }
        }
        return null;    //No hay ningun ticket de dicho cliente
    }
    
    /**
     * Método para buscar todos los tickets que esten comprendidos entre dos fechas
     * 
     * @param inicio La fecha inicial
     * @param fin La fecha final
     * 
     * @return Un ContenedorTickets con los tickets comprendidos entre estas dos fechas, null si no se encuentrá ninguno
     */
    public ContenedorTickets limitar(Calendar inicio, Calendar fin)
    {
        ContenedorTickets archivadorAux = new ContenedorTickets();
        for(Ticket ticket: archivador){
            if(ticket.getFecha().after(inicio) && ticket.getFecha().before(fin)){
                archivadorAux.archivador.add(ticket);
            }
        }
        if(archivadorAux.estaVacio()){
            return null;
        }else{
            return archivadorAux;
        }
    }
}
