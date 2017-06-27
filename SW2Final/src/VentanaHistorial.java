import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class VentanaHistorial extends JFrame implements ActionListener {

	private DBConection oDBConection;
	private Connection cn;
	
	private JFrame frame;

	private JTextField campoFecha;
	
	private JButton	buscar;
	
	private JMenuBar mb;
	private JMenu mAyuda;
	private JMenuItem m1;

	private JLabel iDL, fL, hL, vendL, cantL, tL;
	private JTextArea iDTA, fTA, hTA, vendTA, cantTA;

	private JScrollPane scrollPane;
	
	private JComboBox selector;

	public VentanaHistorial() {
		initialize();
	}

	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(200, 200, 900, 600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Historial");
		frame.setResizable(false);
		
		mb = new JMenuBar();
		frame.setJMenuBar(mb);
		mAyuda = new JMenu("Ayuda");
		m1 = new JMenuItem("Ayuda");
		mAyuda.add(m1);
		
		iDL = new JLabel("ID");
		iDL.setBounds(20, 20, 100, 20);
		frame.getContentPane().add(iDL);
		
		fL = new JLabel("Fecha");
		fL.setBounds(120, 20, 120, 20);
		frame.getContentPane().add(fL);
		
		hL = new JLabel("Hora");
		hL.setBounds(240, 20, 120, 20);
		frame.getContentPane().add(hL);
		
		vendL = new JLabel("Vendedor");
		vendL.setBounds(360, 20, 120, 20);
		frame.getContentPane().add(vendL);
		
		cantL = new JLabel("Cantidad");
		cantL.setBounds(480, 20, 120, 20);
		frame.getContentPane().add(cantL);
		
		campoFecha = new JTextField("dd-mm-aaaa");
		campoFecha.setBounds(630, 50, 120, 20);
		frame.getContentPane().add(campoFecha);
		
		buscar = new JButton("Buscar");
		buscar.setBounds(760, 50, 80, 20);
		frame.getContentPane().add(buscar);
		buscar.addActionListener(this);
		
		tL = new JLabel("Total");
		tL.setBounds(650, 500, 120, 20);
		frame.getContentPane().add(tL);
		
		iDTA = new JTextArea();
		scrollPane = new JScrollPane(iDTA);
		scrollPane.setBounds(20, 50, 100, 400);
		frame.getContentPane().add(scrollPane);
		
		fTA = new JTextArea();
		scrollPane = new JScrollPane(fTA);
		scrollPane.setBounds(120, 50, 120, 400);
		frame.getContentPane().add(scrollPane);
		
		hTA = new JTextArea();
		scrollPane = new JScrollPane(hTA);
		scrollPane.setBounds(240, 50, 120, 400);
		frame.getContentPane().add(scrollPane);
		
		vendTA = new JTextArea();
		scrollPane = new JScrollPane(vendTA);
		scrollPane.setBounds(360, 50, 120, 400);
		frame.getContentPane().add(scrollPane);
		
		cantTA = new JTextArea();
		scrollPane = new JScrollPane(cantTA);
		scrollPane.setBounds(480, 50, 120, 400);
		frame.getContentPane().add(scrollPane);
		
		oDBConection = new DBConection();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		cn = oDBConection.connect();
		
		try {
			if(e.getSource() == buscar) {
				int id = 0;
				String fecha = "";
				String hora = "";
				int vendedor = 0;
				float cantidad = 0;
				String idS = "";
				String vendedorS = "";
				String cantidadS = "";
				
				String fec = campoFecha.getText();

				String sentenciaSQL = "SELECT * FROM log WHERE fecha = '" + fec + "'";
				
				Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery(sentenciaSQL);
				
				while(rs.next()) {
					id = rs.getInt("idventa");
					fecha += rs.getString("fecha");
					hora += rs.getString("hora");
					vendedor = rs.getInt("vendedor");
					cantidad = rs.getFloat("precio");
					
					idS += Integer.toString(id);
					vendedorS += Integer.toString(vendedor);
					cantidadS += Float.toString(cantidad);
					
					idS += "\n";
					fecha += "\n";
					hora += "\n";
					vendedorS += "\n";
					cantidadS += "\n";
				}	
				
				iDTA.setText(idS);
				vendTA.setText(vendedorS);
				fTA.setText(fecha);
				hTA.setText(hora);
				cantTA.setText(cantidadS);	
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
