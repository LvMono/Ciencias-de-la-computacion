package view;

import model.data.Arreglo;
import model.data.Resultado;

import java.util.List;
import java.util.Scanner;

public final class Interfaz {

    private final Scanner scanner;

    public Interfaz() {
        scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {

        System.out.println();
        System.out.println("======================================");
        System.out.println("       ALGORITMOS DE ORDENAMIENTO");
        System.out.println("======================================");
        System.out.println("1. generar y guardar arreglo");
        System.out.println("2. cargar arreglos almacenados");
        System.out.println("3. ejecutar un algoritmo");
        System.out.println("0. salir");
        System.out.println("======================================");
    }

    public int leerOpcion() {

        while (true) {

            System.out.print("seleccione una opción: ");

            String entrada = scanner.nextLine().trim();

            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("ingrese una opción válida.");
            }
        }
    }

    
    public int pedirTamanio() {

        while (true) {

            System.out.print("ingrese el tamaño del arreglo: ");

            String entrada = scanner.nextLine().trim();

            try {

                int tamanio = Integer.parseInt(entrada);

                if (tamanio > 0) {
                    return tamanio;
                }

                System.out.println(
                        "el tamaño debe ser mayor que 0."
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "ingrese un número entero válido."
                );
            }
        }
    }


    public int pedirIndiceArreglo(int cantidadArreglos) {

        while (true) {

            System.out.print(
                    "seleccione el número del arreglo: "
            );

            String entrada = scanner.nextLine().trim();

            try {

                int opcion = Integer.parseInt(entrada);

                if (opcion >= 1 && opcion <= cantidadArreglos) {
                    return opcion - 1;
                }

                System.out.println(
                        "seleccione un número entre 1 y "
                                + cantidadArreglos + "."
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "ingrese un número entero válido."
                );
            }
        }
    }


    public void mostrarCantidadArreglos(int cantidad) {

        System.out.println();
        System.out.println(
                "arreglos almacenados: " + cantidad
        );
    }

    public void mostrarResultado(Resultado resultado) {

        System.out.println();
        System.out.println(
                "=============================================================="
        );
        System.out.println(
                "                 RESULTADO DEL ALGORITMO"
        );
        System.out.println(
                "=============================================================="
        );

        System.out.println("algoritmo: " + resultado.getAlgoritmo());
        System.out.println("tamaño: " + resultado.getTamanio());

        if (resultado.esCasoUnico()) {

            System.out.printf(
                    "tiempo: %d ns%n",
                    resultado.getMejorTiempo()
            );

        } else {

            System.out.printf(
                    "%-20s %-15s %-15s %-15s%n",
                    "caso",
                    "mejor (ns)",
                    "promedio (ns)",
                    "peor (ns)"
            );

            System.out.println(
                    "----------------------------------------------------------------"
            );

            System.out.printf(
                    "%-20s %-15d %-15.2f %-15d%n",
                    "mediciones",
                    resultado.getMejorTiempo(),
                    resultado.getPromedioTiempo(),
                    resultado.getPeorTiempo()
            );
        }

        System.out.println(
                "=============================================================="
        );
    }


    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

   
    public void mostrarArreglos(List<Arreglo> arreglos) {

        if (arreglos == null || arreglos.isEmpty()) {
            System.out.println(
                    "no hay arreglos almacenados."
            );
            return;
        }

        System.out.println();
        System.out.println("arreglos disponibles:");

        for (int i = 0; i < arreglos.size(); i++) {

            System.out.printf(
                    "%d. arreglo de %d elementos%n",
                    i + 1,
                    arreglos.get(i).getTamanio()
            );
        }
    }

    public int pedirIndiceAlgoritmo() {

        System.out.println();
        System.out.println("algoritmos disponibles:");
        System.out.println("1. bubble sort");
        System.out.println("2. radix sort");
        System.out.println("3. shell sort");
        System.out.println("4. binary tree sort");
        System.out.println("5. quick sort");

        while (true) {

            System.out.print("seleccione el algoritmo: ");

            String entrada = scanner.nextLine().trim();

            try {

                int opcion = Integer.parseInt(entrada);

                if (opcion >= 1 && opcion <= 5) {
                    return opcion - 1;
                }

                System.out.println(
                        "seleccione un algoritmo entre 1 y 5."
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "ingrese un número entero válido."
                );
            }
        }
    }
    
    public void cerrar() {
        scanner.close();
    }
}