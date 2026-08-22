package model;

public class QuickSort {

    public void ordenar(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        quickSort(arr, 0, arr.length - 1);
    }

    // metodo recursivo que divide y ordena el arreglo
    
    private void quickSort(int[] arr, int inicio, int fin) {
        if (inicio < fin) {
            
        	// encuentra el indice donde queda el pivote
            
        	int indicePivote = particion(arr, inicio, fin);

            // ordena recursivament ela mitad izquierda
            
        	quickSort(arr, inicio, indicePivote - 1);
            
            // ordena recursivamente la mitad derecha
            
        	quickSort(arr, indicePivote + 1, fin);
        }
    }

    // ubica el pivote en su posicion final y separa los elementos
    
    private int particion(int[] arr, int inicio, int fin) {
        
    	// elegimos el ultimo elemento de la porcion como pivote
        
    	int pivote = arr[fin];
        
        // indice del elemento mas pequeño
        
    	int i = inicio - 1;

        for (int j = inicio; j < fin; j++) {
        
            
        	if (arr[j] <= pivote) {
                i++;
                
                // intercambia los elementos
                
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // coloca el pivote en el centro de las dos mitades
        
        int temp = arr[i + 1];
        arr[i + 1] = arr[fin];
        arr[fin] = temp;

        return i + 1;
    }
}
