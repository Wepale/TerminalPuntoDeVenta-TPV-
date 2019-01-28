package Modelo.Productos;


/**
 * Interfaz de los productos. Contendrá la signatura de métodos comunes a los tres tipos de productos
 * 
 * @author Yeray Rodríguez Martín 
 * @version 1.0
 */

public interface Producto
{
    /**
     * Devuelve la familia a la pertence el producto
     * 
     * @return  La familia del producto
     */
    public String getFamilia();
    
    /**
     * Devuelve el código del producto
     * 
     * @return  El código del producto
     */
    public String getCodigo();
    
    /**
     * Devuelve la descripcion del producto
     * 
     * @return  La descripción del producto
     */
    public String getDescripcion();
    
    /**
     * Devuelve El precio sin IVA del producto
     * 
     * @return  El precio sin IVA del producto
     */
    public double getPrecio();
    
    /**
     * Devuelve el IVA aplicable al producto
     * 
     * @return  el IVA aplicable al producto del producto
     */
    public double getIva();
    
    /**
     * Devuelve el precio con IVA del producto
     * 
     * @return  El precio con IVA del producto
     */
    public double getPrecioIva();
    
    /**
     * Devuelve el stock disponible del producto
     * 
     * @return  stock  El stock disponiblde del producto
     */
    public double getStock();
    
    /**
     * Devuelve cuantos productos se desean vender
     * 
     * @return  vender  La cantidad de productos que se desean vender
     */
    public double getVendido();
    
    /*
     ***********************************************
     **************Metodos de modificación**********
     **********************************************/

    
    /**
     * Modifica la familia del producto
     * 
     * @param   familia     La nueva familia que se le asignará al producto
     */
    public void setFamilia(String familia);
    
    /**
     * Modifica el codigo identificativo del producto
     * 
     * @param   codigo      El nuevo codigo identificativo que se le asignará al producto
     */
    public void setCodigo(String codigo);
    
    /**
     * Modifica la descripción del producto
     * 
     * @param   descripcion     La nueva descripcion que se le asignará al producto
     */
    public void setDescripcion(String descripcion);
    
    /**
     * Modifica el precio sin IVA del producto. Modificar este campo modificará automaticamente el campo precioIva.
     * 
     * @param   precio      El nuevo precio sin iva que le asignará al producto
     */
    public void setPrecio(double precio);
    
    /**
     * Modifica el IVA aplicable al producto. Modificar este campo modificará automaticamente el campo precioIva.
     * 
     * @param   iva     El nuevo IVA aplícable al producto
     */
    public void setIva(double iva);
    
    /**
     * Aumenta en 1 la cantidad a vender
     */
    public void aumentarVendido();
    
    /**
     * Disminuye en 1 la cantidad a vender solo si el valor es mayor que cero
     */
    public void disminuirVendido();
    
    /**
     * Modifica la cantidad a vender con una valor de nuestra elección
     * 
     * @param   venta   La cantidad de productos que se desean vender
     */
    public void setVendido(double venta);
    
    /**
     * Modifica la cantidad a vender sumandole o restandole un valor
     * 
     * @param   venta   La cantidad de productos que queremos sumar
     */
    public void modVendido(double venta);

    /*
     * No se implementa un metodo para cambiar directamente el precio con IVA del producto ya que depende por completo del precio sin IVA y del valor del IVA
     * aplicable.
     */
    
    /**
     * Modifica el stock disponible del producto con un valor de nuestra elección
     * 
     * @param   stock       El nuevo stock disponible del producto
     */
    public void setStock(double stock);
    
    /**
     * Modifica el stock disponible sumandole o restandole un valor
     * 
     * @param stock el stock que se añadirá o restará al stock ya disponible
     */
    public void modStock(double stock);

}
