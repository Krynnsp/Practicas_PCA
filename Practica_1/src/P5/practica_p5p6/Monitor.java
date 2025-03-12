package P5.practica_p5p6;

import java.util.ArrayList;

public class Monitor {
    private final ArrayList<Integer> coleccion = new ArrayList<>();

    public synchronized void addInt(Integer dato){
        coleccion.add(dato);
        imprimirLista();
        notify();
        /* QUE NO SE TE OLVIDE LLAMAR A imprimir_lista() UNA VEZ AÑADIDO */
    }
    public synchronized Integer getPar()throws InterruptedException{
        Integer par = 0;
        if (!coleccion.isEmpty()) {
            if (coleccion.getLast() % 2 == 0) {
                par = coleccion.removeLast();
                imprimirLista();
                notify();
            } else {
                wait();
                par = getPar();
            }
        }
        return par;
        /* QUE NO SE TE OLVIDE LLAMAR A imprimir_lista() UNA VEZ COGIDO  */
    }
    public synchronized Integer getImpar()throws InterruptedException{
        Integer impar = 0;
        if (!coleccion.isEmpty()) {
            if (coleccion.getLast() % 2 == 1) {
                impar = coleccion.removeLast();
                imprimirLista();
                notify();
            } else {
                wait();
                impar = getImpar();
            }
        }
        return impar;
        /* QUE NO SE TE OLVIDE LLAMAR A imprimir_lista() UNA VEZ COGIDO  */
    }
    public void imprimirLista(){
        System.out.println("lista actual: "+coleccion.toString());
    }
}
