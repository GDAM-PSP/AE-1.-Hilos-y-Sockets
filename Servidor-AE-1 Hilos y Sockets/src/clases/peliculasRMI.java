package clases;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import interfaces.consultas;

public class peliculasRMI extends UnicastRemoteObject implements consultas {
	
	private ArrayList<Pelicula> peliculas;
	private int id=5;
	public peliculasRMI() throws RemoteException {
		peliculas = new ArrayList<Pelicula>();
		peliculas.add(new Pelicula(1, "Martin Scorsese", "Taxi Driver", 20.52));
		peliculas.add(new Pelicula(2, "Billy Wilder", "El Apartamento", 15.16));
		peliculas.add(new Pelicula(3, "Steven Spielberg", "La lista de Schindler", 14.99));
		peliculas.add(new Pelicula(4, "Ingmar Bergman", "El s�ptimo sello", 9.99));
		peliculas.add(new Pelicula(5, "Federico Fellini", "La dolce vita", 22.99));
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public String consultarID(int id) throws RemoteException {
		String resultado = "";
		for(Pelicula p : peliculas) {
			if(p.getId()==id) {
				resultado = resultado + p + "\n";
			}
		}
		return resultado;
	}

	@Override
	public String consultarTitulo(String titulo) throws RemoteException {
		String resultado = "";
		for(Pelicula p : peliculas) {
			if(p.getTitulo().contains(titulo)) {
				resultado = resultado + p + "\n";
			}
		}
		return resultado;
	}

	@Override
	public String consultarPeliculasPorDirector(String director) throws RemoteException {
		String resultado = "";
		for(Pelicula p : peliculas) {
			if(p.getDirector().contains(director)) {
				resultado = resultado + p + "\n";
			}
		}
		return resultado;
	}

	@Override
	public synchronized String AnadirPelicula(Pelicula pelicula) throws RemoteException {
		this.id++;
		String resultado = "";
		pelicula.setId(id);
		peliculas.add(pelicula);
		resultado = resultado + "La pelicula se le a asignado el id "+this.id+" y a sido a�adida como "+pelicula;
		return resultado;
	}
	
}
