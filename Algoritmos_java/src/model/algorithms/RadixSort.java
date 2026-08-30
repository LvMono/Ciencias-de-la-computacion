package model.algorithms;

public class RadixSort implements AlgoritmoOrdenamiento {

    @Override
    public void ordenar(int[] datos) {

        if (datos.length == 0) {
            return;
        }

        int maximo = obtenerMaximo(datos);

        for (int exp = 1; maximo / exp > 0; exp *= 10) {

            ordenarPorDigito(datos, exp);
        }
    }

    private int obtenerMaximo(int[] datos) {

        int maximo = datos[0];

        for (int i = 1; i < datos.length; i++) {

            if (datos[i] > maximo) {
                maximo = datos[i];
            }
        }

        return maximo;
    }

    private void ordenarPorDigito(int[] datos, int exp) {

        int n = datos.length;
        int[] salida = new int[n];
        int[] conteo = new int[10];

        for (int dato : datos) {

            int digito = (dato / exp) % 10;
            conteo[digito]++;
        }

        for (int i = 1; i < 10; i++) {

            conteo[i] += conteo[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {

            int digito = (datos[i] / exp) % 10;

            salida[conteo[digito] - 1] = datos[i];

            conteo[digito]--;
        }

        System.arraycopy(salida, 0, datos, 0, n);
    }

    @Override
    public String getNombre() {
        return "Radix Sort";
    }
}