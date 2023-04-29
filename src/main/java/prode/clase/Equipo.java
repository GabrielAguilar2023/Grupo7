package prode.clase;

public class Equipo {
   private String nombre;
   private String descripcion;

//****** CONSTRUCTOR *******
    public Equipo(String nombre) {
        this.nombre = nombre;
        this.descripcion ="Seleccion Nacional";
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

}
