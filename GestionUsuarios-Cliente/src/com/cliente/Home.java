package com.cliente;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.entities.Funcionalidades;
import com.entities.Roles;
import com.entities.Usuario;
import com.services.FuncionalidadesBeanRemote;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Home {

	private JFrame frmInicio;
	private JTable table;

	private Usuario userLogged;
	private JLabel lblNewLabel;
	private List<Funcionalidades> tasksWhithoutRole;
	private List<Funcionalidades> tasksWhithRole;
	
	FuncionalidadesBeanRemote tasksBean = (FuncionalidadesBeanRemote) InitialContext.doLookup("GestionUsuarios-ModuloEJB/FuncionalidadesBean!com.services.FuncionalidadesBeanRemote");

	
	/**
	 * Create the application.
	 */
	public Home(Usuario user) throws NamingException {				
		initialize();
		
		userLogged = user;

		LoadTasks();
		
		frmInicio.setVisible(true);
	}
	
	private void LoadTasks() {
		
		lblNewLabel.setText("Usuario: " + userLogged.getNombre() + " - " + userLogged.getDocumento());
				
		tasksWhithRole = tasksBean.SelectTasksWithRole(userLogged.getRoles().getNombre());
									
		tasksWhithoutRole = tasksBean.Select();
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
        int rows = table.getRowCount();
        for (int i = rows-1; i >= 0; i--) {
            model.removeRow(i);
        }
		
		for (Funcionalidades task : tasksWhithRole) {
			model.addRow(new Object[]{task.getNombre(), "Con Acceso"});
		}
		
		for (Funcionalidades task : tasksWhithoutRole) {
			
			boolean isAlredy = false;
			for (Funcionalidades taskAlredy : tasksWhithRole) {
				if (taskAlredy.getNombre().equals(task.getNombre())) {
					isAlredy = true;
				}
			}
			
			if (!isAlredy) {
				model.addRow(new Object[]{task.getNombre(), "Sin Acceso"});
			}
			
		}
				
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmInicio = new JFrame();
		frmInicio.setTitle("Inicio");
		frmInicio.setBounds(100, 100, 450, 300);
		frmInicio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frmInicio.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Login l = new Login();
					frmInicio.setVisible(false);
				} catch (NamingException e) {
					e.printStackTrace();
				}
			}
		});
		panel.add(btnLogout);
		
		JButton btnUsuarios = new JButton("Usuarios");
		btnUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Users lu = new Users(userLogged);
					frmInicio.setVisible(false);
				} catch (NamingException e) {
					e.printStackTrace();
				}
			}
		});
		panel.add(btnUsuarios);
		
		JButton btnAcceso = new JButton("Comprobar Acceso");
		btnAcceso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() >= tasksWhithRole.size()) {
					JOptionPane.showMessageDialog(null, "El usuario " + userLogged.getNombre() + " no tiene acceso a la funcionalidad " + tasksWhithoutRole.get(table.getSelectedRow() - tasksWhithRole.size()).getNombre());
				} else {
					JOptionPane.showMessageDialog(null, "El usuario " + userLogged.getNombre() + " tiene acceso a la funcionalidad " + tasksWhithRole.get(table.getSelectedRow()).getNombre());
				}
			}
		});
		panel.add(btnAcceso);
		
		lblNewLabel = new JLabel("Usuario: Nombre - Documento");
		panel.add(lblNewLabel);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Funcionalidad", "Acceso"
			}
		));
		frmInicio.getContentPane().add(table, BorderLayout.CENTER);
	}

}
