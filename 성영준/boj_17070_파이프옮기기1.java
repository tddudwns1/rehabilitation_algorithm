import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * dp를 요구하는 문제
 */
public class boj_17070_파이프옮기기1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 집에 벽 배치 정보
        // 한 칸씩 늘려 dp에서 고려 사항을 줄임 (y == 0 일 때, 천장이 없으면 에러)
        char[][] home = new char[n + 1][n + 1];
        for (int y = 1; y <= n; y++) {
            // gpt가 StringTokenizer + Integer.parseInt보다
            // String의 str.charAt이 더 빠를거라 해줘서 작성한 코드
            // 그냥 빠르다고 해서 사유는 모르고
            // 한 자리 문자에 띄어쓰기로 구분되어 주어지는 경우에 유용할 듯
            String str = br.readLine();
            for (int x = 1, cnt = 0; x <= n; x++, cnt += 2)
                home[y][x] = str.charAt(cnt);
        }

        System.out.println(dp(n, home));
    }

    /**
     * dp를 이용하여 방법 수를 카운팅 하는 메서드
     * 3가지 방향에 대해 따로 카운팅
     * 0 = 오른쪽, 1 = 오른쪽 아래 대각선, 2 = 아래
     * @param n 집의 크기
     * @param home 집 벽 배치 정보
     * @return 방법 수
     */
    private static int dp(int n, char[][] home) {
        // 도착지가 벽이라면 탐색 x
        if (home[n][n] == '1')
            return 0;

        int[][][] dp = new int[n + 1][n + 1][3];
        dp[1][2][0] = 1;

        for (int y = 1; y <= n; y++) {
            for (int x = 3; x <= n; x++) {
                // 현재 탐색 좌표가 벽이라면
                if (home[y][x] == '1')
                    continue;

                int left = x - 1;
                int up = y - 1;

                // 오른쪽으로 이동하는 경우
                dp[y][x][0] = dp[y][left][0] + dp[y][left][1];
                // 아래로 이동하는 경우
                dp[y][x][2] = dp[up][x][1] + dp[up][x][2];

                // 대각선 범위에 벽이 있다면 skip
                // 위의 벽들은 왜 범위 확인을 하지 않았냐면
                // 이미 현재 위치가 벽일 경우 skip하기 때문에
                // 이전에 벽의 위치의 값은 0이므로 더해도 상관 없음
                if (home[up][x] == '1' || home[y][left] == '1')
                    continue;

                // 오른쪽 아래로 이동하는 경우
                dp[y][x][1] = dp[up][left][0]
                        + dp[up][left][1]
                        + dp[up][left][2];
            }
        }

        return dp[n][n][0]
                + dp[n][n][1]
                + dp[n][n][2];
    }
}