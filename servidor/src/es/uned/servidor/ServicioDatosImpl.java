package es.uned.servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import es.uned.common.DatosRepositorio;
// import es.uned.common.DatosUsuario;
import es.uned.common.Fichero;
import es.uned.common.ServicioDatosInterface;

/**
 * @author Diego Diaz alvarellos
 * email: ddiaz136@uned.estudiante.es
 * 
 * clase que implementa la interfaz
 * ServicioDatosInterface
 */
public class ServicioDatosImpl extends UnicastRemoteObject implements ServicioDatosInterface{


	protected ServicioDatosImpl() throws RemoteException {
		super();
	}

	private static final long serialVersionUID = 1L;
	
	// private ArrayList<DatosUsuario> listaClientes = new ArrayList<DatosUsuario>();
	// private ArrayList<Fichero> listaFicheros = new ArrayList<Fichero>();
	private static ArrayList<DatosRepositorio> listaRepositorios = new ArrayList<DatosRepositorio>();

	
	private HashMap<String, Integer> usuarioConectado = new HashMap<>();
	private int idSesion = 0;
	
//	private String nombre;
//	private String nombreFichero;
//	private static String URLRegistro = "rmi://localhost:" + 2000 + "/Aut";

	private static String sistema = "Info sistema : ";

	// Para listar los repositorios con su número
	private static Map<Integer, String> sesion_nombre_repo = new HashMap<Integer, String>();
	private static Map<String, Integer> nombre_sesion_repo = new HashMap<String, Integer>();
	private static int numRepositorio = 0;
	// ---------------------------------------------------------------------------------------
	
	// Para listar los usuarios con su número
	private static Map<Integer, String> sesion_nombre_usuario = new HashMap<Integer, String>();
	private static Map<String, Integer> nombre_sesion_usuario = new HashMap<String, Integer>();
	private static int numUsuario = 0;
	// ---------------------------------------------------------------------------------------
	
	// Para listar los usuarios con sus repositorios
	private static Map<String, String> usuario_repositorio = new HashMap<String, String>();
	private static Map<String, String> repositorio_usuario = new HashMap<String, String>();
	// ---------------------------------------------------------------------------------------
	
	
	// Usuario / Cliente
	// ------------------------------------------------------------------
	
	
/*	@Override
	public String getUsuario(String id) throws RemoteException {
		try{
			for(int i=0; i < getlistaUsuario().size(); i++){
				if (getlistaUsuario().get(i).getNombreUsuario().equals(id))
					// Si se encuentra el usuario se retorna el usuario
				return getlistaUsuario().get(i).getNombreUsuario();
			}
			// Si no se encuentra el usuario se retorna con null

		}catch(NullPointerException e){
			System.err.println("Error: no se coge usuario en lista");
		}
		return null;
	}*/


	// Nuevo
	
	public boolean registrarNuevoCl(String nombreCl) throws RemoteException{
		// comprobar que no existe el cliente
		for(DatosRepositorio dr : listaRepositorios){
			if(dr.existe(nombreCl))return false;
		}
		// Añadir a la lista de clientes
		// Genramos un ID
		// emparejarlo con un repositorio
		Random rand = new Random();
		int size = listaRepositorios.size();
		int index = rand.nextInt(size);
		DatosRepositorio datR = listaRepositorios.get(index);
		datR.nuevoUsuario(nombreCl);
		
		// Listas Map con nombre e identificador
		int numUsuario = getNumUsuario();
		sesion_nombre_usuario.put(numUsuario, nombreCl);
		nombre_sesion_usuario.put(nombreCl, numUsuario);
		
		// Listas Map repositorio - cliente
		usuario_repositorio.put(nombreCl, datR.getNombre());
		repositorio_usuario.put(datR.getNombre(), nombreCl);
		
		return true;
	}
	
	public static int getNumUsuario() {
		return ++numUsuario;
	}
	
	public String iniciarSesionCl(String nombreUsuario){
		boolean existeCl=false;
		for(DatosRepositorio dr : listaRepositorios){
			if(dr.existe(nombreUsuario))existeCl=true;
		}
		if(existeCl) return nombreUsuario;
		return "no_existe";
	}
	// Fin usuario / Cliente
	// ------------------------------------------------------------------
	
	// Repositorio
	// ------------------------------------------------------------------
	
	@Override
	public DatosRepositorio getRepositorio(String nombreRepositorio) throws RemoteException {
		for(int i=0; i < getlistaRepositorio().size(); i++){
			if (getlistaRepositorio().get(i).getNombre().equals(nombreRepositorio))
		// Si se encuentra el repositorio se retorna el usuario
				return getlistaRepositorio().get(i);
		}
		// Si no se encuentra el respositorio se retorna con null
		return null;
	}

	@Override
	public void registraRepositorio(String nombreRepositorio) throws RemoteException {
		// Siempre está permitido (se pueden repetir nombres de repositorio)

		listaRepositorios.add(new DatosRepositorio(nombreRepositorio));
		
		// Listas Map con nombre e identificador
		int numRepositorio = getNumRepo();
		sesion_nombre_repo.put(numRepositorio, nombreRepositorio);
		nombre_sesion_repo.put(nombreRepositorio, numRepositorio);
	}
	
	public static int getNumRepo() {
		return ++numRepositorio;
	}
	
	public String iniciarSesionRep(String nombreRep){
		boolean existeRep=false;
		for(DatosRepositorio dr : listaRepositorios){
			if(dr.getNombre().equals(nombreRep)) existeRep=true;
		}
		if(existeRep) return nombreRep;
		return "no_existe";
	}
	
	// Fin Repositorio
	// ------------------------------------------------------------------
	

	// Listar 
	// ------------------------------------------------------------------
	
	@Override
	public String ListarClientes() throws RemoteException {
		return sistema + " Numero de usuarios registrados " + numUsuario + "\n" + 
				sistema + " Lista Usuarios" + sesion_nombre_usuario+"\n";
	}
	
	@Override
	public void ListaRepositorios() throws RemoteException {
		System.out.println(sistema + " Numero de repositorios registrados " + numRepositorio );
		System.out.println(sistema + " Lista Repositorios" + sesion_nombre_repo);
		System.out.println();
	}

	@Override
	public void listarClienteRepositorio() throws RemoteException {
		System.out.println(sistema + " Numero de usuarios registrados " + numUsuario );
		System.out.println(sistema + " Numero de repositorios registrados " + numRepositorio );
		System.out.println(sistema + " Lista (Usuarios=Repositorio) " + usuario_repositorio);
		System.out.println();
		
	}
	// FIN LISTAR
	// ------------------------------------------------------------------
	
	
	// Fichero
	// ------------------------------------------------------------------
	
	@Override
	public void addFichero(Fichero fichero) throws RemoteException {
		getlistaFichero().add(fichero);	
	}

	@Override
	public void borraFichero(Fichero fichero) throws RemoteException {
		getlistaFichero().remove(fichero);	
	}

	@Override
	public void descargaFichero(Fichero fichero) throws RemoteException {
	}
	
	@Override
	public String[] listarFicheros() throws RemoteException {
		String[] listaFicherosCliente = new String[getlistaFichero().size()];
		for (int i = 0; i < getlistaFichero().size(); i++)
			listaFicherosCliente[i] = getlistaFichero().get(i).obtenerPropietario();
		return listaFicherosCliente;
	}
	// Fin fichero
	// ------------------------------------------------------------------
	
	// Id Sesión (para usuarios conectados / posible ampliación)
	// ------------------------------------------------------------------

	@Override
	public int setIdSesion(String nombreUsuario) throws RemoteException {
		// Se añade el usuario on-line y se le asigna un idSesion
		usuarioConectado.put(nombreUsuario, ++idSesion);
		return idSesion; 
	}

	@Override
	public int getIdSesion(String nombreUsuario) throws RemoteException {
		if (usuarioConectado.get(nombreUsuario) == null)
			return -1;
		else
			return usuarioConectado.get(nombreUsuario);
	}

	@Override
	public int eliminarIdSesion(String nombreUsuario) throws RemoteException {
		return usuarioConectado.remove(nombreUsuario);
	}

	// Fin ID Sesion
	// ------------------------------------------------------------------


	// COPIAS LISTAS
	// ------------------------------------------------------------------

//	private ArrayList<DatosUsuario> getlistaUsuario() {
//		return listaClientes;
//	}
	
	private ArrayList<DatosRepositorio> getlistaRepositorio() {
		return listaRepositorios;
	}

	private ArrayList<Fichero> getlistaFichero() {
		return null;
	}

	// FIN COPIAS
	// ------------------------------------------------------------------


}