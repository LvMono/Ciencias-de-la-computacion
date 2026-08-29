package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class Arreglo {
    
    private int[] datos;

    // genera un arreglo de numeros enteros aleatorios positivos
    
    public void generarAleatorio(int n) {
        datos = new int[n];
        Random rand = new Random();
        
        for (int i = 0; i < n; i++) {
            datos[i] = rand.nextInt(4000);
        }
    }

    // devuelve una copia exacta del arreglo original
    
    public int[] obtenerCopia() {
        if (datos == null) {
            return new int[0];
        }
        return datos.clone();
    }

    // obtiene el arreglo original
    
    public int[] getDatos() {
        return datos;
    }

    // guarda el arreglo actual en una linea del archivo con un identificador
    // usa append para no sobrescribir el archivo
    
    public void guardarEnArchivo(String nombreArchivo, int n) {
        if (datos == null) {
            System.out.println("no hay datos. genera un arreglo primero.");
            return;
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo, true))) {
            pw.print(n + ":");
            for (int i = 0; i < datos.length; i++) {
                pw.print(datos[i] + (i < datos.length - 1 ? "," : ""));
            }
            pw.println();
            System.out.println("arreglo de tamaño " + n + " guardado.");
        } catch (IOException e) {
            System.err.println("error al escribir el txt: " + e.getMessage());
        }
    }

    // busca un arreglo en el txt mediante su identificador y lo carga
    // retorna true si lo encontro y false si fallo
    
    public boolean cargarDesdeArchivo(String nombreArchivo, int nBuscado) {
        String identificadorBuscado = nBuscado + ":";
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            
            while ((linea = br.readLine()) != null) {
                if (linea.startsWith(identificadorBuscado)) {
                    
                    String contenido = linea.substring(identificadorBuscado.length());
                    String[] valoresTexto = contenido.split(",");
                    
                    datos = new int[valoresTexto.length];
                    for (int i = 0; i < valoresTexto.length; i++) {
                        datos[i] = Integer.parseInt(valoresTexto[i].trim());
                    }
                    
                    System.out.println("arreglo de tamaño " + nBuscado + " cargado con exito.");
                    return true;
                }
            }
            System.out.println("no se encontro el arreglo con tamaño: " + nBuscado);
            
        } catch (IOException e) {
            System.err.println("error al leer: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("formato numerico invalido en el txt.");
        }
        return false;
    }

    // rastrea el txt para devolver un texto con todos los tamanos ya guardados
    
    public String obtenerTamanosGuardados(String nombreArchivo) {
        StringBuilder tamanos = new StringBuilder();
        
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            
            while ((linea = br.readLine()) != null) {
                int indiceDosPuntos = linea.indexOf(":");
                if (indiceDosPuntos != -1) {
                
                	// saca todo lo que está antes del ':'
                    
                	String nStr = linea.substring(0, indiceDosPuntos).trim();
                    tamanos.append(nStr).append(", ");
                }
            }
        } catch (IOException e) {
            return "el txt aun no existe o es ilegible.";
        }
        
        if (tamanos.length() == 0) {
            return "no hay arreglos almacenados.";
        }
        
        return tamanos.substring(0, tamanos.length() - 2);
    }
}
