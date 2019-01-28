package Controlador.Productos.FormulariosProducto;

import Modelo.Contenedores.ContenedorProductos;
import Modelo.Productos.*;

import javax.swing.*;

/**
 * Tanto está clase como todas las subclases tienen la finalidad de proporcionar al usuario un espacio de texto para que pueda introducir/modificar
 * los datos de un producto. Estos serán JTextField, uno por cada campo que tenga el producto. Las subclases hirán añadiando sus propios JTextField,
 * para poder introducir/modificar los campos que no compartan con su clase padre.
 * 
 * Así mismo todos los JTextField irán identificados por su JLabel correspondiente
 * 
 * @author Yeray Rodríguez Martín 
 * @version 1.0
 */
public abstract class NuevoProductoBase extends JPanel
{
    /**
     * El JTextField donde el usuario introducirá el código del producto
     */
    protected JTextField codigoTexto;
    /**
     * El JTextField donde el usuario introducirá la familia del producto
     */
    protected JTextField familiaTexto;
    /**
     * El JTextField donde el usuario introducirá la descripción del producto
     */
    protected JTextField descripcionTexto;
    /**
     * El JTextField donde el usuario introducirá precio sin iva del producto
     */
    protected JTextField precioTexto;
    /**
     * El JTextField donde el usuario introducirá el IVA del producto
     */
    protected JTextField ivaTexto;
    /**
     * El JTextField donde el usuario introducirá el stock del producto
     */
    protected JTextField stockTexto;
    
    /**
     * Almacenará el código del producto que se quiere crear/modificar
     */
    protected String codigo;
    /**
     * Almacenará la familia del producto que se quiere crear/modificar
     */
    protected String familia;
    /**
     * Almacenará la descripción del producto que se quiere crear/modificar
     */
    protected String descripcion;
    /**
     * Almacenará el precio del producto que se quiere crear/modificar
     */
    protected double precio;
    /**
     * Almacenará el IVA del producto que se quiere crear/modificar
     */
    protected double iva;
    /**
     * Almacenará el stock del producto que se quiere crear/modificar
     */
    protected double stock;
    
    /**
     * Como está clase extiende de JPanel, el contructor se creará así mismo y hará uso del método privado contruirPanel(JPanel,ProductoBase) para
     * introducir en si mismo los JTextField
     * 
     * @param producto Si es null, indica que se está creando un producto nuevo. Si es un objeto de la clase ProductoBase o sus clases hijas, se modificará
     *                      dicho producto.
     */
    public NuevoProductoBase(ProductoBase producto)
    {
        super();
        construirPanel(this, producto);
    }
    
    /**
     * Esté método crea los JTextField para que el usuario pueda introducir los datos del producto a crear o modificar.
     * 
     * @param panel El panel donde se van a introducir los JTextField
     * @param producto Si es null, indica que se está creando un producto nuevo. Si es un objeto de la clase ProductoBase o sus clases hijas, se modificará
     *                      dicho producto.
     */
    protected void construirPanel(JPanel panel, ProductoBase producto)
    { 
        JLabel codigo = new JLabel("Código:");
        codigoTexto = new JTextField(20);
        codigo.setLabelFor(codigoTexto);
 
        JLabel familia = new JLabel("Familia:");
        familiaTexto = new JTextField(20);
        familia.setLabelFor(familiaTexto);
        
        JLabel descripcion = new JLabel("Descripción:");
        descripcionTexto = new JTextField(20);
        descripcion.setLabelFor(descripcionTexto);
        
        JLabel precio = new JLabel("Precio:");
        precioTexto = new JTextField(20);
        precio.setLabelFor(precioTexto);
        
        JLabel iva = new JLabel("IVA:");
        ivaTexto = new JTextField(20);
        iva.setLabelFor(ivaTexto);
        
        JLabel stock = new JLabel("Stock:");
        stockTexto = new JTextField(20);
        stock.setLabelFor(stockTexto);
        
        if(producto != null){       //Excribos los datos actuales del Producto en los JTextField
            codigoTexto.setText(producto.getCodigo());
            familiaTexto.setText(producto.getFamilia());
            descripcionTexto.setText(producto.getDescripcion());
            precioTexto.setText(String.valueOf(producto.getPrecio()));
            ivaTexto.setText(String.valueOf(producto.getIva()));
            stockTexto.setText(String.valueOf(producto.getStock()));
        }
        
        panel.add(codigo);
        panel.add(codigoTexto);
        panel.add(familia);
        panel.add(familiaTexto);
        panel.add(descripcion);
        panel.add(descripcionTexto);
        panel.add(precio);
        panel.add(precioTexto);
        panel.add(iva);
        panel.add(ivaTexto);
        panel.add(stock);
        panel.add(stockTexto);
        
        panel.setLayout(new SpringLayout());
    }
    
    /**
     * Esté método leerá y comprobará que los datos introducidos en los JTextField sean correctos.
     * 
     * @param invntario El luegar en el que se almacenan todos los productos
     * @param Si es null, indica que se está creando un producto nuevo. Si es un objeto de la clase ProductoBase o sus clases hijas, se modificará
     *                      dicho producto.
     *                      
     * @return 0 si al código ya pertenece a otro producto, 1 si el código es una cadena vacia, 2 si el precio/iva/stock no son valores númericos, 3
     *          si todos los datos son correctos
     */
    public int obtenerDatos(ContenedorProductos inventario,ProductoBase producto){
        codigo = codigoTexto.getText();
        if(producto != null){   //Estamos intentando modificar un producto
            if(inventario.existeProducto(codigo) && !producto.getCodigo().equals(codigo)){
                return 0;
            }else if(codigo.equals("")){
                return 1;
            }
        }else{  //Estamos creando un producto
            if(inventario.existeProducto(codigo)){
                return 0;
            }else if(codigo.equals("")){
                return 1;
            }
        }
        
        familia = familiaTexto.getText();
        descripcion = descripcionTexto.getText();
        try{
            precio = Double.parseDouble(precioTexto.getText().trim());
            iva = Double.parseDouble(ivaTexto.getText().trim());
            stock = Double.parseDouble(stockTexto.getText().trim());
        }catch(NumberFormatException ex){
            return 2;
        }
        precio = Double.parseDouble(precioTexto.getText().trim());
        iva = Double.parseDouble(ivaTexto.getText().trim());
        stock = Double.parseDouble(stockTexto.getText().trim());
        return 3;
    }
    
    public abstract void crearProducto(ContenedorProductos contenedor, ProductoBase producto);
}
