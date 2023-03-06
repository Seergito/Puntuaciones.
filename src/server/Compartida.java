package server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Compartida {

	
	ArrayList<ObjectOutputStream> listaoutput;
	ArrayList<Puntuacion> listaPuntos;
	Scanner sc;

	public Compartida() {
		this.listaoutput=new ArrayList<ObjectOutputStream>();
		this.listaPuntos=new ArrayList<Puntuacion>();
	}
	
	
	public synchronized void EnviarPuntuaciones() {
		Comparador c=new Comparador();
		Collections.sort(listaPuntos,c);
		

		for (ObjectOutputStream salida : listaoutput) {   //RECORRE ARRAY Y LO ENVIA
			try {
				salida.writeObject(listaPuntos.size());
				salida.flush();
				for (Puntuacion puntuacion : listaPuntos) {
					System.out.println(puntuacion);
					salida.writeObject(puntuacion);
					salida.flush();
				}
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private synchronized void rellenarLista(){
		BufferedReader ficheroentrada=null;
		try {
			
			ficheroentrada=new BufferedReader(new FileReader("punutaciones.csv"));
	
		sc=new Scanner(ficheroentrada);
		sc.useDelimiter("/");
		
		while(sc.hasNext()) {
			int punto=sc.nextInt();
			String nombre=sc.next();
			Puntuacion p=new Puntuacion(nombre, punto);
			listaPuntos.add(p);
		}
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			sc.close();
			try {
				ficheroentrada.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void añadirSalida(ObjectOutputStream salida) { //AÑADEN AL CODIGO LA LISTA DE SALIDA
		listaoutput.add(salida);
	}
	
	public synchronized void añadirPuntuacion(Puntuacion p) { //AÑADEN AL CODIGO LA LISTA DE PUNTUACION
		listaPuntos.add(p);
	}
	
	public synchronized void quitarSalida(ObjectOutputStream salida) {
		listaoutput.remove(salida);
	}
	
	
}
