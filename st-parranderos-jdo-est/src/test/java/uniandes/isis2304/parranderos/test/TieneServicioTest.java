package uniandes.isis2304.parranderos.test;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import java.io.FileReader;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import org.junit.Test;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import uniandes.isis2304.parranderos.negocio.Alohandes;
import uniandes.isis2304.parranderos.negocio.VOAlojamiento;
import uniandes.isis2304.parranderos.negocio.VOOperador;
import uniandes.isis2304.parranderos.negocio.VOServicio;
import uniandes.isis2304.parranderos.negocio.VOTieneServicio;

public class TieneServicioTest {

    /* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(TieneServicioTest.class.getName());
	
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
	public void unicidadTSTest() 
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

			// Lectura de los DA con un DA adicionado
            VOOperador operador1 = alohandes.adicionarOperador("Ritz", "Hotel");
            VOAlojamiento aloj1 = alohandes.adicionarAlojamiento(operador1.getId(), "Habitacion");
            VOServicio serv1 = alohandes.adicionServicio("Jacuzzi", 1000);

			VOTieneServicio TS1 = alohandes.adicionTS(aloj1.getId(), serv1.getId());
            VOTieneServicio TS2 = alohandes.adicionTS(aloj1.getId(), serv1.getId());
			assertNull ("No puede adicionar dos TS del mismo servicio y alojamiento !!", TS2);
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
            VOServicio serv1 = alohandes.adicionServicio("Jacuzzi", 1000);

			VOTieneServicio TS1 = alohandes.adicionTS(aloj1.getId(), serv1.getId());
            VOTieneServicio TS2 = alohandes.adicionTS(666, serv1.getId());
			assertNull ("No hay operador con el id 666 !!", TS2);
			

		
		}
		catch (Exception e)
		{

			String msg = e.getStackTrace().toString();
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
