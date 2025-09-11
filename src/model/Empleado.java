package model;

/**
 * Modelo de datos (POJO) que representa a un empleado de la empresa.
 * Contiene todos los atributos y sus respectivos getters y setters.
 * @author erick
 */
public class Empleado {
    
    private String dpi;
    private String nombre;
    private String usuario;
    private String area;
    private String turno;
    private String estado;
    private String correo;
    private String contraseña;
    private String rol;

    /**
     * Constructor por defecto.
     */
    public Empleado() {
    }

    /**
     * Constructor con todos los parámetros para crear un objeto Empleado completo.
     */
    public Empleado(String dpi, String nombre, String usuario, String area,
                    String turno, String estado, String correo, String contraseña, String rol) {
        this.setDPI(dpi); // Usamos el setter para aplicar la validación desde el constructor
        this.nombre = nombre;
        this.usuario = usuario;
        this.area = area;
        this.turno = turno;
        this.estado = estado;
        this.correo = correo;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    // --- Getters y Setters ---

    public String getDPI() {
        return dpi;
    }

    /**
     * Asigna el Documento Personal de Identificación (DPI).
     * @param dpi El DPI. Debe contener exactamente 13 dígitos numéricos.
     * @throws IllegalArgumentException si el DPI no cumple con el formato requerido.
     */
    public void setDPI(String dpi) {
        // Validación 1: No puede ser nulo o vacío
        if (dpi == null || dpi.trim().isEmpty()) {
            throw new IllegalArgumentException("El DPI no puede ser nulo o vacío.");
        }
        
        // Validación 2: Debe contener exactamente 13 caracteres
        if (dpi.length() != 13) {
            throw new IllegalArgumentException("El DPI debe tener exactamente 13 dígitos.");
        }
        
        // Validación 3: Debe contener únicamente dígitos numéricos
        // La expresión regular "\\d+" significa "uno o más dígitos"
        if (!dpi.matches("\\d+")) {
            throw new IllegalArgumentException("El DPI solo puede contener números.");
        }
        
        this.dpi = dpi;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    /**
     * Asigna el nombre de usuario.
     * @param usuario El nombre de usuario. No puede ser nulo o vacío.
     * @throws IllegalArgumentException si el usuario es nulo o está en blanco.
     */
    public void setUsuario(String usuario) {
        if (usuario == null || usuario.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario no puede ser nulo o vacío.");
        }
        this.usuario = usuario;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }
    
    /**
     * Asigna la contraseña.
     * @param contraseña La contraseña. No puede ser nula.
     * @throws IllegalArgumentException si la contraseña es nula.
     */
    public void setContraseña(String contraseña) {
        if (contraseña == null) {
            throw new IllegalArgumentException("La contraseña no puede ser nula.");
        }
        this.contraseña = contraseña;
    }

    public String getRol() {
        return rol;
    }
    
    /**
     * Asigna el rol del empleado.
     * @param rol El rol. No puede ser nulo o vacío.
     * @throws IllegalArgumentException si el rol es nulo o está en blanco.
     */
    public void setRol(String rol) {
        if (rol == null || rol.trim().isEmpty()) {
            throw new IllegalArgumentException("El rol no puede ser nulo o vacío.");
        }
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Empleado{" +
               "dpi='" + dpi + '\'' +
               ", nombre='" + nombre + '\'' +
               ", usuario='" + usuario + '\'' +
               ", area='" + area + '\'' +
               ", turno='" + turno + '\'' +
               ", estado='" + estado + '\'' +
               ", correo='" + correo + '\'' +
               ", contraseña='" + "********" + '\'' + // Ocultamos la contraseña por seguridad
               ", rol='" + rol + '\'' +
               '}';
    }
}



