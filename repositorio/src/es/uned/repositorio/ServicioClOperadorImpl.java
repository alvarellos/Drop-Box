package es.uned.repositorio;

import java.io.File;
import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
//import java.net.URL;
import java.rmi.RemoteException;

import es.uned.common.*;

/**
 * @author Diego Diaz alvarellos
 * email: ddiaz136@uned.estudiante.es
 * 
 * clase que implementa la interfaz
 * "ServicioClOperadorInterface"
 */
public class ServicioClOperadorImpl implements ServicioClOperadorInterface{
	
	ServicioClOperadorImpl(){	
		super();
		// System.out.println("Servicio CLOP levantado.");
	}

	@Override
	public boolean subirFichero(String nombreCliente, Fichero fichero) throws IOException {
		File carpeta = new File(nombreCliente);
		carpeta.mkdirs();
		// System.out.println(carpeta.getCanonicalPath());
		// Linux
		//File fich = new File("/" + carpeta + "/" + fichero.obtenerNombre());
		File fich = new File("\\" + carpeta + "\\" + fichero.obtenerNombre());
		if(fich.exists()) return false;
		//String rutaABS = carpeta.getCanonicalPath()+ "/" +fichero.obtenerNombre();
		String rutaABS = carpeta.getCanonicalPath()+ "\\" +fichero.obtenerNombre();
		// System.out.println(rutaABS);
		OutputStream out = new FileOutputStream(rutaABS);
		fichero.escribirEn(out);
		return true;
		
	}

	@Override
	public boolean BorrarFicheroRepositorio(String nombreCliente, String ficheroBorrar) {
		File carpeta = new File(nombreCliente);
		File fichero = null;
		try {
			// fichero = new File("/" + carpeta.getCanonicalPath() + "/" + ficheroBorrar);
			fichero = new File("\\" + carpeta.getCanonicalPath() + "\\" + ficheroBorrar);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (fichero.delete())
			   return true;
		else
		return false;		
				
	}
	
	@Override
	public boolean DescargarFichero(String destino, String nombreCliente, String ficheroDescargar)throws IOException {	
		File carpeta = new File(nombreCliente);
		//File forigen = new File("/" + carpeta.getCanonicalPath() + "/" + ficheroDescargar);
		File forigen = new File("\\" + carpeta.getCanonicalPath() + "\\" + ficheroDescargar);
		if (!forigen.exists()) {
			return false;

		}
		File fdestino = new File(destino);
		
		try {

			InputStream in = new FileInputStream(forigen);
			OutputStream out = new FileOutputStream(fdestino);
			
			// c:\Users\Diego\Desktop\desc
		
			byte[] buf = new byte[1024];
			int len;
			 
			while ((len = in.read(buf)) > 0) {
			  out.write(buf, 0, len);
			}
			
			in.close();
			out.close();
			
		}/* catch (IOException e) {
			System.err.println("Error: directorio de destino '" + destino + "' no existe");
		}*/finally{}
		
		return true;
	}

	
	@Override
	public String ruta(String nombreCliente) throws RemoteException{
		File carpeta = new File(nombreCliente);
		// File destino = null;
		String ruta = "";

		File destino = null;
		try {
			// destino = new  File("/" + carpeta.getCanonicalPath() + "/");
			destino = new  File("\\" + carpeta.getCanonicalPath() + "\\");
		} catch (IOException e) {
			System.err.println("");
		} finally {}
		
		ruta = destino.getPath();
		
		return ruta;
	}



}