
package uniandes.isis2304.parranderos.negocio;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase para modelar el concepto BAR del negocio de los Parranderos
 *
 * @author Germán Bravo
 */
public class Alojamiento implements VOAlojamiento
{
    /* ****************************************************************
     * 			Atributos
     *****************************************************************/
    /**
     * El identificador ÚNICO de los operadores
     */
    private long id;
    
    /**
     * El identificador del operador
     */
    private long idOperador;
    
    /**
     * El tipo del operador ('Habitacion', 'Apartamento', 'Vivienda Personal')
     */
    private String tipo;

    /**
     * Lista de servicios que ocupa el alojamiento
     */
    private List<Servicio> listaServicios;

    /**
     * Si el alojamiento esta ocupado o no
     */
    private Integer ocupado;


    /* ****************************************************************
     * 			Métodos 
     *****************************************************************/
    /**
     * Constructor por defecto
     */
    public Alojamiento() 
    {
        this.id = 0;
        this.idOperador = 0;
        this.tipo = "";
        this.listaServicios = new ArrayList<Servicio>();
        this.ocupado = 0;
        
    }

    /**
     * Constructor con valores
     * @param id - El id del operador
     * @param idOperador - El nombre del operador
     * @param tipo - El presupuesto del operador ('Persona Natural', 'Hotel', 'Hostal', 'Residencia Universitaria')
     */
    public Alojamiento(long id, long idOperador, String tipo) 
    {
        this.id = id;
        this.idOperador = idOperador;
        this.tipo = tipo;

        // Estos valores no se conocen en el momento de la construcción del bebedor
        this.listaServicios = new ArrayList<Servicio>();
        this.ocupado = 0;
    }

    /**
     * @return El id del alojamiento
     */
    public long getId() 
    {
        return id;
    }
    
    /**
     * @param id - El nuevo id del alojamiento
     */
    public void setId(long id) 
    {
        this.id = id;
    }
    
    /**
     * @return el id del operador
     */
    public long getIdOperador() 
    {
        return idOperador;
    }
    
    /**
     * @param idOperador El id  del operador
     */
    public void setIdOperador(long idOperador) 
    {
        this.idOperador = idOperador;
    }
    
    /**
     * @return el tipo del aloajmiento
     */
    public String getTipo() 
    {
        return tipo;
    }
    
    /**
     * @param tipo - El nuevo tipo del operador
     */
    public void setTipo(String tipo) 
    {
        this.tipo = tipo;
    }

        /**
	 * @return Los servicios del alojamiento
	 */
	public List<Servicio> getServicios() 
	{
		return listaServicios;
	}

	/**
	 * @param listaServicios - La nueva lista de servicios del alojamiento
	 */
	public void setServicios(List<Servicio> listaServicios) 
	{
		this.listaServicios = listaServicios;
	}

    public List<String> getNombreServicios (){
        List<String> nombreServicios = new ArrayList<String>();
        for (Servicio servicio : listaServicios) {
            nombreServicios.add(servicio.getNombre());
        }
        return nombreServicios;
    }

    /**
     * @return Si el alojamiento esta ocupado o no
     */

    public Integer getOcupado()
    {
        return ocupado;
    }

    /**
     * @param ocupado - El nuevo estado del alojamiento
     */
    public void setOcupado(int ocupado)
    {
        this.ocupado = ocupado;
    }
    
    
    @Override
    /**
     * @return Una cadena de caracteres con todos los atributos del operador
     */
    public String toString() 
    {
        return "Alojamiento [id=" + id + ", idOperador=" + idOperador + ", tipo=" + tipo + "]";
    }
    

}
