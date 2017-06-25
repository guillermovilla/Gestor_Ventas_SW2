import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;

/***** VENTNA LOGIN *****/

public class LoginWindow extends JFrame implements ActionListener {
	
	public static int idV = 0;
	
	// Declaracion de la ventana de login
	JFrame frame;
	// Declaración de botons y campos de escritura
	private JTextField userField;
	private JPasswordField passField;
	private JButton entryButton;

	private boolean correctLogin;
	
	private MainWindow oMainWindow;
	private DBConection oDBConection;
	private Connection cn;

	public static String tipo;
	public static int id;
	
	public LoginWindow() {
		startWindow();
	}
	
	private void startWindow() {
		// TODO Auto-generated method stub
		// Implementacion y caracteristicas del frame
		tipo = null;
		id = 0;
		
		frame = new JFrame();
		frame.setBounds(650, 320, 540, 370);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Login");
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		JLabel userLabel = new JLabel("Usuario: ");
		userLabel.setBounds(80, 120, 86, 14);
		frame.getContentPane().add(userLabel);
		
		JLabel passLabel = new JLabel("Contraseña: ");
		passLabel.setBounds(59, 170, 86, 14);
		frame.getContentPane().add(passLabel);
		
//		JLabel uTypeLabel = new JLabel("Tipo de usuario: ");
//		uTypeLabel.setBounds(38, 70, 120, 14);
//		frame.getContentPane().add(uTypeLabel);
		
		userField = new JTextField();
		userField.setBounds(150, 115, 176, 25);
		frame.getContentPane().add(userField);
		userField.setColumns(20);
		
		passField = new JPasswordField();
		passField.setBounds(150, 165, 176, 25);
		frame.getContentPane().add(passField);
		passField.setColumns(20);
		
		entryButton = new JButton("Entrar");
		entryButton.setBounds(170, 200, 89, 23);
		frame.getContentPane().add(entryButton);
		entryButton.addActionListener(this);
		
		oDBConection = new DBConection();
		cn = oDBConection.connect();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == entryButton) {
			oDBConection.connect();
			
			String c1, c2;

			c1 = userField.getText();
			c2 = new String(passField.getPassword());
				
			try {
				correctLogin = oDBConection.systemEntry(c1, c2);
			}	
			catch(Exception e1) {
				System.out.println(e1.getMessage());
			}
			
			if(correctLogin) {
				
				correctLogin = true;
				MainWindow ventanap = new MainWindow();
				ventanap.getFrame().setVisible(true);
				this.frame.setVisible(false);
				
			}
			else {
				JOptionPane.showMessageDialog(null, "Usuario y/o contraseña incorrectos, intentelo de nuevo");
			}
		}		
	}
	
	public void setTipo(String c) {
		tipo = c;	
	}
	public String getTipo() {
		return tipo;
	}
	
	public void setId(int i) {
		id = i;
	}
	public int getId() {
		return id;
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow window = new LoginWindow();
					window.frame.setVisible(true);
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}