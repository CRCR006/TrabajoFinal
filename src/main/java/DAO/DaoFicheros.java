package DAO;

import Domain.Mesa;
import Domain.Producto;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Domain.Usuario;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import java.io.*;
import java.util.*;

public class DaoFicheros {

    private static String ficheroUsuarios = "usuarios.json";
    private static String ficheroProductos = "productos.txt";
    private static String ficheroMesas = "mesas.txt";

    private static String ficheroReservas = "reservas";

    public static void registrarUsuario(HashSet<Usuario> usuarios) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(new File(ficheroUsuarios), usuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Usuarios leerUsuarios() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        File fichero = new File(ficheroUsuarios);
        try {
            Usuario[] usuariosArray = objectMapper.readValue(fichero, Usuario[].class);
            List<Usuario> usuarios = Arrays.asList(usuariosArray);
            return new Usuarios(new HashSet<>(usuarios));
        } catch (MismatchedInputException e) {
            return new Usuarios(new HashSet<>());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean limpiezaUsuarios() {
        FileWriter fw;
        try {
            fw = new FileWriter(ficheroUsuarios, false);
            fw.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static void escribirFicheroMesas(HashSet<Mesa>mesas) {
        try (FileWriter fileWriter = new FileWriter(ficheroMesas);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            for (Mesa mesa : mesas) {
                bufferedWriter.write("NumeroMesa:" + mesa.getNumero());
                bufferedWriter.newLine();
                bufferedWriter.write("NumeroPersonas:" + mesa.getnPersonas());
                bufferedWriter.newLine();
                bufferedWriter.write("DineroTotal:" + mesa.getDinero());
                bufferedWriter.newLine();
                bufferedWriter.write("Cuenta:");
                bufferedWriter.newLine();
                Map<Producto, Integer> cuenta = mesa.getProductos();
                for (Map.Entry<Producto, Integer> entry : cuenta.entrySet()) {
                    Producto producto = entry.getKey();
                    int cantidad = entry.getValue();
                    bufferedWriter.write(producto.toString() + ":" + cantidad);
                    bufferedWriter.newLine();
                }
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<Mesa> cargarMesas(){
        ArrayList<Mesa> mesas=null;
        File file = new File(ficheroMesas);
        Scanner sc;
        try {
            sc = new Scanner(file);
        }catch (FileNotFoundException ex){
            return null;
        }
        mesas = new ArrayList<>();
        Mesa mesa = null;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();

            if (line.startsWith("NumeroMesa:")) {
                int numeroMesa = Integer.parseInt(line.substring("NumeroMesa:".length()));
                mesa = new Mesa();
                mesa.setNumero(numeroMesa);
            } else if (line.startsWith("NumeroPersonas:")) {
                int numeroPersonas = Integer.parseInt(line.substring("NumeroPersonas:".length()));
                if (mesa != null) {
                    mesa.setnPersonas(numeroPersonas);
                }
            } else if (line.startsWith("DineroTotal:")) {
                double dineroTotal = Double.parseDouble(line.substring("DineroTotal:".length()));
                if (mesa != null) {
                    mesa.setDinero(dineroTotal);
                }
            } else if (line.equals("Cuenta:")) {
                if (mesa != null) {
                    HashMap<Producto, Integer> cuenta = new HashMap<>();
                    while (sc.hasNextLine()) {
                        line = sc.nextLine();
                        if (line.isEmpty()) {
                            break;
                        }
                        String[] parts = line.split(":");
                        Productos productos=new Productos();
                        if (parts.length == 2) {
                            Producto producto = productos.getProducto(parts[0]);
                            int cantidad = Integer.parseInt(parts[1]);
                            cuenta.put(producto, cantidad);
                        }
                    }
                    mesa.setCuenta(cuenta);
                }
            } else if (line.isEmpty() && mesa != null) {
                mesas.add(mesa);
                mesa = null;
            }
        }
        sc.close();
        return mesas;
    }


    /**public static void escribirFicheroBinario(Productos productos) {
        try {
            FileOutputStream archivo = new FileOutputStream(ficheroProductos);
            DataOutputStream out = new DataOutputStream(archivo);
            out.writeUTF();
            out.writeInt(intentos);
            out.close();
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo.");
        }
    }*/

    public static ArrayList<Producto> leerProductos() {
        ArrayList<Producto> productos = new ArrayList<>();
        Scanner sc = new Scanner(ficheroProductos);
        while (sc.hasNextLine()) {
            String linea = sc.nextLine();
            String[] datosProducto = linea.split(";");
            if (datosProducto.length == 3) {
                int id = Integer.parseInt(datosProducto[0]);
                String nombre = datosProducto[1];
                double precio = Double.parseDouble(datosProducto[2]);

                Producto producto = new Producto(id, nombre, precio);
                productos.add(producto);
            }
        }
        sc.close();
        return productos;
    }
}
