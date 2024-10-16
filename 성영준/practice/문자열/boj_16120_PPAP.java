package practice.문자열;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class boj_16120_PPAP {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();

        System.out.println(process(str));
    }

    private static String process(String str) {
        int limit = str.length() - 1;

        if (limit % 3 != 0)
            return "NP";

        int pCount = 0;
        if (str.charAt(0) == 'P')
            pCount++;

        for (int i = 1; i < limit; i++) {
            if (str.charAt(i) == 'A') {
                if (str.charAt(++i) == 'A')
                    return "NP";
                if (--pCount < 1)
                    return "NP";
            } else {
                pCount++;
            }
        }

        if (pCount == 1)
            return "PPAP";
        else
            return "NP";
    }
}
