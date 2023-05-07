package uniandes.isis2304.parranderos.negocio;

import java.util.List;

/**
 * Interfaz para los métodos get de BAR.
 * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author Germán Bravo
 */
public interface VOAlojamiento   
{
    /* ****************************************************************
     * 			Métodos 
     *****************************************************************/
     /**
     * @return El id del alojamiento
     */
    public long getId();
    
    /**
     * @return el id del operador del alojamiento
     */
    public long getIdOperador();
    
    /**
     * @return el tipo del alojamiento
     */
    public String getTipo();

    /**
     * @return los servicios del alojamiento
     */
    public List<Servicio> getServicios();

    /**
     * @return si el alojamiento esta ocupado o no
     */
    public Integer getOcupado();
    
 

    @Override
    /**
     * @return Una cadena de caracteres con todos los atributos del alojamiento
     */
    public String toString();

}
