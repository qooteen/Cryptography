import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RSA {
    private  static Map<Character, Integer> alph = new TreeMap<>();

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
        alph.put(' ', 628);
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

    public static Numbers rnd(int k) {

        Numbers resN = new Numbers("0");
        ArrayList<Boolean> bits = new ArrayList<>();
        Random brand = new Random();
        for (int i = 0; i < k; i++) {
            bits.add(brand.nextBoolean());
        }

        for (int i = 0; i < bits.size(); i++) {
            if (bits.get(i)) {
                resN = resN.add(Numbers.pow(new Numbers("2"), i));
            }
        }
        return resN;
    }

    public static void generate(Numbers p, Numbers q) {

        try (FileWriter writer = new FileWriter("keys.txt")) {
            Numbers n = new Numbers(p.mul(q).toStr());
            System.out.println("n = " + n.toStr());
            Numbers fi;
//            if (p.toStr().equals(q.toStr())) {
//                fi = new Numbers(new Numbers(Numbers.pow(p, 2).toStr()).sub(p).toStr());
//            }
//            else {
                fi = new Numbers(p.sub(new Numbers("1")).toStr());
                fi = new Numbers(fi.mul(new Numbers(q.sub(new Numbers("1")).toStr())).toStr());
           // }
            System.out.println("fi = " + fi.toStr());

            writer.write("n: " + n.toStr() + "\n");

            Numbers e;
            Numbers d;
            int r;

            while (true) {
                Random random = new Random();
                r = 2 + random.nextInt(128);
                e = rnd(r);
                if (gcd(e, fi).toStr().equals("1") && e.compareTo(new Numbers("1")) == 1 && e.compareTo(fi) == -1)
                    break;
            }

            writer.write("e: " + e.toStr() + "\n");

            System.out.println("e = " + e.toStr());
            d = RSA.modInverse(e, fi);

            System.out.println("e * d (mod fi) = " + Numbers.modPow(new Numbers(e.mul(d).toStr()), new Numbers("1"), fi).toStr());

            writer.write("d: " + d.toStr() + "\n");

            System.out.println("d = " + d.toStr());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static Numbers modInverse(Numbers a, Numbers m)
    {
        Numbers m0 = m;
        Numbers y = new Numbers("0");
        Numbers x = new Numbers("1");

        if (m.toStr().equals("1"))
            return new Numbers("0");

        boolean fy = true;
        boolean ft = true;
        boolean fx = true;

        while (a.compareTo(new Numbers("1")) == 1)
        {
            Numbers q = new Numbers(Numbers.div(a, m).toStr());

            Numbers t = m;

            m = new Numbers(Numbers.mod(a, m).toStr());

            a = t;

            t = y;

            if (!fy)
                ft = false;
            else ft = true;

            if (x.compareTo(new Numbers(q.mul(y).toStr())) == 1 && fy && fx) {
                y = new Numbers(x.sub(new Numbers(q.mul(y).toStr())).toStr());
                fy = true;
            }

            else if (x.toStr().equals(q.mul(y).toStr()) && fy && fx) {
                y = new Numbers("0");
                fy = true;
            }

            else if (x.compareTo(new Numbers(q.mul(y).toStr())) == -1 && fy && fx) {

                y = new Numbers((new Numbers(q.mul(y).toStr())).sub(x).toStr());
                fy = false;

            }
            else if (x.compareTo(new Numbers(q.mul(y).toStr())) == 1 && !fy && !fx) {
                y = new Numbers(x.sub(new Numbers(q.mul(y).toStr())).toStr());
                fy = false;
            }

            else if (x.toStr().equals(q.mul(y).toStr()) && !fy && !fx) {
                y = new Numbers("0");
                fy = true;
            }

            else if (x.compareTo(new Numbers(q.mul(y).toStr())) == -1 && !fy && !fx) {
                y = new Numbers((new Numbers(q.mul(y).toStr())).sub(x).toStr());
                fy = true;
            }
            else if (!fx && fy) {
                y = new Numbers(x.add(new Numbers(q.mul(y).toStr())).toStr());
                fy = false;
            }
            else if (fx && !fy) {
                y = new Numbers(x.add(new Numbers(q.mul(y).toStr())).toStr());
                fy = true;
            }

            x = t;
            if (!ft) {
                fx = false;
            }
            else fx = true;
        }

        if (!fx)
            x = new Numbers(m0.sub(x).toStr());

        return x;
    }

    public static ArrayList<Numbers> convert(Numbers n) {
        String s = "";
        String mess = "";
        try (FileReader reader = new FileReader("message.txt");
        Scanner scanner = new Scanner(reader)){
            while (scanner.hasNext()) {
                char[] ch = scanner.nextLine().toCharArray();
                for (Character character: ch)
                    for (Map.Entry<Character, Integer> pair: alph.entrySet()) {
                        if (pair.getKey().equals(character)) {
                            System.out.println("Коды: " + pair.getKey() + " " + pair.getValue());
                            mess += pair.getKey();
                            s += String.valueOf(pair.getValue());
                            break;
                        }
                 }
            }
            System.out.println();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Numbers> list = new ArrayList<>();
        System.out.println("Сообщение: " + mess);
        System.out.println("Сконкатенированное число: " + s);
        Numbers number = new Numbers(s);
        if (number.compareTo(n) != -1) {
            while (!s.equals("")) {
                Numbers maxx = new Numbers(s.substring(0, 1));
                for (int i = 1; i < s.length() + 1; i++) {
                    if (new Numbers(s.substring(0, i)).compareTo(n) != -1) {
                        s = s.substring(i - 1);
                        break;
                    }
                    if (maxx.compareTo(new Numbers(s.substring(0, i))) == -1 && new Numbers(s.substring(0, i)).compareTo(n) == -1)
                        maxx = new Numbers(s.substring(0, i));
                    if (i == s.length()) {
                        s = "";
                    }
                }
                System.out.println(maxx.toStr());
                list.add(maxx);
            }
        }
        else {
            list.add(number);
        }
            return list;
    }

    public static Numbers gcd(Numbers a, Numbers b) {

        if (a.toStr().equals("0")) return b;
        return gcd(new Numbers(Numbers.mod(b, a).toStr()), a);
    }

    public static void encryption(Numbers e, Numbers n) {
        try (FileWriter writer = new FileWriter("encrypt.txt")) {

            ArrayList<Numbers> mess = RSA.convert(n);

            for (Numbers numbers : mess) {
                Numbers temp = new Numbers(Numbers.modPow(numbers, e, n).toStr());
                writer.write(temp.toStr() + "\n");
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void decryption(Numbers d, Numbers n) {
        String s = "";
        System.out.println();
        try (FileReader fileReader = new FileReader("encrypt.txt");
             Scanner scanner = new Scanner(fileReader);FileWriter fileWriter = new FileWriter("decrypt.txt");
             FileWriter fileWriter2 = new FileWriter("decryptBlocks.txt")){
            while (scanner.hasNextLine()) {
                Numbers a = new Numbers(scanner.nextLine());
                Numbers temp = new Numbers(Numbers.modPow(a, d, n).toStr());
                fileWriter2.write(temp.toStr() + "\n");
                s += temp.toStr();
            }

            System.out.println("Сконкатенированное число: " + s);

            String[] strings = s.split("8");
            for (int i = 0; i < strings.length; i++) {
                strings[i] = strings[i] + "8";
            }
            for (String str: strings)
                for (Map.Entry<Character, Integer> pair: alph.entrySet()) {
                    if (pair.getValue() == Integer.parseInt(str)) {
                        System.out.println("Коды: " + pair.getKey() + " " + pair.getValue());
                        fileWriter.write(pair.getKey());
                        break;
                    }
                }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    static int bits(Numbers n) {
        int k = 0;
        while (!n.toStr().equals("0")) {
            n = Numbers.div(n, new Numbers("2"));
            k++;
        }
        return k;
    }

    static boolean MillerRabinTest(Numbers n, int k) {

        if (n.toStr().equals("2") || n.toStr().equals("3"))
            return true;

        if (n.compareTo(new Numbers("2")) == - 1 || Numbers.mod(n, new Numbers("2")).toStr().equals("0"))
            return false;

        Numbers t = new Numbers(n.sub(new Numbers("1")).toStr());

        long s = 0;

        while (Numbers.mod(t, new Numbers("2")).toStr().equals("0"))
        {
            t = new Numbers(Numbers.div(t, new Numbers("2")).toStr());
            s++;
        }

        for (int i = 0; i < k; i++)
        {
            byte[] _a = new byte[RSA.bits(n)];

            Numbers a;

            do
            {
                Random rng = new Random();
                for (int j = 0; j < _a.length; j++)
                    _a[j] = rng.nextBoolean() ? (byte)1: (byte)0;

                a = new Numbers("0");

                for (int j = 0; j < _a.length; j++) {
                    if (_a[j] == 1) {
                        a = a.add(Numbers.pow(new Numbers("2"), j));
                    }
                }
            }

            while (a.compareTo(new Numbers("2")) == -1 || (a.compareTo(new Numbers(n.sub(new Numbers("2")).toStr())) == 1 || a.toStr().equals(n.sub(new Numbers("2")).toStr())));

            Numbers x = new Numbers(Numbers.modPow(a, t, n).toStr());

            if (x.toStr().equals("1") || x.toStr().equals(n.sub(new Numbers("1")).toStr()))
                continue;

            for (int r = 1; r < s; r++)
            {
                x = new Numbers(Numbers.modPow(x, new Numbers("2"), n).toStr());

                if (x.toStr().equals("1"))
                    return false;

                if (x.toStr().equals(n.sub(new Numbers("1")).toStr()))
                    break;
            }

            if (!x.toStr().equals(n.sub(new Numbers("1")).toStr()))
                return false;
        }

        return true;
    }
}
