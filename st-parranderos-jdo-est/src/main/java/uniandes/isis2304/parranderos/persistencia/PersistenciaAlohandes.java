package uniandes.isis2304.parranderos.persistencia;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.jdo.JDODataStoreException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import uniandes.isis2304.parranderos.negocio.Operador;
import uniandes.isis2304.parranderos.negocio.Reserva;
import uniandes.isis2304.parranderos.negocio.Residen;
import uniandes.isis2304.parranderos.negocio.Residente;
import uniandes.isis2304.parranderos.negocio.Servicio;
import uniandes.isis2304.parranderos.negocio.TieneServicio;
import uniandes.isis2304.parranderos.negocio.Alojamiento;
import uniandes.isis2304.parranderos.negocio.Detalle_aloj;
import uniandes.isis2304.parranderos.negocio.Detalle_operadores;
import uniandes.isis2304.parranderos.negocio.Oferta;

/**
 * Clase para el manejador de persistencia del proyecto Alohandes
 * Traduce la información entre objetos Java y tuplas de la base de datos, en ambos sentidos
 * Sigue un patrón SINGLETON (Sólo puede haber UN objeto de esta clase) para comunicarse de manera correcta
 * con la base de datos
 * Se apoya en las clases SQLAlojamiento, SQLOperador, SQLDetalle_operadores, SQLDetalle_aloj  que son 
 * las que realizan el acceso a la base de datos
 * 
 *
 */
public class PersistenciaAlohandes
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(PersistenciaAlohandes.class.getName());
	
	/**
	 * Cadena para indicar el tipo de sentencias que se va a utilizar en una consulta
	 */
	public final static String SQL = "javax.jdo.query.SQL";

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * Atributo privado que es el único objeto de la clase - Patrón SINGLETON
	 */
	private static PersistenciaAlohandes instance;
	
	/**
	 * Fábrica de Manejadores de persistencia, para el manejo correcto de las transacciones
	 */
	private PersistenceManagerFactory pmf;
	
	/**
	 * Arreglo de cadenas con los nombres de las tablas de la base de datos, en su orden:
	 * Secuenciador, operador, detalle_operador, alojamiento, detalle_aloj, oferta, residente, reserva, residen, servicio
     * y tiene_servicio
	 */
	private List <String> tablas;
	
	/**
	 * Atributo para el acceso a las sentencias SQL propias a PersistenciaParranderos
	 */
	private SQLUtil sqlUtil;
	
	/**
	 * Atributo para el acceso a la tabla OPERADOR de la base de datos
	 */
	private SQLOperador sqlOperador;
	
	/**
	 * Atributo para el acceso a la tabla ALOJAMIENTO de la base de datos
	 */
	private SQLAlojamiento sqlAlojamiento;
	
	/**
	 * Atributo para el acceso a la tabla DETALLE_ALOJ de la base de datos
	 */
	private SQLDetalle_aloj sqlDetalle_aloj;
	
	/**
	 * Atributo para el acceso a la tabla DETALLE_OPERADORES de la base de datos
	 */
	private SQLDetalle_operadores sqlDetalle_operadores;

    /**
	 * Atributo para el acceso a la tabla OFERTA de la base de datos
	 */
	private SQLOferta sqlOferta;

    /**
	 * Atributo para el acceso a la tabla RESERVA de la base de datos
	 */
	private SQLReserva sqlReserva;

    /**
	 * Atributo para el acceso a la tabla RESIDENTE de la base de datos
	 */
	private SQLResidente sqlResidente;

    /**
	 * Atributo para el acceso a la tabla SERVICIO de la base de datos
	 */
	private SQLServicio sqlServicio;

    /**
	 * Atributo para el acceso a la tabla RESIDEN de la base de datos
	 */
	private SQLResiden sqlResiden;

    /**
	 * Atributo para el acceso a la tabla TIENE_SERVICIO de la base de datos
	 */
	private SQLTiene_servicio sqlTiene_servicio;

	
	/* ****************************************************************
	 * 			Métodos del MANEJADOR DE PERSISTENCIA
	 *****************************************************************/

	/**
	 * Constructor privado con valores por defecto - Patrón SINGLETON
	 */
	private PersistenciaAlohandes ()
	{
		pmf = JDOHelper.getPersistenceManagerFactory("Alohandes");		
		crearClasesSQL ();
		
		// Define los nombres por defecto de las tablas de la base de datos
		tablas = new LinkedList<String> ();
		tablas.add ("ALOHANDES_SEQUENCE");
		tablas.add ("OPERADORES");
        tablas.add ("DETALLE_OPERADORES");
		tablas.add ("ALOJAMIENTO");
		tablas.add ("DETALLES_ALOJ");
		tablas.add ("OFERTA");
        tablas.add ("RESIDENTE");
		tablas.add ("RESERVA");
		tablas.add ("RESIDEN");
        tablas.add ("SERVICIO");
        tablas.add ("TIENE_SERVICIO");
}
/**
	 * Constructor privado, que recibe los nombres de las tablas en un objeto Json - Patrón SINGLETON
	 * @param tableConfig - Objeto Json que contiene los nombres de las tablas y de la unidad de persistencia a manejar
	 */
	private PersistenciaAlohandes (JsonObject tableConfig)
	{
		crearClasesSQL ();
		tablas = leerNombresTablas (tableConfig);
		
		String unidadPersistencia = tableConfig.get ("unidadPersistencia").getAsString ();
		log.trace ("Accediendo unidad de persistencia: " + unidadPersistencia);
		pmf = JDOHelper.getPersistenceManagerFactory (unidadPersistencia);
	}

    /**
	 * @return Retorna el único objeto PersistenciaAlohandes existente - Patrón SINGLETON
	 */
	public static PersistenciaAlohandes getInstance ()
	{
		if (instance == null)
		{
			instance = new PersistenciaAlohandes ();
		}
		return instance;
	}

	/**
	 * Constructor que toma los nombres de las tablas de la base de datos del objeto tableConfig
	 * @param tableConfig - El objeto JSON con los nombres de las tablas
	 * @return Retorna el único objeto PersistenciaAlohandes existente - Patrón SINGLETON
	 */
	public static PersistenciaAlohandes getInstance (JsonObject tableConfig)
	{
		if (instance == null)
		{
			instance = new PersistenciaAlohandes (tableConfig);
		}
		return instance;
	}

	/**
	 * Cierra la conexión con la base de datos
	 */
	public void cerrarUnidadPersistencia ()
	{
		pmf.close ();
		instance = null;
	}

	/**
	 * Genera una lista con los nombres de las tablas de la base de datos
	 * @param tableConfig - El objeto Json con los nombres de las tablas
	 * @return La lista con los nombres del secuenciador y de las tablas
	 */
	private List <String> leerNombresTablas (JsonObject tableConfig)
	{
		JsonArray nombres = tableConfig.getAsJsonArray("tablas") ;

		List <String> resp = new LinkedList <String> ();
		for (JsonElement nom : nombres)
		{
			resp.add (nom.getAsString ());
		}
		
		return resp;
	}

    	/**
	 * Crea los atributos de clases de apoyo SQL
	 */
	private void crearClasesSQL ()
	{
		sqlOperador = new SQLOperador(this);
        sqlDetalle_operadores = new SQLDetalle_operadores(this);
        sqlAlojamiento = new SQLAlojamiento(this);
		sqlDetalle_aloj = new SQLDetalle_aloj(this);
		sqlOferta = new SQLOferta(this);
        sqlResidente = new SQLResidente(this);
		sqlReserva = new SQLReserva (this);
        sqlResiden = new SQLResiden(this);
		sqlServicio = new SQLServicio(this);		
        sqlTiene_servicio = new SQLTiene_servicio(this);
		sqlUtil = new SQLUtil(this);
	}

    /**
	 * @return La cadena de caracteres con el nombre del secuenciador de alohandes
	 */
	public String darSeqAlohandes ()
	{
		return tablas.get (0);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Operador de alohandes
	 */
	public String darTablaOperador ()
	{
		return tablas.get (1);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Detalle_operadores de alohandes
	 */
	public String darTablaDetalle_operadores ()
	{
		return tablas.get (2);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Alojamiento de alohandes
	 */
	public String darTablaAlojamiento ()
	{
		return tablas.get (3);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Detalle_aloj de alohandes
	 */
	public String darTablaDetalle_aloj ()
	{
		return tablas.get (4);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Oferta de alohandes
	 */
	public String darTablaOferta ()
	{
		return tablas.get (5);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Residente de alohandes
	 */
	public String darTablaResidente ()
	{
		return tablas.get (6);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Reserva de alohandes
	 */
	public String darTablaReserva ()
	{
		return tablas.get (7);
	}

    /**
	 * @return La cadena de caracteres con el nombre de la tabla de Residen de alohandes
	 */
	public String darTablaResiden ()
	{
		return tablas.get (8);
	}

    /**
	 * @return La cadena de caracteres con el nombre de la tabla de Servicio de alohandes
	 */
	public String darTablaServicio ()
	{
		return tablas.get (9);
	}

        /**
	 * @return La cadena de caracteres con el nombre de la tabla de Tiene_servicio de alohandes
	 */
	public String darTablaTiene_servicio ()
	{
		return tablas.get (10);
	}

	/**
	 * Transacción para el generador de secuencia de Alohandes
	 * Adiciona entradas al log de la aplicación
	 * @return El siguiente número del secuenciador de Alohandes
	 */
	private long nextval ()
	{
        long resp = sqlUtil.nextval (pmf.getPersistenceManager());
        log.trace ("Generando secuencia: " + resp);
        return resp;
    }

	/**
	 * Extrae el mensaje de la exception JDODataStoreException embebido en la Exception e, que da el detalle específico del problema encontrado
	 * @param e - La excepción que ocurrio
	 * @return El mensaje de la excepción JDO
	 */
	private String darDetalleException(Exception e) 
	{
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException"))
		{
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions() [0].getMessage();
		}
		return resp;
	}

	/* ****************************************************************
	 * 			Métodos para manejar el OPERADOR
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla OPERADOR
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del operador
     * @param tipo - El tipo de operador
	 * @return El objeto Operador adicionado. null si ocurre alguna Excepción
	 */
	public Operador adicionarOperador(String nombre, String tipo)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idOperador = nextval ();
			long tuplasInsertadas = sqlOperador.adicionarOperador(pm,idOperador, nombre, tipo);
            tx.commit();
            
            log.trace ("Inserción del operador: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Operador (idOperador, nombre, tipo);
        }
        catch (Exception e)
        {
       		e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla OPERADORES, dado el nombre del operador
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del operador
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarOperadorPorNombre (String nombre) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlOperador.eliminarOperadorPorNombre(pm, nombre);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla OPERADORES, dado el identificador del operador
	 * Adiciona entradas al log de la aplicación
	 * @param idOperador - El identificador del operador
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarOperadorPorId (long idOperador) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlOperador.eliminarOperadorPorId(pm, idOperador);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/**
	 * Método que consulta todas las tuplas en la tabla OPERADORES
	 * @return La lista de objetos OPERADOR, construidos con base en las tuplas de la tabla OPERADORES
	 */
	public List<Operador> darOperadores ()
	{
		
		PersistenceManager pm = pmf.getPersistenceManager();
		return sqlOperador.darOperadores(pm);
	}
 
	/**
	 * Método que consulta todas las tuplas en la tabla OPERADORES que tienen el nombre dado
	 * @param nombre - El nombre del OPERADOR
	 * @return La lista de objetos OPERADOR, construidos con base en las tuplas de la tabla OPERADORES
	 */
	public List<Operador> darOperadoresPorNombre (String nombre)
	{
		return sqlOperador.darOperadoresPorNombre(pmf.getPersistenceManager(), nombre);
	}
 
	/**
	 * Método que consulta todas las tuplas en la tabla OPERADORES con un identificador dado
	 * @param idOperador - El identificador del OPERADOR
	 * @return El objeto OPERADOR, construido con base en las tuplas de la tabla OPERADORES con el identificador dado
	 */
	public Operador darOperadorPorId (long idOperador)
	{
		return sqlOperador.darOperadorPorId (pmf.getPersistenceManager(), idOperador);
	}

	/**
	 * Método que consulta TODA LA INFORMACIÓN DE UN OPERADOR con el identificador dado. Incluye la información básica del operdor,
	 * las ofetas realizadas y los alojamientos que le posee.
	 * @param idOperador - El identificador del operador
	 * @return El objeto OPERADOR, construido con base en las tuplas de la tablas ALOJAMIENTO Y OFERTA
	 * relacionadas con el identificador de operador dado
	 */
	public Operador darOperadorCompleto (long idOperador) 
	{
	
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            Operador operador = sqlOperador.darOperadorPorId(pm, idOperador);
			operador.setAlojamientos(armarAlojamiento(sqlOperador.darAlojamientos(pm, idOperador)));
			operador.setOfertas(armarOferta(sqlOperador.darOfertas(pm, idOperador)));
			Integer [] ganancias = armarGanancias(sqlOperador.darGananciasAnioActual(pm, idOperador), sqlOperador.darGananciasAnioCorrido(pm, idOperador));
			operador.setGananciasAñoActual(ganancias[0]);
			operador.setGananciasAñoCorrido(ganancias[1]);
            tx.commit();
            return operador;
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	

	/**
	 * Método que elimima, de manera transaccional, un OPERADOR y su DETALLE_OPERADOR que ha realizado
	 * Si el operador está referenciado en alguna otra relación, no se borra ni el operador NI los detalles
	 * @param idOperador - El identificador del operador
	 * @return Un arreglo de dos números que representan el número de operadores eliminados y 
	 * el número de detalles eliminados, respectivamente. [-1, -1] si ocurre alguna Excepción
	 */
	public long []  eliminarOperadorYDetalle (long idOperador)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long [] resp = sqlOperador.eliminarOperadorYDetalle(pm, idOperador);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return new long[] {-1, -1};
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/**
	 * Método privado para generar las información basica de los alojamiento del operador: 
	 * La información básica del alojamiento, 
	 * @param tuplas - Una lista de arreglos de 3 objetos, con la información del alojamiento, en el siguiente orden:
	 * 	 aloj.id, , aloj.idoperador, aloj.tipo
	 * @return Una lista de arreglos de objetos ALOJAMIENTOS.
	 */
	private List<Alojamiento> armarAlojamiento (List<Object []> tuplas)
	{
		List<Alojamiento> alojamientos = new LinkedList <Alojamiento> ();
		for (Object [] tupla : tuplas)
		{			
			long idAlojamiento = ((BigDecimal) tupla [0]).longValue ();
			long idOperador = ((BigDecimal) tupla [1]).longValue ();
			String tipoAlojamiento = (String) tupla [2];

		
			Alojamiento alojamiento = new Alojamiento(idAlojamiento, idOperador, tipoAlojamiento);
			
			alojamientos.add(alojamiento);
		}
		return alojamientos;
	}

	/**
	 * Método privado para generar las información basica de los alojamiento del operador: 
	 * La información básica del alojamiento, 
	 * @param tuplas - Una lista de arreglos de 3 objetos, con la información del alojamiento, en el siguiente orden:
	 * 	 aloj.id, , aloj.idoperador, aloj.tipo
	 * @return Una lista de arreglos de objetos ALOJAMIENTOS.
	 */
	private List<Oferta> armarOferta (List<Object []> tuplas)
	{
		List<Oferta> ofertas = new LinkedList <Oferta> ();
		for (Object [] tupla : tuplas)
		{			
			long idOferta = ((BigDecimal) tupla [0]).longValue ();
			long idOperador = ((BigDecimal) tupla [1]).longValue ();
			long idAlojamiento = ((BigDecimal) tupla [2]).longValue ();
			Timestamp fechaOferta = (Timestamp) tupla [3];
			Integer costoOferta = ((BigDecimal) tupla [4]).intValue();
			Integer tiempoLimiteOferta = ((BigDecimal) tupla [5]).intValue();
		
			Oferta oferta = new Oferta(idOferta, idOperador, idAlojamiento, fechaOferta, costoOferta, tiempoLimiteOferta);
			
			ofertas.add(oferta);
		}
		return ofertas;
	}

	/**
	 * Método privado para generar las ganancia del operador en el año actual y en el año corrido:  
	 * @param tuplasAñoActual - Una lista de arreglos de 2 objetos, con la información de las ganancias del año actual, 
	 * en el siguiente orden: idoperador, ganancias año actual
	 * @param tuplasAñoCorrido - Una lista de arreglos de 2 objetos, con la información de las ganancias del año actual, 
	 * en el siguiente orden: idoperador, ganancias año actual
	 * @return Una tupla con las ganancias del año actual y del año corrido
	 */
	private Integer[] armarGanancias (BigDecimal AñoActual, BigDecimal AñoCorrido)
	{
		Integer gananciaAñoActual =  0;
		Integer gananciaAñoCorrido =  0;
		
		if (AñoActual != null){
			 gananciaAñoActual =  AñoActual.intValue();
		} 
		if (AñoCorrido != null){
			 gananciaAñoCorrido =  AñoCorrido.intValue();
		} 

		Integer [] ganancias = {gananciaAñoActual, gananciaAñoCorrido};
		
		return ganancias;
	}

	/**
	 * Método que consulta las GANANCIAS ACTUALES Y CORRIDAS de todos los operadores 
	 * @return Una lista de arreglos de objetos de tamaño 3. Los elementos del arreglo corresponden a los datos de las
	 * ganancias del operador:
	 * 		(idoperador, ganancias año actual, ganancias año corrido)
	 */
	public List<Object []> RFC1 () 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		List<Operador>  operadores = sqlOperador.darOperadores(pm);
		List<Object []> listaOperadoresGanancias = new LinkedList<>();
		for (Operador op: operadores){
			Operador opCompleto = darOperadorCompleto(op.getId());
			Object [] info = new Object[3];
			info[0] = op.getId();
			info[1] = opCompleto.getGananciasAñoActual();
			info[2] = opCompleto.getGananciasAñoCorrido();
			listaOperadoresGanancias.add(info);
		}
		return listaOperadoresGanancias;
	}

	/**
	 * Método que consulta las GANANCIAS ACTUALES Y CORRIDAS de todos los operadores
	 * @param idOperador - El identificador del operador
	 * @param ganancia - La nueva ganancia del operador
	 * @return El número de tuplas modificadas
	 */
	public long actualizarGananciasOperador(long idOperador, long ganancia)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlOperador.actualizarGanancias(pm, idOperador, ganancia);
			tx.commit();

			return tuplasInsertadas;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return 0;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
 
	/* ****************************************************************
	 * 			Métodos para manejar DETALLE_OPERADOR
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla DETALLE_OPERADOR
	 * Adiciona entradas al log de la aplicación
     * @param pm - El manejador de persistencia
     * @param idOperador - El identificador del operador
     * @param registro_cc - El registro en camara de comercio del operador
     * @param registro_supert - El registro_supert del operador
     * @param vinculo - El vinculo del operador
	 * @return El objeto Operador adicionado. null si ocurre alguna Excepción
	 */
	public Detalle_operadores adicionarDetalle_operador(long idOperador, Integer registroCC, Integer registro_supert, String vinculo)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlDetalle_operadores.adicionarDetalle_operadores(pm, idOperador, idOperador, idOperador, vinculo);
            tx.commit();
            
            log.trace ("Inserción del detalle del operador con id: " + idOperador + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Detalle_operadores(idOperador, idOperador, idOperador, vinculo);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla DETALLE_OPERADORES, dado el ID del operador
	 * Adiciona entradas al log de la aplicación
	 * @param idOperador - El nombre del operador
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarDetalle_operadorPorId (long idOperador) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlDetalle_operadores.eliminarDetalle_operadoresPorId(pm, idOperador);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/**
	 * Método que consulta todas las tuplas en la tabla DETALLE_OPERADORES
	 * @return La lista de objetos DETALLE_OPERADORES, construidos con base en las tuplas de la tabla OPERADORES
	 */
	public List<Detalle_operadores> darDetalle_operadores ()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            return sqlDetalle_operadores.darDetalle_operadores(pm);
            
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
		
	}

	/**
	 * Método que consulta todas las tuplas en la tabla DETALLE_OPERADORES con un identificador dado
	 * @param idOperador - El identificador del DETALLE_OPERADORES
	 * @return El objeto DETALLE_OPERADORES, construido con base en las tuplas de la tabla DETALLE_OPERADORES con el identificador dado
	 */
	public Detalle_operadores darDetalleOperadoresPorId (long idOperador)
	{
		return sqlDetalle_operadores.darDetalle_operadoresPorId(pmf.getPersistenceManager(), idOperador);
	}

	/* ****************************************************************
	 * 			Métodos para manejar ALOJAMIENTO
	 *****************************************************************/
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla ALOJAMIENTO
	 * Adiciona entradas al log de la aplicación
     * @param pm - El manejador de persistencia
     * @param idOperador - El identificador del operador dueño del alojamiento
     * @param tipo - El tipo del alojamiento
	 * @return El objeto Operador adicionado. null si ocurre alguna Excepción
	 */
	public Alojamiento adicionarAlojamiento(long idOperador, String tipo)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long idAlojamiento = nextval ();
            long tuplasInsertadas = sqlAlojamiento.adicionarAlojamiento(pm,idAlojamiento, idOperador, tipo);
            tx.commit();
            
            log.trace ("Inserción del alojamiento con Id: " + idAlojamiento + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Alojamiento (idAlojamiento, idOperador, tipo);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla ALOJAMIENTO, dado el Id del alojamiento
	 * Adiciona entradas al log de la aplicación
	 * @param idAlojamiento - El identificador del alojamiento
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarAlojamientoPorTipo (long idAlojamiento) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlAlojamiento.eliminarAlojamientoPorId(pm, idAlojamiento);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla ALOJAMIENTO, dado el Id del alojamiento
	 * Adiciona entradas al log de la aplicación
	 * @param idAlojamiento - El identificador del alojamiento
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarAlojamientoPorId (long idAlojamiento) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlAlojamiento.eliminarAlojamientoPorId(pm, idAlojamiento);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/**
	 * Método que consulta todas las tuplas en la tabla ALOJAMIENTO con un identificador dado
	 * @param idAlojamiento - El identificador del ALOJAMIENTO
	 * @return El objeto ALOJAMIENTO, construido con base en las tuplas de la tabla ALOJAMIENTO con el identificador dado
	 */
	public Alojamiento darAlojamientoPorId (long idAlojamiento)
	{
		return sqlAlojamiento.darAlojamientoPorId(pmf.getPersistenceManager(), idAlojamiento);
	}

	/**
	 * Método que consulta todas las tuplas en la tabla ALOJAMIENTO
	 * @return La lista de objetos ALOJAMIENTO, construidos con base en las tuplas de la tabla ALOJAMIENTO
	 */
	public List<Alojamiento> darAlojamientos ()
	{
		return sqlAlojamiento.darAlojamiento(pmf.getPersistenceManager());
	}

	/**
	 * Método que elimima, de manera transaccional, un ALOJAMIENTO y su DETALLE?ALOJ que ha realizado
	 * Si el ALOJAMIENTO está referenciado en alguna otra relación, no se borra ni el alojamiento NI los detalles
	 * @param idAlojamiento - El identificador del alojamiento
	 * @return Un arreglo de dos números que representan el número de operadores eliminados y 
	 * el número de detalles eliminados, respectivamente. [-1, -1] si ocurre alguna Excepción
	 */
	public long []  eliminarAlojamientoYDetalle (long idAlojamiento)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long [] resp = sqlAlojamiento.eliminarAlojamientoYDetalle(pm, idAlojamiento);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return new long[] {-1, -1};
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/**
	 * Método privado para generar las información basica de los servicios del alojamiento: 
	 * La información básica del alojamiento, 
	 * @pid, nombre servicio, costo servicio
	 * @return Una lista de arreglos de objetos SERVICIO.
	 */
	private List<Servicio> armarServicio (List<Object []> tuplas)
	{
		List<Servicio> servicios = new LinkedList <Servicio> ();
		for (Object [] tupla : tuplas)
		{			
			long idServicio = ((BigDecimal) tupla [0]).longValue ();
			String nombreServicio = (String) tupla [1];
			Integer costoServicio = ((BigDecimal) tupla [2]).intValue();

		
			Servicio servicio = new Servicio(idServicio, nombreServicio, costoServicio);
			
			servicios.add(servicio);
		}
		return servicios;
	}

	/**
	 * Método que consulta TODA LA INFORMACIÓN DE UN ALOJAMIENTO con el identificador dado. Incluye la información básica del alojamiento,
	 *  y los servicios que posee.
	 * @param idAlojamiento - El identificador del alojamiento
	 * @return El objeto ALOJAMIENTO, construido con base en las tuplas de la tablas ALOJAMIENTO Y TIENE_SERVICIO
	 * relacionadas con el identificador de operador dado
	 */
	public Alojamiento darAlojamientoCompleto (long idAlojamiento) 
	{

		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            Alojamiento alojamiento = sqlAlojamiento.darAlojamientoPorId(pm, idAlojamiento);
			alojamiento.setServicios(armarServicio(sqlAlojamiento.darServicios(pm, idAlojamiento)));
            tx.commit();
            return alojamiento;
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/**
	 * Método que consulta los ALOJAMIENTOS que son ofertados en un rango de fechas y poseen ciertos servicios.
	 * @param fechaInicial - La fecha inicial
	 * @param fechaFinal - La fecha final
	 * @param listaServicios - Lista de servicios 
	 * @return Lista de objetos tipo ALOJAMIENTO que cumplen con los requisitos de fecha y servicios
	 */
	public List<Alojamiento> RFC4 (Timestamp fechaInicial, Timestamp fechaFinal, List<String> listaServicios) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
			List<Object> ids = sqlAlojamiento.darAlojamientoOfertadosPorFechas(pm, fechaInicial, fechaFinal);
			List<Alojamiento> alojCumplen = new LinkedList<Alojamiento>();
			for (Object id: ids){
				Alojamiento alojCompleto = darAlojamientoCompleto(((BigDecimal)id).longValue());
				List<String> nombreServicios = new ArrayList<String> ();
				for (Servicio servicio: alojCompleto.getServicios()){
					nombreServicios.add(servicio.getNombre());
				}
				if (nombreServicios.containsAll(listaServicios)) alojCumplen.add(alojCompleto);
			}

            tx.commit();
            return alojCumplen;
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}


	/* ****************************************************************
	 * 			Métodos para manejar DETALLE_ALOJ
	 *****************************************************************/

	 /**
	 Método que inserta, de manera transaccional, una tupla en la tabla DETALLE_ALOJ
	 * Adiciona entradas al log de la aplicación
     * @param idAlojamiento - El id del alojamiento
     * @param categoria - La categoria del alojamiento ('Suite', 'Semisuite', 'Estandar', 'Compartida', 'Individual')
     * @param capacidad - La capacidad del alojamiento
     * @param servicios - Si el alojamiento cuenta con servicios o no
     * @param costo_seguro - El costo del seguro si es el caso
     * @param costo_admon - El cosot de la administracion si es el caso
     * @param amoblado -  Si elojamiento esta amoblado o no
	 * @return El objeto Operador adicionado. null si ocurre alguna Excepción
	 */
	public Detalle_aloj adicionarDetalle_aloj(long idAlojamiento, String categoria, int capacidad, int servicios, int costo_seguro, int costo_admon, int amoblado)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlDetalle_aloj.adicionarDetalle_aloj(pm, idAlojamiento, categoria, capacidad, servicios, costo_seguro, costo_admon, amoblado);
            tx.commit();
            
            log.trace ("Inserción del detalle del alojamiento con id : " + idAlojamiento + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Detalle_aloj (idAlojamiento, categoria, capacidad, servicios, costo_seguro, costo_admon, amoblado);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla DETALLE_ALOJ, dado el ID del alojamiento
	 * Adiciona entradas al log de la aplicación
	 * @param idAlojamiento - El identificador del alojamiento
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarDetalle_alojPorId (long idAlojamiento) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlDetalle_aloj.eliminarDetalle_alojPorId(pm, idAlojamiento);
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/**
	 * Método que consulta todas las tuplas en la tabla DETALLE_ALOJ
	 * @return La lista de objetos DETALLE_ALOJ, construidos con base en las tuplas de la tabla DETALE_ALOJ	
	 */
	public List<Detalle_aloj> darDetalle_aloj ()
	{
		return sqlDetalle_aloj.darDetalle_aloj(pmf.getPersistenceManager());
	}

	/**
	 * Método que consulta todas las tuplas en la tabla DETALLE_ALOJ con un identificador dado
	 * @param idAlojamiento - El identificador del DETALLE_ALOJ
	 * @return El objeto DETALLE_ALOJ, construido con base en las tuplas de la tabla DETALLE_ALOJ con el identificador dado
	 */
	public Detalle_aloj darDetalleAlojPorId (long idAlojamiento)
	{
		Object[] detalleInfo =  sqlDetalle_aloj.darDetalle_alojPorId(pmf.getPersistenceManager(), idAlojamiento);
		String categoria = (String) detalleInfo[1];
		int capacidad = ((BigDecimal) detalleInfo[2]).intValue();
		int servicios = ((BigDecimal) detalleInfo[3]).intValue();
		Detalle_aloj detalle_aloj = new Detalle_aloj(idAlojamiento, categoria, capacidad, servicios, 1, 1, 1);
		return detalle_aloj;
	}

	
	/* ****************************************************************
	 * 			Métodos para manejar OFERTA
	 *****************************************************************/

	/**
	 Método que inserta, de manera transaccional, una tupla en la tabla RESERVA
	 respetando las reglas de negocio
	 * Adiciona entradas al log de la aplicación
     * @param pm               - El manejador de persistencia
     * @param idAlojamiento    - El identificador del alojamiento
     * @param idOperador       - El identificador del operador
     * @param fecha            - Fecha de la oferta
     * @param costo            - Csto de la oferta
     * @param tiempoLimiteDias - Tiempo limite de la oferta
	 * @return El objeto Reserva adicionado. null si ocurre alguna Excepción
	 */
	public Oferta adicionarOferta (long idAlojamiento, long idOperador, Timestamp fecha,
	double costo, int tiempoLimiteDias)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
			long idOferta = nextval ();
			long tuplasInsertadas = sqlOferta.adicionarOferta(pm,idOferta, idAlojamiento, idOperador, fecha, costo,
					tiempoLimiteDias);
            tx.commit();
            
            log.trace ("Inserción de una ofert con id : " + idOferta + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Oferta(idOferta, idAlojamiento, idOperador, fecha, costo, tiempoLimiteDias);
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/**
	 * Metodo que retorna una lista con todas las ofertas presentes en la base de datos
	 * @return Una lista de objetos Oferta con todos las ofertas que se encuentran en la base de datos.
	 */
	public List<Oferta> darOfertas() {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			List<Oferta> ofertas = new ArrayList<>();
			List<Object[]> ofertasObject = sqlOferta.darOfertas(pm);
			for (Object[] infoOferta: ofertasObject){
				long idOferta = ((BigDecimal) infoOferta[0]).longValue();
				long idAlojamiento = ((BigDecimal) infoOferta[1]).longValue();
				long idOperador = ((BigDecimal) infoOferta[2]).longValue();
				Timestamp fecha = (Timestamp) infoOferta[3];
				double costo = ((BigDecimal) infoOferta[4]).doubleValue();
				int tiempoLimiteDias = ((BigDecimal) infoOferta[5]).intValue();
				Oferta oferta = new Oferta(idOferta, idAlojamiento, idOperador, fecha, costo, tiempoLimiteDias);
				oferta.setReservada(((BigDecimal) infoOferta[6]).intValue());
				ofertas.add(oferta);
			}
			tx.commit();

			log.trace("Ofertas retornadas");
			return ofertas;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

	}
	/**
	 * Metodo que retorna una oferta dado su id
	 * @param idOferta - El id de la oferta
	 * @return Un objeto Oferta que tiene el id dado
	 */

	public Oferta darOfertaPorId(long idOferta) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Object[] infoOferta = sqlOferta.darOfertaPorId(pm, idOferta);
			long idAlojamiento = ((BigDecimal) infoOferta[1]).longValue();
			long idOperador = ((BigDecimal) infoOferta[2]).longValue();
			Timestamp fecha = (Timestamp) infoOferta[3];
			double costo = ((BigDecimal) infoOferta[4]).doubleValue();
			int tiempoLimiteDias = ((BigDecimal) infoOferta[5]).intValue();
			Oferta oferta = new Oferta(idOferta, idAlojamiento, idOperador, fecha, costo, tiempoLimiteDias);
			tx.commit();

			log.trace("Oferta retornada");
			return oferta;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	public long actualizarOferta (long idOferta, int reservada){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long oferta = sqlOferta.actualizarOferta(pm, idOferta, reservada);
			tx.commit();

			log.trace ("Actualización de la oferta: " + idOferta );
			return oferta;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	/**
	 * Metodo que retorna una lista con todas las ofertas presentes en la base de datos
	 * @return Una lista de objetos Oferta con todos las ofertas que se encuentran en la base de datos.
	 */
	public List<Oferta> darOfertasDisponibles() {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			List<Oferta> ofertas = new ArrayList<>();
			List<Object[]> ofertasObject = sqlOferta.darOfertasDisponibles(pm);
			for (Object[] infoOferta: ofertasObject){
				long idOferta = ((BigDecimal) infoOferta[0]).longValue();
				long idAlojamiento = ((BigDecimal) infoOferta[1]).longValue();
				long idOperador = ((BigDecimal) infoOferta[2]).longValue();
				Timestamp fecha = (Timestamp) infoOferta[3];
				double costo = ((BigDecimal) infoOferta[4]).doubleValue();
				int tiempoLimiteDias = ((BigDecimal) infoOferta[5]).intValue();
				Oferta oferta = new Oferta(idOferta, idAlojamiento, idOperador, fecha, costo, tiempoLimiteDias);
				ofertas.add(oferta);
			}
			tx.commit();

			log.trace("Ofertas retornadas");
			return ofertas;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

	}
	/**
	 * Metodo que elimina una oferta de la base de datos por su id. 
	 * @param id - El id de la oferta a eliminar
	 * @return	- El numero de tuplas eliminadas. -1 si ocurre alguna Excepción.
	 */
	public long eliminarOfertaPorId(long id) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			long tuplasEliminadas = sqlOferta.eliminarOfertaPorId(pm, id);
			tx.commit();

			log.trace("Eliminación de la oferta con id : " + id + ": " + tuplasEliminadas + " tuplas eliminadas");
			return tuplasEliminadas;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	
	/**
	 * Método que consulta las 20 ofertas más populares
	 * @return La lista de objetos OFERTA, construidos con base en las tuplas de la
	 *         tabla OFERTA
	 */
	public List<Long> darOfertasMasPopulares() {
		List<Object> resp = sqlResiden.darOfertasMasReservadas(pmf.getPersistenceManager());
		List<Long> ofertasPopulares = new LinkedList<Long>();
		for (Object x: resp){
			Long id = ((BigDecimal)x).longValue();
			ofertasPopulares.add(id);
		}
		if (ofertasPopulares.size() < 20)
			return ofertasPopulares;
		else
			return ofertasPopulares.subList(0, 19);
	}

	/**
	 * Método que consulta el índice de ocupación de cada una de las ofertas de
	 * alojamiento registradas
	 * @return La lista de objetos OFERTA, construidos con base en las tuplas de la
	 *         tabla OFERTA
	 */
	public List<Oferta> darIndiceOcupacionOfertas(List<Oferta> listaOfertas) {
		for (Oferta oferta : listaOfertas) {
			oferta.setIndiceOcupacion(
					((BigDecimal)sqlAlojamiento.darIndiceOcupacion(pmf.getPersistenceManager(), oferta.getIdAlojamiento())).doubleValue());
		}
		return listaOfertas;
	}

	/**
	 * Método que consulta el índice de ocupación de cada una de las ofertas de
	 * @param idOferta 		- El identificador de la oferta
	 */
	public void agregarDesahabilitacion (long idOferta) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			sqlOferta.agregarDesahabilitacion(pm, idOferta);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que consulta el índice de ocupación de cada una de las ofertas de
	 * @param idOferta 		- El identificador de la oferta
	 * @return El número de tuplas modificadas
	 */
	public long eliminarDesahabilitacion (long idOferta) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			long tuplasEliminadas = sqlOferta.eliminarDesahabilitacion(pm, idOferta);
			tx.commit();

			log.trace("Eliminación de la oferta con id : " + idOferta + ": " + tuplasEliminadas + " tuplas eliminadas");
			return tuplasEliminadas;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que consulta el índice de ocupación de cada una de las ofertas de
	 * @param idOperador 		- El identificador del operador
	 * @return La lista de objetos OFERTA, construidos con base en las tuplas de la
	 */
	public List<Oferta> darOfertasDesahabilitadasPorOp (long idOperador){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			List<Oferta> ofertas = new ArrayList<>();
			List<Object[]> ofertasObject = sqlOferta.darOfertasDesahabilitadasPorOp(pm, idOperador);
			for (Object[] infoOferta: ofertasObject){
				long idOferta = ((BigDecimal) infoOferta[0]).longValue();
				long idAlojamiento = ((BigDecimal) infoOferta[1]).longValue();
				Timestamp fecha = (Timestamp) infoOferta[3];
				double costo = ((BigDecimal) infoOferta[4]).doubleValue();
				int tiempoLimiteDias = ((BigDecimal) infoOferta[5]).intValue();
				Oferta oferta = new Oferta(idOferta, idAlojamiento, idOperador, fecha, costo, tiempoLimiteDias);
				oferta.setReservada(((BigDecimal) infoOferta[6]).intValue());
				ofertas.add(oferta);
			}
			tx.commit();

			log.trace("Ofertas retornadas");
			return ofertas;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		
	}
	/* ****************************************************************
	 * 			Métodos para manejar RESERVA
	 *****************************************************************/

	/**
	 Método que inserta, de manera transaccional, una tupla en la tabla RESERVA
	 respetando las reglas de negocio
	 * Adiciona entradas al log de la aplicación
     * @param idResidente - El id del residente que pone la reserva
     * @param duracionDias - La duracion en dias de la reserva
	 * @param fecha - La fecha de la reserva
	 * @return El objeto Reserva adicionado. null si ocurre alguna Excepción
	 */
	public Reserva adicionarReserva (long idResidente, int duracionDias, Timestamp fecha)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
			long idReserva = nextval ();
			long tuplasInsertadas = sqlReserva.adicionarReserva(pm,idReserva, idResidente, fecha, duracionDias);
            tx.commit();
	        
            log.trace ("Inserción da reserva del alojamiento con id : " + idReserva + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Reserva(idReserva, idResidente, fecha, duracionDias);
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	

	/**
	 * Método que consulta todas las tuplas en la tabla RESERVA
	 * @param pm - El manejador de persistencia
	 * @return La lista de objetos Reserva, construidos con base en las tuplas de la
	 *         tabla RESERVA
	 */
	public List<Reserva> darReservas() {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			List<Object[]> infoReserva = sqlReserva.darReservas(pm);
			List<Reserva> reservas = new ArrayList<>();
			for (Object[] info: infoReserva){
				long idReserva = ((BigDecimal) info[0]).longValue();
				long idResidente = ((BigDecimal) info[1]).longValue();
				Timestamp fecha = (Timestamp) info[2];
				int duracionDias = ((BigDecimal) info[3]).intValue();
				Reserva reserva = new Reserva(idReserva, idResidente, fecha, duracionDias);
				reserva.setActivo(((BigDecimal) info[4]).intValue());
				reservas.add(reserva);

			}
			tx.commit();
			log.trace("Oferta retornada");
			return reservas;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}


	/**
	 * Metodo que elimina, de manera transaccional, una tupla en la tabla RESERVA.
	 * @param id - El identificador de la reserva.
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción.
	 */
	public long eliminarReservaPorId(long id) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			long tuplasEliminadas = sqlReserva.eliminarReservaPorId(pm, id);
			tx.commit();

			log.trace("Eliminación de la reserva con id : " + id + ": " + tuplasEliminadas + " tuplas eliminadas");
			return tuplasEliminadas;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que retira una reserva de la base de datos por su id y la retorna 
	 * @param id - El id de la reserva a retirar
	 * @return La reserva retirada
	 */
	public Reserva retirarReservaPorId(long id) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			long resp = sqlReserva.retirarReservaPorId(pm, id);
			tx.commit();
			
			tx.begin();
			Reserva reserva = darReservaPorid(id);
			reserva.setActivo(0);
			tx.commit();

			log.trace("Retirar la reserva con id : " + id + ": " + resp + " tuplas actualizadas");
			return reserva;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que actualiza todas las tuplas en la tabla RESERVA que tienen el id dado
	 * @param id - El id de la reserva a actualizar
	 * @return El número de tuplas actualizadas. -1 si ocurre alguna Excepción.
	 */
	public Long actualizarReservaColectiva(long id, long idColectiva){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Long resp = sqlReserva.actualizarReservaColectiva(pm, id, idColectiva);
			tx.commit();

			log.trace("Actualizar la reserva con id : " + id + ": " + resp + " tuplas actualizadas");
			return resp;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que actualiza todas las tuplas en la tabla RESERVA que tienen el id dado
	 * @param id - El id de la reserva a actualizar
	 * @return El número de tuplas actualizadas. -1 si ocurre alguna Excepción.
	 */
	public Long actualizarReservaColectiva(long id){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Long resp = sqlReserva.actualizarReservaColectiva(pm, id);
			tx.commit();

			log.trace("Actualizar la reserva con id : " + id + ": " + resp + " tuplas actualizadas");
			return resp;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}
	/**
	 * Método que actualiza todas las tuplas en la tabla RESERVA que tienen el id dado
	 * @param id - El id de la reserva a actualizar
	 * @return El número de tuplas actualizadas. -1 si ocurre alguna Excepción.
	 */
	public Long deagregarReservaColectiva(long id){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Long resp = sqlReserva.desagregarReservaColectiva(pm, id);
			tx.commit();

			log.trace("Actualizar la reserva con id : " + id + ": " + resp + " tuplas actualizadas");
			return resp;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla RESERVA que tienen el identificador dado
	 * @param id - El identificador de la reserva
	 * @return El objeto Reserva, construido con base en la tuplas de la tabla RESERVA, que tiene el identificador dado
	 */
	public Reserva darReservaPorid (Long id)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			
			
			Object[]  resp = sqlReserva.darReservaPorId(pm, id);
			long idOferta = ((BigDecimal) resp[0]).longValue();
			long idResidente = ((BigDecimal) resp[1]).longValue();
			Timestamp fecha = (Timestamp) resp[2];
			int duaracionDias = ((BigDecimal) resp[3]).intValue();
			Reserva reserva = new Reserva(idOferta, idResidente, fecha, duaracionDias);
		
			tx.commit();
			
			log.trace ("Servicio: " + id);
			
			return reserva ;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que consulta  si un residente ya ha hecho una reserva en el dia actual
	 * @param pm - El manejador de persistencia
     * @param idResidente - El identificador del residente
	 * @return Un numero que representa el numero de reservas en esa fecha
	 */
	public BigDecimal sePuedeReservar (long idResidente)
	{
		
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            BigDecimal resp = sqlReserva.sePuedeReservar(pm, idResidente);
            tx.commit();
            
            log.trace ("El risidente tiene : " + resp + "reserve hoy");
            
            return resp;
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
		
	}

	public List<Long> darReservasIdColectivas(long idResidente){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			List<Long> resp = sqlReserva.darReservasIdColectivas(pm, idResidente);
			tx.commit();
			
			log.trace ("Dar las reservas colectivas del residente: " + idResidente);
			
			return resp;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
		
	}

	public List<Reserva> darReservasColectivas (long idColectiva){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			List<Object[]> resp = sqlReserva.darReservasColectivas(pm, idColectiva);
			tx.commit();
			
			List<Reserva> reservas = new LinkedList<>();
			for (Object[] objects : resp) {
				long idOferta = ((BigDecimal) objects[0]).longValue();
				long idResidente = ((BigDecimal) objects[1]).longValue();
				Timestamp fecha = (Timestamp) objects[2];
				int duaracionDias = ((BigDecimal) objects[3]).intValue();
				Reserva reserva = new Reserva(idOferta, idResidente, fecha, duaracionDias);
				reservas.add(reserva);
			}
			
			log.trace ("Dar las reservas colectivas del residente: " + idColectiva);
			
			return reservas;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
		
	}

	/* ****************************************************************
	 * 			Métodos para manejar RESIDEN
	 *****************************************************************/

	 /**
	 Método que inserta, de manera transaccional, una tupla en la tabla RESIDEN
	 respetando las reglas de negocio
	 * Adiciona entradas al log de la aplicación
     * @param idResidente - El manejador de persistencia
     * @param idOferta    - El identificador del alojamiento
	 * @return El objeto Residen adicionado. null si ocurre alguna Excepción
	 */
	public Residen adicionResiden (long idReserva, long idOferta)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlResiden.adicionarResiden(pm, idReserva, idOferta);
            tx.commit();
            
            log.trace ("Inserción de una tupla residen con idResidente : " + idReserva + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Residen(idReserva, idOferta);
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	

	/**
	 * Método que consulta todas las tuplas en la tabla RESIDEN
	 * @return La lista de objetos Residen, construidos con base en las tuplas de la tabla RESIDEN.
	 */
	public List<Residen> darResiden() {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			List<Residen> reservas = sqlResiden.darResiden(pm);
			tx.commit();

			log.trace("Reservas retornadas");
			return reservas;
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}

	}

	/**
	 * Método que consulta todas las tuplas en la tabla RESIDEN que tienen el identificador dado
	 * @param idReserva - El identificador de la reserva
	 * @return El objeto Residen, construido con base en la tuplas de la tabla RESIDEN, que tiene el identificador dado
	 */
	public long eliminarResiden(long idReserva) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Residen Residen = darResidenPorIdReserva(idReserva);
			long idOferta = Residen.getIdOferta();
			tx.commit();
			tx.begin();
			long tuplasEliminadas = sqlResiden.eliminarResidenPorIdReserva(pm, idReserva);
			tx.commit();
			tx.begin();
			Oferta Oferta = darOfertaPorId(idOferta);
			long idAlojamiento = Oferta.getIdAlojamiento();
			long resp = sqlAlojamiento.actualizarAlojamiento(pm, idAlojamiento);
			tx.commit();
			log.trace("Eliminación de oferta: " + idReserva + ": " + tuplasEliminadas + " tuplas eliminadas");
			return tuplasEliminadas;
		} catch (Exception e) {
			log.error("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que consulta todas las tuplas en la tabla RESIDEN que tienen el identificador dado
	 * @param idOferta - El identificador de la oferta
	 * @return El objeto Residen, construido con base en la tuplas de la tabla RESIDEN, que tiene el identificador dado.
	 */
	public long eliminarResidenOf(long idOferta) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			long tuplasEliminadas = sqlResiden.eliminarResidenPorIdOferta(pm, idOferta);
			tx.commit();
			log.trace("Eliminación de oferta: " + idOferta + ": " + tuplasEliminadas + " tuplas eliminadas");
			return tuplasEliminadas;
		} catch (Exception e) {
			log.error("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Metodo que el elimina un Residen por su id de residente y oferta
	 * @param idResidente - El identificador del residente
	 * @param idOferta - El identificador de la oferta
	 * @return El numero de tuplas eliminadas.
	 */
	public long eliminarResidenPorId(long idResidente, long idOferta) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();

			long resp = sqlResiden.eliminarResidenPorId(pm, idResidente, idOferta);
			tx.commit();

			log.trace("Residen eliminado: " + idResidente + " " + idOferta);
			return resp;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return 0;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que consulta todas las tuplas en la tabla RESIDEN que tienen el identificador dado
	 * @param idReserva - El identificador de la reserva
	 * @return El objeto Residen, construido con base en la tuplas de la tabla RESIDEN, que tiene el identificador dado
	 */
	public Residen darResidenPorIdReserva (long idReserva)
	{
		return sqlResiden.darResidenPorIdReserva(pmf.getPersistenceManager(), idReserva);
	}
	
	/**
	 * Método que consulta todas las tuplas en la tabla RESIDEN que tienen el identificador dado
	 * @param idOferta - El identificador de la oferta
	 * @return El objeto Residen, construido con base en la tuplas de la tabla RESIDEN, que tiene el identificador dado
	 */
	public List<Residen> darResidenPorIdOferta (long idOferta)
	{
		return sqlResiden.darResidenPorIdOferta(pmf.getPersistenceManager(), idOferta);
	}

	/* ****************************************************************
	 * 			Métodos para manejar RESIDENTE
	 *****************************************************************/

	/**
	 * Metodo que inserta, de manera transaccional, una tupla en la tabla RESIDENTE
	 * @param nombre -	El nombre del residente.
	 * @param vinculo - El vinculo del residente.
	 * @return El objeto Residente adicionado. null si ocurre alguna Excepción.
	 */
	public Residente adicionarResidente(String nombre, String vinculo) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			long idResidente = nextval();
			long tuplasInsertadas = sqlResidente.adicionarResidente(pm,idResidente, nombre, vinculo);
			tx.commit();

			log.trace(
					"Inserción de la reserva con id : " + idResidente + ": " + tuplasInsertadas + " tuplas insertadas");
			return new Residente(idResidente, nombre, vinculo);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Metodo que consulta todas las tuplas en la tabla RESIDENTE.
	 * @return La lista de objetos Residente, construidos con base en las tuplas de la tabla RESIDENTE.
	 */
	public List<Residente> darResidentes() {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			List<Residente> resp = new LinkedList<Residente>();
			List<Object[]> residentes = sqlResidente.darResidentes(pm);

			for (Object[] res: residentes){
				Long idResidente = ((BigDecimal)res[0]).longValue();
				String nombre = (String) res[1];
				String vinculo = (String) res[2];

				Residente residente = new Residente(idResidente, nombre, vinculo);
				resp.add(residente);
			}
		
			tx.commit();
			log.trace("Se encontraron: " + residentes.size() + " residentes" );


			return resp;

		} catch (Exception e) {
			log.error("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Metodo que consulta todas las tuplas en la tabla RESIDENTE que tienen el identificador dado
	 * @param idResidente - El identificador del residente.
	 * @return El objeto Residente, construido con base en la tuplas de la tabla RESIDENTE, que tiene el identificador dado.
	 */
	public long eliminarResidentePorId(long idResidente) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();

			long resp = sqlResidente.eliminarResidentePorId(pm, idResidente);
			tx.commit();

			log.trace("Residente eliminado: " + idResidente);
			return resp;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return 0;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}
	

	/* ****************************************************************
	 * 			Métodos para manejar TIENE_SERVICIO
	 *****************************************************************/

	 /**
	 Método que inserta, de manera transaccional, una tupla en la tabla TS
	 respetando las reglas de negocio
	 * Adiciona entradas al log de la aplicación
     * @param idAlojamiento - El id del alojamiento
     * @param idServicio    - El identificador del servicio
	 * @return El objeto Residen adicionado. null si ocurre alguna Excepción
	 */
	public TieneServicio adicionTS (long idAlojamiento, long idServicio)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlTiene_servicio.adicionarTiene_servicio(pm, idAlojamiento, idServicio);
            tx.commit();
            
            log.trace ("Inserción de una tupla TS con idAlojamiento : " + idAlojamiento + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new TieneServicio(idAlojamiento, idServicio) ;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	/* ****************************************************************
	 * 			Métodos para manejar SERVICIO
	 *****************************************************************/

	 /**
	 Método que inserta, de manera transaccional, una tupla en la tabla TS
	 respetando las reglas de negocio
	 * Adiciona entradas al log de la aplicación
     * @param nombre - El nombre del servicio
     * @param costo    - El costo del servicio
	 * @return El objeto Residen adicionado. null si ocurre alguna Excepción
	 */
	public Servicio adicionServicio (String nombre, double costo)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
			long idServicio = nextval();
			long tuplasInsertadas = sqlServicio.adicionarServicio(pm, idServicio, nombre, costo);
            tx.commit();
            
            log.trace ("Inserción de un servicio con idAlojamiento : " + idServicio + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new Servicio(idServicio, nombre, costo) ;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	//Dar servicio por id
	public Servicio darServicioPorid (Long id)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
			
            Object[]  resp = sqlServicio.darServicioPorId(pm, id);
            tx.commit();
            
            log.trace ("Servicio: " + id);
            long idServicio = ((BigDecimal)resp[0]).longValue();
			String nombre = (String) resp[1];
			Integer costo = ((BigDecimal)resp[0]).intValue();
            return new Servicio(idServicio, nombre, costo) ;
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}

	public long getNextVal(){
		return nextval();
	}


	
	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos de Alohandes
	 * Crea y ejecuta las sentencias SQL para cada tabla de la base de datos - EL ORDEN ES IMPORTANTE 
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas.
	 */
	public long [] limpiarAlohandes ()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long [] resp = sqlUtil.limpiarAlohandes(pm);
            tx.commit ();
            log.info ("Borrada la base de datos");
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return new long[] {-1, -1, -1, -1, -1, -1, -1};
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
		
	}
	// darServicios()
	public List<Servicio> darServicios() {
		return sqlServicio.darServicios(pmf.getPersistenceManager());
	}
	// eliminarServicioPorId(idServicio)
	public long eliminarServicioPorId(long idServicio) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();

			long resp = sqlServicio.eliminarServicioPorId(pm, idServicio);
			tx.commit();

			log.trace("Servicio eliminado: " + idServicio);
			return resp;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return 0;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}
	
	public List<Object[]> RFC5() {
		return sqlReserva.rfc5(pmf.getPersistenceManager());
	}

	public List<Object[]> RFC6(Integer resp1) {
		return sqlReserva.rfc6(pmf.getPersistenceManager(), resp1);
	}

	public List<Map> RFC7(String resp1) {
		Map<Integer, Integer> map = new HashMap<>();
		Map<Integer, Integer> map2 = new HashMap<>();
		List<Map> listMap = new ArrayList<>();
		List<Object> response = new ArrayList<>();
		List<Object> list = new ArrayList<>();
		List<Object> list2 = new ArrayList<>();


		if (resp1.equals("mes")) {
			// for i in 1 to 12
			for (int i = 1; i <= 12; i++) {

				response = sqlReserva.rfc7mes(pmf.getPersistenceManager(), i);
				Integer suma = 0;
				map.put(i, response.size());
				// for object in object add suma
				for (int j = 0; j < response.size(); j++) {
					suma += Integer.valueOf(response.get(j).toString());
				}
				map2.put(i, suma);
			}
		} else if (resp1.equals("anio")) {
			list = sqlReserva.rfc7anio(pmf.getPersistenceManager(), 2022);
			list2 = sqlReserva.rfc7anio(pmf.getPersistenceManager(), 2023);
			Integer suma = 0;
			Integer suma2 = 0;
			// for object in object add suma

			for (int j = 0; j < list.size(); j++) {
				suma += Integer.valueOf(list.get(j).toString());
			}
			for (int j = 0; j < list2.size(); j++) {
				suma2 += Integer.valueOf(list2.get(j).toString());
			}

			map2.put(2022, suma);
			map2.put(2023, suma2);
			map.put(2022, list.size());
			map.put(2023, list2.size());
		} else {
			// for i in 1 to 53

			for (int i = 1; i <= 53; i++) {
				list = sqlReserva.rfc7semana(pmf.getPersistenceManager(), i);
				Integer suma = 0;

				for (int j = 0; j < list.size(); j++) {
					suma += Integer.valueOf(list.get(j).toString());
				}

				
				map2.put(i, suma);
				map.put(i, list.size());
			}
		}

		listMap.add(map);
		listMap.add(map2);
		return listMap;
	}

	public List<Object> RFC8one(Integer resp1) {
		List<Object> list = new ArrayList<>();
		list = sqlReserva.rfc8one(pmf.getPersistenceManager(), resp1);
		return list;
	}

	public List<Object> RFC8two(Integer resp2) {
		List<Object> list = new ArrayList<>();
		list = sqlReserva.rfc8two(pmf.getPersistenceManager(), resp2);
		return list;
	}

	public List<Object> RFC9() {
		List<Object> list = sqlOferta.ofertasConPocaDemanda(pmf.getPersistenceManager());
		return list;
	}




}
