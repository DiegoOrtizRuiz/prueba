package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Servicio;

public class SQLServicio {

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
    public SQLServicio(PersistenciaAlohandes pp) {
        this.pp = pp;
    }

    // crea la funcion de agregar servicio
    public long adicionarServicio(PersistenceManager pm, long idServicio, String nombre, double costo) {
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaServicio() + "(id, nombre, costo) values (?, ?, ?)");
        q.setParameters(idServicio, nombre, costo);
        return (long) q.executeUnique();
    }

    // crea la funcion de eliminar servicio
    public long eliminarServicioPorId(PersistenceManager pm, long id) {
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaServicio() + " WHERE id = ?");
        q.setParameters(id);
        return (long) q.executeUnique();
    }

    // crea la funcion de dar servicio por id
    public Object[] darServicioPorId(PersistenceManager pm, long id) {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaServicio() + " WHERE id = ?");
        q.setParameters(id);
        return (Object[] ) q.executeUnique();
    }

    // crea la funcion de dar servicio por nombre
    public Servicio darServicioPorNombre(PersistenceManager pm, String nombre) {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaServicio() + " WHERE nombre = ?");
        q.setParameters(nombre);
        return (Servicio) q.executeUnique();
    }

    // crea la funcion de dar todos los servicios
    public List<Servicio> darServicios(PersistenceManager pm) {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaServicio());
        q.setResultClass(Servicio.class);
        return (List<Servicio>) q.executeList();
    }

    // crea la funcion de actualizar servicio
    public Servicio actualizarServicio(PersistenceManager pm, long id, String nombre, double costo) {
        Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaServicio() + " SET nombre = ?, costo = ? WHERE id = ?");
        q.setParameters(nombre, costo, id);
        return (Servicio) q.executeUnique();
    }

}
