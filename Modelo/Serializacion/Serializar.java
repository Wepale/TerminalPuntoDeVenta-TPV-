package Modelo.Serializacion;
import java.io.File;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.File;

/**
 * Esta clase contendrá los dos métodos necesarios para serializar objetos.
 * Uno de los métodos escribirá un objeto en un fichero y el otro lo leerá.
 * 
 * @author Yeray Rodriguez Martin 
 * @version 1.0
 */
public class  Serializar
{
    /**
     *  Esté metodo recibe un objeto y la ruta de un archivo y lo alamacena en él.
     *  
     *  Se usará para guardar los productos, clientes, tickets y facturas. Debemos pasarle como parametro
     *  el objeto a serializar y la ruta del archivo donde se guardará.
     * 
     * @param nombreFichero el nombre de la ruta donde se guardará el objeto
     * @param objeto el objeto a serializar
     * @return true si la serialización se lleva a cabo de forma correcta, false en caso contrario
     */
    public void guardarEnFichero(String nombreFichero, Object objeto)
    {
        try
        {
            //Se crea un ObjectOutputStream
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombreFichero));
            oos.writeObject(objeto);    //Se ecribe
            oos.close();    //Se cierra
            //return true;
        } catch(Exception e){
            //return false;
        }
    }
    
    /**
     *  Esté metodo lee un archivo y devuelve el objeto contenido en el.
     *  
     *  Se usará para leer los productos, clientes, tickets y facturas ya almacenados en nuestro sistema.
     *  Debemos pasarle como parametro la ruta del archivo que se quiere leer.
     * 
     * @param nombreFichero el nombre de la ruta del archivo que se quiere leer
     * @return Object el objeto que contiene el archivo
     */
    public Object leerDeFichero(String nombreFichero)
    {
        try
        {
            //Se crea un ObjetcInputStream
            ObjectInputStream ois= new ObjectInputStream(new FileInputStream(nombreFichero));
            Object aux = ois.readObject();  //Se lee
            ois.close();    //Se cierra
            return aux;
        }catch(FileNotFoundException e){
            //No hacer, otra clase se encarga de avisar al usuario, dependiendo de que se haya intentado
            //leer lanzará un mensaje personalizado
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
