package com.cliente;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.exceptions.ServiciosException;
import com.entities.Funcionalidades;
import com.entities.Usuario;
import com.services.UsuariosBeanRemote;

import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class Users {

	private JFrame frmUsuarios;
	private JTable table;
	private Usuario userLogged;

	UsuariosBeanRemote usersBean = (UsuariosBeanRemote) InitialContext.doLookup("GestionUsuarios-ModuloEJB/UsuariosBean!com.services.UsuariosBeanRemote");
	
	List<Usuario> users;
	
	/**
	 * Create the application.
	 */
	public Users(Usuario user) throws NamingException {
		initialize();
		
		userLogged = user;
		
		LoadUsers();
		
		frmUsuarios.setVisible(true);
	}
	
	private void LoadUsers() {
		
		users = usersBean.SelectUsers();
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
        int rows = table.getRowCount();
        for (int i = rows-1; i >= 0; i--) {
            model.removeRow(i);
        }
		
		for (Usuario user : users) {
			model.addRow(new Object[]{
					user.getDocumento(),
					user.getApellido(),
					user.getNombre(),
					user.getRoles().getNombre(),
					user.getMail()
			});
		}
		
	}
	
	private void Delete() throws ServiciosException {
		
		if (JOptionPane.showConfirmDialog(null, "Seguro que desea eliminar el Usuario: " + users.get(table.getSelectedRow()).getNombre()) == 0) {
			
			usersBean.DeleteUser(users.get(table.getSelectedRow()).getIdUsuario());
				
			LoadUsers();
			
		}
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmUsuarios = new JFrame();
		frmUsuarios.setTitle("Usuarios");
		frmUsuarios.setBounds(100, 100, 450, 300);
		frmUsuarios.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmUsuarios.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frmUsuarios.getContentPane().add(panel, BorderLayout.SOUTH);
		
		JButton btnCrear = new JButton("Crear");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Formulario f = new Formulario(false, userLogged);
					frmUsuarios.setVisible(false);
				} catch (NamingException e) {
					e.printStackTrace();
				}				
			}
		});
		panel.add(btnCrear);
		
		JButton btnModificacion = new JButton("Modificacion");
		btnModificacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					
					if (table.getSelectedRow() != -1) {
						
						String[] data = {
							users.get(table.getSelectedRow()).getNombre(),
							users.get(table.getSelectedRow()).getApellido(),
							users.get(table.getSelectedRow()).getDocumento(),
							users.get(table.getSelectedRow()).getMail(),
							users.get(table.getSelectedRow()).getClave(),
							users.get(table.getSelectedRow()).getRoles().getNombre()
						};
						
						Formulario f = new Formulario(true, userLogged);
						f.CargarDatos(data);
						
						frmUsuarios.setVisible(false);
						
					}
					
				} catch (NamingException e) {
					e.printStackTrace();
				}				
			}
		});
		panel.add(btnModificacion);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Delete();
				} catch (ServiciosException e) {
					e.printStackTrace();
				}

			}
		});
		panel.add(btnEliminar);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Home h = new Home(userLogged);
					frmUsuarios.setVisible(false);
				} catch (NamingException e) {
					e.printStackTrace();
				}
			}
		});
		panel.add(btnVolver);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Documento", "Apellido", "Nombre", "Rol", "Mail"
			}
		));
		frmUsuarios.getContentPane().add(table, BorderLayout.CENTER);
	}

}
