package view;

import java.util.Scanner;

public class Interfaz {

    private Scanner scanner;

    public Interfaz() {
        scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {

        System.out.println("\n--- menu de algoritmos ---");
        System.out.println("1. BubbleSort");
        System.out.println("2. RadixSort");
        System.out.println("3. QuickSort");
        System.out.println("4. ShellSort");
        System.out.println("5. BinaryTreeSort");
        System.out.println("6. Salir");
        System.out.println("7. Comparar todos los algoritmos");
        System.out.print("Ingrese opcion: ");
    }

    public void mostrarMenuTamano() {

        System.out.println("\n--- origen de los datos ---");
        System.out.println("1. ingresar un tamaño (n) manualmente");
        System.out.println("2. usar tamaño y datos del archivo (.txt)");
        System.out.print("Ingrese opcion: ");
    }

    public int capturarOpcion() {
        return scanner.nextInt();
    }

    public int pedirTamano() {

        System.out.print("Ingrese el tamaño del arreglo (n): ");

        return scanner.nextInt();
    }

    public void mostrarMensaje(String texto) {
        System.out.println(texto);
    }
}
