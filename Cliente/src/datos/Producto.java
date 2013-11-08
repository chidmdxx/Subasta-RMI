package datos;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Me
 */
public class Producto implements java.io.Serializable{
    String vendedor;
    String producto;
    float precioInicial;
    public float precioActual;
    
    /*Constructor*/
    public Producto( String v, String p, float pi ) {
        vendedor = v;
        producto = p;
        precioInicial = pi;
        precioActual = pi;
    }
    
    public boolean actualizaPrecio( float monto ) {

        if( monto > precioActual ) {
            precioActual = monto;
            return true;
        } else
            return false;
    }
    
    public String getNombreProducto() {

        return producto;
    }

    public float getPrecioActual() {

        return precioActual;
    }  
}
