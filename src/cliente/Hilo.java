package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import server.Puntuacion;

public class Hilo extends Thread{

	Socket sck;
	ObjectInputStream input;
	ObjectOutputStream output;
	Ventana_Cliente v;


	public Hilo(Socket sck,Ventana_Cliente v) {
		this.sck=sck;
		this.v=v;

	}

	public void run() {

		ObjectInputStream input;
		try {
			input = new ObjectInputStream(sck.getInputStream());
			while(true) {
				Integer tam=(Integer) input.readObject();
				ArrayList<Puntuacion> lista=new ArrayList<Puntuacion>();
						
				for (int i = 0; i <tam; i++) {
					Puntuacion puntuacion=(Puntuacion) input.readObject();
					lista.add(puntuacion);
				}
				
				System.out.println("Cliente: "+lista.size());
				v.asignarLista(lista);
				v.rellenar_datos_tabla(lista);
			}
		} catch (Exception e) {e.printStackTrace();} 

	}


}



