package Controlador.Clientes;
import Controlador.UtilidadesSpring;
import Modelo.Contenedores.ContenedorClientes;
import Modelo.Contenedores.ContenedorTickets;
import Modelo.Clientes.Cliente;

import java.awt.*;
import javax.swing.*;

/**
 * Está clase será extenderá de JPanel y en ella se introduciran varios JTextField, tantos como campos tenga la clase Cliente, para pedir los datos al usuario
 * del cliente que se desea crear o modificar.
 * 
 * @author Yeray Rodríguez Martín 
 * @version 1.0
 */
public class NuevoCliente extends JPanel
{
    private JTextField codigoTexto;
    private JTextField nombreTexto;
    private JTextField nifTexto;
    private JTextField domicilioTexto;
    
    private String codigo;
    private String nombre;
    private String nif;
    private String domicilio;
    
    /**
     * Constructor. Toda la labor la realizará el metodo construirPanel(Cliente cliente)
     */
    public NuevoCliente(Cliente cliente)
    {
        construirPanel(cliente);
    }
    
    /**
     * Esté método añadirá los JTextFiel para que el usuario pueda introducir los datos del cliente. Cada JTextField estará asociado a su JLabel
     * correspondiente para estar correctamente diferenciado.
     * 
     * @param cliente   El cliente que se quiere modificar los datos, null si se quiere crear un nuevo cliente
     */
    public void construirPanel(Cliente cliente)
    {
        JLabel codigo = new JLabel("Código:");
        codigoTexto = new JTextField(20);
        codigo.setLabelFor(codigoTexto);
 
        JLabel nombre = new JLabel("Nombre/Razón Social:");
        nombreTexto = new JTextField(20);
        nombre.setLabelFor(nombreTexto);
        
        JLabel nif = new JLabel("NIF");
        nifTexto = new JTextField(20);
        nif.setLabelFor(nifTexto);
        
        JLabel domicilio = new JLabel("Domicilio:");
        domicilioTexto = new JTextField(20);
        domicilio.setLabelFor(domicilioTexto);

        if(cliente != null){
            codigoTexto.setText(cliente.getCodigo());
            nombreTexto.setText(cliente.getNombre());
            nifTexto.setText(cliente.getNif());
            domicilioTexto.setText(cliente.getDomicilio());
        }
        
        add(codigo);
        add(codigoTexto);
        add(nombre);
        add(nombreTexto);
        add(nif);
        add(nifTexto);
        add(domicilio);
        add(domicilioTexto);
        
        setLayout(new SpringLayout());
        
        UtilidadesSpring.hacerCuadricula(this,
                4, 2,  //filas, columnas
                10, 10,  //espacio inical X, espacio inicial Y
                10, 10); //espacio compontentes X, espacio componentes Y
    }
    
    /**
     * Este método obtendrá los datos que el usuario haya introducido en los JTexfield.
     * 
     * Para que un código sea valido tendrá que cumplir las siguietnes condiciones :
     * 
     *              1º No ser una cadena vacia
     *              2º Que el código no esté ya en uso por otro cliente dado de alta en el sistema
     *              3º Que el código no esté en uso por otro cliente al que se la haya realizado una venta
     *                 pero ya no este dado de alta en el sistema
     * 
     * @param listaClientes La lista de los clientes dados de alta en el sistema
     * @param archivador    Todos los tickets con las ventas realizadas
     * @param cliente       El cliente que queremos modificar, null en el caso de que se quiera crear un cliente nuevo
     * 
     * @return  0 si el código introducido ya esta en uso por otro cliente, 1 si el código está vacio, 2 si el nombre esta vacio,
     *          3 si el NIF está vacio, 4 si todos los datos introducidos son validos
     */
    
    public int obtenerDatos(ContenedorClientes listado, ContenedorTickets archivador, Cliente cliente)
    {
        codigo = codigoTexto.getText();
        if(cliente != null){    
            if(listado.existeCliente(codigo) && archivador.existeTicket(codigo) && !cliente.getCodigo().equals(codigo)){
                return 0;   //El código ya está en uso por otro cliente
            }else if(codigo.equals("")){
                return 1;   //El código está vacio
            }
        }else{
            if(listado.existeCliente(codigo) && archivador.existeTicket(codigo)){
                return 0;
            }else if(codigo.equals("")){
                return 1;
            }
        }
        nombre = nombreTexto.getText();
        if(nombre.equals("")){
            return 2;
        }
        nif = nifTexto.getText();
        if(nif.equals("")){
            return 3;
        }
        domicilio = domicilioTexto.getText();
        return 4;
        }
    
    /**
     * Esté método recogerá todos los datos introducidos en los JTextField y creará/modificará al cliente con dichos datos.
     * 
     * ATENCION: Este método no comprueba que los datos introducidos son validos, por ello hay que hacer uso del metodo obtenerDatos(...,...)
     * y solo invocar a este método cuando nos aseguremos que los datos no tienen nigún error y son completamente válidos
     * 
     * @param listado El lugar donde se almacenan todos los clientes
     * @param producto El cliente que queremos modificar, null en el caso de que se quiera crear un cliente nuevo
     */
    public void crearCliente(ContenedorClientes listado, Cliente cliente)
    {
        if(cliente != null){
            cliente.setCodigo(codigo);
            cliente.setNombre(nombre);
            cliente.setNif(nif);
            cliente.setDomicilio(domicilio);
        }else{
            Cliente clienteNuevo = new Cliente(codigo, nombre, nif, domicilio);
            listado.insertarCliente(clienteNuevo);
        }
    }
}
