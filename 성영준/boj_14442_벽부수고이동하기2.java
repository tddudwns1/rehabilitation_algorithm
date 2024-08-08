import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * bfs 문제
 */
public class boj_14442_벽부수고이동하기2 {
    /**
     * 해당 위치에 도착하기까지 몇번의 벽을 부수고 왔는지 저장할 클래스
     */
    static class Info {
        int y;
        int x;
        int breakCount;

        public Info(int y, int x, int breakCount) {
            this.y = y;
            this.x = x;
            this.breakCount = breakCount;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        char[][] map = new char[n][m];
        for (int i = 0; i < n; i++)
            map[i] = br.readLine().toCharArray();

        System.out.println(bfs(map, n, m, k));
    }

    /**
     * bfs를 통해 최단 경로를 탐색하는 매서드
     * @param map 맵
     * @param n 맵 세로 크기
     * @param m 맵 가로 크기
     * @param k 부술 수 있는 벽 수
     * @return 최단 거리
     */
    private static int bfs(char[][] map, int n, int m, int k) {
        int[][] move = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        // 부순 벽 수 별 경로 방문 여부
        boolean[][][] visited = new boolean[n][m][k + 1];
        // 위치 정보 저장
        Queue<Info> q = new ArrayDeque<>();

        // 출발지 시작
        q.add(new Info(0, 0, 0));

        // 도착지
        int destinationY = n - 1;
        int destinationX = m - 1;

        // 정답으로 출력할 이동 횟수
        int moveCount = 0;

        while (!q.isEmpty()) {
            // 현재 이동 횟수에서의 가능성 수
            int size = q.size();
            // 이동 횟수 추가
            moveCount++;

            while (size-- > 0) {
                // 현재 위치가
                Info now = q.poll();

                // 도착지면 끝, 이동 횟수 출력
                if (now.y == destinationY && now.x == destinationX)
                    return moveCount;

                for (int i = 0; i < 4; i++) {
                    // 다음 위치가 범위를 벗어나면 다음 위치 탐색
                    int dy = now.y + move[i][0];
                    if (dy < 0 || dy > destinationY)
                        continue;

                    int dx = now.x + move[i][1];
                    if (dx < 0 || dx > destinationX)
                        continue;

                    // 현재 부순 벽 수
                    int newBreakCount = now.breakCount;

                    // 다음 위치가 벽이면
                    if (map[dy][dx] == '1') {
                        // 벽을 부순 수가 한계라면 그 다음 위치 탐색
                        if (newBreakCount == k)
                            continue;

                        // 벽 부수고 이동 후 카운팅
                        newBreakCount++;
                    }

                    // 다음 위치가 이미 간 곳이라면 그 다음 위치 탐색
                    if (visited[dy][dx][newBreakCount])
                        continue;

                    // 다음 위치 방문
                    visited[dy][dx][newBreakCount] = true;
                    q.add(new Info(dy, dx, newBreakCount));
                }
            }
        }

        return -1;
    }
}
