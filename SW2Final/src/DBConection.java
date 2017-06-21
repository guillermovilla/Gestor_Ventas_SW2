import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

import javax.swing.JOptionPane;


/***** CLASE PARA LAS CONSULTAS Y PETICIONES A LA BASE DE DATOS *****/

public class DBConection {

	private String user;
	private String password;
	private String url;

	private Connection con;
	Statement statement;
	
	private LoginWindow oLoginWindow;

	/*Constructor*/
	public DBConection() {
		user = "root";
		password = "";
		url = "jdbc:mysql://localhost/tienda2";

		setConection(null);
		statement = null;
	}

	/*Conexión con la base de datos*/
	public Connection connect() {
		String ok ="Conexion realizada con exito";
		String bad ="No se ha podido conectar";

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			con = DriverManager.getConnection(url, user, password);
		}

		catch (Exception e) {
			JOptionPane.showMessageDialog(null, bad, "Estado de la conexion", JOptionPane.ERROR_MESSAGE);
		} 

		return con;
	}

	public boolean systemEntry(String user, String password) throws SQLException {
		
		String sentenciaSQL;
		
		String tipoUsuario = null;
		int idUsuario = 0;
		
		oLoginWindow = new LoginWindow();
		boolean correctLogin = false;
		
		connect();
		Statement st = con.createStatement();
		
		try {
			sentenciaSQL = "SELECT * FROM login WHERE name ='" + user + "' AND password ='" + password + "'";

			ResultSet rs = st.executeQuery(sentenciaSQL);
			while(rs.next() == true) {
				correctLogin = true;
				idUsuario = rs.getInt("n_registro");
				tipoUsuario = rs.getString("type");
			}
			
			oLoginWindow.setId(idUsuario);
			oLoginWindow.setTipo(tipoUsuario);
			
		}
		catch(Exception e) {
			
		}
		
		return correctLogin;
	}

	public void aniadirProducto(String descripcion, double precio, int cantidad) throws SQLException {
		// TODO Auto-generated method stub

		
		int r;
		String sentenciaSQL;

		connect();
		Statement st = con.createStatement();

		try {

			sentenciaSQL = "insert into productos(descripcion, cantidad, precio) values ('" + descripcion + "', '"
					+ cantidad + "','" + precio + "'    )";

			r = st.executeUpdate(sentenciaSQL);

			JOptionPane.showMessageDialog(null, r + " registro agregado");

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Registro no agregado: Datos incorrectos");
		}

	}
	public void eliminarProducto(String descripcion) throws SQLException {
		int r;
		String sentenciaSQL;
		statement = con.createStatement();
		try {
			sentenciaSQL = "delete from productos where descripcion='" + descripcion + "'";
			r = statement.executeUpdate(sentenciaSQL);
			JOptionPane.showMessageDialog(null, r + " producto eliminado");
			;
		} catch (Exception e) {
			JOptionPane
			.showMessageDialog(null,
					"No se pudo eliminar el producto,\n compruebe que existe en su BD");
		}
	}

	public void aumentar(String productoAumentar, String cantidadAumentar) throws SQLException {
		int r;
		String sentenciaSQL;
		statement = con.createStatement();
		int cant = Integer.parseInt(cantidadAumentar);


		try {
			sentenciaSQL = "UPDATE productos SET cantidad=cantidad +'" + cant +  "' WHERE descripcion= '" + productoAumentar + "'";
			r = statement.executeUpdate(sentenciaSQL);
			JOptionPane.showMessageDialog(null, r + " producto aumentado");
			;
		} catch (Exception e) {
			JOptionPane
			.showMessageDialog(null,
					"No se encuentra el producto");
		}
	}

	public void disminuir(String productoDisminuir, String cantidadDisminuir) throws SQLException {
		int r;
		String sentenciaSQL;
		int dis = Integer.parseInt(cantidadDisminuir);
		statement = con.createStatement();

		Statement st = con.createStatement();
		int cantidadProducto = 0;
		ResultSet rs = st.executeQuery("SELECT cantidad FROM productos WHERE descripcion = '" + productoDisminuir +"'");

		if(rs.next()) {
			cantidadProducto = rs.getInt("cantidad");
		}
		if(dis > cantidadProducto) {
			JOptionPane.showMessageDialog(null, "No haysuficientes productos");
		}
		else {
			try {
				sentenciaSQL = "UPDATE productos SET cantidad=cantidad- '" + dis + "'WHERE descripcion= '" + productoDisminuir + "'";
				r = statement.executeUpdate(sentenciaSQL);
				JOptionPane.showMessageDialog(null, r + " producto disminuido");
				;
			} catch (Exception e) {
				JOptionPane
				.showMessageDialog(null,
						"No se encuentra el producto");
			}
		}
	}

	public String readProduct(int id) throws SQLException {
		Statement st2 = getConection().createStatement();
		String sentencia = "";

		return null;
	}

	public String addProduct(int id) {
		return null;
	}

	private void setConection(Connection con) {
		// TODO Auto-generated method stub
		this.con = con;
	}

	public Connection getConection() {
		return con;
	}
}