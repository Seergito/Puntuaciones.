package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;


public class Hilo extends Thread {

	Socket sck;
	int cliente;
	Compartida compartida; //PARA ENVIAR A TODOS

	public Hilo(Socket sck,Compartida compartida) {
		this.sck=sck;
		this.compartida=compartida;
	}

	@Override
	public void run() {

		try {
			ObjectInputStream input =new ObjectInputStream(sck.getInputStream());
			ObjectOutputStream output=new ObjectOutputStream(sck.getOutputStream());
			compartida.añadirSalida(output);


			while(true) {
				Puntuacion p=(Puntuacion) input.readObject();
				if(p.getNombre().equals("*")) {
					compartida.quitarSalida(output);
					break;
				}else {
					System.out.println("PUNTUACION NUEVA");
					compartida.añadirPuntuacion(p);
					compartida.EnviarPuntuaciones();
				}
			}


		} catch (Exception e) {e.printStackTrace();}

	}

}

