package clases;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Servidor {
	public static void main(String[] args) {
		System.out.println("APLICACI�N DE SERVIDOR MULTITAREA");
		System.out.println("----------------------------------");
		try {
			Registry registro = LocateRegistry.createRegistry(5000);
			peliculasRMI pelicula = new peliculasRMI();
			registro.bind("misPeliculas", pelicula);
			
		ServerSocket servidor = new ServerSocket();
		InetSocketAddress direccion = new InetSocketAddress("127.0.0.1",5020);//localhost, para no tener que adivinar IPs
		servidor.bind(direccion);
		System.out.println("Servidor listo para aceptar solicitudes");
		System.out.println("Direcci�n IP: " + direccion.getAddress());
		while (true) {
		Socket enchufeAlCliente = servidor.accept();
		System.out.println("Comunicaci�n entrante");
		new HiloEscuchador(enchufeAlCliente);
		}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
