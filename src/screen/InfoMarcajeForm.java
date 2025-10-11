/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screen;
import dao.MarcajeDAO;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import model.Marcaje;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


/**
 *
 * @author jorgmms
 */
public class InfoMarcajeForm extends javax.swing.JFrame {
    private final String usuarioActual;
    private final MarcajeDAO marcajeDAO;

    /**
     * Creates new form InfoMarcaje
     */
    public InfoMarcajeForm(String usuario) {
        initComponents();
        this.usuarioActual = usuario;
        this.marcajeDAO = new MarcajeDAO();
        this.setLocationRelativeTo(null);
        this.setTitle("Historial de Marcajes -" + usuario);
        
        configurarTabla();
        cargarMarcajes(); //Carga datos al iniciar
    }
    
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
    ){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };
        jTable1.setModel(model);
}
    
    private String formatFecha(LocalDate fecha) {
        if (fecha == null){
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
            return "No resgistrado";
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
            java.time.Duration duracion = java.time.Duration.between(marcaje.getHoraEntrada(), marcaje.getHoraSalida());
            
            long horas = duracion.toHours();
            long minutos = duracion.toMinutes() %60;
            
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
    
private void cargarMarcajes() {
    try {
        File archivo = new File("turnosRRHHmarcajes.txt");
        
        System.out.println("Buscando archivo en: " + archivo.getAbsolutePath());
        System.out.println("¿Existe el archivo? " + archivo.exists());
        
        if (!archivo.exists()) {
            lblTotalRegistros.setText("Archivo no encontrado");
            return;
        }

        List<Marcaje> marcajesDelUsuario = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(archivo));
        String linea;
        
        while ((linea = br.readLine()) != null) {
            String[] datos = linea.split(",");
            if (datos.length >= 5 && datos[0].trim().equals(usuarioActual)) {
                Marcaje marcaje = new Marcaje(datos[0].trim());
                
                if (datos.length > 1 && !datos[1].trim().isEmpty()) 
                    marcaje.setHoraEntrada(LocalTime.parse(datos[1].trim()));
                if (datos.length > 2 && !datos[2].trim().isEmpty()) 
                    marcaje.setHoraDescanso1(LocalTime.parse(datos[2].trim()));
                if (datos.length > 3 && !datos[3].trim().isEmpty()) 
                    marcaje.setHoraDescanso2(LocalTime.parse(datos[3].trim()));
                if (datos.length > 4 && !datos[4].trim().isEmpty()) 
                    marcaje.setHoraSalida(LocalTime.parse(datos[4].trim()));
                if (datos.length > 5 && !datos[5].trim().isEmpty()) 
                    marcaje.setFecha(LocalDate.parse(datos[5].trim()));
                
                marcajesDelUsuario.add(marcaje);
            }
        }
        br.close();

        // OBTENER el modelo actual de la tabla (NO crear uno nuevo)
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        
        // LIMPIAR todas las filas existentes
        model.setRowCount(0);
        
        System.out.println("Columnas en la tabla: " + model.getColumnCount());
        System.out.println("Marcajes encontrados: " + marcajesDelUsuario.size());

        // AGREGAR los datos a la tabla EXISTENTE
        for (Marcaje marcaje : marcajesDelUsuario) {
            Object[] fila = {
                marcaje.getFecha() != null ? formatFecha(marcaje.getFecha()) : "Sin ficha",
                marcaje.getHoraEntrada() != null ? formatHora(marcaje.getHoraEntrada()) : "No registrado",
                marcaje.getHoraDescanso1() != null ? formatHora(marcaje.getHoraDescanso1()) : "No registrado",
                marcaje.getHoraDescanso2() != null ? formatHora(marcaje.getHoraDescanso2()) : "No registrado",
                marcaje.getHoraSalida() != null ? formatHora(marcaje.getHoraSalida()) : "No registrado",
                calcularHorasTrabajadas(marcaje),
                obtenerEstadoMarcaje(marcaje)
            };
            model.addRow(fila);
        }
        
        lblTotalRegistros.setText("Registros: " + marcajesDelUsuario.size());
        
        // FORZAR actualización visual
        jTable1.revalidate();
        jTable1.repaint();
        
        System.out.println("Datos agregados a la tabla: " + marcajesDelUsuario.size() + " filas");
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
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
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
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
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
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
