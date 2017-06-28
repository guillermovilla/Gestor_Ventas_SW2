import java.awt.EventQueue;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.event.CaretListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.awt.BorderLayout;

public class SellWindow extends JFrame implements ActionListener{

	private JMenuBar mb;
	private JMenu mCaja, mAyuda;
	private JMenuItem m1, m2;
	
	private String cadenaCategoria;
	
	private JFrame frame;
	
	private JLabel idLabel;
	
	private JButton addButton;
	private JButton buyButton;
	private JButton deshacer;
	
	// private JList productList;
	private JTextArea areaDescripcion;
	private JTextArea areaPrecio;
	
	private JTextField idText;
	
	private JTextArea totalText;
	JScrollPane scrollpaneLista;
	
	private DBConection oDBConection;
	private LoginWindow oLoginWindow;
	private GeneratePDFFile oPDF;
	private Connection cn;

	private JScrollPane scrollPane;
	
	private JList listCat;
	private JList listProd;
	
	private DefaultListModel catModel;
	private DefaultListModel prodModel;

	private JComboBox tipoCombo;
	
	private Date fecha;
	private DateFormat hourFormat;
	private DateFormat dateFormat;
	
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
		
		float total = 0;
		String z = "";
		String d = "";
		String n = "";
		
		frame = new JFrame();
		frame.setBounds(700, 70, 900, 600);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.setTitle("Ventas");
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		idLabel = new JLabel("IDproducto");
		idLabel.setBounds(500, 80, 120, 20);
		frame.getContentPane().add(idLabel);
		
		idText = new JTextField();
		idText.setBounds(590, 80, 120, 20);
		frame.getContentPane().add(idText);
		
		addButton = new JButton("Agregar");
		addButton.setBounds(750, 80, 100, 20);
		frame.getContentPane().add(addButton);
		addButton.addActionListener(this);
		
		deshacer = new JButton("Deshacer");
		deshacer.setBounds(700, 430, 150, 20);
		frame.getContentPane().add(deshacer);
		deshacer.addActionListener(this);
		
//		productList = new JList();
//		productList.setBounds(80, 150, 500, 300);
//		frame.getContentPane().add(productList);
//		scrollpaneLista = new JScrollPane();
//		scrollpaneLista.setViewportView(productList);
		
		mb = new JMenuBar();
		frame.setJMenuBar(mb);
		mAyuda = new JMenu("Ayuda");
		mb.add(mAyuda);
		
		m2 = new JMenuItem("Acerca de ...");
		mAyuda.add(m2);
		
		areaDescripcion = new JTextArea();
		scrollPane = new JScrollPane(areaDescripcion);
		scrollPane.setBounds(500, 120, 200, 300);
		frame.getContentPane().add(scrollPane);
		
		areaPrecio = new JTextArea();
		scrollPane = new JScrollPane(areaPrecio);
		scrollPane.setBounds(700, 120, 150, 300);
		frame.getContentPane().add(scrollPane);
		
		
		totalText = new JTextArea("Precio Total: ");
		totalText.setBounds(700, 460, 150, 20);
		frame.getContentPane().add(totalText);
		
		buyButton = new JButton("Confirmar");
		buyButton.setBounds(700, 490, 150, 20);
		frame.getContentPane().add(buyButton);
		buyButton.addActionListener(this);
		
		oDBConection = new DBConection();
		cn = oDBConection.connect();
		
		listCat = new JList();
		listCat.setBounds(80, 120, 120, 300);
		catModel = new DefaultListModel();
		listCat.setModel(catModel);
		frame.getContentPane().add(listCat);
		
		listCat.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                	cadenaCategoria = (String) listCat.getSelectedValue();
                	llenarProducto();
            }
        });
		
		listProd = new JList();
		listProd.setBounds(220, 120, 180, 300);
		prodModel = new DefaultListModel();
		listProd.setModel(prodModel);
		frame.getContentPane().add(listProd);		

		listProd.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					String nombre = (String) listProd.getSelectedValue();
					String sentenciaSQL = "SELECT * FROM productos WHERE nombre = '" + nombre + "'";

					ResultSet rs;
					try {
						Statement st = cn.createStatement();
						rs = st.executeQuery(sentenciaSQL);
						while(rs.next()) {
							int nomb = rs.getInt("cantidad");
							if(nomb <= 0) {
								JOptionPane.showMessageDialog(null, "No se dispone de la suficiente cantidad de alguno de los productos!");
								break;
							}
							else {
								String c1 = (String) listProd.getSelectedValue();
								anadirVenta(c1, cadenaCategoria);
							}
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					
					
				}
			}
		});

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
	
	private void llenarProducto() {
		// TODO Auto-generated method stub
		prodModel.clear();
		
		String sentenciaSQL = "SELECT * FROM productos WHERE categoria = '" + cadenaCategoria + "'";
		String datos[]= new String[1];
		
		try {
			oDBConection.connect();
			
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sentenciaSQL);
			while(rs.next()) {
				datos[0] = rs.getString("nombre");
				prodModel.addElement(datos[0]);
			}		
		}
		catch(Exception e1) {
			e1.printStackTrace();
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
	}

	public void anadirVenta(String c, String categoria) {
			PreparedStatement inserta = null;
			
			try {
				float total = 0;
				inserta = cn.prepareStatement("INSERT INTO compra (nombre, precio) SELECT nombre, precio FROM productos WHERE nombre = '" + c + "'");
				Statement st = cn.createStatement();
				inserta.execute();

				String imprime = "SELECT * FROM compra";

				ResultSet rs = st.executeQuery(imprime);
				String z = "";
				String d = "";
				String n = "";
				while(rs.next()) {
					z += ("" + rs.getString("nombre") + "\n");
					areaDescripcion.setText(z);

					d += ("" + rs.getString("precio") + "€\n");
					areaPrecio.setText(d);

					total += (rs.getFloat("precio"));
					n = Float.toString(total);
					totalText.setText("Precio Total: " + n +"€");
					}
				}

			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

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
				Statement st = cn.createStatement();
				Statement st2 = cn.createStatement();
				String sentenciaSQL = "SELECT * FROM productos WHERE idproducto = '" + id + "'";
				try {
					float total = 0;
					int cant = 0;
					int cont = 0;

					ResultSet rs = st.executeQuery(sentenciaSQL);

					while(rs.next()) {
						cant = (rs.getInt("cantidad"));
						if(cant <= 0) {
							JOptionPane.showMessageDialog(null, "No se dispone de la suficiente cantidad de alguno de los productos!");
							break;
						}
						else {

							insertar = cn.prepareStatement("INSERT INTO compra (nombre, precio) SELECT nombre, precio FROM productos WHERE idproducto ='" + id + "'") ;
							insertar.execute();

							imprime = "SELECT * FROM compra";

							ResultSet rs2 = st2.executeQuery(imprime);
							String z = "";
							String d = "";
							String n = "";
							while(rs2.next()) {
								z += ("" + rs2.getString("nombre") + "\n");
								areaDescripcion.setText(z);

								d += ("" + rs2.getString("precio") + "€\n");
								areaPrecio.setText(d);

								total += (rs2.getFloat("precio"));
								n = Float.toString(total);
								totalText.setText("Precio Total: " + n +"€");
							}
						}
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
				
				fecha = new Date();
				dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				hourFormat = new SimpleDateFormat("HH:mm:ss");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String fechaBD = sdf.format(fecha);
			
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
					String categoriaDis = "";
					int cant;
					int r;						
					int v = LoginWindow.id;

					while(rs2.next()) {
						
						prod += (rs2.getString("nombre"));
						prod += ", ";
						total += (rs2.getFloat("precio"));
						productoDis = (rs2.getString("nombre"));

						n2 = "UPDATE productos SET cantidad = cantidad-1 WHERE nombre= '" + productoDis + "'";
						r = st2.executeUpdate(n2);

						n = Float.toString(total);	
					}
					insertar2 = cn.prepareStatement("INSERT INTO log (fecha, hora, productos, vendedor, precio) values ('" + fechaBD + "','" + hourFormat.format(fecha) + "','"+ prod + "', '" + v + "' ,'"+ total +"')") ;
					insertar2.execute();
					oPDF = new GeneratePDFFile();
					oPDF.creaPDF(fecha);
					
					JOptionPane.showMessageDialog(null, "Venta realizada con éxito");
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
		try {
			if(e.getSource() == deshacer) {
				oDBConection.connect();
			
				areaDescripcion.setText(null);
				areaPrecio.setText(null);
			
				String sentenciaSQL = "DELETE FROM compra ORDER BY idcompra DESC limit 1";
				String sentenciaSQL2 = "SELECT * FROM compra";
				
				Statement st = cn.createStatement();
				Statement st2 = cn.createStatement();
				
				st.execute(sentenciaSQL);
				
				ResultSet rs = st2.executeQuery(sentenciaSQL2);
				
				float total = 0;
				String z = "";
				String d = "";
				String n = "";
				while(rs.next()) {
					z += ("" + rs.getString("nombre") + "\n");
					areaDescripcion.setText(z);

					d += ("" + rs.getString("precio") + "€\n");
					areaPrecio.setText(d);

					total += (rs.getFloat("precio"));
					n = Float.toString(total);
					totalText.setText("Precio Total: " + n +"€");
					}
			}		
		}
		catch(Exception e1) {
			e1.printStackTrace();
		}
	}

	public Window getFrame() {
		// TODO Auto-generated method stub
		return frame;
	}

}
