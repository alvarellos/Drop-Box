package es.uned.common;


import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * @author Diego Diaz alvarellos
 * email: ddiaz136@uned.estudiante.es
 * 
 * contiene la interfaz remota del servicio Servidor-Operador 
 * que depende de la entidad Repositorio.
 * 
 * Este servicio tiene un doble objetivo. Por un lado, 
 * suministra los m�todos necesarios para que el servidor 
 * gestione los lugares de almacenamiento para cada cliente, 
 * y por otro lado se encarga de la operaci�n de bajada de 
 * ficheros desde el repositorio al cliente
 */
public interface ServicioSrOperadorInterface extends Remote {

	// M�todos para gestionar los lugares de almacenamiento cliente
	/**
	 * M�todo que almacena el lugar donde se a�aden los ficheros
	 * de un usuario
	 * @param id
	 * @param lugarAlmacenamiento
	 * @throws RemoteException
	 */
	public void altaRegistroUsuario(String id, int lugarAlmacenamiento) throws RemoteException;
	
	/**
	 * M�todo para la eliminaci�n de un registro asociado a un cliente
	 * @param id
	 * @throws RemoteException
	 */
	public void eliminaRegistroUsuario(String id) throws RemoteException;
	
	
	// M�todo se encarga de operaci�n de bajada
	
	/**
	 * M�todo que se encarga de operaci�n de bajada desde repertorio cliente
	 * @param id
	 * @param fichero
	 * @throws RemoteException
	 */
	void BajarFicheroRepositorio(String id, String fichero) throws RemoteException;
}