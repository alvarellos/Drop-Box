package es.uned.common;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author Diego Diaz alvarellos
 * email: ddiaz136@uned.estudiante.es
 * 
 * Clase adicional. 
 * Utilidades del libro "Computación distribuida" Liu
 */
public class Utils {
	
	public static final String CODEBASE = "java.rmi.server.codebase";
	
	
	/**
	 * Nos permitirá definir de forma dinámica donde se
	 * ubican los ficheros .class de las clases Serializables y de los
	 * stubs de las clases Remote, y asignarlo a la propiedad del
	 * sistema java.rmi.server.codebase
	 */
	public static void setCodeBase(Class<?> c) {
		String ruta = c.getProtectionDomain().getCodeSource()
					   .getLocation().toString();
		
		String path = System.getProperty(CODEBASE);
		
		if (path != null && !path.isEmpty()) {
			ruta = path + " " + ruta;  
		}
		
		System.setProperty(CODEBASE, ruta);
	}
	
	

	/**
	 * Este metodo arranca un registro RMI en la maquina
 	 * local, si no existe en el nuumero de puerto especificado.
	 */
	public static void arrancarRegistro(int numPuertoRMI) throws RemoteException{ 
		try {
		// Un servidor rmiregistry debe ejecutarse en el nodo del servidor de objeto
		//	para poder registrar objetos RMI.
		Registry registro = LocateRegistry.getRegistry(numPuertoRMI); 
		// Esta llamada lanza una excepcion si el registro no existe
		registro.list(); 
		}catch (RemoteException e) {
			// Enlazador = Se crea el registro en el puerto especificado (parte del servidor)
			LocateRegistry.createRegistry(numPuertoRMI);
		}
	}
	
	

	/**
	 * Este método lista los nombres registrados con un objeto Registry
	 */
	public static void listaRegistro(String URLRegistro)throws RemoteException, MalformedURLException {
	System.out.println("Registro " + URLRegistro + " contiene: ");
	String [] nombres = Naming.list(URLRegistro);
	for (int i=0; i<nombres.length; i++)
	System.out.println(nombres[i]);
	} 

}