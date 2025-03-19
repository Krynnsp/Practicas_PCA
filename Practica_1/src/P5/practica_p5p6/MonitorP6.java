package P5.practica_p5p6;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorP6 {
    private final ArrayList<Integer> coleccion = new ArrayList<>();
    private static final int MAX_ELEMENT = 10;
    private Lock lock = new ReentrantLock();
    private Condition hayHueco = lock.newCondition();
    private Condition hayPar = lock.newCondition();
    private Condition hayImpar = lock.newCondition();

    public void addInt(Integer dato){
        lock.lock();
        if (coleccion.size() >= MAX_ELEMENT) {
            try {
                hayHueco.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        coleccion.add(dato);
        if (dato % 2 == 0) {
            hayPar.signal();
        } else {
            hayImpar.signal();
        }
        imprimirLista();
        lock.unlock();
        /* QUE NO SE TE OLVIDE LLAMAR A imprimir_lista() UNA VEZ AÑADIDO */
    }
    public Integer getPar()throws InterruptedException{
        lock.lock();
        Integer par = 0;
        if (!coleccion.isEmpty()) {
            if (coleccion.getLast() % 2 == 0) {
                par = coleccion.removeLast();
                hayHueco.signal();
                imprimirLista();
            } else {
                hayImpar.signal();
                hayPar.await();
                par = getPar();
                hayImpar.signal();
            }
        }
        lock.unlock();
        return par;
        /* QUE NO SE TE OLVIDE LLAMAR A imprimir_lista() UNA VEZ COGIDO  */
    }
    public Integer getImpar()throws InterruptedException{
        lock.lock();
        Integer impar = 0;
        if (!coleccion.isEmpty()) {
            if (coleccion.getLast() % 2 == 1) {
                impar = coleccion.removeLast();
                hayHueco.signal();
                imprimirLista();
            } else {
                hayPar.signal();
                hayImpar.await();
                impar = getImpar();
                hayPar.signal();
            }
        }
        lock.unlock();
        return impar;
        /* QUE NO SE TE OLVIDE LLAMAR A imprimir_lista() UNA VEZ COGIDO  */
    }
    public void imprimirLista(){
        System.out.println("lista actual: "+coleccion.toString());
    }
}