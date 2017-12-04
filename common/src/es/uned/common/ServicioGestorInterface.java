package es.uned.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
//import java.util.List;

/**
 * @author Diego Diaz alvarellos
 * email: ddiaz136@uned.estudiante.es
 * 
 * contiene la interfaz remota del servicio Gestor 
 * que depende de la entidad Servidor.
 * 
 * Este servicio se encarga de gestionar las operaciones 
 * de los clientes en relación a sus ficheros en la nube 
 * (físicamente alojados en los repositorios).
 */
public interface ServicioGestorInterface extends Remote{


	/**
	 * Metodo que almacena un fichero de un cliente
	 * en la base de datos
	 * @param id
	 * @param fichero
	 * @throws RemoteException
	 */
	void SubirFicheroNube(String id, String fichero) throws RemoteException;
	
	/**
	 * Método que descarga un fichero de un cliente
	 * @param id
	 * @param fichero
	 * @throws RemoteException
	 */
	void BajarFicheroNube(String id, String fichero) throws RemoteException;
	
	/**
	 * Método que borra un fichero de un cliente
	 * @param id
	 * @param fichero
	 * @throws RemoteException
	 */
	void BorrarFicheroNube(String id, String fichero) throws RemoteException;
	
	/**
	 * Lista los ficheros de un cliente
	 * @param sesion
	 * @return
	 * @throws RemoteException
	 */
	public String[] ListarFicherosUsuario(DatosUsuario id) throws RemoteException;
	
	
}