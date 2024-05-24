package gziptest;

import java.util.Arrays;

public final class FloatFormatter {

    private final int iketa;
    private final int fketa;
    private final int sign;
    private final int zeropadding;
    private final int[] digits;
    private int base;

    private final int SIGNSYMBOL = 11;
    private final int DECIMALPOINT = 12;
    private final int REVERSEZERO = 10;

    public int getIketa() {
        return iketa;
    }

    public int getFketa() {
        return fketa;
    }

    public int getSign() {
        return sign;
    }

    public int getZeropadding() {
        return zeropadding;
    }

    public int[] getDigits() {
        return digits;
    }

    public FloatFormatter(int iketa, int fketa, int sign, int zeropadding) {
        int tempiketa = (iketa >= 0) ? iketa : 0;
        int tempfketa = (fketa >= 0) ? fketa : 0;
        this.sign = (sign >= 1) ? 1 : 0;
        this.zeropadding = (zeropadding >= 2) ? 2 : zeropadding >= 1 ? 1 : 0; // 2 or 1 or 0
        if (tempiketa >= 15 || tempfketa >= 15 || tempiketa + tempfketa >= 15) {
            this.fketa = (tempfketa < 15) ? tempfketa : 15;
            this.iketa = 15 - this.fketa;
        }
        else {
            this.iketa = tempiketa;
            this.fketa = tempfketa;
        }
        final int d = this.sign + this.iketa + this.fketa + ((this.fketa != 0) ? 1 : 0);
        this.digits = new int[d + 1];
        this.base = this.sign + this.iketa;
        Arrays.fill(this.digits, -1);
    }

    public int[] calcuateAndGetDigits(double value) {
        if (this.digits.length == 1) {
            return this.digits; // 空定義用
        }
        int ival = (int) value;
        if (this.zeropadding == 0) { // 整数桁数算出
            this.base = Math.min(this.iketa + this.sign, (int) Math.log10((ival != 0) ? ival : 1) + 1);
        }
        int nowketa;
        if (this.iketa == 0 && this.sign == 1) {
            this.digits[1] = SIGNSYMBOL;
        } else {
            nowketa = this.base;
            this.digits[nowketa] = ival % 10; // 1桁目は裏ゼロにしない
            ival = ival / 10;
            nowketa--;
            while (1 <= nowketa) {
                if (ival != 0) {
                    this.digits[nowketa] = ival % 10;
                    ival = ival / 10;
                } else {
                    if (nowketa == 1) {
                        this.digits[nowketa] = SIGNSYMBOL;
                    } else if (this.zeropadding == 1) {
                        this.digits[nowketa] = 0;
                    } else {
                        this.digits[nowketa] = REVERSEZERO;
                    }
                }
                nowketa--;
            }

        }
        if (this.fketa != 0) {
            this.digits[this.base + 1] = DECIMALPOINT;
            int fval = (int)Math.round(value * Math.pow(10, this.fketa));
            nowketa = this.digits.length - 1;
            while (this.base + 1 < nowketa) {
                this.digits[nowketa] = fval % 10;
                fval = fval / 10;
                nowketa--;
            }
        }

        return this.digits;
    }

}
