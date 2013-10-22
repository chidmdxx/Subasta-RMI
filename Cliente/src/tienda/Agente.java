/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tienda;

import java.util.Vector;
import datos.Producto;

/**
 *
 * @author Me
 */
public interface Agente extends java.rmi.Remote{
    
    public boolean registraUsuario( String nombre ) throws java.rmi.RemoteException;
    public boolean agregaProductoALaVenta( String vendedor, String producto,float precioInicial )throws java.rmi.RemoteException;
    public boolean agregaOferta(String comprador, String producto, float monto)throws java.rmi.RemoteException;
    public Vector<Producto> obtieneCatalogo() throws java.rmi.RemoteException;
}
