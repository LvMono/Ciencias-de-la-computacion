package model.data;

import java.util.List;
import java.util.Objects;

public final class Estadistica {
    
	private Estadistica() {

    }

    // el menor tiempo registrado representa el mejor rendimiento
    
    public static long mejor(List<Long> tiempos) {

        validarTiempos(tiempos);

        return tiempos.stream()
                .mapToLong(Long::longValue)
                .min()
                .orElseThrow();
    }

    // el mayor tiempo registrado representa el peor rendimiento
    
    public static long peor(List<Long> tiempos) {

        validarTiempos(tiempos);

        return tiempos.stream()
                .mapToLong(Long::longValue)
                .max()
                .orElseThrow();
    }


    public static double promedio(List<Long> tiempos) {

        validarTiempos(tiempos);

        return tiempos.stream()
                .mapToLong(Long::longValue)
                .average()
                .orElseThrow();
    }


    // agrupa las medidas calculadas en un objeto resumen
    
    public static Resultado calcular(
            String algoritmo,
            int tamanio,
            List<Long> tiempos) {

        validarTiempos(tiempos);

        long mejor = mejor(tiempos);
        long peor = peor(tiempos);
        double promedio = promedio(tiempos);

        return new Resultado(
                algoritmo,
                tamanio,
                tiempos.size(),
                mejor,
                peor,
                promedio
        );
    }

    // consolida la regla de validacionn
    
    private static void validarTiempos(List<Long> tiempos) {

        Objects.requireNonNull(
                tiempos,
                "la lista de tiempos no puede ser nula"
        );

        if (tiempos.isEmpty()) {
            throw new IllegalArgumentException(
                    "la lista de tiempos no puede estar vacía"
            );
        }

        // previene un nullpointerexception durante el unboxing en los streams
        
        if (tiempos.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException(
                    "la lista no puede contener tiempos nulos"
            );
        }

        // descarta mediciones invalidadas por valores negativos
       
        if (tiempos.stream().anyMatch(tiempo -> tiempo < 0)) {
            throw new IllegalArgumentException(
                    "los tiempos no pueden ser negativos"
            );
        }
    }
}