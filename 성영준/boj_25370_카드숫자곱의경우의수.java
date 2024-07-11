import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

public class boj_25370_카드숫자곱의경우의수 {
    static HashSet<Integer> checked = new HashSet<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        dfs(1, 1, 0, n);

        System.out.println(checked.size());
    }

    public static void dfs(int index, int now, int depth, int n) {
        if (depth == n) {
            checked.add(now);
            return;
        }

        for (int i = index; i <= 9; i++) {
            dfs(i, now * i, depth + 1, n);
        }
    }
}