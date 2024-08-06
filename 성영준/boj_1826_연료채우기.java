import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 꽤 어려웠던 그리디 문제
 */
public class boj_1826_연료채우기 {
    // 주유소 정보를 담을 클래스
    // 거리를 기준으로 우선순위 (도달할 수 있는지 빠르게 판단해야 하기 때문)
    static class Station implements Comparable<Station>{
        int fuel;
        int spot;

        public Station(int fuel, int spot) {
            this.fuel = fuel;
            this.spot = spot;
        }

        @Override
        public int compareTo(Station o) {
            return Integer.compare(spot, o.spot);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st;

        // 주유소 거리가 가까운 순으로 입력된다는 보장이 없기에 우선순위 큐 사용
        Queue<Station> stations = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int spot = Integer.parseInt(st.nextToken());
            int fuel = Integer.parseInt(st.nextToken());

            stations.add(new Station(fuel, spot));
        }

        st = new StringTokenizer(br.readLine());
        int distance = Integer.parseInt(st.nextToken());
        int fuel = Integer.parseInt(st.nextToken());

        System.out.println(process(stations, distance, fuel));
    }

    /**
     * 그리디 알고리즘 실행할 매서드
     * 연료를 기준으로
     * 도착지에 도달하지 못 한다면
     * 현재 갈 수 있는 주유소 중
     * 가장 많은 연료를 주는 주유소를 택한다
     * 를 반복하는 메서드
     * @param stations 주유소 목록
     * @param distance 도착 예정지
     * @param fuel 현재 기름, 주행 가능 거리
     * @return 주유소 방문 횟수, 도달 실패 시 -1
     */
    private static int process(Queue<Station> stations, int distance, int fuel) {
        // 연료 량을 기준으로 내림차순
        Queue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        // 현재 기름, 주행 가능 거리
        int position = fuel;
        // 주유소 방문 횟수
        int count = 0;

        while (true) {
            // 주행 가능 거리가 도착지를 넘어선다면 종료
            if (position >= distance)
                return count;

            // 현재 위치 기점으로 방문 가능한 주유소 목록 업데이트
            while (!stations.isEmpty())
                if (stations.peek().spot <= position)
                    pq.add(stations.poll().fuel);
                else
                    break;

            // 방문 가능한 주유소가 없다면
            // 도달이 불가능 하기에 종료
            if (pq.isEmpty())
                return -1;

            // 현재 도달 가능한 주유소 목록 중 가장 많은 기름을 주는 주유소 선택
            position += pq.poll();
            count++;
        }
    }
}
