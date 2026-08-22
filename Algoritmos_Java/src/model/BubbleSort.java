package model;

public class BubbleSort {
	
    public void ordenar(int[] arr) {
        int n = arr.length;
        
      // hace el numero completo de comparaciones
      
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
            	
                // compara elementos adyacentes
                
            	if (arr[j] > arr[j + 1]) {
                
            		// intercambia si el actual es mayor que el siguiente
            		
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
}
