
package uniandes.isis2304.parranderos.interfazApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.parranderos.negocio.Alohandes;
import uniandes.isis2304.parranderos.negocio.Alojamiento;
import uniandes.isis2304.parranderos.negocio.Oferta;
import uniandes.isis2304.parranderos.negocio.Reserva;
import uniandes.isis2304.parranderos.negocio.Residen;
import uniandes.isis2304.parranderos.negocio.Servicio;
import uniandes.isis2304.parranderos.negocio.VOAlojamiento;
import uniandes.isis2304.parranderos.negocio.VOOferta;
import uniandes.isis2304.parranderos.negocio.VOOperador;
import uniandes.isis2304.parranderos.negocio.VOReserva;
import uniandes.isis2304.parranderos.negocio.VOResiden;
import uniandes.isis2304.parranderos.negocio.VOResidente;
import uniandes.isis2304.parranderos.negocio.VOServicio;
import uniandes.isis2304.parranderos.negocio.VOTieneServicio;


/**
 * Clase principal de la interfaz
 * @author Germán Bravo
 */
@SuppressWarnings("serial")

public class InterfazParranderosApp extends JFrame implements ActionListener
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(InterfazParranderosApp.class.getName());
	
	/**
	 * Ruta al archivo de configuración de la interfaz
	 */
	private static final String CONFIG_INTERFAZ = "st-parranderos-jdo-est/src/main/resources/config/interfaceConfigApp.json"; 
	
	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos
	 */
	private static final String CONFIG_TABLAS = "st-parranderos-jdo-est/src/main/resources/config/TablasBD_A.json"; 
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
    /**
     * Objeto JSON con los nombres de las tablas de la base de datos que se quieren utilizar
     */
    private JsonObject tableConfig;
    
    /**
     * Asociación a la clase principal del negocio.
     */
    private Alohandes alohandes;

	/**
     * Vinculos de usuario
     */
    private List<String> vinculos;
    
	/* ****************************************************************
	 * 			Atributos de interfaz
	 *****************************************************************/
    /**
     * Objeto JSON con la configuración de interfaz de la app.
     */
    private JsonObject guiConfig;
    
    /**
     * Panel de despliegue de interacción para los requerimientos
     */
    private PanelDatos panelDatos;
    
    /**
     * Menú de la aplicación
     */
    private JMenuBar menuBar;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
    /**
     * Construye la ventana principal de la aplicación. <br>
     * <b>post:</b> Todos los componentes de la interfaz fueron inicializados.
     */
    public InterfazParranderosApp( )
    {
        // Carga la configuración de la interfaz desde un archivo JSON
        guiConfig = openConfig ("Interfaz", CONFIG_INTERFAZ);
        
        // Configura la apariencia del frame que contiene la interfaz gráfica
        configurarFrame ( );
        if (guiConfig != null) 	   
        {
     	   crearMenu( guiConfig.getAsJsonArray("menuBar") );
        }
        
        tableConfig = openConfig ("Tablas BD", CONFIG_TABLAS);
        alohandes = new Alohandes (tableConfig);
		vinculos = new ArrayList<>(Arrays.asList("Estudiante", "Profesor", "Profesor Invitado", "Egresado", "Padre", "Empleado", "Empresa"));

        
    	String path = guiConfig.get("bannerPath").getAsString();
        panelDatos = new PanelDatos ( );

        setLayout (new BorderLayout());
        add (new JLabel (new ImageIcon (path)), BorderLayout.NORTH );          
        add( panelDatos, BorderLayout.CENTER );        
    }
    
	/* ****************************************************************
	 * 			Métodos de configuración de la interfaz
	 *****************************************************************/
    /**
     * Lee datos de configuración para la aplicació, a partir de un archivo JSON o con valores por defecto si hay errores.
     * @param tipo - El tipo de configuración deseada
     * @param archConfig - Archivo Json que contiene la configuración
     * @return Un objeto JSON con la configuración del tipo especificado
     * 			NULL si hay un error en el archivo.
     */
    private JsonObject openConfig (String tipo, String archConfig)
    {
    	JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontró un archivo de configuración válido: " + tipo);
		} 
		catch (Exception e)
		{
			e.printStackTrace ();
			log.info ("NO se encontró un archivo de configuración válido");			
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de interfaz válido: " + tipo, "Alohandes App", JOptionPane.ERROR_MESSAGE);
		}	
        return config;
    }
    
    /**
     * Método para configurar el frame principal de la aplicación
     */
    private void configurarFrame(  )
    {
    	int alto = 0;
    	int ancho = 0;
    	String titulo = "";	
    	
    	if ( guiConfig == null )
    	{
    		log.info ( "Se aplica configuración por defecto" );			
			titulo = "Alohandes APP Default";
			alto = 300;
			ancho = 500;
    	}
    	else
    	{
			log.info ( "Se aplica configuración indicada en el archivo de configuración" );
    		titulo = guiConfig.get("title").getAsString();
			alto= guiConfig.get("frameH").getAsInt();
			ancho = guiConfig.get("frameW").getAsInt();
    	}
    	
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLocation (50,50);
        setResizable( true );
        setBackground( Color.WHITE );

        setTitle( titulo );
		setSize ( ancho, alto);        
    }

    /**
     * Método para crear el menú de la aplicación con base em el objeto JSON leído
     * Genera una barra de menú y los menús con sus respectivas opciones
     * @param jsonMenu - Arreglo Json con los menùs deseados
     */
    private void crearMenu(  JsonArray jsonMenu )
    {    	
    	// Creación de la barra de menús
        menuBar = new JMenuBar();       
        for (JsonElement men : jsonMenu)
        {
        	// Creación de cada uno de los menús
        	JsonObject jom = men.getAsJsonObject(); 

        	String menuTitle = jom.get("menuTitle").getAsString();        	
        	JsonArray opciones = jom.getAsJsonArray("options");
        	
        	JMenu menu = new JMenu( menuTitle);
        	
        	for (JsonElement op : opciones)
        	{       	
        		// Creación de cada una de las opciones del menú
        		JsonObject jo = op.getAsJsonObject(); 
        		String lb =   jo.get("label").getAsString();
        		String event = jo.get("event").getAsString();
        		
        		JMenuItem mItem = new JMenuItem( lb );
        		mItem.addActionListener( this );
        		mItem.setActionCommand(event);
        		
        		menu.add(mItem);
        	}       
        	menuBar.add( menu );
        }        
        setJMenuBar ( menuBar );	
    }
    
	/* ****************************************************************
	 * 			Metodos Operador
	 *****************************************************************/
	
	/**
	 * Adiciona un operador con la información dada por el usuario
	 * Se crea una nueva tupla de operador en la base de datos, si un operador con ese nombre no existía
	 */
    public void adicionarOperador( )
    {
    	try 
    	{	
			String vinculo = capitalizeWords(JOptionPane.showInputDialog (this, "¿Cual es su vinculo con Alohandes?", "Adicionar operador", JOptionPane.QUESTION_MESSAGE));
			

			if (vinculo != null && vinculos.contains(vinculo)){

				String nombre = capitalizeWords(JOptionPane.showInputDialog (this, "Nombre del operador?", "Adicionar operador", JOptionPane.QUESTION_MESSAGE));
				String tipo = capitalizeWords(JOptionPane.showInputDialog (this, "Tipo del operador?", "Adicionar operador", JOptionPane.QUESTION_MESSAGE));
				if (nombre != null  && tipo != null)
				{
					VOOperador op = alohandes.adicionarOperador(nombre, tipo);
					if (op == null)
					{
						throw new Exception ("No se pudo crear un operador con nombre: " + nombre + " y tipo " + tipo);
					}
					String resultado = "En adicionarOperador\n\n";
					resultado += "Operador adicionado exitosamente: " + op;
					resultado += "\n Operación terminada";
					panelDatos.actualizarInterfaz(resultado);
				}
				else
				{
					panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
				}
				
			}else{
				throw new Exception ("El vinculo: " + vinculo + " no es valido");
			}
			

		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }

    /* ****************************************************************
	 * 			Metodos Oferta
	 *****************************************************************/

	/**
	 * Adiciona una oferta con la información dada por el usuario
	 * Se crea una nueva tupla de oferta en la base de datos, si una oferta con ese id no existía
	 */
	 public void adicionarOferta( )
	 {
		 try 
		 {	

			 String idString= JOptionPane.showInputDialog (this, "¿Cual es su id de operador?", "Adicionar operador", JOptionPane.QUESTION_MESSAGE);
			
			 
 
			 if (idString != null){

				long idOperador = Long.parseLong(idString);
				if (Boolean.TRUE.equals(validarOperador(idOperador))){

				 	String tipo = capitalizeWords(JOptionPane.showInputDialog (this, "Tipo de alojamiento que va a ofertar?\n Puede ser: Habitacion, Apartamento o Vivienda Personal", "Adicionar operador", JOptionPane.QUESTION_MESSAGE));
					String servicios = capitalizeWords(JOptionPane.showInputDialog (this, "Ingrese el id los servicios del alojamiento separados por coma", "Adicionar operador", JOptionPane.QUESTION_MESSAGE));
					
				 	if (tipo != null && servicios != null)
				 	{
						VOAlojamiento alojamiento = alohandes.adicionarAlojamiento(idOperador, tipo);
						List<Integer> listaServicios = parseListInt(servicios);
						asociarServicios(listaServicios, alojamiento.getId());
						VOAlojamiento alojamientoCompleto = alohandes.darAlojamientoCompleto(alojamiento.getId());
						
						
						if (alojamiento == null)
						{
							throw new Exception ("No se pudo crear un alojamiento para el tipo: " + tipo);
						}
						String costoString= JOptionPane.showInputDialog (this, "¿Cual va ser el costo de la oferta?", "Adicionar operador", JOptionPane.QUESTION_MESSAGE);
						String tiempoString= JOptionPane.showInputDialog (this, "¿Cual va ser el tiempo limite en dias?", "Adicionar operador", JOptionPane.QUESTION_MESSAGE);

						Integer costo = Integer.parseInt(costoString);
						Integer tiempo = Integer.parseInt(tiempoString);

						VOOferta oferta = alohandes.adicionarOferta(alojamiento.getId(), idOperador, fechaActual(), costo , tiempo);
						
						if (oferta == null)
						{
							throw new Exception ("No se pudo crear una oferta para el alojamiento con id: " + alojamiento.getId());
						}
						String resultado = "En adicionarOferta\n\n";
						resultado += "Oferta adicionada exitosamente: " + oferta;
						resultado += "\n Operación terminada";
						panelDatos.actualizarInterfaz(resultado);
				 	}
				 	else
				 	{
					 	panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
				 	}

				}else throw new Exception ("Usted no figura como operador de Alohandes");
				
				 
			 }else throw new Exception ("El id: " + idString + " no es valido");
			 
		 } 
		 catch (Exception e) 
		 {
 			e.printStackTrace();
			 String resultado = generarMensajeError(e);
			 panelDatos.actualizarInterfaz(resultado);
		 }
	 }

	 /**
	  * Retira una oferta con la información dada por el usuario
	  * Se elimina una tupla de oferta en la base de datos, si una oferta con ese id existía
	  */
	 public void retirarOferta( )
	 {
		 try 
		 {	

			 String idString= JOptionPane.showInputDialog (this, "¿Cual es su id de operador?", "Retirar oferta", JOptionPane.QUESTION_MESSAGE);
			
			 
			 if (idString != null){

				long idOperador = Long.parseLong(idString);
				if (Boolean.TRUE.equals(validarOperador(idOperador))){

				 	Long idOferta = Long.parseLong(JOptionPane.showInputDialog (this, "Ingrese el id de la oferta a retirar", "Retirar oferta", JOptionPane.QUESTION_MESSAGE));
					
				 	if (idOferta != null)
				 	{
						Long xd = alohandes.eliminarResidenOf(idOferta);
						long resp = alohandes.eliminarOfertaPorId(idOferta);

						if (resp == 0 && xd == 0)
						{
							throw new Exception ("No se pudo retirar la oferta con id: " + idOferta);
						}
						String resultado = "En retirarOferta\n\n";
						resultado += "Oferta rertirada exitosamente";
						resultado += "\n Operación terminada";
						panelDatos.actualizarInterfaz(resultado);
				 	}
				 	else
				 	{
					 	panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
				 	}

				}else throw new Exception ("Usted no figura como operador de Alohandes");
				
				 
			 }else throw new Exception ("El id: " + idString + " no es valido");
			 
		 } 
		 catch (Exception e) 
		 {
 			e.printStackTrace();
			 String resultado = generarMensajeError(e);
			 panelDatos.actualizarInterfaz(resultado);
		 }
	 }

	 /**
	  * Deshabilita una oferta con la información dada por el usuario
	  */
	 public void desahabilitarOferta(){

		try 
		{	

			String idString= JOptionPane.showInputDialog (this, "¿Cual es su id de operador?", "Retirar oferta", JOptionPane.QUESTION_MESSAGE);
		   
			
			if (idString != null){

			   long idOperador = Long.parseLong(idString);
			   if (Boolean.TRUE.equals(validarOperador(idOperador))){

					List<Oferta> ofertas = alohandes.darOfertas();
					List<Oferta> ofertasDelOperador = new ArrayList<Oferta>();
					for (Oferta oferta : ofertas) {
						if (oferta.getIdOperador() == idOperador){
							ofertasDelOperador.add(oferta);
						}
					}

					List<Oferta> ofertasLibres = new ArrayList<Oferta>();
					for (Oferta oferta : ofertasDelOperador) {
						if (oferta.getReservada() == 0){
							ofertasLibres.add(oferta);
						}
					}

					List<Oferta> ofertasOcupadas = new ArrayList<Oferta>();
					for (Oferta oferta : ofertasDelOperador) {
						if (oferta.getReservada() == 1){
							ofertasOcupadas.add(oferta);
						}
					}

					if (ofertasDelOperador.size() == 0){
						throw new Exception ("No tiene ofertas");
					}	

					List<Long> idsOfOcp = new ArrayList<Long>();
					String labelOferta = "Ofertas ocupadas: \n";
					for (int i = 0; i < ofertasOcupadas.size() ; i++) {
						idsOfOcp.add(ofertasOcupadas.get(i).getId());
						labelOferta+= "\nIdentificador: " + ofertasOcupadas.get(i).getId();
						labelOferta+= "\nFecha: " + ofertasOcupadas.get(i).getFecha();
						labelOferta+= "\nAlojamiento: " + alohandes.darAlojamientoPorId(ofertasOcupadas.get(i).getIdAlojamiento()).getTipo();
						labelOferta+= "\nReservas asociadas: " ;
						for (Residen residen: alohandes.darResidenPorIdOferta(ofertasOcupadas.get(i).getId())){
							labelOferta+=  "\n+" + residen.getIdReserva();
						}
						labelOferta+= "\n------------------------------------------";
					}
				
				   JTextArea textArea = new JTextArea(20, 30); // create a JTextArea component
        		   JScrollPane scrollPane = new JScrollPane(textArea); // add the JTextArea to a JScrollPane
                   textArea.setText(labelOferta);
				   JOptionPane.showMessageDialog(null, scrollPane, "Ofertas actuales", JOptionPane.PLAIN_MESSAGE); // create a JOptionPane with the JScrollPane as the message

				   List<Long> idsSeleccionados = createCheckBoxList(idsOfOcp);

				   
				  if (ofertasLibres.isEmpty()){
						String labelOferta2= "\nUsted no cuenta con ofertas libres para porder trasladar ofertas vigentes. \n";
						labelOferta2 += "\nLas siguientes ofertas no pudieron ser reemplazadas: \n";
						for (Long id: idsSeleccionados){
							labelOferta2+= "\nOferta con id: " + id;
							labelOferta2+= "\nReservas asociadas que fueron retiradas: " ;
							for (Residen residen: alohandes.darResidenPorIdOferta(id)){
								alohandes.retirarReservaPorId(residen.getIdReserva());
								alohandes.eliminarResidenPorId(residen.getIdReserva(), id);
								labelOferta2+= "\nReserva con id: " + residen.getIdReserva();
							}
							alohandes.actualizarOferta(id, 0);
							alohandes.agregarDesahabilitacion(id);
							
						}
					  panelDatos.actualizarInterfaz(labelOferta2);

				  } else{

				   List<Long> idsOfLib = new ArrayList<Long>();
				   String labelOferta1 = "Ofertas libres: \n";
				   for (int i = 0 ; i < ofertasLibres.size() ; i++) {
					   idsOfLib.add(ofertasLibres.get(i).getId());
					   labelOferta1+= "\nIdentificador: " + ofertasLibres.get(i).getId();
					   labelOferta1+= "\nFecha: " + ofertasLibres.get(i).getFecha();
					   labelOferta1+= "\nAlojamiento: " + alohandes.darAlojamientoPorId(ofertasLibres.get(i).getIdAlojamiento()).getTipo();
					   labelOferta1+= "\n------------------------------------------";
				   }
			   
				  JTextArea textArea1 = new JTextArea(20, 30); // create a JTextArea component
				  JScrollPane scrollPane1 = new JScrollPane(textArea1); // add the JTextArea to a JScrollPane
				  textArea1.setText(labelOferta1);
				  JOptionPane.showMessageDialog(null, scrollPane1, "Ofertas actuales", JOptionPane.PLAIN_MESSAGE); // create a JOptionPane with the JScrollPane as the message

					List<Long> idsLibres = createCheckBoxList(idsOfLib);

					String labelOferta2 = "Ofertas a reemplazar: \n";
					for (int i = 0; i < idsSeleccionados.size(); i++){
						for (Residen residen: alohandes.darResidenPorIdOferta(idsSeleccionados.get(i))){
							alohandes.adicionarResiden(residen.getIdReserva(), idsLibres.get(0));
							alohandes.eliminarResidenPorId(residen.getIdReserva(), idsSeleccionados.get(i));
							labelOferta2+= "\nReserva de la oferta " + idsSeleccionados.get(i) + " movida a la oferta " + idsLibres.get(0) + "\n";
							labelOferta2+= "\nLa oferta con id: " + idsSeleccionados.get(i) + " ha sido deshabilitada. \n";
						}  
						alohandes.desagregarReservaColectiva(idsSeleccionados.get(i));
						alohandes.actualizarOferta(idsSeleccionados.get(i), 0);
						alohandes.actualizarOferta(idsLibres.get(i), 1);
						alohandes.agregarDesahabilitacion(idsSeleccionados.get(i));
						idsSeleccionados.remove(i);
					}
					if (!idsSeleccionados.isEmpty()){
						labelOferta2+= "\nLas siguientes ofertas no pudieron ser reemplazadas: \n";
						for (Long id: idsSeleccionados){
							labelOferta2+= "\nOferta con id: " + id;
							labelOferta2+= "\nReservas asociadas que fueron retiradas: " ;
							for (Residen residen: alohandes.darResidenPorIdOferta(id)){
								alohandes.retirarReservaPorId(residen.getIdReserva());
								labelOferta2+= "\nReserva con id: " + residen.getIdReserva();
							}
							alohandes.agregarDesahabilitacion(id);
						}
						
					}
					panelDatos.actualizarInterfaz(labelOferta2);
				  }

				  
			   }else throw new Exception ("Usted no figura como operador de Alohandes");
			   
				
			}else throw new Exception ("El id: " + idString + " no es valido");
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
		
	 }

	 /**
	  * Metodo que se encarga de retirar una oferta de alojamiento
	  */
	 public void rehabilitarOferta(){
		try 
		{	

			String idString= JOptionPane.showInputDialog (this, "¿Cual es su id de operador?", "Retirar oferta", JOptionPane.QUESTION_MESSAGE);
		   
			
			if (idString != null){

			   long idOperador = Long.parseLong(idString);
			   if (Boolean.TRUE.equals(validarOperador(idOperador))){

				List<Oferta> ofertasDes = alohandes.darOfertasDesahabilitadasPorOp(idOperador);
				List<Long> idsOfDes = new ArrayList<Long>();
				String labelOferta = "";
				for (int i = 0; i < ofertasDes.size() ; i++) {
					idsOfDes.add(ofertasDes.get(i).getId());
					labelOferta+= "\nIdentificador: " + ofertasDes.get(i).getId();
					labelOferta+= "\nFecha: " + ofertasDes.get(i).getFecha();
					labelOferta+= "\nAlojamiento: " + alohandes.darAlojamientoPorId(ofertasDes.get(i).getIdAlojamiento()).getTipo();
					labelOferta+= "\n------------------------------------------";
				}
			
			   JTextArea textArea = new JTextArea(20, 30); // create a JTextArea component
			   JScrollPane scrollPane = new JScrollPane(textArea); // add the JTextArea to a JScrollPane
			   textArea.setText(labelOferta);
			   JOptionPane.showMessageDialog(null, scrollPane, "Ofertas deshabilitadas", JOptionPane.PLAIN_MESSAGE); // create a JOptionPane with the JScrollPane as the message

			   List<Long> idsSeleccionados = createCheckBoxList(idsOfDes);
			
			   String labelOferta2 = "Ofertas rehabilitadas: \n";
			   for (int i = 0; i < idsSeleccionados.size(); i++) {
				   alohandes.eliminarDesahabilitacion(idsSeleccionados.get(i));
				   labelOferta2 += "\nOferta con id: " + idsSeleccionados.get(i) + " rehabilitada. \n";
			
			   }
			   labelOferta2 += "\nEstas ofertas son susceptibles nuevamente de ser reservadas. \n";
			   panelDatos.actualizarInterfaz(labelOferta2);

			   }else throw new Exception ("Usted no figura como operador de Alohandes");
			   
				
			}else throw new Exception ("El id: " + idString + " no es valido");
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
			
		

	 }

	/* ****************************************************************
	 * 			Metodos Residente
	 *****************************************************************/

	 /**
	  * Adiciona un residente con la información dada por el usuario
	  * Se crea una nueva tupla de residente en la base de datos, si un residente con ese nombre no existía
	  */
	 public void adicionarResidente( )
	 {
		 try 
		 {	
			 String vinculo = capitalizeWords(JOptionPane.showInputDialog (this, "¿Cual es su vinculo con Alohandes?", "Adicionar residente", JOptionPane.QUESTION_MESSAGE));
			 
 
			 if (vinculo != null && vinculos.contains(vinculo)){
 
				 String nombre = capitalizeWords(JOptionPane.showInputDialog (this, "Nombre del residente?", "Adicionar residente", JOptionPane.QUESTION_MESSAGE));
				 String tipo = capitalizeWords(JOptionPane.showInputDialog (this, "Vinculo del residente?", "Adicionar residetne", JOptionPane.QUESTION_MESSAGE));
				 if (nombre != null  && tipo != null)
				 {
					 VOResidente op = alohandes.adicionarResidente(nombre, tipo);
					 if (op == null)
					 {
						 throw new Exception ("No se pudo adicionar el residente con nombre: " + nombre + " y vinculo " + tipo);
					 }
					 String resultado = "En adicionarResidente\n\n";
					 resultado += "Residente adicionado exitosamente: " + op;
					 resultado += "\n Operación terminada";
					 panelDatos.actualizarInterfaz(resultado);
				 }
				 else
				 {
					 panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
				 }
				 
			 }else{
				 throw new Exception ("El vinculo: " + vinculo + " no es valido");
			 }
			 
 
		 } 
		 catch (Exception e) 
		 {
 //			e.printStackTrace();
			 String resultado = generarMensajeError(e);
			 panelDatos.actualizarInterfaz(resultado);
		 }
	 }

	/* ****************************************************************
	 * 			Metodos Reserva
	 *****************************************************************/

	 /**
	  * Adiciona una reserva con la información dada por el usuario
	  * Se crea una nueva tupla de reserva en la base de datos, si una reserva con ese id no existía
	  */
	 public void adicionarReserva( )
	 {
		 try 
		 {	

			 String idString= JOptionPane.showInputDialog (this, "¿Cual es su id de residente?", "Adicionar reserva", JOptionPane.QUESTION_MESSAGE);
			
			 
 
			 if (idString != null){

				long idResidente = Long.parseLong(idString);
				if (Boolean.TRUE.equals(validarResidente(idResidente))){

					List<Oferta> ofertasDisponibles = ofertasDisponibles();

					String labelOferta = "Las ofertas disponibles son: \n";
					for (Oferta oferta: ofertasDisponibles){
						labelOferta+= "\nId: " + oferta.getId();
						labelOferta+= " Operador: " + alohandes.darOperadorPorId(oferta.getIdOperador()).getNombre();
						labelOferta+= " Alojamiento: " + alohandes.darAlojamientoPorId(oferta.getIdAlojamiento()).getTipo();
						
					}
				
					JOptionPane.showMessageDialog(rootPane, labelOferta);

					String idOfertaString= JOptionPane.showInputDialog (this, "Ingrese el id de la oferta: ", "Adicionar operador", JOptionPane.QUESTION_MESSAGE);
					String tiempoString= JOptionPane.showInputDialog (this, "¿Cual va ser el tiempo en dias?", "Adicionar operador", JOptionPane.QUESTION_MESSAGE);
					Integer tiempo = Integer.parseInt(tiempoString);

					


				 	if (tiempo != null && idOfertaString != null)
				 	{
						long idOferta = Long.parseLong(idOfertaString);
						
						VOReserva reserva = alohandes.adicionarReserva(idResidente, tiempo, fechaActual());
					
						if (reserva == null)
						{
							throw new Exception ("No se pudo crear una reserva para el residente con id: " + idResidente);
						}

						VOResiden residen = alohandes.adicionarResiden(reserva.getId(), idOferta);
						String resultado = "En adicionarReserva\n\n";
						resultado += "Reserva adicionada exitosamente: " + reserva;
						resultado += "\n Operación terminada";
						panelDatos.actualizarInterfaz(resultado);
				 	}
				 	else
				 	{
					 	panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
				 	}

				}else throw new Exception ("Usted no figura como operador de Alohandes");
				
				 
			 }else throw new Exception ("El id: " + idString + " no es valido");
			 
		 } 
		 catch (Exception e) 
		 {
 			e.printStackTrace();
			 String resultado = generarMensajeError(e);
			 panelDatos.actualizarInterfaz(resultado);
		 }
	 }

	 /**
	  * Elimina una reserva con la información dada por el usuario
	  * Se crea una nueva tupla de reserva en la base de datos, si una reserva con ese id no existía
	  */
	 public void retirarReserva( )
	 {
		 try 
		 {	

			 String idString= JOptionPane.showInputDialog (this, "¿Cual es su id de residente?", "Retirar reserva", JOptionPane.QUESTION_MESSAGE);
			
			 
 
			 if (idString != null){

				long idResidente = Long.parseLong(idString);
				if (Boolean.TRUE.equals(validarResidente(idResidente))){

				
					String idReservaString= JOptionPane.showInputDialog (this, "Ingrese el id de la reserva: ", "Retirar reserva", JOptionPane.QUESTION_MESSAGE);
					
					long idReserva = Long.parseLong(idReservaString);

					VOReserva reserva = alohandes.darReservaPorId(idReserva);
					panelDatos.actualizarInterfaz(""+reserva);
				
				 	if (reserva != null && reserva.getIdResidente() == idResidente)
				 	{
						Double penalizacion = alohandes.calcularPenalidad(idReserva);
						Long eliminados = alohandes.eliminarResiden(idReserva);
						Reserva retirada = alohandes.retirarReservaPorId(idReserva);
						String estado = "";
						if (retirada.getActivo() == 1){
							estado = "Activa";
						}else{
							estado = "Inactiva";
						}
						
						String resultado = "En RetirarReserva\n\n";
						resultado += "Reserva con id: " + idReserva + " con nuevo estado: " + estado;
						resultado += "\n Penalizacion: " + penalizacion + " pesos";
						resultado += "\n Operación terminada";
						panelDatos.actualizarInterfaz(resultado);
				 	}
				 	else
				 	{
					 	panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
				 	}

				}else throw new Exception ("Usted no figura como operador de Alohandes");
				
				 
			 }else throw new Exception ("El id: " + idString + " no es valido");
			 
		 } 
		 catch (Exception e) 
		 {
 			e.printStackTrace();
			 String resultado = generarMensajeError(e);
			 panelDatos.actualizarInterfaz(resultado);
		 }
	 }

	 /**
	  * Adiciona una reserva colectiva con la información dada por el usuario
	  */
	public void adicionarReservaColectiva () {

		try 
		{	

			String idString= JOptionPane.showInputDialog (this, "¿Cual es su id de residente?", "Adicionar reserva colectiva", JOptionPane.QUESTION_MESSAGE);
		   
			if (idString != null){

			   long idResidente = Long.parseLong(idString);
			   if (Boolean.TRUE.equals(validarResidente(idResidente))){

				   String numRersevasStr= JOptionPane.showInputDialog (this, "¿Cuantos alojamientos necesita?", "Adicionar reserva colectiva", JOptionPane.QUESTION_MESSAGE);
				   int numReservas = Integer.parseInt(numRersevasStr);

				   if (numReservas <= 1){
					   throw new Exception ("Una reserva colectiva debe tener mas de un alojamiento");
				   }
				  

				   JPanel panel = new JPanel();
				   JCheckBox checkBox1 = new JCheckBox("Habitacion");
				   JCheckBox checkBox2 = new JCheckBox("Vivienda Personal");
				   JCheckBox checkBox3 = new JCheckBox("Apartamento");	
				   panel.setLayout(new GridLayout(0, 1));
				   panel.add(checkBox1);
				   panel.add(checkBox2);
				   panel.add(checkBox3);
				   int result = JOptionPane.showConfirmDialog(null, panel, "¿Que tipo de alojamiento desea?",
						   JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				   String tipoAlojamiento = "";
				   if (result == JOptionPane.OK_OPTION) {
					   if (checkBox1.isSelected()) {
						   tipoAlojamiento = "Habitacion";
					   }
					   if (checkBox2.isSelected()) {
						   tipoAlojamiento = "Vivienda Personal";
					   }
					   if (checkBox3.isSelected()) {
						   tipoAlojamiento = "Apartamento";
					   }
				   }

				   JPanel panel2 = new JPanel();
				   JCheckBox checkBox4 = new JCheckBox("Suite");
				   JCheckBox checkBox5 = new JCheckBox("Semisuite");
				   JCheckBox checkBox6 = new JCheckBox("Estandar");
				   JCheckBox checkBox7 = new JCheckBox("Compartida");
				   JCheckBox checkBox8 = new JCheckBox("Individual");
				   panel2.setLayout(new GridLayout(0, 1));
				   panel2.add(checkBox4);
				   panel2.add(checkBox5);
				   panel2.add(checkBox6);
				   panel2.add(checkBox7);
				   panel2.add(checkBox8);
				   int result1 = JOptionPane.showConfirmDialog(null, panel2, "¿Que categoria de alojamiento desea?",
						   JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				   String categoria = "";
				   if (result1 == JOptionPane.OK_OPTION) {
					   if (checkBox4.isSelected()) {
						categoria = "Suite";
					   }
					   if (checkBox5.isSelected()) {
						categoria = "Semisuite";
					   }
					   if (checkBox6.isSelected()) {
						categoria = "Estandar";
					   }
					   if (checkBox7.isSelected()) {
						categoria = "Compartida";
					   }
					   if (checkBox8.isSelected()) {
						categoria = "Individual";
					   }
				   }

				   List<String> servicios = serviciosSeleccionados();
				   
				   
				   List<VOOferta> ofertasDisponibles = alohandes.darVOOfertasDisponibles();

				   List<VOOferta> ofertasSeleccionadas = new ArrayList<VOOferta>();
				   for (VOOferta oferta: ofertasDisponibles){
					   if (tipoAlojamiento.equals(alohandes.darAlojamientoCompleto(oferta.getIdAlojamiento()).getTipo())){
						   if (categoria.equals(alohandes.darDetalleAlojPorId(oferta.getIdAlojamiento()).getCategoria())){
							   List<String> serviciosOferta = alohandes.darAlojamientoCompleto(oferta.getIdAlojamiento()).getNombreServicios();
							   if (serviciosOferta.containsAll(servicios)){
								   ofertasSeleccionadas.add(oferta);
							   }
						   }
					   }
				   }

				   if (ofertasDisponibles.isEmpty()){
					   throw new Exception ("No hay ofertas disponibles");
				   }else if (ofertasSeleccionadas.size() < numReservas){
					   throw new Exception ("No hay suficientes ofertas disponibles");
				   }

				   String labelOferta = "";
				   List<Long> idOfertas = new ArrayList<Long>();
				   for (VOOferta oferta: ofertasSeleccionadas){
					   idOfertas.add(oferta.getId());
					   labelOferta+= "\nId: " + oferta.getId();
					   labelOferta+= "\nOperador: " + alohandes.darOperadorPorId(oferta.getIdOperador()).getNombre();
					   labelOferta+= "\nTipo de alojamiento: " + alohandes.darAlojamientoCompleto(oferta.getIdAlojamiento()).getTipo();
					   labelOferta+= "\nCapacidad: " + alohandes.darDetalleAlojPorId(oferta.getIdAlojamiento()).getCapacidad();
					   labelOferta+= "\nCategoria: " + alohandes.darDetalleAlojPorId(oferta.getIdAlojamiento()).getCategoria();
					   labelOferta+= "\nServicios: ";
					   int count = 1;
					   for (Servicio servicio: alohandes.darAlojamientoCompleto(oferta.getIdAlojamiento()).getServicios()){
						   labelOferta+= "\n" + count + ". " + servicio.getNombre();
						   count++;
					   }
					   labelOferta+= "\n-----------------------------------------------------------";
					   
				   }

				   JTextArea textArea = new JTextArea(20, 60); // create a JTextArea component
        		   JScrollPane scrollPane = new JScrollPane(textArea); // add the JTextArea to a JScrollPane
                   textArea.setText(labelOferta);
                   JOptionPane.showMessageDialog(null, scrollPane, "Ofertas disponibles", JOptionPane.PLAIN_MESSAGE); // create a JOptionPane with the JScrollPane as the message
 
				   if (numReservas < idOfertas.size()){
						Boolean correcto = false;
						while (!correcto){
					   		idOfertas = createCheckBoxList(idOfertas);
							if (idOfertas.size() == numReservas){
								correcto = true;
					   		}
						}
					}

				   String tiempoString= JOptionPane.showInputDialog (this, "¿Cual va ser el tiempo en dias?", "Adicionar operador", JOptionPane.QUESTION_MESSAGE);
				   Integer tiempo = Integer.parseInt(tiempoString);

					List<Reserva> reservas = alohandes.adicionarReservaColectiva(idResidente, idOfertas, tiempo);
				   
					if (reservas == null)
					{
						throw new Exception ("No se pudo crear una reserva para el residente con id: " + idResidente);
					}

					
					String resultado = "En adicionarReserva\n\n";
					resultado += "Adicionadas exitosamente " + reservas.size() + " reservas con id:\n";
					for (int i = 0; i < reservas.size(); i++)
					{
						resultado += "+ " + reservas.get(i).getId() + " \n";
					}
					resultado += "\n Operación terminada";
					panelDatos.actualizarInterfaz(resultado);
					

			   }else throw new Exception ("Usted no figura como operador de Alohandes");
			   
				
			}else throw new Exception ("El id: " + idString + " no es valido");
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}

	}

	/**
	 * Retira una reserva colectiva
	 */
	public void retirarReservaColectiva(){
		try 
		{
			String idString = JOptionPane.showInputDialog (this, "Ingrese su id de residente", "Retirar reserva colectiva", JOptionPane.QUESTION_MESSAGE);

		
			if (idString != null){

				long idResidente = Long.parseLong(idString);
				
				if (Boolean.TRUE.equals(validarResidente(idResidente))){

					List<Long> idColectivas = alohandes.darReservasIdColectivas(idResidente);
					if (idColectivas.isEmpty()){
						throw new Exception ("El residente con id: " + idResidente + " no tiene reservas colectivas");
					}

					List<Long> idColectivasRetirar = createCheckBoxList(idColectivas);
					List<Reserva> reservasIdv = alohandes.darReservasColectivas(idColectivasRetirar.get(0));
					String resultado = "En RetirarReserva\n";
					double penalizacion = 0;
					for (Reserva reserva: reservasIdv){
						long idReserva = reserva.getId();
						alohandes.actualizarReservaColectiva(idColectivasRetirar.get(0));
						penalizacion += alohandes.calcularPenalidad(idReserva);
						Reserva retirada = alohandes.retirarReservaPorId(idReserva);
						String estado = "";
						if (retirada.getActivo() == 1){
							estado = "Activa";
						}else{
							estado = "Inactiva";
						}
					
						resultado += "\nReserva con id: " + idReserva + " con nuevo estado: " + estado;
	
					}
					resultado += "\n Penalizacion total: " + penalizacion + " pesos";
					resultado += "\n Operación terminada";
					panelDatos.actualizarInterfaz(resultado);
					

					

				}else throw new Exception ("Usted no figura como operador de Alohandes");
			}
			

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}

	}
	
	
	/* ****************************************************************
	 * 			Metodos Consulta
	 *****************************************************************/

	 /**
	  * Lista las ganancias de los operadores
	  */
	public void listarGananciasOperadores( )
	 {
		 try 
		 {	

						
			List<Object []> ganancias = alohandes.RFC1();

			if (ganancias == null)
			{
				throw new Exception ("Completar la consulta");
			}
			String resultado = "En listarGananciasOperadores\n\n";
			for (Object [] ganancia: ganancias){
				
				resultado += "Operador con id : " + ganancia[0] + "\n";
				resultado += "Año actual: " + ganancia[1] + "\n";
				resultado += "Año corrido: " + ganancia[2] + "\n";
				resultado += "\n";
				panelDatos.actualizarInterfaz(resultado);
			}
			
		
			 
		 } 
		 catch (Exception e) 
		 {
			e.printStackTrace();
			 String resultado = generarMensajeError(e);
			 panelDatos.actualizarInterfaz(resultado);
		 }
	 }


	/**
	 * Lista los alojamientos que complen con un rqeuisto dado por el usuario
	 */
	public void listarAlohamientos( )
	{
		try 
		{	
			String fechaInicialString= JOptionPane.showInputDialog (this, "Indique la fecha incial separada por coma", "Listar alohamientos", JOptionPane.QUESTION_MESSAGE);
			List<Integer> resp1 = parseListInt(fechaInicialString);

			LocalDate myDate = LocalDate.of(resp1.get(0), resp1.get(1), resp1.get(2)); 
			Timestamp fechaInicial = Timestamp.valueOf(myDate.atStartOfDay());

			panelDatos.actualizarInterfaz("Fecha Inicial" + myDate);

			String fechaFinalString= JOptionPane.showInputDialog (this, "Indique la fecha final separada por coma", "Listar alohamientos", JOptionPane.QUESTION_MESSAGE);
			List<Integer> resp2 = parseListInt(fechaFinalString);

			LocalDate myDate2 = LocalDate.of(resp2.get(0), resp2.get(1), resp2.get(2)); 
			Timestamp fechaFinal = Timestamp.valueOf(myDate2.atStartOfDay());

			panelDatos.actualizarInterfaz("Fecha Final" + myDate2);

			String servicios = JOptionPane.showInputDialog (this, "Ingrese los servicios del alojamiento separados por coma", "Adicionar operador", JOptionPane.QUESTION_MESSAGE);
			List<String> listaServicios = parseList(servicios);
				   
			List<Alojamiento> alojamientos = alohandes.RFC4(fechaInicial, fechaFinal, listaServicios);

		  
		   String resultado = "En listar alohanmeintos\n\n";
		   for (Alojamiento alojamiento: alojamientos){
			   
			   resultado += "Alojamiento con identificador : " + alojamiento.getId() + "\n";
			   VOOperador operador = alohandes.darOperadorPorId(alojamiento.getIdOperador());
			   resultado += "Operador : " + operador.getNombre() + "\n";
			   resultado += "Servicios: \n";
			   for (Servicio servicio: alojamiento.getServicios()){
				resultado += "+ " + servicio.getNombre() + "\n";
			   }
			   resultado += "\n";
			   panelDatos.actualizarInterfaz(resultado);
		   }
		   
	   
			
		} 
		catch (Exception e) 
		{
		   e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Lista los 20 ofertas mas populares
	 */
	public void listar20OfertasMasPopulares (){

		try 
		{	

					   
			List<Long> populares = alohandes.RFC2();

			String resultado = "En listar20OfertasMasPopulares\n\n";
			for (Long id: populares){
				
				resultado += "Oferta con id : " + id + "\n";
				resultado += "\n";
				panelDatos.actualizarInterfaz(resultado);
			}
	   
			
		} 
		catch (Exception e) 
		{
		   e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}

	}

	/**
	 * Lista el indice de ocupacion de las ofertas
	 */
	public void mostrarIndiceOcupacion (){

		try 
		{	

					   
			List<Oferta> ofertas = alohandes.darOfertas();

			List<Oferta> ofertas2 = alohandes.RFC3(ofertas);

			String resultado = "En darIndiceDeOcupacion\n\n";
			for (VOOferta oferta: ofertas2){

				resultado += "Oferta con id : " + oferta.getId() + " con indice de opcupacion de: "+ oferta.getIndiceOcupacion() + "\n";
				resultado += "\n";
				panelDatos.actualizarInterfaz(resultado);
				
			}

			
	   
			
		} 
		catch (Exception e) 
		{
		   e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}

	}

    /**
     * Genera una cadena de caracteres con la descripción de la excepcion e, haciendo énfasis en las excepcionsde JDO
     * @param e - La excepción recibida
     * @return La descripción de la excepción, cuando es javax.jdo.JDODataStoreException, "" de lo contrario
     */
	private String darDetalleException(Exception e) 
	{
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException"))
		{
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions() [0].getMessage();
		}
		return resp;
	}

	/**
	 * Genera una cadena para indicar al usuario que hubo un error en la aplicación
	 * @param e - La excepción generada
	 * @return La cadena con la información de la excepción y detalles adicionales
	 */
	private String generarMensajeError(Exception e) 
	{
		String resultado = "\n";
		resultado += e.getLocalizedMessage() + ", " + darDetalleException(e);
		resultado += "\n\nReintente la operacion y verifique los datos";
		return resultado;
	}

	/**
	 * Limpia el contenido de un archivo dado su nombre
	 * @param nombreArchivo - El nombre del archivo que se quiere borrar
	 * @return true si se pudo limpiar
	 */
	private boolean limpiarArchivo(String nombreArchivo) 
	{
		BufferedWriter bw;
		try 
		{
			bw = new BufferedWriter(new FileWriter(new File (nombreArchivo)));
			bw.write ("");
			bw.close ();
			return true;
		} 
		catch (IOException e) 
		{
//			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Abre el archivo dado como parámetro con la aplicación por defecto del sistema
	 * @param nombreArchivo - El nombre del archivo que se quiere mostrar
	 */
	private void mostrarArchivo (String nombreArchivo)
	{
		try
		{
			Desktop.getDesktop().open(new File(nombreArchivo));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* ****************************************************************
	 * 			Métodos de la Interacción
	 *****************************************************************/
    /**
     * Método para la ejecución de los eventos que enlazan el menú con los métodos de negocio
     * Invoca al método correspondiente según el evento recibido
     * @param pEvento - El evento del usuario
     */
    @Override
	public void actionPerformed(ActionEvent pEvento)
	{
		String evento = pEvento.getActionCommand( );		
        try 
        {
			Method req = InterfazParranderosApp.class.getMethod ( evento );			
			req.invoke ( this );
		} 
        catch (Exception e) 
        {
			e.printStackTrace();
		} 
	}
    
	/* ****************************************************************
	 * 			Programa principal
	 *****************************************************************/
    /**
     * Este método ejecuta la aplicación, creando una nueva interfaz
     * @param args Arreglo de argumentos que se recibe por línea de comandos
     */
    public static void main( String[] args )
    {
        try
        {
        	
            // Unifica la interfaz para Mac y para Windows.
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );
            InterfazParranderosApp interfaz = new InterfazParranderosApp( );
            interfaz.setVisible( true );
        }
        catch( Exception e )
        {
            e.printStackTrace( );
        }
    }

	public String capitalizeWords(String inputString) 
	{
		String[] words = inputString.split(" ");
		StringBuilder sb = new StringBuilder();
		for (String word : words) {
			sb.append(word.substring(0, 1).toUpperCase())
			  .append(word.substring(1))
			  .append(" ");
		}
		return sb.toString().trim();
	}

	public Boolean validarOperador (long idOperador) {
		List<VOOperador> listaOperadores = alohandes.darVOOperadores();
		Boolean esta = false;

		for (VOOperador op : listaOperadores){
			if (idOperador == op.getId()) esta = true;
		}

		return esta;
	}

	public Boolean validarResidente (long idResidente) {
		List<VOResidente> listaResidentes = alohandes.darVOResidentes();
		Boolean esta = false;

		for (VOResidente op : listaResidentes){
			if (idResidente == op.getId()) esta = true;
		}

		return esta;
	}

	public Timestamp fechaActual (){
		LocalDate myDate = LocalDate.now(); 
		Timestamp fecha = Timestamp.valueOf(myDate.atStartOfDay());
		return fecha;
	}

	public List<String> parseList(String listString) {
		List<String> list = new ArrayList<>();
		String[] elements = listString.split(",");
		for (String element : elements) {
			list.add(element.trim());
		}
		return list;
	}

	public void asociarServicios (List<Integer> listaServicios, long idAlojamiento){

		for (Integer idServicio: listaServicios){
			
			VOServicio servicio = alohandes.darServicioPorId(idServicio.longValue());
			if (servicio != null){
				VOTieneServicio TS = alohandes.adicionTS(idAlojamiento, servicio.getId());
			}
		}
	}

	public List<Integer> parseListInt(String listString) {
		List<Integer> list = new ArrayList<>();
		String[] elements = listString.split(",");
		for (String element : elements) {
			list.add(Integer.parseInt(element.trim()));
		}
		return list;
	}
	
	public List<Oferta> ofertasDisponibles (){
		List<VOOperador> operadores = alohandes.darVOOperadores();
		List<VOOperador> opCompletos = new LinkedList<VOOperador>();
		for (VOOperador operador: operadores){
			VOOperador comp = alohandes.darOperadorCompleto(operador.getId());
			opCompletos.add(comp);
		}

		List<Oferta> ofertas = new LinkedList<Oferta>();
		for (VOOperador completo: opCompletos){
			List<Oferta> resp = completo.getOfertas();
			ofertas.addAll(resp);
		}

		return ofertas;
	}

	public List<String> serviciosSeleccionados(){
		JFrame frame = new JFrame("Checkbox Example");
        JPanel panel = new JPanel();
        JCheckBox checkBox1 = new JCheckBox("bañera");
        JCheckBox checkBox2 = new JCheckBox("yacuzzi");
        JCheckBox checkBox3 = new JCheckBox("sala");
        JCheckBox checkBox4 = new JCheckBox("cocineta");
        JCheckBox checkBox5 = new JCheckBox("restaurante");
        JCheckBox checkBox6 = new JCheckBox("piscina");
        JCheckBox checkBox7 = new JCheckBox("parqueadero");
        JCheckBox checkBox8 = new JCheckBox("WiFi");
        JCheckBox checkBox9 = new JCheckBox("TV cable");
        JCheckBox checkBox10 = new JCheckBox("recepcion 24 horas");
        JCheckBox checkBox11 = new JCheckBox("comidas");
        JCheckBox checkBox12 = new JCheckBox("acceso a cocina");
        JCheckBox checkBox13 = new JCheckBox("baño privado");
        JCheckBox checkBox14 = new JCheckBox("baño compartido");
        JCheckBox checkBox15 = new JCheckBox("internet");
        JCheckBox checkBox16 = new JCheckBox("telefono");
        JCheckBox checkBox17 = new JCheckBox("agua");
        JCheckBox checkBox18 = new JCheckBox("luz");
        JCheckBox checkBox19 = new JCheckBox("servicio aseo");
        JCheckBox checkBox20 = new JCheckBox("apoyo social y academico");
        JCheckBox checkBox21 = new JCheckBox("salas de estudio");
        JCheckBox checkBox22 = new JCheckBox("salas de esparcimiento");
        JCheckBox checkBox23 = new JCheckBox("gimnasio");
		panel.setLayout(new GridLayout(0, 1));
        panel.add(checkBox1);
        panel.add(checkBox2);
        panel.add(checkBox3);
        panel.add(checkBox4);
        panel.add(checkBox5);
        panel.add(checkBox6);
        panel.add(checkBox7);
        panel.add(checkBox8);
        panel.add(checkBox9);
        panel.add(checkBox10);
        panel.add(checkBox11);
        panel.add(checkBox12);
        panel.add(checkBox13);
        panel.add(checkBox14);
        panel.add(checkBox15);
        panel.add(checkBox16);
        panel.add(checkBox17);
        panel.add(checkBox18);
        panel.add(checkBox19);
        panel.add(checkBox20);
        panel.add(checkBox21);
        panel.add(checkBox22);
        panel.add(checkBox23);
        int result = JOptionPane.showConfirmDialog(null, panel, "Seleccione los servicios deseados",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        List<String> opcionesSeleccionadas = new ArrayList<>();
        if (result == JOptionPane.OK_OPTION) {
            if (checkBox1.isSelected()) {
                opcionesSeleccionadas.add("bañera");
            }
            if (checkBox2.isSelected()) {
                opcionesSeleccionadas.add("yacuzzi");
            }
            if (checkBox3.isSelected()) {
                opcionesSeleccionadas.add("sala");
            }
            if (checkBox4.isSelected()) {
                opcionesSeleccionadas.add("cocineta");
            }
            if (checkBox5.isSelected()) {
                opcionesSeleccionadas.add("restaurante");
            }
            if (checkBox6.isSelected()) {
                opcionesSeleccionadas.add("piscina");
            }
            if (checkBox7.isSelected()) {
                opcionesSeleccionadas.add("parqueadero");
            }
            if (checkBox8.isSelected()) {
                opcionesSeleccionadas.add("WiFi");
            }
			if (checkBox9.isSelected()) {
				opcionesSeleccionadas.add("TV cable");
			}
			if (checkBox10.isSelected()) {
				opcionesSeleccionadas.add("recepcion 24 horas");
			}
			if (checkBox11.isSelected()) {
				opcionesSeleccionadas.add("comidas");
			}
			if (checkBox12.isSelected()) {
				opcionesSeleccionadas.add("acceso a cocina");
			}
			if (checkBox13.isSelected()) {
				opcionesSeleccionadas.add("baño privado");
			}
			if (checkBox14.isSelected()) {
				opcionesSeleccionadas.add("baño compartido");
			}
			if (checkBox15.isSelected()) {
				opcionesSeleccionadas.add("internet");
			}
			if (checkBox16.isSelected()) {
				opcionesSeleccionadas.add("telefono");
			}
			if (checkBox17.isSelected()) {
				opcionesSeleccionadas.add("agua");
			}
			if (checkBox18.isSelected()) {
				opcionesSeleccionadas.add("luz");
			}
			if (checkBox19.isSelected()) {
				opcionesSeleccionadas.add("servicio aseo");
			}
			if (checkBox20.isSelected()) {
				opcionesSeleccionadas.add("apoyo social y academico");
			}
			if (checkBox21.isSelected()) {
				opcionesSeleccionadas.add("salas de estudio");
			}
			if (checkBox22.isSelected()) {
				opcionesSeleccionadas.add("salas de esparcimiento");
			}
			if (checkBox23.isSelected()) {
				opcionesSeleccionadas.add("gimnasio");
			}
		}
		return opcionesSeleccionadas;

	}
	public List<Long> createCheckBoxList(List<Long> values) {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        List<JCheckBox> checkBoxList = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            JCheckBox checkBox = new JCheckBox(String.valueOf(values.get(i)), false);
            checkBoxList.add(checkBox);
            panel.add(checkBox);
        }
        int result = JOptionPane.showConfirmDialog(null, panel, "Seleccione la opción deseada",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        List<Long> selectedValues = new ArrayList<>();
        if (result == JOptionPane.OK_OPTION) {
            for (JCheckBox checkBox : checkBoxList) {
                if (checkBox.isSelected()) {
                    selectedValues.add(Long.parseLong(checkBox.getText()));
                }
            }
        }
        return selectedValues;
    }
}
