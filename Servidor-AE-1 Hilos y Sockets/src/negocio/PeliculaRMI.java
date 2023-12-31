package negocio;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import clases.Pelicula;
import interfaces.PeliculaInterfaceRMI;

public class PeliculaRMI extends UnicastRemoteObject implements PeliculaInterfaceRMI {
	
	private ArrayList<Pelicula> peliculas;
	private int id=5;
	public PeliculaRMI() throws RemoteException {
		peliculas = new ArrayList<Pelicula>();
		peliculas.add(new Pelicula(1, "Taxi Driver", "Martin Scorsese", 20.52));
		peliculas.add(new Pelicula(2, "El Apartamento", "Billy Wilder", 15.16));
		peliculas.add(new Pelicula(3, "La lista de Schindler", "Steven Spielberg", 14.99));
		peliculas.add(new Pelicula(4, "El séptimo sello", "Ingmar Bergman", 9.99));
		peliculas.add(new Pelicula(5, "La dolce vita", "Federico Fellini", 22.99));
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
