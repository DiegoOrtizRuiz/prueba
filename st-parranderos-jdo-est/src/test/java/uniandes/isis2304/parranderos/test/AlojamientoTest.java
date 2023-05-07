package uniandes.isis2304.parranderos.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.FileReader;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import org.junit.Test;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import uniandes.isis2304.parranderos.negocio.Alohandes;
import uniandes.isis2304.parranderos.negocio.Alojamiento;
import uniandes.isis2304.parranderos.negocio.Servicio;
import uniandes.isis2304.parranderos.negocio.VOAlojamiento;
import uniandes.isis2304.parranderos.negocio.VOOferta;
import uniandes.isis2304.parranderos.negocio.VOOperador;
import uniandes.isis2304.parranderos.negocio.VOServicio;
import uniandes.isis2304.parranderos.negocio.VOTieneServicio;

public class AlojamientoTest {
    /* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(AlojamientoTest.class.getName());
	
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
	public void chequeoAlojamientoTest() 
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
			log.info ("Prueba de UNICIDAD de Alojamiento incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de UNICIDAD de Alojamiento incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de alohandes y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
		try
		{
			// Lectura de los Alojamientos con la tabla vacía
			List <VOAlojamiento> lista = alohandes.darVOAlojamientos();
			assertEquals ("Debe haber 0 alojamientos base", 0, lista.size ());

			// Lectura de los Alojamientos con un alojamiento adicionado con tipo equivocado
			VOOperador operador1 = alohandes.adicionarOperador("Ritz", "Hotel");
            VOAlojamiento aloj1 = alohandes.adicionarAlojamiento(operador1.getId(), "Suit Presidencial");
			assertNull ("El tipo no esta entre los permitidos !!", aloj1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			String msg = "Error en la ejecución de las pruebas de CHEQUEO sobre la tabla Alojamiento.\n";
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

    @Test
	public void IntegridadFKAlojamientoTest() 
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
			log.info ("Prueba de UNICIDAD de Alojamiento incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de UNICIDAD de Alojamiento incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de alohandes y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
		try
		{
			// Lectura de los Alojamientos con la tabla vacía
			List <VOAlojamiento> lista = alohandes.darVOAlojamientos();
			assertEquals ("Debe haber 0 alojamientos base", 0, lista.size ());

			// Lectura de los Alojamientos con un alojamiento adicionado con un FK referenciada y otra no
			VOOperador operador1 = alohandes.adicionarOperador("Ritz", "Hotel");
            VOAlojamiento aloj1 = alohandes.adicionarAlojamiento(operador1.getId(), "Habitacion");
            VOAlojamiento aloj2 = alohandes.adicionarAlojamiento(666, "Habitacion");
			assertNull ("No existe operador con el id 666!!", aloj2);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			String msg = "Error en la ejecución de las pruebas de CHEQUEO sobre la tabla Alojamiento.\n";
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

    /**
	 * Método que prueba las operaciones sobre la tabla Alojamiento
	 * 1. Adicionar un Alojamiento
	 * 2. Listar el contenido de la tabla con 0, 1 y 2 registros insertados
	 * 3. Borrar un Alojamiento por su identificador
	 */
    @Test
	public void CRDAlojamientoTest() 
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
			log.info ("Prueba de CRD de Alojamiento incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de CRD de Alojamiento incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
    	try
		{
			// Lectura de los Alojameintos con la tabla vacía
			List <VOAlojamiento> lista = alohandes.darVOAlojamientos();
			assertEquals ("Debe haber 0 alojamientos base", 0, lista.size ());

			// Lectura de los Alojamientos con un operador adicionado
			VOOperador operador1 = alohandes.adicionarOperador("Ritz", "Hotel");
            VOAlojamiento aloj1 = alohandes.adicionarAlojamiento(operador1.getId(), "Habitacion");
			lista = alohandes.darVOAlojamientos();
			assertEquals ("Debe haber un alojamiento creado !!", 1, lista.size ());

			// Lectura de los alojamientos con dos alojamientos adicionados

            VOAlojamiento aloj2 = alohandes.adicionarAlojamiento(operador1.getId(), "Habitacion");
			lista = alohandes.darVOAlojamientos();
			assertEquals ("Debe haber dos alojamientos creados !!", 2, lista.size ());
			assertTrue ("El primer alojamiento adicionado debe estar en la tabla", aloj1.getId() == (lista.get (0).getId()) || aloj1.getId() == (lista.get (1).getId()));
			assertTrue ("El segundo alojameinto adicionado debe estar en la tabla",  aloj2.getId() == (lista.get (0).getId()) || aloj2.getId() == (lista.get (1).getId()));

			// Prueba de eliminación de un alojamiento dado su identificador
			long alojEliminados = alohandes.eliminarAlojamientoporId(aloj1.getId());
			assertEquals ("Debe haberse eliminado un alojamiento !!", 1, alojEliminados);
			lista = alohandes.darVOAlojamientos();
			assertEquals ("Debe haber un solo alojamiento !!", 1, lista.size ());
			assertFalse ("El primer alojamiento adicionado NO debe estar en la tabla", aloj1.equals (lista.get (0)));
			assertTrue ("El segundo alojamiento adicionado debe estar en la tabla",  aloj2.getId() == (lista.get (0).getId()));

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
	 * 			Métodos de prueba para otras operaciones sobre Alojamiento
	 *****************************************************************/
	/**
	 * Método que prueba las operaciones sobre la tabla Alojamiento
	 * 1. Dar un alojamiento por Id
	 * 2. Dar un alojamiento Completo
	 * 3. Dar alojamientos ofertados en un rango de fechas y cumplen con unos servicios
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
			 // Lectura de los Alojameintos con la tabla vacía
			List <VOAlojamiento> lista = alohandes.darVOAlojamientos();
			assertEquals ("Debe haber 0 alojamientos base", 0, lista.size ());

			// Lectura de los Alojamientos con un operador adicionado
			VOOperador operador1 = alohandes.adicionarOperador("Ritz", "Hotel");
            VOAlojamiento aloj1 = alohandes.adicionarAlojamiento(operador1.getId(), "Habitacion");
			lista = alohandes.darVOAlojamientos();
			assertEquals ("Debe haber un alojamiento creado !!", 1, lista.size ());
 
            // Prueba llamar a un alojamiento por su identificador
            VOAlojamiento alojLlamado = alohandes.darAlojamientoPorId(aloj1.getId());
            assertTrue ("Debe ser el mismo alojamiento de la tabla", alojLlamado.getId() == (lista.get (0).getId()));

            //Prueba dar un alojamiento completo
            VOServicio servicio1 = alohandes.adicionServicio("Jacuzzi", 2500);
            VOTieneServicio TS1 = alohandes.adicionTS(aloj1.getId(), servicio1.getId());
            VOAlojamiento alojCompleto1 = alohandes.darAlojamientoCompleto(aloj1.getId());
            List<Servicio> servicioAloj1 = alojCompleto1.getServicios();
            assertEquals("El nombre del primer servicio debe ser Jacuzzi",servicioAloj1.get(0).getNombre(), "Jacuzzi");

            //Prueba RFC4
            LocalDate myDate = LocalDate.of(2023, 3, 15); 
			Timestamp fechaOferta = Timestamp.valueOf(myDate.atStartOfDay());

            LocalDate myDate1 = LocalDate.of(2023, 3, 1); 
			Timestamp fechaInicial = Timestamp.valueOf(myDate1.atStartOfDay());

            LocalDate myDate2 = LocalDate.of(2023, 3, 30); 
			Timestamp fechaFinal = Timestamp.valueOf(myDate2.atStartOfDay());

            VOServicio servicio2 = alohandes.adicionServicio("Cocineta", 1000);
            VOTieneServicio TS2 = alohandes.adicionTS(aloj1.getId(), servicio2.getId());
            
            List<String> listaServicios = new ArrayList<String>();
            listaServicios.add(servicio1.getNombre());
            listaServicios.add(servicio2.getNombre());

            VOOferta oferta1 = alohandes.adicionarOferta(aloj1.getId(), operador1.getId(), fechaOferta, 250000, 8);

            List<Alojamiento> alojamientosCumplen = alohandes.RFC4(fechaInicial, fechaFinal, listaServicios);

            assertEquals("El nombre del primer servicio debe ser alojamiento 1 debe ser jacuzzi", alojamientosCumplen.get(0).getServicios().get(0).getNombre(), "Jacuzzi");

            
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