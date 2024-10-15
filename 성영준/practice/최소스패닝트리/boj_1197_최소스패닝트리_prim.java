package practice.최소스패닝트리;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class boj_1197_최소스패닝트리_prim {
    static class Node implements Comparable<Node> {
        int destination;
        int cost;

        public Node(int destination, int cost) {
            this.destination = destination;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(cost, o.cost);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int v = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());

        Queue<Node>[] infos = new Queue[v + 1];
        for (int i = 1; i <= v; i++) {
            infos[i] = new ArrayDeque<>();
        }

        for (int i = 0; i < e; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            infos[a].add(new Node(b, c));
            infos[b].add(new Node(a, c));
        }

        System.out.println(process(v, infos));
    }

    private static int process(int v, Queue<Node>[] infos) {
        boolean[] visited = new boolean[v + 1];
        Queue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(1, 0));

        int weight = 0;
        int standard = v;

        while (standard > 0) {
            Node now = pq.poll();

            if (visited[now.destination])
                continue;
            visited[now.destination] = true;
            weight += now.cost;
            standard--;

            while(!infos[now.destination].isEmpty()) {
                Node next = infos[now.destination].poll();
                if (visited[next.destination])
                    continue;
                pq.add(next);
            }
        }

        return weight;
    }
}
