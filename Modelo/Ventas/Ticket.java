package Modelo.Ventas;

import java.io.Serializable;

import Modelo.Contenedores.ContenedorProductos;
import Modelo.Clientes.Cliente;
import Modelo.Productos.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Está clase se encargará de realizar los tickets. Una vez creado un ticket, los productos que figuran en él permaneceran inalterables aunque alguna
 * información de los mismos productos que hemos vendido se modifiquen en el almacen.
 * 
 * Esto es totalmente necesario, ya que estos tickets representan una venta y se usaran para efectos fiscales, por lo que deben de reflegar los productos
 * con la misma información que tenian en el momento de su venta.
 * 
 * @author Yeray Rodríguez Martín
 * @version 1.0
 */
public class Ticket implements Serializable
{
    /**
     * El código del ticket. Es unico para cada ticket
     */
    private String codigo;
    /**
     * La fecha y hora de la creación del ticket
     */
    private Calendar fecha;                    
    /**
     * Los productos contenidos en el ticket
     */
    private ContenedorProductos productos;
    /**
     * El cliente al que pertenece el ticket
     */
    private Cliente cliente;
    /**
     * Se utilizará en el método getFecha(int) cuando se desee utilizar la fecha en formato código
     */
    public static final int CODIGO = 1;
    /**
     * Se utilizará en el método getFecha(int) cuando se desee obtener la fecha en un string
     */
    public static final int FECHA = 2;
    
    /**
     * Creará un ticket pasandole los productos a vender y el cliente al que se le realiza la venta
     * 
     * @param ContenedorProductos con los productos a vender
     * @param Cliente el cliente al que se le realizará la venta
     */
    public Ticket(ContenedorProductos productosAVender, Cliente cliente)
    {
        fecha = new GregorianCalendar();
        productos = new ContenedorProductos(productosAVender);
        this.cliente = new Cliente(cliente);
        codigo = getFecha(CODIGO);
    }
    
    /**
     * Copiará otro ticket y creará una instancia completamente nueva. 
     * 
     * @param otroTicket el ticket que se desea copiar
     */
    public Ticket(Ticket otroTicket)
    {
        fecha= otroTicket.fecha;
        productos = new ContenedorProductos(otroTicket.productos);
        cliente = new Cliente(otroTicket.cliente);
        codigo = otroTicket.codigo;
    }
    
    /**
     * Método para acceder a la fecha del ticket
     * 
     * @return Calendar la fecha del ticket en formato Calendar
     */
    public Calendar getFecha()
    {
        return fecha;
    }
    
    /**
     * Método para acceder a los productos contenidos en el ticket
     * 
     * @return ContenedorProductos los productos contenidos en el ticket
     */
    public ContenedorProductos getProductos()
    {
        return productos;
    }
    
    /**
     * Metodo para acceder al cliente del ticket
     * 
     * @return  Cliente El cliente al que pertenece el ticket
     */
    public Cliente getCliente()
    {
        return cliente;
    }
    
    /**
     * Depende del valor introducido como parametro, este método devolverá el código identificativo de la factura,
     * el cual será la fecha de la creación siguiendo el siguiente formato: 
     *      
     *            AAAAMMDDHHmmSS
     *            
     * O bien la fecha de creación de la factura en un String siguiente el siguiente formato:
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
    
    /**
     * Método para calcular el precio total de todos los productos que existen el ticket teniendo un cuenta las unidades vendidas
     * 
     * @return el valor total de los productos contenidos en el ticket
     */
    public double valorProductos()
    {
        double total=0;
        for(ProductoBase producto : productos.getContenedor()){
            total += producto.getVendido()*producto.getPrecioIva();
        }
        return total;
    }
}