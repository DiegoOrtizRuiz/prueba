package uniandes.isis2304.parranderos.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import java.io.FileReader;
import java.sql.Timestamp;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import org.junit.Test;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import uniandes.isis2304.parranderos.negocio.Alohandes;
import uniandes.isis2304.parranderos.negocio.Reserva;
import uniandes.isis2304.parranderos.negocio.Residente;
import uniandes.isis2304.parranderos.negocio.VOReserva;
import uniandes.isis2304.parranderos.negocio.VOResidente;

public class ReservaTest {
    /*
     * ****************************************************************
     * Constantes
     *****************************************************************/
    /**
     * Logger para escribir la traza de la ejecución
     */
    private static Logger log = Logger.getLogger(OfertaTest.class.getName());

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
    public void unicidadReservaTest() {
        // Probar primero la conexión a la base de datos
        try {
            log.info("Probando la restricción de UNICIDAD del nombre de una reserva");
            alohandes = new Alohandes(openConfig(CONFIG_TABLAS_A));
        } catch (Exception e) {
            // e.printStackTrace();
            log.info(
                    "Prueba de UNICIDAD de Reserva incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: "
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
            List<VOReserva> lista = alohandes.darVOOReservas();
            assertEquals("Debe haber 0 reservas base", 0, lista.size());

            // Lectura de los operadores con un operador adicionado
            VOResidente residente = alohandes.adicionarResidente("Test", "Estudiante");
            VOReserva reserva = alohandes.adicionarReserva(residente.getId(), 1, new Timestamp(0, 0, 0, 0, 0, 0, 0));
            lista = alohandes.darVOOReservas();

            assertEquals("Debe haber un tipo de bebida creado !!", 1, lista.size());
        } catch (Exception e) {
            e.printStackTrace();
            String msg = "Error en la ejecución de las pruebas de UNICIDAD sobre la tabla Oferta.\n";
            msg += "Revise el log de alohandes y el de datanucleus para conocer el detalle de la excepción";
            System.out.println(msg);

        } finally {
            alohandes.limpiarAlohandes();
            alohandes.cerrarUnidadPersistencia();
        }
    }


    /*
     * ****************************************************************
     * Métodos de prueba para la tabla Operadores - Creación y borrado
     *****************************************************************/
    /**
     * Método que prueba las operaciones sobre la tabla TipoBebida
     * 1. Adicionar un tipo de bebida
     * 2. Listar el contenido de la tabla con 0, 1 y 2 registros insertados
     * 3. Borrar un tipo de bebida por su identificador
     * 4. Borrar un tipo de bebida por su nombre
     */
    @Test
    public void CRDReservaTest() {
        // Probar primero la conexión a la base de datos
        try {
            log.info("Probando las operaciones CRD sobre TipoBebida");
            alohandes = new Alohandes(openConfig(CONFIG_TABLAS_A));
        } catch (Exception e) {
            // e.printStackTrace();
            log.info(
                    "Prueba de CRD de Tipobebida incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: "
                            + e.getClass().getName());
            log.info("La causa es: " + e.getCause().toString());

            String msg = "Prueba de CRD de Reserva incompleta. No se pudo conectar a la base de datos !!.\n";
            msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
            System.out.println(msg);
            
        }

        // Ahora si se pueden probar las operaciones
        try {
            // Lectura de los tipos de bebida con la tabla vacía
            List<VOReserva> lista = alohandes.darVOOReservas();
            assertEquals("Debe haber 0 reservas base", 0, lista.size());

            // Lectura de los operadores con un operador adicionado con tipo equivocado
            VOResidente residente = alohandes.adicionarResidente("Test", "Estudiante");
            VOReserva reserva = alohandes.adicionarReserva(residente.getId(), 1, new Timestamp(0, 0, 0, 0, 0, 0, 0));

            lista = alohandes.darVOOReservas();

            assertEquals("Debe haber una reserva creado !!", 1, lista.size());
            assertEquals("El objeto creado y el traido de la BD deben ser iguales !!", reserva.getIdResidente(), lista.get(0).getIdResidente());

            VOReserva reserva2 = alohandes.adicionarReserva(residente.getId(), 1, new Timestamp(0, 0, 0, 0, 0, 0, 0));
            // Prueba de eliminación de un tipo de bebida, dado su identificador
            alohandes.eliminarReservaPorId(lista.get(0).getId());
            lista = alohandes.darVOOReservas();
            assertEquals("Debe haberse eliminado una reserva !!", 1, lista.size());
            assertFalse("La primera reserva adicionada NO debe estar en la tabla",
                    reserva2.equals(lista.get(0)));

        } catch (Exception e) {
            // e.printStackTrace();
            String msg = "Error en la ejecución de las pruebas de operaciones sobre la tabla TipoBebida.\n";
            msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
            System.out.println(msg);

        } finally {
            alohandes.limpiarAlohandes();
            alohandes.cerrarUnidadPersistencia();
        }
    }

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
