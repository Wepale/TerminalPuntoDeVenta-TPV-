package Controlador.Clientes;

import Modelo.Clientes.Cliente;
import Modelo.Contenedores.ContenedorClientes;

import Controlador.Clientes.SeleccionarCliente;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Esta clase creará toda la interfaz gráfica necesaria para que el usuario pueda seleccionar a un y solo un cliente entre todos los que estan dados
 * de alta en el sistema. Se utilizará cuando se quiera seleccionar un cliente para realizar una venta. El cliente seleccionado se guardará en el campo
 * "private CLiente cliente". Dará varias opciones al usuario sobre como desea buscar al cliente que quiere seleccionar.En el caso de que el usuario
 * cancele la elección del cliente por cualquier motivo, el campo "cliente" tendrá valor null
 * 
 * @author Yeray Rodríguez Martín 
 * @version 1.0
 */
public class BuscarUnCliente
{
    private final String OPCION_1 = "Buscar por Nombre";
    private final String OPCION_2 = "Buscar por Código";
    private final String N_OCASIONAL = "Cliente ocasional";
    private final String C_OCASIONAL = "00000";
    private final String OPCION_7 = "Mostrar todos los clientes";
    
    /**
     * JDialog con todo lo necesario para elegir a un cliente entre varios
     */
    private SeleccionarCliente elegirCliente;
    /**
     * La variable que contendrá el cliente seleccionado
     */
    private Cliente cliente;
    /**
     * Este contructor solo unicializa el cliente a valor null. El peso se esta clase recaerá sobre el método preguntarPorCliente(JFrame,ContenedorClientes)
     */
    public BuscarUnCliente()
    {
        cliente = null;
    }
    
    /**
     * Devuelve al cliente seleccionado
     * 
     * @return cliente el cliente seleccionado null si se ha cancelado la seleccion del cliente por cualquir motivo.
     */
    public Cliente getCliente()
    {
        return cliente;
    }
    
    /**
     * Este método preguntará al usuario por que atributo quiere buscar al cliente al que se le realizará la venta. Las opciones serán por nombre o código.
     * El sistema támbien permitirá la opcion de realizar la venta a un cliente ocasional, el cual no existe en nuestro sistema
     * 
     * @param el frame padre
     * 
     * @return  la opcion seleccionada
     */
    public String preguntarAtributoCliente(JFrame frame)
    {
        Object seleccion = JOptionPane.showInputDialog(
                                        frame,
                                        "Por seleccione, seleccione como desea seleccionar al cliente.\n\n",
                                        "Seleccione cliente",
                                        JOptionPane.QUESTION_MESSAGE,
                                        null,  // null para icono defecto
                                        new String[] { OPCION_1, OPCION_2, OPCION_7, N_OCASIONAL }, 
                                        OPCION_1);
        return (String)seleccion;
    }
    
    /**
     * Este método pregunta al usuario el código del cliente al que se le quiere realizar la venta
     * 
     * @param frame el frame padre
     * @param listaClientes Los clientes que existen en el sistema
     */
    
    public void preguntarCodigo(JFrame frame, ContenedorClientes listaClientes)
    {
        String seleccion = JOptionPane.showInputDialog(frame, "Introduzca el código del cliente\n\n", JOptionPane.QUESTION_MESSAGE);
        if(seleccion == null){
            //Se ha seleccionado cancelar, volver a preguntar como se desea buscar al cliente
            preguntarPorCliente(frame, listaClientes);
        }else if(listaClientes.existeCliente(seleccion)){
            cliente = listaClientes.buscarPorCodigo(seleccion);
        }else{
            JOptionPane.showMessageDialog(frame, "El código introducido no corresponde con ningún cliente.\n\nIntentelo de nuevo\n\n", "Cliente no encontrado", JOptionPane.ERROR_MESSAGE);
            preguntarCodigo(frame, listaClientes);
        }
    }
    
    /**
     * Este método pregunta al usuario el nombre del cliente al que se le desea realizar la venta. Si existen varias coincidencias se le pedirá
     * que seleccione el cliente adecuado
     * 
     * @param frame el frame padre
     * @param listaClientes    Los clientes que existen en el sistema
     */
    public void preguntarNombre(JFrame frame, ContenedorClientes listaClientes)
    {
        String seleccion = JOptionPane.showInputDialog(frame, "Introduzca el Nombre/Razon Social del cliente\n\n", JOptionPane.QUESTION_MESSAGE);
        if(seleccion == null){
            //se ha presionado cancelar, no hacer nada
            preguntarPorCliente(frame, listaClientes);
            //realizarVenta(frame, inventario, listaClientes, archivador, panel);
        }else if(listaClientes.existeNombre(seleccion)){
            ContenedorClientes resultados = listaClientes.buscarPorNombre(seleccion);
            verClientes(frame, listaClientes, resultados);
        }else{
            JOptionPane.showMessageDialog(frame, "No se han encontrado coincidencias con el Nombre/Razon Social introducido.\n\nIntentelo de nuevo\n\n", "Cliente no encontrado", JOptionPane.ERROR_MESSAGE);
            preguntarNombre(frame, listaClientes);
        }
    }
    
    /**
     * Esté metodo abre un JDialog para que el usuario seleccione uno de los clientes que estén contenidos en el objeto otraListaClientes que se le pasa
     * como parametro.
     * 
     * El cliente seleccionado se guardará en la variable de instancia "cliente".
     * 
     * @param frame el frame padre
     * @param listaClientes todos los clientes dados de alta en el sistema
     * @param otraListaClientes Los clientes que apareceran en el JDialog
     */
    public void verClientes(JFrame frame, ContenedorClientes listaClientes, ContenedorClientes otraListaClientes)
    {
        elegirCliente = new SeleccionarCliente(frame, otraListaClientes); //JDialog
        String codigo = elegirCliente.getCodigoCliente();
        if (codigo == null){
            //Se ha presionado cancelar o se ha cerrado el JDialog, volver a preguntar como se desea buscar al cliente
            preguntarPorCliente(frame, listaClientes);
        }else{
            cliente = otraListaClientes.buscarPorCodigo(codigo);
        }
    }
    
    /**
     * Este método hace todo lo necesario para que el usuario pueda seleccionar al cliente al que se le realizará la venta. Utilizará todos los métodos
     * anteriores segun sea necesario. Es el método que une todos los métodos implementados en esta clase y realiza todo el trabajo, es aquel al que
     * deveremos invocar cuando queramos seleccionar un único ciente.
     * 
     * @param frame el frame padre
     * @param listaClientes la lista donde se encuentran todos los clientes
     */
    
    public void preguntarPorCliente(JFrame frame, ContenedorClientes listaClientes)
    {
        String seleccion = preguntarAtributoCliente(frame);
        if(seleccion == null){
            //Se ha presionado cancelar, no hacer nada
            JOptionPane.showMessageDialog(frame,"No se ha seleccionado ningun cliente.\n\n", "Operación Cancelada", JOptionPane.ERROR_MESSAGE);
        }else if(seleccion.equals(OPCION_1)){
            preguntarNombre(frame, listaClientes);
        }else if(seleccion.equals(OPCION_2)){
            preguntarCodigo(frame, listaClientes);
        }else if(seleccion.equals(OPCION_7)){
            verClientes(frame, listaClientes, listaClientes);
        }else{
            cliente = listaClientes.buscarPorCodigo(C_OCASIONAL);
        }
    }
}