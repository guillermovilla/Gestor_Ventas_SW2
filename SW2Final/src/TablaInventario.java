import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class TablaInventario extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DBConection conexion;
	DefaultTableModel modelo = null;

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public TablaInventario() throws SQLException {
		
		conexion = new DBConection();
		table = new JTable();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(900, 600, 720, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	    int id_producto, cantidad;
        String producto, categoria;
        double precio;
		conexion.connect();
		
        modelo = new DefaultTableModel();
	
        String[] columnas = { "Id_Producto", "Producto", "Categoria" ,"Precio", "Cantidad" };
        modelo.setColumnIdentifiers(columnas);
        table.setModel(modelo);
		Statement st = conexion.connect().createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM productos;");
		
		while (rs.next()){
			id_producto = rs.getInt("idproducto");
        	producto = rs.getString("nombre");
        	categoria = rs.getString("categoria");
        	cantidad = rs.getInt("cantidad");
        	precio = rs.getDouble("precio");
        	modelo.addRow(new Object[] { id_producto,producto,precio, categoria, cantidad } );
		}

		table.setBounds(10, 11, 700, 400);
		contentPane.add(table);
		table.setVisible(true);

	}
}
