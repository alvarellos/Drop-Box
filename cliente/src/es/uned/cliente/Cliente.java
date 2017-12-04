package es.uned.cliente;

import java.io.File;
//import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
//import java.rmi.registry.LocateRegistry;
//import java.rmi.registry.Registry;
//import java.rmi.server.UnicastRemoteObject;
//import java.util.List;

import es.uned.common.*;



/**
 * @author Diego Diaz alvarellos
 * email: ddiaz136@uned.estudiante.es
 * 
 * Clase que contiene el main de la entidad Cliente.
 */
public class Cliente {

	private static String ID="";
	File[] listaFicherosLocales;
	
	private static ServicioAutenticacionInterface servAutentificacion;
	private static String URLRegistro = "rmi://localhost:" + 2000 + "/Aut";
	
	private static ServicioClOperadorInterface servCLOP;
	private static String URLCLOP = "rmi://localhost:" + 2001 + "/ServCLOP";
	
//	private static ServicioDiscoClienteInterface servDisco;
//	private static String URLDisco = "rmi://localhost:" + 2002 + "/ServDisco";
	
	// Nuevos
	Cliente() throws NotBoundException, IOException{
		gui1();
	}
	
	public static void main(String[] args) throws Exception {
		try{
		
		// Enlazador (busca el objeto remoto). Puerto por defecto 1099
		//String URLRegistro = "rmi://localhost:" + 1099 + "/Aut";
		servAutentificacion = (ServicioAutenticacionInterface) Naming.lookup(URLRegistro);
		
		// Otra forma : Manera directa -------------------------------------
		// Registry registry = LocateRegistry.getRegistry(1099);
		// servDatos =(ServicioDatosInterface)registry.lookup("Servidor");
		// -----------------------------------------------------------------
		
		}catch(Exception e){
			System.err.println("ERROR: No se consigue conectar con el servidor");
			System.exit(1);
		}

		new Cliente();
		System.out.println("Fin de conexión, gracias por su visita");
	}

	// MENU PRINCIPAL
	// -----------------------------------------------------
	
	private void gui1() throws NotBoundException, IOException {
		int opt = 0;
		
		do {
			System.out.println();
			opt = Gui.menu("Menu Principal",
							new String[]{ "Registrar un nuevo usuario", 
										  "Login", 
										  "Salir" });
			switch (opt) {
				case 0: {
					registro(); 

				}break;
				case 1: {
					if (login() == true)
						gui2();
				}break;
			}
		}
		while (opt != 2);
	}
	
	// MENU USUARIO
	// -----------------------------------------------------

	private void gui2() throws IOException {
		int opt = 0;
		
		do {
			System.out.println();
			opt = Gui.menu("Menu Usuario", 
							new String[]{ "Subir fichero", 
										  "Bajar fichero", 
										  "Borrar fichero",
										  "Listar ficheros",
										  "Listar Clientes del sistema",
										  "Salir" });
			
			switch (opt) {
				case 0: subirFichero(); break;
				case 1: bajarFichero(); break;
				case 2: borrarFichero(); break;
				case 3: listarFicheros(); break;
				case 4: listarClientes(); break;
			}
		}
		while (opt != 5);
	}
	
	// Métodos Principal
	
	private static void registro() throws RemoteException {
		System.out.println();
		String nombre = Gui.input("Registro de usuario nuevo\nIngrese nombre de usuario: ");
		System.out.println("Nombre de usuario seleccionado: " + nombre);
		//name = nombre;
		try {

			// Si el servidor devuelve true, es que se ha registrado. En caso contrario el nick ya existe
			boolean registrado=servAutentificacion.registrarUsuario(nombre);
			if(registrado)System.out.println("Usuario registrado correctamente, ya puede iniciar sesion.");
			else System.out.println("El usuario ya existe. Seleccione otro nombre");

		} catch (Exception e) {
			// En caso de error se muestra por pantalla
			System.err.print("Error: No se ha podido establecer usuario");
		}
	}
	
	private boolean login() throws RemoteException, MalformedURLException, NotBoundException {
		String nombre = Gui.input("Autenticarse", "Ingrese nombre de usuario: ");
		//try{
			// Si el servidor devuelve true, es que se ha registrado. En caso contrario el ID ya existe

			String logueado = servAutentificacion.iniciarSesionUsuario(nombre);
			if(logueado.equals("no_existe")){
				System.err.println("El usuario '" + nombre + "' no existe en nuestra base de datos");
				return false;
			}
			// this.ID = logueado;
			ID=logueado;
			System.out.println("Bienvenido " + nombre); 
			// Enlazador (busca el objeto remoto)
			servCLOP = (ServicioClOperadorInterface) Naming.lookup(URLCLOP);
		
			// fin generar objeto remoto ---------------------------	

			return true;
		/*} catch (Exception e) {
			// En caso de error se muestra por pantalla
			System.err.print("Error: No se ha podido establecer conexión");
			return false;
		}*/
				
	}

	// fin métodos principal
	

	// Métodos menu secundario
	
	private void subirFichero() throws IOException{
		String ruta= Gui.input("Escriba ruta del fichero: ", "Ruta: ");
		
		File carpeta = new File(ruta);
		if (carpeta.exists()){
			listarFicherosLocal(ruta);

			String fich = Gui.input("Escriba nombre del fichero: ", "Fichero: ");

			if (existe(fich,ruta)){
			Fichero fichero= new Fichero(ruta, fich, ID);

				if(servCLOP.subirFichero(Cliente.ID,fichero)){
					System.out.println("Fichero '"+ fich +"' subido con éxito.");	
				}
			}else{
				System.err.println("El fichero '" + fich + "' no existe");
			}
		}
		
		else{
			System.err.println("Lo sentimos pero la carpeta origen no existe");
		}

	
	}
	


	private static void borrarFichero() throws RemoteException {
		String borraFichero = Gui.input(" Borrar fichero ", " Nombre Fichero ");
		
		//try{
			if(servCLOP.BorrarFicheroRepositorio(Cliente.ID, borraFichero)){
				System.out.println("Fichero '"+ borraFichero +"' borrado con éxito.");
			}
			
			else{
				System.err.println("El fichero '" + borraFichero + "' no existe");
			}
		/*}
		catch (FileNotFoundException fnfe){
			System.out.println("Fichero no encontrado dentro de cliente");
			}*/	
	}
	
	// Método extra
	
	public boolean existe(String nombre, String ruta){

		boolean cierto = false;
		String file;

		File folder = new File(ruta);
		File[] listaF = folder.listFiles();

		for(int i=0; i< listaF.length; i++){
			if(listaF[i].isFile()){
				file = listaF[i].getName();
					if (file.equals(nombre)){
						cierto = true; break;
					}
			}
		}
		return cierto;
	}
	
	private static void listarFicherosLocal(String ruta) {
		System.out.println();
		System.out.println("-- Lista de ficheros disponibles --");
		String files;
		File folder = new File(ruta);
		File[] listaF=folder.listFiles();
		for(int i=0; i< listaF.length; i++){
			if(listaF[i].isFile()){
				files = listaF[i].getName();
				System.out.println(files);
			}
		}
		System.out.println("-- Fin de lista de ficheros disponibles --");
		System.out.println();
	}
	
	
	// Fin métodos servicio Cliente-Operador
	
	
	private static void bajarFichero() throws IOException {
		String destino = Gui.input("Bajar fichero: ", "Ruta destino | nombrefichero.extension: ");
				
		String fich = Gui.input("Escriba nombre del fichero: ", "Fichero: ");
		
		try{
			if(servCLOP.DescargarFichero(destino, Cliente.ID, fich)){
				System.out.println("Fichero "+ fich +" descargado con éxito.");
			} else {
				System.out.println("El fichero '" + fich + "' indicado es erróneo");
			}
		}catch (IOException e){
			System.err.println("No existe el directorio destino indicado");;
			//System.out.println("Fichero no encontrado dentro de cliente");
			}	
	}

	
	private static void listarFicheros() throws IOException {

		String ruta = servCLOP.ruta(Cliente.ID);
		String files;
		File folder = new File(ruta);
		File[] listaF=folder.listFiles();
		System.out.println("Ficheros guardados en repositorio cliente " + Cliente.ID);
		if (listaF.length > 0) { 
			for(int i=0; i< listaF.length; i++){

				if(listaF[i].isFile()){
					files = listaF[i].getName();
					System.out.println(files);
				}
			}
		System.out.println("Fin lista de ficheros de cliente " + Cliente.ID);
		} else {
			System.out.println("No se han subido todavía ficheros");
		}
	}
	
	
	private static void listarClientes() throws RemoteException {
		System.out.println(servAutentificacion.ListarClientesSistema());
		
	}
	

}