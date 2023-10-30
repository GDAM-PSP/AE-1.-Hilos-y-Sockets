package clases;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class HiloEscuchador implements Runnable{
	private Thread hilo;
	private static int numCliente = 0;
	private Socket enchufeAlCliente;
	
	public HiloEscuchador(Socket socket) {
		//Creamos el nuevo hilo y lo ejecutamos
		numCliente++;
		hilo = new Thread(this,"Cliente: "+numCliente);
		this.enchufeAlCliente = socket;
		hilo.start();
	}
	
	@Override
	public void run() {
		System.out.println("Estableciendo conexión con "+hilo.getName());
		
		try {
			//Obtenemos los stream de entrada y salida
			InputStream entrada = enchufeAlCliente.getInputStream();
			OutputStream salida = enchufeAlCliente.getOutputStream();
			String texto = "";
			//Cerramos el hilo y conexion cuando el cliente decide salir
			while (!texto.trim().equals("F")) {
					byte[] mensaje = new byte[100];
					entrada.read(mensaje);
					texto = new String(mensaje);
					if (texto.trim().equals("F")) {
						salida.write("Hasta pronto, gracias por establecer conexi�n".getBytes());
						System.out.println(hilo.getName()+" ha cerrado la comunicaci�n");
					}
			}
			entrada.close();
			salida.close();

			enchufeAlCliente.close();
			
		} catch (IOException e) {
			System.err.println("HiloEscuchador: Error de entrada/salida");
			e.printStackTrace();
		}
	
		
		
	}

}
