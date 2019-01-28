package Controlador.Productos.FormulariosProducto;
import Modelo.Contenedores.ContenedorProductos;
import Modelo.Productos.ProductoBase;
import Modelo.Productos.ProductoTalla;
import Controlador.UtilidadesSpring;
import javax.swing.*;

/**
 * Write a description of class NuevoProductoGenerico here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class NuevoProductoTalla extends NuevoProductoMarca
{
    /**
     * El JTextField donde el usuario introducirá la talla del producto
     */
    protected JTextField tallaTexto;
    /**
     * Almacenará talla del producto que se quiere crear/modificar
     */
    protected String talla;
    
    /**
     * Como está clase extiende de JPanel, el contructor se creará así mismo y hará uso del método privado contruirPanel(JPanel,ProductoBase) para
     * introducir en si mismo los JTextField
     * 
     * @param producto Si es null, indica que se está creando un producto nuevo. Si es un objeto de la clase ProductoBase o sus clases hijas, se modificará
     *                      dicho producto.
     */
    public NuevoProductoTalla(ProductoBase producto)
    {
        super(producto);
    }
    
    /**
     * Esté método crea los JTextField para que el usuario pueda introducir los datos del producto a crear o modificar.
     * 
     * @param panel El panel donde se van a introducir los JTextField
     * @param producto Si es null, indica que se está creando un producto nuevo. Si es un objeto de la clase ProductoBase o sus clases hijas, se modificará
     *                      dicho producto.
     */
    public void construirPanel(JPanel panel, ProductoBase producto)
    {
        JLabel talla = new JLabel("Talla:");
        tallaTexto = new JTextField(20);
        talla.setLabelFor(tallaTexto);
        
        if(producto != null){
            ProductoTalla productoAux = (ProductoTalla)producto;
            tallaTexto.setText(productoAux.getTalla());
        }
      
        panel.add(talla);
        panel.add(tallaTexto);
        
        super.construirPanel(panel, producto);
        
        UtilidadesSpring.hacerCuadricula(panel,
                9, 2,  //filas, columnas, 
                10, 10,  //espacio inical en X, espacio inicial en Y
                10, 10); //espacio componentes X, espacio componentes Y
    }
    
    /**
     * Esté método leerá y comprobará que los datos introducidos en los JTextField sean correctos.
     * 
     * @param invntario El luegar en el que se almacenan todos los productos
     * @param producto Si es null, indica que se está creando un producto nuevo. Si es un objeto de la clase ProductoBase o sus clases hijas, se modificará
     *                      dicho producto.
     *                      
     * @return 0 si al código ya pertenece a otro producto, 1 si el código es una cadena vacia, 2 si el precio/iva/stock no son valores númericos, 3
     *          si todos los datos son correctos
     */
    public int obtenerDatos(ContenedorProductos inventario, ProductoBase producto)
    {
        int control = super.obtenerDatos(inventario, producto);
        if(control==0){
            return 0;
        }else if(control==1){
            return 1;   
        }else if(control == 2){
            return 2;
        }else{
            talla = tallaTexto.getText();
            return 3;
        }
    }
    
    /**
     * Esté método recogerá todos los datos introducidos en los JTextField y creará/modificará el producto con dichos datos.
     * 
     * ATENCION: Este método no comprueba que los datos introducidos con correctos, por lo que nos podría dar errores en tiempo de ejecución,
     * por ello hay que hacer uso del metodo obtenerDatos(...,...) y solo invocar a este método cuando nos aseguremos que los datos no tienen nigún error
     * y son completamente válidos
     * 
     * @param inventario El lugar donde se alamcenan todos los productos
     * @param producto Si es null, indica que se está creando un producto nuevo. Si es un objeto de la clase ProductoBase o sus clases hijas, se modificará
     *                      dicho producto.
     */
    public void crearProducto(ContenedorProductos inventario, ProductoBase producto)
    {
        if(producto != null){
            ProductoTalla productoAux = (ProductoTalla)producto;
            productoAux.setMarca(marca);
            productoAux.setModelo(modelo);
            productoAux.setFamilia(familia);
            productoAux.setTalla(talla);
            productoAux.setCodigo(codigo);
            productoAux.setDescripcion(descripcion);
            productoAux.setPrecio(precio);
            productoAux.setIva(iva);
            productoAux.setStock(stock);
        }else{
            ProductoTalla productoNuevo = new ProductoTalla(familia, codigo, descripcion, precio, iva, (int)stock, 0, marca, modelo, talla);
            inventario.insertarProducto(productoNuevo);
        }
    }
}
