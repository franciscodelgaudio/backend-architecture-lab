import java.util.List;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {

        System.out.println("Iniciando carda do Heap...");

        while (true) {

            List<Pessoa> lista = new ArrayList<>();

            for (int i = 0; i < 100; i++) {
                lista.add(new Pessoa("Pessoa " + i, i));
            }

            lista = null;

            Thread.sleep(50);
        }
    }
}
