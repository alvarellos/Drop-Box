package es.uned.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Diego Diaz alvarellos
 * email: ddiaz136@uned.estudiante.es
 * 
 * contiene la interfaz remota del servicio Datos 
 * que depende de la entidad Servidor.
 * 
 * Este servicio har� las funciones de una base de datos que relacione
 * Clientes-Ficheros-Metadatos-Repositorios. Es decir, mantendr� lista de clientes 
 * y repositorios conectados al sistema, junto con los ficheros; 
 * y los relacionar�n permitiendo operaciones de consulta, borrado y a�adido.
 */
public interface ServicioDatosInterface extends Remote{

	public static final int PUERTO = 1099;
	
	// USUARIO
	
	public boolean registrarNuevoCl(String nombre) throws RemoteException;
	String iniciarSesionCl(String nombreUsuario) throws RemoteException;
	
	
	// REPOSITORIO (necesario para listar clientes repositorio)
	DatosRepositorio getRepositorio(String id) throws RemoteException;
	
	void registraRepositorio(String nombrePropietario) throws RemoteException;
	String iniciarSesionRep(String nombreRep) throws RemoteException;
	
	// LISTA
	// String[] listarClientes2() throws RemoteException;
	String[] listarFicheros() throws RemoteException;
	// String[] listaRepositorios2() throws RemoteException;
	
	void listarClienteRepositorio() throws RemoteException;
	
	void ListaRepositorios() throws RemoteException;
	String ListarClientes() throws RemoteException;
	
	// FICHEROS
	
	void addFichero(Fichero fichero) throws RemoteException;
	void borraFichero(Fichero fichero) throws RemoteException;
	void descargaFichero(Fichero fichero) throws RemoteException;


	// Control ID
	int setIdSesion(String nombreUsuario) throws RemoteException;
	int getIdSesion(String nombreUsuario) throws RemoteException;
	int eliminarIdSesion(String nombreUsuario) throws RemoteException;
	

	
	

}