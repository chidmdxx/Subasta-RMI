package datos;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Me
 */
public class Oferta implements java.io.Serializable{
    String comprador;
    public String producto;
    public float monto;
    
    public Oferta( String c, String p, float m ) {
        comprador = c;
        producto = p;
        monto = m;
   }
}
