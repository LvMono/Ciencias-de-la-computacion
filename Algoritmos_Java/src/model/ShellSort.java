package model;

public class ShellSort {

    public void ordenar(int[] arr) {
        int n = arr.length;

        // inicia la brecha en la mitad del tamano y se reduce a la mitad en cada ciclo
        
        for (int brecha = n / 2; brecha > 0; brecha /= 2) {
            
            // hace un ordenamiento por insercion para la brecha actual
        	
            for (int i = brecha; i < n; i++) {
                
                // guarda el valor actual a comparar
            	
                int temp = arr[i];
                
                int j;
                
                // desplaza los elementos anteriores que sean mayores que temp
                
                for (j = i; j >= brecha && arr[j - brecha] > temp; j -= brecha) {
                    arr[j] = arr[j - brecha];
                }
                
                // coloca el valor temporal en su posicion correcta
                
                arr[j] = temp;
            }
        }
    }
}
