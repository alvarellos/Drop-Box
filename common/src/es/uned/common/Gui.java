package es.uned.common;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Diego Diaz alvarellos
 * email: ddiaz136@uned.estudiante.es
 *
 */
public class Gui {
	
	private static Console console = System.console();
	private static BufferedReader reader = new BufferedReader(
											new InputStreamReader(System.in));
	
	public static String[] input(String name, String[] msgs) {
		String[] inputs = new String[msgs.length];
		
		outLn("=== " + name + " ===");
		
		for (int i = 0; i < msgs.length; i++) {
			inputs[i] = input(msgs[i]);
		}
		
		return inputs;
	}
	
	public static String input(String name, String msg) {
		outLn("=== " + name + " ===");
		
		return input(msg);
	}
	
	public static String input(String msg) {
		
		out(msg);
		String line = readLine();
		
		newLine();
		
		return line;
	}
	
	public static int menu(String name, String[] entradas) {
		outLn("=== " + name + " ===");
		outLn("Seleccione la opcion.\n");

		for (int i = 0; i < entradas.length; i++) {
			outLn((i + 1) + ".- " + entradas[i]);
		}

		String entrada;
		int opt = -1;
		do {

			entrada = readLine().trim();
			if (Gui.isNumeric(entrada)){
				
				opt = Integer.parseInt(entrada);

				if (opt - 1 >= entradas.length || opt <= 0) {
					outLn("Ingrese una opcion del 1 al " + entradas.length);
					opt = -1;
				}
			} else {
				System.err.println("Ingrese una opción del 1 al " + entradas.length);
			}

		}
		while(opt == -1);
		

		
		newLine();

		return opt - 1;


	}
	
	private static boolean isNumeric(String cadena){
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
	
	private static void outLn(String msg) {
		System.out.println(msg);
	}
	
	private static void newLine() {
		System.out.println();
	}
	
	private static void out(String msg) {
		System.out.print(msg);
	}
	
	private static String readLine() {
		if (console != null) return console.readLine();
		
		try {
			return reader.readLine();
		} 
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}