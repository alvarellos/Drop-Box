package es.uned.servidor;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import es.uned.common.*;
/**
 * @author Diego Diaz alvarellos
 * email: ddiaz136@uned.estudiante.es
 * 
 * clase que implementa la interfaz
 * ServicioGestorInterface
 */
public class ServicioGestorImpl extends UnicastRemoteObject implements ServicioGestorInterface{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ServicioDatosInterface servDatos;
	// private static HashMap<String, Fichero> listaFicheros = new HashMap<>();

	protected ServicioGestorImpl() throws RemoteException {
		super();
		try{
			Registry registry = LocateRegistry.getRegistry(1999);
			servDatos = (ServicioDatosInterface)registry.lookup("Gestor");

		}catch (Exception exp){
			System.err.print("No arranca el Servicio Gestor");
		}
	}
	
	@Override
	public void SubirFicheroNube(String id, String fichero) throws RemoteException {
		Fichero ficheroASubir = new Fichero(fichero, id); // Fichero(nombreFichero, propietario)
		// Se sube el Fichero en la Base de Datos
		servDatos.addFichero(ficheroASubir);
	}

	@Override
	public void BorrarFicheroNube(String id, String fichero) throws RemoteException {
		Fichero ficheroABorrar = new Fichero(fichero, id); // Fichero(nombreFichero, propietario)
		// Se borra el Fichero en la Base de Datos
		servDatos.borraFichero(ficheroABorrar);	
		
	}
	
	@Override
	public void BajarFicheroNube(String id, String fichero) throws RemoteException {
		Fichero ficheroDescarga= new Fichero(fichero, id); // Fichero(nombreFichero, propietario)
		// Se borra el Fichero en la Base de Datos
		servDatos.descargaFichero(ficheroDescarga);	
		
	}

	@Override
	public String[] ListarFicherosUsuario(DatosUsuario id) throws RemoteException {
		// String RepoUsuario = new Repositorio(id);
		return servDatos.listarFicheros();
	}


}