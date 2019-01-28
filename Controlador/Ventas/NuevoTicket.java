package Controlador.Ventas;

import Modelo.Clientes.Cliente;
import Modelo.Contenedores.*;
import Modelo.Productos.*;
import Modelo.Ventas.Ticket;

import Controlador.Productos.SeleccionarProducto;
import Controlador.Clientes.BuscarUnCliente;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
 * Esta clase será la encargada de realizar una venta y crear el ticket correspondiente.
 * 
 * En primer lugar solicitará al usuario que seleccione el cliente al que se le realizará la venta mediante las distintas opciones disponibles.
 * Una vez seleccionado, se procederá a introducir los productos en el ticket. Se introducirá uno cada vez. Se controlará que la venta se pueda realizar,
 * y que los valores introducidos sean validos. Así mismo se modificará el stock correspondiente del almacen. Cada vez que se introduzca un producto
 * se le preguntará al usuario el siguiente paso, a elegir entre:
 * 
 * Finalizar la venta
 * Introducir un nuevo producto al ticket
 * Cancelar la venta y salir
 * 
 * Si se finaliza la venta, se crea un nuevo ticket y se almacena.
 * Si se quiere introducir otro producto, prestará la interfaz gráfica necesaria para hacerlo. Si el producto que se quiere introducir ya esta en el ticket
 * se modifica la cantidad a vender del que ya está en el ticket.
 * Si se cancela la venta, se restauran los valores de stock de los productos que existieran en el ticket.
 * 
 * Si mientras se está introduciendo un producto en el ticket y se cancela dicha introducción, se comprobará si el ticket está vacío. En tal caso
 * se cancela la venta directamente. Si no estuviera vacio, se le preguntará al usuario si desea cancelar la venta o finalizarla.
 * 
 * @author Yeray Rodríguez Martín 
 * @version 1.0
 */
public class NuevoTicket
{
    private final String OPCION_1 = "Buscar por Nombre";
    private final String OPCION_2 = "Buscar por Código";
    private final String OPCION_3 = "Añadir otro producto al ticket";
    private final String OPCION_4 = "Finalizar la venta";
    private final String OPCION_5 = "Cancelar y salir";
    private final String OPCION_8 = "Mostrar todo el inventario";
    
    private InformacionTicket mostrar;
    private SeleccionarProducto elegirProducto;
    private BuscarUnCliente buscarUnCliente;
    
    /**
     * Contendrá los productos de la venta
     */
    private ContenedorProductos productosTicket; 
    /**
     * Contendrá al cliente al que se le realizá la venta
     */
    private Cliente cliente;
    
    /**
     * Llamando a este constructor se generará todo lo necesario para poder relizar una venta
     * 
     * @param frame el frame padre
     * @param inventario el luegar en el que se alamcenan todos los productos dados de alta en el sistema
     * @param listaClientes el luegar donde se almacenan todos los clientes dados de alta en el sistema
     * @param archivador el lugar donde se alamacenan los tickets creados
     * @panel panel el panel donde se mostrarán los productos introducidos en el ticket
     */
    public NuevoTicket(JFrame frame, ContenedorProductos inventario, ContenedorClientes listaClientes, ContenedorTickets archivador, JPanel panel){
        productosTicket = new ContenedorProductos();
        mostrar = new InformacionTicket();
        buscarUnCliente = new BuscarUnCliente();
        cliente = null;
        realizarVenta(frame, inventario, listaClientes, archivador, panel);
    }
    
    /**
     * Este método preguntará al usuario por que atributo quiere buscar el producto que desea añadir al ticket. Las opciones serán por nombre,
     * código o mostrar todos los pructos existentes en el inventario
     * 
     * @param frame el frame padre
     * 
     * @return  la opcion seleccionada
     */
    private String preguntarAtributoProducto(JFrame frame)
    {
        Object seleccion = JOptionPane.showInputDialog(
        frame,
        "La venta actual se está realizando a: "+cliente.getNombre()+"\n\n¿Que atributo desea usar para buscar el producto que desea vender?\n\n",
        "Buscar Productos",
        JOptionPane.QUESTION_MESSAGE,
        null,  // null para icono defecto
        new String[] { OPCION_1, OPCION_2, OPCION_8}, 
        OPCION_1);
        
        return (String)seleccion;
    }
    
    /**
     * Este método pregunta al usuario el código del producto que desea añadir al ticket
     * 
     * @param frame el frame padre
     * @param inventario    El inventario con todos los productos
     * @param archivador    El archivador donde el sistema guarda los tickets generados
     * @param panel el panel en el que se mostrarán los productos introducidos en el ticket
     */
    private void preguntarCodigo(JFrame frame, ContenedorProductos inventario, ContenedorTickets archivador, JPanel panel)
    {
        String seleccion = JOptionPane.showInputDialog(frame, "Introduzca el código del producto que desea introducir en el ticket\n\n", JOptionPane.QUESTION_MESSAGE);
        if(seleccion == null){
            //Se ha seleccionado cancelar o se ha cerrado el JOptionPane. Si no hay productos en el ticket, se cancela la venta, en caso contrario se pregunta al usuario el siguente paso
            introducirProducto(frame, inventario, archivador, panel);
        }else if(inventario.existeProducto(seleccion)){
            ProductoBase producto = inventario.buscarCodigo(seleccion);
            preguntarUnidadesAVender(frame, inventario, archivador, producto, panel);
        }else{
            JOptionPane.showMessageDialog(frame, "El código introducido no corresponde con ningun producto.\n\nIntentelo de Nuevo\n\n", "Producto no encontrado", JOptionPane.ERROR_MESSAGE);
            preguntarCodigo(frame, inventario, archivador, panel);
        }
    }
    
    /**
     * Este método pregunta al usuario el nombre del producto que desea añadir al ticket. Si existen varias coincidencias se le pedirá
     * que seleccione el producto adecuado
     * 
     * @param frame el frame padre
     * @param inventario el luegar en el que se alamcenan todos los productos dados de alta en el sistema
     * @param archivador el lugar donde se alamacenan los tickets creados
     * @panel panel el panel donde se mostrarán los productos introducidos en el ticket
     */
    private void preguntarNombre(JFrame frame, ContenedorProductos inventario, ContenedorTickets archivador, JPanel panel)
    {
        String seleccion = JOptionPane.showInputDialog(frame, "Introduzca el nombre, marca o modelo del producto que desea vender\n\n", JOptionPane.QUESTION_MESSAGE);
        if(seleccion == null){
            //Se ha seleccionado cancelar o se ha cerrado el JOptionPane. Si no hay productos en el ticket, se cancela la venta, en caso contrario se pregunta al usuario el siguente paso
            introducirProducto(frame, inventario, archivador, panel);
        }else if(inventario.existeNombre(seleccion)){
            ContenedorProductos resultados = inventario.buscarNombre(seleccion);
            verProductos(frame, inventario, archivador, resultados, panel); //Esto es lo que hay que borrar si ponemos de nuevo el if else
        }else{
            JOptionPane.showMessageDialog(frame, "No se han encontrado coincidencias con el nombre/marca/modelo introducido.\n\nIntentelo de Nuevo\n\n", "Producto no encontrado", JOptionPane.ERROR_MESSAGE);
            preguntarNombre(frame, inventario, archivador, panel);
        }
    }
    
    /**
     * Esté metodo abre un JDialog para que el usuario seleccione uno de los productos que estén contenidos en el objeto ContenedorProductos
     * que se le pasa como parámetro.
     * 
     * Cuando el usuario seleccione el producto, se le preguntará las unidades a vender.
     *
     * @param frame el frame padre
     * @param inventario el luegar en el que se alamcenan todos los productos dados de alta en el sistema
     * @param archivador el lugar donde se alamacenan los tickets creados
     * @panel panel el panel donde se mostrarán los productos introducidos en el ticke
     */
    private void verProductos(JFrame frame, ContenedorProductos inventario, ContenedorTickets archivador, ContenedorProductos contenedor, JPanel panel)
    {
       elegirProducto = new SeleccionarProducto(frame, contenedor);   //JDialog para que el usuario seleccione el producto
       String codigo = elegirProducto.getCodigoProducto();
       if(codigo == null){
           //Se ha presionado cancelar o se ha cerreado el JDialog
           introducirProducto(frame, inventario, archivador, panel);
       }else{
           preguntarUnidadesAVender(frame, inventario, archivador, inventario.buscarCodigo(codigo), panel);
       }
    }
    
    /**
     * Este método pedirá al usuario que intruduzca las unidades a vender de un producto. Si no existe stock del producto, se introducen más unidades del stock
     * disponible o se introduce un dato no valido, se mostrarán los errores oportunos.
     * 
     * @param frame el frame padre
     * @param inventario el luegar en el que se alamcenan todos los productos dados de alta en el sistema
     * @param archivador el lugar donde se alamacenan los tickets creados
     * @param producto el producto que se desea vender
     * @panel panel el panel donde se mostrarán los productos introducidos en el ticket
     */
    
    private void preguntarUnidadesAVender(JFrame frame, ContenedorProductos inventario, ContenedorTickets archivador, ProductoBase producto, JPanel panel)
    {
        String vender = JOptionPane.showInputDialog(frame, producto.getCodigo()+" "+producto.getNombre()+ "\nUnidades disponibles en stock: " +producto.getStock()+
                                                    "\n\nIntroduzca la cantidad de unidades que desea vender\n\n", JOptionPane.QUESTION_MESSAGE);
        double aVenderAux = 0;
        boolean tryActivado = false;
        if(vender == null){
            //Se ha seleccionado cancelar o se ha cerrado la ventana, se pregunta de nuevo el producto que se desea añadir
            introducirProducto(frame, inventario, archivador, panel);
        }else{
            try{
                aVenderAux = Double.parseDouble(vender);
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(frame, "Debe introducir un número\n\nIntentelo de nuevo\n\n", "ERROR", JOptionPane.ERROR_MESSAGE);
                preguntarUnidadesAVender(frame, inventario, archivador, producto, panel);
                tryActivado = true;
            }
            if(tryActivado){
                //A saltado la excepcion, no continuar
            }else{
                double aVender = aVenderAux;
                double stock = producto.getStock();
                if(aVender <= 0){
                    JOptionPane.showMessageDialog(frame, "Debe introducir un numero mayor que 0.\n\nIntentelo de nuevo\n\n", "ERROR", JOptionPane.ERROR_MESSAGE);
                    preguntarUnidadesAVender(frame, inventario, archivador, producto, panel);
                }else if(stock == 0){
                    JOptionPane.showMessageDialog(frame, "Actualmente no existe stock para este producto en el inventario\n\n"+
                                                    "En la siguiente pantalla seleccione otro producto o cancele la venta si así lo desea\n\n", "Fuera de Stock",
                                                    JOptionPane.ERROR_MESSAGE);
                    introducirProducto(frame, inventario, archivador, panel);
                }else if(stock < aVender){
                    JOptionPane.showMessageDialog(frame,"Stock insuficiente. Unidades en stock:"+stock+"\n\nIntetelo de nuevo con menos unidades\n\n", "ERROR", JOptionPane.ERROR_MESSAGE);
                    preguntarUnidadesAVender(frame, inventario, archivador, producto, panel);
                }else{
                    producto.modVendido(aVender);
                    producto.modStock(-aVender);
                    if(productosTicket.existeProducto(producto.getCodigo())){
                        //Como el producto ya esta en la lista de productos del ticket y ya he actualizado las unidades
                        //vendidas y el stock en el inventario no se hace nada
                    }else{  //No está en el ticket, se introduce en él.
                        productosTicket.insertarProducto(producto); 
                    }
                    mostrar.actualizarTicket(panel, productosTicket);
                    JOptionPane.showMessageDialog(frame, "El producto se ha añadido correctamente al ticket\n\n", "Operación Aceptada", JOptionPane.INFORMATION_MESSAGE);
                    preguntarAccion(frame, inventario, archivador, panel);
                }
            }
        }
    }
    
    /**
     * Este método hará todo lo necesario para introducir un producto en el ticket. Preguntará como se quiere buscar el producto a introducir en el ticket, comprobará
     * las unidades que se quieren vender con el stock disponible y finalmente añadirá el producto al ticket.
     * 
     * @param frame el frame padre
     * @param inventario el luegar en el que se alamcenan todos los productos dados de alta en el sistema
     * @param archivador el lugar donde se alamacenan los tickets creados
     * @panel panel el panel donde se mostrarán los productos introducidos en el ticket
     * 
     */
    private void introducirProducto(JFrame frame, ContenedorProductos inventario, ContenedorTickets archivador, JPanel panel)
    {
        String seleccion = preguntarAtributoProducto(frame);
        if(seleccion == null){  //Se ha presionado cancelar
            comprobarCancelacion(frame, inventario, archivador, panel);
        }else if(seleccion.equals(OPCION_1)){
            preguntarNombre(frame, inventario, archivador, panel);
        }else if(seleccion.equals(OPCION_2)){
            preguntarCodigo(frame, inventario, archivador, panel);
        }else{
            verProductos(frame, inventario, archivador, inventario, panel);
        }
    }
    
    /**
     * Despues de introducir un producto en el ticket, se le preguntará al usuario que desea hacer. 
     * Las opciones serán: Añadir otro producto al ticket, cerrar el ticket y finalizar la venta o cancelar la venta
     * 
     * @param frame el frame padre
     * @param inventario el luegar en el que se alamcenan todos los productos dados de alta en el sistema
     * @param archivador el lugar donde se alamacenan los tickets creados
     * @panel panel el panel donde se mostrarán los productos introducidos en el ticke
     */
    private void preguntarAccion(JFrame frame, ContenedorProductos inventario, ContenedorTickets archivador, JPanel panel)
    {
        int seleccion = JOptionPane.showOptionDialog(
        frame,
        "¿Que desea hacer a continuación?\n\n",
        "Seleccione opcion", 
        JOptionPane.YES_NO_CANCEL_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,    // null para icono por defecto.
        new Object[] { OPCION_3, OPCION_4, OPCION_5 },   // null para YES, NO y CANCEL
        OPCION_1);
        if(seleccion == 0){
            introducirProducto(frame, inventario, archivador, panel);
        }else if(seleccion == 1){
            Ticket ticket = new Ticket(productosTicket, cliente);       //Se crea el ticket. Tanto los productos y el cliente seran nuevos objetos para evitar que posteriores cambios en el inventario o los clientes afecten al ticket
            archivador.insertarTicket(ticket);
            productosTicket.resetearVendido();
            JOptionPane.showMessageDialog(frame, "La venta se ha realizado correctamente. El ticket con la información de la venta se ha añadido\n"+
                                                 "al archivador de tickets. Se ha modificado el stock de los productos vendidos\n", "Operación Aceptada", JOptionPane.INFORMATION_MESSAGE);
            panel.removeAll();
            panel.updateUI();
            cliente = null;
        }else{  //Se ha seleccionado cancelar venta o se ha cerrado el JOptionPane
            JOptionPane.showMessageDialog(frame,"La venta ha sido cancelada", "Operación Cancelada", JOptionPane.ERROR_MESSAGE);
            productosTicket.resetearStock();
            productosTicket.resetearVendido();
            panel.removeAll();
            panel.updateUI();
            cliente = null;
        }
    }
    
    /**
     * Esté metodo comprobará si el ticket contiene productos. Si el ticket está vacio, se le mostrará un mensaje al usuario informandole que la venta
     * ha sido cancelada. En caso contrario, se le preguntará al usuario que se desea hacer a continuación.
     * 
     * @param frame el frame padre
     * @param inventario el luegar en el que se alamcenan todos los productos dados de alta en el sistema
     * @param archivador el lugar donde se alamacenan los tickets creados
     * @panel panel el panel donde se mostrarán los productos introducidos en el ticke
     */
    public void comprobarCancelacion(JFrame frame, ContenedorProductos inventario, ContenedorTickets archivador, JPanel panel)
    {
        if(productosTicket.estaVacio()){
            //No existen productos en el ticket, se cancela la venta
            JOptionPane.showMessageDialog(frame,"La venta ha sido cancelada", "Operación Cancelada", JOptionPane.ERROR_MESSAGE);
        }else{
            //Existen productos en el ticket, se pregunta que se desea hacer
            preguntarAccion(frame, inventario, archivador, panel);
        }
    }
    
    /**
     * Este método hará todo lo necesario para realizar una venta. Primero preguntará el cliente al que se le realizará la venta, posteriormente se seleccionarán
     * los productos que se venderán y cuando se confirme la venta el ticket será introducido al archivador de tickets y se modificará el stock de los productos
     * vendidos.
     * Es decir, será el método clave de esta clase y hará uso de todos los demas según sea necesario
     * 
     * @param frame el frame padre
     * @param inventario el lugar en el que se alamcenan todos los productos dados de alta en el sistema
     * @param listaClientes el elugar en el que se almacenan los clientes dados de alta en el sistema
     * @param archivador el lugar donde se alamacenan los tickets creados
     * @panel panel el panel donde se mostrarán los productos introducidos en el ticke
     */
    public void realizarVenta(JFrame frame, ContenedorProductos inventario, ContenedorClientes listaClientes, ContenedorTickets archivador, JPanel panel)
    {
        buscarUnCliente.preguntarPorCliente(frame, listaClientes);
        cliente = buscarUnCliente.getCliente();
        if(cliente == null){
            //No se ha seleccionado ningun cliente por que se ha cancelado. No hacer nada
        }else{
            introducirProducto(frame, inventario, archivador, panel);
        }
    }
}