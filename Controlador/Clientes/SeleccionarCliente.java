package Controlador.Clientes;
import Modelo.Contenedores.ContenedorClientes;
import Modelo.Clientes.Cliente;

import java.util.Enumeration;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
 * Esta clase construirá un JDialog modal donde el usuario podrá seleccionar 1 y solo 1 de los clientes dados de alta en el sistema. El método más importante
 * de esta clase es crearOpciones(ContendorClientes) el cual recibe un numero cualquiera de clientes y por cada uno crea un JRadioButton para poder selecionar
 * solo 1 de ellos. El lugar donde se usará esta clase será cuando se quiera seleccionar a un cliente para realizar una venta o factura, y busquemos a estos
 * por otro atributo que no sea el código del cliente. Esto nos permitirá seleccionar exactamente al cliente que queremos.
 * 
 * Una vez seleccionado el cliente deseado, su código se almacenará en la variable "private String codigoCliente", la cual tendriamos que consultar
 * despues de que se cierre el JDialog para saber que cliente se ha seleccioando.
 * 
 * @author Yeray Rodríguez Martín
 * @version 1.0
 */
public class SeleccionarCliente extends JDialog
{
    private JScrollPane scroll;
    private JPanel panelOpciones;
    private JPanel panelControl;
    private JButton aceptar;
    private JButton cancelar;
    private ButtonGroup grupo;
    /**
     * Almacenará el código del cliente seleccionado
     */
    private String codigoCliente;
    
    /**
     * Crea el JDialog completamente funcional con todo lo necesario para que le usuario pueda seleccionar un producto
     * 
     * @param frame el frame padre
     * @param contenedor los productos a seleccionar
     */
    public SeleccionarCliente(JFrame frame, ContenedorClientes lista)
    {
        super(frame, true);
        setTitle("Seleccione un cliente");
        setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
        grupo = new ButtonGroup();
        codigoCliente = null;
        crearOpciones(lista);
        control();
        scroll = new JScrollPane(panelOpciones);
        getContentPane().add(scroll, BorderLayout.CENTER);
        getContentPane().add(panelControl, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(frame);
        setVisible(true);
    }
    
    /**
     * Devulve la variable de instancia codigoCliente
     * 
     * @return  codigoCliente el código del cliente seleccionado, null si se ha cancelado la seleccion
     */
    public String getCodigoCliente()
    {
        return codigoCliente;
    }       
        
    /*
    public JButton getAceptar()
    {
        return aceptar;
    }
    
    public JButton getCancelar()
    {
        return cancelar;
    }
    */
    /**
     * Por cada cliente que haya en el objeto ContenedorClientes que pasamos como parametro creará un JRadioButton 
     * y lo introducira en la variable de inatancia panelOpciones
     * 
     * @param   lista   El objeto tipo ContenedorClientes donde estarán contenidos los clientes que se desean seleccionar
     */
    public void crearOpciones(ContenedorClientes lista)
    {
        panelOpciones = new JPanel();
        panelOpciones.setLayout(new BoxLayout(panelOpciones, BoxLayout.Y_AXIS));
        for(Cliente cliente : lista.getListaClientes()){
            String codigo = cliente.getCodigo();
            String nombre = cliente.getNombre();
            String nif = cliente.getNif();
            String domicilio = cliente.getDomicilio();
            JRadioButton radio = new JRadioButton("Código: "+codigo+" / Nombre-Razón Social: "+nombre+" / NIF: "+nif+" / Domicilio: "+domicilio, true);
            grupo.add(radio);
            radio.setAlignmentX(Component.LEFT_ALIGNMENT);
            panelOpciones.add(radio);
        }
    }
    
    /**
     * Comprueba que botón ha selecionado el usuario y devuelve el codigo del cliente asociado
     * 
     * @return  String el codig del producto que se ha seleccionado
     */
    public String comprobarEleccion()
    {
        String codigo = "";
        for (Enumeration<AbstractButton> botonesRadio = grupo.getElements(); botonesRadio.hasMoreElements();) {
            AbstractButton botonRadio = botonesRadio.nextElement();

            if (botonRadio.isSelected()) {
                String informacion = botonRadio.getText();
                codigo = informacion.substring(8,informacion.indexOf(" ",9));
            }
        }
        return codigo;  //Nunca sera una cadena vacía puesto que obligatoriamente habrá un JRadioBatton siempre seleccionado
    }
    
    /**
     * Crear dos botones y los introduce un un JPanel con FlowLayout. Se usará para validar la opcione escogida
     * por el usuario
     */
    public void control()
    {
        
        panelControl = new JPanel();
        panelControl.setLayout(new FlowLayout());
              
        aceptar = new JButton("Aceptar");
        aceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                codigoCliente = new String(comprobarEleccion());
                dispose();
            }
        });
        panelControl.add(aceptar);
               
        cancelar = new JButton("Cancelar");
        cancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                dispose();
            }
        });
        panelControl.add(cancelar);        
    }
}
