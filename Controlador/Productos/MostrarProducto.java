package Controlador.Productos;
import Controlador.UtilidadesSpring;
import Modelo.Contenedores.*;
import Modelo.Productos.*;

import java.awt.*;
import javax.swing.*;

/** 
 * Está clase será la encargada de proporcionar toda la interfaz gráfica para que el usuario pueda buscar un producto en el inventario y ver sus
 * datos en pantalla. El usuarío podrá buscar los productos por su código, por su nombre, por su talla o podrá visualizar todos los productos que existen
 * en el inventario.
 * 
 * Si se busca por código, se mostrará un único producto puesto que los códigos son únicos. Si se busca por nombre o talla se mostrarán todas las posibles
 * coincidencias.
 * 
 * @author Yeray Rodríguez
 * @version 1.0
 */
public class MostrarProducto
{
    private final String OPCION_1 = "Buscar producto por código";
    private final String OPCION_2 = "Buscar producto por nombre";
    private final String OPCION_3 = "Buscar producto por talla";
    private final String OPCION_4 = "Mostar todos los productos";
    
    private Color color = Color.cyan.darker();
    
    private final int COLUMNAS = 8;
    
    /**
     * Pregunta al usuario al atributo por el que quiere buscar coincidencias
     * 
     * @return  String Un String con la opcion seleccionada
     */
    public String preguntarAtributo(JFrame frame)
    {
        Object seleccion = JOptionPane.showInputDialog(
                                        frame,
                                        "¿Que atributo desea usar para buscar los productos?",
                                        "Buscar Productos",
                                        JOptionPane.QUESTION_MESSAGE,
                                        null,  // null para icono defecto
                                        new String[] { OPCION_4, OPCION_1, OPCION_2, OPCION_3 }, 
                                        OPCION_4);
        return (String)seleccion;
    }
    
    /**
     * Nos permite encontrar aquellos productos que concuerden con los datos introducidos por el usuario y el atributo seleccionado
     * 
     * @param   contenedor  El objeto ContendorProducto donde estarán contenidos los productos
     * @param   seleccion   El atributo en el que queremos buscar coincidencias
     * 
     * @return  ContendorClientes   Todos los clientes que concuerden con los datos introducidos por el usuario
     */
    public ContenedorProductos buscarPorAtributo(JFrame frame, ContenedorProductos contenedor, String seleccion)
    { 
        ContenedorProductos resultados = new ContenedorProductos();
        if(seleccion.equals(OPCION_1)){
            String eleccion = JOptionPane.showInputDialog(frame, "Introduzca el código del producto que desea buscar", JOptionPane.QUESTION_MESSAGE);
            if(contenedor.existeProducto(eleccion)){
                resultados.insertarProducto(contenedor.buscarCodigo(eleccion));
                return resultados;
            }else{
                return null;  
            }
        }else if(seleccion.equals(OPCION_2)){
            String eleccion = JOptionPane.showInputDialog(frame, "Introduzca el nombre del producto que desea buscar", JOptionPane.QUESTION_MESSAGE);
            return contenedor.buscarNombre(eleccion);
        }else if(seleccion.equals(OPCION_3)){
            String eleccion = JOptionPane.showInputDialog(null, "Introduzca la talla del producto que desea buscar", JOptionPane.QUESTION_MESSAGE);
            return contenedor.buscarTalla(eleccion);
        }else{
            return contenedor;
        }
    }
    
    /**
     * La cabecera para organizar los datos de los productos
     * 
     * @param Panel El panel donde se va a introducir esta cabecera
     */
    public void imprimirCabecera(JPanel panel)
    {
        panel.removeAll();
        JLabel codigo = new JLabel("Código");
        codigo.setForeground(color);
        
        JLabel familia = new JLabel("Familia");
        familia.setForeground(color);
        
        JLabel talla = new JLabel("Talla");
        talla.setForeground(color);
        
        JLabel nombre = new JLabel("Nombre");
        nombre.setForeground(color);
        
        JLabel precio = new JLabel("Precio");
        precio.setForeground(color);
        
        JLabel iva = new JLabel("IVA");
        iva.setForeground(color);
        
        JLabel precioIva = new JLabel("Precio con IVA");
        precioIva.setForeground(color);
        
        JLabel stock = new JLabel("Stock");
        stock.setForeground(color);
        
        panel.add(codigo);
        panel.add(familia);
        panel.add(talla);
        panel.add(nombre);
        panel.add(precio);
        panel.add(iva);
        panel.add(precioIva);
        panel.add(stock);
    }
    
    /**
     * Muestra la información de los productos en un JPanel
     * 
     * @param   contenedor  El objeto ContendorProductos que contiene los productos que quermos mostrar
     * @param   JPanel  El JPanel donde se mostraran
     */
    public void imprimirProductos(ContenedorProductos contenedor, JPanel panel)
    {
        String nombreP;
        String tallaP;
        for(ProductoBase producto : contenedor.getContenedor()){
            String codigoP = new String(producto.getCodigo());
            String familiaP = new String(producto.getFamilia());
            String precioP = new String(String.format("%.2f", producto.getPrecio()));
            String ivaP = new String(Double.toString(producto.getIva()));
            String precioIvaP = new String(String.format("%.2f",producto.getPrecioIva()));
            String stockP = new String(Double.toString(producto.getStock()));
            
            if(producto instanceof ProductoGenerico){
                ProductoGenerico aux = (ProductoGenerico)producto;
                nombreP = new String(aux.getNombre());
                tallaP = new String("No aplicable");
            }else if(producto instanceof ProductoTalla){
                ProductoTalla aux = (ProductoTalla)producto;
                nombreP = new String(aux.getMarca()+" "+aux.getModelo());
                tallaP = new String(aux.getTalla());
            }else{
                ProductoMarca aux = (ProductoMarca)producto;
                nombreP = new String(aux.getMarca()+" "+aux.getModelo());
                tallaP = new String("No aplicable");
            }
            
            JLabel etiquetaCodigo = new JLabel(codigoP);
            JLabel etiquetaFamilia = new JLabel(familiaP);
            JLabel etiquetaTalla = new JLabel(tallaP);
            JLabel etiquetaNombre = new JLabel(nombreP);
            JLabel etiquetaPrecio = new JLabel(precioP);
            JLabel etiquetaIva = new JLabel(ivaP);
            JLabel etiquetaPrecioIva = new JLabel(precioIvaP);
            JLabel etiquetaStock = new JLabel(stockP);
            
            panel.add(etiquetaCodigo);
            panel.add(etiquetaFamilia);
            panel.add(etiquetaTalla);
            panel.add(etiquetaNombre);
            panel.add(etiquetaPrecio);
            panel.add(etiquetaIva);
            panel.add(etiquetaPrecioIva);
            panel.add(etiquetaStock);
        }
    }
    
    /**
     * Organiza la informacion de los productos en el JPanel.
     * 
     * @param contendor  Un objeto ContendorProductos con los productos (debe contener los mismos productos que introdujeron en el JPanel)
     * @param JPanel El JPanel a ordenar, con la informacion ya introducida en él
     */
    public void cuadricular(ContenedorProductos contenedor, JPanel panel)
    {
        panel.setLayout(new SpringLayout());
        
        int filas = contenedor.getContenedor().size() + 1;
        
        UtilidadesSpring.hacerCuadricula(panel,
                   filas, COLUMNAS,     //filas, columnas
                   20, 20,              //espacio inicial en X, espacio inicial en Y
                   20, 20);             //separición de los componentes en X, separación de los componentes en Y
    }
    
    /**
     * Pregunta al usuario por que atributo desea buscar coincidencias en los productos y los muestra por pantalla
     * 
     * @param   lista   Contiene los clientes en los que se quiere buscar conincidencias
     * @param   panel   El JPanel donde se mostrarán los clientes
     */
    public void imprimirResultados(JFrame frame, ContenedorProductos contenedor, JPanel panel)
    {
        String eleccion = preguntarAtributo(frame);
        if(eleccion == null){
            //Se ha presionado cancelar, no hacer nada
        }else{
            ContenedorProductos encontrados = buscarPorAtributo(frame, contenedor, eleccion);
            if(encontrados == null || encontrados.estaVacio()){
                JOptionPane.showMessageDialog(frame, "No existen productos en el inventario que correspondan \n con los criterios de busqueda seleccionados",
                                                        "Busqueda fallida", JOptionPane.ERROR_MESSAGE);
            }else{
                imprimirCabecera(panel);
                imprimirProductos(encontrados,panel);
                cuadricular(encontrados,panel);
                panel.updateUI();
            }
        }
    }
}