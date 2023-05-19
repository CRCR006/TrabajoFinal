package Domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class Mesa implements Serializable {

    private int numero, nPersonas;
    private double dinero = 0;
    private Map<Producto, Integer> productos;
    private boolean abierta = false;

    public Mesa(int numero) {
        this.numero = numero;
        this.productos = new HashMap<>();
        abierta = false;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mesa mesa = (Mesa) o;
        return nPersonas == mesa.nPersonas &&
                Double.compare(mesa.dinero, dinero) == 0 &&
                numero == mesa.numero &&
                Objects.equals(productos, mesa.getProductos());
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero, nPersonas, dinero, productos, abierta);
    }
    public Mesa() {
    }
    public Map<Producto, Integer> getProductos() {
        return productos;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getnPersonas() {
        return nPersonas;
    }

    public void setnPersonas(int nPersonas) {
        this.nPersonas = nPersonas;
    }

    public boolean isAbierta() {
        return abierta;
    }

    public void setAbierta(boolean abierta) {
        this.abierta = abierta;
    }

    public String toString() {
        return ("Mesa nº" + numero + ", nºPersonas: " + nPersonas + ", con una cuenta de " + dinero + "$");
    }

    public double getDinero() {
        double aux=0;
        for (Iterator<Producto>iter=productos.keySet().iterator(); iter.hasNext();) {
            aux=aux+iter.next().getPrecio();
        }
        return aux;
    }

    public void setDinero(double dineroTotal) {
        dinero = dineroTotal;
    }

    public void setCuenta(HashMap<Producto, Integer> cuenta) {
        productos = cuenta;
    }

}
