import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * dp 문제
 */
public class boj_2240_자두나무 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int t = Integer.parseInt(st.nextToken());
        int w = Integer.parseInt(st.nextToken());

        // 자두 낙하 지점 저장용
        boolean[] jadu = new boolean[t + 1];
        // 이동 횟수와 시간에 따라 최대값을 저장하는 dp배열 생성
        int[][] dp = new int[w + 1][t + 1];

        // 초기 값 저장
        for (int i = 1; i <= t; i++) {
            dp[0][i] += dp[0][i - 1];
            if (Integer.parseInt(br.readLine()) == 1) {
                dp[0][i]++;
                jadu[i] = true;
            }
        }

        // 이전 시간 저장 용 변수
        int before = 0;
        // 시간별로
        for (int time = 1; time <= t; time++) {
            // 이동 횟수 별로
            // 여긴 2에 위치
            for (int moveCount = 1; moveCount <= w; moveCount += 2) {
                // 이동 전, 후 비교해서 큰 값을 저장
                dp[moveCount][time] = Math.max(dp[moveCount][before], dp[moveCount - 1][before]);
                // 자두가 2에 떨어지면 더하기
                if (!jadu[time])
                    dp[moveCount][time]++;
            }
            // 여긴 1에 위치 (이하 동일)
            for (int moveCount = 2; moveCount <= w; moveCount += 2) {
                dp[moveCount][time] = Math.max(dp[moveCount][before], dp[moveCount - 1][before]);
                if (jadu[time])
                    dp[moveCount][time]++;
            }
            // 이전 시간 갱신
            before = time;
        }

        // 결과 출력
        int answer = 0;
        for (int i = 0; i <= w; i++)
            answer = Math.max(answer, dp[i][t]);

        System.out.println(answer);
    }
}
