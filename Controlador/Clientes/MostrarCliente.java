package Controlador.Clientes;

import Modelo.Clientes.Cliente;
import Modelo.Contenedores.ContenedorClientes;

import Controlador.Clientes.BuscarUnCliente;
import Controlador.UtilidadesSpring;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Está clase será la encargada de proporcionar toda la interfaz gráfica para que el usuario pueda buscar clientes en el sistema y ver sus
 * datos en pantalla. El usuarío podrá buscar los clientes por su código, por su nombre o podrá visualizar todos los clientes que existen
 * en el sistema.
 * 
 * Si se busca por código, se mostrará un único cliente puesto que los códigos son únicos. Si se busca por nombre se mostrarán todos los clientes con las posibles
 * coincidencias.
 * 
 * 
 * @author Yeray Rodríguez Martín 
 * @version 1.0
 */
public class MostrarCliente
{
    private final String OPCION_1 = "Buscar por código";
    private final String OPCION_2 = "Buscar por nombre";
    private final String OPCION_3 = "Mostrar todos los clientes";
    private final int COLUMNAS = 5;
    private Color color = Color.cyan.darker();
    
    /**
     * Permite seleccionar por que atributo se quiere buscar a los clientes.
     * Las opciones serán: Por código por nombre y ver todos los clientes
     * 
     * @return  String con la opción seleccionada
     */
    
    public String preguntarAtributo()
    {
         Object seleccion = JOptionPane.showInputDialog(
                                        null,
                                        "Seleccione una ópcion\n\n",
                                        "Buscar Clientes",
                                        JOptionPane.QUESTION_MESSAGE,
                                        null,  // null para icono defecto
                                        new String[] { OPCION_3, OPCION_1, OPCION_2 }, 
                                        OPCION_3);
         return (String)seleccion;
    }
    
    /**
     * Nos permite encontrar en un lista de clientes aquellos que concuerden con los datos introducidos por el usuario y el atributo seleccionado
     * 
     * @param frame el frame padre
     * @param   lista La lista donde estarán contenidos los clientes
     * @param   seleccion la opcion de busqueda elegida
     * 
     * @return  ContendorClientes   Todos los clientes que concuerden con los datos introducidos por el usuario
     */
    public ContenedorClientes buscarPorAtributo(JFrame frame, ContenedorClientes lista, String seleccion)
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
     * @param panel El panel donde se va a introducir esta cabecera
     */
    public void imprimirCabecera(JPanel panel)
    {
        JLabel codigo = new JLabel("Código");
        codigo.setForeground(color);
        
        JLabel nombre = new JLabel("Nombre-Razón Social");
        nombre.setForeground(color);
        
        JLabel nif = new JLabel("NIF");
        nif.setForeground(color);
        
        JLabel domicilio = new JLabel("Domicilio");
        domicilio.setForeground(color);
        
        JLabel alta = new JLabel("Fecha de alta");
        alta.setForeground(color);
                
        panel.add(codigo);
        panel.add(nombre);
        panel.add(nif);
        panel.add(domicilio);
        panel.add(alta);
    }
    
    /**
     * Muestra la información de los clientes en un JPanel
     * 
     * @param   lista  Los clientes que queremos mostrar
     * @param   panel  El JPanel donde se mostraran
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
     * @param lista  Una lista con clientes (debe ser la misma lista de clientes que se utilizo para rellenar el JPanel)
     * @param panel El JPanel a ordenar, con la informacion ya introducida en él
     */
    public void cuadricular(ContenedorClientes lista, JPanel panel)
    {
        panel.setLayout(new SpringLayout());
        
        int filas = lista.getListaClientes().size() + 1;
        
        UtilidadesSpring.hacerCuadricula(panel,
                   filas, COLUMNAS,
                   20, 20,  
                   20, 20);
    }
    
    /**
     * Hace uso de todos los métodos anteriores. Pregunta al usuario por que atributo desea buscar a los clientes y los muestra por pantalla
     * 
     * @param   lista   Contiene los clientes en los que se quiere buscar conincidencias
     * @param   panel   El JPanel donde se mostrarán los clientes
     */
    public void imprimirResultados(JFrame frame, ContenedorClientes listaClientes, JPanel panel)
    {
        String eleccion = preguntarAtributo();
        if(eleccion == null){
            //Se ha presionado cancelar, no hacer nada
        }else{
            ContenedorClientes encontrados = buscarPorAtributo(frame, listaClientes,eleccion);
            if(encontrados == null){
                    JOptionPane.showMessageDialog(null, "No existen clientes que correspondan con los criterios de busqueda seleccionados\n\n"+
                                                             "Intentelo de nuevo\n\n",
                                                             "Busqueda fallida", JOptionPane.ERROR_MESSAGE);
                    imprimirResultados(frame, listaClientes, panel);                                     
            }else if(!encontrados.estaVacia()){
                panel.removeAll();
                imprimirCabecera(panel);
                imprimirClientes(encontrados,panel);
                cuadricular(encontrados,panel);
                panel.updateUI();
            }else{
                JOptionPane.showMessageDialog(null, "Actualmente no existe ningun cliente dado de alta en el sistema\n",
                                                    "Busqueda fallida", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}