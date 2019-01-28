package Modelo.Ventas;

import java.io.Serializable;

import Modelo.Contenedores.ContenedorTickets;
import Modelo.Clientes.Cliente;
import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * Esta clase será la encargada de crear las facturas. Las facturas contendran un cliente y todos los tickets que se deseen facturar de dicho cliente,
 * así como la información del vendedor, la fecha de creación de la factura y código identificativo único.
 * 
 * @author Yeray Rodríguez
 * @version 1.0
 */
public class Factura implements Serializable
{
    /**
     * Código identificativo único
     */
    private String codigo;
    /**
     * CIF del vendedor
     */
    private static String cif;                 
    /**
     * Razón Social del vendedor
     */
    private static String razonSocial;          
    /**
     * Los tickets contenidos en esta factura
     */
    private ContenedorTickets tickets;          
    /**
     * El cliente al que pertenecen la factura
     */
    private Cliente cliente;                   
    /**
     * La fecha de la factura
     */
    private Calendar fecha;                     
    /**
     * Se utilizará en el método getFecha(int) cuando se desee utilizar la fecha en formato código
     */
    public static final int CODIGO = 1;
    /**
     * Se utilizará en el método getFecha(int) cuando se desee utilizar la fecha en formato String
     */
    public static final int FECHA = 2;
    
    /**
     * Creará una factura con todos los tickets que se deseen y el cliente al que pertenecen
     * 
     * @param tickets los tickets que se desean facturar
     * @param cliente el cliente al que se desea facturar
     */
    public Factura(ContenedorTickets tickets, Cliente cliente)
    {
        this.cif= cif;
        this.razonSocial= razonSocial;
        this.tickets= tickets;
        this.cliente= cliente;
        fecha= new GregorianCalendar();
        codigo = getFecha(CODIGO);
    }
    
    /**
     * Realiza una copia de una factura, creando una factura completamente nueva
     * 
     * @param otraFactura la factura que se desea copiar
     */
    public Factura(Factura otraFactura)
    {
        codigo= new String(otraFactura.codigo);
        cif= new String(otraFactura.cif);
        razonSocial= new String(otraFactura.razonSocial);
        tickets= new ContenedorTickets(otraFactura.tickets);
        cliente= new Cliente(otraFactura.cliente);
        fecha= otraFactura.fecha;
    }
    
    /**
     * Devuelve el cif del vendedor
     * 
     * @return el cif del vendedor
     */
    public static String getCif()
    {
        return cif;
    }
    
    /**
     * Devuelve la Razón Social del vendedor
     * 
     * @return la razón social del vendedor
     */
    public static String getRazonSocial()
    {
        return razonSocial;
    }
    
    /**
     * Devuelve los tickets contenidos en esta factura
     * 
     * @return ContenedorTickets    Los tickets contenidos en esta factura
     */
    public ContenedorTickets getTickets()
    {
        return tickets;
    }
    
    /**
     * Devuelve el cliente al que pertenece esta factura
     * 
     * @return  Cliente El cliente al que pertenece esta factura
     */
    public Cliente getCliente()
    {
        return cliente;
    }
    
    /**
     * Devuelve la fecha de la factura
     * 
     * @return  Calendar    La fecha de la factura
     */
    public Calendar getFecha()
    {
        return fecha;
    }
    
    /**
     * Modifica la Razón Social del vendedor
     * 
     * @param razonSocial la nueva razón social del vendedor
     */
    public static void setRazonSocial(String nuevaRazon)
    {
        razonSocial = nuevaRazon;
    }
    
    /**
     * Modifica el CIF del vendedor
     * @param cif el nuevo CIF del vendedor
     */
    public static void setCif(String nuevoCif)
    {
        cif = nuevoCif;
    }
    
    /**
     * Depende del valor introducido como parametro, este método devolverá el código identificativo de la factura,
     * el cual será la fecha de la creación siguiendo el siguiente formato: 
     *      
     *            AAAAMMDDHHmmSS
     *            
     * O bien la fecha de creación de la factura siguiente el siguiente formato:
     * 
     *           DD/MM/AAAA HH:mm:SS
     *           
     *           
     *   Siendo AAAA=año, MM=mes, DD=dia, HH=hora, mm=minuto, SS=segundo.
     *   
     * @param tipo la variable final CODIGO si se desea el código o la variable final FECHA si se desea la fecha. Si se introduce otro valor,
     *        devolverá una cadena vacía
     *        
     * @return String la fecha en formato String o el código.
     */
    public String getFecha(int tipo)
    {
        int year=fecha.get(Calendar.YEAR);
        int month=fecha.get(Calendar.MONTH);
        int day=fecha.get(Calendar.DAY_OF_MONTH);
        int hour=fecha.get(Calendar.HOUR_OF_DAY);
        int minute=fecha.get(Calendar.MINUTE);
        int second=fecha.get(Calendar.SECOND);
        
        String anyo= String.format("%04d", year);
        String mes= String.format("%02d", (month+1));
        String dia= String.format("%02d", day);
        String hora= String.format("%02d", hour);
        String minuto= String.format("%02d", minute);
        String segundo= String.format("%02d", second);
        
        if(tipo == 1){
            return anyo.concat(mes).concat(dia).concat(hora).concat(minuto).concat(segundo);
        }else if(tipo ==2){
            return dia+"/"+mes+"/"+anyo+" "+hora+":"+minuto+":"+segundo;
        }else{
            return "";
        }
    }
}
