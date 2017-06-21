import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BoxLayout;
import javax.swing.JTable;


public class VentanaInventario extends JFrame implements ActionListener {

	private JButton botonDisminuir;
	private JButton botonAumentar;
	private JButton botonEliminarProducto;

	private JButton botonAniadirProducto;
	private JButton botonMostrarInventario;
	private DBConection conexion;
	
	private JFrame frame;
	
	public VentanaInventario() {
		iniciarVentanaInventario();
	}
	
	void iniciarVentanaInventario(){
		
		frame = new JFrame();
		frame.setBounds(70, 580, 600, 400);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//frame.setLocationRelativeTo(null);
		frame.setTitle("Inventario");
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		JLabel lblUsuario = new JLabel("Opciones de Inventario: ");
		lblUsuario.setFont(new Font("Verdana", Font.BOLD, 14));
		lblUsuario.setBounds(40, 40, 251, 39);
		frame.getContentPane().add(lblUsuario);
		
		botonAniadirProducto = new JButton("Añadir Producto");
		botonAniadirProducto.setFont(new Font("Verdana", Font.PLAIN, 14));
		botonAniadirProducto.setBounds(50, 120, 160, 49);
		frame.getContentPane().add(botonAniadirProducto);
		botonAniadirProducto.addActionListener(this);
		
		
		botonEliminarProducto = new JButton("Eliminar Producto");
		botonEliminarProducto.setFont(new Font("Verdana", Font.PLAIN, 14));
		botonEliminarProducto.setBounds(50, 200, 160, 49);
		frame.getContentPane().add(botonEliminarProducto);
		botonEliminarProducto.addActionListener(this);
		
		botonAumentar = new JButton("Añadir Stock");
		botonAumentar.setFont(new Font("Verdana", Font.PLAIN, 14));
		botonAumentar.setBounds(390, 120, 160, 49);
		frame.getContentPane().add(botonAumentar);
		botonAumentar.addActionListener(this);
		
		botonDisminuir = new JButton("Retirar Stock");
		botonDisminuir.setFont(new Font("Verdana", Font.PLAIN, 14));
		botonDisminuir.setBounds(390, 200, 160, 49);
		frame.getContentPane().add(botonDisminuir);
		botonDisminuir.addActionListener(this);

		botonMostrarInventario = new JButton("Inventario");
		botonMostrarInventario.setFont(new Font("Verdana", Font.PLAIN, 14));
		botonMostrarInventario.setBounds(220, 300, 160, 49);
		frame.getContentPane().add(botonMostrarInventario);
		botonMostrarInventario.addActionListener(this);
		
		conexion = new DBConection();
		
		frame.setVisible(true);
		
	}
	
	public Window getFrame() {
		return frame;
	}
	
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == botonAniadirProducto) {
			conexion.connect();
		
			String claseProducto = JOptionPane.showInputDialog("¿Que desea añadir? (Camiseta, Zapatillas, Sudadera");
		
			if (claseProducto == null || claseProducto.equals("")){
				JOptionPane.showMessageDialog(null, "Registro no agregado: Introduzca un nombre");
			}else{
				int cantidad = Integer.parseInt(JOptionPane.showInputDialog("¿Cuantos productos quiere añadir?"));
				if (cantidad < 0){
					JOptionPane.showMessageDialog(null, "Registro no agregado: La cantidad debe ser almenos 1");
				}else {
			
					String precioString = JOptionPane.showInputDialog("Precio Venta : ");
					double precio = Double.parseDouble(precioString);
					if (precio<0){
						JOptionPane.showMessageDialog(null, "Registro no agregado: Precio incorrecto");
					}else{
		
			
						claseProducto = claseProducto.toLowerCase();


						try {
							conexion.aniadirProducto(claseProducto, precio, cantidad);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		}	

		if (e.getSource() == botonMostrarInventario) {

			conexion.connect();

			TablaInventario ventana = null;
			try {
				ventana = new TablaInventario();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}        
			ventana.setVisible(true);    

		}
		if (e.getSource() == botonAumentar) {

			conexion.connect();


			String productoAumentar = JOptionPane.showInputDialog("Introducta Descripcion del producto a aumentar");
			String cantidadAumentar = JOptionPane.showInputDialog("Introducta la cantidad de productos a añadir");

			if(Integer.parseInt(cantidadAumentar) < 1) {
				JOptionPane.showMessageDialog(null, "Introduce una cantidad mayor que 0");
			}
			else {



				try {
					conexion.aumentar(productoAumentar, cantidadAumentar);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}        
			}
		}
		if (e.getSource() == botonDisminuir) {
			conexion.connect();

			String productoDisminuir = JOptionPane.showInputDialog("Introducta Descripcion del producto a disminuir");
			String cantidadDisminuir = JOptionPane.showInputDialog("Introducta la cantidad de productos a retirar");

			if(Integer.parseInt(cantidadDisminuir) < 1) {
				JOptionPane.showMessageDialog(null, "Introduce una cantidad mayor que 0");
			}
			else {
				try {
					conexion.disminuir(productoDisminuir, cantidadDisminuir);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}      
			}

		}
	

		if (e.getSource() == botonEliminarProducto){
			String claseEliminar = JOptionPane.showInputDialog("Introducta Descripcion del producto a eliminar");
			conexion.connect();

			try {
				conexion.eliminarProducto(claseEliminar);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}