package uniandes.isis2304.parranderos.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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
import uniandes.isis2304.parranderos.negocio.VOResidente;

public class ResidenteTest {
    /*
     * ****************************************************************
     * Constantes
     *****************************************************************/
    /**
     * Logger para escribir la traza de la ejecución
     */
    private static Logger log = Logger.getLogger(AlojamientoTest.class.getName());

    /**
     * Ruta al archivo de configuración de los nombres de tablas de la base de
     * datos: La unidad de persistencia existe y el esquema de la BD también
     */
    private static final String CONFIG_TABLAS_A = "./src/main/resources/config/TablasBD_A.json";

    /*
     * ****************************************************************
     * Atributos
     *****************************************************************/
    /**
     * Objeto JSON con los nombres de las tablas de la base de datos que se quieren
     * utilizar
     */
    private JsonObject tableConfig;

    /**
     * La clase que se quiere probar
     */
    private Alohandes alohandes;

    @Test
    public void unicidadResidenteTest() {
        // Probar primero la conexión a la base de datos
        try {
            log.info("Probando la restricción de UNICIDAD del nombre del tipo de bebida");
            alohandes = new Alohandes(openConfig(CONFIG_TABLAS_A));
        } catch (Exception e) {
            // e.printStackTrace();
            log.info(
                    "Prueba de UNICIDAD de Operador incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: "
                            + e.getClass().getName());
            log.info("La causa es: " + e.getCause().toString());

            String msg = "Prueba de UNICIDAD de Operador incompleta. No se pudo conectar a la base de datos !!.\n";
            msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
            System.out.println(msg);
            fail(msg);
        }

        // Ahora si se pueden probar las operaciones
        try {
            // Lectura de los tipos de bebida con la tabla vacía
            List<VOResidente> lista = alohandes.darVOResidentes();
            assertEquals("Debe haber 0 operadores base", 0, lista.size());

            // Lectura de los operadores con un operador adicionado
            VOResidente residente = alohandes.adicionarResidente("Miguel", "Estudiante");

            lista = alohandes.darVOResidentes();

            assertEquals("Debe haber un tipo de bebida creado !!", 1, lista.size());

        } catch (Exception e) {
            e.printStackTrace();
            String msg = "Error en la ejecución de las pruebas de UNICIDAD sobre la tabla Oferta.\n";
            msg += "Revise el log de alohandes y el de datanucleus para conocer el detalle de la excepción";
            System.out.println(msg);

            fail("Error en las pruebas de UNICIDAD sobre la tabla alohandes");
        } finally {
            alohandes.limpiarAlohandes();
            alohandes.cerrarUnidadPersistencia();
        }
    }

    @Test
    public void chequeoResidenteTest() {
        // Probar primero la conexión a la base de datos
        try {
            log.info("Probando la restricción de UNICIDAD del nombre del tipo de residente");
            alohandes = new Alohandes(openConfig(CONFIG_TABLAS_A));
        } catch (Exception e) {
            // e.printStackTrace();
            log.info(
                    "Prueba de UNICIDAD de Alojamiento incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: "
                            + e.getClass().getName());
            log.info("La causa es: " + e.getCause().toString());

            String msg = "Prueba de UNICIDAD de Alojamiento incompleta. No se pudo conectar a la base de datos !!.\n";
            msg += "Revise el log de alohandes y el de datanucleus para conocer el detalle de la excepción";
            System.out.println(msg);
            fail(msg);
        }

        // Ahora si se pueden probar las operaciones
        try {
            // Lectura de los Alojamientos con la tabla vacía
            List<VOResidente> lista = alohandes.darVOResidentes();
            assertEquals("Debe haber 0 alojamientos base", 0, lista.size());

            // Lectura de los Alojamientos con un alojamiento adicionado con tipo equivocado
            VOResidente residente = alohandes.adicionarResidente("Miguel", "Estudianteeee");
            assertNull("El tipo no esta entre los permitidos !!", residente);

        } catch (Exception e) {
            e.printStackTrace();
            String msg = "Error en la ejecución de las pruebas de CHEQUEO sobre la tabla Alojamiento.\n";
            msg += "Revise el log de alohandes y el de datanucleus para conocer el detalle de la excepción";
            System.out.println(msg);

            
        } finally {
            alohandes.limpiarAlohandes();
            alohandes.cerrarUnidadPersistencia();
        }
    }

    /**
     * Método que prueba las operaciones sobre la tabla Alojamiento
     * 1. Adicionar un Alojamiento
     * 2. Listar el contenido de la tabla con 0, 1 y 2 registros insertados
     * 3. Borrar un Alojamiento por su identificador
     */
    @Test
    public void CRDResidenteTest() {
        // Probar primero la conexión a la base de datos
        try {
            log.info("Probando las operaciones CRD sobre TipoBebida");
            alohandes = new Alohandes(openConfig(CONFIG_TABLAS_A));
        } catch (Exception e) {
            // e.printStackTrace();
            log.info(
                    "Prueba de CRD de Alojamiento incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: "
                            + e.getClass().getName());
            log.info("La causa es: " + e.getCause().toString());

            String msg = "Prueba de CRD de Alojamiento incompleta. No se pudo conectar a la base de datos !!.\n";
            msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
            System.out.println(msg);
            fail(msg);
        }

        // Ahora si se pueden probar las operaciones
        try {

            List<VOResidente> lista = alohandes.darVOResidentes();
            assertEquals("Debe haber 0 alojamientos base", 0, lista.size());

            VOResidente residente = alohandes.adicionarResidente("Miguel", "Estudiante");
            lista = alohandes.darVOResidentes();
            assertEquals("Debe haber un alojamiento creado !!", 1, lista.size());

            VOResidente residente2 = alohandes.adicionarResidente("Juan", "Estudiante");
            lista = alohandes.darVOResidentes();
            assertEquals("Debe haber dos residentes creados !!", 2, lista.size());

            alohandes.eliminarResidentePorId(residente.getId());
            lista = alohandes.darVOResidentes();
            assertEquals("El primer alojamiento adicionado debe estar en la tabla", residente2.getId(),
                    lista.get(0).getId());

        } catch (Exception e) {
            // e.printStackTrace();
            String msg = "Error en la ejecución de las pruebas de operaciones sobre la tabla Operador.\n";
            msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
            System.out.println(msg);

            fail("Error en las pruebas sobre la tabla Operador");
        } finally {
            alohandes.limpiarAlohandes();
            alohandes.cerrarUnidadPersistencia();
        }
    }

    /*
     * ****************************************************************
     * Métodos de prueba para otras operaciones sobre Alojamiento
     *****************************************************************/
    /**
     * Método que prueba las operaciones sobre la tabla Alojamiento
     * 1. Dar un alojamiento por Id
     * 2. Dar un alojamiento Completo
     * 3. Dar alojamientos ofertados en un rango de fechas y cumplen con unos
     * servicios
     */

    /*
     * ****************************************************************
     * Métodos de configuración
     *****************************************************************/
    /**
     * Lee datos de configuración para la aplicación, a partir de un archivo JSON o
     * con valores por defecto si hay errores.
     * 
     * @param tipo       - El tipo de configuración deseada
     * @param archConfig - Archivo Json que contiene la configuración
     * @return Un objeto JSON con la configuración del tipo especificado
     *         NULL si hay un error en el archivo.
     */
    private JsonObject openConfig(String archConfig) {
        JsonObject config = null;
        try {
            Gson gson = new Gson();
            FileReader file = new FileReader(archConfig);
            JsonReader reader = new JsonReader(file);
            config = gson.fromJson(reader, JsonObject.class);
            log.info("Se encontró un archivo de configuración de tablas válido");
        } catch (Exception e) {
            // e.printStackTrace ();
            log.info("NO se encontró un archivo de configuración válido");
            JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de tablas válido: ",
                    "TipoBebidaTest", JOptionPane.ERROR_MESSAGE);
        }
        return config;
    }
}
