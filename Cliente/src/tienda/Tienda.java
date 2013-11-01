/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tienda;

import datos.Oferta;
import datos.Producto;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;
import java.util.Vector;
import cliente.ClienteInterface;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
//import datos.Oferta;
//import datos.Producto;

/**
 *
 * @author Rachid
 */
public class Tienda implements Agente{

    /**
     * @param args the command line arguments
     */
    Hashtable<String, Producto> productos;
    Hashtable<String, Oferta> ofertas;
    Hashtable<String, ClienteInterface> usuarios;
    Registry registry;
    

    public Tienda() {
        productos = new Hashtable<>();
        ofertas = new Hashtable<>();
        usuarios=new Hashtable<>();
        try {
            registry = LocateRegistry.getRegistry();
        } catch (RemoteException ex) {
            Logger.getLogger(Tienda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized boolean registraUsuario(String nombre) throws RemoteException {
        if (!usuarios.containsKey(nombre)) {
            System.out.println("Agregando un nuevo usuario: " + nombre);
            ClienteInterface stub;
            try {
                stub=(ClienteInterface)registry.lookup(nombre);
            } catch (    NotBoundException | AccessException ex) {
                Logger.getLogger(Tienda.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
            usuarios.put(nombre,stub);
            return true;
        } else {
            return false;
        }
    }
    
    public synchronized boolean borrarUsuario (String nombre) throws RemoteException
    {
        if (usuarios.containsKey(nombre)) {
            System.out.println("Borrando usuario: " + nombre);
            usuarios.remove(nombre);
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean agregaProductoALaVenta(String vendedor, String producto, float precioInicial) throws RemoteException {
        if (!productos.containsKey(producto)) {
            Producto nuevo= new Producto(vendedor,producto, precioInicial);
            System.out.println("Agregando un nuevo producto: " + producto);
            productos.put(producto, nuevo);
            mandarProductoAClientes(nuevo);
            return true;

        } else {
            return false;
        }
    }

    private void mandarProductoAClientes(Producto nuevo)
    {
        boolean success;
        ClienteInterface stub;
        for (String key:usuarios.keySet())
        {
            stub=usuarios.get(key);
            success=false;
            for(int i=0;i<2;i++)
            {
                try {
                    success=stub.mandarProductoNuevo(nuevo.getNombreProducto());
                } catch (RemoteException ex) {
                    success=false;
                }
                if(success)
                {
                    break;
                }
            }
            if(!success)
            {
                usuarios.remove(key);
            }
            
        }
    }
    
    public synchronized boolean agregaOferta(String comprador, String producto, float monto) throws RemoteException {
        if (productos.containsKey(producto)) {

            Producto infoProd;
            infoProd = (Producto) productos.get(producto);
            Oferta nueva=new Oferta(comprador, producto, monto);
            if (infoProd.actualizaPrecio(monto)) {
                ofertas.put(producto + comprador, nueva );
                mandarOfertaAClientes(nueva);
                return true;

            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void mandarOfertaAClientes(Oferta nuevo)
    {
        boolean success;
        ClienteInterface stub;
        for(String key: usuarios.keySet())
        {
            stub=usuarios.get(key);
            success=false;
            for(int i=0;i<2;i++)
            {
                try {
                    success=stub.mandarPrecioNuevo(nuevo.producto, nuevo.monto);
                } catch (RemoteException ex) {
                    success=false;
                }
                if(success)
                {
                    break;
                }
            }
            if(!success)
            {
                usuarios.remove(key);
            }
            
        }
    }
    
    
    public synchronized Vector<Producto> obtieneCatalogo() throws RemoteException {
        Vector<Producto> resultado;
        resultado = new Vector<Producto>(productos.values());
        return resultado;
    }

    public static void main(String[] args) {
        try {
            Tienda tienda = new Tienda();Thread actualizarClientes=new Thread();
            Agente stub = (Agente) UnicastRemoteObject.exportObject(tienda, 0);//Va 0 ahí? // creo que si
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Agente", stub);
            System.err.println("Server ready");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }    

}
