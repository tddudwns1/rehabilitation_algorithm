import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

public class boj_1541_잃어버린괄호 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        
        // - 할 때 가장 큰 값으로 빼면 되기에
        // -가 아닌 뭉치들을 모아 한번에 뺌

        // 합한 수를 저장할 큐
        Queue<Integer> q = new ArrayDeque<>();
        // 누적 스트링을 저장할 빌더
        StringBuilder sb = new StringBuilder();
        // 누적 수를 저장할 변수
        int num = 0;

        // line을 순회
        for (int i = 0; i < line.length(); i++) {
            char now = line.charAt(i);

            // 문자면
            if (now != '+' && now != '-') {
                // 빌더에 스트링 누적
                sb.append(now);
                continue;
            }

            // 누적 스트링을 누적 수에 합함
            num += Integer.parseInt(sb.toString());
            // 누적 스트링 초기화
            sb.setLength(0);

            // 만약 빼기 기호였다면
            if (now == '-') {
                // 누적 수 q에 저장
                q.add(num);
                // 누적 수 초기화
                num = 0;
            }
        }

        // 마지막 수 q에 저장
        num += Integer.parseInt(sb.toString());
        q.add(num);

        // 최종 출력할 수, q에서 맨 앞수 뺌
        int total = q.poll();

        // q가 빌 때 까지
        while (!q.isEmpty()) {
            // 최종 출력할 수에 남은 배열만큼 빼기 반복
            total -= q.poll();
        }

        System.out.println(total);
    }
}
