package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Detalle_aloj;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto Detalle_aloj de Alohandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 */
class SQLDetalle_aloj
{
    /* ****************************************************************
     * 			Constantes
     *****************************************************************/
    /**
     * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
     * Se renombra acá para facilitar la escritura de las sentencias
     */
    private final static String SQL = PersistenciaAlohandes.SQL;

    /* ****************************************************************
     * 			Atributos
     *****************************************************************/
    /**
     * El manejador de persistencia general de la aplicación
     */
    private PersistenciaAlohandes pp;

    /* ****************************************************************
     * 			Métodos
     *****************************************************************/

    /**
     * Constructor
     * @param pp - El Manejador de persistencia de la aplicación
     */
    public SQLDetalle_aloj (PersistenciaAlohandes pp)
    {
        this.pp = pp;
    }
    
    /**
     * Crea y ejecuta la sentencia SQL para adicionar informacion de un ALOJAMIENTO a DETALLE_ALOJ a la base de datos de Alohandes
     * @param idAlojamiento - El id del alojamiento
     * @param categoria - La categoria del alojamiento ('Suite', 'Semisuite', 'Estandar', 'Compartida', 'Individual')
     * @param capacidad - La capacidad del alojamiento
     * @param servicios - Si el alojamiento cuenta con servicios o no
     * @param costo_seguro - El costo del seguro si es el caso
     * @param costo_admon - El cosot de la administracion si es el caso
     * @param amoblado -  Si elojamiento esta amoblado o no
     * @return El número de tuplas insertadas
     */
    public long adicionarDetalle_aloj (PersistenceManager pm, long idAlojamiento, String categoria, int capacidad, int servicios, int costo_seguro, int costo_admon, int amoblado) 
    {
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaDetalle_aloj () + "(idalojamiento, categoria, capacidad, servicios, costo_seguro, costo_admon, amoblado ) values (?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(idAlojamiento, categoria, capacidad, servicios, costo_seguro, costo_admon, amoblado);
        return (long) q.executeUnique();
    }


    /**
     * Crea y ejecuta la sentencia SQL para eliminar UN DETALLE_ALOJ de la base de datos de Alohandes, por su identificador
     * @param pm - El manejador de persistencia
     * @param idAlojamiento - El identificador del alojamiento
     * @return EL número de tuplas eliminadas
     */
    public long eliminarDetalle_alojPorId (PersistenceManager pm, long idAlojamiento)
    {
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaDetalle_aloj () + " WHERE id = ?");
        q.setParameters(idAlojamiento);
        return (long) q.executeUnique();
    }

    /**
     * Crea y ejecuta la sentencia SQL para encontrar la información de UN DETALLE_ALOJ de la 
     * base de datos de Alohandes, por su identificador
     * @param pm - El manejador de persistencia
     * @param idAlojamiento - El identificador del alojamiento
     * @return El objeto BAR que tiene el identificador dado
     */
    public Object[] darDetalle_alojPorId (PersistenceManager pm, long idAlojamiento) 
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaDetalle_aloj () + " WHERE idAlojamiento = ? ");
        q.setParameters(idAlojamiento);
        return (Object[]) q.executeUnique();
    }


    /**
     * Crea y ejecuta la sentencia SQL para encontrar la información de LOS DETALLE_ALOJ de la 
     * base de datos de Alohandes
     * @param pm - El manejador de persistencia
     * @return Una lista de objetos BAR
     */
    public List<Detalle_aloj> darDetalle_aloj (PersistenceManager pm)
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaDetalle_aloj ());
        q.setResultClass(Detalle_aloj.class);
        return (List<Detalle_aloj>) q.executeList();
    }

}
