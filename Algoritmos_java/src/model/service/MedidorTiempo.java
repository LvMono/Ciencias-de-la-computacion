package model.service;

import model.algorithms.AlgoritmoOrdenamiento;
import java.util.Objects;

public final class MedidorTiempo {

    // captura el tiempo transcurrido durante la ejecucion de la logica en nanosegundos
	
    public long medir(
            AlgoritmoOrdenamiento algoritmo,
            int[] datos) {

        Objects.requireNonNull(
                algoritmo,
                "el algoritmo no puede ser nulo"
        );

        Objects.requireNonNull(
                datos,
                "el arreglo no puede ser nulo"
        );

        // utiliza el reloj jvm para minimizar el margen de error
    
        long inicio = System.nanoTime();

        algoritmo.ordenar(datos);

        long fin = System.nanoTime();

        return fin - inicio;
    }
}