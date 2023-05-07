
package uniandes.isis2304.parranderos.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto BAR de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 
 */
class SQLUtil
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
	public SQLUtil(PersistenciaAlohandes pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para obtener un nuevo número de secuencia
	 * @param pm - El manejador de persistencia
	 * @return El número de secuencia generado
	 */
	public long nextval (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT " + pp.darSeqAlohandes() + ".nextval FROM DUAL");
        q.setResultClass(Long.class);
        long resp = (long) q.executeUnique();
        return resp;
	}

	/**
	 * Crea y ejecuta las sentencias SQL para cada tabla de la base de datos - EL ORDEN ES IMPORTANTE 
	 * @param pm - El manejador de persistencia
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas.
	 */
	public long [] limpiarAlohandes (PersistenceManager pm)
	{
		Query qTS = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaTiene_servicio());          
        Query qResiden = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaResiden());
        Query qServicio = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaServicio());
        Query qReserva = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaReserva());
        Query qResidente = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaResidente());
        Query qOferta = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaOferta());
        Query qDA = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaDetalle_aloj());
		Query qAlojamiento = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaAlojamiento());
		Query qDO = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaDetalle_operadores());
		Query qOperadores = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaOperador());

        long TS = (long) qTS.executeUnique ();
		long Residen = (long) qResiden.executeUnique ();
        long Servicio = (long) qServicio.executeUnique ();
        long Reserva = (long) qReserva.executeUnique ();
        long Residente = (long) qResidente.executeUnique ();
        long Oferta =(long) qOferta.executeUnique ();
        long DA = (long) qDA.executeUnique ();
		long Alojamiento = (long) qAlojamiento.executeUnique ();
		long DO = (long) qDO.executeUnique ();
		long Operadores = (long) qOperadores.executeUnique ();


        
        return new long[] {TS, Residen, Servicio, Reserva, Residente, Oferta, DA, Alojamiento, DO, Operadores};
	}

}
