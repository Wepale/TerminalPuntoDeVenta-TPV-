package Modelo.Productos;
import java.io.Serializable;

/**
 * Esta clase es la encargada de almacenar los datos comunes a todos los productos. Será la clase padre de todos los diferentes tipos de productos, cada uno
 * con información especializada.
 * 
 * 
 * @author  Yeray Rodríguez Martín
 * @version 1.0
 */
public abstract class ProductoBase implements Producto, Serializable
{
    /**
     * El tipo de producto al que pertenece (Procesadores, altavoces, discos duros... || Zapatos, bolsos, camisas...)
     */
    protected String familia;
    /**
     * El código que identifica al producto en nuestro establecimiento (No puede haber 2 iguales)
     */
    protected String codigo;
    /**
     * Breve descripción del producto
     */
    protected String descripcion;
    /**
     * Precio sin IVA del producto
     */
    protected double precio;            
    /**
     * %IVA aplicable
     */
    protected double iva;               
    /**
     * Precio con IVA del producto
     */
    protected double precioIva;         
    /**
     * ECantidad disponible en nuestro almacen
     */
    protected double stock;           
    /**
     * Cantidad de productos a vender o vendidos en una venta
     */
    protected double vendido;
    
    /**
     * Constructor de esta superclase abstracta. Se usará para crear objetos de las subclases incilizando todos sus
     * campos con los valores que pasemos como parametros
     */
    
    public ProductoBase (String familia, String codigo, String descripcion, double precio, double iva, double stock, double vendido)
    {
        this.familia = familia;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.iva = iva;
        precioIva = (precio*iva)/100.0+precio;
        this.stock = stock;
        this.vendido = vendido;
    }
    
    /*
    *******************************************
               Metodos de Acceso
    *******************************************
    */
    
    /**
     * Devuelve la familia a la pertence el producto
     * 
     * @return  La familia del producto
     */
    public String getFamilia()
    {
        return familia;
    }
    
    /**
     * Devuelve el código del producto
     * 
     * @return  El código del producto
     */
    public String getCodigo()
    {
        return codigo;
    }
    
    /**
     * Devuelve la descripcion del producto
     * 
     * @return  La descripción del producto
     */
    public String getDescripcion()
    {
        return descripcion;
    }
    
    /**
     * Devuelve El precio sin iva del producto
     * 
     * @return  El precio sin iva del producto
     */
    public double getPrecio()
    {
        return precio;
    }
    
    /**
     * Devuelve el IVA aplicable al producto
     * 
     * @return  el IVA aplicable al producto del producto
     */
    public double getIva()
    {
        return iva;
    }
    
    /**
     * Devuelve el precio con IVA del producto
     * 
     * @return  El precio con IVA del producto
     */
    public double getPrecioIva()
    {
        return precioIva;
    }
    
    /**
     * Devuelve el stock disponible del producto
     * 
     * @return  stock  El stock disponiblde del producto
     */
    public double getStock()
    {
        return stock;
    }
    
    /**
     * Devuelve cuantos productos se desean vender
     * 
     * @return  vender  La cantidad de productos que se desean vender
     */
    public double getVendido()
    {
        return vendido;
    }
    
    /**
     * Devuelve el nombre del producto. Cada subclase implementará este método segun sus necesidades.
     * 
     * @return String un String con el nombre del producto si es un ProductoGenerico o con la marca y modelo
     * si es un ProductoMarca o ProductoTalla
     */
    public abstract String getNombre();
    
     /*
    *******************************************
               Metodos de Modificacion
    *******************************************
    */
    
    /**
     * Modifica la familia del producto
     * 
     * @param   familia     La nueva familia que se le asignará al producto
     */
    public void setFamilia(String familia)
    {
        this.familia = familia;
    }
    
    /**
     * Modifica el código identificativo del producto
     * 
     * @param   codigo      El nuevo codigo identificativo que se le asignará al producto
     */
    public void setCodigo(String codigo)
    {
        this.codigo = new String(codigo);
    }
    
    /**
     * Modifica la descripción del producto
     * 
     * @param   descripcion     La nueva descripcion que se le asignará al producto
     */
    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }
    
    /**
     * Modifica el precio sin IVA del producto. Modificar este campo modificará automaticamente el campo precioIva.
     * 
     * @param   precio      El nuevo precio sin iva que le asignará al producto
     */
    public void setPrecio(double precio)
    {
        this.precio = precio;
        precioIva = (precio*iva)/100+precio;
    }
    
    /**
     * Modifica el IVA aplicable al producto. Modificar este campo modificará automaticamente el campo precioIva.
     * 
     * @param   iva     El nuevo IVA aplícable al producto
     */
    public void setIva(double iva)
    {
        this.iva = iva;
        precioIva = (precio*iva)/100+precio;
    }
    
    /**
     * Aumenta en 1 la cantidad a vender
     */
    public void aumentarVendido()
    {
        vendido++;
    }
    
    /**
     * Disminuye en 1 la cantidad a vender solo si el valor es mayor que cero
     */
    public void disminuirVendido()
    {
        if(vendido>0){
            vendido--;
        }
    }
    
    /**
     * Modifica la cantidad a vender con una valor de nuestra elección
     * 
     * @param   venta   La cantidad de productos que se desean vender
     */
    public void setVendido(double venta)
    {
        vendido=venta;
    }
    
    /**
     * Modifica la cantidad a vender sumandole un valor
     * 
     * @param   venta   La cantidad de productos que queremos sumar
     */
    public void modVendido(double venta)
    {
        vendido+=venta;
    }

    /*
     * No se implementa un metodo para cambiar directamente el precio con IVA del producto ya que depende por completo del precio sin IVA y del valor del IVA
     * aplicable.
     */
    
    /**
     * Modifica el stock disponible del producto con un valor de nuestra elección
     * 
     * @param   stock       El nuevo stock disponible del producto
     */
    public void setStock(double stock)
    {
        this.stock = stock;
    }
    
    /**
     * Modifica el stock disponible sumandole o restandole un valor
     * 
     * @param   stock   El stock que se añadirá o se restará al stock ya disponible
     */
    public void modStock(double stock)
    {
        this.stock += stock;
    }
}