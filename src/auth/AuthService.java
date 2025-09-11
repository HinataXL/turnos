package auth;

import dao.EmpleadoDAO;
import model.Empleado;

/**
 * Servicio de autenticación que maneja la lógica de negocio para el inicio de sesión.
 * Utiliza un EmpleadoDAO para acceder a los datos de los empleados.
 */
public class AuthService {

    private final EmpleadoDAO empleadoDAO;

    public AuthService() {
        // Al crear el servicio, se instancia el DAO que usará para consultar los datos.
        this.empleadoDAO = new EmpleadoDAO();
    }

    /**
     * Autentica a un usuario basándose en su nombre de usuario y contraseña.
     * @param usuario El nombre de usuario.
     * @param contraseña La contraseña.
     * @return Un objeto AuthResult con el resultado del intento de login.
     */
    public AuthResult autenticar(String usuario, String contraseña) {
        // 1. Busca al empleado en el archivo usando el DAO.
        Empleado empleado = empleadoDAO.buscarPorUsuario(usuario);

        // 2. Regla de negocio: El usuario no existe.
        if (empleado == null) {
            return AuthResult.failure("El usuario no existe.");
        }

        // 3. Regla de negocio: La contraseña no coincide.
        if (!empleado.getContraseña().equals(contraseña)) {
            return AuthResult.failure("Contraseña incorrecta.");
        }

        // 4. Regla de negocio: El empleado está inactivo.
        if (!"Activo".equalsIgnoreCase(empleado.getEstado())) {
            return AuthResult.failure("El usuario está inactivo. Contacte al administrador.");
        }
        
        // 5. Regla de negocio: El empleado no tiene un rol válido para iniciar sesión.
        String rol = empleado.getRol();
        if (rol == null || rol.trim().equalsIgnoreCase("Sin Rol") || rol.trim().isEmpty()) {
            return AuthResult.failure("El usuario no tiene un rol asignado para iniciar sesión.");
        }

        // 6. ¡Éxito! Todas las reglas se cumplieron.
        return AuthResult.success(rol);
    }
}

