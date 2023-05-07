
package uniandes.isis2304.parranderos.negocio;

/**
 * Clase para modelar el concepto DETALLE_OPERADORES del negocio de los Parranderos
 *
 * @author Germán Bravo
 */
public class Detalle_operadores implements VODetalle_operadores
{
    /* ****************************************************************
     * 			Atributos
     *****************************************************************/
    /**
     * El identificador ÚNICO de los operadores
     */
    private long idoperador;
    
    /**
     * El registro de camara de comercio del operador
     */
    private long registro_cc;
    
    /**
     * El registro de la super turismo del operador
     */
    private long registro_supert;

      /**
     * El vinculo del operador con Uniandes ('Estudiante', 'Profesor', 'Profesor invitado', 'Egresado', 'Padre', 'Empleado','Empresa')
     */
    private String vinculo;
    

    /* ****************************************************************
     * 			Métodos 
     *****************************************************************/
    /**
     * Constructor por defecto
     */
    public Detalle_operadores() 
    {
        this.idoperador = 0;
        this.registro_cc = 0;
        this.registro_supert = 0;
        this.vinculo = "";
    }

    /**
     * Constructor con valores
     * @param idoperador - El id del operador
     * @param registro_cc - El registro en camara de comercio del operador
     * @param registro_supert - El registro en la super turismo del operador
     * @param vinuclo - El vinculo del operador con Uniandes ('Estudiante', 'Profesor', 'Profesor invitado', 'Egresado', 'Padre', 'Empleado','Empresa')
     */
    public Detalle_operadores(long idoperador, long registro_cc, long registro_supert, String vinculo) 
    {
        this.idoperador = idoperador;
        this.registro_cc = registro_cc;
        this.registro_supert = registro_supert;
        this.vinculo = vinculo;
    }

    /**
     * @return El id del operador
     */
    public long getIdOperador() 
    {
        return idoperador;
    }
    
    /**
     * @param idOperaador - El nuevo id del operador
     */
    public void setIdOperador(long idOperador) 
    {
        this.idoperador = idOperador;
    }
    
    /**
     * @return el registro_cc del operador
     */
    public long getRegistro_cc() 
    {
        return registro_cc;
    }
    
    /**
     * @param registro_cc El nuevo registro_cc del operador
     */
    public void setRegistro_cc(long registro_cc) 
    {
        this.registro_cc = registro_cc;
    }
    
    /**
     * @return el registro_supert del operador
     */
    public long getRegistro_supert() 
    {
        return registro_supert;
    }
    
    /**
     * @param registro_supert - La nuevo registro_supert del operador
     */
    public void setRegistro_supert(Long registro_supert) 
    {
        this.registro_supert = registro_supert;
    }
        
    /**
     * @return el vinuclo del operador
     */
    public String getVinculo() 
    {
        return vinculo;
    }
    
    /**
     * @param vinculo - La nuevo vinculo del operador
     */
    public void setVinculo(String vinculo) 
    {
        this.vinculo = vinculo;
    }
    
    @Override
    /**
     * @return Una cadena de caracteres con todos los atributos del operador
     */
    public String toString() 
    {
        return "Detalle_operadores [idOperador=" + idoperador + ", registro_cc=" + registro_cc + ", registro_supert=" + registro_supert + ", vinculo=" + vinculo + "]";
    }
    

}
