package clases;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import negocio.PeliculaRMI;

public class Servidor {
	public static void main(String[] args) {
		System.out.println("APLICACIÓN DE SERVIDOR MULTITAREA");
		System.out.println("----------------------------------");
		try {
			Registry registro = LocateRegistry.createRegistry(5000);
			//Objeto a compartir con cliente
			PeliculaRMI pelicula = new PeliculaRMI();
			//Objeto STUB para devolver al cliente
			registro.bind("misPeliculas", pelicula);
			
			ServerSocket servidor = new ServerSocket();
			//IP localhost para evitar adivinar IPs
			InetSocketAddress direccion = new InetSocketAddress("127.0.0.1",5020);
			servidor.bind(direccion);
			System.out.println("Servidor listo para aceptar solicitudes");
			System.out.println("Dirección IP: " + direccion.getAddress());
			//Creamos hilos por cada conexion con clientes diferentes, 
			while (true) {
				Socket enchufeAlCliente = servidor.accept();
				System.out.println("Comunicación entrante");
				new HiloEscuchador(enchufeAlCliente);
			}
		//Excepciones necesarias para la funciones usadas
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		}
	}
}
