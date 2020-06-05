import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrmGUI extends JFrame {

	private JPanel contentPane;
	private JTable tbData;
	private DefaultTableModel tableModel;

	/**
	 * Launch the application.
	 */
	public  void inicio() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmGUI frame = new FrmGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public void mostrarEntidad(Entidades entidad) {
		try {
			while (tableModel.getRowCount() > 0) {
				tableModel.removeRow(0);
				
			}
//		System.out.println("Indice: " + entidad.getIndice());
//		System.out.println("Nombre: " + entidad.getNombre());
//		System.out.println("Cantidad de atributos: " + entidad.getCantidad());
//		System.out.println("Atributos:");
		int i = 1;
		for (Atributos atributo : entidad.getAtributos()) {
//			System.out.println("\tNo. " + i);
//			System.out.println("\tNombre: " + atributo.getNombre());
//			System.out.println("\tTipo de dato: " + atributo.getNombreTipoDato());
			tableModel.addRow(new Object[] {i, atributo.getNombre(), atributo.getNombreTipoDato() });
			if (atributo.isRequiereLongitud()) {
				tableModel.addRow(new Object[] {i, atributo.getNombre(), atributo.getNombreTipoDato(),atributo.getLongitud() });
			}
			i++;
		}
	}catch (Exception e) {
		
	}
	}
	/**
	 * Create the frame.
	 */
	public FrmGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 545, 427);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tableModel = new DefaultTableModel();
		tableModel.addColumn("INDICE");
		tableModel.addColumn("NOMBRE");
		tableModel.addColumn("DATO");
		tableModel.addColumn("LONGITUD");
		tbData = new JTable();
		tbData.setModel(tableModel);
		JScrollPane scrollPane = new JScrollPane(tbData);
		scrollPane.setBounds(10, 28, 509, 213);
		contentPane.add(scrollPane);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(196, 46, 6, 20);
		contentPane.add(textPane);
		
		JButton btnListar = new JButton("Listar");
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			mostrarEntidad(entidad);
			}
		});
		btnListar.setBounds(385, 252, 134, 31);
		contentPane.add(btnListar);
	}
}
