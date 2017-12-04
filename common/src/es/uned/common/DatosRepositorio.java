package es.uned.common;

import java.util.ArrayList;

/**
 * @author Diego Diaz alvarellos
 * email: ddiaz136@uned.estudiante.es
 *
 * Clase metadatos para los Repositorios
 */
public class DatosRepositorio {

//	private static final long serialVersionUID = 1L;
	private ArrayList<DatosRepositorio> listaRepositorios;
	private String nombreRep;
	private int numRepositorio;
	private ArrayList<String> ficheros;
	private ArrayList<DatosUsuario> listaUsuarios = new ArrayList<DatosUsuario>();
	
	
	/**
	 * Constructor
	 */
	public DatosRepositorio(String nombreRep){
		this.nombreRep = nombreRep;
		ficheros = new ArrayList<>();
	}
	
	public boolean existe(String nombre){
		for(DatosUsuario du:listaUsuarios){
			if (du.getNombreUsuario().equals(nombre)){
				return true;
			}
		}
		return false;
	}

	public String getNombre() {
		return nombreRep;
	}

	public int getNumRepo() {
		return numRepositorio;
	}
	
	public void setNumRepo(int numRepositorio){
		this.numRepositorio = numRepositorio;
	}

	public ArrayList<String> getFicheros() {
		return ficheros;
	}

	public void setFicheros(ArrayList<String> ficheros) {
		this.ficheros = ficheros;
	}

	public ArrayList<DatosRepositorio> getTablaRepositorio(){
		return listaRepositorios;
	}
	
	public void nuevoUsuario(String nombre){
		listaUsuarios.add(new DatosUsuario(nombre));
	}
	
	public ArrayList<DatosUsuario> getTablaUsuarios(){
		return listaUsuarios;
	}
	
}
