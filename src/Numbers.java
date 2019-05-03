import java.util.ArrayList;

public class Numbers {
    ArrayList<Integer> longDigit = new ArrayList<>();
    private static int base = 10;
    private static int lenghtOfBase = 1;
    private boolean sign = true;
    static int k = 0;
    private String str = "";


    Numbers(String str) {
        this.str = str;
        for (int i = str.length(); i > 0; i -= lenghtOfBase) {
            if (i < lenghtOfBase)
                longDigit.add(Integer.parseInt(str.substring(0, i)));
            else
                longDigit.add(Integer.parseInt(str.substring(i - lenghtOfBase, i)));
        }
    }

    public String toStr () {
        String res = "";
        int k = this.longDigit.size();
        if (k > 1) {
            while (this.longDigit.size() > 0 && this.longDigit.get(this.longDigit.size() - 1) == 0) {
                this.longDigit.remove(this.longDigit.size() - 1);
            }
        }

        if (this.longDigit.size() == 0)
            this.longDigit.add(0);

        if (this.str.equals(""))
            for (int i = this.longDigit.size() - 1; i >= 0; i--)
                res += this.longDigit.get(i);
        else res += this.str;
        return res;
    }

    private Numbers(ArrayList<Integer> a, boolean sign) {
        this.longDigit = a;
        this.sign = sign;
    }

    private Numbers(int[] a, boolean sign) {
        for (int i : a) {
            this.longDigit.add(i);
        }
        this.sign = sign;
    }

    private Numbers(ArrayList<Integer> arr) {
        this.longDigit = arr;
    }

    private void changeSign(boolean flag) {
        sign = flag;
    }

    public int compareTo(Numbers a) {
        int isBigger = 0;
        if (this.longDigit.size() == a.longDigit.size()) {
            for (int i = 0; i < this.longDigit.size(); i++) {
                if (this.longDigit.get(i) > a.longDigit.get(i))
                    isBigger = 1;
                if (this.longDigit.get(i) < a.longDigit.get(i))
                    isBigger = -1;
            }
        } else {
            if (this.longDigit.size() > a.longDigit.size())
                isBigger = 1;
            if (this.longDigit.size() < a.longDigit.size())
                isBigger = -1;
        }
        return isBigger;
    }

    private static ArrayList<Integer> delNull(ArrayList<Integer> a) {
        while (a.size() > 0 && a.get(a.size() - 1) == 0)
            a.remove(a.size() - 1);
        if (a.size() == 0)
            a.add(0);
        return a;
    }

    Numbers add(Numbers a) {
        int k = 0;
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < Math.max(a.longDigit.size(), this.longDigit.size()); i++) {
            res.add((a.longDigit.size() > i ? a.longDigit.get(i) : 0) + (this.longDigit.size() > i ? this.longDigit.get(i) : 0) + k);
            if (res.get(i) >= base) {
                res.set(i, res.get(i) - base);
                k = 1;
            } else
                k = 0;
        }
        if (k == 1)
            res.add(k);
        return new Numbers(res);
    }

    Numbers sub(Numbers a) {
        k = 0;

        int nMax = Math.max(a.longDigit.size(), this.longDigit.size());
        int nMin = Math.min(a.longDigit.size(), this.longDigit.size());
        Numbers arg3 = new Numbers("");
        for (int i = 0; i < nMax; i++)
            arg3.longDigit.add(0);
        for (int i = 0; i < nMin; i++) {
            arg3.longDigit.set(i, (this.longDigit.get(i) - a.longDigit.get(i) + k));
            if (arg3.longDigit.get(i) < 0) {
                arg3.longDigit.set(i, arg3.longDigit.get(i) + base);
                k = -1;
            } else
                k = 0;
        }
        if (this.longDigit.size() > a.longDigit.size()) {
            for (int i = nMin; i < nMax; i++) {
                arg3.longDigit.set(i, (this.longDigit.get(i) + k));
                if (arg3.longDigit.get(i) < 0) {
                    arg3.longDigit.set(i, arg3.longDigit.get(i) + base);
                    k = -1;
                } else
                    k = 0;
            }
        } else {
            for (int i = nMin; i < nMax; i++) {
                arg3.longDigit.set(i, (a.longDigit.get(i) + k));
                if (arg3.longDigit.get(i) < 0) {
                    arg3.longDigit.set(i, arg3.longDigit.get(i) + 10);
                    k = -1;
                } else
                    k = 0;
            }
        }
        return arg3;
    }

    Numbers mul(int a) {
        int k = 0;
        ArrayList<Integer> ans = new ArrayList<>();
        for (Integer aDigit : this.longDigit) {
            long temp = (long) aDigit * (long) a + k;
            ans.add((int) (temp % base));
            k = (int) (temp / base);
        }
        ans.add(k);
        return new Numbers(delNull(ans));
    }

    Numbers mul(Numbers a) {
        Numbers ans = new Numbers("0");
        for (int i = 0; i < this.longDigit.size(); i++) {
            Numbers temp = a.mul(this.longDigit.get(i));
            for (int j = 0; j < i; j++)
                temp.longDigit.add(0, 0);
            ans = ans.add(temp);
        }
        return ans;
    }

    Numbers  div(int v) {
        int num = this.longDigit.size() - 1;
        int ost = 0;
        Numbers res = new Numbers("");
        for (int i = 0; i <= num; i++)
            res.longDigit.add(0);
        while (num >= 0) {
            int cur = ost * base + this.longDigit.get(num);
            res.longDigit.set(num, cur / v);
            ost = cur % v;
            num--;
        }
        return res;
    }

    public static Numbers div(Numbers u, Numbers v) {
        if (u.compareTo(v) == -1)
            return new Numbers("0");

        Numbers q;

        if (v.longDigit.size() == 1) {
            int k22 = v.longDigit.get(0);
            return u.div(k22);
        }

        int n = v.longDigit.size();
        int m = u.longDigit.size() - v.longDigit.size();

        int[] tempArray = new int[m + 1];
        tempArray[m] = 1;
        q = new Numbers(tempArray, true);

        int d = (base / (v.longDigit.get(n - 1) + 1));

        u = u.mul(d);
        v = v.mul(d);

        if (d == 1 || u.longDigit.size() <= m + n)
            u.longDigit.add(0);

        for (int j = m; j >= 0; j--) {
            long t = (long) (u.longDigit.get(j + n)) * (long) (base) + u.longDigit.get(j + n - 1);
            int qt = (int) (t / v.longDigit.get(n - 1));
            int rt = (int) (t % v.longDigit.get(n - 1));
            while (rt < base && (qt == base || ((long) qt * (long) v.longDigit.get(n - 2) > (long) base * (long) rt + u.longDigit.get(j + n - 2)))) {
                qt--;
                rt += v.longDigit.get(n - 1);
            }

            Numbers u2 = new Numbers(new ArrayList<Integer>(u.longDigit.subList(j, j + n + 1)), true);

            Numbers temp = v.mul(qt);

            Numbers temp2 = new Numbers("");
            for (int i = 0; i < u.longDigit.size(); i++)
                temp2.longDigit.add(0);

            for (int i = 0; i < temp.longDigit.size(); i++)
                temp2.longDigit.set(i + j, temp.longDigit.get(i));

            u = u.sub(temp2);

            q.longDigit.set(j, qt);

            if (u.k == -1) {
                k = 0;
                u2.changeSign(true);
                q.longDigit.set(j, qt - 1);

                Numbers bn = new Numbers("");
                for (int i = 0; i < u.longDigit.size(); i++)
                    bn.longDigit.add(0);

                for (int i = 0; i < v.longDigit.size(); i++)
                    bn.longDigit.set(i + j, v.longDigit.get(i));

                int count = u.longDigit.size();
                u = u.add(bn);
                if (count < u.longDigit.size())
                    u.longDigit.remove(u.longDigit.size() - 1);
            }

        }
        q.longDigit = q.delNull(q.longDigit);
        return q;
    }

    Numbers mod(int v) {
        int num = this.longDigit.size() - 1;
        int ost = 0;
        Numbers res = new Numbers("");
        for (int i = 0; i <= num; i++)
            res.longDigit.add(0);
        while (num >= 0) {
            int cur = ost * base + this.longDigit.get(num);
            res.longDigit.set(num, cur / v);
            ost = cur % v;
            num--;
        }
        return new Numbers(Integer.toString(ost));
    }

    public static Numbers mod(Numbers u, Numbers v) {
        if (u.compareTo(v) == -1)
            return u;
        Numbers q, r;

        if (v.longDigit.size() == 1) {
            int k22 = v.longDigit.get(0);
            return u.mod(k22);
        }

        int n = v.longDigit.size();
        int m = u.longDigit.size() - v.longDigit.size();
        if (m < 0)
            return u;

        int[] tempArray = new int[m + 1];
        tempArray[m] = 1;
        q = new Numbers(tempArray, true);

        int d = (base / (v.longDigit.get(n - 1) + 1));

        u = u.mul(d);
        v = v.mul(d);

        if (d == 1 || u.longDigit.size() <= m + n)
            u.longDigit.add(0);

        if (v.longDigit.size() == 1) {
            int k22 = v.longDigit.get(0);
            return u.mod(k22);
        }

        for (int j = m; j >= 0; j--) {
            long t = (long) (u.longDigit.get(j + n)) * (long) (base) + u.longDigit.get(j + n - 1);
            int qt = (int) (t / v.longDigit.get(n - 1));
            int rt = (int) (t % v.longDigit.get(n - 1));
            while (rt < base && (qt == base || ((long) qt * (long) v.longDigit.get(n - 2) > (long) base * (long) rt + u.longDigit.get(j + n - 2)))) {
                qt--;
                rt += v.longDigit.get(n - 1);
            }

            Numbers u2 = new Numbers(new ArrayList<Integer>(u.longDigit.subList(j, j + n + 1)), true);

            Numbers temp = v.mul(qt);

            Numbers temp2 = new Numbers("");
            for (int i = 0; i < u.longDigit.size(); i++)
                temp2.longDigit.add(0);

            for (int i = 0; i < temp.longDigit.size(); i++)
                temp2.longDigit.set(i + j, temp.longDigit.get(i));

            u = u.sub(temp2);

            q.longDigit.set(j, qt);

            if (u.k == -1) {
                k = 0;
                u2.changeSign(true);
                q.longDigit.set(j, qt - 1);

                Numbers bn = new Numbers("");
                for (int i = 0; i < u.longDigit.size(); i++)
                    bn.longDigit.add(0);

                for (int i = 0; i < v.longDigit.size(); i++)
                    bn.longDigit.set(i + j, v.longDigit.get(i));

                int count = u.longDigit.size();
                u = u.add(bn);
                if (count < u.longDigit.size())
                    u.longDigit.remove(u.longDigit.size() - 1);
            }
        }
        r = new Numbers(new ArrayList<Integer>(u.longDigit.subList(0, n)), true).div(d);
        r.longDigit = r.delNull(r.longDigit);
        return r;
    }

    public static Numbers modPow(Numbers x, Numbers n, Numbers m) {

        Numbers cnt = new Numbers("1");

        if (n.str.equals("0"))
            return new Numbers("1");

        while (true) {
            if (n.str.equals("0"))
                break;

            Numbers a = new Numbers(n.mod(2).toStr());
            if (a.str.equals("0")) {
                n = new Numbers(n.div(2).toStr());
                x = new Numbers(x.mul(x).toStr());
                x = new Numbers(mod(x, m).toStr());
            } else {
                n = new Numbers(n.sub(new Numbers("1")).toStr());
                cnt = new Numbers(cnt.mul(x).toStr());
                cnt = new Numbers(mod(cnt, m).toStr());
            }
        }
        return mod(cnt, m);
    }

    public static Numbers pow(Numbers x, int n) {

        Numbers cnt = new Numbers("1");

        if (n == 0)
            return new Numbers("1");

        while (true) {
            if (n == 0)
                break;


            if (n % 2 == 0) {
                n /= 2;
                x = new Numbers(x.mul(x).toStr());
            } else {
                n--;
                cnt = new Numbers(cnt.mul(x).toStr());
            }
        }
        return cnt;
    }

    static boolean isNumber(String s) {
        char[] a = s.toCharArray();

        if (a[0] == '0' && a.length > 1)
            return false;

        for (int i = 0; i < a.length; i++) {

            if (a[i] < '0' || a[i] >'9')
                return false;
        }
        return true;
    }
}