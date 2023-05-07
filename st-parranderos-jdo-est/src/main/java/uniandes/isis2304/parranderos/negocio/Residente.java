package uniandes.isis2304.parranderos.negocio;

public class Residente implements VOResidente {

    private long id;
    private String nombre;
    private String vinculo;

    public Residente() {

        this.id = 0;
        this.nombre = "";
        this.vinculo = "";

    }

    public Residente(long id, String nombre, String vinculo) {
        this.id = id;
        this.nombre = nombre;
        this.vinculo = vinculo;
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

    public String getVinculo() {
        return vinculo;
    }

    public void setVinculo(String vinculo) {
        this.vinculo = vinculo;
    }

    @Override
    public String toString() {
        return "Residente [id=" + id + ", nombre=" + nombre + ", vinculo=" + vinculo + "]";
    }

}
