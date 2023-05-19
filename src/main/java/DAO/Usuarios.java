package DAO;
import Domain.Usuario;
import java.util.HashSet;

public class Usuarios {
    private HashSet<Usuario> usuarios;

    public Usuarios(HashSet<Usuario> aux) {
        usuarios = aux;
    }

    public Usuarios() {
        usuarios = new HashSet<>();
    }

    public boolean existeUsuario(String user) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombre().compareTo(user) == 0) {
                return true;
            }
        }
        return false;
    }

    public boolean contrasenaCorrecta(String user, String contra) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombre().compareToIgnoreCase(user) == 0) {
                if (usuario.getContra().compareTo(contra) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public void anadirUsuario(String nombre, String contra, boolean admin) {
        usuarios.add(new Usuario(nombre, contra, admin));
        DaoFicheros.registrarUsuario(usuarios);
    }

    public Usuario getUsuario(String user) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombre().compareToIgnoreCase(user) == 0) {
                return usuario;
            }
        }
        return null;
    }

    public boolean isEmpty() {
        return usuarios.isEmpty();
    }

    public HashSet<Usuario> listarUsuarios() {
        return usuarios;
    }

    public void borrarUsuario(String usuario) {
        for (Usuario u : usuarios) {
            if (u.getNombre().compareTo(usuario) == 0) {
                usuarios.remove(u);
                break;
            }
        }
        DaoFicheros.registrarUsuario(usuarios);
    }

    public void cambiarNombre(String usuario, String nombre) {
        for (Usuario u : usuarios) {
            if (u.getNombre().compareTo(usuario) == 0) {
                u.setNombre(nombre);
                break;
            }
        }
        DaoFicheros.registrarUsuario(usuarios);
    }

    public void cambiaContra(String usuario, String nuevaContra) {
        for (Usuario u : usuarios) {
            if (u.getNombre().compareTo(usuario) == 0) {
                u.setContra(nuevaContra);
                break;
            }
        }
        DaoFicheros.registrarUsuario(usuarios);
    }

    public void cambiarPermisos(String usuario, boolean b) {
        for (Usuario u : usuarios) {
            if (u.getNombre().compareTo(usuario) == 0) {
                u.setAdmin(b);
                break;
            }
        }
        DaoFicheros.registrarUsuario(usuarios);
    }

    public boolean borrarUsuarios() {
        usuarios.clear();
        return DaoFicheros.limpiezaUsuarios();
    }
}

