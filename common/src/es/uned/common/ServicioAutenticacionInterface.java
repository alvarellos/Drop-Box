package es.uned.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Diego Diaz alvarellos
 * email: ddiaz136@uned.estudiante.es
 * 
 * contiene la interfaz remota del servicio de autenticación 
 * que depende de la entidad Servidor.
 * 
 * Se encarga de registrar y de autenticar, cuando sea necesario, 
 * las otras entidades participantes en el sistema: clientes y repositorios.
 */
public interface ServicioAutenticacionInterface extends Remote{
	
	public static final int PUERTO = 2000;
	// Clientes
	public boolean registrarUsuario(String nombre) throws RemoteException;
	String iniciarSesionUsuario(String id) throws RemoteException;
	void cerrarSesionUsuario(String id) throws RemoteException;
	
	String ListarClientesSistema() throws RemoteException;
	// Repositorios
	boolean registraRepositorio(String id) throws RemoteException;
	String iniciarSesionRep(String nombre) throws RemoteException;
	void cierraRepositorio(String id) throws RemoteException;
	
	public String[] ListarClientesRepositorio(String id) throws RemoteException;
	
	// Base de datos
	public void dameBD(ServicioDatosInterface datos) throws RemoteException;


}