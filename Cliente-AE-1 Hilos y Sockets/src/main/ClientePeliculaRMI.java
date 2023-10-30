package main;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import beans.Pelicula;
import interfaces.PeliculaInterfaceRMI;

public class ClientePeliculaRMI {
	public static void main(String[] args) {
		Registry registry;
		Scanner lector = new Scanner(System.in);
		try (Socket cliente = new Socket()) {
		InetSocketAddress direccionServidor = new InetSocketAddress("127.0.0.1",5020);
		cliente.connect(direccionServidor);
		System.out.println("Comunicación establecida con el servidor");
		InputStream entrada = cliente.getInputStream();
		OutputStream salida = cliente.getOutputStream();
		try {	
			System.out.println("Esperando a que el servidor acepte la conexión");
			registry = LocateRegistry.getRegistry("127.0.0.1", 5000);
			System.out.println("Hemos obtenido el registro");
			PeliculaInterfaceRMI peliculas = (PeliculaInterfaceRMI) registry.lookup("misPeliculas");
			System.out.println("Hemos obtenido el objeto remoto (STUB)");
			System.out.println();
			
			String texto_usuario;
			String opcion;

			do {
				escribirMenu(); 
				opcion = lector.nextLine().toUpperCase();
				switch (opcion) {
					case "I":
						System.out.println("Escribe el id (numérico) de la canción: ");
						texto_usuario = lector.nextLine();
						String titulo = peliculas.consultarID(Integer.parseInt(texto_usuario));
						
						if(!titulo.isEmpty())
							System.out.println(titulo);
						else
							System.out.println("No se ha encontrado la película indicada.");
						break;
					case "T":
						System.out.println("Escribe título canción: ");
						texto_usuario = lector.nextLine();
						titulo = peliculas.consultarTitulo(texto_usuario);
						
						if(!titulo.isEmpty())
							System.out.println(titulo);
						else
							System.out.println("No se ha encontrado la película indicada.");
						break;
					case "D":
						System.out.println("Escribe el nombre del director: ");
						texto_usuario = lector.nextLine();
						System.out.println(peliculas.consultarPeliculasPorDirector(texto_usuario));//SIGUE SIENDO EL OBJETO STUB
						break;
					case "A":
						System.out.println("Introduce los datos de la película a añadir: ");
						titulo = lector.nextLine();
						String director = lector.nextLine();
						Double precio = Double.parseDouble(lector.nextLine());
						Integer id = Integer.parseInt(lector.nextLine());
						Pelicula pelicula = new Pelicula(id, titulo, director, precio);
						System.out.println(peliculas.AnadirPelicula(pelicula));
						break;
					case "F":
						System.out.println("Programa finalizado");
						break;
					default:
						System.out.println("Opción incorrecta");
				}
			}while (!opcion.equals("F"));
			salida.write("salir".getBytes());
			byte[] mensaje = new byte[100];
			entrada.read(mensaje);
			String texto = new String(mensaje);
			System.out.println(texto);
			entrada.close();
			salida.close();
			cliente.close();
		} catch (RemoteException | NotBoundException e) {
			System.out.println(e.getMessage());
		}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		lector.close();
		
		
	}
	private static void escribirMenu() {
		System.out.println("--------------------------");
		System.out.println("Búsqueda de peliculas");
		System.out.println("--------------------------");
		System.out.println("I - Consultar película por ID");
		System.out.println("T - Consultar película por título");
		System.out.println("D - Consultar películas por director");
		System.out.println("A - Añadir película");
		System.out.println("F - Salir de la aplicación");
		System.out.println("--------------------------");
		System.out.println("Elija opción:");
		}
}