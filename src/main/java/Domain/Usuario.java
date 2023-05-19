package Domain;

public class Usuario implements Comparable<Usuario>{
    private String nombre;
    private String contra;
    private boolean admin;
    public Usuario(String nombre,String contra,boolean admin){
        this.admin=admin;
        this.contra=contra;
        this.nombre=nombre;
    }
    public Usuario(){}
    public String getContra() {
        return contra;
    }

    public String getNombre() {
        return nombre;
    }
    public boolean getAdmin() {
        return admin;
    }
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String toStringPantalla() {
        if(admin){
            return nombre+" admin";
        }else return nombre+" no admin";
    }
    @Override
    public int compareTo(Usuario otroUsuario) {
        return this.nombre.compareTo(otroUsuario.nombre);
    }
}
