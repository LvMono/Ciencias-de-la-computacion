package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import model.Arreglo;
import model.BinaryTreeSort;
import model.BubbleSort;
import model.QuickSort;
import model.RadixSort;
import model.ShellSort;
import view.Interfaz;

public class Controlador {

    // nombre del archivo para guardar arreglos
    private static final String ARCHIVO = "arreglos_algoritmo.txt";

    // identificadores de cada algoritmo
    private static final int BUBBLE_SORT = 1;
    private static final int RADIX_SORT = 2;
    private static final int QUICK_SORT = 3;
    private static final int SHELL_SORT = 4;
    private static final int BINARY_TREE_SORT = 5;

    // valor centinela devuelto cuando el usuario ingresa texto en lugar de numeros
    private static final int VALOR_INESPERADO = Integer.MIN_VALUE;

    // limite de errores consecutivos para abortar bucles infinitos
    private static final int MAX_ERRORES = 3;

    private final Interfaz vista;

    // solucion de concurrencia para evitar que varios hilos compartan la misma instancia
    // threadlocal garantiza que cada hilo reciba su propio objeto de ordenamiento
    // evitando que el progreso de un hilo sobreescriba los atributos de otro
    private final ThreadLocal<BubbleSort> burbuja = ThreadLocal.withInitial(BubbleSort::new);
    private final ThreadLocal<RadixSort> radix = ThreadLocal.withInitial(RadixSort::new);
    private final ThreadLocal<QuickSort> quick = ThreadLocal.withInitial(QuickSort::new);
    private final ThreadLocal<ShellSort> shell = ThreadLocal.withInitial(ShellSort::new);
    private final ThreadLocal<BinaryTreeSort> arbol = ThreadLocal.withInitial(BinaryTreeSort::new);

    // monitor para sincronizar impresiones en consola y evitar mezcla de textos
    private final Object lockImpresion = new Object();

    // pool de hilos reutilizable para ejecuciones de calentamiento
    private final ExecutorService warmupPool = Executors.newFixedThreadPool(4);

    // inicializa la vista del programa
    public Controlador() {
        vista = new Interfaz();
    }

    // metodo de entrada para iniciar la logica principal
    public void iniciar() {
        Arreglo arreglo = new Arreglo();

        mostrarArreglosGuardados(arreglo);

        if (!prepararArreglo(arreglo)) {
            return;
        }

        ejecutarMenuPrincipal(arreglo);
    }

    // lee el archivo de texto y muestra los tamanos disponibles
    private void mostrarArreglosGuardados(Arreglo arreglo) {
        String tamanos = arreglo.obtenerTamanosGuardados(ARCHIVO);

        vista.mostrarMensaje("\n*** ARREGLOS GUARDADOS ***");

        boolean sinArreglos = tamanos.equals("el txt aun no existe o es ilegible.")
                || tamanos.equals("no hay arreglos almacenados.");

        vista.mostrarMensaje(sinArreglos
                ? "No hay arreglos guardados."
                : "Tamanos disponibles: " + tamanos);

        vista.mostrarMensaje("**************************");
    }

    // gestiona la creacion o carga de un arreglo manejando entradas no validas y bucles
    private boolean prepararArreglo(Arreglo arreglo) {
        int errores = 0;

        while (errores < MAX_ERRORES) {
            vista.mostrarMenuTamano();
            int opcion = leerOpcion();

            switch (opcion) {
                case 1 -> {
                    int n = leerTamano();
                    if (n == VALOR_INESPERADO) {
                        errores++;
                        vista.mostrarMensaje("Valor invalido. Ingrese un numero entero para el tamano.");
                        break;
                    }

                    errores = 0;
                    vista.mostrarMensaje("Generando arreglo de " + n + " elementos...");
                    arreglo.generarAleatorio(n);

                    vista.mostrarMensaje("Guardando arreglo en el archivo...");
                    arreglo.guardarEnArchivo(ARCHIVO, n);

                    vista.mostrarMensaje("Se genero y guardo el arreglo correctamente.");
                    return true;
                }

                case 2 -> {
                    vista.mostrarMensaje("Ingrese el tamano del arreglo que desea cargar:");
                    int n = leerOpcion();

                    if (n == VALOR_INESPERADO) {
                        errores++;
                        vista.mostrarMensaje("Valor invalido. Ingrese un numero entero para el tamano.");
                        break;
                    }

                    errores = 0;
                    vista.mostrarMensaje("Cargando arreglo de " + n + " elementos...");

                    if (arreglo.cargarDesdeArchivo(ARCHIVO, n)) {
                        vista.mostrarMensaje("Arreglo cargado correctamente.");
                        return true;
                    }

                    vista.mostrarMensaje("No se encontro un arreglo con ese tamano.");
                }

                case VALOR_INESPERADO -> {
                    errores++;
                    vista.mostrarMensaje("Entrada no reconocida. Intentos restantes: " + (MAX_ERRORES - errores));
                }

                default -> {
                    errores = 0;
                    vista.mostrarMensaje("Opcion fuera de rango.");
                }
            }
        }

        vista.mostrarMensaje("Multiples errores detectados. Saliendo por seguridad...");
        return false;
    }

    // menu principal con proteccion de intentos para evitar la consola infinita
    private void ejecutarMenuPrincipal(Arreglo arreglo) {
        boolean continuar = true;
        int errores = 0;

        while (continuar && errores < MAX_ERRORES) {
            vista.mostrarMenu();
            int opcion = leerOpcion();

            switch (opcion) {
                case 1 -> { errores = 0; medirBubbleSort(arreglo.obtenerCopia()); }
                case 2 -> { errores = 0; medirRadixSort(arreglo.obtenerCopia()); }
                case 3 -> { errores = 0; medirQuickSort(arreglo.obtenerCopia()); }
                case 4 -> { errores = 0; medirShellSort(arreglo.obtenerCopia()); }
                case 5 -> { errores = 0; medirBinaryTreeSort(arreglo.obtenerCopia()); }
                case 6 -> {
                    errores = 0;
                    vista.mostrarMensaje("Finalizando ejecucion...");
                    warmupPool.shutdown();
                    continuar = false;
                }
                case 7 -> { errores = 0; compararAlgoritmos(arreglo.obtenerCopia()); }
                case VALOR_INESPERADO -> {
                    errores++;
                    vista.mostrarMensaje("Entrada no reconocida. Intentos restantes: " + (MAX_ERRORES - errores));
                }
                default -> {
                    errores = 0;
                    vista.mostrarMensaje("Opcion no valida.");
                }
            }
            vista.mostrarMensaje("");
        }

        if (errores >= MAX_ERRORES) {
            vista.mostrarMensaje("Cierre forzado por multiples entradas no validas consecutivas.");
            warmupPool.shutdown();
        }
    }

    // bloque de metodos para medir un solo algoritmo especifico
    private void medirBubbleSort(int[] arreglo) {
        vista.mostrarMensaje("\nOrdenando con BubbleSort...");
        medirOrdenamiento("BubbleSort", arreglo, BUBBLE_SORT);
    }

    private void medirRadixSort(int[] arreglo) {
        vista.mostrarMensaje("\nOrdenando con RadixSort...");
        medirOrdenamiento("RadixSort", arreglo, RADIX_SORT);
    }

    private void medirQuickSort(int[] arreglo) {
        vista.mostrarMensaje("\nOrdenando con QuickSort...");
        medirOrdenamiento("QuickSort", arreglo, QUICK_SORT);
    }

    private void medirShellSort(int[] arreglo) {
        vista.mostrarMensaje("\nOrdenando con ShellSort...");
        medirOrdenamiento("ShellSort", arreglo, SHELL_SORT);
    }

    private void medirBinaryTreeSort(int[] arreglo) {
        vista.mostrarMensaje("\nOrdenando con BinaryTreeSort...");
        medirOrdenamiento("BinaryTreeSort", arreglo, BINARY_TREE_SORT);
    }

    // ejecuta el cronometro y el algoritmo seleccionado
    private void medirOrdenamiento(String nombre, int[] arreglo, int algoritmo) {
        vista.mostrarMensaje("Iniciando medicion...");

        long inicio = System.nanoTime();
        ejecutarAlgoritmo(arreglo, algoritmo);
        long fin = System.nanoTime();

        mostrarTiempo(nombre, inicio, fin);
    }

    // calcula la diferencia y formatea el resultado en pantalla
    private void mostrarTiempo(String algoritmo, long inicio, long fin) {
        long tiempoNano = fin - inicio;
        double tiempoMili = tiempoNano / 1_000_000.0;

        vista.mostrarMensaje(
                "Tiempo " + algoritmo + ": " + tiempoNano + " ns"
                + " | " + String.format("%.6f", tiempoMili) + " ms"
        );
    }

    // orquesta la comparativa general utilizando ejecucion en paralelo
    private void compararAlgoritmos(int[] original) {
        int n = original.length;

        vista.mostrarMensaje("\n**********************************************");
        vista.mostrarMensaje("        COMPARACION DE ALGORITMOS");
        vista.mostrarMensaje("**********************************************");
        vista.mostrarMensaje("Tamano del arreglo: " + n);

        int repeticiones = obtenerRepeticiones(n);
        vista.mostrarMensaje("Repeticiones por algoritmo: " + repeticiones);

        int[][] casos = prepararCasosEnParalelo(original);
        if (casos == null) {
            return;
        }

        ejecutarComparacionesEnParalelo(casos, repeticiones);

        vista.mostrarMensaje("\n**********************************************");
    }

    // construye los arreglos mejor promedio y peor simultaneamente
    private int[][] prepararCasosEnParalelo(int[] original) {
        vista.mostrarMensaje("\nPreparando los 3 casos en paralelo...");

        ExecutorService prepExecutor = Executors.newFixedThreadPool(3);

        Future<int[]> futureMejor = prepExecutor.submit(() -> crearMejorCaso(original));
        Future<int[]> futurePromedio = prepExecutor.submit(() -> crearCasoPromedio(original));
        Future<int[]> futurePeor = prepExecutor.submit(() -> crearPeorCaso(original));

        try {
            int[] mejorCaso = futureMejor.get();
            int[] casoPromedio = futurePromedio.get();
            int[] peorCaso = futurePeor.get();

            return new int[][] { mejorCaso, casoPromedio, peorCaso };
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            vista.mostrarMensaje("Error en la preparacion de datos: " + e.getMessage());
            return null;
        } finally {
            prepExecutor.shutdown();
        }
    }

    // corre las comparaciones de cada escenario al mismo tiempo
    // esto reduce el tiempo total a costa de una ligera variacion en los nanosegundos
    private void ejecutarComparacionesEnParalelo(int[][] casos, int repeticiones) {
        vista.mostrarMensaje("Ejecutando comparaciones concurrentes...\n");

        ExecutorService comparacionExecutor = Executors.newFixedThreadPool(3);

        comparacionExecutor.execute(() -> ejecutarBloqueComparacion("MEJOR CASO", casos[0], repeticiones));
        comparacionExecutor.execute(() -> ejecutarBloqueComparacion("CASO PROMEDIO", casos[1], repeticiones));
        comparacionExecutor.execute(() -> ejecutarBloqueComparacion("PEOR CASO", casos[2], repeticiones));

        comparacionExecutor.shutdown();

        try {
            comparacionExecutor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // envoltorio para aislar visualmente el reporte de cada caso
    private void ejecutarBloqueComparacion(String titulo, int[] arreglo, int repeticiones) {
        synchronized (lockImpresion) {
            vista.mostrarMensaje("\n------------- " + titulo + " -------------");
        }
        mostrarComparacion(arreglo, repeticiones);
    }

    // ajusta las iteraciones dependiendo del peso del arreglo para no saturar memoria
    private int obtenerRepeticiones(int n) {
        if (n <= 10_000) return 10;
        if (n <= 100_000) return 5;
        if (n <= 1_000_000) return 3;
        return 1;
    }

    // genera un escenario optimo con datos ya ordenados
    private int[] crearMejorCaso(int[] original) {
        imprimirSincronizado("Preparando mejor caso...");
        int[] arreglo = original.clone();
        Arrays.sort(arreglo);
        return arreglo;
    }

    // genera un escenario con entropia usando valores aleatorios
    private int[] crearCasoPromedio(int[] original) {
        imprimirSincronizado("Preparando caso promedio...");
        int[] arreglo = original.clone();
        Random random = new Random();

        for (int i = 0; i < arreglo.length; i++) {
            arreglo[i] = random.nextInt(100_000);
        }
        return arreglo;
    }

    // genera un escenario con los datos ordenados a la inversa
    private int[] crearPeorCaso(int[] original) {
        imprimirSincronizado("Preparando peor caso...");
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

    // imprime los resultados tabulados por cada metodo de ordenamiento
    private void mostrarComparacion(int[] arreglo, int repeticiones) {
        synchronized (lockImpresion) {
            vista.mostrarMensaje("Algoritmo             Mejor(ns)       Promedio(ns)       Peor(ns)");
            vista.mostrarMensaje("-----------------------------------------------------------------------");
        }

        medirYMostrar("BubbleSort", arreglo, repeticiones, BUBBLE_SORT);
        medirYMostrar("RadixSort", arreglo, repeticiones, RADIX_SORT);
        medirYMostrar("QuickSort", arreglo, repeticiones, QUICK_SORT);
        medirYMostrar("ShellSort", arreglo, repeticiones, SHELL_SORT);
        medirYMostrar("BinaryTreeSort", arreglo, repeticiones, BINARY_TREE_SORT);
    }

    // realiza las pasadas oficiales de un algoritmo y consolida sus tiempos
    private void medirYMostrar(String nombre, int[] arreglo, int repeticiones, int algoritmo) {
        calentarEnParalelo(arreglo, algoritmo);

        long mejor = Long.MAX_VALUE;
        long peor = 0;
        long suma = 0;

        for (int i = 1; i <= repeticiones; i++) {
            imprimirSincronizado("Midiendo " + nombre + " (" + i + "/" + repeticiones + ")...");

            int[] copia = arreglo.clone();

            long inicio = System.nanoTime();
            ejecutarAlgoritmo(copia, algoritmo);
            long fin = System.nanoTime();

            long tiempo = fin - inicio;
            suma += tiempo;
            mejor = Math.min(mejor, tiempo);
            peor = Math.max(peor, tiempo);
        }

        long promedio = suma / repeticiones;

        synchronized (lockImpresion) {
            vista.mostrarMensaje(String.format("%-20s %12d %18d %15d", nombre, mejor, promedio, peor));
            vista.mostrarMensaje(String.format("                     Promedio: %.6f ms", promedio / 1_000_000.0));
        }
    }

    // ejecuta tareas fantasmas para que el compilador jit optimice la ruta de codigo
    private void calentarEnParalelo(int[] arreglo, int algoritmo) {
        if (arreglo.length > 1_000_000) {
            return;
        }

        List<Future<?>> tareas = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            int[] copia = arreglo.clone();
            tareas.add(warmupPool.submit(() -> ejecutarAlgoritmo(copia, algoritmo)));
        }

        for (Future<?> tarea : tareas) {
            try {
                tarea.get(10, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException | TimeoutException e) {
                imprimirSincronizado("Fallo en fase de calentamiento: " + e.getMessage());
            }
        }
    }

    // delega la ordenacion a la instancia contenida en el threadlocal
    private void ejecutarAlgoritmo(int[] arreglo, int algoritmo) {
        switch (algoritmo) {
            case BUBBLE_SORT -> burbuja.get().ordenar(arreglo);
            case RADIX_SORT -> radix.get().ordenar(arreglo);
            case QUICK_SORT -> quick.get().ordenar(arreglo);
            case SHELL_SORT -> shell.get().ordenar(arreglo);
            case BINARY_TREE_SORT -> arbol.get().ordenar(arreglo);
            default -> throw new IllegalArgumentException("Algoritmo no soportado: " + algoritmo);
        }
    }

    // agrupa las impresiones bajo un monitor para no entrelazar lineas de diferentes hilos
    private void imprimirSincronizado(String mensaje) {
        synchronized (lockImpresion) {
            vista.mostrarMensaje(mensaje);
        }
    }

    // funcion protectora que intercepta errores si el usuario tipea basura
    // devuelve un entero negativo inusual para que la logica superior lo filtre
    private int leerOpcion() {
        try {
            return vista.capturarOpcion();
        } catch (Exception e) {
            return VALOR_INESPERADO;
        }
    }

    // opera bajo el mismo principio de seguridad que leeropcion
    private int leerTamano() {
        try {
            return vista.pedirTamano();
        } catch (Exception e) {
            return VALOR_INESPERADO;
        }
    }
}
