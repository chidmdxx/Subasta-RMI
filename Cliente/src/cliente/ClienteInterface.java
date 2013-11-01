package cliente;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Vector; 
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author rachi_000
 */
public interface ClienteInterface extends Remote{
    
    boolean mandarPrecioNuevo( String producto, float nuevoPrecio ) throws RemoteException;
    boolean mandarProductoNuevo(String producto) throws RemoteException;
}