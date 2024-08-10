import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 매개 변수 탐색 문제
 */
public class boj_2110_공유기설치 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        // 집 정보 목록
        // 매개 변수 탐색을 위해 거리순으로 정렬
        int[] houses = new int[n];
        for (int i = 0; i < n; i++)
            houses[i] = Integer.parseInt(br.readLine());
        Arrays.sort(houses);

        System.out.println(process(houses, n, c));
    }

    /**
     * 매개 변수 탐색을 통해 가장 인접한 두 공유기 사이의 최대 거리를 탐색
     * @param houses 집 정보
     * @param n 집 개수
     * @param c 공유기 개수
     * @return 가장 인접한 두 공유기 사이의 최대 거리
     */
    private static int process(int[] houses, int n, int c) {
        // 최소 거리
        int left = 1;
        // 최대 거리
        int right = houses[n - 1] - houses[0];
        // 가장 인접한 두 공유기 사이의 최대 거리
        int answer = 0;

        while (left <= right) {
            // 현재 비교할 거리
            int mid = (left + right) / 2;

            // 만약 모두 놓을 수 있는만큼의 거리라면
            if (canPlaceRouters(houses, n, c, mid)) {
                // 정답 갱신
                answer = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return answer;
    }

    /**
     * 현재 거리가 공유기를 모두 놓을 수 있는 경우인지 판단
     * @param houses 집 목록
     * @param n 집 개수
     * @param c 공유기 개수
     * @param distance 현재 거리
     * @return 놓을 수 있는지 여부
     */
    private static boolean canPlaceRouters(int[] houses, int n, int c, int distance) {
        int count = 1;
        int prev = houses[0];

        for (int i = 1; i < n; i++) {
            // 다음 거리가 이전 거리보다 거리가 현재 거리보다 작다면 스킵
            if (houses[i] - prev < distance)
                continue;

            // 그렇게 놓은 공유기 수가 전체 개수에 도달했다면 멈춤
            if (++count == c)
                return true;

            // 아니라면 계속 진행
            prev = houses[i];
        }

        return false;
    }
}