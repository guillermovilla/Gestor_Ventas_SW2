import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class InventoryWindow extends JFrame implements ActionListener {

	private DBConection oDBConection;
	private Connection cn;
	
	private String cadenaCategoria;
	private String cadenaProducto;
	
	private JFrame frame;
	
	private JTextField cantidad;
	private JTextField nProducto;
	
	private JMenuBar mb;
	private JMenu mAyuda;
	private JMenuItem ma;
	
	private JList listaCategoria;
	private JList listaProd;
	private JList listaCant;
	
	private JTextField lNomb, lCat, lPrec, lCant;
	
	private DefaultListModel categoriaModel;
	private DefaultListModel prodModel;
	private DefaultListModel cantModel;
	
	private JButton add, quit, anadir, eliminar;
	
	private JLabel nombreL, nombreL2, cantL, cantL2, precioL, categoriaL;
	
	public InventoryWindow() {
		initialize();
	}

	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 600);
		frame.setTitle("Inventario");
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		mb = new JMenuBar();
		frame.setJMenuBar(mb);
		mAyuda = new JMenu("Ayuda");
		mb.add(mAyuda);
		
		ma = new JMenuItem("Instrucciones");
		mAyuda.add(ma);
		
		nombreL2 = new JLabel("Nombre");
		nombreL2.setBounds(40, 100, 120, 20);
		frame.getContentPane().add(nombreL2);
		
		categoriaL = new JLabel("Categoria");
		categoriaL.setBounds(40, 160, 120, 20);
		frame.getContentPane().add(categoriaL);
		
		precioL = new JLabel("Precio");
		precioL.setBounds(40, 220, 120, 20);
		frame.getContentPane().add(precioL);
		
		cantL2 = new JLabel("Cantidad");
		cantL2.setBounds(40, 280, 120, 20);
		frame.getContentPane().add(cantL2);
		
		lNomb = new JTextField();
		lNomb.setBounds(40, 120, 200, 25);
		frame.getContentPane().add(lNomb);
		
		lCat = new JTextField();
		lCat.setBounds(40, 180, 200, 25);
		frame.getContentPane().add(lCat);
		
		lPrec = new JTextField();
		lPrec.setBounds(40, 240, 200, 25);
		frame.getContentPane().add(lPrec);
		
		lCant = new JTextField();
		lCant.setBounds(40, 300, 200, 25);
		frame.getContentPane().add(lCant);
		
		anadir = new JButton("Añadir");
		anadir.setBounds(40, 350, 80, 25);
		frame.getContentPane().add(anadir);
		anadir.addActionListener(this);
		
		eliminar = new JButton("Eliminar");
		eliminar.setBounds(130, 350, 80, 25);
		frame.getContentPane().add(eliminar);
		eliminar.addActionListener(this);
		
		nombreL = new JLabel("Nombre");
		nombreL.setBounds(750, 115, 100, 20);
		frame.getContentPane().add(nombreL);
		
		cantL = new JLabel("Cantidad");
		cantL.setBounds(750, 165, 100, 20);
		frame.getContentPane().add(cantL);
		
		cantidad = new JTextField();
		cantidad.setBounds(750, 185, 100, 25);
		frame.getContentPane().add(cantidad);
		
		nProducto = new JTextField();
		nProducto.setBounds(750, 135, 100, 25);
		frame.getContentPane().add(nProducto);
		
		add = new JButton("+");
		add.setBounds(750, 220, 50, 30);
		frame.getContentPane().add(add);
		add.addActionListener(this);
		
		quit = new JButton("-");
		quit.setBounds(800, 220, 50, 30);
		frame.getContentPane().add(quit);
		quit.addActionListener(this);
		
		listaCategoria = new JList();
		listaCategoria.setBounds(380, 120, 120, 300);
		categoriaModel = new DefaultListModel();
		listaCategoria.setModel(categoriaModel);
		frame.getContentPane().add(listaCategoria);	
		
		listaProd = new JList();
		listaProd.setBounds(510, 120, 160, 300);
		prodModel = new DefaultListModel();
		listaProd.setModel(prodModel);
		frame.getContentPane().add(listaProd);
		
		listaCant = new JList();
		listaCant.setBounds(680, 120, 50, 300);
		cantModel = new DefaultListModel();
		listaCant.setModel(cantModel);
		frame.getContentPane().add(listaCant);
	
		categoriaModel.addElement("camisetas");
		categoriaModel.addElement("sudaderas");
		categoriaModel.addElement("camisas");
		categoriaModel.addElement("punto");
		categoriaModel.addElement("jeans");
		categoriaModel.addElement("pantalones");
		categoriaModel.addElement("bermudas");
		categoriaModel.addElement("zapatos");
		categoriaModel.addElement("zapatillas");
		categoriaModel.addElement("botas");
		categoriaModel.addElement("relojes");
		categoriaModel.addElement("pulseras");
		categoriaModel.addElement("otros");
		
		oDBConection = new DBConection();
	
		listaCategoria.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				prodModel.clear();
				cantModel.clear();
				
				cn = oDBConection.connect();
				cadenaCategoria = (String) listaCategoria.getSelectedValue();
				
				String sentenciaSQL = "SELECT * FROM productos WHERE categoria = '" + cadenaCategoria + "'";
				String datos[] = new String[2];

				try {
					oDBConection.connect();
					
					Statement st = cn.createStatement();
					ResultSet rs = st.executeQuery(sentenciaSQL);
					while(rs.next()) {
						datos[0] = rs.getString("nombre");
						datos[1] = rs.getString("cantidad");
						prodModel.addElement(datos[0]);
						cantModel.addElement(datos[1]);
					}		
				}
				catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		listaProd.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//cadenaProducto = (String) listaProd.getSelectedValue();
			}
			
		});
		
		
	}

	public Window getFrame() {
		// TODO Auto-generated method stub
		return frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		try {
			if(e.getSource() == add) {
				String cantS = cantidad.getText();
				cadenaProducto = nProducto.getText();

				oDBConection.aumentar(cadenaProducto, cantS);
				nProducto.setText(null);
				cantidad.setText(null);

				prodModel.clear();
				cantModel.clear();

				cn = oDBConection.connect();
				cadenaCategoria = (String) listaCategoria.getSelectedValue();

				String sentenciaSQL = "SELECT * FROM productos WHERE categoria = '" + cadenaCategoria + "'";
				String datos[] = new String[2];

				try {
					oDBConection.connect();

					Statement st = cn.createStatement();
					ResultSet rs = st.executeQuery(sentenciaSQL);
					while(rs.next()) {
						datos[0] = rs.getString("nombre");
						datos[1] = rs.getString("cantidad");
						prodModel.addElement(datos[0]);
						cantModel.addElement(datos[1]);
					}		
				}
				catch(Exception e1) {
					e1.printStackTrace();
				}

				cadenaCategoria = null;
			}
		}
		catch(Exception e1) {
			JOptionPane.showMessageDialog(null, "Indique un producto y cantidad adecuados");
		}
		try {
			if(e.getSource() == quit) {
				String cantS = cantidad.getText();
				cadenaProducto = nProducto.getText();

				oDBConection.disminuir(cadenaProducto, cantS);
				nProducto.setText(null);
				cantidad.setText(null);

				prodModel.clear();
				cantModel.clear();

				cn = oDBConection.connect();
				cadenaCategoria = (String) listaCategoria.getSelectedValue();

				String sentenciaSQL = "SELECT * FROM productos WHERE categoria = '" + cadenaCategoria + "'";
				String datos[] = new String[2];

				try {
					oDBConection.connect();

					Statement st = cn.createStatement();
					ResultSet rs = st.executeQuery(sentenciaSQL);
					while(rs.next()) {
						datos[0] = rs.getString("nombre");
						datos[1] = rs.getString("cantidad");
						prodModel.addElement(datos[0]);
						cantModel.addElement(datos[1]);
					}		
				}
				catch(Exception e1) {
					JOptionPane.showMessageDialog(null, "Indique un producto y cantidad adecuados");
				}

				cadenaCategoria = null;
			}
			
			try {
				if(e.getSource() == anadir) {
					String nombre = lNomb.getText();
					String categoria = lCat.getText();
					String precio = lPrec.getText();
					String cantidad = lCant.getText();
					
					float prec = Float.parseFloat(precio);
					int cant = Integer.parseInt(cantidad);
					
					oDBConection.aniadirProducto(nombre, categoria, prec, cant);
					
					lNomb.setText(null);
					lCat.setText(null);
					lPrec.setText(null);
					lCant.setText(null);
				}
			}
			
			catch(Exception e1) {
				e1.printStackTrace();
			}
			
			try {
				if(e.getSource() == eliminar) {
					String nombre = lNomb.getText();
//					String categoria = lCat.getText();
//					String precio = lPrec.getText();
//					String cantidad = lCant.getText();
					
					//float prec = Float.parseFloat(precio);
					//int cant = Integer.parseInt(cantidad);
					
					oDBConection.eliminarProducto(nombre);
					
					lNomb.setText(null);
					lCat.setText(null);
					lPrec.setText(null);
					lCant.setText(null);
				}
			}
			
			catch(Exception e1) {
				e1.printStackTrace();
			}
		}
		catch(Exception e1) {
			JOptionPane.showMessageDialog(null, "Indique un producto y cantidad adecuados");		}
	}
}
