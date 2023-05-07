package uniandes.isis2304.parranderos.negocio;

public class TieneServicio implements VOTieneServicio
{
    /* ****************************************************************
      * 			Atributos
      *****************************************************************/
     /**
      * El identificado del alojamiento
      */
      private long idAlojamiento;
     
      /**
       * El nombre del operador
       */
      private long idServicio;
      
  
      /* ****************************************************************
       * 			Métodos 
       *****************************************************************/
      /**
       * Constructor por defecto
       */
      public TieneServicio() 
      {
          this.idAlojamiento = 0;
          this.idServicio = 0;
         
      }
  
      /**
       * Constructor con valores
       * @param idAlojamiento - El id del alojamiento
       * @param idServicio - El id del serivicio
       */
      public TieneServicio(long idAlojamiento, long idServicio) 
      {
          this.idAlojamiento = idAlojamiento;
          this.idServicio = idServicio;
 
      }

    public long getIdAlojamiento() {
        return idAlojamiento;
    }

    public void setIdAlojamiento(long idAlojamiento) {
        this.idAlojamiento = idAlojamiento;
    }

    public long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(long idServicio) {
        this.idServicio = idServicio;
    }

}
