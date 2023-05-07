package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

public interface VOOferta {

    /**
     * @return
     */
    public long getId();

    public long getIdAlojamiento();

    public long getIdOperador();

    public Timestamp getFecha();

    public double getCosto();

    public int getTiempoLimiteDias();

    public double getIndiceOcupacion();

    public String toString();

    public int getReservada();

}
