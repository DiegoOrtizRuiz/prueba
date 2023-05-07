package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

public class Oferta implements VOOferta {
    // Atributos
    // id, idalojamiento, idoperador, fecha, costo, tiempo_limite_dias
    private long id;
    private long idAlojamiento;
    private long idOperador;
    private Timestamp fecha;
    private double costo;
    private int tiempoLimiteDias;
    private double indiceOcupacion;
    private int reservada;

    public Oferta() {
        this.id = 0;
        this.idAlojamiento = 0;
        this.idOperador = 0;
        this.fecha = null;
        this.costo = 0;
        this.tiempoLimiteDias = 0;
        this.reservada = 0;
    }

    public Oferta(long id, long idAlojamiento, long idOperador, Timestamp fecha, double costo, int tiempoLimiteDias) {
        this.id = id;
        this.idAlojamiento = idAlojamiento;
        this.idOperador = idOperador;
        this.fecha = fecha;
        this.costo = costo;
        this.tiempoLimiteDias = tiempoLimiteDias;
        this.reservada = 0;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdAlojamiento() {
        return idAlojamiento;
    }

    public void setIdAlojamiento(long idAlojamiento) {
        this.idAlojamiento = idAlojamiento;
    }

    public long getIdOperador() {
        return idOperador;
    }

    public void setIdOperador(long idOperador) {
        this.idOperador = idOperador;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public int getTiempoLimiteDias() {
        return tiempoLimiteDias;
    }

    public void setTiempoLimiteDias(int tiempoLimiteDias) {
        this.tiempoLimiteDias = tiempoLimiteDias;
    }

    @Override
    public String toString() {
        return "Oferta [id=" + id + ", idAlojamiento=" + idAlojamiento + ", idOperador=" + idOperador + ", fecha="
                + fecha + ", costo=" + costo + ", tiempoLimiteDias=" + tiempoLimiteDias + "]";
    }

    public double getIndiceOcupacion() {
        return indiceOcupacion;
    }

    public void setIndiceOcupacion(double indiceOcupacion) {
        this.indiceOcupacion = indiceOcupacion;
    }

    public int getReservada() {
        return reservada;
    }

    public void setReservada(int reservada) {
        this.reservada = reservada;
    }
}
