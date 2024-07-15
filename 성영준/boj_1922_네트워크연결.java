import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * prim 알고리즘에 이용하기 위한 도착지, 비용 class
 * 낮은 비용이 우선순위 높음
 */
class Info implements Comparable<Info>{
    int next;
    int expense;

    public Info(int next, int expense) {
        this.next = next;
        this.expense = expense;
    }

    @Override
    public int compareTo(Info o) {
        return expense - o.expense;
    }
}

/**
 * MST 문제
 * prim 알고리즘 채택
 */
public class boj_1922_네트워크연결 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int m = Integer.parseInt(br.readLine());

        // prim 알고리즘에 사용될 연결 정보를 저장 할 2차원 배열
        int[][] expenses = new int[n + 1][n + 1];
        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            expenses[a][b] = c;
            expenses[b][a] = c;
        }

        System.out.println(prim(expenses, n));
    }

    private static int prim(int[][] expenses, int n) {
        Queue<Info> pq = new PriorityQueue<>();
        boolean[] checked = new boolean[n + 1];
        int answer = 0;

        // 시작 지점 선정
        pq.add(new Info(1, 0));

        // 시작점 제외 n - 1번 돌아야 하니
        // 시작점 포함 n 번 진행
        int count = 0;
        while (count < n) {
            Info now = pq.poll();

            if (checked[now.next])
                continue;

            checked[now.next] = true;
            answer += now.expense;
            count++;

            for (int i = 1; i <= n; i++)
                if (expenses[now.next][i] != 0)
                    pq.add(new Info(i, expenses[now.next][i]));
        }

        return answer;
    }
}