
package uniandes.isis2304.parranderos.negocio;

/**
 * Clase para modelar el concepto DETALLE_ALOJ del negocio de los Parranderos
 *
 * @author Germán Bravo
 */
public class Detalle_aloj implements VODetalle_aloj
{
    /* ****************************************************************
     * 			Atributos
     *****************************************************************/
    /**
     * El identificador ÚNICO de los alojamientos
     */
    private long idalojamiento;
    
    /**
     * La categoria del alojamiento ('Suite', 'Semisuite', 'Estandar', 'Compartida', 'Individual')
     */
    private String categoria;
    
    /**
     * La capacidad del alojamiento
     */
    private Integer capacidad;

    /**
    * Si el alojamiento tiene o no algún servicio (1,0)
    */
    private Integer servicios;

    /**
    * Costo del seguro en caso de requerirlo
    */
    private Integer costo_seguro;
    
    /**
    * Costo de la administración en caso de requerirlo
    */
    private Integer costo_admon;

    /**
    * Si el alojamiento esta amoblado o no (1,0)
    */
    private Integer amoblado;

    /* ****************************************************************
     * 			Métodos 
     *****************************************************************/
    /**
     * Constructor por defecto
     */
    public Detalle_aloj() 
    {
        this.idalojamiento = 0;
        this.categoria = "";
        this.capacidad = 0;
        this.servicios = 0;
        this.costo_seguro = 0;
        this.costo_admon = 0;
        this.amoblado = 0;
    }

    /**
     * Constructor con valores
     * @param idAlojamiento - El id del alojamiento
     * @param categoria - La categoria del alojamiento ('Suite', 'Semisuite', 'Estandar', 'Compartida', 'Individual')
     * @param capacidad - La capacidad del alojamiento
     * @param servicios - Si el alojamiento cuenta con servicios o no
     * @param costo_seguro - El costo del seguro si es el caso
     * @param costo_admon - El cosot de la administracion si es el caso
     * @param amoblado -  Si elojamiento esta amoblado o no
     */
    public Detalle_aloj(long idAlojamiento, String categoria, int capacidad, int servicios, int costo_seguro, int costo_admon, int amoblado) 
    {
        this.idalojamiento = idAlojamiento;
        this.categoria = categoria;
        this.capacidad = capacidad;
        this.servicios = servicios;
        this.costo_seguro = costo_seguro;
        this.costo_admon = costo_admon;
        this.amoblado = amoblado;
    }

    /**
     * @return id del alojamiento
     */
    public long getIdAlojamiento() {
        return idalojamiento;
    }

    /**
     * @param idalojamiento 
     */
    public void setIdAlojamiento(long idalojamiento) {
        this.idalojamiento = idalojamiento;
    }

    /**
     * @return la categoria del alojamiento
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * @param categoria - La nueva categoria del alojamiento
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * @return capacidad 
     */
    public Integer getCapacidad() {
        return capacidad;
    }

    /**
     * @param capacidad - La nueva capacidad del alojamiento
     */
    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    /**
     * @return serivios
     */
    public Integer getServicios() {
        return servicios;
    }

    /**
     * @param serivicios - Si el elojamiento tendra servicios o no
     */
    public void setServicios(Integer servicios) {
        this.servicios = servicios;
    }

    /**
     * @return costo_seguro
     */
    public Integer getCosto_seguro() {
        return costo_seguro;
    }

    /**
     * @param costo_seguro - El nuevo costo del seguro del alojamiento
     */
    public void setCosto_seguro(Integer costo_seguro) {
        this.costo_seguro = costo_seguro;
    }

    /**
     * @return costo_admon
     */
    public Integer getCosto_admon() {
        return costo_admon;
    }

    /**
     * @param costo_admon - El nuevo costo de la administracion del alojamiento
     */
    public void setCosto_admon(Integer costo_admon) {
        this.costo_admon = costo_admon;
    }

    /**
     * @return amoblado
     */
    public Integer getAmoblado() {
        return amoblado;
    }

    /**
     * @param amoblado - Si el alojamiento va ser amoblado o no
     */
    public void setAmoblado(Integer amoblado) {
        this.amoblado = amoblado;
    }

    
    @Override
    /**
     * @return Una cadena de caracteres con todos los atributos del operador
     */
    public String toString() 
    {
        return "Detalle_operadores [idAlojamiento=" + idalojamiento + ", categoria=" + categoria + ", capacidad=" + capacidad + ", servicios=" + servicios + 
                ", costo_seguro=" + costo_seguro + ", costo_admon=" + costo_admon + ", amoblado=" + amoblado + "]";
    }

}
