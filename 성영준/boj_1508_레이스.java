import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 매개 변수 탐색 문제
 */
public class boj_1508_레이스 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 심판이 있을 수 있는 위치를 저장할 배열
        // 이 문제의 경우 오름차순이 보장되어 있으므로 정렬 필요 x
        int[] referees = new int[k];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < k; i++)
            referees[i] = Integer.parseInt(st.nextToken());

        // 두 심판의 최대 거리를 구하고
        int bestMid = findBestMid(n, m, k, referees);
        // 최대 거리를 기준으로 심판을 배치한 후
        String answer = calculateAnswer(bestMid, m, k, referees);
        // 정답 출력
        System.out.println(answer);
    }

    /**
     * 두 심판의 최대 거리를 구하는 메서드
     *
     * 매개 변수 탐색 알고리즘을 활용
     * 이진 탐색과 같은 매커니즘
     *
     * 특정 거리 기준 조건을 만족하면 더 큰 범위로,
     * 만족하지 않는다면 더 작은 밤위로 진행
     *
     * 최종 거리를 도출
     *
     * @param n 트랙의 길이
     * @param m 심판의 수
     * @param k 심판이 있을 수 있는 위치의 수
     * @param referees 심판이 있을 수 있는 위치를 저장한 배열
     * @return 최대 근접 거리
     */
    private static int findBestMid(int n, int m, int k, int[] referees) {
        int left = 1;
        int right = n;
        int bestMid = 0;

        portal:
        while (left <= right) {
            // 현재 확인 할 최대 근접한 기준 거리
            int mid = (left + right) >> 1;
            // 이전에 선택된 위치, 다음에 선택될 위치의 기준값
            // 첫 위치는 무조건 선택되도록 -mid로 선정
            int before = -mid;
            // 배치된 심판의 수
            int count = 0;

            for (int i = 0; i < k; i++) {
                // 현재 설 수 있는 위치가
                int position = referees[i];

                // 이전에 선 심판으로 부터 기준 거리보다 가깝다면 skip
                if (position - before < mid)
                    continue;

                // 현재 위치에 심판을 세우고
                before = position;

                // 만약 심판이 덜 배치됐다면 계속 진행
                if (++count != m)
                    continue;

                // 배치가 다됐다면
                // 현재 거리로는 충분한 것이므로
                // 더 먼 거리 탐색
                left = mid + 1;
                bestMid = mid;
                continue portal;
            }

            // 만약 배치된 선수가 적다면
            // 기준 거리가 긴 것이므로
            // 기준 거리 축소
            right = mid - 1;
        }

        return bestMid;
    }

    /**
     * 최대 거리를 기준으로 심판을 배치하는 메서드
     *
     * 주어진 거리를 기준으로 해당 거리 내의 위치면 0, 외의 거리면 1을 출력
     * 이진수 활용
     * 최대 50명이므로 long 활용
     * StringBuilder 써도 됨
     * @param bestMid 최대 근접 거리
     * @param m 심판의 수
     * @param k 심판이 있을 수 있는 위치의 수
     * @param referees 심판이 있을 수 있는 위치를 저장한 배열
     * @return 심판 배치도
     */
    private static String calculateAnswer(int bestMid, int m, int k, int[] referees) {
        // 정답으로 출력할 수
        long answer = 0;
        int before = -bestMid;
        int count = 0;

        for (int i = 0; i < k; i++) {
            int position = referees[i];

            // 심판 배치도 한칸 왼쪽으로 밈
            answer = answer << 1;

            if (position - before < bestMid)
                continue;

            // 심판 배치
            answer++;
            before = position;

            if (++count != m)
                continue;

            while (++i < k)
                // 빈칸 배치
                answer = answer << 1;

            break;
        }

        return Long.toBinaryString(answer);
    }
}
