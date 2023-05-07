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
import uniandes.isis2304.parranderos.negocio.Alojamiento;
import uniandes.isis2304.parranderos.negocio.VOAlojamiento;
import uniandes.isis2304.parranderos.negocio.VODetalle_aloj;
import uniandes.isis2304.parranderos.negocio.VOOperador;
public class DetallesAlojTest {

    /* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(DetallesAlojTest.class.getName());
	
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
	public void unicidadDATest() 
	{
    	// Probar primero la conexión a la base de datos
		try
		{
			log.info ("Probando la restricción de UNICIDAD del idAlojamiento del DA");
			alohandes = new Alohandes (openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de UNICIDAD de DA incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de UNICIDAD de DA incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de aloahandes y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
		try
		{
            // Lectura de los Alojameintos con la tabla vacía
			List <VODetalle_aloj> lista = alohandes.darVODetalle_aloj();
			assertEquals ("Debe haber 0 alojamientos base", 0, lista.size ());

			// Lectura de los DA con un DA adicionado
			VOOperador operador1 = alohandes.adicionarOperador("Ritz", "Hotel");
            VOAlojamiento aloj1 = alohandes.adicionarAlojamiento(operador1.getId(), "Habitacion");
			VODetalle_aloj DA1 = alohandes.adicionarDetalle_aloj(aloj1.getId(), "Estandar", 10, 0, 0, 0, 0);
			VODetalle_aloj DA2 = alohandes.adicionarDetalle_aloj(aloj1.getId(), "Estandar", 10, 0, 0, 0, 0);
			assertNull ("No puede adicionar dos DA del mismo operador !!", DA2);
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
	public void integridadDATest() 
	{
    	// Probar primero la conexión a la base de datos
		try
		{
			log.info ("Probando la restricción de INTEGRIDAD  del DA");
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
            VOAlojamiento aloj1 = alohandes.adicionarAlojamiento(operador1.getId(), "Habitacion");
			VODetalle_aloj DA1 = alohandes.adicionarDetalle_aloj(aloj1.getId(), "Estandar", 10, 0, 0, 0, 0);
			VODetalle_aloj DA2 = alohandes.adicionarDetalle_aloj(666, "Estandar", 10, 0, 0, 0, 0);
			assertNull ("No hay operador con el id 666 !!", DA2);
			assertTrue("El primer DO si debio ser añadido", DA1.getIdAlojamiento() == aloj1.getId());

			// Prueba de borrado de llaves maestras y dependientes
			long [] eliminados = alohandes.eliminarAlojamientoYDetalle(aloj1.getId());
			List <VODetalle_aloj> lista = alohandes.darVODetalle_aloj();
			List<Alojamiento> Ops = alohandes.darAlojamientos();
			assertEquals("Debe haber 0 alojamientos",0, Ops.size());
			assertEquals("Debe haber 0 DA",0, lista.size());
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
            // Lectura de los DO con un DO adicionado
			VOOperador operador1 = alohandes.adicionarOperador("Ritz", "Hotel");
            VOOperador operador2 = alohandes.adicionarOperador("Magola", "Hostal");
            VOAlojamiento aloj1 = alohandes.adicionarAlojamiento(operador1.getId(), "Habitacion");
            VOAlojamiento aloj2 = alohandes.adicionarAlojamiento(operador2.getId(), "Habitacion");
			VODetalle_aloj DA1 = alohandes.adicionarDetalle_aloj(aloj1.getId(), "Estandar", 10, 0, 0, 0, 0);
			VODetalle_aloj DA2 = alohandes.adicionarDetalle_aloj(aloj2.getId(), "Closet", 10, 3, -1000, -1000, 3);
			
			assertNull ("Closet no es un tipo de categoria valido !!", DA2);
            assertNull ("El costo de la admon debe ser positivo !!", DA2);
            assertNull ("El costo del seguro debe ser positivo !!", DA2);
            assertNull ("El servicio debe ser 1 o 0!!", DA2);
            assertNull ("El amoblado debe ser 1 o 0!!", DA2);
			assertTrue("El primer DA si debio ser añadido", DA1.getIdAlojamiento() == aloj1.getId());

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
