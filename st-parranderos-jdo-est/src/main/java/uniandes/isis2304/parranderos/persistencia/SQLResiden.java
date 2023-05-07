package uniandes.isis2304.parranderos.persistencia;

import java.math.BigDecimal;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Residen;

public class SQLResiden {

    private final static String SQL = PersistenciaAlohandes.SQL;

    /*
     * ****************************************************************
     * Atributos
     *****************************************************************/
    /**
     * El manejador de persistencia general de la aplicación
     */
    private PersistenciaAlohandes pp;

    /*
     * ****************************************************************
     * Métodos
     *****************************************************************/

    /**
     * Constructor
     * 
     * @param pp - El Manejador de persistencia de la aplicación
     */
    public SQLResiden(PersistenciaAlohandes pp) {
        this.pp = pp;
    }


    public long adicionarResiden(PersistenceManager pm, long idReserva, long idOferta) {
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaResiden() + "(idreserva, idoferta) values (?, ?)");
        q.setParameters(idReserva, idOferta);
        Query q2 = pm.newQuery(SQL, "UPDATE " + pp.darTablaOferta() + " SET reservada = 1 WHERE id = ?");
        q2.setParameters(idOferta);
        q2.executeUnique();
        Query q3 = pm.newQuery(SQL, "SELECT idAlojamiento FROM " + pp.darTablaOferta() + " WHERE id = ?");
        q3.setParameters(idOferta);
        long idAlojameinto =  ((BigDecimal) q3.executeUnique()).longValue();
        Query q4 = pm.newQuery(SQL, "UPDATE " + pp.darTablaAlojamiento() + " SET ocupado = 1 WHERE id = ?");
        q4.setParameters(idAlojameinto);
        q4.executeUnique();
    
        return (long) q.executeUnique();
    }

    public long eliminarResidenPorId(PersistenceManager pm, long idReserva, long idOferta) {
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaResiden() + " WHERE idreserva = ? AND idoferta = ?");
        q.setParameters(idReserva, idOferta);
        return (long) q.executeUnique();
    }

    public long eliminarResidenPorIdReserva(PersistenceManager pm, long idReserva) {
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaResiden() + " WHERE idreserva = ?");
        q.setParameters(idReserva);
        Query q2 = pm.newQuery(SQL, "SELECT idoferta FROM " + pp.darTablaResiden() + " WHERE idreserva = ?");
        q2.setParameters(idReserva);
        Query q3 = pm.newQuery(SQL, "UPDATE " + pp.darTablaOferta() + " SET reservada = 0 WHERE id = ?");
        q3.setParameters(q2.executeUnique());
        q3.executeUnique();
        return (long) q.executeUnique();
    }

    public long eliminarResidenPorIdOferta(PersistenceManager pm, long idOferta) {
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaResiden() + " WHERE idoferta = ?");
        q.setParameters(idOferta);
        return (long) q.executeUnique();
    }

    public List<Residen> darResiden(PersistenceManager pm) {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaResiden());
        q.setResultClass(Residen.class);
        return (List<Residen>) q.executeList();
    }

    public long actualizarIdOferta(PersistenceManager pm, long idResidente, long idOferta, long idOfertaNueva) {
        Query q = pm.newQuery(SQL,
                "UPDATE" + pp.darTablaResiden() + "SET idoferta = ? WHERE idresidente = ? AND idoferta = ?");
        q.setParameters(idOfertaNueva, idResidente, idOferta);
        return (long) q.executeUnique();
    }

    public List<Object> darOfertasMasReservadas(PersistenceManager pm) {
        Query q = pm.newQuery(SQL,
                "SELECT idoferta FROM " + pp.darTablaResiden() + " GROUP BY idoferta ORDER BY count(*) desc");
        return (List<Object>) q.executeList();
    }

    public Residen darResidenPorIdReserva(PersistenceManager pm, long idReserva) {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaResiden() + " WHERE idreserva = ?");
        q.setResultClass(Residen.class);
        q.setParameters(idReserva);
        return (Residen) q.executeUnique();
    }

    public List<Residen> darResidenPorIdOferta (PersistenceManager pm, long idOferta) {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaResiden() + " WHERE idoferta = ?");
        q.setResultClass(Residen.class);
        q.setParameters(idOferta);
        return (List<Residen>) q.executeList();
    }

}
