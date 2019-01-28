package Modelo.Productos;
import java.io.Serializable;

/**
 * Esta clase será la encargada de crear productos en los que sean necesarios una marca y un modelo.
 * 
 * @author Yeray Rodríguez Martín
 * @version 1.0
 */
public class ProductoMarca extends ProductoBase implements Serializable
{
    /**
     * La marca del producto
     */
    String marca;
    /**
     * El modelo del producto
     */
    String modelo;
    
    /**
     * Crea una instancia de ProductoMarca pasandole todos los valores a sus campos
     */
    public ProductoMarca(String familia, String codigo, String descripcion, double precio, double iva, int stock, int vendido, String marca, String modelo)
    {
        super(familia, codigo, descripcion, precio, iva, (double)stock, (double)vendido);
        this.marca=marca;
        this.modelo=modelo;
    }
    
    /**
     * Crea una instancia de ProductoMarca a partir de otra instancia de ProductoMarca. Es decir, realiza una copia del producto que le pasamos
     * por parametro y crea un objeto nuevo
     * 
     * @param otroProducto el producto del que se desea realizar una copia
     */
    
    public ProductoMarca(ProductoMarca otroProducto)
    {
        super(new String(otroProducto.familia), otroProducto.codigo, new String(otroProducto.descripcion), otroProducto.precio,
                                                                   (int)otroProducto.iva, (int)otroProducto.stock, otroProducto.vendido);
        marca= new String(otroProducto.marca);
        modelo= new String(otroProducto.modelo);
    }
    
    //Métodos de acceso
    
    /**
     * Devuelve la marca de producto
     * 
     * @return  La marca del producto
     */
    public String getMarca()
    {
       return marca; 
    }
    
    /**
     * Devuelve el modelo del producto
     * 
     * @return  El modelo del producto
     */
    public String getModelo()
    {
        return modelo;
    }
    
    /**
     * Devuelve el nombre y el modelo en una sola cadena
     * 
     * @return  La marca y el modelo en una sola cadena
     */
    public String getNombre()
    {
        return marca.concat(" "+modelo);
    }
    
    
    //Métodos de modificación
    
    
    /**
     * Modifica la marca el producto
     * 
     * @param   La nueva marca del producto
     */
    public void setMarca(String marca)
    {
        this.marca=marca;
    }
    
    /**
     * Modifica el modelo del producto
     * 
     * @param   El nuevo modelo del producto
     */
    public void setModelo(String modelo)
    {
        this.modelo=modelo;
    }
}
