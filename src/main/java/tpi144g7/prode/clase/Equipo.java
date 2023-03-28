package tpi144g7.prode.clase;

public class Equipo {
   public String nombre;
   public String descripcion;

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
