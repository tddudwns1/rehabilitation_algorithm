import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * dp 문제
 */
public class boj_2629_양팔저울 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 총 무게 확인 범위를 딱 맞게 선언하기 위한 변수
        int sum = 0;

        // 추의 무게를 저장하며 sum 계산
        int weightsCount = Integer.parseInt(br.readLine());
        int[] weights = new int[weightsCount];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < weightsCount; i++)
            sum += weights[i] = Integer.parseInt(st.nextToken());

        // 구슬의 무게가 측정 가능한지 판별할 배열
        // 위에서 구한 sum 활용하여 최대 범위 부여
        boolean[] canCheck = new boolean[sum + 1];
        canCheck[0] = true;

        // dp를 효율적으로 진행하기 위한 변수
        int beforeMax = 0;

        // 2가지 방향의 dp 진행
        // 모두 역추적을 통한 갱신

        // 추 무게를 더하는 경우
        for (int weight : weights) {
            for (int j = beforeMax; j >= 0; j--)
                if (canCheck[j])
                    canCheck[j + weight] = true;
            beforeMax += weight; // 현재까지 추의 합산 값을 갱신
        }

        // 추 무게를 빼는 경우
        for (int weight : weights)
            for (int j = weight + 1; j <= beforeMax; j++)
                if (canCheck[j])
                    canCheck[j - weight] = true;

        StringBuilder sb = new StringBuilder();

        // 구슬 무게를 측정하며 정답 출력
        int beadsCount = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < beadsCount; i++)
            sb.append(yn(Integer.parseInt(st.nextToken()), canCheck, sum));

        System.out.println(sb);
    }

    private static String yn(int bead, boolean[] canCheck, int sum) {
        // 구슬 무게가 최대 측정 가능 무게를 벗어나지 않으며
        // 측정 가능한 무게면 Y
        if (bead <= sum && canCheck[bead])
            return "Y ";
        // 아니면 N
        return "N ";
    }
}
