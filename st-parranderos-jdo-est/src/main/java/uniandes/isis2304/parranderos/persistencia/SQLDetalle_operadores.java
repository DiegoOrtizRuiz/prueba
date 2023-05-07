package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Detalle_operadores;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto Detalle_operadores de Alohandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 */
class SQLDetalle_operadores
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
    public SQLDetalle_operadores (PersistenciaAlohandes pp)
    {
        this.pp = pp;
    }
    
    /**
     * Crea y ejecuta la sentencia SQL para adicionar informacion de un DETALLE_OPERADORES a DETALLE_OPERADORES a la base de datos de Alohandes
     * @param pm - El manejador de persistencia
     * @param idOperador - El identificador del operador
     * @param registro_cc - El registro en camara de comercio del operador
     * @param registro_supert - El registro_supert del operador
     * @param vinculo - El vinculo del operador
     * @return El número de tuplas insertadas
     */
    public long adicionarDetalle_operadores (PersistenceManager pm, long idOperador, long registro_cc, long registro_supert, String vinculo) 
    {
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaDetalle_operadores () + "(idoperador, registro_cc, registro_supert, vinculo) values (?, ?, ?, ?)");
        q.setParameters(idOperador, registro_cc, registro_supert, vinculo);
        return (long) q.executeUnique();
    }


    /**
     * Crea y ejecuta la sentencia SQL para eliminar UN DETALLE_OPERADOES de la base de datos de Alohandes, por su identificador
     * @param pm - El manejador de persistencia
     * @param idOperador - El identificador del operador
     * @return EL número de tuplas eliminadas
     */
    public long eliminarDetalle_operadoresPorId (PersistenceManager pm, long idOperador)
    {
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaDetalle_operadores () + " WHERE id = ?");
        q.setParameters(idOperador);
        return (long) q.executeUnique();
    }

    /**
     * Crea y ejecuta la sentencia SQL para encontrar la información de UN DETALLE_DETALLE_OPERADORES de la 
     * base de datos de Alohandes, por su identificador
     * @param pm - El manejador de persistencia
     * @param idOperador - El identificador del alojamiento
     * @return El objeto BAR que tiene el identificador dado
     */
    public Detalle_operadores darDetalle_operadoresPorId (PersistenceManager pm, long idOperador) 
    {
        Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaDetalle_operadores () + " WHERE id = ?");
        q.setResultClass(Detalle_operadores.class);
        q.setParameters(idOperador);
        return (Detalle_operadores) q.executeUnique();
    }


    /**
     * Crea y ejecuta la sentencia SQL para encontrar la información de LOS DETALLE_DETALLE_OPERADORES de la 
     * base de datos de Alohandes
     * @param pm - El manejador de persistencia
     * @return Una lista de objetos BAR
     */
    public List<Detalle_operadores> darDetalle_operadores (PersistenceManager pm)
    {
        Query q = pm.newQuery(SQL, "SELECT idoperador, registro_cc, registro_supert, vinculo FROM " + pp.darTablaDetalle_operadores());
         q.setResultClass(Detalle_operadores.class);
         return (List<Detalle_operadores>) q.executeList();
    }

    

}
