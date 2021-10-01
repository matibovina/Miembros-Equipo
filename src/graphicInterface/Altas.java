package graphicInterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import modelo.DAOMiembro;
import modelo.Miembro;

import javax.swing.ScrollPaneConstants;
import javax.swing.SortOrder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSeparator;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JTabbedPane;
import java.awt.Font;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Altas extends JFrame {

	private JFrame frame;
	private JPanel contentPane;
	private JTable table;
	private JTextField textField_nombre;
	private JTextField textField_edad;
	private JComboBox<String> cargos;
	private JComboBox<String> filtroCargo;
	private Miembro miembro;
	private String cargo;
	private JLabel lblCantidad;
	private JLabel lblPromedio;
	private JDialog dialog;
	JButton btnGuardar;
	DefaultTableModel model;
	private TableRowSorter<TableModel> sorter = null;

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 */
	public Altas() throws SQLException { // Constructor que crea instancia de miembro e inicializa la vista.
		miembro = new Miembro();
		initialize();
	}

	public void initialize() throws SQLException { // Metodo que crea la vista /

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 612, 488);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel(); // Panel que contiene la tabla
		panel.setBounds(0, 17, 590, 129);
		contentPane.add(panel);
		panel.setLayout(null);

		JPanel panel_1 = new JPanel(); // Panel que contiene botones, etiquetas y campos de texto.
		panel_1.setBounds(0, 157, 592, 290);
		contentPane.add(panel_1);

		model = new DefaultTableModel();
		table = new JTable(model); // Creacion de tabla

		table.setFont(new Font("Tahoma", Font.PLAIN, 11));
		table.setSurrendersFocusOnKeystroke(true);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				try {
					llenarCampos();
				} catch (NumberFormatException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panel_1.setLayout(null);

		JScrollPane scrollPane = new JScrollPane(table); // Creacion del scroll en el que va insertada la tabla.
		scrollPane.setBounds(10, 5, 572, 274);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		panel_1.add(scrollPane);
		JLabel lblFiltroCargo = new JLabel("Filtrar por Cargo");
		lblFiltroCargo.setHorizontalAlignment(SwingConstants.CENTER);
		lblFiltroCargo.setBounds(13, 48, 105, 14);
		panel.add(lblFiltroCargo);

		filtroCargo = new JComboBox(); // Combobox que sirve para filtrar en la tabla (pero busca en base de datos la
										// informacion)
		filtroCargo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				try {
					pintarTabla();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		filtroCargo.setModel(new DefaultComboBoxModel(new String[] { "Seleccionar", "Junior", "Senior", "Analista" }));
		filtroCargo.setSelectedIndex(0);
		filtroCargo.setBounds(128, 44, 102, 22);
		panel.add(filtroCargo);
		this.pintarTabla();

		JLabel lblNombre = new JLabel("Nombre y Apellidos");
		lblNombre.setBounds(13, 11, 113, 14);
		panel.add(lblNombre);

		textField_nombre = new JTextField();
		textField_nombre.setBounds(139, 8, 171, 20);
		panel.add(textField_nombre);
		textField_nombre.setColumns(10);

		JLabel lblEdad = new JLabel("Edad");
		lblEdad.setBounds(323, 11, 41, 14);
		panel.add(lblEdad);

		textField_edad = new JTextField();
		textField_edad.setColumns(10);
		textField_edad.setBounds(377, 8, 31, 20);
		panel.add(textField_edad);

		JLabel lblCargo = new JLabel("Cargo");
		lblCargo.setBounds(421, 11, 41, 14);
		panel.add(lblCargo);

		cargos = new JComboBox();
		cargos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargo = (String) cargos.getSelectedItem().toString();
			}
		});
		cargos.setModel(new DefaultComboBoxModel(new String[] { "Seleccionar", "Junior", "Senior", "Analista" }));
		cargos.setSelectedIndex(0);
		cargos.setBounds(475, 7, 102, 22);
		panel.add(cargos);

		JLabel lblCantidadMiembros = new JLabel("Cantidad Miembros");
		lblCantidadMiembros.setHorizontalAlignment(SwingConstants.CENTER);
		lblCantidadMiembros.setBounds(262, 48, 113, 14);
		panel.add(lblCantidadMiembros);

		lblCantidad = new JLabel();
		lblCantidad.setForeground(Color.BLUE);
		lblCantidad.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCantidad.setHorizontalAlignment(SwingConstants.CENTER);
		lblCantidad.setBounds(377, 48, 31, 14);
		cantidadMiembros();
		panel.add(lblCantidad);

		JLabel lblPromedioEdades = new JLabel("Promedio Edades");
		lblPromedioEdades.setHorizontalAlignment(SwingConstants.CENTER);
		lblPromedioEdades.setBounds(425, 48, 102, 14);
		panel.add(lblPromedioEdades);

		lblPromedio = new JLabel();
		lblPromedio.setForeground(Color.BLUE);
		lblPromedio.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPromedio.setHorizontalAlignment(SwingConstants.CENTER);
		lblPromedio.setBounds(526, 48, 51, 14);
		promedioEdad();
		panel.add(lblPromedio);

		btnGuardar = new JButton("Guardar Nuevo");
		btnGuardar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				try {
					guardarNuevo();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});
		btnGuardar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try {
					guardarNuevo();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnGuardar.setBounds(12, 94, 133, 23);
		panel.add(btnGuardar);

		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				try {
					actualizar();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnActualizar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					actualizar();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnActualizar.setBounds(157, 94, 133, 23);
		panel.add(btnActualizar);

		JButton btnBorrarFila = new JButton("Borrar Fila");
		btnBorrarFila.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				borrarFila();
			}
		});
		btnBorrarFila.setBounds(302, 94, 133, 23);
		panel.add(btnBorrarFila);

		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				limpiarCampos();
			}
		});

		btnLimpiar.setBounds(447, 94, 133, 23);
		panel.add(btnLimpiar);

		JSeparator separator = new JSeparator();
		separator.setBounds(6, 36, 576, 39);
		panel.add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(6, 73, 576, 2);
		panel.add(separator_1);

		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setBounds(240, 36, 12, 38);
		panel.add(separator_2);

//
	}

	public void pintarTabla() throws SQLException { // Metodo para rellenar la tabla y mostrarla en la vista
		table.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Nombre y Apellidos", "Edad", "Cargo" }) {
					Class[] columnTypes = new Class[] { Integer.class, String.class, Integer.class, String.class };

					public Class getColumnClass(int columnIndex) {
						return columnTypes[columnIndex];
					}
				});
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(0).setPreferredWidth(57);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(60);
		table.getColumnModel().getColumn(3).setPreferredWidth(156);

		ArrayList<Miembro> listaMiembros = new ArrayList<Miembro>(); // Creacion de la lista que guarda miembros de
																		// equipo.

		if (!filtroCargo.getSelectedItem().toString().equals("Seleccionar")) { // Si esta seleccionada alguna opcion del
																				// combobox muestra eso
			if (listaMiembros.size() != 0) {
				listaMiembros = DAOMiembro.getInstance().buscarPorCargo(filtroCargo.getSelectedItem().toString());
			} else {
				JOptionPane.showMessageDialog(null, "No existen miembros con ese cargo");
				filtroCargo.setSelectedItem("Seleccionar");
			}

		} else {
			listaMiembros = DAOMiembro.getInstance().obtenerLista_DAO(); // Sino muestra todos los miembros.
		}

		for (Miembro m : listaMiembros) {
			Object[] fila = new Object[4];

			fila[0] = m.getID();
			fila[1] = m.getNombre();
			fila[2] = m.getEdad();
			fila[3] = m.getCargo();

			((DefaultTableModel) table.getModel()).addRow(fila);

		}

	}

	public void cantidadMiembros() throws SQLException { // Da valor al label cantidad de miembros

		lblCantidad.setText(Integer.toString(miembro.cantidadMiembros()));

	}

	public void promedioEdad() throws SQLException { // Da valor al label promedio de edad
		lblPromedio.setText(Float.toString(miembro.mediaEdad()));
	}

	public void limpiarCampos() { // Vacia los campos que esten rellenos.
		textField_nombre.setText(null);
		textField_edad.setText(null);
		cargos.setSelectedIndex(0);
		miembro.setID(0);
		miembro.setNombre("");
		miembro.setEdad(0);
	}

	public void guardarNuevo() throws SQLException { // Guarda nuevo miembro, si ya existe muestra mensaje.
		if (textField_nombre.getText().length() == 0 || textField_edad.getText().length() == 0
				|| cargos.getSelectedItem() == "Seleccionar") {
			JOptionPane.showMessageDialog(null, "DEBE COMPLETAR TODOS LOS CAMPOS");
		} else {
			miembro.setNombre(textField_nombre.getText());
			try {
				miembro.setEdad(Integer.parseInt(textField_edad.getText()));
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "El valor debe ser un numero");
				e.printStackTrace();
			}
			miembro.setCargo(cargos.getSelectedItem().toString());
		}

		try {
			if (miembro.getID() == 0 && miembro.getNombre().length() != 0
					&& !Integer.toString(miembro.getEdad()).equals("0") && miembro.getCargo() != "Seleccionar") {

				miembro.insert();
				limpiarCampos();
			} else if (miembro.getID() != 0) {
				JOptionPane.showMessageDialog(null, "El usuario ya existe, puede actualizarlo.");
			}
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pintarTabla();
		cantidadMiembros();
		promedioEdad();

	}

	public void actualizar() throws SQLException { // Actualiza miembros ya existentes. Si no existe muestra mensaje.
		if (textField_nombre.getText().length() == 0 || textField_edad.getText().length() == 0
				|| cargos.getSelectedItem() == "Seleccionar") {
			JOptionPane.showMessageDialog(null, "DEBE COMPLETAR TODOS LOS CAMPOS");
		} else {
			miembro.setNombre(textField_nombre.getText());
			miembro.setEdad(Integer.parseInt(textField_edad.getText()));
			miembro.setCargo(cargos.getSelectedItem().toString());
		}

		try {
			if (miembro.getID() != 0 && miembro.getNombre().length() != 0
					&& !Integer.toString(miembro.getEdad()).equals("0") && miembro.getCargo() != "Seleccionar") {
				miembro.update();
				limpiarCampos();
			} else {
				JOptionPane.showMessageDialog(null, "El usuario no existe, debe guardarlo como Nuevo Miembro.");
			}
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		pintarTabla();
		cantidadMiembros();
		promedioEdad();

	}

	public void borrarFila() { // Borra miembro seleccionado.
		DefaultTableModel tm = (DefaultTableModel) table.getModel();
		String dato = String.valueOf(tm.getValueAt(table.getSelectedRow(), 0));

		if (miembro.getID() > 0) {
			try {

				miembro.delete(Integer.parseInt(dato));
				pintarTabla();
				limpiarCampos();
				cantidadMiembros();
				promedioEdad();

			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	public void llenarCampos() throws NumberFormatException, SQLException { // Completa los campos con los datos del
																			// miembros seleccionado.
		DefaultTableModel tm = (DefaultTableModel) table.getModel();
		String dato = String.valueOf(tm.getValueAt(table.getSelectedRow(), 0));

		try {
			miembro = DAOMiembro.getInstance().traerPorId(Integer.parseInt(dato));

			textField_nombre.setText(miembro.getNombre());
			textField_edad.setText(String.valueOf(miembro.getEdad()));
			cargos.setSelectedItem(miembro.getCargo());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
