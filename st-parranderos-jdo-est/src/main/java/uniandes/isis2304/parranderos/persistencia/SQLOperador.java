
 package uniandes.isis2304.parranderos.persistencia;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import uniandes.isis2304.parranderos.negocio.Operador;
 
 /**
  * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto BAR de Alohandes
  * Nótese que es una clase que es sólo conocida en el paquete de persistencia
  * 
  * @author Germán Bravo
  */
 class SQLOperador
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
     public SQLOperador (PersistenciaAlohandes pp)
     {
         this.pp = pp;
     }
     
     /**
      * Crea y ejecuta la sentencia SQL para adicionar un OPERADOR a la base de datos de Alohandes
      * @param pm - El manejador de persistencia
      * @param idOperador - El identificador del operador
      * @param nombre - El nombre del operador
      * @param tipo - El tipo del operador
      * @return El número de tuplas insertadas
      */
     public long adicionarOperador(PersistenceManager pm,long id,  String nombre, String tipo) 
     {
         Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaOperador() + "(id, nombre, tipo) values (?, ?, ?)");
         q.setParameters(id, nombre, tipo);
         return (long) q.executeUnique();
     }
 
     /**
      * Crea y ejecuta la sentencia SQL para eliminar OPERADORES de la base de datos de Alohandes, por su nombre
      * @param pm - El manejador de persistencia
      * @param nombreOp - El nombre del operador
      * @return EL número de tuplas eliminadas
      */
     public long eliminarOperadorPorNombre (PersistenceManager pm, String nombreOp)
     {
         Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaOperador () + " WHERE nombre = ?");
         q.setParameters(nombreOp);
         return (long) q.executeUnique();
     }
 
     /**
      * Crea y ejecuta la sentencia SQL para eliminar UN OPERDAOR de la base de datos de Alohandes, por su identificador
      * @param pm - El manejador de persistencia
      * @param idOp - El identificador del operador
      * @return EL número de tuplas eliminadas
      */
     public long eliminarOperadorPorId (PersistenceManager pm, long idOp)
     {
         Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaOperador () + " WHERE id = ?");
         q.setParameters(idOp);
         return (long) q.executeUnique();
     }
 
     /**
      * Crea y ejecuta la sentencia SQL para encontrar la información de UN OPERADOR de la 
      * base de datos de Alohandes, por su identificador
      * @param pm - El manejador de persistencia
      * @param idOperador - El identificador del operador
      * @return El objeto BAR que tiene el identificador dado
      */
     public Operador darOperadorPorId (PersistenceManager pm, long idOperador) 
     {
         Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaOperador () + " WHERE id = ?");
         q.setResultClass(Operador.class);
         q.setParameters(idOperador);
         return (Operador) q.executeUnique();
     }
 
     /**
      * Crea y ejecuta la sentencia SQL para encontrar la información de LOS OPERADORES de la 
      * base de datos de Alohandes, por su nombre
      * @param pm - El manejador de persistencia
      * @param nombreOperador - El nombre de bar buscado
      * @return Una lista de objetos BAR que tienen el nombre dado
      */
     public List<Operador> darOperadoresPorNombre (PersistenceManager pm, String nombreOperador) 
     {
         Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaOperador () + " WHERE nombre = ?");
         q.setResultClass(Operador.class);
         q.setParameters(nombreOperador);
         return (List<Operador>) q.executeList();
     }
 
     /**
      * Crea y ejecuta la sentencia SQL para encontrar la información de LOS OPERADORES de la 
      * base de datos de Alohandes
      * @param pm - El manejador de persistencia
      * @return Una lista de objetos BAR
      */
     public List<Operador> darOperadores (PersistenceManager pm)
     {
         Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaOperador ());
         q.setResultClass(Operador.class);
         return (List<Operador>) q.executeList();
     }

    /**
	 * Crea y ejecuta la sentencia SQL para:
	 * Eliminar un operador y los detalles del operador: 
	 * En caso que el operador esté referenciado por otra relación, NO SE BORRA NI EL OPERADOR, NI SUS DETALLES
	 * Adiciona entradas al log de la aplicación
	 * @param pm - El manejador de persistencia
	 * @param idOperador - El operador que se quiere eliminar
	 * @return Una pareja de números [número de bebedores eliminados, número de visitas eliminadas]
	 */
	public long [] eliminarOperadorYDetalle (PersistenceManager pm, long idOperador) 
	{
      Query q1 = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaDetalle_operadores() + " WHERE idoperador = ?");
      q1.setParameters(idOperador);
      Query q2 = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaOperador () + " WHERE id = ?");
      q2.setParameters(idOperador);
      
      long detalleEliminado = (long) q1.executeUnique ();
      long operadorEliminado = (long) q2.executeUnique ();
      return new long[] {operadorEliminado, detalleEliminado};
	}

    /**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS ALOJAMIENTOS DEL OPERADOR de la 
	 * base de datos de Alohandes
	 * @param pm - El manejador de persistencia
	 * @param idOperador - El identificador del operador
	 * @return Una lista de arreglos de objetos de tamaño 2. Los elementos del arreglo corresponden a los datos del 
	 * alojamiento del operador:
	 * 		(id, tipo)
	 */
	public List<Object []> darAlojamientos (PersistenceManager pm, long idOperador)
	{
        String sql = "SELECT aloj.id, aloj.idoperador, aloj.tipo";
        sql += " FROM ";
        sql += pp.darTablaAlojamiento() + " aloj ";
       	sql	+= " WHERE ";
       	sql += "aloj.idoperador = ?";
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(idOperador);
		return q.executeList();
    }

      /**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LAS OFERTAS DEL OPERADOR de la 
	 * base de datos de Alohandes
	 * @param pm - El manejador de persistencia
	 * @param idOperador - El identificador del operador
	 * @return Una lista de arreglos de objetos de tamaño 6. Los elementos del arreglo corresponden a los datos de la
	 * oferta del operador:
	 * 		(id, idoperador, idalojamiento, fecha, costo, tiempo_limite_dias)
	 */
	public List<Object []> darOfertas (PersistenceManager pm, long idOperador)
	{
        String sql = "SELECT *";
        sql += " FROM ";
        sql += pp.darTablaOferta() + " o ";
       	sql	+= " WHERE ";
       	sql += "o.idoperador = ?";
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(idOperador);
		return q.executeList();
    }

    /**
	 * Crea y ejecuta la sentencia SQL para encontrar las ganancias del anio corrido un OPERADOR
	 * @param pm - El manejador de persistencia
     * @param idOperador - El identificador del operador
	 * @return Una  arreglo de objetos de tamaño 2. Los elementos del arreglo corresponden a los datos de las
	 * ganancias del operador:
	 * 		(idoperador, ganancias año corrido)
	 */
    public BigDecimal darGananciasAnioCorrido(PersistenceManager pm, long idOperador)
	{
        String sql = "SELECT SUM(o.costo) AS anio_actual";
        sql += " FROM ";
        sql += pp.darTablaOferta() + " o ";
        sql += " JOIN ";
        sql += pp.darTablaResiden() + " r ";
        sql += " ON ";
        sql += "o.id = r.idoferta";
       	sql	+= " AND ";
        sql	+= " o.idoperador = ? ";
        sql	+= " AND ";
       	sql += "o.fecha >= DATE '2023-01-01'";
        sql	+= " AND ";
        sql += "o.fecha <= DATE '2024-01-01'";
        sql += " GROUP BY o.idoperador";
		Query q = pm.newQuery(SQL, sql);
        q.setParameters(idOperador);
		return (BigDecimal) q.executeUnique();
    }
    
    /**
	 * Crea y ejecuta la sentencia SQL para encontrar las ganancias del anio actual un OPERADOR
	 * @param pm - El manejador de persistencia
     * @param idOperador - El identificador del operador
	 * @return Una lista de arreglos de objetos de tamaño 2. Los elementos del arreglo corresponden a los datos de las
	 * ganancias del operador:
	 * 		(idoperador, ganancias año actual)
	 */
	public BigDecimal darGananciasAnioActual (PersistenceManager pm, long idOperador)
	{
        
        // Get the current date
        long timestamp = System.currentTimeMillis();
        Timestamp now = new Timestamp(timestamp);

        Instant instant = Instant.now().minus(Duration.ofDays(365));
        Timestamp oneYearAgo = Timestamp.from(instant);

        
        String sql = "SELECT SUM(o.costo) AS anio_actual";
        sql += " FROM ";
        sql += pp.darTablaOferta() + " o ";
        sql += " JOIN ";
        sql += pp.darTablaResiden() + " r ";
        sql += " ON ";
        sql += "o.id = r.idoferta";
       	sql	+= " AND ";
        sql	+= " o.idoperador = ? ";
        sql	+= " AND ";
       	sql += "o.fecha >= ?";
        sql	+= " AND ";
        sql += "o.fecha <= ?";
        sql += " GROUP BY o.idoperador";
		Query q = pm.newQuery(SQL, sql);
        q.setParameters(idOperador, oneYearAgo,now );
		return (BigDecimal) q.executeUnique();
    }
 
    public long actualizarGanancias(PersistenceManager pm, long idOperador, Long ganancias)
    {
        String sql = "UPDATE GANANCIAS" + " SET ganancias = ganancias + ? WHERE idOperador = ?";
        Query q = pm.newQuery(SQL, sql);
        q.setParameters(ganancias, idOperador);
        return (long) q.executeUnique();
    }
     
 }
 