package Modelo.Ventas;
import Modelo.Productos.*;
import java.util.Calendar;
//import java.util.GregorianCalendar;

/**
 * Esta clase imprimira el ticket por pantalla, menteniendo un formato general.
 * 
 * @author Yeray Rodríguez Martin
 * @version 1.0
 */
public class Impresora
{
    //Sin campos
    
    public Impresora()
    {
        //Nada que declarar
    }
    
    public String imprimirCabecera(Ticket ticket)
    {
        Calendar fecha=ticket.getFecha();
        int anyo=fecha.get(Calendar.YEAR);
        int mes=fecha.get(Calendar.MONTH);
        int dia=fecha.get(Calendar.DAY_OF_MONTH);
        int hora=fecha.get(Calendar.HOUR_OF_DAY);
        int minuto=fecha.get(Calendar.MINUTE);
        int segundo=fecha.get(Calendar.SECOND);
       
        String formato = String.format("Fecha: %02d/%02d/%02d Hora: %02d:%02d:%02d   ", dia, (mes+1), anyo, hora, minuto, segundo);
        return formato;
        //System.out.printf("%02d%02d%02d%02d%02d%02d %n", dia, (mes+1), anyo, hora, minuto, segundo);
        //System.out.println("Ticket nº "+ticket.getCodigo());
    }
    
    public void imprimirProductos(Ticket ticket)
    {
        for(ProductoBase producto : ticket.getProductos().getContenedor()){
            
        }
    }
}
