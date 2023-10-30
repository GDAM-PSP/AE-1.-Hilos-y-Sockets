package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import clases.Pelicula;

public interface PeliculaInterfaceRMI extends Remote{
	public String consultarID(int id) throws RemoteException;
	public String consultarTitulo(String titulo) throws RemoteException;
	public String consultarPeliculasPorDirector(String director) throws RemoteException;
	public String AnadirPelicula(Pelicula pelicula) throws RemoteException;
	
}