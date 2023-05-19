package Service;

import Common.Comparador;
import DAO.DaoFicheros;
import DAO.Usuarios;
import Domain.Usuario;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

public class GestionUsuarios {
    private Usuarios usuarios;
    public GestionUsuarios(){
        usuarios=new Usuarios();
    }

    public boolean existeUsuario(String user) {return usuarios.existeUsuario(user);}

    public boolean contrasenaCorrecta(String user, String contra) {
        return usuarios.contrasenaCorrecta(user,contra);
    }

    public boolean crearUsuario(String nombre, String contra, boolean admin) {
        if(!existeUsuario(nombre)){
            usuarios.anadirUsuario(nombre,contra,admin);
            return true;
        }return false;
    }

    public Usuario getUsuario(String user) {
        return usuarios.getUsuario(user);
    }

    public HashSet<Usuario> listarUsuarios() {
       return usuarios.listarUsuarios();
    }

    public boolean esAdmin(String user) {return usuarios.getUsuario(user).getAdmin();}

    public boolean borrarUsuario(String usuario) {
        if(existeUsuario(usuario)){
            usuarios.borrarUsuario(usuario);
            return true;
        }else return false;
    }

    public void cambiarNombre(String usuario, String nombre) {
        usuarios.cambiarNombre(usuario,nombre);
    }

    public void cambiaContra(String usuario, String nuevaContra) {
        usuarios.cambiaContra(usuario,nuevaContra);
    }

    public void cambiarPermisos(String usuario, boolean b) {
        usuarios.cambiarPermisos(usuario,b);
    }

    public boolean borrarUsuarios() {
        return usuarios.borrarUsuarios();
    }

    public void cargarDatos() {
        if(DaoFicheros.leerUsuarios()!=null)
            usuarios=DaoFicheros.leerUsuarios();
    }

    public ArrayList<Usuario> listarUsuariosOrdenados(boolean ascendente) {
        return (ArrayList<Usuario>) usuarios.listarUsuarios()
                .stream()
                .filter(Usuario.class::isInstance)
                .map(Usuario.class::cast)
                .sorted(ascendente ? new Comparador() : new Comparador().reversed())
                .collect(Collectors.toList());
    }
}
