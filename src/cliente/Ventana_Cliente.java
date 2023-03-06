package cliente;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import server.Puntuacion;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Ventana_Cliente extends JFrame {

	private JPanel contentPane;
	private DefaultTableModel modelopuntuaciones=new DefaultTableModel(0,2);
	ObjectInputStream input;
	ObjectOutputStream output;
	ArrayList<Puntuacion> lista;
	Socket sck;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ventana_Cliente frame = new Ventana_Cliente();
					frame.setVisible(true);
					frame.iniciar();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Ventana_Cliente() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 473, 317);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Nombre");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(21, 26, 49, 14);
		contentPane.add(lblNewLabel);

		JLabel lblPuntuacin = new JLabel("Puntuación");
		lblPuntuacin.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPuntuacin.setBounds(21, 57, 65, 14);
		contentPane.add(lblPuntuacin);

		tf_nombre = new JTextField();
		tf_nombre.setBounds(96, 23, 330, 20);
		contentPane.add(tf_nombre);
		tf_nombre.setColumns(10);

		tf_puntuacion = new JTextField();
		tf_puntuacion.setColumns(10);
		tf_puntuacion.setBounds(96, 54, 96, 20);
		contentPane.add(tf_puntuacion);

		btnNewButton = new JButton("Añadir");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(tf_puntuacion.getText().trim().length()>0 && tf_nombre.getText().trim().length()>0) {
					try {
						Puntuacion p=new Puntuacion(tf_nombre.getText().trim(), Integer.valueOf(tf_puntuacion.getText().trim()));
						output.writeObject(p);
						output.flush();
					} catch (IOException e1) {e1.printStackTrace();}
				}else {
					JFrame jframe=new JFrame();
					JOptionPane.showMessageDialog(jframe, "Introduce Nombre y Puntuación");
				}
			}
		});
		btnNewButton.setBounds(308, 53, 89, 23);
		contentPane.add(btnNewButton);

		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(21, 110, 416, 159);
		contentPane.add(scrollPane);

		puntuaciones = new JTable();
		puntuaciones.setModel(new DefaultTableModel(
				new Object[][] {
					{null, null},
					{null, null},
					{null, null},
				},
				new String[] {
						"Nombre", "Puntos"
				}
				));
		scrollPane.setViewportView(puntuaciones);

		vaciar_e_inicilizar_tabla();


	}


	private JButton btnNewButton;
	private JScrollPane scrollPane;
	private JTextField tf_puntuacion;
	private JTable puntuaciones;
	private JTextField tf_nombre;

	public void vaciar_e_inicilizar_tabla() {
		int num_filas=modelopuntuaciones.getRowCount();
		for (int i=0;i<num_filas;i++) modelopuntuaciones.removeRow(0);
		String filaencabezado[]={"Nombre","Puntos"};
		modelopuntuaciones.setColumnIdentifiers(filaencabezado);
	}

	public void rellenar_datos_tabla(ArrayList<Puntuacion> lista) {
		int num_filas=modelopuntuaciones.getRowCount();
		for (int i=0;i<num_filas;i++) modelopuntuaciones.removeRow(0);
		String fila[]=new String[2];
		for(Puntuacion puntuacion : lista) {
			fila[0]=puntuacion.getNombre();
			fila[1]=String.valueOf(puntuacion.getPuntos());
			modelopuntuaciones.addRow(fila);
		}
		puntuaciones.setModel(modelopuntuaciones);
	}

	public void asignarLista(ArrayList<Puntuacion> lista) {
		this.lista=lista;
	}

	public Ventana_Cliente getthis() {
		return this;
	}
	
	public void iniciar() {
		try {
			sck=new Socket("localhost",5000);
			 output=new ObjectOutputStream(sck.getOutputStream());
			Hilo h=new Hilo(sck, getthis());
			h.start();

		} catch (Exception e) {e.printStackTrace();}
	
	}
	
}
