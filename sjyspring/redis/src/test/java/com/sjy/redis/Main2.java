package com.sjy.redis;

import java.util.Scanner;

/**
 * 美丽的大树
 */
public class Main2 {
    public static void main(String[] args) {
        int n, m = 1, maxx = 0, s, si = 1, sign = 0;
        int[] ni = new int[101];
        int[][] f = new int[3][52];
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        for (int i = 1; i <= n; i++)
            ni[i] = in.nextInt();
        for (int j = 1; j <= 2; j++) {
            for (int i = 1; i <= 50; i++) {
                f[j][i] = m;
                for (int k = 1; k <= n; k++)
                    if (m == ni[k]) {
                        f[j][i] = 0;
                        break;
                    }
                m += 2;
            }
            m = 2;
        }

        for (int j = 1; j <= 2; j++) {
            s = 0;
            if (j == 1) si = 1;
            if (j == 2) si = 2;
            for (int i = 1; i <= 50; i++) {
                if (f[j][i] == 0 || i == 50) {
                    if (i == 50 && f[j][i] != 0)
                        s++;
                    if (s > maxx) {
                        sign = si;
                        maxx = s;
                    }
                    s = 0;
                    si = f[j][i + 1];
                } else
                    s++;
            }
        }

        System.out.println(sign + " " + maxx);
    }

}
