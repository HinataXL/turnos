package auth;

/**
 * Representa el resultado de un intento de autenticación.
 * Contiene el estado (éxito/fallo), un mensaje para el usuario,
 * y el rol si la autenticación fue exitosa.
 */
public class AuthResult {

    public enum AuthStatus {
        SUCCESS,
        FAILED
    }

    private final AuthStatus status;
    private final String message;
    private final String role;

    private AuthResult(AuthStatus status, String message, String role) {
        this.status = status;
        this.message = message;
        this.role = role;
    }

    /**
     * Crea un resultado de autenticación exitoso.
     * @param role El rol del usuario que inició sesión.
     * @return Un objeto AuthResult de éxito.
     */
    public static AuthResult success(String role) {
        return new AuthResult(AuthStatus.SUCCESS, "Inicio de sesión exitoso.", role);
    }

    /**
     * Crea un resultado de autenticación fallido.
     * @param message El motivo del fallo.
     * @return Un objeto AuthResult de fallo.
     */
    public static AuthResult failure(String message) {
        return new AuthResult(AuthStatus.FAILED, message, null);
    }

    public AuthStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getRole() {
        return role;
    }
}
