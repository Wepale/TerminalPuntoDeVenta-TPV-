package Controlador.Productos;
import Modelo.Contenedores.ContenedorProductos;
import Controlador.Productos.FormulariosProducto.*;
import Modelo.Productos.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Esta clase creará un JDialog para que el usuario pueda introducir los datos de un prodcuto que desea crear o modificar
 * 
 * @author Yeray Rodríguez Martín 
 * @version 1.0
 */
public class VentanaProducto extends JDialog
{
    private static final String CREAR_1 = "Producto Generico";
    private static final String CREAR_2 = "Producto con marca y modelo";
    private static final String CREAR_3 = "Producto con marca, modelo y talla";
    
    private NuevoProductoBase panelFormulario;
    private JPanel panelControl;
    private JButton botonAceptar;
    private JButton botonCancelar;
    private boolean crearVentana;
    
    /**
     * Constructor de está clase. Utilizará todos los métodos privados de esta clase para crear un JDialog donde usuario pueda introducir o modificar los datos de
     * un produto.
     * 
     * @param frame el frame padre
     * @param inventario El luegar en el que se almacenan los productos
     * @param producto Si es null, indica que se está creando un producto nuevo. Si es un objeto de la clase ProductoBase o sus clases hijas, se modificará
     *                      dicho producto.
     */
    public VentanaProducto(JFrame frame, ContenedorProductos inventario, ProductoBase producto)
    {
        super(frame, true);
        crearVentana = true;
        formulario(frame, producto);       //Esté método cambiaría el valor de crearVentana
        if(crearVentana){
            control(frame, inventario, producto);
            setTitle("Rellene el formulario");
            getContentPane().setLayout(new BorderLayout());
            getContentPane().add(panelFormulario, BorderLayout.CENTER);
            getContentPane().add(panelControl, BorderLayout.SOUTH);
            pack();
            setLocationRelativeTo(frame);
            setVisible(true);
        }
    }
    
    public VentanaProducto()
    {
        //No hacer nada
    }
    
    public String getCrear1()
    {
        return CREAR_1;
    }
    public String getCrear2()
    {
        return CREAR_2;
    }
    public String getCrear3()
    {
        return CREAR_3;
    }
    
    /**
     * Debido a que nuestro sistema puede albergar 3 clases de productos distintos, este método será el encargado, en el momento en el que usuario
     * quiera dar de alta un producto, de preguntar que clase de producto se quiere dar de alta.
     * 
     * @param frame el frame padre
     * @param producto Si es null, indica que se está creando un producto nuevo. Si es un objeto de la clase ProductoBase o sus clases hijas, se modificará
     *                 dicho producto.
     */
    
    private void formulario(JFrame frame, ProductoBase producto)
    {
        if(producto == null){
            Object seleccion = JOptionPane.showInputDialog(
                                    frame,
                                    "Selecione el tipo del nuevo producto a crear",
                                    "Nuevo Producto",
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,  // null para icono defecto
                                    new String[] { CREAR_1, CREAR_2, CREAR_3 }, 
                                    CREAR_1);
            if(seleccion == null){
                //Se ha cancelado, no mostrar la ventana
                crearVentana = false;
            }else if(seleccion.equals(CREAR_1)){
                panelFormulario = new NuevoProductoGenerico(null);      //Producto = null; estamos creando un producto nuevo
            }else if(seleccion.equals(CREAR_2)){
                panelFormulario = new NuevoProductoMarca(null);
            }else{
                panelFormulario = new NuevoProductoTalla(null);
            }   
        }else{
            if(producto instanceof ProductoGenerico){
                panelFormulario = new NuevoProductoGenerico(producto);      //Le estamos pasando un producto; queremos modificar dicho producto
            }else if(producto instanceof ProductoMarca){
                panelFormulario = new NuevoProductoMarca(producto);
            }else{
                panelFormulario = new NuevoProductoTalla(producto);
            }
        }
    }
    
    /**
     * Este método será el encargado de crear los dos botones, "Aceptar" y "Cancelar" y asignarles sus acciones. Posteriormente se introducirán en un panel con
     * disposicion FlowLayout.
     * 
     * Presionando el boton "Aceptar", el usuario introduce los datos del producto en el sistema y presionando cancelar cierra el JDialog.
     * 
     * @param frame el frame padre
     * @param inventario    El lugar donde se almecenan los produtos
     * @param producto      Si es null, indica que se está creando un producto nuevo. Si es un objeto de la clase ProductoBase o sus clases hijas, se modificará
     *                      dicho producto.
     */
    private void control(JFrame frame, ContenedorProductos inventario, ProductoBase producto)
    {
        panelControl = new JPanel(new FlowLayout());
        botonAceptar = new JButton("Aceptar");
        botonAceptar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                int datosCorrectos = panelFormulario.obtenerDatos(inventario, producto);
                if(datosCorrectos == 3){
                    panelFormulario.crearProducto(inventario,producto);
                    dispose();
                    if(producto == null){
                        JOptionPane.showMessageDialog(frame,"El producto se ha creado correctamente y ha sido añadido al inventario", "Operación Realizada", JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(frame,"El producto ha sido modificado correctamente", "Operación Realizada", JOptionPane.INFORMATION_MESSAGE);
                    }
                }else if(datosCorrectos == 2){
                    JOptionPane.showMessageDialog(frame, "El precio/iva/stock deben ser valores numericos, intentelo de nuevo", "Error", JOptionPane.ERROR_MESSAGE);
                }else if(datosCorrectos == 1){
                    JOptionPane.showMessageDialog(frame, "Es obligatorio introducir un código, intentelo de nuevo", "Error", JOptionPane.ERROR_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(frame, "El código introducido ya pertenece a un producto, introduzca otro código", "Error", JOptionPane.ERROR_MESSAGE);
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