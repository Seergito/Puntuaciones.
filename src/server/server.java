package server;

import java.net.ServerSocket;
import java.net.Socket;


public class server {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ServerSocket sv_sck;
		Socket sck;
		Compartida c=new Compartida();
		try {
			sv_sck=new ServerSocket(5000);
			System.out.println("Servidor esperando...");
			
			while(true) {
				sck=sv_sck.accept();
				Hilo h=new Hilo(sck,c);
				h.start();
					
			}
			
		} catch (Exception e) {e.printStackTrace();}
		

	}

}
