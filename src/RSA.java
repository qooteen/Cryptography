import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;

public class RSA {
    private static Map<Character, Integer> alph = new TreeMap<>();

    static {
        alph.put('0', 18);
        alph.put('1', 28);
        alph.put('2', 38);
        alph.put('3', 48);
        alph.put('4', 58);
        alph.put('5', 68);
        alph.put('6', 78);
        alph.put('7', 118);
        alph.put('8', 128);
        alph.put('9', 138);
        alph.put('а', 148);
        alph.put('б', 158);
        alph.put('в', 168);
        alph.put('г', 178);
        alph.put('д', 218);
        alph.put('е', 228);
        alph.put('ё', 238);
        alph.put('ж', 248);
        alph.put('з', 258);
        alph.put('и', 268);
        alph.put('й', 278);
        alph.put('к', 318);
        alph.put('л', 328);
        alph.put('м', 338);
        alph.put('н', 348);
        alph.put('о', 358);
        alph.put('п', 368);
        alph.put('р', 378);
        alph.put('с', 418);
        alph.put('т', 428);
        alph.put('у', 438);
        alph.put('ф', 448);
        alph.put('х', 458);
        alph.put('ц', 468);
        alph.put('ч', 478);
        alph.put('ш', 518);
        alph.put('щ', 528);
        alph.put('ь', 538);
        alph.put('ы', 548);
        alph.put('ъ', 558);
        alph.put('э', 568);
        alph.put('ю', 578);
        alph.put('я', 618);
        alph.put(',', 638);
        alph.put('.', 648);
        alph.put('-', 658);
        alph.put('?', 668);
        alph.put('!', 678);
        alph.put('А', 718);
        alph.put('Б', 728);
        alph.put('В', 738);
        alph.put('Г', 748);
        alph.put('Д', 758);
        alph.put('Е', 768);
        alph.put('Ё', 778);
        alph.put('Ж', 1118);
        alph.put('З', 1128);
        alph.put('И', 1138);
        alph.put('Й', 1148);
        alph.put('К', 1158);
        alph.put('Л', 1168);
        alph.put('М', 1178);
        alph.put('Н', 1218);
        alph.put('О', 1228);
        alph.put('П', 1238);
        alph.put('Р', 1248);
        alph.put('С', 1258);
        alph.put('Т', 1268);
        alph.put('У', 1278);
        alph.put('Ф', 1318);
        alph.put('Х', 1328);
        alph.put('Ц', 1338);
        alph.put('Ч', 1348);
        alph.put('Ш', 1358);
        alph.put('Щ', 1368);
        alph.put('Ь', 1378);
        alph.put('Ы', 1418);
        alph.put('Ъ', 1428);
        alph.put('Э', 1438);
        alph.put('Ю', 1448);
        alph.put('Я', 1458);
    }

    public static BigInteger rnd(int k) {

        BigInteger resN = new BigInteger("0");
        ArrayList<Boolean> bits = new ArrayList<>();
        Random brand = new Random();
        for (int i = 0; i < k; i++) {
            bits.add(brand.nextBoolean());
        }

        for (int i = 0; i < bits.size(); i++) {
            if (bits.get(i)) {
                resN = resN.add(new BigInteger("2").pow(i));
            }
        }
        return resN;
    }

    public static void generate(BigInteger p, BigInteger q) {

        try (FileWriter writer = new FileWriter("keys.txt")) {
            BigInteger n = p.multiply(q);
            System.out.println("n = " + n);
            BigInteger fi = p.subtract(new BigInteger("1"));
            fi = fi.multiply(q.subtract(new BigInteger("1")));
            System.out.println("fi = " + fi);

            writer.write("n: " + n + "\n");

            BigInteger e;
            BigInteger d;
            int r;

            while (true) {
                Random random = new Random();
                r = 2 + random.nextInt(128);
                e = rnd(r);
                if (e.gcd(fi).equals(new BigInteger("1")) && e.compareTo(new BigInteger("1")) == 1 && e.compareTo(fi) == -1)
                    break;
            }

            writer.write("e: " + e + "\n");

            System.out.println("e = " + e);
            d = RSA.modInverse(e, fi);

            System.out.println("e * d (mod fi) = " + (e.multiply(d)).mod(fi));

            writer.write("d: " + d + "\n");

            System.out.println("d = " + d);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static BigInteger modInverse(BigInteger a, BigInteger m) {
        BigInteger m0 = m;
        BigInteger y = new BigInteger("0");
        BigInteger x = new BigInteger("1");

        if (m.equals(new BigInteger("1")))
            return new BigInteger("0");

        boolean fy = true;
        boolean ft = true;
        boolean fx = true;

        while (a.compareTo(new BigInteger("1")) == 1) {
            BigInteger q = a.divide(m);

            BigInteger t = m;

            m = a.mod(m);

            a = t;

            t = y;

            if (!fy)
                ft = false;
            else ft = true;

            if (x.compareTo(q.multiply(y)) == 1 && fy && fx) {
                y = x.subtract(q.multiply(y));
                fy = true;
            } else if (x.equals(q.multiply(y)) && fy && fx) {
                y = new BigInteger("0");
                fy = true;
            } else if (x.compareTo(q.multiply(y)) == -1 && fy && fx) {

                y = (q.multiply(y)).subtract(x);
                fy = false;

            } else if (x.compareTo(q.multiply(y)) == 1 && !fy && !fx) {
                y = x.subtract(q.multiply(y));
                fy = false;
            } else if (x.equals(q.multiply(y)) && !fy && !fx) {
                y = new BigInteger("0");
                fy = true;
            } else if (x.compareTo(q.multiply(y)) == -1 && !fy && !fx) {
                y = (q.multiply(y)).subtract(x);
                fy = true;
            } else if (!fx && fy) {
                y = x.add(q.multiply(y));
                fy = false;
            } else if (fx && !fy) {
                y = x.add(q.multiply(y));
                fy = true;
            }

            x = t;
            if (!ft) {
                fx = false;
            } else fx = true;
        }

        if (!fx)
            x = m0.subtract(x);

        return x;
    }

    public static ArrayList<BigInteger> convert(BigInteger n) {
        String s = "";
        String mess = "";
        try (FileReader reader = new FileReader("message.txt");
             Scanner scanner = new Scanner(reader)) {
            while (scanner.hasNext()) {
                char[] ch = scanner.nextLine().toCharArray();
                for (Character character : ch)
                    for (Map.Entry<Character, Integer> pair : alph.entrySet()) {
                        if (pair.getKey().equals(character)) {
                            System.out.println("Коды: " + pair.getKey() + " " + pair.getValue());
                            mess += pair.getKey();
                            s += String.valueOf(pair.getValue());
                            break;
                        }
                    }
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<BigInteger> list = new ArrayList<>();
        System.out.println("Сообщение: " + mess);
        System.out.println("Сконкатенированное число: " + s);
        BigInteger number = new BigInteger(s);
        if (number.compareTo(n) != -1) {
            while (!s.equals("")) {
                BigInteger maxx = new BigInteger(s.substring(0, 1));
                for (int i = 1; i < s.length() + 1; i++) {
                    if (new BigInteger(s.substring(0, i)).compareTo(n) != -1) {
                        s = s.substring(i - 1);
                        break;
                    }
                    if (maxx.compareTo(new BigInteger(s.substring(0, i))) == -1 && new BigInteger(s.substring(0, i)).compareTo(n) == -1)
                        maxx = new BigInteger(s.substring(0, i));
                    if (i == s.length()) {
                        s = "";
                    }
                }
                System.out.println(maxx);
                list.add(maxx);
            }
        } else {
            list.add(number);
        }
        return list;
    }

    public static void encryption(BigInteger e, BigInteger n) {
        try (FileWriter writer = new FileWriter("encrypt.txt")) {

            ArrayList<BigInteger> mess = RSA.convert(n);

            for (BigInteger numbers : mess) {
                BigInteger temp = numbers.modPow(e, n);
                writer.write(temp + "\n");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void decryption(BigInteger d, BigInteger n) {
        String s = "";
        System.out.println();
        try (FileReader fileReader = new FileReader("encrypt.txt");
             Scanner scanner = new Scanner(fileReader); FileWriter fileWriter = new FileWriter("decrypt.txt");
             FileWriter fileWriter2 = new FileWriter("decryptBlocks.txt")) {
            while (scanner.hasNextLine()) {
                BigInteger a = new BigInteger(scanner.nextLine());
                BigInteger temp = a.modPow(d, n);
                fileWriter2.write(temp + "\n");
                s += temp.toString();
            }

            System.out.println("Сконкатенированное число: " + s);

            String[] strings = s.split("8");
            for (int i = 0; i < strings.length; i++) {
                strings[i] = strings[i] + "8";
            }
            for (String str : strings)
                for (Map.Entry<Character, Integer> pair : alph.entrySet()) {
                    if (pair.getValue() == Integer.parseInt(str)) {
                        System.out.println("Коды: " + pair.getKey() + " " + pair.getValue());
                        fileWriter.write(pair.getKey());
                        break;
                    }
                }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static BigInteger jacobi(BigInteger a, BigInteger b) {

        if (!a.gcd(b).equals(BigInteger.ONE)) return BigInteger.ZERO;
        BigInteger r = BigInteger.ONE;

        if (a.compareTo(BigInteger.ZERO) == -1){
            a = a.negate();
            if (b.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3))){
                r = r.negate();
            }
        }

        while (!a.equals(BigInteger.ZERO)) {
            BigInteger t = BigInteger.ZERO;
            while (a.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)){
                t = t.add(BigInteger.ONE);
                a  = a.divide(BigInteger.valueOf(2));
            }

            if (!t.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) {
                BigInteger mod = b.mod(BigInteger.valueOf(8));

                if (mod.equals(BigInteger.valueOf(3)) || mod.equals(BigInteger.valueOf(5))) {
                    r = r.negate();
                }
            }

            BigInteger modA = a.mod(BigInteger.valueOf(4));

            if (modA.equals(b.mod(BigInteger.valueOf(4))) && modA.equals(BigInteger.valueOf(3))) {
                r = r.negate();
            }
            BigInteger c = a;
            a = b.mod(c);
            b = c;
        }
        return r;
    }

    public static final BigInteger ONE = new BigInteger("1");
    public static final BigInteger TWO = new BigInteger("2");

    public static Boolean soloveyShtrassIsPrime(BigInteger n, int k) {
        if (n.equals(new BigInteger("3")) || n.equals(new BigInteger("2"))) {
            return true;
        }
        for (int i = 0; i < k; i++) {
            BigInteger a = new BigInteger("2").add((new BigInteger(10, new SecureRandom())).mod(n.subtract(new BigInteger("3"))));
            if (a.gcd(n).compareTo(ONE) == 1) {
                return false;
            }
            BigInteger s = a.modPow((n.subtract(ONE)).divide(TWO), n);
            if (s.equals(n.subtract(ONE))) {
                s = s.subtract(n);
            }
            BigInteger j = jacobi(a, n);
            if (!(s).equals(j)) {
                return false;
            }
        }
        return true;
    }
}
