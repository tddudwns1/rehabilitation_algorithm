import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class boj_9461_파도반수열 {
    static long[] s = new long[101];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        s[1] = s[2] = s[3] = 1;
        s[4] = s[5] = 2;

        for (int tc = 1; tc <= T; tc++) {
            int now = Integer.parseInt(br.readLine());
            sb.append(recursion(now)).append("\n");
        }

        System.out.println(sb);
    }

    private static long recursion(int n) {
        if (s[n] != 0)
            return s[n];

        return s[n] = recursion(n - 1) + recursion(n - 5);
    }
}
