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
import java.util.concurrent.TimeUnit;

import clases.Pelicula;
import interfaces.PeliculaInterfaceRMI;

public class ClientePeliculaRMI {
	public static void main(String[] args) {
		//Inicializamos el registry
		Registry registry;
		//Inicializamos el scanner de input
		Scanner lector = new Scanner(System.in);
		try (Socket cliente = new Socket()) {
		//IP localhost para no tener que adivinar IPs
		InetSocketAddress direccionServidor = new InetSocketAddress("127.0.0.1",5020);
		//Nos conectamos al socket en el host y puerto indicados
		cliente.connect(direccionServidor);
		System.out.println("Comunicación establecida con el servidor");
		//Obtenemos los stream de entrada y salida de la conexion
		InputStream entrada = cliente.getInputStream();
		OutputStream salida = cliente.getOutputStream();
		try {	
			System.out.println("Esperando a que el servidor acepte la conexi�n");
			//Nos conectamos y obtenemos el registro en el host y puerto indicados
			registry = LocateRegistry.getRegistry("127.0.0.1", 5000);
			System.out.println("Hemos obtenido el registro");
			//Obtenemos el objeto del servidor (STUB)
			PeliculaInterfaceRMI peliculas = (PeliculaInterfaceRMI) registry.lookup("misPeliculas");
			System.out.println("Hemos obtenido el objeto remoto");
			System.out.println();
			
			String texto_usuario;
			String opcion;
			
			//Hacemos un loop mientras no se haya salido del programa
			do {
				//Mostramos las opciones
				escribirMenu(); 
				opcion = lector.nextLine().toUpperCase();
				salida.write(opcion.getBytes());
				//Dependiendo de la opcion vamos al evento adecuado
				switch (opcion) {
					case "I":
						System.out.println("Escribe el id (num�rico) de la canci�n: ");
						texto_usuario = lector.nextLine();
						//Consultamos por ID con la interfaz RMI
						String titulo = peliculas.consultarID(Integer.parseInt(texto_usuario));
						
						if(!titulo.isEmpty())
							System.out.println(titulo);
						else
							System.out.println("No se ha encontrado la pel�cula indicada.");
						TimeUnit.SECONDS.sleep(2);
						break;
					case "T":
						System.out.println("Escribe t�tulo canci�n: ");
						texto_usuario = lector.nextLine();
						//Consultamos por titulo con la interfaz RMI
						titulo = peliculas.consultarTitulo(texto_usuario);
						
						if(!titulo.isEmpty())
							System.out.println(titulo);
						else
							System.out.println("No se ha encontrado la pel�cula indicada.");
						TimeUnit.SECONDS.sleep(2);
						break;
					case "D":
						System.out.println("Escribe el nombre del director: ");
						texto_usuario = lector.nextLine();
						//Consultamos por director con la interfaz RMI
						System.out.println(peliculas.consultarPeliculasPorDirector(texto_usuario));//SIGUE SIENDO EL OBJETO STUB
						TimeUnit.SECONDS.sleep(2);
						break;
					case "A":
						System.out.println("Introduce el t�tulo: ");
						titulo = lector.nextLine();
						System.out.println("Introduce el director: ");
						String director = lector.nextLine();
						System.out.println("Introduce el precio: ");
						Double precio = Double.parseDouble(lector.nextLine());
						//Creamos el objeto pelicula para que sea insertado con los datos obtenidos
						Pelicula pelicula = new Pelicula(0, titulo, director, precio);
						System.out.println(peliculas.AnadirPelicula(pelicula));
						System.out.println("Se ha creado la pel�cula.");
						TimeUnit.SECONDS.sleep(2);
						break;
					case "F":
						System.out.println("Programa finalizado");
						break;
					default:
						System.out.println("Opci�n incorrecta");
				}
			}while (!opcion.equals("F"));
			//Si el usuario decide cerrar el cliente se lo comunicamos al servidor
			salida.write("salir".getBytes());
			byte[] mensaje = new byte[100];
			entrada.read(mensaje);
			String texto = new String(mensaje);
			System.out.println(texto.trim());
			//Cerramos todas las conexiones con el servidor y los streams
			entrada.close();
			salida.close();
			cliente.close();
			//Excepciones necesarias para las funciones usadas.
		} catch (RemoteException | NotBoundException e) {
			System.out.println(e.getMessage());
		} catch (InterruptedException e) {
			e.printStackTrace();
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
		System.out.println("I - Consultar pel�cula por ID");
		System.out.println("T - Consultar pel�cula por t�tulo");
		System.out.println("D - Consultar pel�culas por director");
		System.out.println("A - Añadir pel�cula");
		System.out.println("F - Salir de la aplicaci�n");
		System.out.println("--------------------------");
		System.out.println("Elija opci�n:");
		}
}
