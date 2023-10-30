package main;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import negocio.MusicaRMI;

public class Servidor {
	public static void main(String[] args) {
		String host;
		int puerto = 5055;
		/*
		 * En esta l�nea averiguamos program�ticamente la direcci�n IP de 
		 * la red local de la m�quina que aloja el programa servidor. 
		 * Dicha direcci�n IP la guardamos en la variable host. 
		 * Tambi�n podr�amos haber asignado directamente la cadena "localhost". 
		 * 
		 * El m�todo getHostAddress() puede desencadenar una excepci�n de tipo 
		 * UnknownHostException si por alguna circunstancia no puede averiguar 
		 * la direcci�n IP local.
		 * 
		 * Esto solo se usa para mostrar en el log de arranque la ip de nuestro
		 * servidor
		 */
		host = "127.0.0.1";//localhost, para no tener que adivinar IPs
		
		try {
			/*
			 * Con esta l�nea estamos creando el registro de objetos remotos. 
			 * Los objetos que se inscriban en dicho registro quedar�n a 
			 * disposici�n de los clientes a partir de la direcci�n IP del 
			 * servidor y el n�mero de puerto especificado en el argumento del 
			 * m�todo createRegistry. 
			 * Observa que m�s arriba hemos establecido la variable puerto con 
			 * el valor 5055.
			 */
			Registry registro = LocateRegistry.createRegistry(puerto);
			/*
			 * Aqu� creamos el objeto que deseamos compartir y que conforma 
			 * la l�gica del servicio prestado.
			 */
			MusicaRMI musica = new MusicaRMI();
			
			/*
			 * Nuestro registro de objetos remotos dispone del m�todo rebind, 
			 * que nos permitir� inscribir el nuevo objeto dentro de �l para 
			 * que los clientes puedan obtener el stub. Como primer argumento 
			 * indicamos un identificador para el nuevo objeto. Los clientes que 
			 * deseen acceder al objeto remoto necesitar�n conocer este identificador.
			 */
			registro.rebind("miMusica", musica);
			
			System.out.println("Servicio registrado en host " + host + " y puerto " + puerto);
		} catch (RemoteException e) {
			System.out.println("No se ha podido registrar el servicio");
			System.out.println(e.getMessage());
		}
		/*
		 * Tambien seria posible eliminar el objeto programaticamente
		 * mediante la siguiente linea
		 * UnicastRemoteObject.unexportObject(musica, true);
		 */
	}
}
