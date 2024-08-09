import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 최장 증가 수열 문제
 */
public class boj_2565_전깃줄 {
    /**
     * 전깃줄 정보 저장용 클래스
     */
    static class Wire implements Comparable<Wire>{
        int start;
        int end;

        public Wire(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Wire o) {
            return start - o.start;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 전깃줄 정보를 정렬하여 저장할 리스트
        List<Wire> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());

            list.add(new Wire(start, end));
        }
        Collections.sort(list);

        System.out.println(process(n, list));
    }

    /**
     * 최장 증가 수열 진행할 메서드
     * @param n 전체 전깃줄 수
     * @param list 정렬한 전깃줄 목록
     * @return 제거할 전깃줄 최소 갯수
     */
    private static int process(int n, List<Wire> list) {
        int[] connect = new int[n + 1];
        // 연결 할 전깃줄 최대 수
        int len = 0;

        for (Wire now : list) {
            // 현재 B에 걸 전깃줄이
            int destination = now.end;

            // 이미 걸린 전깃줄보다 뒤라면 연결
            if (connect[len] < destination)
                connect[++len] = destination;
            // 앞이라면 새로 걸기
            else
                connect[binarySearch(connect, len, destination)] = destination;
        }

        // 총 갯수에서 최대 연결 갯수 제외한 값 반환
        return n - len;
    }

    /**
     * 이진 탐색
     */
    private static int binarySearch(int[] connect, int len, int destination) {
        int left = 1;
        int right = len;

        while (left < right) {
            int mid = (left + right) >> 1;

            if (connect[mid] < destination)
                left = mid + 1;
            else
                right = mid;
        }

        return right;
    }
}
