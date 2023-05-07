package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Residente;

public class SQLResidente {

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
    public SQLResidente(PersistenciaAlohandes pp) {
        this.pp = pp;
    }

    // crear la funcion para añadir un residente
    public long adicionarResidente(PersistenceManager pm,long id,  String nombre, String vinculo) {
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaResidente() + "(id, nombre, vinculo) values (?, ?, ?)");
        q.setParameters(id, nombre, vinculo);
        return (long) q.executeUnique();
    }

    public long eliminarResidentePorId(PersistenceManager pm, long id) {
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaResidente() + " WHERE id = ?");
        q.setParameters(id);
        return (long) q.executeUnique();
    }

    public Residente darResidentePorId(PersistenceManager pm, long id) {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaResidente() + " WHERE id = ?");
        q.setParameters(id);
        return (Residente) q.executeUnique();
    }

    public Residente actualizarResidentePorId(PersistenceManager pm, long id, String nombre, String vinculo) {
        Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaResidente() + " SET nombre = ?, vinculo = ? WHERE id = ?");
        q.setParameters(nombre, vinculo, id);
        return (Residente) q.executeUnique();
    }

    public List<Object[]> darResidentes(PersistenceManager pm) {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaResidente());
        return (List<Object[]>) q.executeList();
    }
}
