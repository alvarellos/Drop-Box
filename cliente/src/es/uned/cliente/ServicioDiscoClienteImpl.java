package es.uned.cliente;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import es.uned.common.*;

/**
 * @author Diego Diaz alvarellos
 * email: ddiaz136@uned.estudiante.es
 * clase que implementa la interfaz remota 
 * "ServicioDiscoClienteInterface"
 */
public class ServicioDiscoClienteImpl extends UnicastRemoteObject implements ServicioDiscoClienteInterface{

	protected ServicioDiscoClienteImpl() throws RemoteException {
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}

}
