package uniandes.isis2304.parranderos.negocio;

public class Residen implements VOResiden {

    private long idReserva;
    private long idOferta;

    public Residen() {

        this.idReserva = 0;
        this.idOferta = 0;

    }

    public Residen(long idReserva, long idOferta) {
        this.idReserva = idReserva;
        this.idOferta = idOferta;
    }

    public long getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(long idReserva) {
        this.idReserva = idReserva;
    }

    public long getIdOferta() {
        return idOferta;
    }

    public void setIdOferta(long idOferta) {
        this.idOferta = idOferta;
    }

    @Override
    public String toString() {
        return "Residen [idReserva=" + idReserva + ", idOferta=" + idOferta + "]";
    }

}
