package org.mytoys;

/// Project Euler: [Problem1](https://projecteuler.net/problem) = 1
///      If we list all the natural numbers below 10 that are multiples of 3 or 5, we get 3,5,6,9.
///     The sum of these multiples is 23.
///      Find the sum of all the multiples of 3 or 5 below 1000.
public class Main {

    public static void main(String[] args) {
        int limit = 10000;

        int res1 = version1(limit);
        int res2 = version2(limit);
        int res3 = version3(limit);

        System.out.println(res1 + " <> " + res2+ " <> " + res3);
    }

    public static int version1(final int limit){
        long t0 = System.nanoTime();
        int sum = 0;
        for (int i = 0; i < limit; i++) {
           if (i % 3 == 0 && i% 5 != 0){
               sum += i;
           }
           if (i% 5 == 0){
               sum += i;
           }
        }
        long t1 = System.nanoTime();
        System.out.println("version1: Computing time = " + (t1-t0)/1000000 + "ns");
        return sum;
    }

    public static int version2(final int limit){
        long t0 = System.nanoTime();
        final int limit3 = limit/3;
        final int limit5  = limit/5;
        int sum = 0;
        for (int i = 1; i <= limit3; i++) {
            int m = 3 * i;
            if (i % 5 == 0)  {
                continue;
            }
            sum += m;
        }

        for (int i = 1; i < limit5; i++) {
            int m = 5 * i;
            sum += m;
        }
        long t1 = System.nanoTime();
        System.out.println("version2: Computing time = " + (t1-t0)/1000000 + "ns");
        return sum;
    }

    // TODO why does this take longer ????
    public static int version3(final int limit){
        long t0 = System.nanoTime();
        final int limit3 = limit/3;
        final int limit5  = limit/5;
        int sum = 0;
        for (int i = 1; i <= limit3; i++) {
            int m = 3 * i;
            sum += m;
            if ( i < limit5 ) {
                int m5 = 5 * i;
                if(m5 %3 != 0){
                    sum += m5;
                }
            }
        }
        long t1 = System.nanoTime();
        System.out.println("version3: Computing time = " + (t1-t0) + "ns");
        return sum;
    }
}
