package es.uned.repositorio;

import java.io.File;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

// import es.uned.cliente.Cliente;
import es.uned.common.*;

/**
 * @author Diego Diaz alvarellos
 * email: ddiaz136@uned.estudiante.es
 * 
 * Clase que contiene el main de la entidad Repositorio.
 */
public class Repositorio {

	//private static ServicioDatosInterface servDatos;

	private static String name = ""; // Mantener en staic ???
	private static ServicioAutenticacionInterface servAutenticacion;
	private static String URLRegistro = "rmi://localhost:" + 2000 + "/Aut";

	private static ServicioClOperadorInterface servCLOP;
	private static String URLCLOP = "rmi://localhost:" + 2001 + "/ServCLOP";;
	
//	private static ServicioDiscoClienteInterface servDisco;
//	private static String URLDisco = "rmi://localhost:" + 2002 + "/ServDisco";
//	
//	private static ServicioDatosInterface servDatos;
	
	Repositorio()throws NotBoundException, IOException{

			gui1();
	}
	
	public static void main(String[] args) throws Exception {
		
		//try{
		// Enlazador (busca el objeto remoto). Puerto por defecto 1099
		// String URLRegistro = "rmi://localhost:" + 2000 + "/Aut";
		servAutenticacion = (ServicioAutenticacionInterface) Naming.lookup(URLRegistro);
		
		// Otra forma : Manera directa -------------------------------------
		// Registry registry = LocateRegistry.getRegistry(1099);
		// servDatos =(ServicioDatosInterface)registry.lookup("Servidor");
		// -----------------------------------------------------------------
		
		// Generar objeto remoto para servicioClOperador
		
		Utils.setCodeBase(ServicioClOperadorInterface.class);
		// 1. Obtener el valor del numero de puerto, 
		Utils.arrancarRegistro(2001);
		
		servCLOP = new ServicioClOperadorImpl();
		ServicioClOperadorInterface SCLOPRemoto = (ServicioClOperadorInterface)UnicastRemoteObject.exportObject(servCLOP,2001);
		// URLCLOP = "rmi://localhost:" + 2001 + "/ServCLOP";
		Naming.rebind(URLCLOP, SCLOPRemoto);
		// fin generar objeto remoto ----------------------

		
		//}catch(Exception e){
			//System.err.println("ERROR: No se consigue conectar con el servidor");
			//((System.exit(0);
		//}

		new Repositorio();
		
		System.out.println("Fin de conexión de repositorio");
		System.exit(0);
	}
	
	// MENU PRINCIPAL
	// -----------------------------------------------------

	private void gui1() throws NotBoundException, IOException {
	int opt = 0;
	
		do {
			System.out.println();
			opt = Gui.menu("Menu Principal",
					new String[]{ "Registrar un nuevo repositorio", 
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

	// MENU SECUNDARIO
	// -----------------------------------------------------

	private static void gui2() throws IOException {
		int opt = 0;
		
		do {
			System.out.println();
			opt = Gui.menu("Menu Repositorio", 
							new String[]{ "Listar clientes", 
										  "Listar fichero del cliente", 
										  "Salir" });
			
			switch (opt) {
				case 0: listarClientes(); break;
				case 1: listarFicherosCliente(); break;

			}
		}
		while (opt != 2);
	}

	// MÉTODOS menu ppal
	
	private static void registro() throws RemoteException {
		System.out.println();
		String nombre = Gui.input("Registro de repositorio nuevo", "Ingrese nombre de repositorio: ");

		try {
			// Si el servidor devuelve true, es que se ha registrado. En caso contrario el nombre ya existe
				servAutenticacion.registraRepositorio(nombre);
				System.out.println("Repositorio registrado correctamente, ya puede iniciar sesion.");

		} catch (Exception e) {
			// En caso de error se muestra por pantalla
			System.err.print("Error: No se ha podido establecer el repositorio");
		}
	}
	
	private boolean login() {
		String nombre = Gui.input("Autenticarse", "Ingrese nombre de repositorio: ");

		try{

			// Si el servidor devuelve true, es que se ha registrado. En caso contrario el nick ya existe

			String logueado =servAutenticacion.iniciarSesionRep(nombre);
			if(logueado.equals("no_existe")) {
				System.err.println("El repositorio no está en nuestra base de datos");
				return false;
			}
			// this.name=logueado;
			name=logueado;
			System.out.println("Bienvenido " + name); 
			// Enlazador (busca el objeto remoto)
			// No funciona
			// servDisco = (ServicioDiscoClienteInterface) Naming.lookup(URLDisco);
			return true;
		} catch (Exception e) {
			// En caso de error se muestra por pantalla
			System.err.print("Error: No se ha podido establecer conexión");
			return false;
		}		

	}
	
	// fin métodos menu ppal
	
	// Métodos menú secundario
	

	private static void listarClientes() throws RemoteException {
		System.out.println("Lista de clientes del repositorio '" + Repositorio.name + "' ");
		String[] lista = servAutenticacion.ListarClientesRepositorio(Repositorio.name);
		System.out.println(Arrays.toString(lista));
		System.out.println();
	}
	
	private static void listarFicherosCliente() throws RemoteException {
		String nombre = Gui.input("Ficheros de cliente \n Ingrese nombre cliente: ");
		
		try{
		String ruta = servCLOP.ruta(nombre);
		String files;
		File folder = new File(ruta);
		File[] listaF=folder.listFiles();
		if (listaF.length > 0) { 
			System.out.println("Ficheros guardados en repositorio cliente " + nombre);
			for(int i=0; i< listaF.length; i++){
				if(listaF[i].isFile()){
					files = listaF[i].getName();
					System.out.println(files);
				}
			}
			System.out.println("Fin lista de ficheros de cliente '" + nombre + "' ");
		}else{
			System.out.println("No se han subido todavía ficheros");
		}
		}catch(Exception e){
			System.err.println("El usuario no está dentro de este repositorio");
		}
		
		
	}
}