package uniandes.isis2304.parranderos.negocio;

/**
 * Interfaz para los métodos get de DETALLE_OPERADORES.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 */
public interface VODetalle_aloj
{
    /* ****************************************************************
     * 			Métodos 
     *****************************************************************/
     /**
     * @return El id del alojameinto
     */
    public long getIdAlojamiento();
    
    /**
     * @return La categoria del alojamiento
     */
    public String getCategoria();
    
    /**
     * @return Si se tienen servicios o no
     */
    public Integer getServicios();

    /**
     * @return El costo del seguro si es el caso
     */
    public Integer getCosto_seguro();

    /**
     * @return El costo de la administración si es el caso
     */
    public Integer getCosto_admon();

    /**
     * @return Si el alojamiento esta amoblado o no
     */
    public Integer getAmoblado();

    public Integer getCapacidad();



    @Override
    /**
     * @return Una cadena de caracteres con todos los atributos del operador
     */
    public String toString();

}