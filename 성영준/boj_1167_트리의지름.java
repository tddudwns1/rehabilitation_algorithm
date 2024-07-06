import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

class Edge {
    int node;
    int value;

    public Edge(int node, int value) {
        this.node = node;
        this.value = value;
    }
}


public class boj_1167_트리의지름 {
    static Edge max = new Edge(0, 0);
    static boolean[] checked;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int v = Integer.parseInt(br.readLine());

        List<Edge>[] distances = new ArrayList[v + 1];
        for (int i = 1; i <= v; i++)
            distances[i] = new ArrayList<>();

        for (int i = 0; i < v; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int now = Integer.parseInt(st.nextToken());

            while (true) {
                int next = Integer.parseInt(st.nextToken());

                if (next == -1)
                    break;

                int distance = Integer.parseInt(st.nextToken());

                distances[now].add(new Edge(next, distance));
            }
        }

        checked = new boolean[v + 1];
        dfs(1, 0, v, distances);

        checked = new boolean[v + 1];
        dfs(max.node, 0, v, distances);

        System.out.println(max.value);
    }

    private static void dfs(int now, int sum, int v, List<Edge>[] distances) {
        checked[now] = true;

        for (Edge next : distances[now]) {
            if (next.value == 0)
                continue;

            if (checked[next.node])
                continue;

            dfs(next.node, sum + next.value, v, distances);
        }

        if (sum > max.value)
            max = new Edge(now, sum);
    }
}
