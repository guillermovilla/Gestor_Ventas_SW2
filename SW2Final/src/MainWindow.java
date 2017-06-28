import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.sql.Connection;

public class MainWindow extends JFrame implements ActionListener {

	private JMenuBar mb;
	private JMenu mAdmin, mAyuda, mCaja, mSesion;
	private JMenuItem m1, m2, caja, mCerrarS;

	private SellWindow oSellWindow;
	private VentanaInventario oVentanaInventario;
	private AdminWindow oAdminWindow;
	private DBConection oDBConection;
	private LoginWindow oLoginWindow;
	private VentanaHistorial oVentanaHistorial;
	private Connection cn;
	private Connection con;

	private String tipo;

	private JFrame frame;

	private JPanel panel;

	private JButton btnVenta;
	private JButton btnInventario;
	private JButton btnHistorial;

	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(350, 300, 450, 340);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.setTitle("Ventana Principal");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);

		mb = new JMenuBar();
		frame.setJMenuBar(mb);
		mAdmin = new JMenu("Administrador");
		mCaja = new JMenu("Caja");
		mAyuda = new JMenu("Ayuda");
		mSesion = new JMenu("Sesión");
		
		mb.add(mAdmin);
		mb.add(mCaja);
		mb.add(mAyuda);
		mb.add(mSesion);

		m1 = new JMenuItem("Añadir o eliminar usuarios");
		m1.addActionListener(this);
		mAdmin.add(m1);

		m2 = new JMenuItem("Acerca de ...");
		mAyuda.add(m2);

		caja = new JMenuItem("Cerrar caja");
		caja.addActionListener(this);
		mCaja.add(caja);
		
		mCerrarS = new JMenuItem("Cerrar sesión");
		mSesion.add(mCerrarS);
		mCerrarS.addActionListener(this);

		btnVenta = new JButton("Venta");	
		btnVenta.setBounds(10, 10, 425, 80);
		frame.getContentPane().add(btnVenta);
		btnVenta.addActionListener(this);

		btnInventario = new JButton("Inventario");
		btnInventario.setBounds(10, 100, 425, 80);
		frame.getContentPane().add(btnInventario);
		btnInventario.addActionListener(this);

		btnHistorial = new JButton("Historial Ventas");
		btnHistorial.setBounds(10, 190, 425, 80);
		frame.getContentPane().add(btnHistorial);
		btnHistorial.addActionListener(this);

		String i = LoginWindow.tipo;

		if(!i.equals("administrador")) {
			m1.setEnabled(false);
		}

		if(i.equals("empleado")) {
			btnInventario.setEnabled(false);
		}

		oDBConection = new DBConection();
		cn = oDBConection.connect();

		String sentenciaSQL;
		String nombre = null;

		oDBConection.comprobarStock();

	}

	public void actionPerformed(ActionEvent e) {

		try {
			if(e.getSource() == btnVenta) {
				oSellWindow = new SellWindow();
				oSellWindow.getFrame().setVisible(true);
			}
		}
		catch(Exception e1){

		}

		try {
			if(e.getSource() == btnInventario) {
				oVentanaInventario = new VentanaInventario();
				oVentanaInventario.getFrame().setVisible(true);
			}
		}
		catch(Exception e1) {

		}

		try {
			if(e.getSource() == btnHistorial) {
				oVentanaHistorial = new VentanaHistorial();
				oVentanaHistorial.getFrame().setVisible(true);
			}
		}
		catch(Exception e1) {

		}

		try {
			if(e.getSource() == m1) {
				oAdminWindow = new AdminWindow();
				oAdminWindow.getFrame().setVisible(true);
			}
		}
		catch(Exception e1) {

		}

		try {
			if(e.getSource() == caja) {
				oDBConection.cerrarCaja();
			}
		}
		catch(Exception e1) {

		}
		
		try {
			if(e.getSource() == mCerrarS) {
				oLoginWindow = new LoginWindow();
				oLoginWindow.getFrame().setVisible(true);
				getFrame().setVisible(false);
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
