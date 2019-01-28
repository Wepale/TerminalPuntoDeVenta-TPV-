package Controlador.Productos;
import Modelo.Contenedores.ContenedorProductos;
import Modelo.Productos.*;

import java.util.Enumeration;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Esta clase construirá un JDialog modal donde el usuario podrá seleccionar 1 y solo 1 de los productos del inventario. El método más importante de esta clase es
 * crearOpciones(ContendorProductos) el cual recibe un numero cualquiera de productos y por cada uno crea un JRadioButton para poder selecionar solo 1 de ellos.
 * El lugar donde se usará esta clase será cuando se quiera introducir en el ticket un producto mediante su nombre y existan varios productos con el mismo nombre,
 * esto nos permitirá seleccionar exactamente el producto que queremos introducir.
 * 
 * Una vez seleccionado el producto deseado, su código se almacenará en la variable "private String codigoProducto", la cual tendriamos que consultar
 * despues de que se cierre el JDialog para saber que producto se a seleccioando.
 * 
 * @author Yeray Rodríguez Martín 
 * @version 1.0
 */
public class SeleccionarProducto extends JDialog
{
    private JScrollPane scroll;
    private JPanel panelOpciones;
    private JPanel panelControl;
    private JButton aceptar;
    private JButton cancelar;
    private ButtonGroup grupo;
    /**
     * Almacenará el código del producto seleccionado
     */
    private String codigoProducto;
    
    /**
     * Crea el JDialog completamente funcional con todo lo necesario para que le usuario pueda seleccionar un producto
     * 
     * @param frame el frame padre
     * @param contenedor los productos a seleccionar
     */
    public SeleccionarProducto(JFrame frame, ContenedorProductos contenedor)
    {
        super(frame, true); //Lo hacemos modal
        setTitle("Seleccione un producto");
        grupo = new ButtonGroup();
        codigoProducto = null;
        crearOpciones(contenedor);
        control();
        scroll = new JScrollPane(panelOpciones);
        getContentPane().add(scroll, BorderLayout.CENTER);
        getContentPane().add(panelControl, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(frame);
        setVisible(true);
    }
    
    /**
     * Devulve la variable de instancia codigoProducto
     * 
     * @return  codigoProducto el código del producto seleccionado, null si se ha cancelado la seleccion
     */
    public String getCodigoProducto()
    {
        return codigoProducto;
    }
    
    /**
     * Devuelve el boton aceptar
     * 
     * @return  JButton el boton aceptar
     */
    public JButton getAceptar()
    {
        return aceptar;
    }
    
    /**
     * Devuelve el boton cancelar
     * 
     * @return  JButton el boton cancelar
     */
    public JButton getCancelar()
    {
        return cancelar;
    }
    
    /**
     * Por cada producto que haya en el objeto ContenedorProductos creará un JRadioButton y lo introducirá en un JPanel
     * 
     * @param   contenedor  contiene los productos a mostrar
     */
    
    public void crearOpciones(ContenedorProductos contenedor)
    {
        panelOpciones = new JPanel();
        panelOpciones.setLayout(new BoxLayout(panelOpciones, BoxLayout.Y_AXIS));
        
        for(ProductoBase producto : contenedor.getContenedor()){
            String nombre;
            if(producto instanceof ProductoGenerico){
                ProductoGenerico productoAux = (ProductoGenerico)producto;
                nombre = productoAux.getNombre();
            }else if(producto instanceof ProductoMarca){
                ProductoMarca productoAux = (ProductoMarca)producto;
                nombre = productoAux.getMarca() + " " + productoAux.getModelo();
            }else{
                ProductoTalla productoAux = (ProductoTalla)producto;
                nombre = nombre = productoAux.getMarca() + " " + productoAux.getModelo();
            }
            String codigo = producto.getCodigo();
            String precio = String.valueOf(producto.getPrecio());
            String stock = String.valueOf(producto.getStock());
            JRadioButton radio = new JRadioButton("Código:"+codigo+" / Nombre:"+nombre+" / Precio con Iva"+precio+" / Stock:"+stock, true);
            grupo.add(radio);
            panelOpciones.add(radio);
        }
    }
    
    /**
     * Comprueba que boton ha selecionado el usuario y devuelve el codigo del producto asociado
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
                codigo = informacion.substring(7,informacion.indexOf(" "));
            }
        }
        return codigo;  //Nunca sera una cadena vacía puesto que obligatoriamente habrá un JRadioBatton siempre seleccionado
    }
    
    /**
     * Crear dos botones y sus correspondientes acciones y los introduce un un JPanel con FlowLayout. Si se presiona "Aceptar" se comprobará que producto
     * a seleccionado el usuario y guardará el código en el campo "codigoProducto". Si se presiona "Cancelar" de cierra el JDialog y la variable codigoProducto
     * tendrá valor null.
     */
    public void control()
    {
        panelControl = new JPanel();
        panelControl.setLayout(new FlowLayout());
        
        aceptar = new JButton("Aceptar");
        aceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                codigoProducto = new String(comprobarEleccion());
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
