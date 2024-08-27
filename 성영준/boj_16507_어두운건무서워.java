import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * prefixSum 문제
 */
public class boj_16507_어두운건무서워 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        // 밝기 저장
        int[][] brights = new int[r + 1][c + 1];
        for (int y = 1; y <= r; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 1; x <= c; x++) {
                brights[y][x] = Integer.parseInt(st.nextToken());
            }
        }

        // 밝기 누적합 계산
        prefixSum(r, c, brights);

        StringBuilder sb = new StringBuilder();

        // 누적합 계산
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());

            int r1 = Integer.parseInt(st.nextToken());
            int c1 = Integer.parseInt(st.nextToken());

            int r2 = Integer.parseInt(st.nextToken());
            int c2 = Integer.parseInt(st.nextToken());

            sb.append(getPixelAvg(r1 - 1, c1 - 1, r2, c2, brights)).append("\n");
        }

        System.out.println(sb);
    }

    /**
     * 가로 -> 세로 순으로 누적합 계산
     */
    private static void prefixSum(int r, int c, int[][] brights) {
        for (int y = 1; y <= r; y++) {
            for (int x = 2; x <= c; x++) {
                brights[y][x] += brights[y][x - 1];
            }
        }

        for (int y = 2; y <= r; y++) {
            for (int x = 1; x <= c; x++) {
                brights[y][x] += brights[y - 1][x];
            }
        }
    }

    /**
     * 큰 네모 - 왼쪽 네모 - 위쪽 네모 + 겹치는 네모 / 네모 크기
     */
    private static int getPixelAvg(int r1, int c1, int r2, int c2, int[][] brights) {
        int sumOfRange = brights[r2][c2] - brights[r1][c2] - brights[r2][c1] + brights[r1][c1];
        int sizeOfRange = (r2 - r1) * (c2 - c1);
        return sumOfRange / sizeOfRange;
    }
}
