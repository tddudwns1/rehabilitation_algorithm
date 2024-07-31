import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 복잡한 백트레킹 구현 문제
 */
public class boj_15684_사다리조작 {
    // dfs 탐색 중지 버튼
    static boolean flag = false;

    // 추가해야 하는 가로선 개수의 최솟값 (불가능한 경우로 초기화)
    static int answer = -1;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int h = Integer.parseInt(st.nextToken());

        // 사다리 판
        // 가로의 경우 1 ~ n-1까지 추가 가능하므로 양쪽으로 한칸씩 여유를 두기 위해 n + 1로 설정
        // 세로의 경우 1 ~ h까지 확인 하고 아래위로 확인하는 과정은 없기에 fit하게 h + 1로 설정
        boolean[][] ladder = new boolean[h + 1][n + 1];

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());

            int y = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());

            ladder[y][x] = true;
        }

        // 초기 설정된 사다리 판에서 가로 선이 홀수개인 라인이 3개 이상이라면 탐색 필요 x
        // 문제에서 제시한 최대 가로선의 개수가 3이기 때문
        // 사다리가 제자리로 돌아오기 위해선 짝수개의 왕복선이 필요
        if (checkOddLine(ladder, n, h) <= 3)
            // for문 범위 설정을 초기 설정된 사다리 판에서
            // 가로 선이 홀수 개면 1, 3개
            // 짝수 개면 0, 2개 더 놓도록 설정
            for (int limit = m % 2; limit < 4; limit += 2) {
                dfs(n, h, 1, 1, 0, limit, ladder);

                // 만약 가능한 수가 나왔다면
                if (flag) {
                    // 현재 선 개수를 저장하고 탐색 종료
                    answer = limit;
                    break;
                }
            }

        System.out.println(answer);
    }

    /**
     * 초기 설정된 사다리 판에서 가로 선이 홀수개인 라인 갯수를 확인하는 메서드
     * @param ladder 사다리 판
     * @param n 가로 줄 갯수
     * @param h 세로 줄 갯수
     * @return 홀수 라인 갯수
     */
    private static int checkOddLine(boolean[][] ladder, int n, int h) {
        // 총 홀수 라인 갯수
        int countOdd = 0;

        for (int x = 1; x < n; x++) {
            // 라인 갯수 홀수 여부
            boolean checkOdd = false;
            for (int y = 1; y <= h; y++)
                // 가로선이 있다면 짝 홀수 반전
                if (ladder[y][x])
                    checkOdd = !checkOdd;

            // 홀수면 카운팅
            if (checkOdd)
                countOdd++;
        }

        return countOdd;
    }

    /**
     * 정해진 가로선 수 만큼 조건에 맞는 위치에 놓는 dfs
     * @param n 가로선 갯수
     * @param h 세로선 갯수
     * @param startY 탐색을 시작 할 세로 위치
     * @param startX 탐색을 시작할 가로 위치
     * @param count 현재까지 더 놓은 가로선의 갯수
     * @param limit 더 놓을 전체 가로선의 갯수
     * @param ladder 사다리 판
     */
    private static void dfs(int n, int h, int startY, int startX, int count, int limit, boolean[][] ladder) {
        // 만약 가로선을 최대로 놓았다면 확인 시작
        if (count == limit) {
            // 확인
            if (!checkVerticalLine(n, h, ladder))
                return;

            // 맞다면 탐색 중지
            flag = true;
            return;
        }

        // 탐색이 겹치지 않도록 하기 위해 for문 2개로 분할
        // 첫 줄은 시작하는 y, 시작하는 x에서 시작
        for (int x = startX; x < n; x++)
            callDfs(n, h, startY, x, count, limit, ladder);

        // 다음 줄 부터 1부터 탐색 시작
        for (int y = startY + 1; y <= h; y++)
            for (int x = 1; x < n; x++)
                callDfs(n, h, y, x, count, limit, ladder);
    }

    /**
     * dfs를 호출하는 함수, 즉 가로선을 놓을 수 있어 다음단계로 넘어가도 되는지 확인하는 함수
     * @param n 이하 생략 (dfs 함수와 동일)
     * @param h
     * @param y
     * @param x
     * @param count
     * @param limit
     * @param ladder
     */
    private static void callDfs(int n, int h, int y, int x, int count, int limit, boolean[][] ladder) {
        // 현재 놓는 곳과, 놓으면서 닿을 양쪽에 가로선이 존재하는지 확인
        // 더하기 빼기 연산을 뒤로 미뤄 첫 조건에서 걸리면 계산하지 않도록 설정(잡기술)
        if (ladder[y][x] || ladder[y][x - 1] || ladder[y][x + 1])
            return;

        // 가로선 넣고 다음단계로 가기
        ladder[y][x] = true;
        dfs(n, h, y, x, count + 1, limit, ladder);

        // 이미 찾았으면 더 이상 탐색 진행 x
        if (flag)
            return;

        // 가로선 빼기
        ladder[y][x] = false;
    }

    /**
     * 완성된 사다리 판에서 모든 출발지가 일자로 내려가는지 확인하는 함수
     * @param n
     * @param h
     * @param ladder
     * @return
     */
    private static boolean checkVerticalLine(int n, int h, boolean[][] ladder) {
        for (int x = 1; x <= n; x++) {
            // 현재 가로 세로 위치를 기록할 변수
            int nowX = x;
            int nowY = 0;

            // 세로는 끝까지 쭉 내려감
            while (++nowY <= h) {
                // 가로는 가로선을 만나면 그쪽으로 이동
                if (ladder[nowY][nowX])
                    nowX++;
                else {
                    // 2번 계산하는 것을 피하기 위한 변수(잡기술)
                    int left = nowX - 1;
                    if (ladder[nowY][left])
                        nowX = left;
                }
            }

            // 만약 일자로 안 내려오면 확인 중지
            if (nowX != x)
                return false;
        }

        // 모든 과정을 수료했다면 통과
        return true;
    }
}
