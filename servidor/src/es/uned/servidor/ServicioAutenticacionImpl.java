package es.uned.servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import es.uned.common.*;

/**
 * @author Diego Diaz alvarellos
 * email: ddiaz136@uned.estudiante.es
 * 
 * clase que implementa la interfaz
 * ServicioAutenticacionInterface
 */
public class ServicioAutenticacionImpl extends UnicastRemoteObject implements ServicioAutenticacionInterface {
	private ServicioDatosInterface misDatos;
	private static final long serialVersionUID = 1L;
	private static ServicioDatosInterface servDatos;
	
	
	// Constructor
	public ServicioAutenticacionImpl() throws RemoteException {
		super();
	}



	// Usuario
	
	public void dameBD(ServicioDatosInterface datos){
		this.misDatos=datos;
	}
	
	public boolean registrarUsuario(String nombre) throws RemoteException{
		return misDatos.registrarNuevoCl(nombre);
		//ID del cliente
	}
	
	
	@Override
	public String iniciarSesionUsuario(String nombreUsuario) throws RemoteException {
		//comprobamos en base de datos si existe el usuario
		return misDatos.iniciarSesionCl(nombreUsuario);
		
	}

	

	@Override
	public void cerrarSesionUsuario(String id) throws RemoteException {
		servDatos.eliminarIdSesion(id);	
	}
	
	@Override
	public String ListarClientesSistema() throws RemoteException {
		return misDatos.ListarClientes();
		
	}


	// Repositorio
	

	
	@Override
	public boolean registraRepositorio(String id) throws RemoteException {
		misDatos.registraRepositorio(id);
		return true;
	}
	
	public String iniciarSesionRep(String nombreRep) throws RemoteException{
		return misDatos.iniciarSesionRep(nombreRep);
	}
	
	@Override
	public void cierraRepositorio(String id) throws RemoteException {
		servDatos.eliminarIdSesion(id);
	}

	@Override
	public String[] ListarClientesRepositorio(String id) throws RemoteException {
		// Busca en el objeto Repositorio que corresponde con ID del repositorio
		// la tabla de Usuarios y devuelve la lista
		ArrayList<DatosUsuario> lista = misDatos.getRepositorio(id).getTablaUsuarios();
		String[] listaClientes = new String[lista.size()];
		for (int i = 0; i < lista.size(); i++){
			listaClientes[i] = lista.get(i).getNombreUsuario();
		}
		return listaClientes;
	}







}