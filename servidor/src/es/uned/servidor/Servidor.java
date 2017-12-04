package es.uned.servidor;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import es.uned.common.Gui;
import es.uned.common.ServicioAutenticacionInterface;
import es.uned.common.ServicioDatosInterface;
import es.uned.common.Utils;


/**
 * @author Diego Diaz alvarellos
 * email: ddiaz136@uned.estudiante.es
 * 
 * Clase que contiene el main de la entidad Servidor.
 * comprueba primero si se está ejecutando actualmente un registro
 * RMI en el puerto por defecto. Si no es así, se activa un registro RMI.
 * 
 */
public class Servidor {

	private static ServicioAutenticacionInterface servAutenticacion;
	private static ServicioDatosInterface servDatos;
	private static String URLRegistro;
	
	// private static String URLDatos;
	//private static ServicioGestorImpl serGestor;
	
	public static void main(String[] args) throws Exception {
		
		// Se inicia el gestor de Autenticacion
		Utils.setCodeBase(ServicioAutenticacionInterface.class);
		Utils.setCodeBase(ServicioDatosInterface.class);
		
		// "Enlazador" : 
		servDatos = new ServicioDatosImpl();
		// 1. Obtener el valor del numero de puerto, 
		Utils.arrancarRegistro(ServicioAutenticacionImpl.PUERTO);
		Utils.arrancarRegistro(ServicioDatosImpl.PUERTO);
		// 2. Creación del objeto Remoto
		servAutenticacion = new ServicioAutenticacionImpl();
		URLRegistro = "rmi://localhost:" + ServicioAutenticacionImpl.PUERTO + "/Aut";

		
		servAutenticacion.dameBD(servDatos);		
		servDatos = new ServicioDatosImpl();
		// URLDatos = "rmi://localhost:" + ServicioDatosImpl.PUERTO + "/ServDatos";
		

		// Otra manera (directa)---------------------------

		// Enlazador (Registro del objeto remoto)
		// Registry registry = LocateRegistry.createRegistry(8888);
		// ServicioAutenticacionInterface remote = (ServicioAutenticacionInterface)UnicastRemoteObject.exportObject(servidor, 8888);
		// System.out.println("Servicio autenticacion levantado");//Aqui estas exportando el servidor y no el Servicio Auth
		// -----------------------------------------------
		try{
			// 3. Registro del objeto remoto de nombre "/Aut"
			// La clase Naming proporciona métodos para almacenar y obtener referencias del
			// registro. En particular, el método rebind permite almacenar en el registro 
			// una referencia a un objeto con un URL
			// El método rebind sobreescribe cualquier referencia en el registro asociada al nombre
			// de la referencia. Si no se desea sobreescribir, existe un método denominado bind.
			Naming.rebind(URLRegistro, servAutenticacion);
			// Naming.rebind(URLDatos, servDatos);
			
			// Otra manera (directa) ---------------------------------
			// Registry registry = LocateRegistry.getRegistry();
			// registry.rebind("Servidor", remote);
			// ------------------------------------------------------
		}catch (Exception e){
			System.err.println("Error en la conexión del servidor" + e);
			System.exit(1);
		}

		gui();

		
		// Desconectar el servidor / fin del objeto remoto
		Naming.unbind(URLRegistro);
		UnicastRemoteObject.unexportObject(servAutenticacion, true);

		// Naming.unbind(URLDatos);
		// UnicastRemoteObject.unexportObject(servDatos, true);
		
		System.out.println("Servidor desconectado");
		System.exit(0);
	}
	
	private static void gui() throws IOException, RemoteException {
		int opt = 0;
		
		do {
			opt = Gui.menu("Menu Servidor", 
							new String[]{ "Listar clientes", 
										  "Listar Repositorios",
										  "Listar Parejas Repositorio-Cliente",
										  "Salir" });
			
			switch (opt) {
				case 0: listarClientes(); break;
				case 1: listaRepositorios(); break;
				case 2: listarParejasRepoCliente(); break;

			}
		}
		while (opt != 3);
	}
	
	private static void listarClientes() throws RemoteException {
		System.out.println(servDatos.ListarClientes());
	}
	
	private static void listaRepositorios() throws RemoteException {
		servDatos.ListaRepositorios();
	}

	private static void listarParejasRepoCliente() throws RemoteException {
		servDatos.listarClienteRepositorio();	
	}

}