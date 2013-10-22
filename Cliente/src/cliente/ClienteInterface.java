/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cliente;

import java.util.Vector;

/**
 *
 * @author rachi_000
 */
public interface ClienteInterface extends java.rmi.Remote{
    
    public boolean mandarPrecioNuevo( double precio ) throws java.rmi.RemoteException;
    
}