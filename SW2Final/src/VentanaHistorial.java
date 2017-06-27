import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class VentanaHistorial extends JFrame implements ActionListener {

	private JFrame frame;

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
		fL.setBounds(130, 20, 120, 20);
		frame.getContentPane().add(fL);
		
		hL = new JLabel("Hora");
		hL.setBounds(260, 20, 120, 20);
		frame.getContentPane().add(hL);
		
		vendL = new JLabel("Vendedor");
		vendL.setBounds(390, 20, 120, 20);
		frame.getContentPane().add(vendL);
		
		cantL = new JLabel("Cantidad");
		cantL.setBounds(520, 20, 120, 20);
		frame.getContentPane().add(cantL);
		
		tL = new JLabel("Total");
		tL.setBounds(650, 500, 120, 20);
		frame.getContentPane().add(tL);
		
		iDTA = new JTextArea();
		scrollPane = new JScrollPane(iDTA);
		scrollPane.setBounds(20, 50, 100, 400);
		frame.getContentPane().add(scrollPane);
		
		fTA = new JTextArea();
		scrollPane = new JScrollPane(fTA);
		scrollPane.setBounds(130, 50, 120, 400);
		frame.getContentPane().add(scrollPane);
		
		hTA = new JTextArea();
		scrollPane = new JScrollPane(hTA);
		scrollPane.setBounds(260, 50, 120, 400);
		frame.getContentPane().add(scrollPane);
		
		vendTA = new JTextArea();
		scrollPane = new JScrollPane(vendTA);
		scrollPane.setBounds(390, 50, 120, 400);
		frame.getContentPane().add(scrollPane);
		
		cantTA = new JTextArea();
		scrollPane = new JScrollPane(cantTA);
		scrollPane.setBounds(520, 50, 120, 400);
		frame.getContentPane().add(scrollPane);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public Window getFrame() {
		// TODO Auto-generated method stub
		return frame;
	}
}
