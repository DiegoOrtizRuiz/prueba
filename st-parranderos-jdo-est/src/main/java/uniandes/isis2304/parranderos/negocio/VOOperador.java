 package uniandes.isis2304.parranderos.negocio;

import java.util.List;

/**
  * Interfaz para los métodos get de OPERADOR.
  * Sirve para proteger la información del negocio de posibles manipulaciones desde la interfaz 
  * 
  */
 public interface VOOperador 
 {
     /* ****************************************************************
      * 			Métodos 
      *****************************************************************/
      /**
      * @return El id del operador
      */
     public long getId();
     
     /**
      * @return el nombre del operador
      */
     public String getNombre();
     
     /**
      * @return el tipo del operador
      */
     public String getTipo();

    /**
      * @return la lista de ofertas del operador
      */
      public List<Oferta> getOfertas();

    /**
      * @return la lista de alojamientos del operador
      */
      public List<Alojamiento> getAlojamientos();

      /**
      * @return las ganancias del operador actuales
      */
      public Integer getGananciasAñoActual();

    /**
      * @return la lista de alojamientos del operador
      */
      public Integer getGananciasAñoCorrido();
     
 
     @Override
     /**
      * @return Una cadena de caracteres con todos los atributos del operador
      */
     public String toString();
 
 }
 