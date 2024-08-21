import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 복잡한 dfs 문제
 */
public class boj_12843_복수전공 {
    static int connect = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 강의 수
        int n = Integer.parseInt(st.nextToken());
        // 관계 수
        int m = Integer.parseInt(st.nextToken());

        /**
         * 컴퓨터 강의와 소프트웨어 강의를 입력 받는다
         * 그럼 컴퓨터 강의와 소프트웨어 강의가 1대 1로 매칭되는 수만큼 전체 수에서 빼면 정답이 나온다
         *
         * 매칭이 안 된 강의들은 뺴는 수에 포함이 되지 않노
         * 1대 1로 매칭되는 강의들은 빼면 되기 때문
         *
         * 앞으로의 풀이는 컴퓨터 강의를 기준으로 소프트웨어 강의를 매칭시킬 예정
         */

        // 컴퓨터 강의 기준으로 저장
        List<Integer> computers = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int number = Integer.parseInt(st.nextToken());
            String department = st.nextToken();

            if (department.equals("c"))
                computers.add(number);
        }

        // 컴퓨터 강의 기준으로 매칭된 소프트웨어 강의 저장
        List<Integer>[] connectBySoftware = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++)
            connectBySoftware[i] = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int computer = Integer.parseInt(st.nextToken());
            int software = Integer.parseInt(st.nextToken());

            connectBySoftware[computer].add(software);
        }

        // 소프트웨어 강의 기준으로 매칭된 컴퓨터 강의 저장 용
        int[] connectByComputer = new int[n + 1];

        // 연결 되는 수를 계산
        for (int now : computers)
            if (dfs(now, connectBySoftware, connectByComputer, new boolean[n + 1]))
                connect++;

        // 전체 강의 수에서 연결된 수 만큼 감소한 결과 출력
        System.out.println(n - connect);
    }

    /**
     * 컴퓨터 강의와 소프트웨어 강의와 연결되는 정보를 계산하는 메서드
     *
     * 해당 컴퓨터 강의와 연결되는 소프트웨어 강의를 확인함
     *  1. 이미 이번 과정에서 연결을 했다면 스킵
     *  2. 전체 과정에서 연결 된 적 없다면 연결
     *  3. 만약 이미 연결된 소프트웨어 강의와 연결을 희망하는 컴퓨터 강의가 있다면
     *      이전의 강의는 다음 연결되는 소프트웨어 강의와 연결 가능 여부를 확인
     *      3-1. 만약 연결이 된다면 한 칸 씩 밀려 새로 연결
     *      3-2. 만약 연결이 안 된다면 이번 소프트웨어 강의와는 연결이 안 되므로 1로 돌아감
     * @param now
     * @param connectBySoftware
     * @param connectByComputer
     * @param visited
     * @return
     */
    private static boolean dfs(int now, List<Integer>[] connectBySoftware, int[] connectByComputer, boolean[] visited) {
        for (int software : connectBySoftware[now]) {
            if (visited[software])
                continue;
            visited[software] = true;

            if (connectByComputer[software] == 0 || dfs(connectByComputer[software], connectBySoftware, connectByComputer, visited)) {
                connectByComputer[software] = now;
                return true;
            }
        }

        return false;
    }
}
