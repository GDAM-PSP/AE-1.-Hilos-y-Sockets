package clases;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class HiloEscuchador implements Runnable{
	private Thread hilo;
	private static int numCliente = 0;
	private Socket enchufeAlCliente;
	
	public HiloEscuchador(Socket socket) {
		numCliente++;
		hilo = new Thread(this,"Cliente: "+numCliente);
		this.enchufeAlCliente = socket;
		hilo.start();
	}
	
	@Override
	public void run() {
		System.out.println("Estableciendo conexión con "+hilo.getName());
		PrintStream salida = null;
		InputStreamReader entrada = null;
		BufferedReader entradaBuffer = null;
		
		
		try {
			//Salida del servidor al cliente
			salida = new PrintStream(enchufeAlCliente.getOutputStream());
			//Entrada del servidor al cliente
			entrada = new InputStreamReader(enchufeAlCliente.getInputStream());
			entradaBuffer = new BufferedReader(entrada);
			
			String texto = "";
			boolean continuar = true;
			
			//Recorremos el bucle hasta que el cliente decida salir de la aplicación
			while(continuar) {
				texto = entradaBuffer.readLine();
				
			//Si el cliente escribe "salir" dejamos de recorrer el bucle
				if (texto.trim().equalsIgnoreCase("Salir")){
					salida.println("Saliendo...");
					System.out.println(hilo.getName() + " ha cerrado la comunicacion");
					continuar = false;
				}
					
			}
			//Cerramos el socket
			enchufeAlCliente.close();
			
		} catch (IOException e) {
			System.err.println("HiloEscuchador: Error de entrada/salida");
			e.printStackTrace();
		}
	
		
		
	}

}
