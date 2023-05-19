package UI;

import Common.Constantes;
import Common.answerNotValidException;
import DAO.Productos;
import Domain.Mesa;
import Domain.Usuario;
import Service.GestionMesas;
import Service.GestionUsuarios;

import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

public class Gestor {
    static GestionUsuarios gestorUsuarios = new GestionUsuarios();

    private Gestor() {
    }

    public static void gestion() {
        //Usuario principal Carlos, contraseña 1234
        gestorUsuarios.cargarDatos();
        Scanner sc = new Scanner(System.in);
        int r = 0;
        boolean continuar;
        do {
            System.out.println("RESTAURANTE SUKINNY");
            if (gestorUsuarios.listarUsuarios() == null || gestorUsuarios.listarUsuarios().isEmpty()) {
                System.out.println("No hay usuarios registrados, crea el que será el admin");
                crearUsuario(true);
            } else {
                System.out.println(Constantes.usuarios);
                int contador = 1;
                for (Iterator<Usuario> iter = gestorUsuarios.listarUsuarios().iterator(); iter.hasNext(); ) {
                    System.out.println(contador + ". " + iter.next().toStringPantalla());
                    contador++;
                }
                System.out.print(Constantes.OPCION6);
                String user = sc.nextLine();
                System.out.print(Constantes.OPCION7);
                String contra = sc.nextLine();
                if (gestorUsuarios.existeUsuario(user)) {
                    if (gestorUsuarios.contrasenaCorrecta(user, contra)) {
                        System.out.println(Constantes.OPCION5);
                        System.out.println(Constantes.menuMesas);
                        if (gestorUsuarios.esAdmin(user)) {
                            System.out.println(Constantes.gestionUsuarios);
                            System.out.println("3." + Constantes.salir);
                        } else System.out.println("2." + Constantes.salir);
                        System.out.print(Constantes.OPCION4);
                        r = sc.nextInt();
                        sc.nextLine();
                        do {
                            continuar = true;
                            try {
                                if (gestorUsuarios.esAdmin(user)) {
                                    if (r < 1 || r > 4)
                                        throw new answerNotValidException(r);
                                } else if (r < 1 || r > 3)
                                    throw new answerNotValidException(r);
                            } catch (answerNotValidException ex1) {
                                continuar = false;
                                r = sc.nextInt();
                                sc.nextLine();
                            }
                        } while (!continuar);
                        if (r == 1) {
                            //gestor de mesas para admin
                            do {
                                menuMesas();
                                if (gestorUsuarios.esAdmin(user))
                                    menuMesasAdmin();
                                r = sc.nextInt();
                                sc.nextLine();
                                do {
                                    continuar = true;
                                    try {
                                        if (r < 1 || r > 5)
                                            throw new answerNotValidException(r);
                                    } catch (answerNotValidException ex1) {
                                        continuar = false;
                                        r = sc.nextInt();
                                        sc.nextLine();
                                    }
                                } while (!continuar);
                                GestionMesas gestorMesas = new GestionMesas();
                                switch (r) {
                                    case 1 -> {
                                        int n = 0;
                                        for (Iterator<Mesa> iter = gestorMesas.getMesas().getMesas().iterator(); iter.hasNext();)
                                            System.out.println(iter);
                                        do {
                                            System.out.println("Escriba la mesa que quiera abrir: ");
                                            n = sc.nextInt();
                                            continuar = true;
                                            try {
                                                if (!gestorMesas.existeMesa(n))
                                                    throw new answerNotValidException(n);
                                            } catch (answerNotValidException ex1) {
                                                continuar = false;
                                                r = sc.nextInt();
                                                sc.nextLine();
                                            }
                                        } while (!continuar);
                                        gestorMesas.cambiarEstado(n);
                                    }
                                    case 2 -> {
                                        int n;
                                        do {
                                            System.out.println("Escriba la mesa que quiera editar: ");
                                            n = sc.nextInt();
                                            continuar = true;
                                            try {
                                                if (!gestorMesas.existeMesa(n))
                                                    throw new answerNotValidException(r);
                                            } catch (answerNotValidException ex1) {
                                                continuar = false;
                                                r = sc.nextInt();
                                                sc.nextLine();
                                            }
                                        } while (!continuar);
                                        System.out.println("Que desea editar: \n 1. Numero de personas \n 2. Algun producto \n 3.Aplicar descuento \n 4.Añadir producto:  ");
                                        System.out.print(Constantes.OPCION4);
                                        int a = sc.nextInt();
                                        sc.nextLine();
                                        if (a == 1) {
                                            System.out.print("Escriba el nuevo numero de personas: ");
                                            int nPersonas = sc.nextInt();
                                            sc.nextLine();
                                            gestorMesas.cambiarPersonas(n, nPersonas);
                                        } else if (a == 2) {
                                            String producto = null;
                                            do {
                                                continuar = true;
                                                try {
                                                    System.out.print("Escriba el producto: ");
                                                    producto = sc.nextLine();
                                                    if (!gestorMesas.existeProducto(n, producto))
                                                        throw new InputMismatchException();
                                                } catch (InputMismatchException ex) {
                                                    System.out.println("Producto no encontrado, intentelo de nuevo");
                                                    continuar = false;
                                                }
                                            } while (!continuar);
                                            System.out.println("Escriba la nueva cantidad: ");
                                            int num = sc.nextInt();
                                            sc.nextLine();
                                            if (num == 0)
                                                gestorMesas.borrarProducto(n, producto);
                                            else gestorMesas.cambiarNProductos(n, producto, num);
                                        } else if (a == 3) {
                                            System.out.print("Cuanto descuento quiere ponerle: ");
                                            int des = 0;
                                            do {
                                                des = sc.nextInt();
                                                sc.nextLine();
                                            } while (des < 0 || des > 100);
                                            gestorMesas.descuento(n, des);
                                        } else {
                                            Productos productos = new Productos();
                                            for (int i = 0; i < productos.getProductos().size(); i++) {
                                                System.out.println(i + 1 + "." + productos.getProductos().get(i).toString());
                                            }
                                            System.out.print("Escriba el producto que desea: ");
                                            String producto = sc.nextLine();
                                            while (gestorMesas.existeProducto(producto)) {
                                                System.out.print("Producto no disponible, escriba otro: ");
                                                producto = sc.nextLine();
                                            }
                                            System.out.print("Escriba la cantidad: ");
                                            int cant = sc.nextInt();
                                            sc.nextLine();
                                            gestorMesas.anadirProducto(n, producto, cant);
                                        }
                                    }
                                    case 3 -> {
                                        int n = 0;
                                        do {
                                            System.out.println("Escriba la mesa que quiera cerrar: ");
                                            n = sc.nextInt();
                                            continuar = true;
                                            try {
                                                if (!gestorMesas.existeMesa(n))
                                                    throw new answerNotValidException(r);
                                            } catch (answerNotValidException ex1) {
                                                continuar = false;
                                                r = sc.nextInt();
                                                sc.nextLine();
                                            }
                                        } while (!continuar);
                                        gestorMesas.cambiarEstado(n);
                                        gestorMesas.borrarMesa(n);
                                    }
                                    case 4 -> {
                                        System.out.print("Que numero quiere darle a la nueva mesa: ");
                                        int num = sc.nextInt();
                                        sc.nextLine();
                                        while (gestorMesas.existeMesa(num)) {
                                            System.out.print("Esta mesa ya existe, escriba otro numero: ");
                                            num = sc.nextInt();
                                            sc.nextLine();
                                        }
                                        gestorMesas.crearMesas(num);
                                    }
                                    case 5 -> {
                                        System.out.print("Que numero de mesa quiere eliminar: ");
                                        int num = sc.nextInt();
                                        sc.nextLine();
                                        while (!gestorMesas.existeMesa(num)) {
                                            System.out.print("Esta mesa no existe, escriba otro numero: ");
                                            num = sc.nextInt();
                                            sc.nextLine();
                                        }
                                        gestorMesas.eliminarMesa(num);
                                    }
                                }
                            } while (r != 6);
                        } else if (r == 2) {
                            //gestor de usuarios
                            do {
                                if (gestorUsuarios.esAdmin(user)) {
                                    menuAdmin();
                                    r = sc.nextInt();
                                    sc.nextLine();
                                    do {
                                        continuar = true;
                                        try {
                                            if (r < 1 || r > 6)
                                                throw new answerNotValidException(r);
                                        } catch (answerNotValidException ex1) {
                                            continuar = false;
                                            r = sc.nextInt();
                                            sc.nextLine();
                                        }
                                    } while (!continuar);
                                    String usuario;
                                    switch (r) {
                                        case 1 -> {
                                            System.out.print(Constantes.admin);
                                            String admin = sc.nextLine();
                                            do {
                                                continuar = true;
                                                try {
                                                    if (admin.compareToIgnoreCase("si") != 0 && admin.compareToIgnoreCase("no") != 0)
                                                        throw new answerNotValidException(admin);
                                                } catch (answerNotValidException ex1) {
                                                    continuar = false;
                                                    admin = sc.nextLine();
                                                }
                                            } while (!continuar);
                                            if (admin.compareToIgnoreCase("si") == 0) {
                                                if (crearUsuario(true))
                                                    System.out.println(Constantes.creado);
                                                else System.out.println(Constantes.noCreado);
                                            } else if (crearUsuario(false))
                                                System.out.println(Constantes.creado);
                                            else System.out.println(Constantes.noCreado);
                                        }
                                        case 2 -> {
                                            System.out.print(Constantes.eliminar);
                                            usuario = sc.nextLine();
                                            if (!gestorUsuarios.borrarUsuario(usuario)) {
                                                System.out.println(Constantes.noEncontrado);
                                            } else System.out.println(Constantes.eliminado);
                                        }
                                        case 3 -> {
                                            System.out.print(Constantes.OPCION6);
                                            usuario = sc.nextLine();
                                            while (!gestorUsuarios.existeUsuario(usuario)) {
                                                System.out.println(Constantes.noEncontrado);
                                                usuario = sc.nextLine();
                                            }
                                            System.out.println(Constantes.modificar);
                                            System.out.print(Constantes.OPCION4);
                                            r = sc.nextInt();
                                            sc.nextLine();
                                            switch (r) {
                                                case 1 -> {
                                                    System.out.print(Constantes.nombre);
                                                    String nombre = sc.nextLine();
                                                    while (gestorUsuarios.existeUsuario(nombre)) {
                                                        System.out.println(Constantes.noCreado);
                                                        nombre = sc.nextLine();
                                                    }
                                                    gestorUsuarios.cambiarNombre(usuario, nombre);
                                                }
                                                case 2 -> {
                                                    System.out.print(Constantes.cambiaContra);
                                                    String nuevaContra = sc.nextLine();
                                                    gestorUsuarios.cambiaContra(usuario, nuevaContra);
                                                }
                                                case 3 -> {
                                                    if (gestorUsuarios.esAdmin(usuario)) {
                                                        System.out.print(Constantes.quitarPermisos);
                                                        String respuesta = sc.nextLine();
                                                        do {
                                                            continuar = true;
                                                            try {
                                                                if (respuesta.compareToIgnoreCase("si") != 0 && respuesta.compareToIgnoreCase("no") != 0)
                                                                    throw new answerNotValidException(respuesta);
                                                            } catch (answerNotValidException ex1) {
                                                                continuar = false;
                                                                respuesta = sc.nextLine();
                                                            }
                                                        } while (!continuar);
                                                        gestorUsuarios.cambiarPermisos(usuario, false);
                                                    } else {
                                                        System.out.print(Constantes.darPermisos);
                                                        String respuesta = sc.nextLine();
                                                        do {
                                                            continuar = true;
                                                            try {
                                                                if (respuesta.compareToIgnoreCase("si") != 0 && respuesta.compareToIgnoreCase("no") != 0)
                                                                    throw new answerNotValidException(respuesta);
                                                            } catch (answerNotValidException ex1) {
                                                                continuar = false;
                                                                respuesta = sc.nextLine();
                                                            }
                                                        } while (!continuar);
                                                        gestorUsuarios.cambiarPermisos(usuario, true);
                                                    }
                                                }
                                            }
                                        }
                                        case 4 -> {
                                            System.out.print(Constantes.estaSeguro);
                                            String respuesta = sc.nextLine();
                                            do {
                                                continuar = true;
                                                try {
                                                    if (respuesta.compareToIgnoreCase("si") != 0 && respuesta.compareToIgnoreCase("no") != 0)
                                                        throw new answerNotValidException(respuesta);
                                                } catch (answerNotValidException ex1) {
                                                    continuar = false;
                                                    respuesta = sc.nextLine();
                                                }
                                            } while (!continuar);
                                            if (respuesta.compareToIgnoreCase("si") == 0)
                                                if (gestorUsuarios.borrarUsuarios())
                                                    System.out.println(Constantes.usuariosBorrados);
                                        }
                                        case 5 -> {
                                            System.out.println("Desea ascendente o descendente: ");
                                            String respuesta = sc.nextLine();
                                            System.out.println(Constantes.usuarios);
                                            if (respuesta.compareToIgnoreCase("ascendente") == 0)
                                                for (int i = 0; i < gestorUsuarios.listarUsuarios().size(); i++) {
                                                    System.out.println(i + 1 + ". " + gestorUsuarios.listarUsuariosOrdenados(true).get(i).toStringPantalla());
                                                }
                                            else for (int i = 0; i < gestorUsuarios.listarUsuarios().size(); i++) {
                                                System.out.println(i + 1 + ". " + gestorUsuarios.listarUsuariosOrdenados(false).get(i).toStringPantalla());
                                            }

                                        }
                                    }
                                }
                            } while (r != 6 && r != 4);
                        }
                    } else System.out.println(Constantes.incorrecto);
                } else System.out.println(Constantes.noEncontrado);
            }
        } while (r != 3);
        System.out.println(Constantes.FIN);
    }

    private static void menuMesasAdmin() {
    }

    private static void menuMesas() {
        System.out.println(Constantes.menuMesas1);
        System.out.println(Constantes.menuMesas2);
        System.out.println(Constantes.menuMesas3);
        System.out.println(Constantes.menuMesas4);
        System.out.println(Constantes.menuMesas5);
        System.out.println("6. " + Constantes.salir);
        System.out.print(Constantes.OPCION4);
    }

    private static void menuAdmin() {
        System.out.println(Constantes.gestionUsuarios1);
        System.out.println(Constantes.gestionUsuarios2);
        System.out.println(Constantes.gestionUsuarios3);
        System.out.println(Constantes.gestionUsuarios4);
        System.out.println(Constantes.gestionUsuarios5);
        System.out.println("6. " + Constantes.salir);
        System.out.print(Constantes.OPCION4);
    }

    private static void gestionMesa() {
        System.out.println(Constantes.menuMesas);
        System.out.println(Constantes.gestionMesas1);
        System.out.println(Constantes.gestionMesas2);
        System.out.println(Constantes.gestionMesas3);
    }

    private static boolean crearUsuario(boolean admin) {
        Scanner sc = new Scanner(System.in);
        System.out.print(Constantes.user);
        String nombre = sc.nextLine();
        System.out.print(Constantes.contra);
        String contra = sc.nextLine();
        return gestorUsuarios.crearUsuario(nombre, contra, admin);
    }
}
