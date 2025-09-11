/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turnosrrhh;
import screen.LoginForm;
import screen.ConsultarUsuarioForm;
import screen.AgregarEmpleadoForm;



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
            LoginForm LoginForm = new LoginForm();
            LoginForm.setVisible(true);
        });
    }
    
}


