import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainWindow extends JFrame {

	private SellWindow oSellWindow;
	private VentanaInventario oVentanaInventario;
	
	private JFrame frame;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(70, 70, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(3, 1, 0, 0));
		
		JButton btnVenta = new JButton("Venta");
		
		btnVenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				oSellWindow = new SellWindow();
				oSellWindow.getFrame().setVisible(true);
				//frame.setVisible(false);
			}
		});
		
		panel.add(btnVenta);
		
		JButton btnInventario = new JButton("Inventario");
		panel.add(btnInventario);
		btnInventario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e2) {
				oVentanaInventario = new VentanaInventario();
				oVentanaInventario.getFrame().setVisible(true);
			}
		});
	}

	public Window getFrame() {
		// TODO Auto-generated method stub
		return frame;
	}

}
