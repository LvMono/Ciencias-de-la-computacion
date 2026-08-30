package model.algorithms;

public class BubbleSort implements AlgoritmoOrdenamiento {

    @Override
    public void ordenar(int[] datos) {

        int n = datos.length;

        for (int i = 0; i < n - 1; i++) {

            for (int j = 0; j < n - i - 1; j++) {

                if (datos[j] > datos[j + 1]) {

                    int temporal = datos[j];
                    datos[j] = datos[j + 1];
                    datos[j + 1] = temporal;
                }
            }
        }
    }

    @Override
    public String getNombre() {
        return "Bubble Sort";
    }
}