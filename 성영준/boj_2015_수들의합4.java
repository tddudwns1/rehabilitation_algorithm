import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class boj_2015_수들의합4 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);

        long answer = 0;
        int total = 0;

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            total += Integer.parseInt(st.nextToken());

            answer += map.getOrDefault(total - k, 0);

            map.put(total, map.getOrDefault(total, 0) + 1);
        }

        System.out.println(answer);
    }
}
