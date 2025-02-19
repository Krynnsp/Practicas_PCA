import practicas_pca.TesterPracticas;
public class EjemploUso {
    public static void main(String[] args) {
        int numberOfThreads = 6;
        TesterPracticas TP = new TesterPracticas(new AlgoritmoBucketSort());
        TP.evaluarPractica(TesterPracticas.Instancias.NUMBER_2500000, numberOfThreads);
        System.out.print("\n");
        TP.evaluarPractica(TesterPracticas.Instancias.NUMBER_5000000, numberOfThreads);
        System.out.print("\n");
        TP.evaluarPractica(TesterPracticas.Instancias.NUMBER_12500000, numberOfThreads);
        System.out.print("\n");
        TP.evaluarPractica(TesterPracticas.Instancias.NUMBER_25000000, numberOfThreads);

    }
}