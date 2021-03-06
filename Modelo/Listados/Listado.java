package Modelo.Listados;
import Modelo.Contenedores.ContenedorTickets;
import Modelo.Contenedores.ContenedorProductos;
import Modelo.Productos.*;
import Modelo.Ventas.Ticket;
import Modelo.Clientes.Cliente;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

/**
 * Esta clase será la encargada de generar los listados correspondientes.
 * 
 * Generará 3 clases de listados:
 *                      
 *                              1.Productos más vendido en un determinado espacio de tiempo
 *                              2.Productos vendidos en un determinado espacio de tiempo agrupados estos por clientes
 *                              3.Productos vendidos a un cliente en un espacio de tiempo determinado
 * 
 * @author Yeray Rodiguez
 * @version 1.0
 */
public class Listado
{
    /**
     * Dado un intervalo de tiempo este metodo devuelve los productos vendidos en dicho intervalo ordenados en numero de ventas (de mayor a menor)
     * 
     * @param   Calendar    El inicio de la fecha
     * @param   Calendar    El fin de la fecha
     * @para    ContendorTickets    El contendor con todas las ventas que se han realizado
     * 
     * @return  ContendorProductos  Los productos ordenados por número de ventas, null si no hay ninguno
     */
    public ContenedorProductos masVendidos(Calendar inicio, Calendar fin, ContenedorTickets ventas)
    {
        if(ventas.limitar(inicio, fin) == null){ //No existen tickets en el peridodo especifiado
            return null;
        }else{    
            ContenedorTickets ventasMod = ventas.limitar(inicio,fin);//Creamos un contenedor de tickets con los tickets comprendidos en el intervalo de fechas
            ContenedorProductos masVendidos = new ContenedorProductos();
            for(Ticket ticket : ventasMod.getArchivador()){    //Recorremos todos los tickets donde se encuentran todos los productos que hemos venidos en el intervalo
                Ticket ticketMod = new Ticket(ticket);
                for(ProductoBase producto : ticketMod.getProductos().getContenedor()){ //Recorremos todos lo productos que se encuentran en cada ticket
                    String codigoAux = new String(producto.getCodigo());
                    if(!masVendidos.getContenedor().isEmpty() && masVendidos.existeProducto(producto.getCodigo())){ //El producto ya se encuentra en la lista de mas vendidos
                            masVendidos.buscarCodigo(codigoAux).modVendido(producto.getVendido());
                        }
                        else{   //El producto no se encuentra en la lista de mas vendidos, por lo que lo añadimos
                            masVendidos.insertarProducto(producto);
                        }
                    }
            }
            if(masVendidos.estaVacio()){        //Aún no se ha realizado ninguna venta
                return null;
            }else{
                Collections.sort(masVendidos.getContenedor(), new OrdenarMasVendidos());    //Ordenamos los productos
                return masVendidos;
            }
        }
    }
    
    /**
     * Devolverá todas los productos vendidos en un intevalo de tiempo agrupados estos por clientes
     * 
     * @param   Calendar    El inicio del intervalo del tiempo
     * @param   Calendar    El fin del intervalo de tiempo
     * @param   ContenedorTickets   Los tickets de ventas
     * 
     * @return  ContenedorTickets Todas las ventas agrupadas por clientes
     */
    public ContenedorTickets ventasPorCliente(Calendar inicio, Calendar fin, ContenedorTickets ventas)
    {
        if(ventas.limitar(inicio, fin) == null){ //No existen tickets en el peridodo especifiado
            return null;
        }else{
            //Creamos un contenedor de tickets con los tickets comprendidos en el intervalo de fechas dado
            ContenedorTickets ventasMod = ventas.limitar(inicio,fin);
            ContenedorTickets ventasPorCliente = new ContenedorTickets();
            for(Ticket ticket : ventasMod.getArchivador()){
                Ticket ticketAux = new Ticket(ticket);
                String codigoCliente= ticketAux.getCliente().getCodigo();  //Codigo del cliente
                if(!ventasPorCliente.getArchivador().isEmpty() && ventasPorCliente.existeTicket(codigoCliente)){      //Ya existe un ticket del cliente, se añaden los productos
                    ContenedorProductos contenedor= ventasPorCliente.buscarPrimerTicket(codigoCliente).getProductos();
                    for(ProductoBase producto : ticketAux.getProductos().getContenedor()){
                        if(contenedor.existeProducto(producto.getCodigo())){ //El cliente ya ha comprado el producto
                            contenedor.buscarCodigo(producto.getCodigo()).modVendido(producto.getVendido());      //Le sumamos las unidades vendidas
                        }else{  //El cliente no ha comprado anteriormente el producto
                            contenedor.insertarProducto(producto);      //Añadimos el producto
                        }
                    }
                }
                else{   //No existe un ticket para este cliente, se añade el ticket
                    ventasPorCliente.insertarTicket(ticketAux);
                }
            }
            if(ventasPorCliente.estaVacio()){       //Aún no se ha realizado ninguna venta
                return null;
            }else{
                return ventasPorCliente;
            }
        }
    }
    
    /**
     * Devolverá las ventas realizadas en un intervalo de tiempo determinado a un solo cliente
     * 
     * @param   Calendar    El inicio del intervalo del tiempo
     * @param   Calendar    El fin del intervalo de tiempo
     * @param   ContenedorTickets   Los tickets de ventas
     * @param   codigoCliente     El codigo del cliente cliente
     * 
     * @return  ContenedorTickets un archivador de tickets el cual solo tendrá un único ticket con todos los productos que ha comprado
     *                            el cliente entre las dos fechas dadas, null si no se encuentran resultados
     *                              
     */
    public ContenedorTickets ventasACliente(Calendar inicio, Calendar fin, ContenedorTickets ventas, String codigoCliente)
    {
        if(ventas.limitar(inicio, fin) == null){ //No existen tickets en el peridodo especifiado
            return null;
        }else{
            //Creamos un contenedor de tickets con los tickets comprendidos en el intervalo de fechas dado
            ContenedorTickets ventasMod = ventas.limitar(inicio,fin);
            Cliente cliente = null;
            ContenedorProductos contenedor = new ContenedorProductos();
            for(Ticket ticket : ventasMod.getArchivador()){
                Ticket ticketAux = new Ticket(ticket);
                if(ticketAux.getCliente().getCodigo().equals(codigoCliente)){
                    cliente = ticketAux.getCliente();
                    for(ProductoBase producto : ticketAux.getProductos().getContenedor()){
                        if(contenedor.existeProducto(producto.getCodigo())){
                            contenedor.buscarCodigo(producto.getCodigo()).modVendido(producto.getVendido());
                        }else{
                            contenedor.insertarProducto(producto);
                        }
                    }
                }
            }
            if(contenedor.estaVacio()){     //No se ha realizado ninguna venta a esté cliente en las fechas seleccionadas
                return null;
            }else{
                ContenedorTickets resultado = new ContenedorTickets();
                resultado.insertarTicket(new Ticket(contenedor, cliente));        
                return resultado;   //Se devuelve un ContenedorProductos que contiene 1 único ticket con todos los productos que ha comprado el cliente entre las dos fechas dadas
            }
        }
    }
    
    /*
     * Clase interna que ordenará los productos por sus unidades vendidas de mayor a menor
     */
    class OrdenarMasVendidos implements Comparator<ProductoBase>
    {
        @Override
        public int compare(ProductoBase p1, ProductoBase p2)
        {
            return new Double(p2.getVendido()).compareTo(new Double(p1.getVendido()));
        }
    }    
}
