package Controlador.Clientes;

import Modelo.Clientes.Cliente;
import Modelo.Contenedores.ContenedorClientes;
import Vista.SpringUtilities;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Write a description of class BusquedaCliente here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BusquedaCliente
{
    private final String OPCION_1 = "Buscar por código";
    private final String OPCION_2 = "Buscar por nombre";
    private final String OPCION_3 = "Mostrar todos los clientes";
    private final int COLUMNAS = 5;
    
    private ContenedorClientes listaClientes;

    /**
     * Constructor
     */
    public BusquedaCliente()
    {
        //No hacer nada
    }
    
    /*
     * Los métodos a continuación implementados se utilizarán para realizar busquedas de clientes en el 
     */
    
    /**
     * Permite seleccionar por que atributo se quiere buscar a los clientes
     * 
     * @return  String con la opción seleccionada
     */
    
    public String preguntarAtributo()
    {
         Object seleccion = JOptionPane.showInputDialog(
                                        null,
                                        "¿Que atributo desea usar para buscar los clientes?\n\n",
                                        "Buscar Clientes",
                                        JOptionPane.QUESTION_MESSAGE,
                                        null,  // null para icono defecto
                                        new String[] { OPCION_3, OPCION_1, OPCION_2,}, 
                                        OPCION_3);
         return (String)seleccion;
    }
    
    /**
     * Nos permite encontrar en un lista de clientes aquellos que concuerden con los datos introducidos por el usuario y el atributo seleccionado
     * 
     * @param   ContendorClientes La lista donde estarán contenidos los clientes
     * @param   String El atributo en el que queremos buscar coincidencias
     * 
     * @return  ContendorClientes   Todos los clientes que concuerden con los datos introducidos por el usuario
     */
    public ContenedorClientes buscarPorAtributo(ContenedorClientes lista, String seleccion)
    {
        ContenedorClientes resultados = new ContenedorClientes();
        if(seleccion.equals(OPCION_1)){
            String eleccion = JOptionPane.showInputDialog(null, "Introduzca el código del cliente\n\n", JOptionPane.QUESTION_MESSAGE);
            if(lista.existeCliente(eleccion)){
                resultados.insertarCliente(lista.buscarPorCodigo(eleccion));
                return resultados;
            }else{
                return null;  
            }
        }else if(seleccion.equals(OPCION_2)){
            String eleccion = JOptionPane.showInputDialog(null, "Introduzca el Nombre/Razón Social del cliente\n\n", JOptionPane.QUESTION_MESSAGE);
            return lista.buscarPorNombre(eleccion);
        }else{
            return lista;
        }
    }
      
    /**
     * La cabecera para organizar los datos de los clientes
     *
     * @param Panel El panel donde se va a introducir esta cabecera
     */
    public void imprimirCabecera(JPanel panel)
    {
        JLabel codigo = new JLabel("Código");
        
        JLabel nombre = new JLabel("Nombre-Razón Social");
        
        JLabel nif = new JLabel("NIF");
        
        JLabel domicilio = new JLabel("Domicilio");
        
        JLabel alta = new JLabel("Fecha de alta");
                
        panel.add(codigo);
        panel.add(nombre);
        panel.add(nif);
        panel.add(domicilio);
        panel.add(alta);
    }
    
    /**
     * Muestra la información de los clientes en un JPanel
     * 
     * @param   ContenedorClientes  Los clientes que queremos mostrar
     * @param   JPanel  El JPanel donde se mostraran
     */
    public void imprimirClientes(ContenedorClientes lista, JPanel panel)
    {
        for(Cliente cliente : lista.getListaClientes()){
            String codigoP = new String(cliente.getCodigo());
            String nombreP = new String(cliente.getNombre());
            String nifP = new String(cliente.getNif());
            String domicilioP = new String(cliente.getDomicilio());
            String altaP = new String(cliente.getCadenaAlta());
            
            JLabel etiquetaCodigo = new JLabel(codigoP);
            JLabel etiquetaNombre = new JLabel(nombreP);
            JLabel etiquetaNif = new JLabel(nifP);
            JLabel etiquetaDomicilio = new JLabel(domicilioP);
            JLabel etiquetaAlta = new JLabel(altaP);
        
            panel.add(etiquetaCodigo);
            panel.add(etiquetaNombre);
            panel.add(etiquetaNif);
            panel.add(etiquetaDomicilio);
            panel.add(etiquetaAlta);
        }
    }
    
    /**
     * Organiza la informacion de los clientes en el JPanel
     * 
     * @param ContenedorClientes  Una lista con clientes (debe ser la misma lista de clientes que se utilizo para rellenar el JPanel)
     * @param JPanel El JPanel a ordenar, con la informacion ya introducida en él
     */
    public void cuadricular(ContenedorClientes lista, JPanel panel)
    {
        panel.setLayout(new SpringLayout());
        
        int filas = lista.getListaClientes().size() + 1;
        
        SpringUtilities.makeCompactGrid(panel,
                   filas, COLUMNAS,  //rows, cols
                   20, 20,  //initX, initY
                   20, 20); //xPad, yPad
    }
    
    /**
     * Pregunta al usuario por que atributo desea buscar a los clientes y los muestra por pantalla
     * 
     * @param   lista   Contiene los clientes en los que se quiere buscar conincidencias
     * @param   panel   El JPanel donde se mostrarán los clientes
     */
    public void imprimirResultados(ContenedorClientes listaClientes, JPanel panel)
    {
        String eleccion = preguntarAtributo();
        if(eleccion == null){
            //Se ha presionado cancelar, no hacer nada
        }else{
            ContenedorClientes encontrados = buscarPorAtributo(listaClientes,eleccion);
            if(encontrados == null || encontrados.estaVacia()){
                        JOptionPane.showMessageDialog(null, "No existen clientes que correspondan con los criterios de busqueda seleccionados\n\n"+
                                                             "Intentelo de nuevo\n\n",
                                                             "Busqueda fallida", JOptionPane.ERROR_MESSAGE);
                        imprimirResultados(listaClientes, panel);                                     
            }else{
                panel.removeAll();
                imprimirCabecera(panel);
                imprimirClientes(encontrados,panel);
                cuadricular(encontrados,panel);
                panel.updateUI();
            }
        }
    }
    
    /*
     * Los métodos a continuacion implementados se utilizarán para asociar un cliente a una venta realizada
     */
}
