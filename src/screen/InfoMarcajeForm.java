package screen;
import dao.MarcajeDAO;
import java.io.IOException;
import model.Marcaje;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;

/**
 *
 * @author jorgmms
 */
public class InfoMarcajeForm extends javax.swing.JFrame {
    private final String usuarioActual;
    private final MarcajeDAO marcajeDAO;

    /**
     * Creates new form InfoMarcaje
     * @param usuario
     */
    public InfoMarcajeForm(String usuario) {
        initComponents();
        this.usuarioActual = usuario;
        this.marcajeDAO = new MarcajeDAO();
        this.setLocationRelativeTo(null);
        this.setTitle("Historial de Marcajes -" + usuario);
        
        configurarTabla();
        cargarMarcajes(); // Carga datos al iniciar
    }
    
    // Método que configura la tabla con los títulos de las columnas
    private void configurarTabla() {
        DefaultTableModel model = new DefaultTableModel(
            new Object[][]{},
            new String[]{
                "Fecha", 
                "Hora Entrada", 
                "Hora Descanso 1", 
                "Hora Descanso 2", 
                "Hora Salida", 
                "Horas Trabajadas",
                "Estado"
            }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hace la tabla no editable
            }
        };
        jTable1.setModel(model);
    }
    
    // Métodos de formato y cálculo de datos
    private String formatFecha(LocalDate fecha) {
        if (fecha == null) {
            return "No registrada";
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return fecha.format(formatter);
        } catch (Exception e) {
            return fecha.toString();
        }
    }

    private String formatHora(LocalTime hora) {
        if (hora == null) {
            return "No registrado";
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            return hora.format(formatter);
        } catch (Exception e) {
            return hora.toString();
        }
    }

    private String calcularHorasTrabajadas(Marcaje marcaje) {
        if (marcaje.getHoraEntrada() == null || marcaje.getHoraSalida() == null) {
            return "Incompleto";
        }
        try {
            Duration duracion = Duration.between(marcaje.getHoraEntrada(), marcaje.getHoraSalida());
            
            long horas = duracion.toHours();
            long minutos = duracion.toMinutes() % 60;
            
            return String.format("%d h %02d min", horas, minutos);
        } catch (Exception e) {
            return "Error calculo";
        }
    }

    private String obtenerEstadoMarcaje(Marcaje marcaje) {
        if (marcaje.getHoraEntrada() != null && marcaje.getHoraSalida() != null) {
            return "Completado";
        } else if (marcaje.getHoraEntrada() != null) {
            return "En progreso";
        } else {
            return "No iniciado";
        }
    }
    
    private void packTableColumns() {
        try {
            for (int column = 0; column < jTable1.getColumnCount(); column++) {
                
                javax.swing.table.TableColumn tableColumn = jTable1.getColumnModel().getColumn(column);
                int preferredWidth = tableColumn.getMinWidth();
                int maxWidth = tableColumn.getMaxWidth();
                
                for (int row = 0; row < jTable1.getRowCount(); row++) {
                    javax.swing.table.TableCellRenderer cellRenderer = jTable1.getCellRenderer( row, column);
                    java.awt.Component c = jTable1.prepareRenderer(cellRenderer, row, column);
                    
                    int width = c.getPreferredSize().width + jTable1.getIntercellSpacing().width;
                    preferredWidth = Math.max(preferredWidth, width);
                    
                    if (preferredWidth >= maxWidth) {
                        preferredWidth = maxWidth;
                        break;
                    }
                }
                tableColumn.setPreferredWidth(preferredWidth + 10);
            }
        } catch (Exception e) {
            System.err.println("Error ajustando columnas: " + e.getMessage());
        }
    }

    // Este es el único y correcto método para cargar los datos en la tabla
    private void cargarMarcajes() {
        try {
            List<Marcaje> marcajesDelUsuario = marcajeDAO.obtenerMarcajesPorUsuario(usuarioActual);
            
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            for (Marcaje marcaje : marcajesDelUsuario) {
                Object[] fila = {
                    formatFecha(marcaje.getFecha()),
                    formatHora(marcaje.getHoraEntrada()),
                    formatHora(marcaje.getHoraDescanso1()),
                    formatHora(marcaje.getHoraDescanso2()),
                    formatHora(marcaje.getHoraSalida()),
                    calcularHorasTrabajadas(marcaje),
                    obtenerEstadoMarcaje(marcaje)
                };
                model.addRow(fila);
            }
            
            lblTotalRegistros.setText("Registros: " + marcajesDelUsuario.size());
            jTable1.revalidate();
            jTable1.repaint();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos del archivo.", "Error de Archivo", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tblMarcajes = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnActualizar = new javax.swing.JButton();
        lblTotalRegistros = new javax.swing.JLabel();
        btnRegresar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setText("INFORMACION DE MARCAJES");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 40, 360, 41));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblMarcajes.setViewportView(jTable1);

        jPanel1.add(tblMarcajes, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 140, 890, 300));

        btnActualizar.setText("ACTUALIZAR");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        jPanel1.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 450, 220, 40));

        lblTotalRegistros.setText("jLabel2");
        jPanel1.add(lblTotalRegistros, new org.netbeans.lib.awtextra.AbsoluteConstraints(1140, 20, 100, 30));

        btnRegresar.setText("REGRESAR");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });
        jPanel1.add(btnRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 580, 150, 70));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 710));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        try {
            cargarMarcajes();
            JOptionPane.showMessageDialog(this, "Datos actualizados correctamente", "Informacion", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actulizar los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InfoMarcajeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InfoMarcajeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InfoMarcajeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InfoMarcajeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblTotalRegistros;
    private javax.swing.JScrollPane tblMarcajes;
    // End of variables declaration//GEN-END:variables
}
