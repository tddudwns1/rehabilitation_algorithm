import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class boj_30804_과일탕후루 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] types = new int[10];
        int type = 0;
        int count = 0;
        int answer = 0;
        Queue<Integer> q = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            int now = Integer.parseInt(st.nextToken());

            if(types[now] != 0) {
                q.add(now);
                types[now]++;
                count++;
                continue;
            }

            answer = Math.max(count, answer);
            if(++type <= 2) {
                q.add(now);
                types[now]++;
                count++;
                continue;
            }

            while(true) {
                count--;
                if(--types[q.poll()] == 0)
                    break;
            }

            q.add(now);
            types[now]++;
            count++;
            continue;
        }

        System.out.println(Math.max(count, answer));
    }
}
