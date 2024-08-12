import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 백트레킹을 이용한 조합 문제
 */
public class boj_18429_근손실 {
    static int count = 0, n, k, kits[], needWeight[];
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        // 키트 정보를 저장한 배열
        kits = new int[n];
        for (int i = 0; i < n; i++)
            kits[i] = Integer.parseInt(st.nextToken());

        // 일자별 필요한 추가 중량을 저장한 배열
        needWeight = new int[n + 1];
        for (int i = 0; i <= n; i++)
            needWeight[i] = k * (i + 1);

        // 가능한 총 경우의 수
        count = factorial(n);

        // backtracking 을 위한 조합 배열
        visited = new boolean[n];

        backtracking(0, n);

        System.out.println(count);
    }

    /**
     * 모든 경우의 수를 확인하는 메서드
     * 과정 중에 남은 기간 확인 할 필요가 없을 경우 다음 경우를 보지 않음
     * 1. 이미 충분한 중량을 갖췄을 경우
     * 2. 이미 중량이 초기보다 낮아진 경우
     *  이 경우는 남은 경우의 수를 전체 경우의 수에서 뺀다
     * @param nowWeight 현재 중량
     * @param day 남은 일자 (최종 일자까지 남은 일자)
     */
    private static void backtracking(int nowWeight, int day) {
        // 조건 1
        if (nowWeight >= needWeight[day])
            return;

        // 조건 2
        if (nowWeight < 0) {
            count -= factorial(day);
            return;
        }

        // 조합 상 다음 단계
        for (int i = 0; i < n; i++) {
            if (visited[i])
                continue;
            visited[i] = true;
            backtracking(nowWeight + kits[i] - k, day - 1);
            visited[i] = false;
        }
    }

    /**
     * 팩토리얼 메서드
     */
    private static int factorial(int n) {
        int value = 1;
        for (int i = 2; i <= n; i++)
            value *= i;

        return value;
    }
}
