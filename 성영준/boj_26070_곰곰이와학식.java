import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class boj_26070_곰곰이와학식 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 슥사 하고싶은 곰곰이 현황
        long[] bears = new long[3];
        for (int i = 0; i < 3; i++)
            bears[i] = Long.parseLong(st.nextToken());

        st = new StringTokenizer(br.readLine());

        // 슥사 티켓 현황
        long[] tickets = new long[3];
        for (int i = 0; i < 3; i++)
            tickets[i] = Long.parseLong(st.nextToken());

        // 배 부른 곰곰이 수
        long answer = 0;
        // 메뉴 번호
        int now = 0;

        while (true) {
            // 밥 먹을 수 있는 곰곰이 수
            long min = Math.min(bears[now], tickets[now]);
            if (min != 0) {
                // 티켓, 곰곰이, 배 부른 곰곰이 업데이트
                answer += min;
                tickets[now] -= min;
                bears[now] -= min;
            }

            // 더 사줄 수 있는지 여부
            if (isFinish(tickets))
                break;

            // 남은 티켓 손해 교환
            int next = (now + 1) % 3;
            tickets[next] += tickets[now] / 3;
            tickets[now] %= 3;
            now = next;
        }

        System.out.println(answer);
    }

    /**
     * 남은 티켓이 3장 미만이라면 못 사줌
     * @param tickets 슥사 티켓 현황
     * @return 더 사줄 수 있나?
     */
    private static boolean isFinish(long[] tickets) {
        for (int i = 0; i < 3; i++)
            if (tickets[i] >= 3)
                return false;
        return true;
    }


}
