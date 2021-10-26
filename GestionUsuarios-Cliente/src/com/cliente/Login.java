package com.cliente;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.exception.ViewsException;
import com.entities.Usuario;
import com.services.UsuariosBeanRemote;

import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login {

	private JFrame frmLogin;
	private JTextField textField;
	
	UsuariosBeanRemote usersBean = (UsuariosBeanRemote) InitialContext.doLookup("GestionUsuarios-ModuloEJB/UsuariosBean!com.services.UsuariosBeanRemote");
	private JTextField passwordField;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frmLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() throws NamingException {
		initialize();
		frmLogin.setVisible(true);
	}

	@SuppressWarnings("deprecation")
	private void LoginCheck() throws ViewsException {
		
		//Si los campos estan nulos se coincidera no se quiere logear
		if (textField.getText() != null && passwordField.getText() != null) {
			
			//Se verifica los campos no esten vacios
			if (textField.getText().trim() == "" || passwordField.getText().trim() == "")
				throw new ViewsException("Debe de llenar ambos campos");
											
			//Se verifica el usuario exista
			Usuario user = usersBean.Login(textField.getText(), passwordField.getText());
			if (user == null)
				throw new ViewsException("El usuario y/o contraseña es incorrecto");
			
			try {
				Home h = new Home(user);
				frmLogin.setVisible(false);
			} catch (NamingException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
			
		}
		
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setTitle("Login");
		frmLogin.setBounds(100, 100, 450, 300);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frmLogin.getContentPane().add(panel, BorderLayout.SOUTH);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					LoginCheck();
				} catch (ViewsException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
		panel.add(btnLogin);
		
		JPanel panel_1 = new JPanel();
		frmLogin.getContentPane().add(panel_1, BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		
		JLabel lblDocumento = new JLabel("Documento:");
		panel_2.add(lblDocumento);
		
		textField = new JTextField();
		panel_2.add(textField);
		textField.setColumns(10);
		
		JPanel panel_2_1 = new JPanel();
		panel_1.add(panel_2_1);
		
		JLabel lblClave = new JLabel("Clave:");
		panel_2_1.add(lblClave);
		
		passwordField = new JTextField();
		passwordField.setColumns(10);
		panel_2_1.add(passwordField);
		
		JPanel panel_3 = new JPanel();
		frmLogin.getContentPane().add(panel_3, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Gestion Usuarios - Smart Bytes");
		panel_3.add(lblNewLabel);
	}

}
