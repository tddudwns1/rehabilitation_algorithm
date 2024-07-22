import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class boj_1504_특정한최단경로 {
    /**
     * 다익스트라를 위한 class
     * 끝 점, 거리 정보 저장
     */
    static class Info implements Comparable<Info> {
        int end;
        int distance;

        public Info(int end, int distance) {
            this.end = end;
            this.distance = distance;
        }

        @Override
        public int compareTo(Info o){
            return distance - o.distance;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());

        Queue<Info>[] distances = new ArrayDeque[n + 1];
        for(int i = 1; i <= n; i++)
            distances[i] = new ArrayDeque<>();

        for (int i = 0; i < e; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            // 양방향 저장
            distances[a].add(new Info(b, c));
            distances[b].add(new Info(a, c));
        }

        // 경유지 순서를 따로 체크하기 위해 저장
        int[] order = new int[2];
        st = new StringTokenizer(br.readLine());
        order[0] = Integer.parseInt(st.nextToken());
        order[1] = Integer.parseInt(st.nextToken());

        // 수행 후 정답 출력
        System.out.println(process(n, distances, order, new int[n + 1]));
    }

    /**
     * 경유지 도착 2가지 방법 수행 후 가장 작은 거리 반환
     * @param n 최종 도착지
     * @param distances 정점 간 거리
     * @param order 경유 순서
     * @param total 총 이동 거리
     * @return 가장 작은 거리
     */
    private static int process(int n, Queue<Info>[] distances, int[] order, int[] total) {
        Info stopover = new Info(1, 0);

        // 1 2 n 순서
        for (int i = 0; i <= 1; i++) {
            // 목표지점까지 다익스트라 진행
            stopover = dijkstra(stopover, new boolean[n + 1], distances, order[i], total);
            // 도달 못하면 탐색 중단
            if (stopover.end == -1)
                return -1;
        }

        Info candidate1 = dijkstra(stopover, new boolean[n + 1], distances, n, total);
        if (candidate1.end == -1)
            return -1;

        stopover = new Info(1, 0);

        // 2 1 n 순서
        for (int i = 1; i >= 0; i--) {
            stopover = dijkstra(stopover, new boolean[n + 1], distances, order[i], total);
            if (stopover.end == -1)
                return -1;
        }

        Info candidate2 = dijkstra(stopover, new boolean[n + 1], distances, n, total);
        if (candidate2.end == -1)
            return -1;

        return Math.min(candidate1.distance, candidate2.distance);
    }

    /**
     * 다익스트라 진행
     * @param start 출발지
     * @param distances 정점 간 거리
     * @param target 목표지
     * @param total 총 이동 거리
     * @return 도착 정보
     */
    private static Info dijkstra(Info start, boolean[] checked, Queue<Info>[] distances, int target, int[] total) {
        Queue<Info> pq = new PriorityQueue<>();
        Arrays.fill(total, Integer.MAX_VALUE);

        pq.add(start);
        total[start.end] = start.distance;

        while(!pq.isEmpty()) {
            Info now = pq.poll();

            if (checked[now.end])
                continue;
            checked[now.end] = true;

            if (target == now.end)
                return new Info(now.end, now.distance);

            for(Info next : distances[now.end])
                if (total[next.end] > total[now.end] + next.distance)
                    pq.add(new Info(next.end, total[next.end] = total[now.end] + next.distance));
        }

        return new Info(-1, -1);
    }
}