package uniandes.isis2304.parranderos.negocio;

public class Servicio implements VOServicio {
    // id, nombre, costo
    private long id;
    private String nombre;
    private double costo;

    public Servicio() {

        this.id = 0;
        this.nombre = "";
        this.costo = 0;

    }

    public Servicio(long id, String nombre, double costo) {
        this.id = id;
        this.nombre = nombre;
        this.costo = costo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    @Override
    public String toString() {
        return "Servicio [id=" + id + ", nombre=" + nombre + ", costo=" + costo + "]";
    }

}
