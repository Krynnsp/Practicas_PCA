package P5.practica_p5p6;

import P5.practica_p5p6.Monitor;
import practica_p5p6.*;

import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        int elem = 60;
        Monitor monitor = new Monitor(); //Para P5
        //MonitorP6 monitor = new MonitorP6(); //Para P6

        Thread hiloproductor1 = new Thread(() -> {
            for (int a = 1; a <= elem/2; a++) {
                monitor.addInt(a);
            }
        });
        Thread hiloproductor2 = new Thread(() -> {
            for (int a = elem; a > elem/2; a--) {
                monitor.addInt(a);
            }
        });

        Thread hiloconsumidorPar = new Thread(()->{
            int elem_recogidos = 0;
            int aux;
            ArrayList<Integer> listaPar = new ArrayList<>();

            while(elem_recogidos<(elem/2)){
                try {
                    aux = monitor.getPar();
                    if(aux > 0) {
                        listaPar.add(aux);
                        elem_recogidos++;
                    }
                }catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Collections.sort(listaPar);
            System.out.println("Números pares ordenados: "+ listaPar.toString());
        });

        Thread hiloconsumidorImpar = new Thread(()->{
            int elem_recogidos = 0;
            int aux;
            ArrayList<Integer> listaImpar = new ArrayList<>();

            while(elem_recogidos<(elem/2)){
                try {
                    aux = monitor.getImpar();
                    if(aux > 0){
                        listaImpar.add(aux);
                        elem_recogidos++;
                        //System.out.println("Cogido elemento " + aux);
                    }
                }catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Collections.sort(listaImpar);
            System.out.println("Números impares ordenados: "+ listaImpar.toString());
        });
        hiloproductor1.start();
        hiloproductor2.start();
        hiloconsumidorPar.start();
        hiloconsumidorImpar.start();

        try{
            hiloproductor1.join();
            hiloproductor2.join();
            hiloconsumidorPar.join();
            hiloconsumidorImpar.join();
        }catch(Exception e){
            e.printStackTrace();
        }
        monitor.imprimirLista();
        System.out.println("El programa finalizó");
    }
}