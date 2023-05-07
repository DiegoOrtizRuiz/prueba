package uniandes.isis2304.parranderos.persistencia;

import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Oferta;

public class SQLOferta {
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
    public SQLOferta(PersistenciaAlohandes pp) {
        this.pp = pp;
    }

    /**
     * Crea y ejecuta la sentencia SQL para adicionar un OPERADOR a la base de datos
     * de Alohandes
     * 
     * @param pm               - El manejador de persistencia
     * @param id               - El identificador de la oferta
     * @param idAlojamiento    - El identificador del alojamiento
     * @param idOperador       - El identificador del operador
     * @param fecha            - Fecha de la oferta
     * @param costo            - Csto de la oferta
     * @param tiempoLimiteDias - Tiempo limite de la oferta
     * @return El número de tuplas insertadas
     */
    public long adicionarOferta(PersistenceManager pm,long idOferta, long idAlojamiento, long idOperador, Timestamp fecha,
            double costo, int tiempoLimiteDias) {
        System.out.println("adicionandoOFERTAOferta");
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaOferta()
                + "(id, idAlojamiento, idOperador, fecha, costo, tiempo_limite_dias) values (?, ?, ?, ?, ?, ?)");
        q.setParameters(idOferta, idAlojamiento, idOperador, fecha, costo, tiempoLimiteDias);
        return (long) q.executeUnique();

    }

    /**
     * Crea y ejecuta la sentencia SQL para eliminar OPERADORES de la base de datos
     * de Alohandes, por su nombre
     * 
     * @param pm       - El manejador de persistencia
     * @param idOferta - El id del la oferta
     * @return EL número de tuplas eliminadas
     */
    public long eliminarOfertaPorId(PersistenceManager pm, long idOferta) {
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaOferta() + " WHERE id = ?");
        q.setParameters(idOferta);
        return (long) q.executeUnique();
    }

    /**
     * Modifica una oferta existente en la base de datos de Alohandes con el id de
     * oferta dado. Esta función actualiza la oferta con el id especificado con
     * nuevos valores para su alojamiento asociado, operador, fecha, costo y límite
     * de tiempo en días.
     * 
     * @param pm               - El manejador de persistencia.
     * @param idOferta         - El id de la oferta a modificar.
     * @param idAlojamiento    - El id del alojamiento asociado con la oferta.
     * @param idOperador       - El id del operador asociado con la oferta.
     * @param fecha            - La nueva fecha para la oferta.
     * @param costo            - El nuevo costo para la oferta.
     * @param tiempoLimiteDias - El nuevo límite de tiempo en días para la oferta.
     * @return El número de tuplas afectadas.
     */
    public long modificarOfertaPorId(PersistenceManager pm, long idOferta, long idAlojamiento, long idOperador,
            Timestamp fecha, double costo, int tiempoLimiteDias) {
        Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaOferta()
                + "SET idAlojamiento = ?, idOperador = ?, fecha = ?, costo = ?, tiempoLimiteDias = ? WHERE id = ?");
        q.setParameters(idAlojamiento, idOperador, fecha, costo, tiempoLimiteDias, idOferta);
        return (long) q.executeUnique();
    }

    /**
     * Obtiene una oferta con el id dado de la base de datos de Alohandes.
     * 
     * @param pm       - El manejador de persistencia.
     * @param idOferta - El id de la oferta a obtener.
     * @return La oferta con el id especificado, o null si no existe ninguna oferta
     *         con ese id.
     */
    public Object[] darOfertaPorId(PersistenceManager pm, long idOferta) {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaOferta() + " WHERE id = ?");
        q.setParameters(idOferta);
        return (Object[]) q.executeUnique();
    }

    /**
     * Obtiene todas las ofertas de la base de datos de Alohandes.
     * 
     * @param pm - El manejador de persistencia.
     * @return Una lista de todas las ofertas en la base de datos.
     */
    public List<Object[]> darOfertas(PersistenceManager pm) {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaOferta());
        return q.executeList();
    }

    public List<Object[]> darOfertasDisponibles (PersistenceManager pm){
        Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaOferta() + " WHERE reservada = 0");
        return q.executeList();
    }

    public long actualizarOferta (PersistenceManager pm, long idOferta, int reservada){
        Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaOferta() + " SET reservada = ? WHERE id = ?");
        q.setParameters(reservada, idOferta);
        return (long) q.executeUnique();
    }

    public void agregarDesahabilitacion (PersistenceManager pm, long idOferta){
        Query q = pm.newQuery(SQL,"INSERT INTO DESHABILITADAS " + "(idOferta) values (?)");
        q.setParameters(idOferta);
        q.executeUnique();
    }

    public List<Object[]> darOfertasDesahabilitadasPorOp (PersistenceManager pm, long idOperador){
        Query q = pm.newQuery(SQL,"SELECT * FROM OFERTA WHERE id IN (SELECT idOferta FROM DESHABILITADAS) AND OFERTA.idOperador = ?");
        q.setParameters(idOperador);
        return q.executeList();
    }
    public long eliminarDesahabilitacion (PersistenceManager pm, long idOferta){
        Query q = pm.newQuery(SQL,"DELETE FROM DESHABILITADAS " + "WHERE idOferta = ?");
        q.setParameters(idOferta);
        return (long) q.executeUnique();
    }
    
}
