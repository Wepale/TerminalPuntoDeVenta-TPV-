package Modelo.Contenedores;
import Modelo.Productos.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.ArrayDeque;


/**
 * Esta clase puede cumplir varías funciones. Será un contenedor que podrá albergar productos. Poseé una serie de metodos para acceder
 * a los productos almecenados en él
 *  
 * Podemos usarla para que sea nuestro almacen/inventario completamente operativo. Así mismo, se utilizará para albergar los productos
 * contenidos en los tickets. Es definitiva, podrá contener un conjunto de productos destinados a distintos fines.
 * 
 * @author Yeray Rodríguez Martín 
 * @version 1.0
 */
public class ContenedorProductos implements Serializable
{
    /**
     * Este ArrayList será el encargado de almacenar productos
     */
    private ArrayList<ProductoBase> contenedor ;
    
    /**
     * Creara un contenedor de productos(ArrayList) vacio.
     */
    public ContenedorProductos()
    {
        contenedor=new ArrayList<ProductoBase>();
    }
        
    /**
     * Crea una nueva instancia de ContenedorProductos copiando los productos que haya en otra intancia de ContenedorProductos.
     * Los productos que contenga el ContenedorProductos que pasamos como parametro también seran copias completamente nuevas
     * 
     * @param otroContenedor la instancia de ContenedorProductos al que se le realizará la copia
     */
    public ContenedorProductos(ContenedorProductos otroContenedor)
    {
        contenedor= new ArrayList<ProductoBase>();
        for(ProductoBase producto : otroContenedor.contenedor){
            if(producto instanceof ProductoGenerico){
                contenedor.add(new ProductoGenerico((ProductoGenerico)producto));
            }else if(producto instanceof ProductoMarca){
                contenedor.add(new ProductoMarca((ProductoMarca)producto));
            }else if(producto instanceof ProductoTalla){
                contenedor.add(new ProductoTalla((ProductoTalla)producto));
            }
        }
    }
        
    /**
     * Metodo que utilizaremos para tener acceso al deposito
     *
     * @return  deposito El ArrayList que contiene los productos
     */
    public ArrayList<ProductoBase> getContenedor()
    {
        return contenedor;
    }
    
    /**
     * Método que utilizaremos para saber si el contendor de productos está vacio
     * 
     * @return  true si está vacio, false en caso contrario
     */
    public boolean estaVacio()
    {
        return contenedor.isEmpty();
    }
    
    /*
     * Los siguientes métodos seran los encargados de dar de alta o de baja un producto en el deposito
     */
    
    /**
     * Método para añadir un producto al deposito
     * 
     * @param  producto  El producto a añadir
     */
    public void insertarProducto(ProductoBase producto)
    {
        contenedor.add(producto);
    }
    
    /**
     * Método para eliminar un producto del deposito
     * 
     * @param producto  El producto a eliminar
     */
    public void eliminarProducto(ProductoBase producto)
    {
        contenedor.remove(producto);
    }
    
    /*
     * Los siguientes métodos serán los encargados de buscar productos en el depósito, encontrando coincidencias en sus campos
     */
    
    /**
     * Busca un producto en el deposito por su CÓDIGO
     * 
     * @param   codigo  El código del producto a buscar
     * 
     * @return  ProductoBase El producto que corresponda con dicho código o null si el codigo no se corresponde con nigun producto
     */
    public ProductoBase buscarCodigo(String codigo)
    {
        for(ProductoBase producto : contenedor){
            if(producto.getCodigo().equals(codigo)){
                return producto;
            }
        }
        return null;    //Devuelve null si no se encuentra
    }
    
    /**
     * Comprueba que existe un producto en el deposito mediante su codigo
     * 
     * @param   codigo  El código del producto a buscar
     * 
     * @return  boolean true si el producto existe en el deposito, false en caso contrario
     */
    public boolean existeProducto(String codigo)
    {
        for(ProductoBase producto : contenedor){
            if(producto.getCodigo().equals(codigo)){
                return true;
            }
        }
        return false;    //Devuelve null si no se encuentra
    }
    
    /**
     * Comprueba que exiten productos con el nombre pasado como parametro
     * 
     * @param nombre    El nombre que se desea buscar
     * 
     * @return true si existe algun producto con el mismo nombre/marca/modelo, false en caso contrario
     */
    public boolean existeNombre(String nombre)
    {
        ContenedorProductos resultados = buscarNombre(nombre);
        if(resultados == null){
            return false;
        }else{
            return true;
        }
    }
    
    /**
     * Busca un producto en el deposito por su NOMBRE, MARCA O MODELO y devuelve todos los productos que hayan tenido coincidiencias.
     * 
     * Para que una busqueda tenga éxito, todas y cada una de las palabras introducidas deben aparecer en el nombre, marca o modelo.
     * 
     * @param   buscarCadena  El String que se utilizará como criterio de busqueda
     * 
     * @return  Un ContenedorProductos con todos los productos que hallan tenido coincidencia, null si no se ha encontrado ningún producto.
     */
    public ContenedorProductos buscarNombre(String buscarCadena)
    {
        //Creamos el COntenedorProductos donde se almacenarán todas las posibles coincidencias
        ContenedorProductos resultado = new ContenedorProductos();
        
        //Pasamos la cadena a buscar a minúscula, quitamos los espacios en blanco extras y la dividimos por sus palabaras
        String[] encontrar= AyudaBusqueda.separar(buscarCadena);
        
        //Recorremos el deposito
        for(ProductoBase producto : contenedor){
            String nombre = AyudaBusqueda.modCadena(producto.getNombre()); //Modificamos el nombre
            if(AyudaBusqueda.coincideTodo(nombre,encontrar)){   //Buscamos coincidencias
                    resultado.insertarProducto(producto);
                }
        }
        if(resultado.estaVacio()){  //No existieron coincidencias
            return null;
        }else{
            return resultado;
        }
    }
    
    /**
     * Método que busca un producto en el almacén por su DESCRIPCIÓN.
     * 
     * Para que una busqueda tenga éxito, todas y cada una de las palabras introducidas deben aparecer en la descripción
     * 
     * @param   String      La cadena que se quiere comparar con la descripción
     * 
     * @return  Un ContenedorProductos con todos los productos que hallan tenido coincidencia, null si no se ha encontrado ningún producto
     */
    public ContenedorProductos buscarDescripcion(String buscarCadena)
    {
        //Creamos el ArrayDeque donde se almacenarán todas las posibles coincidencias
        ContenedorProductos resultado = new ContenedorProductos();
        
        //Pasamos la cadena a buscar a minúscula, quitamos los espacios en blanco extras y la dividimos por sus palabaras
        String[] encontrar= AyudaBusqueda.separar(buscarCadena);
        
        //Recorremos el deposito
        for(ProductoBase producto : contenedor){
            String descripcion= AyudaBusqueda.modCadena(producto.getDescripcion());
            if(AyudaBusqueda.coincideTodo(descripcion,encontrar)){
                resultado.insertarProducto(producto);
            }
        }
        if(resultado.estaVacio()){
            return null;
        }else{
            return resultado;
        }
    }
    
    /**
     * Método que busca un producto en el deposito por su TALLA. 
     * 
     * Introduciendo distintas tallas, separadas cada una de ellas por al menos un espacio, se devolverán todos los productos que coincidan con las tallas
     * introducidas.
     * 
     * @param   String      La cadena que contendrá todas las tallas con las que se quieren encontrar coincidencias
     * 
     * @return  Un ContenedorProductos con todos los productos que hallan tenido coincidencia, null si no se ha encontrado ningún producto
     */
    public ContenedorProductos buscarTalla(String buscarTalla)
    {
        //Creamos el ArrayDeque donde se almacenarán todas las posibles coincidencias
        ContenedorProductos resultado = new ContenedorProductos();
        
        //Pasamos la cadena a buscar a minúscula, quitamos los espacios en blanco extras y la dividimos por sus palabaras
        String[] encontrar= AyudaBusqueda.separar(buscarTalla);
        
        for(ProductoBase producto : contenedor){
            if(producto instanceof ProductoTalla){
                ProductoTalla aux= (ProductoTalla) producto;
                String talla = AyudaBusqueda.modCadena(aux.getTalla());
                if(AyudaBusqueda.coincideAlgo(talla,encontrar)){
                    resultado.insertarProducto(aux);
                }
            }
        }
        if(resultado.estaVacio()){  //No existen coincidencias
            return null;
        }else{
            return resultado;
        }
    }
    
    /**
     * Esté método restaurará el stock en el inventario de los productos que se hayan añadido al ticket.
     * Se utilizará cuando la venta se cancele por algún motivo.
     */
    public void resetearStock()
    {
        for(ProductoBase producto : contenedor){
            double vendido = producto.getVendido();
            producto.modStock(vendido);
        }
    }
    
    /**
     * Este método restaurá las unidades vendidas de un producto para dejarlas de nuevo a 0.
     * Se utilizará cuando se confirme o se cancele una venta.
     */
    public void resetearVendido()
    {
        for(ProductoBase producto : contenedor){
            producto.setVendido(0);
        }
    }
}