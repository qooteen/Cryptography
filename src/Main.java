import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        try (FileWriter fileWriter = new FileWriter("primes.txt")){

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Введите число бит в p:");
            int kp = Integer.parseInt(reader.readLine());
            System.out.println("Введите число бит в q:");
            int kq = Integer.parseInt(reader.readLine());
            Numbers p = Generator.generate(kp);
            Numbers q = Generator.generate(kq);

            if (Generator.MillerRabinTest(p, kp) && Generator.MillerRabinTest(p, kq))
            fileWriter.write("p: " + p.toStr() + "\n" + "q: " + q.toStr());
        }
    }
}
