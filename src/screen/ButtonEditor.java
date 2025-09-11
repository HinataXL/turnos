package screen;

import dao.EmpleadoDAO;
import model.EmailSender;

import javax.swing.*;
import java.awt.Component;
import java.awt.event.ActionListener;

/**
 * Editor de celda para JTable que renderiza y maneja un botón.
 * Ejecuta acciones de larga duración (como consultas a DB o envío de correos)
 * en un hilo de fondo usando SwingWorker para no congelar la interfaz de usuario (UI).
 */
public class ButtonEditor extends DefaultCellEditor {
    
    protected JButton button;
    private String label;
    private JTable table;
    private int row; // Variable para almacenar la fila correcta en la que se hizo clic
    private EmpleadoDAO dao; // Usamos una única instancia del DAO
    private boolean isPushed;

    /**
     * Constructor del editor de botones.
     * @param checkBox Un JCheckBox, requerido por el constructor de DefaultCellEditor.
     * @param label El texto que mostrará el botón (ej. "Aceptar").
     * @param table La tabla a la que pertenece este editor.
     * @param dao Una instancia del DAO para realizar las operaciones de base de datos.
     */
    public ButtonEditor(JCheckBox checkBox, String label, JTable table, EmpleadoDAO dao) {
        super(checkBox);
        this.label = label;
        this.table = table;
        this.dao = dao; // Asignamos la instancia del DAO
        
        button = new JButton(label);
        button.setOpaque(true);
        
        // Usamos una expresión lambda (Java 8+) para el ActionListener
        button.addActionListener(e -> fireEditingStopped());
    }

  

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        // Al iniciar la edición (hacer clic), guardamos el número de fila correcto.
        // Este es el método más seguro para obtener la fila.
        this.row = row;
        this.isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            // Obtenemos los datos de la fila que guardamos previamente.
            String usuario = table.getValueAt(this.row, 2).toString();
            String tipo = table.getValueAt(this.row, 0).toString();

            // Deshabilitamos el botón para evitar dobles clics mientras se procesa.
            button.setEnabled(false);

            // Creamos un SwingWorker para ejecutar la tarea en segundo plano.
            SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
                
                /**
                 * Este método se ejecuta en un hilo de fondo.
                 * Aquí van todas las operaciones lentas (DB, red, etc.).
                 */
                @Override
                protected String doInBackground() throws Exception {
                    String correo = dao.obtenerCorreoPorUsuario(usuario);
                    if (correo != null) {
                        String estado = label.equals("Aceptar") ? "aprobada" : "rechazada";
                        String mensaje = String.format("Hola %s, tu solicitud de %s ha sido %s.", usuario, tipo, estado);
                        EmailSender.enviarCorreo(correo, "Estado de tu solicitud", mensaje);
                        return "Correo enviado a " + usuario;
                    } else {
                        return "No se encontró el correo del usuario: " + usuario;
                    }
                }

                /**
                 * Este método se ejecuta en el hilo de Swing (EDT) cuando doInBackground termina.
                 * Es seguro para actualizar la interfaz de usuario (mostrar diálogos, etc.).
                 */
                @Override
                protected void done() {
                    try {
                        String resultado = get(); // Obtiene el resultado de doInBackground()
                        JOptionPane.showMessageDialog(button, resultado, "Proceso Completado", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(button, "Ocurrió un error al procesar la solicitud.", "Error", JOptionPane.ERROR_MESSAGE);
                    } finally {
                        // Volvemos a habilitar el botón sin importar si hubo éxito o error.
                        button.setEnabled(true);
                    }
                }
            };
            
            worker.execute(); // Inicia la ejecución del SwingWorker.
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }
}