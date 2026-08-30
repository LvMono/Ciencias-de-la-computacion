package model.data;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Arreglo {

    private final int[] datos;

    // asigna espacio asegurando un tamanio valido
    
    public Arreglo(int tamanio) {
        if (tamanio <= 0) {
            throw new IllegalArgumentException("el tamaño debe ser mayor que 0");
        }

        this.datos = new int[tamanio];
    }

    // realiza copia para aislar los datos originales
    
    public Arreglo(int[] datos) {
        if (datos == null || datos.length == 0) {
            throw new IllegalArgumentException("el arreglo no puede ser nulo o vacío");
        }

        this.datos = Arrays.copyOf(datos, datos.length);
    }

    // llena el arreglo con enteros dentro del rango inclusivo
    
    public void generarAleatorio(int minimo, int maximo) {
        if (minimo > maximo) {
            throw new IllegalArgumentException("el minimo no puede ser mayor que el maximo");
        }

        for (int i = 0; i < datos.length; i++) {
            
        	// se suma 1 al maximo porque nextint excluye el limite superior
            
        	datos[i] = ThreadLocalRandom.current().nextInt(minimo, maximo + 1);
        }
    }

    // expone la referencia directa permitiendo modificacion externa
    
    public int[] getDatos() {
        return datos;
    }

    public int getTamanio() {
        return datos.length;
    }

    // entrega una copia nueva para proteger la inmutabilidad
    
    public int[] copiarDatos() {
        return Arrays.copyOf(datos, datos.length);
    }
}