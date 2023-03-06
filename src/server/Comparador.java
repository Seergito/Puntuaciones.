package server;

import java.util.Comparator;

public class Comparador implements Comparator<Puntuacion> {

	@Override
	public int compare(Puntuacion o1, Puntuacion o2) {
		Integer puntos1=o1.getPuntos();
		Integer puntos2=o2.getPuntos();
		return puntos1.compareTo(puntos2);
	}
}
