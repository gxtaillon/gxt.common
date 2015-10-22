package gxt.common;

/**
 * Created by taig1501 on 15-10-20.
 */
public class Math {
    public static long roundNearest(long number, long multiple)
    {
        long pos;
        long n;
        long half = multiple / 2;
        if (number < 0) {
            n = java.lang.Math.abs(number);
            pos = -1;
        } else {
            n = number;
            pos = 1;
        }
        if (n % multiple < half) {
            return pos * roundDown(n, multiple);
        } else {
            return pos * roundUp(n, multiple);
        }

    }

    public static long roundDown(long number, long multiple)
    {
        return number - number % multiple;
    }

    public static long roundUp(long number, long multiple)
    {
        return (multiple - number % multiple) + number;
    }
}
