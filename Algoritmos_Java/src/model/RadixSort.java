package model;

public class RadixSort {
	
    public void ordenar(int[] arr) {
        int n = arr.length;
        
        if (n == 0) {
            return;
        }

        // encuentra el numero mas grande para saber cuantos digitos tiene
        
        int max = obtenerMaximo(arr, n);

        // aplica counting sort para cada posicion decimal
        // exp es 1 para unidades 10 para decenas 100 para centenas...
        
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSort(arr, n, exp);
        }
    }

    // funcion auxiliar para encontrar el numero mas grande
    
    private int obtenerMaximo(int[] arr, int n) {
        int max = arr[0];
        for (int i = 1; i < n; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }

    // funcion que ordena los numeros basandose en un digito especifico
    
    private void countingSort(int[] arr, int n, int exp) {
    	
        // arreglo temporal para guardar la salida ordenada
        
    	int[] salida = new int[n]; 
        
        // arreglo para contar del 0 al 9
    	
        int[] conteo = new int[10]; 

        // cuenta la frecuencia de cada digito en la posicion actual
        
        for (int i = 0; i < n; i++) {
            conteo[(arr[i] / exp) % 10]++;
        }

        // modifica el conteo para que tenga la posicion final de cada digito
        
        for (int i = 1; i < 10; i++) {
            conteo[i] += conteo[i - 1];
        }

        // construye el arreglo de salida recorriendo el original de derecha a izquierda
        
        for (int i = n - 1; i >= 0; i--) {
            salida[conteo[(arr[i] / exp) % 10] - 1] = arr[i];
            conteo[(arr[i] / exp) % 10]--;
        }

        // copia el arreglo de salida al arreglo original
        
        for (int i = 0; i < n; i++) {
            arr[i] = salida[i];
        }
    }
}
