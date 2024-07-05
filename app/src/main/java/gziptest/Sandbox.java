package gziptest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sandbox {

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        List<FloatFormatter> ffs = new ArrayList<FloatFormatter>(2 * 4 * 4 * 3);
        for (int sign = 0; sign <= 1; sign++) {
            for (int iketa = 0; iketa <= 3; iketa++) {
                for (int fketa = 12; fketa <= 15; fketa++) {
                    for (int zp = 0; zp <= 2; zp++) {
                        ffs.add(new FloatFormatter(iketa, fketa, sign, zp));
                    }
                }
            }
        }
        while (true) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            final double d = Double.parseDouble(br.readLine());
            ffs.stream().forEach(f -> {
                System.out.print("i:" + f.getIketa() + ",f:" + f.getFketa() + ",s:" + f.getSign() + ",p:"
                        + f.getZeropadding() + " ");
                // System.out.print(String.join(",",Arrays.stream(f.calcuateAndGetDigits2(d)).boxed().map(String::valueOf).toList()));
                f.calcuateAndGetDigits(d);
                System.out.println(joinFormat(f.getDigits()));
            });

        }
    }

    public static String joinFormat(int[] digits) {
        sb.delete(0, sb.length());
        Arrays.stream(digits).boxed().map(i -> {
            String s;
            switch (i) {
                case 12:
                    s = ".";
                    break;
                case 11:
                    s = "+";
                    break;
                case 10:
                    s = "Q";
                    break;
                case -1:
                    s = "*";
                    break;
                default:
                    s = String.valueOf(i);
                    break;
            }
            return s;
        }).forEach(sb::append);
        return sb.toString();
    }
}
