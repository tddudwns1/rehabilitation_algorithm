import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class boj_1446_지름길 {
    static class Info implements Comparable<Info> {
        int destination;
        int cost;

        public Info(int destination, int cost) {
            this.destination = destination;
            this.cost = cost;
        }

        @Override
        public int compareTo(Info o) {
            return cost - o.cost;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken()); // 길 수
        int d = Integer.parseInt(st.nextToken()); // 도착지

        Map<Integer, Map<Integer, Integer>> map = new HashMap<>();
        TreeSet<Integer> spots = new TreeSet<>();
        spots.add(0);
        spots.add(d);
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());

            int s = Integer.parseInt(st.nextToken()); // 시작
            int e = Integer.parseInt(st.nextToken()); // 끝
            int l = Integer.parseInt(st.nextToken()); // 길이

            if (s >= d || e > d)
                continue;

            spots.add(s);
            spots.add(e);

            Map<Integer, Integer> spot = map.getOrDefault(s, new HashMap<>());

            if (spot.containsKey(e) && spot.get(e) <= l)
                continue;

            spot.put(e, l);
            map.put(s, spot);
        }

        int prev = spots.pollFirst();
        while (!spots.isEmpty()) {
            int next = spots.pollFirst();

            int commonLength = next - prev;

            Map<Integer, Integer> spot = map.getOrDefault(prev, new HashMap<>());

            if (!spot.containsKey(next) || spot.get(next) > commonLength) {
                spot.put(next, commonLength);
                map.put(prev, spot);
            }

            prev = next;
        }

        System.out.println(process(d, map));
    }

    private static int process(int d, Map<Integer, Map<Integer, Integer>> map) {
        Queue<Info> pq = new PriorityQueue<>();
        pq.add(new Info(0, 0));

        while (true) {
            Info now = pq.poll();

            if (now.destination == d)
                return now.cost;

            Map<Integer, Integer> nextMap = map.get(now.destination);
            Set<Integer> keySet = nextMap.keySet();
            for (int next : keySet) {
                pq.add(new Info(next, nextMap.get(next) + now.cost));
            }
        }
    }
}
