import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

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

                if (!isNumber(p) || new BigInteger(p).compareTo(new BigInteger("1")) != 1)
                {
                    throw new NumberFormatException();
                }

                if (!RSA.soloveyShtrassIsPrime(new BigInteger(p), 130)) {
                    System.out.println("Ошибка! Вы ввели составное p!");
                    return;
                }

                System.out.println("Введите q, q > 2 (если p = 2, то введедите q > 3):");
                String q = reader.readLine();
                if (p.equals(q)) {
                    System.out.println("Ошибка p не должно быть равно q");
                    return;
                }
                if (!isNumber(q) || new BigInteger(q).compareTo(new BigInteger("2")) != 1 || (p.equals("2") && new BigInteger(q).compareTo(new BigInteger("3")) != 1))
                {
                    throw new NumberFormatException();
                }

                if (!RSA.soloveyShtrassIsPrime(new BigInteger(q), 130)) {
                    System.out.println("Ошибка! Вы ввели составное q!");
                    return;
                }

                RSA.generate(new BigInteger(p), new BigInteger(q));
            }
            else if (k == 1) {
                System.out.println("Введите e:");
                String e = reader.readLine();
                if (!isNumber(e)) {
                    throw new NumberFormatException();
                }
                System.out.println("Введите n:");
                String n = reader.readLine();
                if (!isNumber(n)) {
                    throw new NumberFormatException();
                }
                RSA.encryption(new BigInteger(e), new BigInteger(n));
            }
                else {
                System.out.println("Введите d:");
                String d = reader.readLine();
                if (!isNumber(d)) {
                    throw new NumberFormatException();
                }
                System.out.println("Введите n:");
                String n = reader.readLine();
                if (!isNumber(n)) {
                    throw new NumberFormatException();
                }
                RSA.decryption(new BigInteger(d), new BigInteger(n));
            }

        }
        catch (IOException | NumberFormatException e) {
            System.out.println("Ошибка! Некорректный ввод");
        }
    }

    static boolean isNumber(String s) {
        char[] a = s.toCharArray();
        if (a[0] == '0' && a.length > 1) {
            return false;
        } else {
            for(int i = 0; i < a.length; ++i) {
                if (a[i] < '0' || a[i] > '9') {
                    return false;
                }
            }

            return true;
        }
    }
}