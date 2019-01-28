package Controlador.Clientes;
import Modelo.Contenedores.ContenedorClientes;
import Modelo.Contenedores.ContenedorTickets;
import Modelo.Clientes.Cliente;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Esta clase extenderá de JDialog y creará toda la interfaz gráfica para pedir al usuario los datos del cliente que se desea crear o modificar
 * 
 * @author Yeray Rodríguez 
 * @version 1.0
 */
public class VentanaCliente extends JDialog
{
    /**
     * El JPanel que contiene los JTextField
     */
    private NuevoCliente panelFormulario;   //El JPanel que contiene los JTextField
    /**
     * El JPanel donde se añadirán los botones de aceptar y cancelar
     */
    private JPanel panelControl;            
    /**
     * El botón "Aceptar"
     */
    private JButton botonAceptar;
    /**
     * El botón "Cancelar"
     */
    private JButton botonCancelar;
   
    /**
     * Constructor del JDialog. Crerá una ventana completamente funcional donde el usuario introducirá la información del cliente
     * 
     * @param frame El JFrame padre
     * @param listaClientes Los clientes dados de alta en el sistema
     * @param archivador Los tickets de las ventas realizadas
     * @param cliente El cliente que se quiere modificar, null se se quiere crear un nuevo cliente
     */
    public VentanaCliente(Frame frame, ContenedorClientes listaClientes, ContenedorTickets archivador, Cliente cliente)
    {
        super(frame, true);
        setTitle("Rellene el formulario");
        getContentPane().setLayout(new BorderLayout());
        panelFormulario = new NuevoCliente(cliente);
        control(frame, listaClientes, archivador, cliente);
        getContentPane().add(panelFormulario, BorderLayout.CENTER);
        getContentPane().add(panelControl, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(frame);
        setVisible(true);
    }
    
    /**
     * Añade al panelControl los botones Aceptar y Cancelar y define las acciones a realizar si se presionan.
     * 
     * Si se presiona el boton Aceptar se recogerán los datos introducidos por el usuario.
     * Si se presiones el boton Cancelar se cerrará el JDialog
     * 
     * @param frame el frame padre
     * @param listaClientes Los clientes dados de alta en el sistema
     * @param archivador Los tickets de las ventas realizadas
     * @param cliente El cliente que se quiere modificar, null se se quiere crear un nuevo cliente
     */
    
    public void control(Frame frame, ContenedorClientes listaClientes, ContenedorTickets archivador, Cliente cliente)
    {
        panelControl = new JPanel(new FlowLayout());
        
        botonAceptar = new JButton("Aceptar");
        botonAceptar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                int datosCorrectos = panelFormulario.obtenerDatos(listaClientes, archivador, cliente);
                if(datosCorrectos == 0){
                    JOptionPane.showMessageDialog(frame, "El código introducido ya pertenece a otro cliente, introduzca otro código", "Error", JOptionPane.ERROR_MESSAGE);
                }else if(datosCorrectos == 1){
                    JOptionPane.showMessageDialog(frame, "Es obligatorio introducir un código, intentelo de nuevo", "Error", JOptionPane.ERROR_MESSAGE);
                }else if(datosCorrectos == 2){
                    JOptionPane.showMessageDialog(frame, "Es obligatorio introducir el Nombre/Razon Social, intentelo de nuevo", "Error", JOptionPane.ERROR_MESSAGE);
                }else if(datosCorrectos == 3){
                    JOptionPane.showMessageDialog(frame, "Es obligatorio introducir el NIF, intentelo de nuevo", "Error", JOptionPane.ERROR_MESSAGE);
                }else{
                    panelFormulario.crearCliente(listaClientes,cliente);
                    dispose();
                    if(cliente == null){
                        JOptionPane.showMessageDialog(frame,"El cliente se ha creado correctamente");
                    }else{
                        JOptionPane.showMessageDialog(frame,"Los datos del cliente se ha modificado correctamente");
                    }
                }
            }
        });
        
        botonCancelar = new JButton("Cancelar");
        botonCancelar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        panelControl.add(botonAceptar);
        panelControl.add(botonCancelar);
    }
}