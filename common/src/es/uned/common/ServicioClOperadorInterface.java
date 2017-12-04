package es.uned.common;

//import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Diego Diaz alvarellos
 * email: ddiaz136@uned.estudiante.es
 * 
 * contiene la interfaz remota del servicio Cliente-Operador 
 * que depende de la entidad Repositorio.
 * 
 * Este servicio se encarga de las operaciones de subida de 
 * ficheros al repositorio y borrado de los mismos.
 */
public interface ServicioClOperadorInterface extends Remote{
	
	boolean subirFichero(String nombreUsuario, Fichero fichero) throws RemoteException, FileNotFoundException, IOException;

	boolean BorrarFicheroRepositorio(String nombreCliente, String fichero) throws RemoteException;
	
	boolean DescargarFichero(String destino, String nombreCliente, String fichero) throws RemoteException, IOException;

	// Ruta donde se guardan los archivos de un cliente
	String ruta(String nombreCliente) throws RemoteException;
}