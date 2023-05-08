package uniandes.isis2304.parranderos.persistencia;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Reserva;

public class SQLReserva {

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
    public SQLReserva(PersistenciaAlohandes pp) {
        this.pp = pp;
    }

    public long adicionarReserva(PersistenceManager pm,long idReserva, long idResidente, Timestamp fecha, int duracionDias) {
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaReserva()
                + "(id, idresidente, fecha, duracion_dias) values (?, ?, ?, ?)");
        q.setParameters(idReserva, idResidente, fecha, duracionDias);
        return (long) q.executeUnique();
    }

    public long eliminarReservaPorId(PersistenceManager pm, long id) {
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaReserva() + " WHERE id = ?");
        q.setParameters(id);
        return (long) q.executeUnique();
    }

    public Object[] darReservaPorId(PersistenceManager pm, long id) {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaReserva() + " WHERE id = ?");
        q.setParameters(id);
        return (Object[]) q.executeUnique();
    }

    public List<Object[]> darReservas(PersistenceManager pm) {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaReserva());
        q.setResultClass(Reserva.class);
        return (List<Object[]>) q.executeList();
    }

    public List<Reserva> darReservasPorResidente(PersistenceManager pm, long idResidente) {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaReserva() + " WHERE idresidente = ?");
        q.setParameters(idResidente);
        return (List<Reserva>) q.executeList();
    }

    public long actualizarReserva(PersistenceManager pm, long id, long idResidente, Timestamp fecha, int duracionDias) {
        Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaReserva()
                + " SET idresidente = ?, fecha = ?, duracion_dias = ? WHERE id = ?");
        q.setParameters(idResidente, fecha, duracionDias, id);
        return (long) q.executeUnique();
    }

    public long actualizarReservaColectiva(PersistenceManager pm, long id, long idColectiva) {
        Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaReserva()
                + " SET colectiva = ? WHERE id = ?");
        q.setParameters(idColectiva, id);
        return (long) q.executeUnique();
    }

    public long actualizarReservaColectiva(PersistenceManager pm, long id) {
        Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaReserva()
                + " SET colectiva = NULL WHERE colectiva = ?");
        q.setParameters(id);
        return (long) q.executeUnique();
    }

    public long desagregarReservaColectiva(PersistenceManager pm, long id) {
        Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaReserva()
                + " SET colectiva = NULL WHERE id = ?");
        q.setParameters(id);
        return (long) q.executeUnique();
    }
    
    public List<Long> darReservasIdColectivas(PersistenceManager pm, long idResidente) {
        Query q = pm.newQuery(SQL, "SELECT colectiva FROM " + pp.darTablaReserva() + " WHERE idResidente = ? AND colectiva IS NOT NULL GROUP BY colectiva");
        q.setParameters(idResidente);
        return (List<Long>) q.executeList();
    }

    public List<Object[]> darReservasColectivas(PersistenceManager pm, long idColectiva) {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaReserva() + " WHERE colectiva = ?");
        q.setParameters(idColectiva);
        return (List<Object[]>) q.executeList();
    }

    public long retirarReservaPorId (PersistenceManager pm, long idReserva){
        Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaReserva() + " SET activo = 0 WHERE id = ?");
        q.setParameters(idReserva);
        return (long) q.executeUnique();
    }

    /**
	 * Crea y ejecuta la sentencia SQL para encontrar si un residente ya ha hecho una reserva en el dia actual
	 * @param pm - El manejador de persistencia
     * @param idResidente - El identificador del residente
	 * @return Un numero que representa el numero de reservas en esa fecha
	 */
	public BigDecimal sePuedeReservar (PersistenceManager pm, long idResidente)
	{
        
        // Get the current date
        LocalDate currentDate = LocalDate.now();
     
        // Format the date as a string in year-month-day format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String currentDateFormated = currentDate.format(formatter);

        String sql = "SELECT COUNT(r.idreserva)";
        sql += " FROM ";
        sql += pp.darTablaResiden() + " r ,";
        sql += pp.darTablaReserva() + " res ";
        sql += "WHERE ";
        sql += "r.idreserva = res.id";
       	sql	+= " AND ";
        sql	+= "res.idresidente = ?";
        sql	+= " AND ";
        sql	+= "res.fecha =  ?";
		Query q = pm.newQuery(SQL, sql);
        q.setParameters(idResidente, new Timestamp(System.currentTimeMillis()) );
		return (BigDecimal) q.executeUnique();
    }

    public List<Object[]> rfc5(PersistenceManager pm) {
        Query q = pm.newQuery(SQL,
                "SELECT vinculo, count(*) as NUMERO_USUARIOS, avg(duracion_dias) AS PROMEDIO_DIAS_CONTRATADOS FROM "
                        + pp.darTablaReserva()
                        + " JOIN RESIDENTE ON RESERVA.IDRESIDENTE = RESIDENTE.ID GROUP BY vinculo");
        return (List<Object[]>) q.executeList();
    }
    public List<Object[]> rfc6(PersistenceManager pm, Integer resp1) {
        Query q = pm.newQuery(SQL,
                "SELECT DURACION_DIAS as NUMERO_DE_NOCHES, TIPO as TIPO_ALOJAMIENTO, COSTO as DINERO_PAGADO FROM "
                        + pp.darTablaReserva()
                        + " JOIN RESIDENTE ON RESERVA.IDRESIDENTE = RESIDENTE.ID JOIN RESIDEN ON RESERVA.ID = RESIDEN.IDRESERVA JOIN OFERTA ON RESIDEN.IDOFERTA = OFERTA.ID JOIN ALOJAMIENTO ON IDALOJAMIENTO = ALOJAMIENTO.ID WHERE IDRESIDENTE = ?");
        q.setParameters(resp1);
        return (List<Object[]>) q.executeList();
    }

    public List<Object> rfc7semana(PersistenceManager pm, Integer resp1) {
        Query q = pm.newQuery(SQL,
                "SELECT SUM(COSTO) AS INGRESOS FROM RESERVA JOIN RESIDENTE ON RESERVA.IDRESIDENTE = RESIDENTE.ID JOIN RESIDEN ON RESERVA.ID = RESIDEN.IDRESERVA JOIN OFERTA ON RESIDEN.IDOFERTA = OFERTA.ID JOIN ALOJAMIENTO ON IDALOJAMIENTO = ALOJAMIENTO.ID WHERE TO_CHAR(RESERVA.FECHA, 'WW') = '"
                        + resp1.toString() + "'GROUP BY RESERVA.FECHA");
        return (List<Object>) q.executeList();
    }

    public List<Object> rfc7mes(PersistenceManager pm, Integer resp1) {
        Query q = pm.newQuery(SQL,
                "SELECT SUM(COSTO) AS INGRESOS FROM RESERVA JOIN RESIDENTE ON RESERVA.IDRESIDENTE = RESIDENTE.ID JOIN RESIDEN ON RESERVA.ID = RESIDEN.IDRESERVA JOIN OFERTA ON RESIDEN.IDOFERTA = OFERTA.ID JOIN ALOJAMIENTO ON IDALOJAMIENTO = ALOJAMIENTO.ID WHERE EXTRACT(MONTH FROM RESERVA.FECHA) = ? GROUP BY RESERVA.FECHA");
        q.setParameters(resp1);
        return (List<Object>) q.executeList();
    }

    public List<Object> rfc7anio(PersistenceManager pm, Integer resp1) {
        Query q = pm.newQuery(SQL,
                "SELECT SUM(COSTO) AS INGRESOS FROM RESERVA JOIN RESIDENTE ON RESERVA.IDRESIDENTE = RESIDENTE.ID JOIN RESIDEN ON RESERVA.ID = RESIDEN.IDRESERVA JOIN OFERTA ON RESIDEN.IDOFERTA = OFERTA.ID JOIN ALOJAMIENTO ON IDALOJAMIENTO = ALOJAMIENTO.ID WHERE EXTRACT(YEAR FROM RESERVA.FECHA) = ? GROUP BY RESERVA.FECHA");
        q.setParameters(resp1);
        return (List<Object>) q.executeList();
    }

    public List<Object> rfc8one(PersistenceManager pm, Integer resp1) {
        Query q = pm.newQuery(SQL,
                "SELECT idresidente FROM RESERVA JOIN RESIDENTE ON RESERVA.IDRESIDENTE = RESIDENTE.ID JOIN RESIDEN ON RESERVA.ID = RESIDEN.IDRESERVA JOIN OFERTA ON RESIDEN.IDOFERTA = OFERTA.ID JOIN ALOJAMIENTO ON IDALOJAMIENTO = ALOJAMIENTO.ID WHERE idalojamiento = ? HAVING count(*) >= 3 GROUP BY idresidente");
        q.setParameters(resp1);
        return (List<Object>) q.executeList();
    }

    public List<Object> rfc8two(PersistenceManager pm, Integer resp1) {
        Query q = pm.newQuery(SQL,
                "SELECT UNIQUE(IDRESIDENTE) FROM RESERVA JOIN RESIDENTE ON RESERVA.IDRESIDENTE = RESIDENTE.ID JOIN RESIDEN ON RESERVA.ID = RESIDEN.IDRESERVA JOIN OFERTA ON RESIDEN.IDOFERTA = OFERTA.ID JOIN ALOJAMIENTO ON IDALOJAMIENTO = ALOJAMIENTO.ID WHERE idalojamiento = ? AND DURACION_DIAS > 15");
        q.setParameters(resp1);
        return (List<Object>) q.executeList();
    }



}
