package Controlador.Ventas;
import Modelo.Clientes.Cliente;
import Modelo.Contenedores.*;
import Modelo.Productos.*;
import Controlador.UtilidadesSpring;

import java.awt.*;
import javax.swing.*;

/**
 * Está clase se encargará de mostrar los productos en la pantalla cuando se realizá un venta, cuando se visualiza un ticket o se visualiza una factura.
 * 
 * Mostrará una linea por cada producto donde se incluirá el codigo del producto, el nombre, el precio con iva, el iva aplicable, las unidades vendidas
 * y el precio total respecto a la cantidad de unidades vendidas
 * 
 * @author Yeray Rodiguez Martín
 * @version 1.0
 */
public class InformacionTicket
{
    private final Color COLOR = Color.red.darker();
    private final int COLUMNAS = 6;
    
    /**
     * Constructor. Crea una nueva instancia de InformacionTicket. La acción de está clase recaerá en el metodo "actualizarTicket(Jpanel,ContenedorProductos)"
     * el cual cada vez que sea ejecutado mostrará en el JPanel la información de cada producto contenido en ContendorProductos
     */
    public InformacionTicket()
    {
        //No es necesario hacer nada
    }
    
    /**
     * Esté método mostrará en un JPanel la información de los productos perfectamente ordenada
     * 
     * @param panel El panel donde se mostrará la inforamacion
     * @param contenedor Los productos que se van a mostrar
     */
    
    public void actualizarTicket(JPanel panel, ContenedorProductos contenedor)
    {
        panel.removeAll();
        
        JLabel codigo = new JLabel("Código");
        codigo.setForeground(COLOR);
        
        JLabel nombre = new JLabel("Nombre");
        nombre.setForeground(COLOR);
        
        JLabel precio = new JLabel("Precio Con Iva");
        precio.setForeground(COLOR);
        
        JLabel iva = new JLabel("IVA Aplicable");
        iva.setForeground(COLOR);
        
        JLabel vendidos = new JLabel("Unidades Vendidas");
        vendidos.setForeground(COLOR);
        
        JLabel total = new JLabel("Precio Total");
        total.setForeground(COLOR);
        
        panel.add(codigo);
        panel.add(nombre);
        panel.add(precio);
        panel.add(iva);
        panel.add(vendidos);
        panel.add(total);
        
        String nombreP;
        for(ProductoBase producto : contenedor.getContenedor()){
            String codigoP = new String(producto.getCodigo());
            String precioIvaP = new String(Double.toString(producto.getPrecioIva()));
            String ivaP = new String(Double.toString(producto.getIva()));
            String vendidosP = new String(Double.toString(producto.getVendido()));
            String precioTotalP = new String(String.format("%.2f",producto.getPrecioIva()*producto.getVendido()));
            
            if(producto instanceof ProductoGenerico){
                ProductoGenerico aux = (ProductoGenerico)producto;
                nombreP = new String(aux.getNombre());
            }else if(producto instanceof ProductoMarca){
                ProductoMarca aux = (ProductoMarca)producto;
                nombreP = new String(aux.getMarca()+" "+aux.getModelo());
            }else{
                ProductoTalla aux = (ProductoTalla)producto;
                nombreP = new String(aux.getMarca()+" "+aux.getModelo());
            }
            
            JLabel etiquetaCodigo = new JLabel(codigoP);
            JLabel etiquetaNombre = new JLabel(nombreP);
            JLabel etiquetaPrecioIva = new JLabel(precioIvaP);
            JLabel etiquetaIva = new JLabel(ivaP);
            JLabel etiquetaVendidos = new JLabel(vendidosP);
            JLabel etiquetaTotal = new JLabel(precioTotalP);
            
            panel.add(etiquetaCodigo);
            panel.add(etiquetaNombre);
            panel.add(etiquetaPrecioIva);
            panel.add(etiquetaIva);
            panel.add(etiquetaVendidos);
            panel.add(etiquetaTotal);
        }
        ordenar(panel, contenedor);
    }
    
    /**
     * Ordenará los componentes que esten introducidos en el Jpanel.
     * 
     * @param El JPanel a ordenar
     * @param contenedor El mismo contenedor que incluya los mismos productos que se hayan utilizado para rellenar el JPanel
     */
    public void ordenar(JPanel panel, ContenedorProductos contenedor)
    {
        panel.setLayout(new SpringLayout());
        int filas = contenedor.getContenedor().size() + 1;
        
        UtilidadesSpring.hacerCuadricula(panel,
        filas, COLUMNAS,  //filas, columnas
        20, 10,  //espacio Inicial en X, espacio inicial en Y
        20, 10); //espacio entre componentes en X, espacio entre componentes en Y 
        
        panel.updateUI();
    }
}
