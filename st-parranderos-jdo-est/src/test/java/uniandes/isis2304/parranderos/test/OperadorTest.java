package uniandes.isis2304.parranderos.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.FileReader;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import org.junit.Test;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import uniandes.isis2304.parranderos.negocio.Alohandes;
import uniandes.isis2304.parranderos.negocio.Alojamiento;
import uniandes.isis2304.parranderos.negocio.Operador;
import uniandes.isis2304.parranderos.negocio.VOAlojamiento;
import uniandes.isis2304.parranderos.negocio.VOOferta;
import uniandes.isis2304.parranderos.negocio.VOOperador;
import uniandes.isis2304.parranderos.negocio.VOReserva;
import uniandes.isis2304.parranderos.negocio.VOResiden;
import uniandes.isis2304.parranderos.negocio.VOResidente;

public class OperadorTest {
    /* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(OperadorTest.class.getName());
	
	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos: La unidad de persistencia existe y el esquema de la BD también
	 */
	private static final String CONFIG_TABLAS_A = "./src/main/resources/config/TablasBD_A.json"; 
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
    /**
     * Objeto JSON con los nombres de las tablas de la base de datos que se quieren utilizar
     */
    private JsonObject tableConfig;
    
	/**
	 * La clase que se quiere probar
	 */
    private Alohandes alohandes;

    @Test
	public void unicidadOperadorTest() 
	{
    	// Probar primero la conexión a la base de datos
		try
		{
			log.info ("Probando la restricción de UNICIDAD del nombre del Operador");
			alohandes = new Alohandes (openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de UNICIDAD de Operador incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de UNICIDAD de Operador incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
		try
		{
			// Lectura de los tipos de bebida con la tabla vacía
			List <VOOperador> lista = alohandes.darVOOperadores();
			assertEquals ("Debe haber 0 operadores base", 0, lista.size ());

			// Lectura de los operadores con un operador adicionado
			String nombreOperador1 = "Ritz";
            String tipoOperador1 = "Hotel";
			VOOperador operador1 = alohandes.adicionarOperador(nombreOperador1, tipoOperador1);
			lista = alohandes.darVOOperadores();
			assertEquals ("Debe haber un tipo de bebida creado !!", 1, lista.size ());

			VOOperador operador2 = alohandes.adicionarOperador(nombreOperador1, tipoOperador1);
			assertNull ("No puede adicionar dos operadores con el mismo nombre !!", operador2);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			String msg = "Error en la ejecución de las pruebas de UNICIDAD sobre la tabla Operadores.\n";
			msg += "Revise el log de alohandes y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);

    		fail ("Error en las pruebas de UNICIDAD sobre la tabla alohandes");
		}    				
		finally
		{
			alohandes.limpiarAlohandes ();
    		alohandes.cerrarUnidadPersistencia ();    		
		}
	}

	@Test
	public void chequeoOperadorTest() 
	{
    	// Probar primero la conexión a la base de datos
		try
		{
			log.info ("Probando la restricción de UNICIDAD del nombre del tipo de bebida");
			alohandes = new Alohandes (openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de UNICIDAD de Operador incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de UNICIDAD de Operador incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
		try
		{
			// Lectura de los tipos de bebida con la tabla vacía
			List <VOOperador> lista = alohandes.darVOOperadores();
			assertEquals ("Debe haber 0 operadores base", 0, lista.size ());

			// Lectura de los operadores con un operador adicionado con tipo equivocado
			String nombreOperador1 = "Ritz";
            String tipoOperador1 = "Condominio";
			VOOperador operador1 = alohandes.adicionarOperador(nombreOperador1, tipoOperador1);
			assertNull ("El tipo no esta entre los permitidos !!", operador1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			String msg = "Error en la ejecución de las pruebas de CHEQUEO sobre la tabla Operadores.\n";
			msg += "Revise el log de alohandes y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);

    		fail ("Error en las pruebas de CHEQUEO sobre la tabla alohandes");
		}    				
		finally
		{
			alohandes.limpiarAlohandes ();
    		alohandes.cerrarUnidadPersistencia ();    		
		}
	}

	/* ****************************************************************
	 * 			Métodos de prueba para la tabla Operadores - Creación y borrado
	 *****************************************************************/
	/**
	 * Método que prueba las operaciones sobre la tabla Operadores
	 * 1. Adicionar un tipo de bebida
	 * 2. Listar el contenido de la tabla con 0, 1 y 2 registros insertados
	 * 3. Borrar un Operador por su identificador
	 */
    @Test
	public void CRDOperadorTest() 
	{
    	// Probar primero la conexión a la base de datos
		try
		{
			log.info ("Probando las operaciones CRD sobre OPERADOR");
			alohandes = new Alohandes (openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de CRD de OPERADOR incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de CRD de OPERADOR incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
    	try
		{
			// Lectura de los tipos de bebida con la tabla vacía
			List <VOOperador> lista = alohandes.darVOOperadores();
			assertEquals ("Debe haber 0 operadores base", 0, lista.size ());

			// Lectura de los operadores con un operador adicionado
			String nombreOperador1 = "Ritz";
            String tipoOperador1 = "Hotel";
			VOOperador operador1 = alohandes.adicionarOperador(nombreOperador1, tipoOperador1);
			lista = alohandes.darVOOperadores();
			assertEquals ("Debe haber un tipo de bebida creado !!", 1, lista.size ());

			// Lectura de los operadores con dos operadores adicionados
			String nombreOperador2 = "Robles";
            String tipoOperador2 = "Hostal";
			VOOperador operador2 = alohandes.adicionarOperador(nombreOperador2, tipoOperador2);
			lista = alohandes.darVOOperadores();
			assertEquals ("Debe haber dos operadores creados !!", 2, lista.size ());
			assertTrue ("El primer operador adicionado debe estar en la tabla", operador1.getId() == (lista.get (0).getId()) || operador1.getId() == (lista.get (1).getId()));
			assertTrue ("El segundo operador adicionado debe estar en la tabla",  operador2.getId() == (lista.get (0).getId()) || operador2.getId() == (lista.get (1).getId()));

			// Prueba de eliminación de un operador, dado su identificador
			long opEliminados = alohandes.eliminarOperadorporId(operador1.getId());
			assertEquals ("Debe haberse eliminado un operador !!", 1, opEliminados);
			lista = alohandes.darVOOperadores();
			assertEquals ("Debe haber un solo operador !!", 1, lista.size ());
			assertFalse ("El primer operador adicionado NO debe estar en la tabla", operador1.equals (lista.get (0)));
			assertTrue ("El segundo operador adicionado debe estar en la tabla",  operador2.getId() == (lista.get (0).getId()));

		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String msg = "Error en la ejecución de las pruebas de operaciones sobre la tabla Operador.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);

    		fail ("Error en las pruebas sobre la tabla Operador");
		}
		finally
		{
			alohandes.limpiarAlohandes ();
    		alohandes.cerrarUnidadPersistencia ();    		
		}
	}

	/* ****************************************************************
	 * 			Métodos de prueba para otras operaciones sobre Operadores
	 *****************************************************************/
	/**
	 * Método que prueba las operaciones sobre la tabla Operadores
	 * 1. Dar un operador por Id
	 * 2. Dar un operador Completo
	 * 3. Dar las ganancias de todos los operadores
	 */

	 @Test
	 public void otrosMetodosTest() 
	 {
		 // Probar primero la conexión a la base de datos
		 try
		 {
			 log.info ("Probando las operaciones CRD sobre TipoBebida");
			 alohandes = new Alohandes (openConfig (CONFIG_TABLAS_A));
		 }
		 catch (Exception e)
		 {
 //			e.printStackTrace();
			 log.info ("Prueba de CRD de Tipobebida incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			 log.info ("La causa es: " + e.getCause ().toString ());
 
			 String msg = "Prueba de CRD de Tipobebida incompleta. No se pudo conectar a la base de datos !!.\n";
			 msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			 System.out.println (msg);
			 fail (msg);
		 }
		 
		 // Ahora si se pueden probar las operaciones
		 try
		 {
			 // Lectura de los tipos de bebida con la tabla vacía
			 List <VOOperador> lista = alohandes.darVOOperadores();
			 assertEquals ("Debe haber 0 operadores base", 0, lista.size ());
 
			 // Lectura de los operadores con un operador adicionado
			 String nombreOperador1 = "Ritz";
			 String tipoOperador1 = "Hotel";
			 VOOperador operador1 = alohandes.adicionarOperador(nombreOperador1, tipoOperador1);
			 lista = alohandes.darVOOperadores();
			 assertEquals ("Debe haber un operador creado !!", 1, lista.size ());
 
			 // Prueba llamar a un operador por su identificador
			 VOOperador operadorLlamado = alohandes.darOperadorPorId(operador1.getId());
			 assertTrue ("Debe ser el mismo operador de la tabla", operadorLlamado.getId() == (lista.get (0).getId()));
		
			 //Prueba dar un operador completo
			 LocalDate myDate = LocalDate.of(2023, 3, 15); // replace with your specific date
			 Timestamp sqlTimestamp = Timestamp.valueOf(myDate.atStartOfDay());

			 VOAlojamiento alojamiento1 = alohandes.adicionarAlojamiento(operador1.getId(), "Apartamento");
			 VOAlojamiento alojamiento2 = alohandes.adicionarAlojamiento(operador1.getId(), "Habitacion");

			 VOOferta oferta1 = alohandes.adicionarOferta(alojamiento1.getId(), operador1.getId(),sqlTimestamp , 10000, 8);
			 VOResidente residente1 = alohandes.adicionarResidente("Juan Perez", "Estudiante" );
			 VOReserva reserva1 = alohandes.adicionarReserva(residente1.getId(), 180, sqlTimestamp );
			 VOResiden residen1 = alohandes.adicionarResiden(reserva1.getId(), oferta1.getId());
			

			 VOOferta oferta2 = alohandes.adicionarOferta(alojamiento2.getId(), operador1.getId(),sqlTimestamp , 5000, 8);
			 VOResidente residente2 = alohandes.adicionarResidente("Jose Diaz", "Estudiante" );
			 VOReserva reserva2 = alohandes.adicionarReserva(residente2.getId(), 180, sqlTimestamp );
			 VOResiden residen2 = alohandes.adicionarResiden(reserva2.getId(), oferta2.getId());

			 VOOperador operadorCompleto1 = alohandes.darOperadorCompleto(operador1.getId());

			 assertEquals("Debe tener dos  alojamiento", 2, operadorCompleto1.getAlojamientos().size());
			 assertEquals("Debe tener una sola oferta", 2, operadorCompleto1.getOfertas().size());
			 assertTrue("Debe tener ganancias actuales de 15000", 15000 == operadorCompleto1.getGananciasAñoActual());
			 assertTrue("Debe tener ganancias corridas de 15000", 15000 == operadorCompleto1.getGananciasAñoCorrido());

			 //Prueba dar un ganancias de todos los operadores
			 String nombreOperador2 = "Marriot";
			 VOOperador operador2 = alohandes.adicionarOperador(nombreOperador2, tipoOperador1);
			 lista = alohandes.darVOOperadores();
			 assertEquals ("Debe haber un operador creado !!", 2, lista.size ());
			 VOOperador operadorCompleto2 = alohandes.darOperadorCompleto(operador2.getId());
			 List<Object []> listaGanancias = alohandes.RFC1();
			 Object[] ganancias1 = listaGanancias.get(0);
			 Object[] ganancias2 = listaGanancias.get(1);
			 assertEquals ("Debe haber dos operadores con ganancias !!", 2, listaGanancias.size ());
			 assertEquals("El segundo operador no debe tener ganancias actuales", 0, ganancias2[1]);
			 assertEquals("El segundo operador no debe tener ganancias corridas", 0, ganancias2[2]);


		 }
		 catch (Exception e)
		 {
 			e.printStackTrace();
			 String msg = "Error en la ejecución de las pruebas de operaciones sobre la tabla Operador.\n";
			 msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			 System.out.println (msg);
 
			 fail ("Error en las pruebas sobre la tabla Operador");
		 }
		 finally
		 {
			 alohandes.limpiarAlohandes ();
			 alohandes.cerrarUnidadPersistencia ();    		
		 }
	 }


    /* ****************************************************************
	 * 			Métodos de configuración
	 *****************************************************************/
    /**
     * Lee datos de configuración para la aplicación, a partir de un archivo JSON o con valores por defecto si hay errores.
     * @param tipo - El tipo de configuración deseada
     * @param archConfig - Archivo Json que contiene la configuración
     * @return Un objeto JSON con la configuración del tipo especificado
     * 			NULL si hay un error en el archivo.
     */
    private JsonObject openConfig (String archConfig)
    {
    	JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontró un archivo de configuración de tablas válido");
		} 
		catch (Exception e)
		{
//			e.printStackTrace ();
			log.info ("NO se encontró un archivo de configuración válido");			
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de tablas válido: ", "TipoBebidaTest", JOptionPane.ERROR_MESSAGE);
		}	
        return config;
    }
    
}
