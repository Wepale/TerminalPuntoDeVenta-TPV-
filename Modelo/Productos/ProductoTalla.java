package Modelo.Productos;
import java.io.Serializable;

/**
 * Esta clase creará productos para los cuales, a parte de marca y modelo, también necesiten talla.
 * 
 * @author Yeray Rodríguez Martín 
 * @version 1.0
 */
public class ProductoTalla extends ProductoMarca implements Serializable
{
    /**
     * La talla del producto
     */
       String talla;
       
    /**
     * Crea una instancia de ProductoTalla pasandole todos los valores a sus campos
     */
    public ProductoTalla(String familia, String codigo, String descripcion, double precio, double iva, int stock, int vendido, String marca, String modelo, String talla)
    {
       super(familia, codigo, descripcion, precio, iva, stock, vendido, marca, modelo);
       this.talla = talla;
    }
       
    /**
     * Crea una instancia de ProductoTalla a partir de otra instancia de ProductoTalla. Es decir, realiza una copia del producto que le pasamos
     * por parametro y crea un objeto nuevo
     */
    public ProductoTalla(ProductoTalla otroProducto)
    {
        super(new String(otroProducto.familia), otroProducto.codigo, new String(otroProducto.descripcion), otroProducto.precio,
                    otroProducto.iva, (int)otroProducto.stock, (int)otroProducto.vendido, new String(otroProducto.marca), new String(otroProducto.modelo));
        talla= new String(otroProducto.talla);
    }
     
    //Métodos de acceso
       
    /**
     * Devuelve la talla del producto
     * 
     * @return   La talla del rodcuto
     */
    public String getTalla()
    {
        return talla;
    }      
    
    //Métodos de modificación
       
    /**
     * Modifica la talla del producto
     * 
     * @param    talla   La nueva talla del producto
     */
    
    public void setTalla(String talla)
    {
        this.talla=talla;
    }
}
