import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class boj_15681_트리와쿼리 {
    static class Node {
        int parent = -1;
        int countOfSubTrees = 1;
        List<Integer> connected = new ArrayList<>();

        public Node() {

        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        Node[] trees = new Node[n + 1];
        for (int i = 1; i <= n; i++) {
            trees[i] = new Node();
        }

        for (int i = 1; i < n; i++) {
            st = new StringTokenizer(br.readLine());

            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            trees[u].connected.add(v);
            trees[v].connected.add(u);
        }

        setTrees(r, trees);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            int u = Integer.parseInt(br.readLine());

            sb.append(trees[u].countOfSubTrees).append("\n");
        }

        System.out.println(sb);
    }

    private static void setTrees(int now, Node[] trees) {
        for (int next : trees[now].connected) {
            if (next == trees[now].parent) {
                continue;
            }
            trees[next].parent = now;
            setTrees(next, trees);
            trees[now].countOfSubTrees += trees[next].countOfSubTrees;
        }
    }
}
