/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tienda;

/**
 *
 * @author Me
 */
public class Oferta implements java.io.Serializable{
    String comprador;
    String producto;
    float monto;
    
    public Oferta( String c, String p, float m ) {
        comprador = c;
        producto = p;
        monto = m;
   }
}
