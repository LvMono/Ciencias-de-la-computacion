package model.algorithms;

public class ShellSort implements AlgoritmoOrdenamiento {

    @Override
    public void ordenar(int[] datos) {

        int n = datos.length;

        // inicia la brecha en la mitad del tamaño y la reduce a la mitad
       
        for (int brecha = n / 2; brecha > 0; brecha /= 2) {

            // realiza un ordenamiento por inserción para los elementos separados
        
        	for (int i = brecha; i < n; i++) {

                int temporal = datos[i];

                int j = i;

                while (j >= brecha && datos[j - brecha] > temporal) {

                    datos[j] = datos[j - brecha];
                    j -= brecha;
                }

                datos[j] = temporal;
            }
        }
    }

    @Override
    public String getNombre() {
        return "Shell Sort";
    }
}