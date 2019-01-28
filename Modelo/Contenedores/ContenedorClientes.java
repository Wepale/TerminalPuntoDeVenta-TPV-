package Modelo.Contenedores;

import java.io.Serializable;
import Modelo.Clientes.Cliente;
import java.util.ArrayList;

/**
 * Esta clase será la encargada de almacenar a los clientes.
 * 
 * Contendrá las herramientas necesarias para poder dar de alta/baja clientes y buscar clientes de distintas maneras
 * 
 * @author Yeray Rodríguez 
 * @version 1.0
 */
public class ContenedorClientes implements Serializable
{
    /**
     * Este ArrayList sera el lugar en el que se almacenarán los clientes
     */
    private ArrayList<Cliente> listaClientes;
    /**
     * Crea un contendor de clientes vacio
     */
    public ContenedorClientes()
    {
        listaClientes= new ArrayList<Cliente>();
    }
    
    /**
     * Crea un nuevo contendor de clientes copiando los clientes de otro contendor
     * 
     * @param otraListaClientes El ContenedorClientes al que se desea realizar una copia
     */
    public ContenedorClientes(ContenedorClientes otraListaClientes)
    {
        listaClientes= new ArrayList<Cliente>();
        for(Cliente cliente : otraListaClientes.listaClientes){
            listaClientes.add(new Cliente(cliente));
        }
    }
    
    /**
     * Devuelve el ArrayList en la que se almacenan los clientes
     * 
     * @return  Los clientes dados de alta en el sistema
     */
    public ArrayList<Cliente> getListaClientes()
    {
        return listaClientes;
    }
    
    /**
     * Comprueba si la lista de clientes esta vacia
     * 
     * @return true si la lista no contiene ningun cliente, false en caso contrario
     */
    public boolean estaVacia()
    {
        return listaClientes.isEmpty();
    }
    
    /**
     * Introduce un nuevo cliente en el ArrayList
     * 
     * @param   Cliente   El cliente a introducir
     */
    public void insertarCliente(Cliente cliente)
    {
        listaClientes.add(cliente);
    }
    
    /**
     * Método para eliminar un cliente del ArrayList
     * 
     * @param Cliente  El cliente a eliminar
     */
    public void eliminarCliente(Cliente cliente)
    {
        listaClientes.remove(cliente);
    }
    
    /**
     * Nos permite buscar un cliente por su codigo
     * 
     * @param   String  El código de cliente que queremos buscar
     * @return  Cliente El cliente asociado a dicho código, null si no existen coincidencias
     */
    public Cliente buscarPorCodigo(String codigo)
    {
        String codigoMod = new String(AyudaBusqueda.modCadena(codigo));
        for(Cliente cliente : listaClientes){
            if(cliente.getCodigo().equals(codigoMod)){
                return cliente;
            }
        }
        return null;
    }
    
    /**
     * Nos permite saber si existe un cliente mediante su codigo
     * 
     * @param   String  El código de cliente que queremos buscar
     * @return  boolean true si existe el cliente, false en caso contrario
     */
    public boolean existeCliente(String codigo)
    {
        String codigoMod = new String(AyudaBusqueda.modCadena(codigo));
        for(Cliente cliente : listaClientes){
            if(cliente.getCodigo().equals(codigoMod))
                return true;
        }
        return false;
    }
    
    /**
     * Nos permite saber si existe un cliente mediante su nombre o razón social
     * 
     * @param   String  El nombre o razón social que queremos buscar
     * @return  boolean true si existe algun cliente con dicho nombre, false en caso contrario
     */
    public boolean existeNombre(String nombre)
    {
        ContenedorClientes lista = buscarPorNombre(nombre);
        if(lista == null){
            return false;
        }else{
            return true;
        }
    }
    
    /**
     * Nos permite buscar un cliente por su nif
     * 
     * @param   nif  El nif de cliente que queremos buscar
     * @return  Cliente El cliente asociado a dicho nif, null si no existe
     */
    public Cliente buscarPorNif(String nif)
    {
        String nifMod = new String(AyudaBusqueda.modCadena(nif));
        for(Cliente cliente : listaClientes){
            if(cliente.getNif().equals(nifMod)){
                return cliente;
            }
        }
        return null;
    }
    
    /**
     * Nos permite buscar un cliente por su nombre o razón social
     * 
     * @param   nombre  El nombre/razón social que queremos buscar
     * @return  Cliente El cliente asociado a dicho nombre/razón social, null si no existe
     */
    public ContenedorClientes buscarPorNombre(String nombre)
    {
        ContenedorClientes lista = new ContenedorClientes();
        String[] encontrar = AyudaBusqueda.separar(nombre);     //Separá cada palabra del parametro nombre,las pasa a minusculas y mete cada una en un array
        for(Cliente cliente : listaClientes){
            String nombreModificado = AyudaBusqueda.modCadena(cliente.getNombre()); //Se extrae el nombre del cliente y se pasa a minuscula
            if(AyudaBusqueda.coincideTodo(nombreModificado, encontrar)){
                lista.insertarCliente(cliente);
            }
        }
        if(lista.estaVacia()){
            return null;
        }else{
            return lista;
        }
    }
}
