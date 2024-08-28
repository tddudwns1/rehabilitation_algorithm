import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 최소 공통 조상
 */
public class boj_3584_가장가까운공통조상 {
    // 부모와 자신의 깊이를 저장하는 class
    static class Node {
        int parent;
        int depth;

        public Node() {
            depth = -1;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();

        for (int tc = 1; tc <= T; tc++) {
            int n = Integer.parseInt(br.readLine());

            // 트리 생성
            Node[] tree = new Node[n + 1];
            for (int i = 1; i <= n; i++)
                tree[i] = new Node();

            // 트리 관계 설정
            for (int i = 1; i < n; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());

                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                tree[b].parent = a;
            }

            // 루트 탐색 후 깊이 설정
            int root = getRoot(tree);
            tree[root].depth = 0;

            // 다른 노드 탐색 후 깊이 설정
            for (int now = 1; now <= n; now++)
                setDepth(now, tree);

            StringTokenizer st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            // 최소 공통 조상 탐색
            sb.append(findSameParent(a, b, tree)).append("\n");
        }

        System.out.println(sb);
    }

    /**
     * 한 노드를 기점으로 최대 높이까지 이동 후 해당 노드 반환
     */
    private static int getRoot(Node[] tree) {
        int now = 1;

        while (tree[now].parent != 0)
            now = tree[now].parent;

        return now;
    }

    /**
     * 해당 노드가
     *  초기화 되지 않았다면
     *  초기화 된 부모노드까지 이동 후
     *  부모 노드로 부터 +1 씩 부여
     */
    private static void setDepth(int now, Node[] tree) {
        if (tree[now].depth != -1)
            return;

        setDepth(tree[now].parent, tree);

        tree[now].depth = tree[tree[now].parent].depth + 1;
    }

    /**
     * 최대 같은 깊이가 된 순간이 정답
     */
    private static int findSameParent(int a, int b, Node[] tree) {
        while (tree[a].depth > tree[b].depth) {
            a = tree[a].parent;
        }

        while (tree[a].depth < tree[b].depth) {
            b = tree[b].parent;
        }

        while (a != b) {
            a = tree[a].parent;
            b = tree[b].parent;
        }

        return a;
    }
}
