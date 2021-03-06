package Modelo.Clientes;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * Está clase será la encargada de crear a los clientes. Simplemente definirá sus atributos e 
 * implementará los métodos de acceso y modificación
 * 
 * @author Yeray Rodríguez Martín
 * @version 1.0
 */
public class Cliente implements Serializable
{
    //*************************************************************************************
    private static String AUTO_CODIGO = "00000";
    private static String AUTO_NOMBRE = "Cliente Ocasional";
    private static String AUTO_NIF = "No aplicable";
    private static String AUTO_DOMICILIO = "No aplicable";
    //**************************************************************************************
    /**
     * Cada vez que el sistema inicie tendrá un cliente almacenado automaticamente.
     * Este Cliente se utilizará para realizar una venta a un cliente esporádico que compra
     * en el establecimiento pero no está registrado en el sistema. Sus datos serán los siguientes:
     * private static final String AUTO_CODIGO = "00000";
     * private static final String AUTO_NOMBRE = "Cliente Ocasional";
     * private static final String AUTO_NIF = "No aplicable";
     * private static final String AUTO_DOMICILIO = "No aplicable";
     */
    
    /**
     * El código del cliente (único para cada cliente)
     */
    private String codigo;
    /**
     * El Nombre/Razón Social del cliente
     */
    private String nombre;
    /**
     * El NIF del cliente
     */
    private String nif;
    /**
     * El domicilio del cliente
     */
    private String domicilio;
    /**
     * La fecha de alta del cliente en el sistema
     */
    private Calendar alta;
    
    
    //CONSTRUCTORES
    
    /**
     * Creará un nuevo cliente introduciendo sus datos
     * 
     * @param codigo el codigo identificativo del cliente
     * @param nombre el nombre del cliente
     * @param nif el NIF del cliente
     * @param domicilio el domicilio
     */
    public Cliente(String codigo, String nombre, String nif, String domicilio)
    {
        this.codigo=codigo;
        this.nombre=nombre;
        this.nif=nif;
        this.domicilio=domicilio;
        alta= new GregorianCalendar();
    }
    
    /**
     * Creará un nuveo cliente copiando los datos de otro cliente
     * 
     * @param otroCliente el cliente al cual se le copiarán los datos
     */
    public Cliente(Cliente otroCliente)
    {
        codigo = new String(otroCliente.codigo);
        nombre = new String(otroCliente.nombre);
        nif = new String(otroCliente.nif);
        domicilio = new String(otroCliente.domicilio);
        alta = otroCliente.alta;
    }
    
    
    //MÉTODOS DE ACCESO
    
    
    /**
     * Devuelve el código identificativo del "Cliente Ocasional"
     * 
     * @return el código identifiactivo del "Cliente Ocasional"
     */
    public static String getAutoCodigo()
    {
        return AUTO_CODIGO;
    }
    
    /**
     * Devulve el nombre/razón social del "Cliente Ocasional"
     * 
     * @return El nombre/razón social del "Cliente Ocasional"
     */
    public static String getAutoNombre()
    {
        return AUTO_NOMBRE;
    }
    
    /**
     * Devulve el nif del "Cliente Ocasional"
     * 
     * @return El nif del "Cliente Ocasional"
     */
    public static String getAutoNif()
    {
        return AUTO_NIF;
    }
    
    /**
     * Devulve el domicilio del "Cliente Ocasional"
     * 
     * @return El domicilio del "Cliente Ocasional"
     */
    public static String getAutoDomicilio()
    {
        return AUTO_DOMICILIO;
    }
    
    
    //*********************************************************************************************//
    
    
    
    /**
     * Devulve el código identificativo del cliente
     * 
     * @return  El código identificativo del cliente
     */
    public String getCodigo()
    {
        return codigo;
    }
    
    /**
     * Devulve el nombre/razón social del cliente
     * 
     * @return  El nombre/razón social del cliente
     */
    public String getNombre()
    {
        return nombre;
    }
    
    /**
     * Devulve el nif del cliente
     * 
     * @return  El nif del cliente
     */
    public String getNif()
    {
        return nif;
    }
    
    /**
     * Devulve el domicilio del cliente
     * 
     * @return  El domicilio del cliente
     */
    public String getDomicilio()
    {
        return domicilio;
    }
    
    /**
     * Devulve la fecha de alta del cliente
     * 
     * @return  La fecha de alta del cliente
     */
    public Calendar getAlta()
    {
        return alta;
    }
    
    /**
     * Devulve un String con la fecha de alta del cliente en el sistema en el siguiente formato:
     * 
     *                              DD/MM/AAAA
     *                              
     * @return el string con la fecha de alta del cliente en el sistema
     */
    public String getCadenaAlta()
    {
        int anyo=alta.get(Calendar.YEAR);
        int mes=alta.get(Calendar.MONTH);
        int dia=alta.get(Calendar.DAY_OF_MONTH);
        
        String formato = String.format("%02d/%02d/%02d", dia, (mes+1), anyo);
        return formato;
    }
    
    
    //METODOS DE MODIFICACIÓN
    
    
    /**
     * Modifica el código identificativo del cliente
     * 
     * @param  El nuevo código identificativo del cliente
     */
    public void setCodigo(String Codigo)
    {
        this.codigo = new String(codigo);
    }
    
    /**
     * Modifica el nombre/razón social del cliente
     * 
     * @param  El nuevo nombre/razón social del cliente
     */
    public void setNombre(String nombre)
    {
        this.nombre= new String(nombre);
    }
    
    /**
     * Modifica el nif del cliente
     * 
     * @param  El nuevo nif del cliente
     */
    public void setNif(String nif)
    {
        this.nif= new String(nif);
    }
    
    /**
     * Modifica el domicilio del cliente
     * 
     * @param  El nuevo domicilio del cliente
     */
    public void setDomicilio(String domicilio)
    {
        this.domicilio= new String(domicilio);
    }
    
    //La fecha de alta del cliente no se podrá modificar
    
}