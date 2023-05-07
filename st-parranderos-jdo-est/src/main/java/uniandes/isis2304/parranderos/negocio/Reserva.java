package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

public class Reserva implements VOReserva {
    // Atributos
    // id, idresidente, fecha, duracionDias
    private long id;
    private long idResidente;
    private Timestamp fecha;
    private int duracion_dias;
    private int activo;

    public Reserva (){

        this.id = 0;
        this.idResidente = 0;
        this.fecha = null;
        this.duracion_dias = 0;
        this.activo = 1;

    }

    public Reserva(long id, long idResidente, Timestamp fecha, int duracionDias) {
        this.id = id;
        this.idResidente = idResidente;
        this.fecha = fecha;
        this.duracion_dias = duracionDias;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdResidente() {
        return idResidente;
    }

    public void setIdResidente(long idResidente) {
        this.idResidente = idResidente;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public int getDuracionDias() {
        return duracion_dias;
    }

    public void setDuracion_dias(int duracion_dias) {
        this.duracion_dias = duracion_dias;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo){
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Reserva [id=" + id + ", idResidente=" + idResidente + ", fecha=" + fecha + ", duracionDias="
                + duracion_dias + "]";
    }

}
