package cliente;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import datos.Producto;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import tienda.Agente;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;
import java.util.Vector;
//import datos.*;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
//import tienda.*;

/**
 *
 * @author Rachid
 */
public class Cliente implements ClienteInterface {

    Hashtable<String, Producto> productos;
    Agente tienda;
    Registry registry;
    String nombre;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try{
            
            //el codigo del dr es:
            //String response = stub.sayHello();
	    //System.out.println("response: " + response);
            //falta agregar la interfaz aqui
            
            ClienteApplet applet = new ClienteApplet();
            applet.setVisible(true);
            
        }catch (Exception e){
            System.err.println("Client exception: " + e.toString());
	    e.printStackTrace();
        }
    }
    /*
    public void encontrarTienda() {
        try {
            Registry registry = LocateRegistry.getRegistry();
            tienda = (Agente) registry.lookup("Agente");
        } catch (Exception ex) {
        }
    }*/

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
    
    public void setNombre(String nombre){
        
        this.nombre = nombre;
    }

    public boolean RegistrarUsuario(String str) {
         try {
             nombre=str;
            Registry registry = LocateRegistry.getRegistry();
            ClienteInterface stub = (ClienteInterface) UnicastRemoteObject.exportObject(this, 0);//Va 0 ahí? // creo que si
            registry.bind(nombre, stub);
            tienda=(Agente)registry.lookup("Agente");
            tienda.registraUsuario(nombre);
        } catch (RemoteException | NotBoundException | AlreadyBoundException ex) {
            return false;
        }
        return true;
    }
    
    
    public boolean borrarUsuario() {
         try {
            tienda.borrarUsuario(nombre);
        } catch (RemoteException ex) {
             try {
                 Registry registry = LocateRegistry.getRegistry();
                 registry.unbind(nombre);
                 return true;
             } catch (     RemoteException | NotBoundException ex1) {
                 return false;
             }
        }
        return true;
    }

    @Override
    public boolean mandarProductoNuevo(Producto producto) throws RemoteException {
        if (!productos.containsKey(producto)) {

            productos.put(producto.getNombreProducto(),producto);

            return true;
        } else {
            return false;
        }
    }
    
}
