import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

import javax.swing.*;

//import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.Statement;

public class LoginWindow extends JFrame implements ActionListener {
	
	// Declaracion de la ventana de login
	JFrame frame;
	// Declaración de botons y campos de escritura
	private JTextField userField;
	private JPasswordField passField;
	private JButton entryButton;

	private boolean correctLogin = false;
	
	private MainWindow oMainWindow;
	private DBConection oDBConection;
	private Connection cn;

	public LoginWindow() {
		startWindow();
	}
	
	private void startWindow() {
		// TODO Auto-generated method stub
		// Implementacion y caracteristicas del frame
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
		
		JLabel uTypeLabel = new JLabel("Tipo de usuario: ");
		uTypeLabel.setBounds(38, 70, 120, 14);
		frame.getContentPane().add(uTypeLabel);
		
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
		}
		
//		try {
//			correctLogin = oDBConection.systemEntry(userField.getText(), String.valueOf(passField.getPassword()));
//		}
//		
//		catch(SQLException e1) {
//			e1.printStackTrace();
//		}
		
		String c1, c2;
		String DatoCapturado = "";
		c1 = userField.getText();
		
		c2 = new String(passField.getPassword());
		
		String sql= "SELECT * FROM login WHERE name ='" + c1 + "' AND password ='" + c2 + "'";
		
		try {
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) {
				DatoCapturado = rs.getString("n_registro");
			}
			
			if(DatoCapturado.equals("")) {
				JOptionPane.showMessageDialog(null, "El usuario no existe", "Error al iniciar sesion", JOptionPane.OK_OPTION);
				//frame.setVisible(false);
				//oMainWindow.getFrame().setVisible(true);
			}
			else {
				
				correctLogin = true;
				MainWindow ventanap = new MainWindow();
				ventanap.getFrame().setVisible(true);
				this.frame.setVisible(false);
				
			}
		}
		catch(Exception e1) {
			System.out.println(e1.getMessage());
		}
		
		
//		if(correctLogin) {
//			
//			oMainWindow.getFrame().setVisible(true);
//			
//			this.frame.setVisible(false);
//			this.frame.dispose();
//			this.frame = null;
//		}
//		else {
//			String str1 = "Nombre de usuario o contraseña incorrectos.";
//			
//			JOptionPane.showMessageDialog(null, str1, "Acceder", JOptionPane.ERROR_MESSAGE);
//		}
		
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