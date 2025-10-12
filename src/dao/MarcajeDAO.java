// Archivo: dao/MarcajeDAO.java
package dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import model.Marcaje;
import java.time.LocalDate;
import java.time.LocalTime;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class MarcajeDAO {
    
    private final String NOMBRE_ARCHIVO = "marcajes.txt"; // El nombre del archivo sigue siendo .txt
    private final Gson gson;
    
    public MarcajeDAO() {
        // Gson es la herramienta que usaremos para manejar JSON
        this.gson = new GsonBuilder()
            .registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter())
            .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
            .create();
    }
    
    public void guardar(Marcaje nuevoMarcaje) throws IOException {
        try (FileWriter fw = new FileWriter(NOMBRE_ARCHIVO, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            
            // Convertimos el objeto Marcaje a una línea JSON
            String jsonMarcaje = gson.toJson(nuevoMarcaje);
            out.println(jsonMarcaje);
        }
    }
    
    public List<Marcaje> obtenerTodos() throws IOException {
        List<Marcaje> listaDeMarcajes = new ArrayList<>();
        File archivo = new File(NOMBRE_ARCHIVO);

        if (!archivo.exists() || archivo.length() == 0) {
            return listaDeMarcajes;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }
                try {
                    // Convertimos la línea JSON de nuevo a un objeto Marcaje
                    Marcaje marcaje = gson.fromJson(linea, Marcaje.class);
                    listaDeMarcajes.add(marcaje);
                } catch (Exception e) {
                    System.err.println("Error de formato JSON en la línea: " + linea + ". Error: " + e.getMessage());
                }
            }
        }
        return listaDeMarcajes;
    }
    
    public List<Marcaje> obtenerMarcajesPorUsuario(String usuario) throws IOException {
        List<Marcaje> marcajesUsuario = new ArrayList<>();
        List<Marcaje> todosLosMarcajes = obtenerTodos();
        
        for (Marcaje marcaje : todosLosMarcajes) {
            if (marcaje.getUsuario() != null && marcaje.getUsuario().equals(usuario)) {
                marcajesUsuario.add(marcaje);
            }
        }
        return marcajesUsuario;
    }
    
    // Adaptadores para que Gson maneje LocalTime y LocalDate (déjalos al final de la clase)
    private static class LocalTimeTypeAdapter extends TypeAdapter<LocalTime> {
        @Override
        public void write(JsonWriter out, LocalTime value) throws IOException {
            out.value(value.toString());
        }
        @Override
        public LocalTime read(JsonReader in) throws IOException {
            return LocalTime.parse(in.nextString());
        }
    }
    
    private static class LocalDateTypeAdapter extends TypeAdapter<LocalDate> {
        @Override
        public void write(JsonWriter out, LocalDate value) throws IOException {
            out.value(value.toString());
        }
        @Override
        public LocalDate read(JsonReader in) throws IOException {
            return LocalDate.parse(in.nextString());
        }
    }
}