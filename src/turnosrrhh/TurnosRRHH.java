/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turnosrrhh;
import screen.LoginForm;
import screen.ConsultarUsuarioForm;
import screen.AgregarEmpleadoForm;
import screen.GestionTurnosForm;



/**
 *
 * @author erick
 */
public class TurnosRRHH {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            GestionTurnosForm GestionTurnosForm = new GestionTurnosForm();
            GestionTurnosForm.setVisible(true);
        });
    }
    
}


