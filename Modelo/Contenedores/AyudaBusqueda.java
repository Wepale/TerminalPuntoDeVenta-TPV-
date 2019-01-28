package Modelo.Contenedores;


/**
 * Esta clase servirá para modificará las distintas con el fin de comparlas entre ellas.
 * 
 * Los métodos implementados en esta clase se ha realizado con la idea de reutilzar código y para ser usados de forma auxiliar por otras clases.
 * Contienen sentencias que se usuran continuamente cuando se quieran buscar productos/tickets/facturas/clientes comparando los campos de tipo String.
 * Estos métodos no buscarán productos/tickets/facturas/clientes por si solos, seran usados por los métodos que realizen dicha función
 * 
 * @author Yeray Rodríguez Martín 
 * @version 1.0
 */
public class AyudaBusqueda
{
    /**
     * Metodo para pasar un cadena a minuscula y quitarle todos y cada uno sus espacios. 
     * 
     * @param cadena La cadena a la cual queremos realizar la modificación
     * 
     * @return String   La cadena modificada
     */
    public static String modCadena(String cadena)
    {
        return cadena.toLowerCase().replace(" ","");
    }
    
    /**
     * Método que recibe una cadena y realiza lo siguiente:
     *              -Elemina los espacios en blanco al inicio al final
     *              -Transforma dos o más espacios en blanco en medio del String en un solo espacio en blanco
     *              
     * @param cadena La cadena a la cual queremos realizar la modificación
     * 
     * @return String   La cadena modificada
     */
    public static String modCadena2(String cadena)
    {
        return cadena.trim().replaceAll(" +", " ");
    }
    
    /**
     * Método que recibe dos cadenas, las pasa a minúscula, les quita todos y cada uno de sus espacios y las concatena.
     * 
     * @param String cadena1    La primera cadena
     * @param String cadena2    La segunda cadena
     * 
     * @return String Un String que contendrá las dos cadenas concatenadas, en minúscula y sin espacios en blanco
     */
    public static String modCadena(String cadena1, String cadena2)
    {
        return modCadena(cadena1).concat(modCadena(cadena2));
    }
    
    /**
     * Método que recibe una cadena, la cual dividirá en palabras y añadirá cada una a un vector. Este metodo se encargará automaticamente de
     * eleminar cualquier espacio en blanco al final y al principio de la cadena, asi mismo, si una palabra esta separada de otra por varios espacios
     * en blanco, los eliminará dejando solo un espacio entre cada palabra. 
     * 
     * @param String cadena La cadena que queremos dividir en palabras
     * 
     * @return  String[]    Un vector con todas las palabras que contiene la cadena, con la primera palabra en la posición 0, la segunda en la 1 y
     *                      asi sucesivamente
     */
    public static String[] separar(String cadena)
    {
        return modCadena2(cadena).toLowerCase().split(" ");
    }
    
    /**
     * Método que recibe una cadena y un vector de String y comprueba si TODOS Y CADA UNO de los String contenidos en el vector estan incluidos en la cadena
     * 
     * @param   String   La cadena en la que queramos buscar coincidencias
     * @param   String[] El vector con las palabras que queremos buscar
     * 
     * @return true si la cadena contiene todas las palabras que contiene el vector, false en caso contrario
     */
    public static boolean coincideTodo(String cadena, String[] vector)
    {
        int coincidencias=0;
        for(int i=0; i<vector.length; i++){
            if(cadena.contains(vector[i])){
                coincidencias++;
            }
        }
        if(coincidencias==vector.length){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Método que recibe una cadena y un vector de String y comprueba si ALGUNO de los String contenidos en el vector es IGUAL a la cadena
     * 
     * @param   String   La cadena en la que queramos buscar coincidencias
     * @param   String[] El vector con las palabras que queremos buscar
     * 
     * @return true si la cadena es igual a alguna palabras que contiene el vector, false en caso contrario.
     */
    public static boolean coincideAlgo(String cadena, String[] vector)
    {
        for(int i=0; i<vector.length; i++){
            if(cadena.equals(vector[i])){
                return true;
            }
        }
        return false;
    }
}