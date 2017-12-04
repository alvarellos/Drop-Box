package es.uned.common;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Diego Diaz alvarellos
 * email: ddiaz136@uned.estudiante.es
 * 
 * contiene la interfaz remota del servicio DiscoCliente 
 * que depende de la entidad Cliente.
 * 
 * Utilizado por el servicio Servidor-Operador del 
 * repositorio para descargar al disco duro local del cliente 
 * el fichero que este considere oportuno.
 * 
 */
public interface ServicioDiscoClienteInterface extends Remote {

	public static final int PUERTO = 2002;
	
	boolean DescargarFichero(String destino, String nombreCliente, String ficheroDescargar) throws RemoteException, IOException;
	
	
}
