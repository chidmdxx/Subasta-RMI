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
public class Tienda implements Agente {

    /**
     * @param args the command line arguments
     */
    Hashtable usuarios;
    Hashtable productos;
    Hashtable ofertas;

    public Tienda() {
        usuarios = new Hashtable();
        productos = new Hashtable();
        ofertas = new Hashtable();
    }

    public boolean registraUsuario(String nombre) throws RemoteException {
        if (!usuarios.containsKey(nombre)) {
            System.out.println("Agregando un nuevo usuario: " + nombre);
            usuarios.put(nombre, nombre);
            return true;

        } else {
            return false;
        }
    }

    public boolean agregaProductoALaVenta(String vendedor, String producto, float precioInicial) throws RemoteException {
        if (!productos.containsKey(producto)) {

            System.out.println("Agregando un nuevo producto: " + producto);
            productos.put(producto,
                    new Producto(vendedor,
                    producto,
                    precioInicial));
            return true;

        } else {
            return false;
        }
    }

    public boolean agregaOferta(String comprador, String producto, float monto) throws RemoteException {
        if (productos.containsKey(producto)) {

            Producto infoProd;
            infoProd = (Producto) productos.get(producto);

            if (infoProd.actualizaPrecio(monto)) {
                ofertas.put(producto + comprador,
                        new Oferta(comprador,
                        producto,
                        monto));
                return true;

            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public Vector<Producto> obtieneCatalogo() throws RemoteException {
        Vector<Producto> resultado;
        resultado = new Vector<Producto>(productos.values());
        return resultado;
    }

    public static void main(String[] args) {
        try {
            Tienda tienda = new Tienda();
            Agente stub = (Agente) UnicastRemoteObject.exportObject(tienda, 0);//Va 0 ahí?
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Agente", stub);
            System.err.println("Server ready");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
