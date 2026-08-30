package model.repository;

import model.data.Arreglo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

// persistencia de arreglos en sistema de archivos local

public class ArchivoArreglos implements RepositorioArreglos {

    // registro de cosas raras durante el procesamiento de lectura del archivo
	
    private static final Logger LOGGER =
            Logger.getLogger(ArchivoArreglos.class.getName());

    private final Path ruta;

    public ArchivoArreglos(String ruta) {
        Objects.requireNonNull(ruta, "la ruta no puede ser nula");
        this.ruta = Path.of(ruta);
    }

    @Override
    
    // anexa la representacion del arreglo al final del archivo existente sin sobrescribirlo
    
    public void guardar(Arreglo arreglo) throws IOException {

        Objects.requireNonNull(arreglo, "el arreglo no puede ser nulo");

        crearDirectorioPadre();

        try (BufferedWriter writer = Files.newBufferedWriter(
                ruta,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {

            writer.write(serializar(arreglo));
            writer.newLine();
        }
    }

    @Override
    
    // procesa el archivo completo ignorando lineas en blanco o con formato invalido,
    // por si se daña el archivo o algo
    
    public List<Arreglo> cargar() throws IOException {

        if (Files.notExists(ruta)) {
            return new ArrayList<>();
        }

        List<Arreglo> arreglos = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(
                ruta,
                StandardCharsets.UTF_8)) { // esto estandariza sin importar el sistema, }
        	                               // se me complica el arch
            reader.lines()
                    .map(String::trim)
                    .filter(linea -> !linea.isBlank())
                    .map(this::deserializar)
                    .filter(Objects::nonNull)
                    .forEach(arreglos::add);
        }

        return arreglos;
    }

    // codifica el estado estrcutural
    
    private String serializar(Arreglo arreglo) {

        int[] datos = arreglo.getDatos();

        
        String valores = Arrays.stream(datos)
                .mapToObj(Integer::toString)
                .collect(java.util.stream.Collectors.joining(","));

        return datos.length + "|" + valores;
    }

    // intenta reconstruir la instancia validando su estructura
    
    private Arreglo deserializar(String linea) {

        // aisla los datos
    	
        String[] partes = linea.split("\\|", 2);

        if (partes.length != 2) {
            registrarLineaInvalida(linea, "formato incorrecto");
            return null;
        }

        try {
            int tamanio = Integer.parseInt(partes[0].trim());

            if (tamanio < 0) {
                registrarLineaInvalida(linea, "el tamaño no puede ser negativo");
                return null;
            }

            String valores = partes[1].trim();


            // previene la instanciacion de arreglos inconsistentes o sin contenido
            
            if (tamanio == 0) {
                if (!valores.isBlank()) {
                    registrarLineaInvalida(
                            linea,
                            "un arreglo de tamaño 0 no puede contener valores"
                    );
                }

                return null;
            }

            if (valores.isBlank()) {
                registrarLineaInvalida(
                        linea,
                        "faltan los valores del arreglo"
                );
                return null;
            }

            // el limite negativo evita que se descarten elementos vacios al final de la cadena
           
            String[] numeros = valores.split(",", -1);

            if (numeros.length != tamanio) {
                registrarLineaInvalida(
                        linea,
                        "el tamaño declarado no coincide con la cantidad de elementos"
                );
                return null;
            }

            int[] datos = Arrays.stream(numeros)
                    .map(String::trim)
                    .mapToInt(Integer::parseInt)
                    .toArray();

            return new Arreglo(datos);

        } catch (NumberFormatException e) {


            registrarLineaInvalida(
                    linea,
                    "contiene valores numéricos inválidos"
            );

            return null;

        } catch (IllegalArgumentException e) {


            registrarLineaInvalida(
                    linea,
                    "los datos no son válidos para Arreglo"
            );

            return null;
        }
    }

    // asegura la existencia del arbol para evitar errores de escritura
    
    private void crearDirectorioPadre() throws IOException {

        Path directorioPadre = ruta.getParent();

        if (directorioPadre != null) {
            Files.createDirectories(directorioPadre);
        }
    }

    // revisa si tiene fallos 
   
    private void registrarLineaInvalida(String linea, String motivo) {

        LOGGER.log(
                Level.WARNING,
                "línea ignorada en el archivo de arreglos: {0}. motivo: {1}",
                new Object[]{linea, motivo}
        );
    }
}