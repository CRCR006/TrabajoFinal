package Service;

import DAO.DaoFicheros;
import DAO.Mesas;
import DAO.Productos;

public class GestionMesas {
    Mesas mesas;
    public GestionMesas(){
        if(DaoFicheros.cargarMesas()!=null)
            mesas= new Mesas(DaoFicheros.cargarMesas());
        else mesas=new Mesas();
    }

    public Mesas getMesas() {
        return mesas;
    }

    public boolean existeMesa(int n) {
        return mesas.existeMesa(n);
    }

    public void cambiarEstado(int n){
        mesas.cambiarEstadoMesa(n);
    }

    public void borrarMesa(int n) {
        mesas.borrarMesa(n);
    }

    public void cambiarPersonas(int n, int nPersonas) {
        mesas.cambiarPersonas(n,nPersonas);
    }

    public boolean existeProducto(int n, String producto) {
        return mesas.existeProducto(n,producto);
    }
    public boolean existeProducto(String producto) {
        Productos productos=new Productos();
        return productos.existeProducto(producto);
    }

    public void borrarProducto(int n, String producto) {
        mesas.borrarProducto( n,  producto);
    }

    public void cambiarNProductos(int n, String producto, int num) {
        mesas.cambiarNProductos( n,  producto,  num);
    }

    public void descuento(int n, int des) {
        mesas.descuento(n,des);
    }

    public void anadirProducto(int n, String producto, int cant) {
        mesas.cambiarNProductos( n,  producto,  cant);
    }

    public void crearMesas(int num) {
        mesas.crearMesas(num);
    }

    public void eliminarMesa(int num) {
        mesas.eliminarMesa(num);
    }
}
