package uniandes.isis2304.parranderos.negocio;

/**
  * Interfaz para los métodos get de TIENESERVICIO.
  * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
  * 
  */
  public interface VOTieneServicio 
  {
      /* ****************************************************************
       * 			Métodos 
       *****************************************************************/
       /**
       * @return El id del alojamiento
       */
      public long getIdAlojamiento();
      
      /**
       * @return El id del servicio
       */
      public long getIdServicio();
    

    }
