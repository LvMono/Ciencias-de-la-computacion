package controller;

import model.Arreglo;
import model.BubbleSort;
import model.RadixSort;
import model.QuickSort;
import model.ShellSort;
import model.BinaryTreeSort;
import view.Interfaz;

public class Controlador {

    private Interfaz vista;
    private BubbleSort burbuja;
    private RadixSort radix;
    private QuickSort quick;
    private ShellSort shell;
    private BinaryTreeSort arbol;

    public Controlador() {
        // inicializa la vista y los algoritmos
        this.vista = new Interfaz();
        this.burbuja = new BubbleSort();
        this.radix = new RadixSort();
        this.quick = new QuickSort();
        this.shell = new ShellSort();
        this.arbol = new BinaryTreeSort();
    }

    public void iniciar() {
       
    	// pide al usuario el tamano n por consola al arrancar
        
    	int n = vista.pedirTamano();
        
        Arreglo miArreglo = new Arreglo();
        miArreglo.generarAleatorio(n);
        
        // muestra el arreglo generado originalmente (desordenado)
       
        vista.mostrarMensaje("Arreglo original:");
        vista.mostrarArreglo(miArreglo.getDatos());
        vista.mostrarMensaje("");

        boolean activo = true;
        
        // ciclo principal del menu
        
        while (activo) {
            vista.mostrarMenu();
            int opcion = vista.capturarOpcion();
            
            long inicio = 0;
            long fin = 0;
            
            // evalua la opcion y procesa una copia del arreglo
        
            if (opcion == 1) {
                vista.mostrarMensaje("Ordenando con BubbleSort...");
                int[] copia = miArreglo.obtenerCopia();
                inicio = System.currentTimeMillis();
                burbuja.ordenar(copia);
                fin = System.currentTimeMillis();
                
                vista.mostrarMensaje("Arreglo ordenado:");
                vista.mostrarArreglo(copia);
                vista.mostrarMensaje("Tiempo BubbleSort: " + (fin - inicio) + " milisegundos");
                
            } else if (opcion == 2) {
                vista.mostrarMensaje("Ordenando con RadixSort...");
                int[] copia = miArreglo.obtenerCopia();
                inicio = System.currentTimeMillis();
                radix.ordenar(copia);
                fin = System.currentTimeMillis();
                
                vista.mostrarMensaje("Arreglo ordenado:");
                vista.mostrarArreglo(copia);
                vista.mostrarMensaje("Tiempo RadixSort: " + (fin - inicio) + " milisegundos");
                
            } else if (opcion == 3) {
                vista.mostrarMensaje("Ordenando con QuickSort...");
                int[] copia = miArreglo.obtenerCopia();
                inicio = System.currentTimeMillis();
                quick.ordenar(copia);
                fin = System.currentTimeMillis();
                
                vista.mostrarMensaje("Arreglo ordenado:");
                vista.mostrarArreglo(copia);
                vista.mostrarMensaje("Tiempo QuickSort: " + (fin - inicio) + " milisegundos");
                
            } else if (opcion == 4) {
                vista.mostrarMensaje("Ordenando con ShellSort...");
                int[] copia = miArreglo.obtenerCopia();
                inicio = System.currentTimeMillis();
                shell.ordenar(copia);
                fin = System.currentTimeMillis();
                
                vista.mostrarMensaje("Arreglo ordenado:");
                vista.mostrarArreglo(copia);
                vista.mostrarMensaje("Tiempo ShellSort: " + (fin - inicio) + " milisegundos");
                
            } else if (opcion == 5) {
                vista.mostrarMensaje("Ordenando con BinaryTreeSort...");
                int[] copia = miArreglo.obtenerCopia();
                inicio = System.currentTimeMillis();
                arbol.ordenar(copia);
                fin = System.currentTimeMillis();
                
                vista.mostrarMensaje("Arreglo ordenado:");
                vista.mostrarArreglo(copia);
                vista.mostrarMensaje("Tiempo BinaryTreeSort: " + (fin - inicio) + " milisegundos");
                
            } else if (opcion == 6) {
                vista.mostrarMensaje("Saliendo del programa...");
                activo = false;
                
            } else {
                vista.mostrarMensaje("Opcion no valida intente de nuevo");
            }
            
            vista.mostrarMensaje("");
        }
    }
}
