package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.TieneServicio;

public class SQLTiene_servicio {
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
    public SQLTiene_servicio(PersistenciaAlohandes pp) {
        this.pp = pp;
    }

    public long adicionarTiene_servicio(PersistenceManager pm, long idAlojamiento, long idServicio) {
        Query q = pm.newQuery(SQL,
                "INSERT INTO " + pp.darTablaTiene_servicio() + "(idalojamiento, idservicio) values (?, ?)");
        q.setParameters(idAlojamiento, idServicio);
        return (long) q.executeUnique();
    }

    public long eliminarTiene_servicioPorId(PersistenceManager pm, long idAlojamiento, long idServicio) {
        Query q = pm.newQuery(SQL,
                "DELETE FROM " + pp.darTablaTiene_servicio() + " WHERE idAlojamiento = ? AND idServicio = ?");
        q.setParameters(idAlojamiento, idServicio);
        return (long) q.executeUnique();
    }

    public TieneServicio darTiene_servicioPorId(PersistenceManager pm, long idAlojamiento, long idServicio) {
        Query q = pm.newQuery(SQL,
                "SELECT * FROM " + pp.darTablaTiene_servicio() + " WHERE idAlojamiento = ? AND idServicio = ?");
        q.setParameters(idAlojamiento, idServicio);
        return (TieneServicio) q.executeUnique();
    }

    public long actualizarTiene_servicioPorId(PersistenceManager pm, long idAlojamiento, long idServicio) {
        Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaTiene_servicio()
                + " SET idAlojamiento = ?, idServicio = ? WHERE idAlojamiento = ? AND idServicio = ?");
        q.setParameters(idAlojamiento, idServicio);
        return (long) q.executeUnique();
    }

    // dar todos los tiene_servicios
    public List<TieneServicio> darTiene_servicios(PersistenceManager pm) {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaTiene_servicio());
        q.setResultClass(TieneServicio.class);
        return (List<TieneServicio>) q.executeList();
    }
}
