package Modelo.Serializacion;
import java.io.File;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Write a description of class Guardar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Guardar
{
    public void guardarEnfichero(File fichero, Object objeto)
    {
        try
        {
            //Se crea un ObjectOutputStream
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichero));
            oos.writeObject(objeto);
            oos.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public Object leerDeFichero(File fichero)
    {
        try
        {
            //Se crea un ObjetcInputStream
            ObjectInputStream ois= new ObjectInputStream(new FileInputStream(fichero));
            Object aux = ois.readObject();
            ois.close();
            return aux;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
