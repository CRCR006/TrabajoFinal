package DAO;

import Domain.Producto;

import java.util.ArrayList;

public class Productos {
    ArrayList<Producto> productos;
    public Productos(){
        productos=DaoFicheros.leerProductos();
    }
    public Producto getProducto(String nombre){
        for (int i = 0; i < productos.size(); i++) {
            if(productos.get(i).getNombre().compareToIgnoreCase(nombre)==0)
                return productos.get(i);
        }
        return null;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public boolean existeProducto(String producto) {
        for (int i = 0; i < productos.size(); i++) {
            if(productos.get(i).getNombre().compareToIgnoreCase(producto)==0)
                return true;
        }return false;
    }
}
