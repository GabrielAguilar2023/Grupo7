package tpi144g7.prode.clase;

public class Equipo {
   public String nombre;
   private String descripcion;

    public Equipo(String nombre) {
        this.nombre = nombre;
        this.descripcion ="Seleccion";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
