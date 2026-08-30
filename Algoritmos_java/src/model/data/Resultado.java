package model.data;
public final class Resultado {

    private final String algoritmo;
    private final int tamanio;
    private final int repeticiones;
    private final long mejorTiempo;
    private final long peorTiempo;
    private final double promedioTiempo;

    // asegura que las medidas cumplan los limites
    
    public Resultado(
            String algoritmo,
            int tamanio,
            int repeticiones,
            long mejorTiempo,
            long peorTiempo,
            double promedioTiempo) {

        if (algoritmo == null || algoritmo.isBlank()) {
            throw new IllegalArgumentException(
                    "el nombre del algoritmo no puede estar vacío"
            );
        }

        if (tamanio < 0) {
            throw new IllegalArgumentException(
                    "el tamaño no puede ser negativo"
            );
        }

        if (repeticiones <= 0) {
            throw new IllegalArgumentException(
                    "las repeticiones deben ser mayores que 0"
            );
        }

        // evita estados inconsistentes por mediciones temporales invalidas
        
        if (mejorTiempo < 0
                || peorTiempo < 0
                || promedioTiempo < 0) {

            throw new IllegalArgumentException(
                    "los tiempos no pueden ser negativos"
            );
        }

        this.algoritmo = algoritmo;
        this.tamanio = tamanio;
        this.repeticiones = repeticiones;
        this.mejorTiempo = mejorTiempo;
        this.peorTiempo = peorTiempo;
        this.promedioTiempo = promedioTiempo;
    }

    public String getAlgoritmo() {
        return algoritmo;
    }

    public int getTamanio() {
        return tamanio;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public long getMejorTiempo() {
        return mejorTiempo;
    }

    public long getPeorTiempo() {
        return peorTiempo;
    }

    public double getPromedioTiempo() {
        return promedioTiempo;
    }

    // verifica si la muestra viene de una unica vez 
    
    public boolean esCasoUnico() {
        return repeticiones == 1;
    }
}