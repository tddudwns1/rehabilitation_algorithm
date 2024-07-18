import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class boj_11404_플로이드 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int m = Integer.parseInt(br.readLine());

        /** 플로이드 워셜 알고리즘 시작 **/

        // MAX_VALUE를 하면 안됨
        // 경로 상에 거쳐가는 값이 더 작으면 갱신하는 방식인데
        // 거쳐가는 과정에서 overflow가 나면 음수가 되며 더 작은 값으로 인식함
        // 이 문제의 경우 n번의 도시를 모두 거친
        // 가장 높은 비용에 n을 곱한 값인 천만으로 적용함
        final int MAX = 1000_0000;

        // 인접 행렬을 사용
        // 매 순간 해당 노드를 지나가는 비용을 알아야 하는데
        // 인접 리스트의 경우 비효율적임
        int[][] costs = new int[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            Arrays.fill(costs[i], MAX);
            // 해당 도시에서 출발하고 도착하는 값을 0으로 초기화해주지 않으면
            // 다른 도시를 거쳐 오는 값으로 갱신됨
            costs[i][i] = 0;
        }

        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            // 노선은 여러 개 일 수 있다고 문제에 명시되어 있기에
            // Math.min 사용
            costs[s][e] = Math.min(costs[s][e], c);
        }

        // 기준 도시를 거쳐가는 경우가 있다면
        // 거쳐가지 않는 값인 기존 값과
        // 거쳐가는 값과 비교하여
        // 낮은 값으로 갱신
        for (int center = 1; center <= n; center++)
            for (int start = 1; start <= n; start++)
                for (int end = 1; end <= n; end++)
                    costs[start][end] = Math.min(costs[start][end], costs[start][center] + costs[center][end]);

        // 출력
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++)
                if (costs[i][j] == MAX)
                    sb.append("0 ");
                else
                    sb.append(costs[i][j]).append(" ");
            sb.append("\n");
        }

        System.out.println(sb);
    }
}
