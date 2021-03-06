package Modelo.Productos;
import java.io.Serializable;

/**
 * Esta clase creará productos para los cuales no sea necesario una marca o modelo. 
 * Por ejemplo en un supermercado se podría aplicar para: Frutas, Hortalizas, Pescaderia, Carniceria, Frutos Secos a granel...
 * 
 * @author Yeray Rodriguez
 * @version 1.0
 */
public class ProductoGenerico extends ProductoBase implements Serializable
{
    /**
     * El nombre del producto
     */
    private String nombre;
    
    /**
     * Crea una instancia de ProductoGenerico pasandole todos los valores a sus campos
     */
    public ProductoGenerico(String familia, String codigo, String descripcion, double precio, double iva, double stock, double vendido, String nombre)
    {
        super(familia, codigo, descripcion, precio, iva, stock, vendido);
        this.nombre=nombre;
    }
    
    /**
     * Crea una instancia de ProductoGenerico a partir de otra instancia de ProductoGenerico. Es decir, realiza una copia del producto que le pasamos
     * por parametro y crea un objeto nuevo
     * 
     * @param otroProducto el producto del que se desea realizar una copia
     */
    
    public ProductoGenerico(ProductoGenerico otroProducto)
    {
        super(new String(otroProducto.familia), otroProducto.codigo, new String(otroProducto.descripcion), otroProducto.precio, otroProducto.iva, otroProducto.stock, otroProducto.vendido);
        nombre= new String(otroProducto.nombre);
    }
    
    //Metodos de acceso
    
    /**
     * Devuelve el nombre del producto
     * 
     * @return  El nombre del producto
     */
    public String getNombre()
    {
        return nombre;
    }
    
    //Metodos de modificación
    
    /**
     * Modifica el nombre del producto por uno de nuestra elección
     * 
     * @param   El nuevo nombre del producto
     */
    
    public void setNombre(String nombre)
    {
        this.nombre=nombre;
    }
}
