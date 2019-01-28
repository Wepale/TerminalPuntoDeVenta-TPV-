package Controlador;
import javax.swing.*;
import javax.swing.SpringLayout;
import java.awt.*;

/**
 * Esta clase permite crear ordenar componentes en una cuadricula. Se basará en el gestor de disposición SpringLayout.
 * 
 * Para que todo sea correcto y no salten errores, es necesario que 
 */
public class UtilidadesSpring {
    /* Usuado por hacerCuadricula */
    private static SpringLayout.Constraints getContencion(
                                                int row, int col,
                                                Container parent,
                                                int cols) {
        SpringLayout layout = (SpringLayout) parent.getLayout();
        Component c = parent.getComponent(row * cols + col);
        return layout.getConstraints(c);
    }

    /**
     * Alinea los componentes del JPanel en una grilla. Cada componente en la misma columna será tan ancho como el componente más grande que haya
     * en dicha columna. El JPanel será tan grande como sea necesario para albergar a todos los componentes.
     *
     * @param rows número de filas
     * @param cols número de columnas
     * @param initialX separacion en el eje x del primer componente 
     * @param initialY separacion en el eje y del primer componente
     * @param xPad separacion en el eje x de los componentes
     * @param yPad separacion en el eje y de los componentes
     */
    public static void hacerCuadricula(Container padre,
                                       int filas, int columnas,
                                       int inicioX, int inicioY,
                                       int xSeparacion, int ySeparacion) {
        SpringLayout layout;
        try {
            layout = (SpringLayout)padre.getLayout();
        } catch (ClassCastException exc) {
            System.err.println("El contenedor debe debe usar el gestor de disposicion SpringLayout.");
            return;
        }
        
        //Alinea todos los componentes en cada columna y hace que todos tengasn el mismo ancho
        Spring x = Spring.constant(inicioX);
        for (int c = 0; c < columnas; c++) {
            Spring ancho = Spring.constant(0);
            for (int r = 0; r < filas; r++) {
                ancho = Spring.max(ancho,
                                   getContencion(r, c, padre, columnas).
                                       getWidth());
            }
            for (int r = 0; r < filas; r++) {
                SpringLayout.Constraints constraints =
                        getContencion(r, c, padre, columnas);
                constraints.setX(x);
                constraints.setWidth(ancho);
            }
            x = Spring.sum(x, Spring.sum(ancho, Spring.constant(xSeparacion)));
        }
        
        //Alinea cada componente en una fila y hace que todos tengan la misma altura
        Spring y = Spring.constant(inicioY);
        for (int r = 0; r < filas; r++) {
            Spring altura = Spring.constant(0);
            for (int c = 0; c < columnas; c++) {
                altura = Spring.max(altura,
                                    getContencion(r, c, padre, columnas).
                                        getHeight());
            }
            for (int c = 0; c < columnas; c++) {
                SpringLayout.Constraints constraints =
                        getContencion(r, c, padre, columnas);
                constraints.setY(y);
                constraints.setHeight(altura);
            }
            y = Spring.sum(y, Spring.sum(altura, Spring.constant(ySeparacion)));
        }
        
        //Modificamos el tamaño del panel
        SpringLayout.Constraints pCons = layout.getConstraints(padre);
        pCons.setConstraint(SpringLayout.SOUTH, y);
        pCons.setConstraint(SpringLayout.EAST, x);
    }
}