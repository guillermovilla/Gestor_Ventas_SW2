import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainWindow extends JFrame implements ActionListener {

	private JMenuBar mb;
	private JMenu mAdmin, mAyuda;
	private JMenuItem m1, m2;
	
	private SellWindow oSellWindow;
	private VentanaInventario oVentanaInventario;
	private AdminWindow oAdminWindow;
	
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
		mAyuda = new JMenu("Ayuda");
		mb.add(mAdmin);
		mb.add(mAyuda);
		
		m1 = new JMenuItem("Añadir o eliminar usuarios");
		m1.addActionListener(this);
		mAdmin.add(m1);
		
		m2 = new JMenuItem("Acerca de ...");
		mAyuda.add(m2);
		
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
			if(e.getSource() == m1) {
				oAdminWindow = new AdminWindow();
				oAdminWindow.getFrame().setVisible(true);
			}
		}
		catch(Exception e1) {
			
		}
	}

	public Window getFrame() {
		// TODO Auto-generated method stub
		return frame;
	}

}
