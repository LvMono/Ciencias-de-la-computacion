package model;

import java.util.Random;

public class Arreglo {
    
    private int[] datos;

    // genera un arreglo de numeros enteros aleatorios positivos
    
    public void generarAleatorio(int n) {
        datos = new int[n];
        Random rand = new Random();
        
        for (int i = 0; i < n; i++) {
            datos[i] = rand.nextInt(Integer.MAX_VALUE); // cambiar a un valor designado entero si da estalla
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
}
