
 package uniandes.isis2304.parranderos.negocio;

import java.util.ArrayList;
import java.util.List;

/**
  * Clase para modelar el concepto OPERADOR del negocio de los Parranderos
  *
  */
 public class Operador implements VOOperador
 {
     /* ****************************************************************
      * 			Atributos
      *****************************************************************/
     /**
      * El identificador ÚNICO de los operadores
      */
     private long id;
     
     /**
      * El nombre del operador
      */
     private String nombre;
     
     /**
      * El tipo del operador ('Persona Natural', 'Hotel', 'Hostal', 'Residencia Universitaria')
      */
     private String tipo;

      /**
      * Lista de alojamientos del operador
      */
      private List<Alojamiento> listaAlojamientos;

      /**
      * Lista de ofertas del operador
      */
      private List<Oferta> listaOfertas;

     /**
      * Ganancias del operador en el año actual
      */
      private Integer gananciasAñoActual;

     /**
      * Ganancias del operador en el año corrido
      */
      private Integer gananciasAñoCorrido;
     
 
     /* ****************************************************************
      * 			Métodos 
      *****************************************************************/
     /**
      * Constructor por defecto
      */
     public Operador() 
     {
         this.id = 0;
         this.nombre = "";
         this.tipo = "";
         this.listaAlojamientos = new ArrayList<Alojamiento>();
         this.listaOfertas = new ArrayList<Oferta>();
         this.gananciasAñoActual =  0;
         this.gananciasAñoCorrido = 0;  
        
     }
 
     /**
      * Constructor con valores
      * @param id - El id del operador
      * @param nombre - El nombre del operador
      * @param tipo - El presupuesto del operador ('Persona Natural', 'Hotel', 'Hostal', 'Residencia Universitaria')
      */
     public Operador(long id, String nombre, String tipo) 
     {
         this.id = id;
         this.nombre = nombre;
         this.tipo = tipo;

        //Estos valores no se conocen al momento de crear al Operador
         this.listaAlojamientos = new ArrayList<Alojamiento>();
         this.listaOfertas = new ArrayList<Oferta>();
         this.gananciasAñoActual = 0;
         this.gananciasAñoCorrido = 0;

     }
 
     /**
      * @return El id del operador
      */
     public long getId() 
     {
         return id;
     }
     
     /**
      * @param id - El nuevo id del operador
      */
     public void setId(long id) 
     {
         this.id = id;
     }
     
     /**
      * @return el nombre del operador
      */
     public String getNombre() 
     {
         return nombre;
     }
     
     /**
      * @param nombre El nuevo nombre del operador
      */
     public void setNombre(String nombre) 
     {
         this.nombre = nombre;
     }
     
     /**
      * @return el tipo del operador
      */
     public String getTipo() 
     {
         return tipo;
     }
     
     /**
      * @param tipo - el nuevo tipo del operador
      */
     public void setTipo(String tipo) 
     {
         this.tipo = tipo;
     }
     
    /**
	 * @return Las ofertas del operador
	 */
	public List<Oferta> getOfertas() 
	{
		return listaOfertas;
	}

	/**
	 * @param listaOfertas - La nueva lista de ofertas del operador
	 */
	public void setOfertas(List<Oferta> listaOfertas) 
	{
		this.listaOfertas = listaOfertas;
	}

    /**
	 * @return Los alojamientos del operador
	 */
	public List<Alojamiento> getAlojamientos() 
	{
		return listaAlojamientos;
	}

	/**
	 * @param listaAlojamientos - La nueva lista de alojamientos del operador
	 */
	public void setAlojamientos(List<Alojamiento> listaAlojamientos) 
	{
		this.listaAlojamientos = listaAlojamientos;
	}

    public Integer getGananciasAñoActual() {
        return gananciasAñoActual;
    }

    public Integer getGananciasAñoCorrido() {
        return gananciasAñoCorrido;
    }

    public void setGananciasAñoActual(Integer gananciasAñoActual) {
        this.gananciasAñoActual += gananciasAñoActual;
    }

    public void setGananciasAñoCorrido(Integer gananciasAñoCorrido) {
        this.gananciasAñoCorrido += gananciasAñoCorrido;
    }
     
     @Override
     /**
      * @return Una cadena de caracteres con todos los atributos del operador
      */
     public String toString() 
     {
         return "Operador [id=" + id + ", nombre=" + nombre + ", tipo=" + tipo + "]";
     }

     
     /**
      * @return Una cadena de caracteres con todos los atributos del operador completo
      */
     public String toStringCompleto() 
     {
         return "Operador [id=" + id + ", nombre=" + nombre + ", tipo=" + tipo +
          " alojamientos: " + listaAlojamientos + " ofertas: " + listaOfertas + "ganancias: " + gananciasAñoActual + gananciasAñoCorrido + "]";
     }




 }
 