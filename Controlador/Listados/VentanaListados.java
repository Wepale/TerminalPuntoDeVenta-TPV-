package Controlador.Listados;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Enumeration;
/**
 * Esta clase abrirá un JDialog modal donde el usuario podrá elegir el tipo de listado a visualizar y la fecha correspondiente.
 * 
 * @author Yeray Rodirguez 
 * @version 1.0
 */
public class VentanaListados extends JDialog
{
    protected final String OPCION_1 = "Productos más vendidos";
    protected final String OPCION_2 = "Ventas realizadas agrupadas por clientes";
    protected final String OPCION_3 = "Ventas realizadas a un cliente";
    private final int TAMANYO_DIA_MES = 2;
    private final int TAMANYO_ANYO = 4;
    
    private JRadioButton boton1;        
    private JRadioButton boton2;        //Estos botones se utilazarán para elegir el listado
    private JRadioButton boton3;
    
    private ButtonGroup grupo;          //Para solo poder seleccionar uno de los JRadioButton
    
    private JButton verListado;         //Para recoger los datos introducidos por el usuario cuando pulse dicho boton
    private JButton cancelar;           //Para cancelar y cerrar sin hacer nada.
    
    private JPanel panelRadio;        //Contendrá los JRadioButton
    private JPanel panelFecha;          //Contendrá los JTextField donde el usuario podrá introducir la fecha
    private JPanel panelControl;        //Contará con dos botones, uno para aceptar y recoger los datos del usuario y otro para cancela
    
    private JTextField diaInicial;
    private JTextField mesInicial;
    private JTextField anyoInicial;     //Los JTextField para que el usuario introduzca las fechas
    private JTextField diaFinal;
    private JTextField mesFinal;
    private JTextField anyoFinal;

    /**
     * Contendrá la fecha inicial introducida por el usuario
     */
    private Calendar fechaInicio;       //Se utilazarán los datos introducidos en el JTextField para crear esta variable
    /**
     * Contendrá la fecha final introducida por el usuario
     */
    private Calendar fechaFinal;        //Se utilazarán los datos introducidos en el JTextField para crear esta variable
    /**
     * Contendrá el nombre del listado seleccionado por el usuario
     */
    private String eleccionUsuario;  
    
    /**
     * Abrirá un JDialog donde el usuario podrá elegir el tipo de listado a mostrar e introducir las fechas para generar dicho listado
     * 
     * @param frame el frame padre
     */
    public VentanaListados(JFrame frame)
    {
        super(frame, true); //Lo hacemos modal
        setTitle("Introduzca la fecha y seleccione el listado");
        crearPanelFecha();
        crearPanelRadio();
        crearPanelControl(frame);
        getContentPane().add(panelFecha, BorderLayout.NORTH);
        getContentPane().add(panelRadio, BorderLayout.CENTER);
        getContentPane().add(panelControl, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(frame);
        setVisible(true);
    }
    
    /**
     * Método para acceder al nombre del JRadioButton que el usuario a seleccionado
     * 
     * @return eleccionUsauario el campo eleccionUsuario
     */
    public String getEleccionUsuario()
    {
        return eleccionUsuario;
    }
    
    /**
     * Método para acceder a la fecha inicial
     * 
     * @return el campo fechaInicio
     */
    public Calendar getFechaInicio()
    {
        return fechaInicio;   
    }
    
    /**
     * Método para acceder a la fecha final
     * 
     * @return el campo fechaInicial
     */
    public Calendar getFechaFinal()
    {
        return fechaFinal;   
    }
    
    /**
     * Esté método introducirá los JRadioButton en el panelBotones, el cual tendrá un gestor de disposición con BoxLayout vertical
     */
    public void crearPanelRadio()
    {
        //Creamos el JPanel y el gestor de dispocición
        panelRadio = new JPanel();
        panelRadio.setLayout(new BoxLayout(panelRadio, BoxLayout.Y_AXIS));
        //Creamos los JRadioButton  el ButtonGroup
        boton1 = new JRadioButton(OPCION_1, true);  //true = seleccionado por defecto
        boton2 = new JRadioButton(OPCION_2);
        boton3 = new JRadioButton(OPCION_3);
        grupo = new ButtonGroup();
        //Añadimos los JRadioButton al grupo
        grupo.add(boton1);
        grupo.add(boton2);
        grupo.add(boton3);
        //Los alineamos
        boton1.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton2.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton3.setAlignmentX(Component.CENTER_ALIGNMENT);
        //Los añadimos al panel
        panelRadio.add(boton1);
        panelRadio.add(boton2);
        panelRadio.add(boton3);
    }
    
    /**
     * Comprueba que botón que ha selecionado el usuario y asigan el nombre de dicho boton a la variable eleccionUsuario
     */
    public void comprobarEleccion()
    {
        eleccionUsuario = "";
        for (Enumeration<AbstractButton> botonesRadio = grupo.getElements(); botonesRadio.hasMoreElements();) {
            AbstractButton botonRadio = botonesRadio.nextElement();
            if (botonRadio.isSelected()) {//Siempre será true, ya que uno de los JRadioButton esta seleccionado por defecto en el momento de su creación
                eleccionUsuario = botonRadio.getText();
            }
        }
    }
    
    /**
     * Crea el panel donde el usuario podrá introducir las fechas
     */
    public void crearPanelFecha()
    {
        //Panel principal que contentrá todo lo necesario para pedir las fechas al usiario
        panelFecha = new JPanel(new FlowLayout(FlowLayout.CENTER, 15,0));
        //Creamos los paneles que albergarán las fechas por separado y definimos sus gestores por separado
        JPanel panelInicio = new JPanel();
        JPanel panelFinal = new JPanel();
        panelInicio.setLayout(new BoxLayout(panelInicio, BoxLayout.X_AXIS));
        panelFinal.setLayout(new BoxLayout(panelFinal, BoxLayout.X_AXIS));
        //Creamos los JTextField
        diaInicial = new JTextField(TAMANYO_DIA_MES);
        diaInicial.setText("DD");
        mesInicial = new JTextField(TAMANYO_DIA_MES);
        mesInicial.setText("MM");
        anyoInicial = new JTextField(TAMANYO_ANYO);
        anyoInicial.setText("AAAA");
        diaFinal = new JTextField(TAMANYO_DIA_MES);
        diaFinal.setText("DD");
        mesFinal = new JTextField(TAMANYO_DIA_MES);
        mesFinal.setText("MM");
        anyoFinal = new JTextField(TAMANYO_ANYO);
        anyoFinal.setText("AAAA");
                
        JLabel jInicial = new JLabel("Fecha Inicial:  ");
        JLabel jFinal = new JLabel("Fecha Final:  ");
        //Medidas del area rígida
        int areaRigidaX = 2;
        int areaRigidaY = 0;
        //Introducimos los componentes correspondientes en panelInicio
        panelInicio.add(jInicial);
        
        panelInicio.add(diaInicial);
        panelInicio.add(Box.createRigidArea(new Dimension(areaRigidaX,areaRigidaY)));
        panelInicio.add(new JLabel("/"));
        panelInicio.add(Box.createRigidArea(new Dimension(areaRigidaX,areaRigidaY)));
        
        panelInicio.add(mesInicial);
        panelInicio.add(Box.createRigidArea(new Dimension(areaRigidaX,areaRigidaY)));
        panelInicio.add(new JLabel("/"));
        panelInicio.add(Box.createRigidArea(new Dimension(areaRigidaX,areaRigidaY)));
        
        panelInicio.add(anyoInicial);
        
        //Introducimos los componentes correspondientes en panelFinal
        panelFinal.add(jFinal);
        
        panelFinal.add(diaFinal);
        panelFinal.add(Box.createRigidArea(new Dimension(areaRigidaX,areaRigidaY)));
        panelFinal.add(new JLabel("/"));
        panelFinal.add(Box.createRigidArea(new Dimension(areaRigidaX,areaRigidaY)));
        
        panelFinal.add(mesFinal);
        panelFinal.add(Box.createRigidArea(new Dimension(areaRigidaX,areaRigidaY)));
        panelFinal.add(new JLabel("/"));
        panelFinal.add(Box.createRigidArea(new Dimension(areaRigidaX,areaRigidaY)));
        
        panelFinal.add(anyoFinal);
        
        //Por último, añadimos los dos paneles con las fechas al panel principal
        panelFecha.add(panelInicio);
        panelFecha.add(panelFinal);
    }
    
    /**
     * Comprueba que las fechas introducidas sean valores correctos.
     * Como paremetros tedremos que pasarle los JTextField donde se recogen el día, el mes  y el año.
     * Comprobará que los datos introducidos sean valores numéricos, que los días estén entre 1 y 28/29/30/31 dependiendo del més introducido y de si el año
     * es bisiesto o no. Así mismo comprabará que el mes introducido se encuentro entre los valores 1 y 12.
     * 
     * @param campoDia el JTextField donde debería de introducirse el dia
     * @param campoMes el JTextField donde debería de introducirse el mes
     * @param campoAnyo el JTextField donde debería de introducirse el anyo
     * @return  0 si la fecha es correcta, 1 si alguno de los valores introducidos no son números enteros, 2 si alguno de los valores inroducidos no son nº
     *          válidos
     */
    public int comprobarFecha(JTextField campoDia, JTextField campoMes, JTextField campoAnyo)
    {
        GregorianCalendar calendar = new GregorianCalendar();
        int dia;
        int mes;
        int anyo;
        //Comprobamos que entre los datos introducidos no hayan letras ni nº no enteros
        try{
            dia = Integer.parseInt(campoDia.getText());
            mes = Integer.parseInt(campoMes.getText());
            anyo = Integer.parseInt(campoAnyo.getText());
        }catch(NumberFormatException ex){
            return 1;
        }
        //Comprobamos que los datos introducidos estan dentro de los límites
        dia = Integer.parseInt(campoDia.getText());
        mes = Integer.parseInt(campoMes.getText());
        anyo =Integer.parseInt(campoAnyo.getText());
        if((dia<1 || dia>31) || (mes<1 || mes>12) ||  (anyo<1000 || anyo>4000)){       //Primero comprobamos que estén dentro de los valores generales
            return 2;
        }
        //A partir de aquí, damos por hecho que los días mese y años están dentro de los límites
        if(mes == 2 || mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes ==8 || mes == 10 || mes == 12){  //Meses con 31 dias más febrero
            if(mes == 2){
                if(!calendar.isLeapYear(anyo) && dia>28 || calendar.isLeapYear(anyo) && dia>29){
                    return 2;
                }else{
                    return 0;
                }
            }else{          //Meses con 31 días
                return 0;
            }
        }else{  //Meses con 30 días
            if(dia>30){
                return 2;
            }else{
                return 0;
            }
        }
    }
    
    /**
     * Este método recogerá los datos de las fechas introducidos en los JTextField y creará un objeto GregorianCalendar con ellos
     * 
     * ATENCION: Este método no comprueba que las fechas sean correctas, para ello debemos usar el método "comprobarFechas(...)"
     * 
     * @return fecha    Un objeto tipo GregorianCalendar con las fechas introducidas
     */
    public void crearFecha()
    {
        /* No se incluirán try-catch ya que la idea de este método es solo ser llamado cuando el método comprovarFecha() devuelva 0,
         * lo que significa que los datos son correctos
         */
        int diaInicial = Integer.parseInt(this.diaInicial.getText());
        int mesInicial = Integer.parseInt(this.mesInicial.getText());
        int anyoInicial = Integer.parseInt(this.anyoInicial.getText());
        
        int diaFinal = Integer.parseInt(this.diaFinal.getText());
        int mesFinal = Integer.parseInt(this.mesFinal.getText());
        int anyoFinal = Integer.parseInt(this.anyoFinal.getText());
        
        fechaInicio = new GregorianCalendar(anyoInicial, mesInicial-1, diaInicial, 0, 0, 0);
        fechaFinal = new GregorianCalendar(anyoFinal, mesFinal-1, diaFinal, 23, 59, 59);
    }
    
    /**
     * Crea el panel control. Este contendra dos botones con su acciones correspondientes,
     * uno para recoger los datos del usuario y otro para cancelar y cerrar el JDialog
     * 
     * @param frame el frame padre
     */
    public void crearPanelControl(JFrame frame)
    {
        panelControl = new JPanel(new FlowLayout());
        
        verListado = new JButton("Ver Listado");
        verListado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                int controlInicial = comprobarFecha(diaInicial, mesInicial, anyoInicial);
                int controlFinal = comprobarFecha(diaFinal, mesFinal, anyoFinal);
                if(controlInicial == 1 || controlFinal == 1){
                    JOptionPane.showMessageDialog(frame, "Alguno de los campos introducidos no son números enteros\n"+
                                                         "Por favor, reviselos e intentelo de nuevo",
                                                         "Fecha Incorrecta", JOptionPane.ERROR_MESSAGE);
                }else if(controlInicial == 2 || controlFinal == 2){
                    JOptionPane.showMessageDialog(frame, "Alguno de los datos introducidos se encuentrán fuera del límite.\n"+
                                                         "Recuerde que:\n\n\t El rango de días es del 1 al 30-31 en función del mes, excepto Febrero, el "+
                                                         "cual tendra 28 días si el año no es bisitesto, 29 en caso de que si lo sea\n"+
                                                         "El rango de meses es 1-12\nEl rango de años es 1000-3000\n\nPor favor, revise "+
                                                         "los datos e intentelo de nuevo",
                                                         "Busqueda fallida", JOptionPane.ERROR_MESSAGE);
                }else{  //Las dos fechas son correctas
                    comprobarEleccion();    //Guardamos la eleccion del usuario
                    crearFecha();           //Creamos las fechas
                    dispose();              
                }
            }
        });
        panelControl.add(verListado);
        
        cancelar = new JButton("Cancelar");
        cancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                dispose();
            }
        });
        panelControl.add(cancelar);
    }
}