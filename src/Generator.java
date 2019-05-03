import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class Generator {

    private static final Numbers two = new Numbers("2");
    private static final Numbers one = new Numbers("1");
    private static final Numbers zero = new Numbers("0");


    static int bits(Numbers n) {
        int k = 0;
        while (!n.toStr().equals("0")) {
            n = Numbers.div(n, new Numbers("2"));
            k++;
        }
        return k;
    }

    static Numbers generateQ(int k) {

        Numbers resN = zero;
        ArrayList<Boolean> bits = new ArrayList<>();
        bits.add(true);
        Random brand = new Random();
        for (int i = 0; i < k - 2; i++) {
            bits.add(brand.nextBoolean());
        }
        bits.add(true);

        for (int i = 0; i < bits.size(); i++) {
            if (bits.get(i)) {
                resN = resN.add(Numbers.pow(two, i));
            }
        }

        while (true){
            if (!Numbers.mod(resN, two).toStr().equals("0")) {
                if (MillerRabinTest(resN, 5)) {
                    return new Numbers(resN.toStr());

                }
                else resN = resN.add(one);
            }
            else
                resN = resN.add(one);
        }
    }

    static Numbers generate(int k) {

        while (true) {

                Numbers q = generateQ(k / 2);
                int sz = k - k / 2;

                for (Numbers s = new Numbers(Numbers.pow(two, sz).toStr()); s.compareTo(new Numbers(Numbers.pow(two, sz + 1).toStr())) == -1 || s.toStr().equals(Numbers.pow(two, sz + 1).toStr()); s = new Numbers(s.add(two).toStr())) {

                Numbers p = new Numbers(new Numbers(q.mul(s).toStr()).add(one).toStr());
                Numbers temp = new Numbers(Numbers.pow(new Numbers(new Numbers(two.mul(q).toStr()).add(one).toStr()), 2).toStr());
                    Numbers t = Numbers.modPow(two, s, p);
                int c = bits(p);
                    //System.out.println(c);
                if (c > k) break;
                if (c == k && p.compareTo(temp) == -1 &&
                        Numbers.modPow(two, new Numbers(q.mul(s).toStr()), p).toStr().equals("1") &&
                        !t.toStr().equals("1")) {
                    System.out.println("q: " + q.toStr());
                    System.out.println("s: " + s.toStr());
                    System.out.println(s.mul(q).toStr());
                    return p;
                }
            }
        }
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
