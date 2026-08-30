package model.algorithms;

public class BinaryTreeSort implements AlgoritmoOrdenamiento {

    private static class Nodo {
        private final int valor;
        private Nodo izquierdo;
        private Nodo derecho;
        private int altura;

        private Nodo(int valor) {
            this.valor = valor;
            this.altura = 1; // todo nodo nuevo empieza con altura 1
        }
    }

    @Override
    public void ordenar(int[] datos) {
        if (datos.length == 0) {
            return;
        }

        Nodo raiz = null;
        for (int dato : datos) {
            raiz = insertar(raiz, dato);
        }

        int[] posicion = {0};
        recorrerInorden(raiz, datos, posicion);
    }

    private Nodo insertar(Nodo nodo, int valor) {
       
    	// aqui entra el arbol
        
    	if (nodo == null) {
            return new Nodo(valor);
        }

        // menores a la izquierda, mayores o iguales a la derecha (permite duplicados)
        
    	if (valor < nodo.valor) {
            nodo.izquierdo = insertar(nodo.izquierdo, valor);
        } else {
            nodo.derecho = insertar(nodo.derecho, valor);
        }

        //  actualiza la altura del nodo actual
        
    	nodo.altura = 1 + Math.max(
                obtenerAltura(nodo.izquierdo),
                obtenerAltura(nodo.derecho)
        );

        // Calcular el factor de balance para ver si se descompensó
        
    	int balance = obtenerBalance(nodo);

      //aplicar rotaciones si el nodo se desbalanceo
        
        // caso izquierda-izquierda
        
    	if (balance > 1 && obtenerBalance(nodo.izquierdo) >= 0) {
            return rotarDerecha(nodo);
        }

        // caso izquierda-derecha
        if (balance > 1 && obtenerBalance(nodo.izquierdo) < 0) {
            nodo.izquierdo = rotarIzquierda(nodo.izquierdo);
            return rotarDerecha(nodo);
        }

        // caso derecha-derecha
        if (balance < -1 && obtenerBalance(nodo.derecho) <= 0) {
            return rotarIzquierda(nodo);
        }

        // caso derecha-izquierda
        if (balance < -1 && obtenerBalance(nodo.derecho) > 0) {
            nodo.derecho = rotarDerecha(nodo.derecho);
            return rotarIzquierda(nodo);
        }

        return nodo; // retorna el nodo sin cambios si estaba balanceado
    }

    // --- metodo de balanceo 

    private int obtenerAltura(Nodo nodo) {
        return (nodo == null) ? 0 : nodo.altura;
    }

    private int obtenerBalance(Nodo nodo) {
        return (nodo == null) ? 0 : obtenerAltura(nodo.izquierdo) - obtenerAltura(nodo.derecho);
    }

    private Nodo rotarDerecha(Nodo y) {
        Nodo x = y.izquierdo;
        Nodo t2 = x.derecho;

        // rotacion
        
        x.derecho = y;
        y.izquierdo = t2;

        // actualizar altura
        
        y.altura = Math.max(obtenerAltura(y.izquierdo), obtenerAltura(y.derecho)) + 1;
        x.altura = Math.max(obtenerAltura(x.izquierdo), obtenerAltura(x.derecho)) + 1;

        return x; // nueva raiz
    }

    private Nodo rotarIzquierda(Nodo x) {
        Nodo y = x.derecho;
        Nodo t2 = y.izquierdo;

        // ejecuta la rotacion
     
        y.izquierdo = x;
        x.derecho = t2;

        // qctualiza altura
        
        x.altura = Math.max(obtenerAltura(x.izquierdo), obtenerAltura(x.derecho)) + 1;
        y.altura = Math.max(obtenerAltura(y.izquierdo), obtenerAltura(y.derecho)) + 1;

        return y; // Nueva raíz
    }

    // recorrido in orden

    private void recorrerInorden(Nodo nodo, int[] datos, int[] posicion) {
        if (nodo == null) {
            return;
        }

        recorrerInorden(nodo.izquierdo, datos, posicion);
        datos[posicion[0]] = nodo.valor;
        posicion[0]++;
        recorrerInorden(nodo.derecho, datos, posicion);
    }

    @Override
    public String getNombre() {
        return "AVL Tree Sort (Balanceado)";
    }
}