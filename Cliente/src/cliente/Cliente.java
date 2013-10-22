/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;
import java.util.Vector;


/**
 *
 * @author Rachid
 */
public class Cliente implements ClienteInterface{

    Hashtable productos;    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }

    
    
    
    @Override
    public boolean mandarPrecioNuevo(String producto, float nuevoPrecio) throws RemoteException {
        if (productos.containsKey(producto)) {

            Producto infoProd;
            infoProd = (Producto) productos.get(producto);

            if (infoProd.actualizaPrecio(nuevoPrecio)) {
                
                return true;

            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
