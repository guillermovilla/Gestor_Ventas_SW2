import java.awt.EventQueue;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;

import java.awt.BorderLayout;

public class SellWindow extends JFrame implements ActionListener{

	private JFrame frame;
	
	private JLabel idLabel;
	
	private JButton addButton;
	private JButton buyButton;
	
	// private JList productList;
	private JTextArea areaDescripcion;
	private JTextArea areaPrecio;
	
	private JTextField idText;
	
	private JTextArea totalText;
	JScrollPane scrollpaneLista;
	
	private DBConection oDBConection;
	private Connection cn;

	private JScrollPane scrollPane;
	
	private JList listCat;
	private JList listProd;
	
	private DefaultListModel catModel;
	private DefaultListModel prodModel;

	private JComboBox tipoCombo;
	
	/**
	 * Create the application.
	 */
	public SellWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(700, 70, 900, 550);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.setTitle("Ventas");
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
//		idLabel = new JLabel("IDproducto");
//		idLabel.setBounds(80, 80, 120, 20);
//		frame.getContentPane().add(idLabel);
//		
//		idText = new JTextField();
//		idText.setBounds(170, 80, 150, 20);
//		frame.getContentPane().add(idText);
//		
//		addButton = new JButton("Agregar");
//		addButton.setBounds(350, 80, 100, 20);
//		frame.getContentPane().add(addButton);
//		addButton.addActionListener(this);
		
//		productList = new JList();
//		productList.setBounds(80, 150, 500, 300);
//		frame.getContentPane().add(productList);
//		scrollpaneLista = new JScrollPane();
//		scrollpaneLista.setViewportView(productList);
		
		areaDescripcion = new JTextArea();
		scrollPane = new JScrollPane(areaDescripcion);
		scrollPane.setBounds(80, 120, 200, 300);
		frame.getContentPane().add(scrollPane);
		
		areaPrecio = new JTextArea();
		scrollPane = new JScrollPane(areaPrecio);
		scrollPane.setBounds(300, 120, 150, 300);
		frame.getContentPane().add(scrollPane);
		
		
		totalText = new JTextArea("Precio Total: ");
		totalText.setBounds(220, 430, 230, 20);
		frame.getContentPane().add(totalText);
		
		buyButton = new JButton("Confirmar");
		buyButton.setBounds(300, 460, 150, 20);
		frame.getContentPane().add(buyButton);
		buyButton.addActionListener(this);
		
		listCat = new JList();
		listCat.setBounds(500, 120, 150, 300);
		catModel = new DefaultListModel();
		listCat.setModel(catModel);
		frame.getContentPane().add(listCat);
		
		listProd = new JList();
		listProd.setBounds(700, 120, 150, 300);
		prodModel = new DefaultListModel();
		listProd.setModel(prodModel);
		frame.getContentPane().add(listProd);
		
		oDBConection = new DBConection();
		cn = oDBConection.connect();
		
		String[] cat = {"Partes de arriba", "Partes de abajo", "Calzado", "Accesorios"};
		
		tipoCombo = new JComboBox(cat);
		tipoCombo.setSelectedIndex(0);
		tipoCombo.setBounds(80, 80, 170, 25);
		frame.getContentPane().add(tipoCombo);
		tipoCombo.addActionListener(this);
		
		PreparedStatement drop = null;
		try {
			drop = cn.prepareStatement("drop table compra");
			//aux = cn.prepareStatement("INSERT INTO compra (idcompra) values (1)");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			drop.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PreparedStatement createTab = null;
		PreparedStatement aux = null;
		try {
			createTab = cn.prepareStatement("create table compra (idcompra int(11) auto_increment ,nombre varchar(30), marca varchar(30),precio float,cantidad int, primary key (idcompra))");
			//aux = cn.prepareStatement("INSERT INTO compra (idcompra) values (1)");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			createTab.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void llenarCat(String c) {
		// TODO Auto-generated method stub
		String categorias = c;
		
		if(categorias.equals("Partes de arriba")) {
			catModel.addElement("camisetas");
			catModel.addElement("sudaderas");
			catModel.addElement("camisas");
			catModel.addElement("punto");
		}
		if(categorias.equals("Partes de abajo")) {
			catModel.addElement("jeans");
			catModel.addElement("pantalones");
			catModel.addElement("bermudas");
		}
		if(categorias.equals("Calzado")) {
			catModel.addElement("zapatos");
			catModel.addElement("zapatillas");
			catModel.addElement("botas");
		}
		if(categorias.equals("Accesorios")) {
			catModel.addElement("relojes");
			catModel.addElement("pulseras");
			catModel.addElement("otros");
		}
//		String sentenciaSQL = "select * from categorias";
//		String datos[]= new String[1];
//		
//		try {
//			oDBConection.connect();
//			Statement st = cn.createStatement();
//			ResultSet rs = st.executeQuery(sentenciaSQL);
//			while(rs.next()) {
//				datos[0] = rs.getString("nombre");
//				catModel.addElement(datos[0]);
//			}		
//		}
//		catch(Exception e1) {
//			
//		}
	}

	public void actionPerformed(ActionEvent e) {
		
		try {
			if(e.getSource() == addButton) {	
				oDBConection.connect();
		
				String id;
				String DatoCapturado;
				id = idText.getText();

				String descr = "";
				String imprime = "";
				String precioTotal = "";
				PreparedStatement insertar = null;
				Statement st2 = cn.createStatement();
				try {
					float total = 0;
					
//					descr ="SELECT * FROM productos WHERE idproducto ='" + id + "'";
					
					
					insertar = cn.prepareStatement("INSERT INTO compra (descripcion, precio) SELECT descripcion, precio FROM productos WHERE idproducto ='" + id + "'") ;
					insertar.execute();
					
					imprime = "SELECT * FROM compra";
					
					ResultSet rs2 = st2.executeQuery(imprime);
					String z = "";
					String d = "";
					String n = "";
					while(rs2.next()) {
						z += ("" + rs2.getString("descripcion") + "\n");
						areaDescripcion.setText(z);
						
						d += ("" + rs2.getString("precio") + "�\n");
						areaPrecio.setText(d);
						
						total += (rs2.getFloat("precio"));
						n = Float.toString(total);
						totalText.setText("Precio Total: " + n +"�");
					}
				}
				catch(Exception e2) {

				}
			}
		}
		catch(Exception e1) {

		}
		try {
			if(e.getSource() == buyButton) {
			
				PreparedStatement insertar2 = null;
				Statement st2 = cn.createStatement();
				String productos2 = "";
				try {
					float total = 0;

					productos2 = "SELECT * FROM compra";

					ResultSet rs2 = st2.executeQuery(productos2);
					
					String prod = "";
					String prec = "";
					String n = "";
					String n2 = "";
					String productoDis = "";
					int r;									
					
					while(rs2.next()) {
						prod += (rs2.getString("descripcion"));
						prod += ", ";
						total += (rs2.getFloat("precio"));
						productoDis = (rs2.getString("descripcion"));
						
						n2 = "UPDATE productos SET cantidad=cantidad-1 WHERE descripcion= '" + productoDis + "'";
						r = st2.executeUpdate(n2);
						
						n = Float.toString(total);	
					}	
					insertar2 = cn.prepareStatement("INSERT INTO log (productos, precio) values ('" + prod + "', '"+ total +"')") ;
					insertar2.execute();
					
					JOptionPane.showMessageDialog(null, "Venta realizada con �xito");
					frame.setVisible(false);

				}
				catch(Exception e4) {

				}
			}
			
			if(e.getSource() == tipoCombo) {
				catModel.clear();
				String x = (String) tipoCombo.getSelectedItem();
				llenarCat(x);
			}
		}
		catch(Exception e3) {
		}	
	}

	public Window getFrame() {
		// TODO Auto-generated method stub
		return frame;
	}

}
