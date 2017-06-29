import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;

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

	private JTextField campoFecha, campoMes, campoAnho, campoVend, campoTotal;
	
	private JButton	buscar;
	
	private JMenuBar mb;
	private JMenu mAyuda;
	private JMenuItem m1;

	private JLabel iDL, fL, hL, vendL, cantL, tL, diaL, mesL, anhoL, empL;
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
		mb.add(mAyuda);
		
		m1 = new JMenuItem("Instrucciones");
		mAyuda.add(m1);
		
		iDL = new JLabel("ID");
		iDL.setBounds(40, 50, 100, 20);
		frame.getContentPane().add(iDL);
		
		fL = new JLabel("Fecha");
		fL.setBounds(140, 50, 120, 20);
		frame.getContentPane().add(fL);
		
		hL = new JLabel("Hora");
		hL.setBounds(260, 50, 120, 20);
		frame.getContentPane().add(hL);
		
		vendL = new JLabel("Vendedor");
		vendL.setBounds(380, 50, 120, 20);
		frame.getContentPane().add(vendL);
		
		cantL = new JLabel("Cantidad");
		cantL.setBounds(500, 50, 120, 20);
		frame.getContentPane().add(cantL);
		
		diaL = new JLabel("Fecha");
		diaL.setBounds(780, 80, 120, 20);
		frame.getContentPane().add(diaL);
		
		mesL = new JLabel("Mes");
		mesL.setBounds(780, 105, 120, 20);
		//frame.getContentPane().add(mesL);
		
		anhoL = new JLabel("Año");
		anhoL.setBounds(780, 130, 120, 20);
		//frame.getContentPane().add(anhoL);
		
		empL = new JLabel("ID");
		empL.setBounds(780, 105, 120, 20);
		frame.getContentPane().add(empL);
		
		campoTotal = new JTextField();
		campoTotal.setBounds(500, 485, 120, 25);
		frame.add(campoTotal);
		
		campoFecha = new JTextField();
		campoFecha.setBounds(650, 80, 120, 20);
		frame.getContentPane().add(campoFecha);
		
		campoMes = new JTextField();
		campoMes.setBounds(650, 105, 120, 20);
		//frame.getContentPane().add(campoMes);
		
		campoAnho = new JTextField();
		campoAnho.setBounds(650, 130, 120, 20);
		//frame.getContentPane().add(campoAnho);
		
		campoVend = new JTextField();
		campoVend.setBounds(650, 105, 120, 20);
		frame.getContentPane().add(campoVend);
		
		buscar = new JButton("Buscar");
		buscar.setBounds(650, 130, 80, 20);
		frame.getContentPane().add(buscar);
		buscar.addActionListener(this);
		
		tL = new JLabel("Total");
		tL.setBounds(450, 490, 120, 20);
		frame.getContentPane().add(tL);
		
		iDTA = new JTextArea();
		scrollPane = new JScrollPane(iDTA);
		scrollPane.setBounds(40, 80, 100, 400);
		frame.getContentPane().add(scrollPane);
		
		fTA = new JTextArea();
		scrollPane = new JScrollPane(fTA);
		scrollPane.setBounds(140, 80, 120, 400);
		frame.getContentPane().add(scrollPane);
		
		hTA = new JTextArea();
		scrollPane = new JScrollPane(hTA);
		scrollPane.setBounds(260, 80, 120, 400);
		frame.getContentPane().add(scrollPane);
		
		vendTA = new JTextArea();
		scrollPane = new JScrollPane(vendTA);
		scrollPane.setBounds(380, 80, 120, 400);
		frame.getContentPane().add(scrollPane);
		
		cantTA = new JTextArea();
		scrollPane = new JScrollPane(cantTA);
		scrollPane.setBounds(500, 80, 120, 400);
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
				float total = 0;
				String idS = "";
				String vendedorS = "";
				String cantidadS = "";
				String sentenciaSQL = "";
				String fec = campoFecha.getText();
				
				if(!campoFecha.getText().isEmpty() && campoVend.getText().isEmpty()) {
				//String sentenciaSQL = "SELECT * FROM log WHERE fecha = '" + fec + "'";
					sentenciaSQL = "SELECT * FROM log WHERE date_format(fecha, '%d-%m-%Y') = '" + fec + "'";
				}			
				else if(!campoFecha.getText().isEmpty() && !campoVend.getText().isEmpty()) {
					String s = campoVend.getText();
					int i = Integer.parseInt(s);
					
					sentenciaSQL = "SELECT * FROM log WHERE (date_format(fecha, '%d-%m-%Y') = '" + fec + "') AND (vendedor = '" + i + "')";
				}
				else if(campoFecha.getText().isEmpty() && !campoVend.getText().isEmpty()) {
					String s = campoVend.getText();
					int i = Integer.parseInt(s);
					
					sentenciaSQL = "SELECT * FROM log WHERE vendedor = '" + i + "'";
				}
				
				Statement st = cn.createStatement();
				ResultSet rs = st.executeQuery(sentenciaSQL);
				
				while(rs.next()) {
					
					id = rs.getInt("idventa");
					fecha += rs.getString("fecha");
					hora += rs.getString("hora");
					vendedor = rs.getInt("vendedor");
					cantidad = rs.getFloat("precio");
					
					total += cantidad;
					
					idS += Integer.toString(id);
					vendedorS += Integer.toString(vendedor);
					cantidadS += Float.toString(cantidad);
					
					idS += "\n";
					fecha += "\n";
					hora += "\n";
					vendedorS += "\n";
					cantidadS += "\n";
				}	
				
				String totalS = Float.toString(total);
				
				
				iDTA.setText(idS);
				vendTA.setText(vendedorS);
				fTA.setText(fecha);
				hTA.setText(hora);
				cantTA.setText(cantidadS);	
				campoTotal.setText(totalS);
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
