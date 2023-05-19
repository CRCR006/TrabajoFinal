package DAO;
import Domain.Mesa;
import Domain.Producto;

import java.util.ArrayList;
import java.util.HashSet;

public class Mesas {
    private ArrayList<Mesa> mesas;

    public Mesas(ArrayList<Mesa> mesas) {
        this.mesas = mesas;
    }
    public Mesas(){
        mesas=new ArrayList<>();
    }
    public void eliminarMesa(int mesa) {
        mesas.remove(mesa);
    }
    public boolean existeMesa(int n) {
        for (Mesa mesa : mesas) {
            if (mesa.getNumero() == n)
                return true;
        }
        return false;
    }

    public void cambiarEstadoMesa(int n) {
        for (Mesa mesa : mesas) {
            if (mesa.getNumero() == n)
                mesa.setAbierta(!mesa.isAbierta());
        }
    }

    public void borrarMesa(int n) {
        for (Mesa mesa : mesas)
            if (mesa.getNumero() == n)
                mesa = new Mesa(n);
    }

    public void cambiarPersonas(int n, int nPersonas) {
        for (Mesa mesa : mesas) {
            if (mesa.getNumero() == n) {
                mesa.setnPersonas(nPersonas);
            }
        }
    }

    public boolean existeProducto(int n, String producto) {
        Productos productos=new Productos();
        Producto producto1=productos.getProducto(producto);
        for (int i=0;i<mesas.size();i++) {
            if (mesas.get(i).getNumero() == n) {
                for(int j=0;j<mesas.get(i).getProductos().size();j++){
                    if(mesas.get(i).getProductos().containsKey(producto1))
                        return true;
                }
            }
        }return false;
    }

    public void borrarProducto(int n, String producto) {
        Productos productos=new Productos();
        Producto producto1=productos.getProducto(producto);
        for (int i=0;i<mesas.size();i++) {
            if (mesas.get(i).getNumero() == n) {
                    mesas.get(i).getProductos().remove(producto1);
            }
        }
    }

    public void cambiarNProductos(int n, String producto, int num) {
        Productos productos=new Productos();
        Producto producto1=productos.getProducto(producto);
        for (int i=0;i<mesas.size();i++) {
            if (mesas.get(i).getNumero() == n) {
                mesas.get(i).getProductos().put(producto1,num);
            }
        }
    }

    public void descuento(int n, double des) {
        for (int i=0;i<mesas.size();i++)
            if (mesas.get(i).getNumero() == n)
                mesas.get(i).setDinero(mesas.get(i).getDinero()-(mesas.get(i).getDinero()*(des/100)));
    }

    public void crearMesas(int num) {
        mesas.add(new Mesa(num));
    }

    public ArrayList<Mesa> getMesas() {
        return mesas;
    }

}
