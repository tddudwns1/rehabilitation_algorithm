import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * dp 문제
 */
public class boj_18427_함께블록쌓기 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int h = Integer.parseInt(st.nextToken());

        // memorize용, 학생 별 블록의 높이 저장
        int[][] blocks = new int[n + 1][h + 1];

        // 계산용, 학생 별 블록의 높이 저장
        Queue<Integer>[] students = new ArrayDeque[n + 1];
        for (int i = 1; i <= n; i++)
            students[i] = new ArrayDeque<>();

        for (int student = 1; student <= n; student++) {
            // 이 학생 블록들을 사용하지 않을 수 있음
            // 그럴 땐 그대로 내려야 함
            students[student].add(0);

            st = new StringTokenizer(br.readLine());
            while (st.hasMoreTokens()) {
                // 현재 블록의 높이가 탑의 높이를 넘지 않는다면 저장 (테스트 케이스에는 없음)
                int now = Integer.parseInt(st.nextToken());
                if (now > h)
                    continue;

                students[student].add(now);
                blocks[student][now] = 1;
            }
        }

        // 현재 보는 학생의 현재 블록 높이 기준으로 이전 학생의 현황과 비교하여 가능성 기록
        int before = 1;
        for (int nowStudent = 2; nowStudent <= n; nowStudent++) {
            for (int nowBlockHeight : students[nowStudent])
                for (int candidate = nowBlockHeight; candidate <= h; candidate++)
                    blocks[nowStudent][candidate] = (blocks[nowStudent][candidate] + blocks[before][candidate - nowBlockHeight]) % 1_0007;

            before = nowStudent;
        }

        System.out.println(blocks[n][h]);
    }
}
