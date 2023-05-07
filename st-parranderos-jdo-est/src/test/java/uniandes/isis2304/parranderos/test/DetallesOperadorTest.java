package uniandes.isis2304.parranderos.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.FileReader;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import org.junit.Test;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import uniandes.isis2304.parranderos.negocio.Alohandes;
import uniandes.isis2304.parranderos.negocio.Detalle_operadores;
import uniandes.isis2304.parranderos.negocio.Operador;
import uniandes.isis2304.parranderos.negocio.VODetalle_operadores;
import uniandes.isis2304.parranderos.negocio.VOOperador;

public class DetallesOperadorTest {

    /* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(DetallesOperadorTest.class.getName());
	
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
	public void unicidadDOTest() 
	{
    	// Probar primero la conexión a la base de datos
		try
		{
			log.info ("Probando la restricción de UNICIDAD del idOperador del DO");
			alohandes = new Alohandes (openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de UNICIDAD de DO incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de UNICIDAD de DO incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de aloahandes y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
		try
		{
			// Lectura de los DO con un DO adicionado
			VOOperador operador1 = alohandes.adicionarOperador("Ritz", "Hotel");
			VODetalle_operadores DO1 = alohandes.adicionarDetalle_operador(operador1.getId(), 1234567890, 987654321,"Empresa");

			VODetalle_operadores DO2 = alohandes.adicionarDetalle_operador(operador1.getId(), 22222222, 6666666,"Egresado");
			assertNull ("No puede adicionar dos DO del mismo operador !!", DO2);
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
	public void integridadDOTest() 
	{
    	// Probar primero la conexión a la base de datos
		try
		{
			log.info ("Probando la restricción de INTEGRIDAD  del DO");
			alohandes = new Alohandes (openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de INTEGRIDAD de DO incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de INTEGRIDAD de DO incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de aloahandes y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
		try
		{
			// Lectura de los DO con un DO adicionado
			VOOperador operador1 = alohandes.adicionarOperador("Ritz", "Hotel");
			VODetalle_operadores DO1 = alohandes.adicionarDetalle_operador(operador1.getId(), 1234567890, 987654321,"Empresa");
			VODetalle_operadores DO2 = alohandes.adicionarDetalle_operador(666, 22222222, 6666666,"Egresado");
			assertNull ("No hay operador con el id 666 !!", DO2);
			assertTrue("El primer DO si debio ser añadido", DO1.getIdOperador() == operador1.getId());

			// Prueba de borrado de llaves maestras y dependientes
			long [] eliminados = alohandes.eliminarOperadorYDetalle(operador1.getId());
			List<Detalle_operadores> DOs = alohandes.darDetalleOperadores();
			List<Operador> Ops = alohandes.darOperadores();
			assertEquals("Debe haber 0 operadores",0, Ops.size());
			assertEquals("Debe haber 0 DO",0, DOs.size());
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
	public void chequeoDOTest() 
	{
    	// Probar primero la conexión a la base de datos
		try
		{
			log.info ("Probando la restricción de CHEQUEO del DO");
			alohandes = new Alohandes (openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de CHEQUEO de DO incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de CHEQUEO de DO incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de aloahandes y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
		try
		{
			// Lectura de los DO con un DO adicionado
			VOOperador operador1 = alohandes.adicionarOperador("Ritz", "Hotel");
			VOOperador operador2 = alohandes.adicionarOperador("Magola", "Hostal");
			VODetalle_operadores DO1 = alohandes.adicionarDetalle_operador(operador1.getId(), 1234567890, 987654321,"Empresa");
			VODetalle_operadores DO2 = alohandes.adicionarDetalle_operador(operador2.getId(), 22222222, 6666666,"Bufon");
			assertNull ("Bufon no es un tipo de vinculo valido !!", DO2);
			assertTrue("El primer DO si debio ser añadido", DO1.getIdOperador() == operador1.getId());

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
