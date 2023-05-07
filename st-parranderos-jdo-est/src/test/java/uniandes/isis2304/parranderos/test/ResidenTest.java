package uniandes.isis2304.parranderos.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
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
import uniandes.isis2304.parranderos.negocio.Alojamiento;
import uniandes.isis2304.parranderos.negocio.Oferta;
import uniandes.isis2304.parranderos.negocio.Operador;
import uniandes.isis2304.parranderos.negocio.Reserva;
import uniandes.isis2304.parranderos.negocio.Residen;
import uniandes.isis2304.parranderos.negocio.Residente;
import uniandes.isis2304.parranderos.negocio.VOOferta;
import uniandes.isis2304.parranderos.negocio.VOResiden;
import uniandes.isis2304.parranderos.negocio.VOResidente;
import uniandes.isis2304.parranderos.negocio.VOReserva;

public class ResidenTest {
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
    public void unicidadResidenTest() {
        // Probar primero la conexión a la base de datos
        try {
            log.info("Probando la restricción de UNICIDAD del nombre del tipo de bebida");
            alohandes = new Alohandes(openConfig(CONFIG_TABLAS_A));
        } catch (Exception e) {
            // e.printStackTrace();
            log.info(
                    "Prueba de UNICIDAD de Residen incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: "
                            + e.getClass().getName());
            log.info("La causa es: " + e.getCause().toString());

            String msg = "Prueba de UNICIDAD de Residen incompleta. No se pudo conectar a la base de datos !!.\n";
            msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
            System.out.println(msg);
            fail(msg);
        }

        // Ahora si se pueden probar las operaciones
        try {
            // Lectura de los tipos de bebida con la tabla vacía
            List<VOResiden> lista = alohandes.darVOResiden();
            assertEquals("Debe haber 0 operadores base", 0, lista.size());

            // Lectura de los operadores con un operador adicionado
            Operador operador = alohandes.adicionarOperador("Ritz", "Hotel");
            Alojamiento alojamiento = alohandes.adicionarAlojamiento(operador.getId(), "Habitacion");
            VOOferta oferta = alohandes.adicionarOferta(alojamiento.getId(), operador.getId(),
                    new Timestamp(0, 0, 0, 0, 0, 0, 0), 1.0, 1);

            VOResidente residente = alohandes.adicionarResidente("Juan", "Estudiante");
            VOReserva reserva = alohandes.adicionarReserva(residente.getId(), 5, new Timestamp(0, 0, 0, 0, 0, 0, 0));
            VOResiden residen = alohandes.adicionarResiden(reserva.getId(), oferta.getId());

            lista = alohandes.darVOResiden();

            assertEquals("Debe haber un residen creado !!", 1, lista.size());

            VOResiden residen2 = alohandes.adicionarResiden(reserva.getId(), oferta.getId());
            assertNull("No puede adicionar dos residen con el mismo id !!", residen2);
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
    public void CRDResidenTest() {
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

            String msg = "Prueba de CRD de Tipobebida incompleta. No se pudo conectar a la base de datos !!.\n";
            msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
            System.out.println(msg);
            fail(msg);
        }

        // Ahora si se pueden probar las operaciones
        try {
            // Lectura de los tipos de bebida con la tabla vacía
            List<VOResiden> lista = alohandes.darVOResiden();
            assertEquals("Debe haber 0 operadores base", 0, lista.size());

            // Lectura de los tipos de bebida con un tipo de bebida adicionado
            // Lectura de los operadores con un operador adicionado
            Operador operador = alohandes.adicionarOperador("Ritz", "Hotel");
            Alojamiento alojamiento = alohandes.adicionarAlojamiento(operador.getId(), "Habitacion");
            VOOferta oferta = alohandes.adicionarOferta( alojamiento.getId(), operador.getId(),
                    new Timestamp(0, 0, 0, 0, 0, 0, 0), 1.0, 1);

            VOResidente residente = alohandes.adicionarResidente("Juan", "Estudiante");
            VOReserva reserva = alohandes.adicionarReserva(residente.getId(), 5, new Timestamp(0, 0, 0, 0, 0, 0, 0));
            VOResiden residen = alohandes.adicionarResiden(reserva.getId(), oferta.getId());

            lista = alohandes.darVOResiden();

            assertEquals("Debe haber un tipo de bebida creado !!", 1, lista.size());
            assertTrue("El objeto creado y el traido de la BD deben ser iguales !!", residen.getIdOferta() == lista.get(0).getIdOferta()
                    && residen.getIdReserva() == lista.get(0).getIdReserva());
            // Lectura de los operadores con un operador adicionado.
            Operador operador2 = alohandes.adicionarOperador("Ritzax", "Hotel");
            Alojamiento alojamiento2 = alohandes.adicionarAlojamiento(operador2.getId(), "Habitacion");
            VOOferta oferta2 = alohandes.adicionarOferta( alojamiento2.getId(), operador2.getId(),
                    new Timestamp(0, 0, 0, 0, 0, 0, 0), 1.0, 1);

            VOResidente residente2 = alohandes.adicionarResidente("Juanitolbn", "Estudiante");
            VOReserva reserva2 = alohandes.adicionarReserva(residente2.getId(), 5, new Timestamp(0, 0, 0, 0, 0, 0, 0));
            VOResiden residen2 = alohandes.adicionarResiden(reserva2.getId(), oferta2.getId());
            // Prueba de eliminación de un tipo de bebida, dado su identificador
            alohandes.eliminarResidenPorId(residen.getIdReserva(), residen.getIdOferta());
            lista = alohandes.darVOResiden();
            assertEquals("Debe haberse eliminado un residen !!", 1, lista.size());
            assertFalse("El primer tipo de bebida adicionado NO debe estar en la tabla",
                    residen2.equals(lista.get(0)));

        } catch (Exception e) {
            // e.printStackTrace();
            String msg = "Error en la ejecución de las pruebas de operaciones sobre la tabla TipoBebida.\n";
            msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
            System.out.println(msg);

            fail("Error en las pruebas sobre la tabla TipoBebida");
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
