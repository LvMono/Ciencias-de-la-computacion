package model.service;

import model.data.Arreglo;

public final class GeneradorArreglos {

    private final int minimo;
    private final int maximo;

    // establece los limites generales para los numeros a generar, para modificar mirar el main, 
    // tambien al asignar valores pequeños el quicksort va lento por repeticion
    
    public GeneradorArreglos(int minimo, int maximo) {

        // evita la asignacion de un intervalo numerico contradictorio
    	
        if (minimo > maximo) {
            throw new IllegalArgumentException(
                    "el valor mínimo no puede ser mayor que el máximo"
            );
        }

        this.minimo = minimo;
        this.maximo = maximo;
    }

    // construye el objeto de datos y delega el llenado de sus elementos
    
    public Arreglo generar(int tamanio) {

        // asegura que la estructura de datos tenga espacio util asignado
    
    	if (tamanio <= 0) {
            throw new IllegalArgumentException(
                    "el tamaño debe ser mayor que 0"
            );
        }

        Arreglo arreglo = new Arreglo(tamanio);
        arreglo.generarAleatorio(minimo, maximo);

        return arreglo;
    }
}