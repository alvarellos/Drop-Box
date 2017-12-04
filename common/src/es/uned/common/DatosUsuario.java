package es.uned.common;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Diego Diaz alvarellos
 * email: ddiaz136@uned.estudiante.es
 *
 */
public class DatosUsuario implements Serializable {

	private static final long serialVersionUID = 1L;
	private ArrayList<DatosUsuario> listaUsuarios;
	public String nombreUsuario;
	private int numSesion;
	private ArrayList<String> ficheros;
	
	
	/**
	 * Constructor de la clase DatosUsuario
	 */
	public DatosUsuario(String nombreUsuario){
		this.nombreUsuario = nombreUsuario;
		ficheros = new ArrayList<>();
	}

	// Getters & Setters
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public int getSesion() {
		return numSesion;
	}

	public void setSesion(int numSesion) {
		this.numSesion = numSesion;
	}


	public ArrayList<String> getFicheros() {
		return ficheros;
	}

	public void setFicheros(ArrayList<String> ficheros) {
		this.ficheros = ficheros;
	}
	
	   public ArrayList<DatosUsuario> getTablaUsuario(){
	    	return listaUsuarios;
	    }
	
}