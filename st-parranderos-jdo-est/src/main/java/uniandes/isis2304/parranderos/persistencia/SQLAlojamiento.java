
package uniandes.isis2304.parranderos.persistencia;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Alojamiento;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto BAR de Alohandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Germán Bravo
 */
class SQLAlojamiento
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
    public SQLAlojamiento (PersistenciaAlohandes pp)
    {
        this.pp = pp;
    }
    
    /**
     * Crea y ejecuta la sentencia SQL para adicionar un ALOJAMIENTO a la base de datos de Alohandes
     * @param pm - El manejador de persistencia
     * @param idAlojamiento - El identificador del alojamiento
     * @param idOperador - El identificador del operador dueño del alojamiento
     * @param tipo - El tipo del alojamiento
     * @return El número de tuplas insertadas
     */
    public long adicionarAlojamiento (PersistenceManager pm, long idAlojamiento, long idOperador, String tipo) 
    {
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaAlojamiento () + "(id, idoperador, tipo) values (?, ?, ?)");
        q.setParameters(idAlojamiento, idOperador, tipo);
        return (long) q.executeUnique();
    }

    /**
     * Crea y ejecuta la sentencia SQL para eliminar ALOJAMIENTOS de la base de datos de Alohandes, por su tipo
     * @param pm - El manejador de persistencia
     * @param tipo - El tipo del alojamiento
     * @return EL número de tuplas eliminadas
     */
    public long eliminarAlojamientoPorTipo (PersistenceManager pm, String tipo)
    {
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaAlojamiento () + " WHERE tipo = ?");
        q.setParameters(tipo);
        return (long) q.executeUnique();
    }

    /**
     * Crea y ejecuta la sentencia SQL para eliminar UN ALOJAMIENTO de la base de datos de Alohandes, por su identificador
     * @param pm - El manejador de persistencia
     * @param idAlojamiento - El identificador del alojamiento
     * @return EL número de tuplas eliminadas
     */
    public long eliminarAlojamientoPorId (PersistenceManager pm, long idAlojamiento)
    {
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaAlojamiento () + " WHERE id = ?");
        q.setParameters(idAlojamiento);
        return (long) q.executeUnique();
    }

    /**
     * Crea y ejecuta la sentencia SQL para encontrar la información de UN ALOJAMIENTO de la 
     * base de datos de Alohandes, por su identificador
     * @param pm - El manejador de persistencia
     * @param idAlojamiento - El identificador del alojamiento
     * @return El objeto ALOJAMIENTO que tiene el identificador dado
     */
    public Alojamiento darAlojamientoPorId (PersistenceManager pm, long idAlojamiento) 
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaAlojamiento () + " WHERE id = ?");
        q.setParameters(idAlojamiento);
        q.setResultClass(Alojamiento.class);
        return (Alojamiento) q.executeUnique();
    }


    /**
     * Crea y ejecuta la sentencia SQL para encontrar la información de LOS ALOJAMIENTOS de la 
     * base de datos de Alohandes
     * @param pm - El manejador de persistencia
     * @return Una lista de objetos BAR
     */
    public List<Alojamiento> darAlojamiento (PersistenceManager pm)
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaAlojamiento ());
        q.setResultClass(Alojamiento.class);
        return (List<Alojamiento>) q.executeList();
    }

    /**
	 * Crea y ejecuta la sentencia SQL para:
	 * Eliminar un alojamiento y los detalles del alojamiento: 
	 * En caso que el alojamiento esté referenciado por otra relación, NO SE BORRA NI EL ALOJAMIENTO, NI SUS DETALLES
	 * Adiciona entradas al log de la aplicación
	 * @param pm - El manejador de persistencia
	 * @param idOperador - El alojamiento que se quiere eliminar
	 * @return Una pareja de números [número de alojamientos eliminados, número de detalles eliminadas]
	 */
	public long [] eliminarAlojamientoYDetalle (PersistenceManager pm, long idAlojamiento) 
	{
      Query q1 = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaDetalle_aloj() + " WHERE idalojamiento = ?");
      q1.setParameters(idAlojamiento);
      Query q2 = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaAlojamiento() + " WHERE id = ?");
      q2.setParameters(idAlojamiento);
      
      long detalleEliminado = (long) q1.executeUnique ();
      long alojamientoEliminado = (long) q2.executeUnique ();
      return new long[] {alojamientoEliminado, detalleEliminado};
	}

    /**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS SERVICIOS DEL ALOJAMIENTO de la 
	 * base de datos de Alohandes
	 * @param pm - El manejador de persistencia
	 * @param idAlojamiento - El identificador del alojamiento
	 * @return Una lista de arreglos de objetos de tamaño 3. Los elementos del arreglo corresponden a los datos del 
	 * alojamiento del operador:
	 * 		(id, nombre servicio, costo servicio)
	 */
	public List<Object []> darServicios (PersistenceManager pm, long idAlojamiento)
	{
        String sql = "SELECT s.id, s.nombre, s.costo";
        sql += " FROM ";
        sql += pp.darTablaServicio() + " s, ";
        sql += pp.darTablaTiene_servicio() + " ts ";
       	sql	+= " WHERE ";
       	sql += "ts.idalojamiento = ?";
        sql	+= " AND s.id = ts.idservicio";
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(idAlojamiento);
		return q.executeList();
    }

    /**
    * Crea y ejecuta la sentencia SQL para encontrar los ALOJAMIENTOS que son ofertados en un rango de fechs de la base de datos de Alohandes
    * @param pm - El manejador de persistencia
    * @param fechaInicial - Fecha de inicio
    * @param fechaFinal - Fecha de fin 
    * @return Una lista de arreglos de objetos de tamaño 3. Los elementos del arreglo corresponden a los datos del 
    * alojamiento :
    * 		(id, idOperador, tipo)
    */
   public List<Object> darAlojamientoOfertadosPorFechas (PersistenceManager pm, Timestamp fechaInicial, Timestamp fechaFinal)
   {
       String sql = "SELECT o.idalojamiento";
       sql += " FROM ";
       sql += pp.darTablaOferta() + " o ";
       sql += " WHERE ";
       sql += "o.fecha <=  ?";
       sql += " AND o.fecha >=  ?";
       Query q = pm.newQuery(SQL, sql);
       q.setParameters(fechaFinal, fechaInicial);
       return q.executeList();
   }

   /**
    * Crea y ejecuta la sentencia SQL para encontrar los ALOJAMIENTOS que son ofertados en un rango de fechs de la base de datos de Alohandes
    * @param persistenceManager - El manejador de persistencia
    * @param idAlojamiento - id del alojamiento
    * @return El índice de ocupación del alojamiento
    */
   public BigDecimal darIndiceOcupacion(PersistenceManager persistenceManager, long idAlojamiento) {
       Query q = persistenceManager.newQuery(SQL,
               "SELECT CAPACIDAD FROM " + pp.darTablaDetalle_aloj() + " WHERE idalojamiento = ?");
       q.setParameters(idAlojamiento);
       return (BigDecimal) q.executeUnique();
   }

   public long actualizarAlojamiento(PersistenceManager pm, long idAlojamiento){
         Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaAlojamiento() + " SET OCUPADO = 0 WHERE id = ?");
         q.setParameters(idAlojamiento);
         return (long) q.executeUnique();
   }

   
   

    
}
