package dao;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import model.Marcaje;

public class MarcajeDAO {
    
    private final String NOMBRE_ARCHIVO = "marcajes.txt";
    
    // GUARDA UN OBJETO MARCAJE EN EL ARCHIVO DE TEXTO
    
    public void guardar(Marcaje marcaje) throws IOException {
    // try-with-resources ASEGURA QUE EL ARCHIVO SE CIERRE CORRECTAMENTE
       try (FileWriter fw  = new FileWriter(NOMBRE_ARCHIVO, true);
               BufferedWriter bw = new BufferedWriter(fw);
               PrintWriter out = new PrintWriter(bw)) {
           
           out.println(marcaje.toCsvString());
       }
    }
    
    // LEE TODOS LOS MARCAJES DEL ARCHIVO Y LOS DEVUELVE COMO UNA LISTA DE OBJETOS
    public List<Marcaje> obtenerTodos() throws IOException {
        List<Marcaje> listaDeMarcajes = new ArrayList<>();
        
        File archivo = new File (NOMBRE_ARCHIVO);
        if (!archivo.exists()) {
            return listaDeMarcajes; // DEVUELVE LISTA VACIA SI EL ARCHIVO NO EXISTE
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(NOMBRE_ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) !=null) {
                String[] datos = linea.split(",");
                if (datos.length >= 5) {
                    Marcaje marcaje = new Marcaje(datos[0]);// usuario
                                // Convertir strings a LocalTime (manejar posibles nulls)
                if (!datos[1].isEmpty() && !datos[1].equals("null")) {
                    marcaje.setHoraEntrada(LocalTime.parse(datos[1]));
                }
                if (!datos[2].isEmpty() && !datos[2].equals("null")) {
                    marcaje.setHoraDescanso1(LocalTime.parse(datos[2]));
                }
                if (!datos[3].isEmpty() && !datos[3].equals("null")) {
                    marcaje.setHoraDescanso2(LocalTime.parse(datos[3]));
                }
                if (!datos[4].isEmpty() && !datos[4].equals("null")) {
                    marcaje.setHoraSalida(LocalTime.parse(datos[4]));
                }
                
                // Si guardas fecha, agregar aquí
                if (datos.length >= 6 && !datos[5].isEmpty()) {
                    marcaje.setFecha(LocalDate.parse(datos[5]));
                }
                
                listaDeMarcajes.add(marcaje);
                    listaDeMarcajes.add(marcaje);
                }
            } 
        }
        return listaDeMarcajes;
    }
    
    public List<Marcaje> obtenerMarcajesPorUsuario(String usuario) throws IOException {
    List<Marcaje> marcajesUsuario = new ArrayList<>();
    // Usar el método existente obtenerTodos()
    List<Marcaje> todosLosMarcajes = obtenerTodos();
    
    // Filtrar por usuario
    for (Marcaje marcaje : todosLosMarcajes) {
        if (marcaje.getUsuario() != null && marcaje.getUsuario().equals(usuario)) {
            marcajesUsuario.add(marcaje);
        }
        marcajesUsuario.add(marcaje);
    }
    
    return marcajesUsuario;
  }
}
