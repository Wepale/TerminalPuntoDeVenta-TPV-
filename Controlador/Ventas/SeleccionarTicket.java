package Controlador.Ventas;
import Modelo.Contenedores.ContenedorTickets;
import Modelo.Ventas.Ticket;

import java.util.ArrayList;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
 * Esta clase se utilizará cuando se quiera crear una factura. Abrirá un JDialog modal y permitira que se seleccionen los tickets que
 * se deseen introducir en la factura.
 * 
 * Cada ticket tendrá su propio JCheckBox asociado y se podrán seleccionar todos cuantos se deseen.
 * 
 * @author Yeray Rodríguez Martín 
 * @version 1.0
 */
public class SeleccionarTicket extends JDialog
{
    private ArrayList<JCheckBox> listaCheck;
    private ContenedorTickets ticketsSeleccionados;
    private JScrollPane scroll;
    private JPanel panelOpciones;
    private JPanel panelControl;
    private JButton aceptar;
    private JButton cancelar;
    int marcados;
    
    public SeleccionarTicket(JFrame frame, ContenedorTickets archivadorLimitado)
    {
        super(frame, true);
        setTitle("Seleccione los Tickets");
        setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
        ticketsSeleccionados = null;
        marcados = 0;
        listaCheck = new ArrayList<JCheckBox>();
        crearOpciones(archivadorLimitado);
        control(archivadorLimitado);
        scroll = new JScrollPane(panelOpciones);
        getContentPane().add(scroll, BorderLayout.CENTER);
        getContentPane().add(panelControl, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(frame);
        setVisible(true);
    }
    
    /**
     * Da acceso a los tickets que se han seleccionados
     * 
     * @return ContenedorTickets con los tickets selecionados, null si se ha cerrado el JDialog
     */
    public ContenedorTickets getTicketsSeleccionados()
    {
        return ticketsSeleccionados;
    }
    
    /**
     * Por cada ticket que haya en el objeto ContenedorTickets que pasamos como parametro creará un JCheckBox para poder seleccionarlo
     * y lo introducira en un JPanel
     * 
     * @param   archivadorLimitado   los posibles tickets que se pueden introducir en la factura.
     */
    public void crearOpciones(ContenedorTickets archivadorLimitado)
    {
        panelOpciones = new JPanel();
        panelOpciones.setLayout(new BoxLayout(panelOpciones, BoxLayout.Y_AXIS));
        for(Ticket ticket : archivadorLimitado.getArchivador()){
            String codigo = ticket.getFecha(Ticket.CODIGO);
            String fecha = ticket.getFecha(Ticket.FECHA);
            JCheckBox check = new JCheckBox("Ticket nº: "+codigo+" / Fecha: "+fecha, false);
            check.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent actionEvent) {
                        AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
                        boolean selected = abstractButton.getModel().isSelected();
                        if (selected){
                            marcados++;
                            aceptar.setEnabled(true);
                        }else{
                            marcados--;
                        }
                        if(marcados == 0){
                            aceptar.setEnabled(false);
                        }
                    }
            });         
            listaCheck.add(check);
            check.setAlignmentX(Component.LEFT_ALIGNMENT);
            panelOpciones.add(check);
        }
    }
    
    /**
     * Comprueba que JCheckBox se han selecionados el usuario y devuelve un objeto ContendorTickets con los tickets seleccionados
     * 
     * @param archivadorLimitado los posibles tickets que se pueden introducir en la factura. Deberán de ser los mismo tickets que se usaron
     *                              en el método crearOpciones(ContenedorTickets), es decir, aquellos para los que se crearon los JCheckBox
     * 
     * @return  ContenedorTickets con los tickets seleccionados
     * 
     */
    public ContenedorTickets comprobarEleccion(ContenedorTickets archivadorLimitado)
    {
        ContenedorTickets ticketSeleccionados = new ContenedorTickets();
        for(JCheckBox check : listaCheck){
            if(check.isSelected()){
                String informacion = check.getText();
                String codigoTicket = informacion.substring(11, informacion.indexOf(" ",12));
                ticketSeleccionados.insertarTicket(archivadorLimitado.buscarTicket(codigoTicket));
            }
        }
        return ticketSeleccionados;
    }
    
    /**
     * Crear dos botones y los introduce un un JPanel con FlowLayout. Se usará cuando el usuario haya decidio que tickets quiere facturar.
     * El botón "Aceptar" comprobará que JCkeckBox estan seleccionados y el botón "Cancelar" cerrará el JDialog
     * 
     * @param archivadorLimitado los posibles tickets que se pueden introducir en la factura. Deberán de ser los mismo tickets que se usaron
     *                              en el método crearOpciones(ContenedorTickets), es decir, aquellos para los que se crearon los JCheckBox
     */
    public void control(ContenedorTickets archivadorLimitado)
    {
        panelControl = new JPanel();
        panelControl.setLayout(new FlowLayout());
        
        aceptar = new JButton("Aceptar");
        aceptar.setEnabled(false);
        aceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                ticketsSeleccionados = comprobarEleccion(archivadorLimitado);
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