package Modelo.Listados;
import java.util.Comparator;
import Modelo.Productos.*;

/**
 * Write a description of class OrdenarMasVendidos here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class OrdenarMasVendidos implements Comparator<ProductoBase>
{
   @Override
   public int compare(ProductoBase p1, ProductoBase p2)
   {
       return new Double(p1.getVendido()).compareTo(new Double(p2.getVendido()));
   }
}
