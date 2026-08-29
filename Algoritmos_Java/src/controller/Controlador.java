package controller;

import java.util.Arrays;
import java.util.Random;

import model.Arreglo;
import model.BinaryTreeSort;
import model.BubbleSort;
import model.QuickSort;
import model.RadixSort;
import model.ShellSort;
import view.Interfaz;

public class Controlador {

    private Interfaz vista;

    private BubbleSort burbuja;
    private RadixSort radix;
    private QuickSort quick;
    private ShellSort shell;
    private BinaryTreeSort arbol;

    private final String ARCHIVO = "arreglos_algoritmo.txt";

    public Controlador() {

        vista = new Interfaz();

        burbuja = new BubbleSort();
        radix = new RadixSort();
        quick = new QuickSort();
        shell = new ShellSort();
        arbol = new BinaryTreeSort();
    }

    public void iniciar() {

        Arreglo arreglo = new Arreglo();
        boolean arregloListo = false;

        // muestra los tamaños que ya existen en el archivo

        String tamanos = arreglo.obtenerTamanosGuardados(ARCHIVO);

        vista.mostrarMensaje("\n=== ARREGLOS GUARDADOS ===");

        if (tamanos.equals("el txt aun no existe o es ilegible.")
                || tamanos.equals("no hay arreglos almacenados.")) {

            vista.mostrarMensaje("No hay arreglos guardados.");

        } else {

            vista.mostrarMensaje("Tamaños disponibles: " + tamanos);
        }

        vista.mostrarMensaje("==========================");

        // generar o cargar el arreglo
        
        while (!arregloListo) {

            vista.mostrarMenuTamano();

            int opcion = vista.capturarOpcion();

            if (opcion == 1) {

                int n = vista.pedirTamano();

                vista.mostrarMensaje(
                    "Generando arreglo de " + n + " elementos..."
                );

                arreglo.generarAleatorio(n);

                vista.mostrarMensaje("Guardando arreglo en el archivo...");

                arreglo.guardarEnArchivo(ARCHIVO, n);

                vista.mostrarMensaje(
                    "Se genero y guardo el arreglo correctamente."
                );

                arregloListo = true;

            } else if (opcion == 2) {

                vista.mostrarMensaje(
                    "Ingrese el tamaño del arreglo que desea cargar:"
                );

                int n = vista.capturarOpcion();

                vista.mostrarMensaje(
                    "Cargando arreglo de " + n + " elementos..."
                );

                if (arreglo.cargarDesdeArchivo(ARCHIVO, n)) {

                    vista.mostrarMensaje(
                        "Arreglo cargado correctamente."
                    );

                    arregloListo = true;

                } else {

                    vista.mostrarMensaje(
                        "No se encontro un arreglo con ese tamaño."
                    );
                }

            } else {

                vista.mostrarMensaje("Opcion no valida.");
            }
        }

      // menu de los algoritmos
        
        while (arregloListo) {

            vista.mostrarMenu();

            int opcion = vista.capturarOpcion();

            if (opcion == 1) {

                medirBubbleSort(arreglo.obtenerCopia());

            } else if (opcion == 2) {

                medirRadixSort(arreglo.obtenerCopia());

            } else if (opcion == 3) {

                medirQuickSort(arreglo.obtenerCopia());

            } else if (opcion == 4) {

                medirShellSort(arreglo.obtenerCopia());

            } else if (opcion == 5) {

                medirBinaryTreeSort(arreglo.obtenerCopia());

            } else if (opcion == 6) {

                vista.mostrarMensaje("Saliendo del programa...");
                break;

            } else if (opcion == 7) {

                compararAlgoritmos(arreglo.obtenerCopia());

            } else {

                vista.mostrarMensaje("Opcion no valida.");
            }

            vista.mostrarMensaje("");
        }
    }


    // mediciones individuales

    private void medirBubbleSort(int[] arreglo) {

        vista.mostrarMensaje(
            "\nOrdenando con BubbleSort..."
        );

        medirOrdenamiento("BubbleSort", arreglo, 1);
    }


    private void medirRadixSort(int[] arreglo) {

        vista.mostrarMensaje(
            "\nOrdenando con RadixSort..."
        );

        medirOrdenamiento("RadixSort", arreglo, 2);
    }


    private void medirQuickSort(int[] arreglo) {

        vista.mostrarMensaje(
            "\nOrdenando con QuickSort..."
        );

        medirOrdenamiento("QuickSort", arreglo, 3);
    }


    private void medirShellSort(int[] arreglo) {

        vista.mostrarMensaje(
            "\nOrdenando con ShellSort..."
        );

        medirOrdenamiento("ShellSort", arreglo, 4);
    }


    private void medirBinaryTreeSort(int[] arreglo) {

        vista.mostrarMensaje(
            "\nOrdenando con BinaryTreeSort..."
        );

        medirOrdenamiento("BinaryTreeSort", arreglo, 5);
    }


    private void medirOrdenamiento(
            String nombre,
            int[] arreglo,
            int algoritmo) {

        vista.mostrarMensaje(
            "Iniciando medicion..."
        );

        long inicio = System.nanoTime();

        ejecutarAlgoritmo(arreglo, algoritmo);

        long fin = System.nanoTime();

        mostrarTiempo(nombre, inicio, fin);
    }


    // muestra tiempo


    private void mostrarTiempo(
            String algoritmo,
            long inicio,
            long fin) {

        long tiempoNano = fin - inicio;

        double tiempoMili = tiempoNano / 1_000_000.0;

        vista.mostrarMensaje(
            "Tiempo " + algoritmo + ": "
            + tiempoNano + " ns"
            + " | "
            + String.format("%.6f", tiempoMili)
            + " ms"
        );
    }



    // cuando se compara en general


    private void compararAlgoritmos(int[] original) {

        int n = original.length;

        vista.mostrarMensaje(
            "\n=============================================="
        );

        vista.mostrarMensaje(
            "        COMPARACION DE ALGORITMOS"
        );

        vista.mostrarMensaje(
            "=============================================="
        );

        vista.mostrarMensaje(
            "Tamaño del arreglo: " + n
        );

        /*
         * la cantidad de repeticiones de mejor peor y promedio
         * se hace en base a que tan grande es el n escojido
         */
        int repeticiones = obtenerRepeticiones(n);

        vista.mostrarMensaje(
            "Repeticiones por algoritmo: " + repeticiones
        );


        vista.mostrarMensaje(
            "\n------------- MEJOR CASO -------------"
        );

        int[] mejorCaso = crearMejorCaso(original);

        mostrarComparacion(mejorCaso, repeticiones);

        mejorCaso = null;



        vista.mostrarMensaje(
            "\n------------ CASO PROMEDIO ------------"
        );

        int[] casoPromedio = crearCasoPromedio(original);

        mostrarComparacion(casoPromedio, repeticiones);

        casoPromedio = null;



        vista.mostrarMensaje(
            "\n-------------- PEOR CASO --------------"
        );

        int[] peorCaso = crearPeorCaso(original);

        mostrarComparacion(peorCaso, repeticiones);

        peorCaso = null;


        vista.mostrarMensaje(
            "\n=============================================="
        );
    }



    // repeticiones segun el tañano


    private int obtenerRepeticiones(int n) {

        if (n <= 10000) {

            return 10;

        } else if (n <= 100000) {

            return 5;

        } else if (n <= 1000000) {

            return 3;

        } else {

            return 1;
        }
    }



    private int[] crearMejorCaso(int[] original) {

        vista.mostrarMensaje(
            "Preparando mejor caso..."
        );

        int[] arreglo = original.clone();

        Arrays.sort(arreglo);

        return arreglo;
    }




    private int[] crearCasoPromedio(int[] original) {

        vista.mostrarMensaje(
            "Preparando caso promedio..."
        );

        int[] arreglo = original.clone();

        Random random = new Random();

        for (int i = 0; i < arreglo.length; i++) {

            arreglo[i] = random.nextInt(100000);
        }

        return arreglo;
    }




    private int[] crearPeorCaso(int[] original) {

        vista.mostrarMensaje(
            "Preparando peor caso..."
        );

        int[] arreglo = original.clone();

        Arrays.sort(arreglo);

        int izquierda = 0;
        int derecha = arreglo.length - 1;

        while (izquierda < derecha) {

            int temporal = arreglo[izquierda];

            arreglo[izquierda] = arreglo[derecha];
            arreglo[derecha] = temporal;

            izquierda++;
            derecha--;
        }

        return arreglo;
    }



    private void mostrarComparacion(
            int[] arreglo,
            int repeticiones) {

        vista.mostrarMensaje(
            "Algoritmo             Mejor(ns)       Promedio(ns)       Peor(ns)"
        );

        vista.mostrarMensaje(
            "-----------------------------------------------------------------------"
        );


        medirYMostrar(
            "BubbleSort",
            arreglo,
            repeticiones,
            1
        );


        medirYMostrar(
            "RadixSort",
            arreglo,
            repeticiones,
            2
        );


        medirYMostrar(
            "QuickSort",
            arreglo,
            repeticiones,
            3
        );


        medirYMostrar(
            "ShellSort",
            arreglo,
            repeticiones,
            4
        );


        medirYMostrar(
            "BinaryTreeSort",
            arreglo,
            repeticiones,
            5
        );
    }


    // =========================================================
    // MEDIR ALGORITMO
    // =========================================================

    private void medirYMostrar(
            String nombre,
            int[] arreglo,
            int repeticiones,
            int algoritmo) {

        long mejor = Long.MAX_VALUE;
        long peor = 0;
        long suma = 0;


        /*
         * Para arreglos pequeños hacemos unas ejecuciones previas.
         * Para arreglos grandes no se hace calentamiento.
         */
        if (arreglo.length <= 1000000) {

            for (int i = 0; i < 2; i++) {

                int[] copia = arreglo.clone();

                ejecutarAlgoritmo(copia, algoritmo);
            }
        }


        // mediciones
        for (int i = 0; i < repeticiones; i++) {

            vista.mostrarMensaje(
                "Midiendo " + nombre
                + " (" + (i + 1) + "/" + repeticiones + ")..."
            );

            int[] copia = arreglo.clone();

            long inicio = System.nanoTime();

            ejecutarAlgoritmo(copia, algoritmo);

            long fin = System.nanoTime();

            long tiempo = fin - inicio;

            suma += tiempo;


            if (tiempo < mejor) {

                mejor = tiempo;
            }


            if (tiempo > peor) {

                peor = tiempo;
            }
        }


        long promedio = suma / repeticiones;


        vista.mostrarMensaje(
            String.format(
                "%-20s %12d %18d %15d",
                nombre,
                mejor,
                promedio,
                peor
            )
        );


        vista.mostrarMensaje(
            String.format(
                "                     Promedio: %.6f ms",
                promedio / 1_000_000.0
            )
        );
    }


    private void ejecutarAlgoritmo(
            int[] arreglo,
            int algoritmo) {

        if (algoritmo == 1) {

            burbuja.ordenar(arreglo);

        } else if (algoritmo == 2) {

            radix.ordenar(arreglo);

        } else if (algoritmo == 3) {

            quick.ordenar(arreglo);

        } else if (algoritmo == 4) {

            shell.ordenar(arreglo);

        } else if (algoritmo == 5) {

            arbol.ordenar(arreglo);
        }
    }
}
