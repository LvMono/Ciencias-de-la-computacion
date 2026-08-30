package controller;
import model.data.Arreglo;
import model.data.Resultado;
import model.repository.RepositorioArreglos;
import model.service.EjecutorAlgoritmos;
import model.service.GeneradorArreglos;
import view.Interfaz;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public final class Controlador {

    private final Interfaz interfaz;
    private final GeneradorArreglos generador;
    private final RepositorioArreglos repositorio;
    private final EjecutorAlgoritmos ejecutor;

    public Controlador(
            Interfaz interfaz,
            GeneradorArreglos generador,
            RepositorioArreglos repositorio,
            EjecutorAlgoritmos ejecutor) {

        this.interfaz = Objects.requireNonNull(
                interfaz,
                "la interfaz no puede ser nula"
        );

        this.generador = Objects.requireNonNull(
                generador,
                "el generador no puede ser nulo"
        );

        this.repositorio = Objects.requireNonNull(
                repositorio,
                "el repositorio no puede ser nulo"
        );

        this.ejecutor = Objects.requireNonNull(
                ejecutor,
                "el ejecutor no puede ser nulo"
        );
    }

    // mantiene el ciclo interactivo principal hasta que el usuario decida salir
    
    public void iniciar() {

        boolean continuar = true;

        while (continuar) {

            interfaz.mostrarMenu();

            int opcion = interfaz.leerOpcion();

            continuar = switch (opcion) {

                case 1 -> {
                    generarArreglo();
                    yield true;
                }

                case 2 -> {
                    cargarArreglos();
                    yield true;
                }

                case 3 -> {
                    ejecutarAlgoritmo();
                    yield true;
                }

                case 0 -> {
                    interfaz.mostrarMensaje("programa finalizado.");
                    yield false;
                }

                default -> {
                    interfaz.mostrarMensaje("opción no válida.");
                    yield true;
                }
            };
        }
    }

    // crea nuevo datos y su alamacenamiento en el txt
    
    private void generarArreglo() {

        try {

            int tamanio = interfaz.pedirTamanio();

            Arreglo arreglo = generador.generar(tamanio);

            repositorio.guardar(arreglo);

            interfaz.mostrarMensaje(
                    "arreglo generado y guardado correctamente."
            );

        } catch (IllegalArgumentException e) {

            interfaz.mostrarMensaje(
                    "no fue posible generar el arreglo: "
                            + e.getMessage()
            );

        } catch (IOException e) {

            interfaz.mostrarMensaje(
                    "error al guardar el arreglo."
            );
        }
    }

    // recupera la informacion guardada pa mostral el arreglo guardado
    
    private void cargarArreglos() {

        try {

            List<Arreglo> arreglos = repositorio.cargar();

            if (arreglos.isEmpty()) {
                interfaz.mostrarMensaje(
                        "no hay arreglos almacenados."
                );
                return;
            }

            interfaz.mostrarArreglos(arreglos);

        } catch (IOException e) {

            interfaz.mostrarMensaje(
                    "error al cargar los arreglos."
            );
        }
    }

    // gestiona el flujo completo de seleccion, prueba de rendimiento y presentacion de mmedidas
    
    private void ejecutarAlgoritmo() {

        try {

            List<Arreglo> arreglos = repositorio.cargar();

            if (arreglos.isEmpty()) {
                interfaz.mostrarMensaje(
                        "no hay arreglos almacenados."
                );
                return;
            }

            interfaz.mostrarArreglos(arreglos);

            int indiceArreglo =
                    interfaz.pedirIndiceArreglo(arreglos.size());

            Arreglo arreglo = arreglos.get(indiceArreglo);

            int indiceAlgoritmo =
                    interfaz.pedirIndiceAlgoritmo();

            Resultado resultado =
                    ejecutor.ejecutarUno(
                            arreglo,
                            indiceAlgoritmo
                    );

            interfaz.mostrarResultado(resultado);

        } catch (IOException e) {

            interfaz.mostrarMensaje(
                    "error al cargar los arreglos."
            );

        } catch (IllegalArgumentException e) {

            interfaz.mostrarMensaje(
                    e.getMessage()
            );
        }
    }
}