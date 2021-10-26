package com.services;

import java.util.List;
import javax.ejb.Remote;
import com.exceptions.ServiciosException;
import com.entities.Usuario;

@Remote
public interface UsuariosBeanRemote {
	void CreateUser(Usuario user) throws ServiciosException;					//Metodo para Crear un Usuario
	void UpdateUser(Usuario user, String oldUser) throws ServiciosException;	//Metodo para Modificar un Usuario
	void DeleteUser(Long id) throws ServiciosException;							//Metodo para Eliminar un Usuario con un ID determinado
	List<Usuario> SelectUsers();												//Metodo para obtener un List con todos los Usuarios
	Long GetIDUser(String nombUsuario);											//Metodo para obtener un Long con la ID de un Usuario con determinado nombre de usuario
	Usuario Login(String nombUsuario, String contrasenia);						//Metodo para verificar un usuario en base a su contraseña y nombre de usuario
}
