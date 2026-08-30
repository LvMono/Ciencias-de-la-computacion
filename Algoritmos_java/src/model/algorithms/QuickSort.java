package model.algorithms;

public class QuickSort implements AlgoritmoOrdenamiento {

    @Override
    public void ordenar(int[] datos) {
        quickSort(datos, 0, datos.length - 1);
    }

    private void quickSort(int[] datos, int inicio, int fin) {
        if (inicio < fin) {
            int posicionPivote = particionar(datos, inicio, fin);

            quickSort(datos, inicio, posicionPivote - 1);
            quickSort(datos, posicionPivote + 1, fin);
        }
    }

    private int particionar(int[] datos, int inicio, int fin) {
        int medio = inicio + (fin - inicio) / 2; // aqui escoje el pivoto en el centro
        
        int tempPivote = datos[medio];
        datos[medio] = datos[fin];
        datos[fin] = tempPivote;
        
        int pivote = datos[fin];
        int i = inicio - 1;

        for (int j = inicio; j < fin; j++) {
            if (datos[j] <= pivote) {
                i++;
                int temporal = datos[i];
                datos[i] = datos[j];
                datos[j] = temporal;
            }
        }

        int temporal = datos[i + 1];
        datos[i + 1] = datos[fin];
        datos[fin] = temporal;

        return i + 1;
    }

    @Override
    public String getNombre() {
        return "Quick Sort (Pivote Central)";
    }
}