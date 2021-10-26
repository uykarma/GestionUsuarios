package com.cliente;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.services.UsuariosBean;

import com.exceptions.ServiciosException;
import com.exception.ViewsException;
import com.entities.Roles;
import com.entities.Usuario;
import com.services.UsuariosBeanRemote;

import javax.swing.JComboBox;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class Formulario {

	private JFrame frame;
	static private JTextField textFieldNombre;
	static private JTextField textFieldApellido;
	static private JTextField textFieldDocumento;
	static private JTextField textFieldMail;
	static private JComboBox<String> comboBox;
	private boolean modificar = false;
	static private JPasswordField textFieldClave;
	private static String oldDocument;
	JButton btnVolver;

	Usuario userLogged;
	
	UsuariosBeanRemote usersBean = (UsuariosBeanRemote) InitialContext.doLookup("GestionUsuarios-ModuloEJB/UsuariosBean!com.services.UsuariosBeanRemote");
	
	/**
	 * Create the application.
	 */
	public Formulario(boolean update, Usuario user) throws NamingException {
		initialize();
	
		modificar = update;
		
		userLogged = user;
		
		frame.setVisible(true);
	}
	
	public static void CargarDatos(String[] data) {
		
		textFieldNombre.setText(data[0]);
		textFieldApellido.setText(data[1]);
		textFieldDocumento.setText(data[2]);
		textFieldMail.setText(data[3]);
		textFieldClave.setText(data[4]);
		comboBox.setSelectedItem(data[5]);
		
		textFieldClave.setEnabled(false);
		textFieldDocumento.setEnabled(false);
		
		oldDocument = data[2];
		
	}
	
	private void CleanFields() {
		textFieldNombre.setText("");
		textFieldApellido.setText("");
		textFieldDocumento.setText("");
		textFieldMail.setText("");
		textFieldClave.setText("");
		textFieldNombre.setText("");
		comboBox.setSelectedIndex(1);
	}
	
	private void EfectuarCambios() throws ViewsException, ServiciosException {
		
		//Si los campos no estan nulos, se interpreta se quiere ingresar algo
		if (textFieldNombre.getText() != null && textFieldApellido.getText() != null && textFieldDocumento.getText() != null && textFieldClave.getText() != null) {
						
			//Se verifica los campos no esten vacios
			if (textFieldNombre.getText().trim() == "" || textFieldApellido.getText().trim() == "" || textFieldDocumento.getText().trim() == "" || textFieldClave.getText().trim() == "")
				throw new ViewsException("No puedes dejar campos obligatorios vacios");
													
			Usuario newUser = new Usuario();
			newUser.setNombre(textFieldNombre.getText());
			newUser.setApellido(textFieldApellido.getText());
			newUser.setMail(textFieldMail.getText());
			newUser.setDocumento(textFieldDocumento.getText());
			newUser.setClave(textFieldClave.getText());
			
			Roles r = new Roles();
			r.setIdRol(comboBox.getSelectedIndex() + 1);
			newUser.setRoles(r);
					
			if (modificar) {
							
				newUser.setIdUsuario(usersBean.GetIDUser(oldDocument));
							
				usersBean.UpdateUser(newUser, oldDocument);
							
				JOptionPane.showMessageDialog(null, "El Usuario ha sido modificado correctamente");
				
				btnVolver.doClick();
							
			} else {
							
				usersBean.CreateUser(newUser);
							
				JOptionPane.showMessageDialog(null, "El Usuario ha sido creado correctamente");
						
			}
			
			CleanFields();
		
		}
												
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		
		JButton btnAcion = new JButton("Efectuar Cambios");
		btnAcion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					EfectuarCambios();
				} catch (ViewsException | ServiciosException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
		panel.add(btnAcion);
		
		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Users u = new Users(userLogged);
					frame.setVisible(false);
				} catch (NamingException e) {
					e.printStackTrace();
				}
			}
		});
		panel.add(btnVolver);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		
		JLabel lblNewLabel = new JLabel("Nombre:");
		panel_2.add(lblNewLabel);
		
		textFieldNombre = new JTextField();
		panel_2.add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		JPanel panel_2_1 = new JPanel();
		panel_1.add(panel_2_1);
		
		JLabel lblApellido = new JLabel("Apellido:");
		panel_2_1.add(lblApellido);
		
		textFieldApellido = new JTextField();
		textFieldApellido.setColumns(10);
		panel_2_1.add(textFieldApellido);
		
		JPanel panel_2_1_1 = new JPanel();
		panel_1.add(panel_2_1_1);
		
		JLabel lblDocumento = new JLabel("Documento:");
		panel_2_1_1.add(lblDocumento);
		
		textFieldDocumento = new JTextField();
		textFieldDocumento.setColumns(10);
		panel_2_1_1.add(textFieldDocumento);
		
		JPanel panel_2_1_1_1 = new JPanel();
		panel_1.add(panel_2_1_1_1);
		
		JLabel lblMail = new JLabel("Mail:");
		panel_2_1_1_1.add(lblMail);
		
		textFieldMail = new JTextField();
		textFieldMail.setColumns(10);
		panel_2_1_1_1.add(textFieldMail);
		
		JPanel panel_2_1_1_1_1 = new JPanel();
		panel_1.add(panel_2_1_1_1_1);
		
		JLabel lblRol = new JLabel("Rol:");
		panel_2_1_1_1_1.add(lblRol);
		
		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Administrador", "Experto", "Com\u00FAn"}));
		panel_2_1_1_1_1.add(comboBox);
		
		JPanel panel_2_1_1_1_1_1 = new JPanel();
		panel_1.add(panel_2_1_1_1_1_1);
		
		JLabel lblClave = new JLabel("Clave:");
		panel_2_1_1_1_1_1.add(lblClave);
		
		textFieldClave = new JPasswordField();
		textFieldClave.setColumns(10);
		panel_2_1_1_1_1_1.add(textFieldClave);
	}

}
