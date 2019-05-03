import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution {
    public static void main(String[] args) {

        try  {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Введите 0 - сгенерировать ключи, 1 - зашифровать сообщение, 2 - расшифровать криптограмму:");
            int k = Integer.parseInt(reader.readLine());
            if (k != 1 && k != 2 && k != 0) throw new NumberFormatException();

            if (k == 0){
                System.out.println("Введите p, p > 1:");
                String p = reader.readLine();

                if (!Numbers.isNumber(p) || new Numbers(p).compareTo(new Numbers("1")) != 1)
                {
                    throw new NumberFormatException();
                }

                if (!RSA.MillerRabinTest(new Numbers(p), 130)) {
                    System.out.println("Ошибка! Вы ввели составное p!");
                    return;
                }

                System.out.println("Введите q, q > 2 (если p = 2, то введедите q > 3):");
                String q = reader.readLine();

                if (!Numbers.isNumber(q) || new Numbers(q).compareTo(new Numbers("2")) != 1 || (p.equals("2") && new Numbers(q).compareTo(new Numbers("3")) != 1))
                {
                    throw new NumberFormatException();
                }

                if (!RSA.MillerRabinTest(new Numbers(q), 130)) {
                    System.out.println("Ошибка! Вы ввели составное q!");
                    return;
                }

                RSA.generate(new Numbers(p), new Numbers(q));
            }
            else if (k == 1) {
                System.out.println("Введите e:");
                String e = reader.readLine();
                if (!Numbers.isNumber(e)) {
                    throw new NumberFormatException();
                }
                System.out.println("Введите n:");
                String n = reader.readLine();
                if (!Numbers.isNumber(n)) {
                    throw new NumberFormatException();
                }
                RSA.encryption(new Numbers(e), new Numbers(n));
            }
                else {
                System.out.println("Введите d:");
                String d = reader.readLine();
                if (!Numbers.isNumber(d)) {
                    throw new NumberFormatException();
                }
                System.out.println("Введите n:");
                String n = reader.readLine();
                if (!Numbers.isNumber(n)) {
                    throw new NumberFormatException();
                }
                RSA.decryption(new Numbers(d), new Numbers(n));
            }

        }
        catch (IOException | NumberFormatException e) {
            System.out.println("Ошибка! Некорректный ввод");
        }
    }
}