
package uniandes.isis2304.parranderos.negocio;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import com.google.gson.JsonObject;

import oracle.net.aso.p;
import uniandes.isis2304.parranderos.persistencia.PersistenciaAlohandes;

/**
 * Clase principal del negocio
 * Sarisface todos los requerimientos funcionales del negocio
 *
 */
public class Alohandes 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(Alohandes.class.getName());
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia
	 */
	private PersistenciaAlohandes pa;
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * El constructor por defecto
	 */
	public Alohandes ()
	{
		pa = PersistenciaAlohandes.getInstance ();
	}
	
	/**
	 * El constructor qye recibe los nombres de las tablas en tableConfig
	 * @param tableConfig - Objeto Json con los nombres de las tablas y de la unidad de persistencia
	 */
	public Alohandes (JsonObject tableConfig)
	{
		pa = PersistenciaAlohandes.getInstance (tableConfig);
	}
	
	/**
	 * Cierra la conexión con la base de datos (Unidad de persistencia)
	 */
	public void cerrarUnidadPersistencia ()
	{
		pa.cerrarUnidadPersistencia ();
	}

	/* ****************************************************************
	 * 			Métodos para manejar el OPERADOR
	 *****************************************************************/

	 /**
	 * Adiciona de manera persistente un operador 
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del operador
     * @param tipo - El tipo de operador
	 * @return El objeto Operador adicionado. null si ocurre alguna Excepción
	 */
	public Operador adicionarOperador (String nombre, String tipo)
	{
        log.info ("Adicionando operador: " + nombre);
        Operador operador = pa.adicionarOperador (nombre, tipo);		
        log.info ("Adicionando operador: " + operador);
        return operador;
	}

	/**
	 * Elimina de manera persistente un operador por id
	 * Adiciona entradas al log de la aplicación
	 * @param idOperador - El identificador del operador
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarOperadorporId (long idOperador)
	{
        log.info ("Eliminando operador por Id: " + idOperador);
        long resp = pa.eliminarOperadorPorId(idOperador);		
        log.info ("Eliminado operador por Id: " + idOperador);
        return resp;
	}

	/**
	 * Elimina de manera persistente un operador y su detalle por id
	 * Adiciona entradas al log de la aplicación
	 * @param idOperador - El identificador del operador
	 * @return El número de tuplas eliminadas
	 */
	public long[] eliminarOperadorYDetalle (long idOperador)
	{
        log.info ("Eliminando operador y detalle por Id: " + idOperador);
        long[] resp = pa.eliminarOperadorYDetalle(idOperador);	
        log.info ("Eliminado operador y detalle por Id: " + idOperador);
        return resp;
	}

	/**
	 * Encuentra todos los operadores en Alohandese
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Operador con todos las operador que conoce la aplicación, llenos con su información básica
	 */
	public List<Operador> darOperadores ()
	{
        log.info ("Listando Operadores");
        List<Operador> operadores = pa.darOperadores ();	
        log.info ("Listando Operadores: " + operadores.size() + " operadores existentes");
        return operadores;
	}
	
	/**
	 * Encuentra todos los Operadores en Alhandes y los devuelve como VOOperador
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOBebedor con todos las bebedores que conoce la aplicación, llenos con su información básica
	 */
	public List<VOOperador> darVOOperadores ()
	{
        log.info ("Generando los VO de Operadores");
        List<VOOperador> voOperadores = new LinkedList<VOOperador> ();
        for (Operador op : pa.darOperadores())
        {
        	voOperadores.add (op);
        }
        log.info ("Generando los VO de Operadores: " + voOperadores.size() + " operadores existentes");
       return voOperadores;
	}

	/**
	 * Encuentra un operador por Id
	 * Adiciona entradas al log de la aplicación
	 * @param IdOperador - Identificador del operador
	 * @return Un objeto de tipo Operador
	 */
	public Operador darOperadorPorId (long idOperador)
	{
        log.info ("Encontrando operador por Id " + idOperador);
        Operador operador = pa.darOperadorPorId(idOperador);	
        log.info ("Encontrado operador: " + operador);
        return operador;
	}

	/**
	 * Encuentra un operador, su información básica y sus alojamientos, ofertas y ganancias
	 * con las que está directamente relacionado, según su identificador
	 * @param idOperador - El identificador del operador buscado
	 * @return Un objeto Operador que corresponde con el identificador buscado y lleno con su información básica y 
	 * 		los alojamientos y ofertas con los que está directamente relacionado
	 * 			null, si un operador con dicho identificador no existe
	 */
	public Operador darOperadorCompleto (long idOperador)
	{
        log.info ("Dar información COMPLETA de un operador por id: " + idOperador);
        Operador operador = pa.darOperadorCompleto(idOperador);
        log.info ("Buscando operador por Id: " + operador.toStringCompleto());
        return operador;
	}

	/**
	 * Método que consulta las GANANCIAS ACTUALES Y CORRIDAS de todos los operadores 
	 * @return Una lista de arreglos de objetos de tamaño 3. Los elementos del arreglo corresponden a los datos de las
	 * ganancias del operador:
	 * 		(idoperador, ganancias año actual, ganancias año corrido)
	 */
	public List<Object []>  RFC1 ()
	{
        log.info ("Dar las ganancias actuales y corridas de todos los operadores ");
        List<Object []> listaOperadoresGanancias = pa.RFC1();
        log.info ("Encontradas las ganancias de " + listaOperadoresGanancias.size() + "operadores");
        return listaOperadoresGanancias;
	}

	/* ****************************************************************
	 * 			Métodos para manejar DETALLE_OPERADOR
	 *****************************************************************/

	/**
	 * Adiciona de manera persistente los detalles de un operador
	 * Adiciona entradas al log de la aplicación
     * @param idOperador - El identificador del operador
     * @param registro_cc - El registro en camara de comercio del operador
     * @param registro_supert - El registro_supert del operador
     * @param vinculo - El vinculo del operador
	 * @return El objeto Detalle_operador adicionado.
	 */
	public Detalle_operadores adicionarDetalle_operador(long idOperador, Integer registroCC, Integer registro_supert, String vinculo)
	{
		log.info ("Adicionando detalles del operador con id " + idOperador);
        Detalle_operadores detalleOperadores = pa.adicionarDetalle_operador(idOperador, registroCC, registro_supert, vinculo);
        log.info ("Adicionado detalles del operador con id: " + idOperador);
        return detalleOperadores;
	}

	/**
	 * Elimina de manera persistente un los detalles de un operador por id
	 * Adiciona entradas al log de la aplicación
	 * @param idOperador - El identificador del operador
	 * @return El número de tuplas eliminadas
	 */
	public long  eliminarDetalle_operadorPorId (long idOperador)
	{
        log.info ("Eliminando detalles del operador por Id: " + idOperador);
        long resp = pa.eliminarDetalle_operadorPorId(idOperador);		
        log.info ("Eliminado detalles del operador por Id: " + idOperador);
        return resp;
	}

	/**
	 * Encuentra todos los detalles operadores en Alohandese
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Operador con todos las operador que conoce la aplicación, llenos con su información básica
	 */
	public List<Detalle_operadores> darDetalleOperadores ()
	{
        log.info ("Listando DO");
        List<Detalle_operadores> operadores = pa.darDetalle_operadores();	
        log.info ("Listando DO: " + operadores.size() + " operadores existentes");
        return operadores;
	}
	
	/**
	 * Encuentra todos los Detalles en Alohandes y los devuelve como VODetalle_operadores
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VODetalle_operadores con todos los que conoce la aplicación, llenos con su información básica
	 */
	public List<VODetalle_operadores> darVODetalle_operadores ()
	{
        log.info ("Generando los VO de DO");
        List<VODetalle_operadores> VDO = new LinkedList<VODetalle_operadores> ();
        for (Detalle_operadores detalle : pa.darDetalle_operadores())
        {
        	VDO.add (detalle);
        }
        log.info ("Generando los VO de Operadores: " + VDO.size() + " operadores existentes");
       return VDO;
	}

	/**
	 * Encuentra un detalle de operador por Id
	 * Adiciona entradas al log de la aplicación
	 * @param IdOperador - Identificador del operador
	 * @return Un objeto de tipo DetalleOperador
	 */
	public Detalle_operadores darDetalleOperadorPorId (long idOperador)
	{
        log.info ("Encontrando operador por Id " + idOperador);
        Detalle_operadores operador = pa.darDetalleOperadoresPorId(idOperador);	
        log.info ("Encontrado operador: " + operador);
        return operador;
	}

	/* ****************************************************************
	 * 			Métodos para manejar ALOJAMIENTO
	 *****************************************************************/

	  /**
	 * Adiciona de manera persistente un alojamiento 
	 * Adiciona entradas al log de la aplicación
	 * @param idOperador - El identificador del operador
     * @param tipo - El tipo de operador
	 * @return El objeto Operador adicionado. null si ocurre alguna Excepción
	 */
	public Alojamiento adicionarAlojamiento (long idOperador, String tipo)
	{
        log.info ("Adicionando alohamiento al operador con id: " + idOperador);
        Alojamiento alojamiento = pa.adicionarAlojamiento(idOperador, tipo);		
        log.info ("Adicionado alojamiento al operador: " + idOperador);
        return alojamiento;
	}

	/**
	 * Elimina de manera persistente un alojamiento por id
	 * Adiciona entradas al log de la aplicación
	 * @param idAlojamiento - El identificador del operador
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarAlojamientoporId (long idAlojameinto)
	{
        log.info ("Eliminando alojameinto por Id: " + idAlojameinto);
        long resp = pa.eliminarAlojamientoPorId(idAlojameinto);		
        log.info ("Eliminado alojameinto por Id: " + idAlojameinto);
        return resp;
	}
	/**
	 * Elimina de manera persistente un aloajmiento y su detalle por id
	 * Adiciona entradas al log de la aplicación
	 * @param idAlojamiento - El identificador del operador
	 * @return El número de tuplas eliminadas
	 */
	public long[] eliminarAlojamientoYDetalle (long idAlojameinto)
	{
        log.info ("Eliminando alojamiento y detalle por Id: " + idAlojameinto);
        long[] resp = pa.eliminarAlojamientoYDetalle(idAlojameinto);	
        log.info ("Eliminado alojamiento y detalle por Id: " + idAlojameinto);
        return resp;
	}

	/**
	 * Encuentra todos los alojamientos en Alohandese
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Operador con todos las operador que conoce la aplicación, llenos con su información básica
	 */
	public List<Alojamiento> darAlojamientos ()
	{
        log.info ("Listando Alojamientos");
        List<Alojamiento> alojamientos = pa.darAlojamientos();	
        log.info ("Listando Alojamientos: " + alojamientos.size() + " alojamientos existentes");
        return alojamientos;
	}
	
	/**
	 * Encuentra todos los Alojamientos en Alhandes y los devuelve como VOAlojamiento
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOBebedor con todos las bebedores que conoce la aplicación, llenos con su información básica
	 */
	public List<VOAlojamiento> darVOAlojamientos ()
	{
        log.info ("Generando los VO de Alojamientos");
        List<VOAlojamiento> voAlojamientos = new LinkedList<VOAlojamiento> ();
        for (Alojamiento al : pa.darAlojamientos())
        {
        	voAlojamientos.add (al);
        }
        log.info ("Generando los VO de Alojamientos: " + voAlojamientos.size() + " alojamientos existentes");
       return voAlojamientos;
	}

	/**
	 * Encuentra un alojamiento por Id
	 * Adiciona entradas al log de la aplicación
	 * @param idAlojamiento - Identificador del operador
	 * @return Un objeto de tipo Operador
	 */
	public Alojamiento darAlojamientoPorId (long idAlojamiento)
	{
        log.info ("Encontrando alojamiento por Id " + idAlojamiento);
        Alojamiento alojamiento = pa.darAlojamientoPorId(idAlojamiento);	
        log.info ("Encontrado alojamiento: " + alojamiento);
        return alojamiento;
	}

	/**
	 * Encuentra un alojamiento, su información básica y sus servicios
	 * con las que está directamente relacionado, según su identificador
	 * @param idAlojamiento - El identificador del operador buscado
	 * @return Un objeto Alojamiento que corresponde con el identificador buscado y lleno con su información básica y 
	 * 		los servicios con los que está directamente relacionado
	 * 			null, si un alojamiento con dicho identificador no existe
	 */
	public Alojamiento darAlojamientoCompleto (long idAlojamiento)
	{
        log.info ("Dar información COMPLETA de un alojamiento por id: " + idAlojamiento);
        Alojamiento alojamiento = pa.darAlojamientoCompleto(idAlojamiento);
        log.info ("Buscando alojamiento por Id: " + alojamiento.toString()!= null ? alojamiento : "NO EXISTE");
        return alojamiento;
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
		log.info ("Dar lista alojamientos ofertados: " );
        List<Alojamiento>  alojamientos = pa.RFC4(fechaInicial, fechaFinal, listaServicios);
        log.info ("Generando Alojamientos: " + alojamientos);
		for (Alojamiento aloj: alojamientos){
			log.info ("Alojamiento con Id: " + aloj.getId() + " y servicios: " + aloj.getServicios());
		}
        return alojamientos;	
	}

	
	/* ****************************************************************
	 * 			Métodos para manejar DETALLE_ALOJ
	 *****************************************************************/

	/**
	 * Adiciona de manera persistente los detalles de un alojamiento
	 * Adiciona entradas al log de la aplicación
     * @param idAlojamiento - El id del alojamiento
     * @param categoria - La categoria del alojamiento ('Suite', 'Semisuite', 'Estandar', 'Compartida', 'Individual')
     * @param capacidad - La capacidad del alojamiento
     * @param servicios - Si el alojamiento cuenta con servicios o no
     * @param costo_seguro - El costo del seguro si es el caso
     * @param costo_admon - El cosot de la administracion si es el caso
     * @param amoblado -  Si elojamiento esta amoblado o no
	 * @return El objeto Detalle_aloj adicionado.
	 */
	public VODetalle_aloj adicionarDetalle_aloj(long idAlojamiento, String categoria, int capacidad, int servicios,
			int costo_seguro, int costo_admon, int amoblado)
	{
		log.info ("Adicionando detalles del alojamiento con id " + idAlojamiento);
        Detalle_aloj detalleAlojamientos = pa.adicionarDetalle_aloj(idAlojamiento, categoria, capacidad, servicios, costo_seguro, costo_admon, amoblado);
        log.info ("Adicionado detalles del alojamiento con id: " + idAlojamiento);
        return detalleAlojamientos;
	}

	/**
	 * Elimina de manera persistente un los detalles de un alojamiento por id
	 * Adiciona entradas al log de la aplicación
	 * @param idAlojamiento - El identificador del alojamiento
	 * @return El número de tuplas eliminadas
	 */
	public long  eliminarDetalle_alojPorId (long idAlojameinto)
	{
        log.info ("Eliminando detalles del alojamiento por Id: " + idAlojameinto);
        long resp = pa.eliminarDetalle_alojPorId(idAlojameinto);		
        log.info ("Eliminado detalles del alojamiento por Id: " + idAlojameinto);
        return resp;
	}

	/**
	 * Encuentra todos los detalles alojamiento en Alohandese
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos detalle_aloj con todos los alojamientos que conoce la aplicación, llenos con su información básica
	 */
	public List<Detalle_aloj> darDetalleAlojamientos ()
	{
        log.info ("Listando Alojamientos");
        List<Detalle_aloj> alojamientos = pa.darDetalle_aloj();	
        log.info ("Listando Operadores: " + alojamientos.size() + " alojamientos existentes");
        return alojamientos;
	}
	
	/**
	 * Encuentra todos los Detalles en Alohandes y los devuelve como VODetalle_aloj
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VODetalle_aloj con todos las bebedores que conoce la aplicación, llenos con su información básica
	 */
	public List<VODetalle_aloj> darVODetalle_aloj ()
	{
        log.info ("Generando los VO de Operadores");
        List<VODetalle_aloj> VAL = new LinkedList<VODetalle_aloj> ();
        for (Detalle_aloj detalle : pa.darDetalle_aloj())
        {
        	VAL.add (detalle);
        }
        log.info ("Generando los VO de Alojamientos: " + VAL.size() + " alojamientos existentes");
       return VAL;
	}

	/**
	 * Encuentra un detalle de alojamiento por Id
	 * Adiciona entradas al log de la aplicación
	 * @param idAlojamiento - Identificador del alojamiento
	 * @return Un objeto de tipo Detalle_aloj
	 */
	public VODetalle_aloj darDetalleAlojPorId(long idAlojamiento)
	{
        log.info ("Encontrando alojamiento por Id " + idAlojamiento);
        Detalle_aloj alojamiento = pa.darDetalleAlojPorId(idAlojamiento);	
        log.info ("Encontrado alojamienot: " + alojamiento);
        return alojamiento;
	}

	/* ****************************************************************
	 * 			Métodos para manejar RESERVA
	 *****************************************************************/
	 
	/**
	 * Adiciona de manera persistente una reserva
	 * Adiciona entradas al log de la aplicación
	 * 
	 * @param idResidente - El id del residente que pone la reserva
	 * @param l           - La duracion en dias de la reserva
	 * @param fecha       - La fecha de la reserva
	 * @return El objeto Reserva adicionado. null si ocurre alguna Excepción
	 */
	public Reserva adicionarReserva(long id, long idResidente,  Timestamp fecha, int tiempo)
	{
        log.info ("Adicionando reserva del residente con id: " + idResidente);
		if (pa.sePuedeReservar(idResidente) == BigDecimal.ZERO){
			Reserva reserva = pa.adicionarReserva(idResidente ,tiempo, fecha);
        	log.info ("Adicionando reserva con id: " + reserva.getId());
        	return reserva;
		}else{
			log.info ("Ya reservo el dia de hoy");
			return null;
		}
        
	}

	public List<Reserva> adicionarReservaColectiva (long idResidente, List<Long> idOfertas, int tiempo)
	{
		log.info ("Adicionando reserva colectiva del residente con id: " + idResidente);
		List<Reserva> reservas = new LinkedList<Reserva>();
		if (pa.sePuedeReservar(idResidente) == BigDecimal.ZERO){
			long idColectiva = pa.getNextVal();
			for (Long idof: idOfertas) {
				Reserva reserva = pa.adicionarReserva(idResidente ,tiempo, new Timestamp(System.currentTimeMillis()));
				Residen residen = pa.adicionResiden(reserva.getId(), idof);
				reservas.add(reserva);
				log.info ("Adicionando reserva con id: " + reserva.getId());
			}
			for (Reserva reserva: reservas){
				pa.actualizarReservaColectiva(reserva.getId(),idColectiva);
			}
		return reservas;
		}else{
			log.info ("Ya reservo el dia de hoy");
			return null;
		}
	}


	public long actualizarReservaColectiva (long idReserva, long idColectiva){
		log.info ("Actualizando reserva colectiva con id: " + idReserva);
		long resp = pa.actualizarReservaColectiva(idReserva, idColectiva);
		log.info ("Actualizando reserva colectiva con id: " + idReserva);
		return resp;
	}

	public long actualizarReservaColectiva (long idColectiva){
		log.info ("Actualizando reserva colectiva con idColectiva: " + idColectiva);
		long resp = pa.actualizarReservaColectiva(idColectiva);
		log.info ("Actualizando reserva colectiva con idColectiva: " + idColectiva);
		return resp;
	}

	public long desagregarReservaColectiva (long idReserva){
		log.info ("Desagregando reserva colectiva con id: " + idReserva);
		long resp = pa.actualizarReservaColectiva(idReserva);
		log.info ("Desagregada reserva colectiva con id: " + idReserva);
		return resp;
	}
	
	/**
	 * Elimina una reserva por su id
	 * @param idReserva - El id de la reserva a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public Long eliminarReserva (long idReserva)
	{
        log.info ("Eliminando reserva con id: " + idReserva);
		
		long eliminados = pa.eliminarReservaPorId(idReserva);		
		log.info ("Eliminada reserva con id: " + idReserva);
		return eliminados;
        
	}

	/**
	 * Encuentra todas las reservas en Alohandes
	 * @param idReserva - El id de la reserva a eliminar
	 * @return Una lista de objetos Reserva con todos las reservas que conoce la aplicación,
	 */
	public Reserva retirarReservaPorId (long idReserva)
	{
		log.info ("Retirando reserva con id: " + idReserva);
		
		Reserva resp = pa.retirarReservaPorId(idReserva);		
		log.info ("Retirada reserva con id: " + idReserva);
		return resp;
		
	}

	/**
	 * 	Encuentra todas las reservas en Alohandes
	 * @param idReserva - El id de la reserva a eliminar
	 * @return Una lista de objetos Reserva con todos las reservas que conoce la aplicación,
	 *  llenos con su información básica
	 */

	public VOReserva darReservaPorId(long idReserva)
	{
        log.info ("Dando reserva con id: " + idReserva);
		
		Reserva resp = pa.darReservaPorid(idReserva);		
		log.info ("Dada reserva con id: " + idReserva);
		return resp;
        
	}

	/**
	 * Encuentra todas las reservas en Alohandes
	 * @return Una lista de objetos Reserva con todos las reservas que conoce la aplicación,
	 */
	public List<VOReserva> darVOOReservas() {
		log.info("Listando reservas");
		List<VOReserva> reservas = new ArrayList<VOReserva>();
		for (Reserva reserva : pa.darReservas()) {
			reservas.add(reserva);
		}
		log.info("Listando reservas existentes");
		return reservas;
	}

	/**
	 * Encuentra todas las reservas en Alohandes
	 * @param idResidente - El id del residente que pone la reserva
	 * @param duracionDias - La duracion en dias de la reserva
	 * @param fecha - La fecha de la reserva
	 * @return Una lista de objetos Reserva con todos las reservas que conoce la aplicación,
	 */
	public Reserva adicionarReserva(long idResidente, int duracionDias, Timestamp fecha) {
		log.info("Adicionando reserva del residente con id: " + idResidente);
		Reserva reserva = pa.adicionarReserva(idResidente,duracionDias, fecha);
		log.info("Adicionando reserva del residente con id: " + idResidente);
		return reserva;
	}

	/**
	 * Elimina una reserva por su id
	 * @param id - El id de la reserva a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarReservaPorId(long id) {
		log.info("Eliminando reserva con id: " + id);
		long resp = pa.eliminarReservaPorId(id);
		log.info("Eliminando reserva con id: " + id);
		return resp;
	}

	/**
	 * Encuentra todas las reservas en Alohandes
	 * @param idReserva 		- El id de la reserva a eliminar
	 * @return Una lista de objetos Reserva con todos las reservas que conoce la aplicación,
	 */
	public double calcularPenalidad(long idReserva){
		log.info("Calculando penalidad de la reserva con id: " + idReserva);
		Residen residen = pa.darResidenPorIdReserva(idReserva);
		long idOferta = residen.getIdOferta();
		Oferta oferta = pa.darOfertaPorId(idOferta);
		Reserva reserva = pa.darReservaPorid(idReserva);
		Timestamp currentTimestamp = new Timestamp(new Date().getTime());
		long daysBetween = ChronoUnit.DAYS.between(currentTimestamp.toLocalDateTime(), reserva.getFecha().toLocalDateTime());
		double resp = 0;
		if(daysBetween < oferta.getTiempoLimiteDias()){
			resp += oferta.getCosto() * 10;
		} 
		else if (daysBetween < oferta.getTiempoLimiteDias() * 2){
			resp += oferta.getCosto() * 30;
		}
		else if (daysBetween < oferta.getTiempoLimiteDias() * 3){
			resp += oferta.getCosto() * 50;
		}
	
		log.info("Calculando penalidad de la reserva con id: " + idReserva);
		return resp;
	}

	public List<Long> darReservasIdColectivas(long idResidente){
		log.info("Listando reservas colectivas del residente con id: " + idResidente);
		List<Long> resp = pa.darReservasIdColectivas(idResidente);
		log.info("Listando reservas colectivas del residente con id: " + idResidente);
		return resp;
	}

	public List<Reserva> darReservasColectivas (long idColectiva){
		log.info("Listando reservas colectivas de la reserva colectiva con id: " + idColectiva);
		List<Reserva> resp = pa.darReservasColectivas(idColectiva);
		log.info("Listando reservas colectivas de la reserva colectiva con id: " + idColectiva);
		return resp;
	}
	/* ****************************************************************
	 * 			Métodos para manejar OFERTA
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente una reserva 
	 * Adiciona entradas al log de la aplicación
     * @param id               - El identificador de la oferta
     * @param idAlojamiento    - El identificador del alojamiento
     * @param idOperador       - El identificador del operador
     * @param fecha            - Fecha de la oferta
     * @param costo            - Csto de la oferta
     * @param tiempoLimiteDias - Tiempo limite de la oferta
	 * @return El objeto Reserva adicionado. null si ocurre alguna Excepción
	 */
	public VOOferta adicionarOferta(long idAlojamiento, long idOperador, Timestamp fecha,
	double costo, int tiempoLimiteDias)
	{
        log.info ("Adicionando oferta del operador con id: " + idOperador);
		Oferta oferta = pa.adicionarOferta(idAlojamiento, idOperador, fecha, costo, tiempoLimiteDias);
		log.info ("Adicionando oferta  con id: " + oferta.getId() + " y fecha: " + oferta.getFecha());
		return oferta;
	}

	/**
	 * Elimina una oferta por su id
	 * @param listaOfertas - El id de la oferta a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public List<Oferta> RFC3 (List<Oferta> listaOfertas)
	{
        log.info ("Calculando indice de ocupación ");
		List<Oferta> oferta = pa.darIndiceOcupacionOfertas(listaOfertas);
		log.info ("Calculado indice de ocupación ");
		return oferta;
	
	}

	/**
	 * Encuentra todas las ofertas en Alohandes
	 * @param id - El id de la oferta a eliminar
	 * @return Una lista de objetos Oferta con todos las ofertas que conoce la aplicación,
	 */
	public long eliminarOfertaPorId(long id) {
		log.info("Eliminando oferta con id: " + id);
		long resp = pa.eliminarOfertaPorId(id);
		log.info("Eliminando oferta con id: " + resp);
		return resp;
	}

	/**
	 * Encuentra todas las ofertas en Alohandes
	 * @return Una lista de objetos Oferta con todos las ofertas que conoce la aplicación,
	 */
	public List<VOOferta> darVOOfertas() {
		log.info("Listando Ofertas");
		List<VOOferta> voOfertas = new LinkedList<VOOferta>();
		for (Oferta oferta : pa.darOfertas()) {
			voOfertas.add(oferta);
			log.info("Oferta: " + oferta);
		}

		log.info("Listando Ofertas: " + voOfertas.size() + " Ofertas existentes");
		return voOfertas;
	}

	/**
	 * Encuentra todas las ofertas en Alohandes
	 * @return Una lista de objetos Oferta con todos las ofertas que conoce la aplicación,
	 */
	public List<VOOferta> darVOOfertasDisponibles() {
		log.info("Listando Ofertas");
		List<VOOferta> voOfertas = new LinkedList<VOOferta>();
		for (Oferta oferta : pa.darOfertasDisponibles()) {
			voOfertas.add(oferta);
			log.info("Oferta: " + oferta);
		}

		log.info("Listando Ofertas: " + voOfertas.size() + " Ofertas existentes");
		return voOfertas;
	}

	/**
	 * Encuentra todas las ofertas en Alohandes
	 * @return Una lista de objetos Oferta con todos las ofertas que conoce la aplicación,
	 */
	public List<Oferta> darOfertas() {
		log.info("Listando Ofertas");
		List<Oferta> ofertas = pa.darOfertas();
		log.info("Listando Ofertas: " + ofertas.size() + " Ofertas existentes");
		return ofertas;
	}

	/**
	 * Encuentra todas las ofertas en Alohandes
	 * @return Una lista de objetos Oferta con todos las ofertas que conoce la aplicación,
	 */
	public List<Oferta> darOfertasDesahabilitadasPorOp(long idOperador) {
		log.info("Listando Ofertas");
		List<Oferta> ofertas = pa.darOfertasDesahabilitadasPorOp(idOperador);
		log.info("Listando Ofertas: " + ofertas.size() + " Ofertas existentes");
		return ofertas;
	}

	
	/**
	 * Da las 20 ofetas mas populares de alohandes
	 * @return Una lista de ids  con las 20 ofertas mas populares
	 */
	public List<Long> RFC2 ()
	{
        log.info ("Dando 20 ofertas: " );
		List<Long> residen = pa.darOfertasMasPopulares();
		log.info ("Dando 20 ofertas: " );
		return residen;
	
	}

	public long actualizarOferta (long idOferta, int reservada){
		log.info("Actualizando oferta con id: " + idOferta);
		long oferta = pa.actualizarOferta(idOferta, reservada);
		log.info("Actualizando oferta con id: " + idOferta);
		return oferta;
	}

	public void agregarDesahabilitacion (long idOferta) {
		log.info("Actualizando oferta con id: " + idOferta);
		pa.agregarDesahabilitacion(idOferta);
		log.info("Actualizando oferta con id: " + idOferta);
	}

	public long eliminarDesahabilitacion (long idOferta) {
		log.info("Actualizando oferta con id: " + idOferta);
		long oferta = pa.eliminarDesahabilitacion(idOferta);
		log.info("Actualizando oferta con id: " + idOferta);
		return oferta;
	}


	/* ****************************************************************
	 * 			Métodos para manejar RESIDEN
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente una reserva 
	 * Adiciona entradas al log de la aplicación
     * @param idReserva       - El identificador de la reserva
	 * @param idOferta 			-El identificador de la oferta
	 * @return El objeto Residen adicionado. null si ocurre alguna Excepción
	 */
	public VOResiden adicionarResiden(long idReserva, long idOferta)
	{
        log.info ("Adicionando residen del residente con id: " + idReserva);
		Residen residen = pa.adicionResiden(idReserva, idOferta);
		log.info ("Adicionando residen con id de reserva: " + idReserva);
		return residen;
	
	}

	/**
	 * Elimina una reserva por su id
	 * @param idReserva - El id de la reserva a eliminar
	 * @return	El número de tuplas eliminadas
	 */
	public Long eliminarResiden (long idReserva)
	{
        log.info ("Eliminando reserva con id: " + idReserva);
		
		long eliminados = pa.eliminarResiden(idReserva);		
		log.info ("Eliminada reserva con id: " + idReserva);
		return eliminados;
        
	}

	/**
	 * Elimina una reserva por su id
	 * @param idOferta - El id de la reserva a eliminar
	 * @return	El número de tuplas eliminadas
	 */
	public Long eliminarResidenOf(long idOferta)
	{
        log.info ("Eliminando reserva con id: " + idOferta);
		
		long eliminados = pa.eliminarResidenOf(idOferta);		
		log.info ("Eliminada reserva con id: " + idOferta);
		return eliminados;
        
	}

	/**
	 * Encuentra todas las reservas en Alohandes
	 * @return Una lista de objetos Reserva con todos las reservas que conoce la aplicación,
	 */
	public List<VOResiden> darVOResiden() {
		log.info("Listando Residen");
		List<VOResiden> voResiden = new LinkedList<VOResiden>();
		for (Residen residen : pa.darResiden()) {
			voResiden.add(residen);
			log.info("Residen: " + residen);
		}

		log.info("Listando Residen: " + voResiden.size() + " Residen existentes");
		return voResiden;
	}

	/**
	 * Encuentra todas las reservas en Alohandes
	 * @param idReserva - El id de la reserva a buscar
	 * @param idOferta - El id de la oferta a buscar
	 * @return Una lista de objetos Reserva con todos las reservas que conoce la aplicación,
	 */
	public long eliminarResidenPorId(long idReserva, long idOferta) {
		log.info("Eliminando Residen con idReserva: " + idReserva + " y idOferta: " + idOferta);
		long resp = pa.eliminarResidenPorId(idReserva, idOferta);
		log.info("Eliminando Residen con idReserva: " + idReserva + " y idOferta: " + idOferta);
		return resp;
	}

	/**
	 * Encuentra todas las reservas en Alohandes
	 * @param idOferta - El id de la oferta a buscar
	 * @return Una lista de objetos Reserva con todos las reservas que conoce la aplicación,
	 */
	public List<Residen> darResidenPorIdOferta (long idOferta)
	{
		log.info("Dar información de una reserva por id de oferta: " + idOferta);
		List<Residen> residen = pa.darResidenPorIdOferta(idOferta);
		log.info("Buscando reserva por id de oferta: " + residen != null ? residen : "NO EXISTE");
		return residen;
	}

	/* ****************************************************************
	 * 			Métodos para manejar RESIDENTE
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente una reserva 
	 * Adiciona entradas al log de la aplicación
     * @param nombre       - El nombre del residente
	 * @param vinculo 	   -El identificador de la oferta
	 * @return El objeto Residen adicionado. null si ocurre alguna Excepción
	 */
	public VOResidente adicionarResidente(String nombre, String vinculo)
	{
        log.info ("Adicionando residente con el nombre: " + nombre);
		Residente residente = pa.adicionarResidente(nombre, vinculo);
		log.info ("Adicionando residen  con id: " + residente.getId() + " y nombre: " + residente.getNombre());
		return residente;
	
	}

	/**
	 * Elimina un residente por id
	 * @return El número de tuplas eliminadas
	 */
	public List<VOResidente> darVOResidentes ()
	{
        log.info ("Generando los VO de Residentes");
        List<VOResidente> resp = new LinkedList<VOResidente> ();
        for (Residente res : pa.darResidentes())
        {
        	resp.add (res);
        }
        log.info ("Generando los VO de Residentes: " + resp.size() + " residentes existentes");
       return resp;
	}

	/**
	 * Elimina un residente por id
	 * @param idResidente - El id del residente a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarResidentePorId(long idResidente) {
		log.info("Eliminando Residente con idResidente: " + idResidente);
		long resp = pa.eliminarResidentePorId(idResidente);
		log.info("Eliminando Residente con idResidente: " + idResidente);
		return resp;
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
        log.info ("Adicionando TS con el id de alojamiento: " + idAlojamiento);
		TieneServicio TS = pa.adicionTS(idAlojamiento, idServicio);
		log.info ("Adicionado TS con el id de alojamiento: " + idAlojamiento);
		return TS;
	
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
		public VOServicio adicionServicio(String nombre, double costo)
	{
		log.info ("Adicionando servicio con el nombre: " + nombre);
		Servicio servicio = pa.adicionServicio(nombre, costo);
		log.info ("Adicionado servicio con el id: " + servicio.getId());
		return servicio;
	}

	/**
	 * Dar un servicio por id
	 * @param idServicio - El id del servicio 
	 * @return El objeto Servicio, null si no existe
	 */
	public VOServicio darServicioPorId(Long id)
	{
		log.info ("Dando servicio con el id: " + id);
		Servicio servicio = pa.darServicioPorid(id);
		log.info ("Dando servicio con el id: " + servicio.getId());
		return servicio;
	}


	// dar VOservicios
	public List<VOServicio> darVOServicios() {
		List<VOServicio> voServicios = new LinkedList<VOServicio>();
		for (Servicio servicio : pa.darServicios()) {
			voServicios.add(servicio);
		
		}

		return voServicios;
	}

	// adicionarServicio
	public Servicio adicionarServicio(String nombre, double costo) {

		Servicio servicio = pa.adicionServicio(nombre, costo);
		return servicio;
	}

	// eliminarServicioPorId
	public long eliminarServicioPorId(long idServicio) {
		
		long resp = pa.eliminarServicioPorId(idServicio);
		
		return resp;
	}

	/* ****************************************************************
	 * 			Métodos para administración
	 *****************************************************************/

	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos de Alohandes
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas.
	 */
	public long [] limpiarAlohandes ()
	{
        log.info ("Limpiando la BD de Alohandes");
        long [] borrrados = pa.limpiarAlohandes();	
        log.info ("Limpiando la BD de Alohandes: Listo!");
        return borrrados;
	}


}