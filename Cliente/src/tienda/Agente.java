package tienda;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import datos.Producto;
import java.util.Vector;
//import datos.Producto;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Me
 */
public interface Agente extends Remote{
    
    boolean registraUsuario( String nombre ) throws RemoteException;
    boolean agregaProductoALaVenta( String vendedor, String producto,float precioInicial )throws RemoteException;
    boolean agregaOferta(String comprador, String producto, float monto)throws RemoteException;
    Vector<Producto> obtieneCatalogo() throws RemoteException;
}
