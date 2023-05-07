package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

public interface VOReserva {
    public long getId();

    public long getIdResidente();

    public Timestamp getFecha();

    public int getDuracionDias();

    public int getActivo();
}
