package Modelo.Contenedores;

import java.io.Serializable;
import Modelo.Ventas.Factura;
import java.util.ArrayList;

/**
 * Esta clase será el lugar en el que se almacenen las facturas creadas. Dispondrá de las herramientas para
 * buscar fucturas especificas comparando distintos campos.
 * 
 * @author Yeray Rodríguez Martín
 * @version 1.0
 */
public class ContenedorFacturas implements Serializable
{
    /**
     * Este ArrayList contendrá nuestras facturas
     */
    ArrayList<Factura> archivador;
    
    /**
     * Crea un archivador vacio en el que almacenar las facturas
     */
    public ContenedorFacturas()
    {
        archivador = new ArrayList<Factura>();
    }
    
    /**
     * Crea un nuevo ContendorFacturas copiando las facturas que contiene otro ContendorFacturas
     * 
     * @param otroArchivador El objeto ContenedorFacturas que se desea copiar
     */
    public ContenedorFacturas(ContenedorFacturas otroArchivador)
    {
        archivador = new ArrayList<Factura>();
        for(Factura factura : otroArchivador.archivador){
            archivador.add(new Factura(factura));
        }
    }
    
    /**
     * Método para acceder al archivador
     * 
     * @return      El campo archivador(El ArrayList)
     */
    public ArrayList<Factura> getArchivador()
    {
        return archivador;
    }
    
    /**
     * Método para saber si el archivador de facturas está vacio
     * 
     * @return true si esta vacio, false en caso contrario
     */
    public boolean estaVacio()
    {
       return archivador.isEmpty();
    }
    
    /**
     * Método para añadir una factura al archivador
     * 
     * @param   Factura   La factura a añadir
     */
    public void insertarFactura(Factura factura)
    {
        archivador.add(factura);
    }
    
    /**
     * Método para buscar una factura por su codigo
     * 
     * @param   String codigo   El código que buscaremos
     * 
     * @return la factura con dicho codigo, null si no existe
     */
    public Factura buscarPorCodigo(String codigo)
    {
        String cod= AyudaBusqueda.modCadena(codigo);
        for(Factura factura : archivador){
            if(factura.getFecha(Factura.CODIGO).equals(cod)){
                return factura;
            }
        }
        return null;
    }
    
    /**
     * Método para saber si existe una factura mediante su codigo
     * 
     * @param codigoFactura el código de la factura que se quiere buscar
     * 
     * @return true si en el archivador existe un ticket con dicho codigo, false en caso contrario
     */
    public boolean existeFactura(String codigoFactura)
    {
        for(Factura factura : archivador){
            String codigo2=factura.getFecha(Factura.CODIGO);
            if(codigoFactura.equals(codigo2)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Metodo para saber si existe una factura asociada a un Cliente
     * 
     * @param codigoCliente El código del cliente
     * 
     * @return true si existe una factura asociada a dicho cliente, false en caso contrario
     */
    public boolean existeFacturaCliente(String codigoCliente)
    {
        for(Factura factura : archivador){
            String codigo2=factura.getCliente().getCodigo();
            if(codigoCliente.equals(codigo2)){
                return true;
            }
        }
        return false;    //No hay ninguna factura de dicho cliente
    }
    
    /**
     * Método que devuelve todas las facturas cuyo nombre/razón social del cliente coincida con el introducido como parametro
     * 
     * @param nombre El nombre/razon social que se desea buscar
     * 
     * @return ContenedorFacturas con las facturas que coincidan, null si no existe ninguna factura
     */
    public ContenedorFacturas buscarPorNombre(String nombre)
    {
        ContenedorFacturas resultados = new ContenedorFacturas();
        String[] encontrar = AyudaBusqueda.separar(nombre);
        for(Factura factura : archivador){
            String nombreCliente = AyudaBusqueda.modCadena(factura.getCliente().getNombre());
            if(AyudaBusqueda.coincideTodo(nombreCliente, encontrar)){
                resultados.insertarFactura(factura);
            }
        }
        if(resultados.estaVacio()){
            return null;
        }else{
            return resultados;
        }
    }
    
    /**
     * Método que devulve todas las facturas de un cliente introduciendo el código del cliente
     * 
     * @param codigo el codigo del cliente
     * 
     * @return ContendorFacturas con todas las facturas de dicho cliente, null si no exite ninguna factura
     */
    public ContenedorFacturas buscarPorCodigoCliente(String codigo)
    {
        ContenedorFacturas resultados = new ContenedorFacturas();
        for(Factura factura : archivador){
            String codigoCliente = factura.getCliente().getCodigo();
            if(codigoCliente.equals(codigo)){
                resultados.insertarFactura(factura);
            }
        }
        if(resultados.estaVacio()){
            return null;
        }else{
            return resultados;
        }
    }
}