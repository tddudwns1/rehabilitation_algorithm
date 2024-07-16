import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Edge {
    // 출발 지점, 도착 지점, 소요 시간
    int start;
    int end;
    int cost;

    public Edge(int start, int end, int cost) {
        this.start = start;
        this.end = end;
        this.cost = cost;
    }
}

public class boj_1865_웜홀 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        for (int tc = 1; tc <= T; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            // 웜홀이 없다면 찾을 필요 x
            if (w == 0) {
                for (int i = 0; i < m * 2; i++)
                    br.readLine();

                sb.append("NO");
            } else {
                // List 아니면 Queue를 대체할 자료구조
                // 속도를 줄이기 위해 채택
                // List를 사용하는 방식이 정석
                // 모든 이동 과정을 저장하기 위해 배열 크기를 m * 2 + w로 설정
                Edge[] edges = new Edge[m * 2 + w];
                int index = 0;

                for (int i = 0; i < m; i++) {
                    st = new StringTokenizer(br.readLine());

                    int s = Integer.parseInt(st.nextToken());
                    int e = Integer.parseInt(st.nextToken());
                    int t = Integer.parseInt(st.nextToken());

                    // 도로의 경우 양방향 이동이 가능
                    edges[index++] = new Edge(s, e, t);
                    edges[index++] = new Edge(e, s, t);
                }

                for (int i = 0; i < w; i++) {
                    st = new StringTokenizer(br.readLine());

                    int s = Integer.parseInt(st.nextToken());
                    int e = Integer.parseInt(st.nextToken());
                    int t = Integer.parseInt(st.nextToken());

                    // 웜홀의 경우 단방향 이동이며 시간이 뒤로 흐름
                    edges[index++] = new Edge(s, e, -t);
                }

                sb.append(bellmanFord(edges, n)).append("\n");
            }
        }

        System.out.println(sb);
    }

    private static String bellmanFord(Edge[] edges, int n) {
        int[] times = new int[n + 1];
        // Integer.MAX_VALUE를 사용하는 것이 정석이나
        // 가능한 최고치를 계산해서 적용
        //
        // 만약 Integer.MAX_VALUE를 적용한 후 이 방식을 적용하면
        // 더 오래 걸리는 경로가 나올 경우
        // overflow가 나 음수가 나오며 갱신이 진행됨
        Arrays.fill(times, 5_000_000);

        // 시작 지점으로 이전 시간에 돌아오는 경우는,
        // 즉 벨만 포드에서 음의 사이클이 존재하는지 판별하는 방식은
        // 경로의 개선이 지역의 수만큼 더 진행되면 존재함
        // 그래서 cycle을 정의하고 카운팅 함
        for (int cycle = 1; cycle < n; cycle++) {
            for (Edge edge : edges) {
                int candidate = times[edge.start] + edge.cost;
                if (times[edge.end] <= candidate)
                    continue;

                times[edge.end] = candidate;
            }
        }
        // 만약 한번 더 개선되는 경우가 생긴다면
        // 무한히 개선이 되는 경우이므로
        // 음의 사이클이 생성됐다는 뜻임
        for (Edge edge : edges)
            if (times[edge.end] > times[edge.start] + edge.cost)
                return "YES";

        // 아니면 이전 시간에 시작지점으로 오는 경우는 없음
        return "NO";
    }
}
