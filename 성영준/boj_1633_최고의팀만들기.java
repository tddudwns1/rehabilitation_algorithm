import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * dp 문제
 */
public class boj_1633_최고의팀만들기 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int[][] dp = new int[16][16];

        while (true) {
            String line = br.readLine();
            if (line == null)
                break;

            StringTokenizer st = new StringTokenizer(line);
            int white = Integer.parseInt(st.nextToken());
            int black = Integer.parseInt(st.nextToken());

            // dp 배열을 역추적을 통해 업데이트 함
            // 이렇게 하면 3차원 배열이 아닌 2차원 배열로도 풀이가 가능
            //
            // 또한 3차원 배열의 경우 선언 시 최종 길이를 이전에 알아야 하기 때문에
            // 이런 문제와 같은 경우, 입력 값을 미리 list 에 저장 한 뒤 길이를 구하는 과정이 추가 됨
            //
            // 하지만 지금과 같이 역추적을 통해 업데이트를 한다면 차원 하나를 줄일 수 있음
            for (int w = 15; w >= 0; w--) {
                for (int b = 15; b >= 0; b--) {
                    if (w > 0)
                        dp[w][b] = Math.max(dp[w][b], dp[w - 1][b] + white);

                    if (b > 0)
                        dp[w][b] = Math.max(dp[w][b], dp[w][b - 1] + black);
                }
            }
        }

        System.out.println(dp[15][15]);
    }
}