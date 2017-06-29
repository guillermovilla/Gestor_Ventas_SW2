import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class AdminWindow extends JFrame implements ActionListener{

	private DBConection oDBConection;
	
	private JFrame frame;
	
	private JLabel name;
	private JLabel pass;
	private JLabel passConf;
	private JLabel type;
	
	private JTextField gName;
	private JTextField gPass;
	private JTextField gPassConf;
	
	private JComboBox gType;
	
	private JButton add;
	private JButton delete;
	private JMenuBar mb;
	private JMenu mAyuda;
	private JMenuItem m1;

	
	public AdminWindow() {
		initialize();
	}
	
	private void initialize() {
		String[] tipoUsuario = {"administrador", "gerente", "empleado"};
		
		frame = new JFrame(); 
		frame.setBounds(300, 300, 620, 400);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.setTitle("Administrador");
		frame.getContentPane().setLayout(null);
		
		mb = new JMenuBar();
		frame.setJMenuBar(mb);
		mAyuda = new JMenu("Ayuda");
		mb.add(mAyuda);
		
		m1 = new JMenuItem("Instrucciones");
		mAyuda.add(m1);
		
		name = new JLabel("Nombre");
		name.setBounds(110, 70, 50, 15);
		frame.getContentPane().add(name);
		
		gName = new JTextField();
		gName.setBounds(170, 65, 140, 25);
		frame.getContentPane().add(gName);
		
		pass = new JLabel("Contraseña");
		pass.setBounds(90, 120, 70, 15);
		frame.getContentPane().add(pass);
		
		gPass = new JPasswordField();
		gPass.setBounds(170, 125, 140, 25);
		frame.getContentPane().add(gPass);
		
		passConf = new JLabel("Confirmar contraseña");
		passConf.setBounds(30, 180, 140, 15);
		frame.getContentPane().add(passConf);
		
		gPassConf = new JPasswordField();
		gPassConf.setBounds(170, 175, 140, 25);
		frame.getContentPane().add(gPassConf);
		
		type = new JLabel("Tipo de usuario");
		type.setBounds(350, 100, 160, 15);
		frame.getContentPane().add(type);
		
		gType = new JComboBox(tipoUsuario);
		gType.setSelectedIndex(0);
		gType.setBounds(350, 130, 170, 25);
		frame.getContentPane().add(gType);
		gType.addActionListener(this);
		
		add = new JButton("Añadir");
		add.setBounds(160, 250, 80, 25);
		frame.getContentPane().add(add);
		add.addActionListener(this);
		
		delete = new JButton("Eliminar");
		delete.setBounds(360, 250, 80, 25);
		frame.getContentPane().add(delete);
		delete.addActionListener(this);
		
		oDBConection = new DBConection();
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

				//oDBConection.connect();
				String nombre, contrasena, confirmar, tipo;
				tipo = (String) gType.getSelectedItem();

				nombre = gName.getText();
				contrasena = gPass.getText();
				confirmar = gPassConf.getText();

				if(nombre.isEmpty() || contrasena.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Alguno de los campos está vacio, por favor compruebe que ha completado todos los campos.");
				}
				else if(!contrasena.equals(confirmar)) {
					JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden, vuelva a intentarlo");
				}
				else {
					oDBConection.anadirUsuario(nombre, contrasena, tipo);
				}

			}
		}
		catch(Exception e1) {
			e1.printStackTrace();
		}

		try {
			if(e.getSource() == delete) {
				String nombre, contrasena, confirmar, tipo;
				tipo = (String) gType.getSelectedItem();

				nombre = gName.getText();
				contrasena = gPass.getText();
				confirmar = gPassConf.getText();

				if(nombre.isEmpty() || contrasena.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Alguno de los campos está vacio, por favor compruebe que ha completado todos los campos.");
				}
				else if(!contrasena.equals(confirmar)) {
					JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden, vuelva a intentarlo");
				}
				else {
					oDBConection.eliminarUsuario(nombre);
				}
			}	

		}
		catch(Exception e1) {
			e1.printStackTrace();
		}

	}
}
