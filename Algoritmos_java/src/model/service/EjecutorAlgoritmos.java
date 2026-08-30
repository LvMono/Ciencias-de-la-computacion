package model.service;

import model.algorithms.AlgoritmoOrdenamiento;
import model.data.Arreglo;
import model.data.Estadistica;
import model.data.Resultado;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class EjecutorAlgoritmos {

    private final List<AlgoritmoOrdenamiento> algoritmos;
    private final MedidorTiempo medidorTiempo;
    
    public EjecutorAlgoritmos(
            List<AlgoritmoOrdenamiento> algoritmos,
            MedidorTiempo medidorTiempo) {

        Objects.requireNonNull(
                algoritmos,
                "la lista de algoritmos no puede ser nula"
        );

        Objects.requireNonNull(
                medidorTiempo,
                "el medidor de tiempo no puede ser nulo"
        );

        if (algoritmos.isEmpty()) {
            throw new IllegalArgumentException(
                    "debe existir al menos un algoritmo"
            );
        }

        // consolida la lista como inmutable para evitar alteraciones durante las pruebas
        
        this.algoritmos = List.copyOf(algoritmos);
        this.medidorTiempo = medidorTiempo;
    }

    // coordina la prueba de un algoritmo especifico devolviendo sus medidas consolidadas
    
    public Resultado ejecutarUno(
            Arreglo arreglo,
            int indiceAlgoritmo) {

        Objects.requireNonNull(
                arreglo,
                "el arreglo no puede ser nulo"
        );

        // previene intentos de ejecucion con referencias fuera de los limites permitidos
        
        if (indiceAlgoritmo < 0 ||
                indiceAlgoritmo >= algoritmos.size()) {

            throw new IllegalArgumentException(
                    "el algoritmo seleccionado no existe."
            );
        }

        AlgoritmoOrdenamiento algoritmo =
                algoritmos.get(indiceAlgoritmo);

        int repeticiones = determinarRepeticiones(
                arreglo.getTamanio()
        );

        List<Long> tiempos = medirAlgoritmo(
                algoritmo,
                arreglo.getDatos(),
                repeticiones
        );

        return Estadistica.calcular(
                algoritmo.getNombre(),
                arreglo.getTamanio(),
                tiempos
        );
    }

    
    // establece la cantidad de ensayos segun el volumen de datos a procesar
    // es el return, 
    
    private int determinarRepeticiones(int tamanio) {

        if (tamanio <= 3_000) {
            return 1;
        }

        if (tamanio <= 30_000) {
            return 1;
        }

        if (tamanio <= 300_000) {
            return 1;
        }

        if (tamanio <= 3_000_000) {
            return 1;
        }

        if (tamanio <= 3_000_000) {
            return 1;
        }

        if (tamanio <= 30_000_000) {
            return 1;
        }

        return 1;
    }

    // obtiene el registro de tiempos repitiendo la prueba segun la cantidad indicada
    //se modifica en el return 
    private List<Long> medirAlgoritmo(
            AlgoritmoOrdenamiento algoritmo,
            int[] original,
            int repeticiones) {

        List<Long> tiempos = new ArrayList<>(repeticiones);

        for (int i = 0; i < repeticiones; i++) {

            // aisla cada iteracion trabajando sobre una copia limpia de los datos
        	
            int[] copia = Arrays.copyOf(
                    original,
                    original.length
            );

            long tiempo = medidorTiempo.medir(
                    algoritmo,
                    copia
            );

            tiempos.add(tiempo);
        }

        return tiempos;
    }
}