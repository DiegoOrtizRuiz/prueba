package uniandes.isis2304.parranderos.negocio;

/**
 * Interfaz para los métodos get de DETALLE_OPERADORES.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 */
public interface VODetalle_operadores 
{
    /* ****************************************************************
     * 			Métodos 
     *****************************************************************/
     /**
     * @return El id del operador
     */
    public long getIdOperador();
    
    /**
     * @return el registro_cc del operador
     */
    public long getRegistro_cc();
    
    /**
     * @return el registro_supert del operador
     */
    public long getRegistro_supert();

    /**
     * @return el registro_supert del operador
     */
    public String getVinculo();
    

    @Override
    /**
     * @return Una cadena de caracteres con todos los atributos del operador
     */
    public String toString();

}