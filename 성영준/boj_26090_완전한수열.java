import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 에라토스테네스의 체 와 누적 합을 요구하는 문제
 */
public class boj_26090_완전한수열 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        // 누적합을 저장할 배열
        int[] sequence = new int[n + 1];
        for (int i = 1; i <= n; i++)
            sequence[i] = Integer.parseInt(st.nextToken()) + sequence[i - 1];

        // 최종 누적 합과 배열의 길이를 비교하여 큰 값을 한계 값으로 지정
        // 이 문제의 경우 0도 포함되어 있기 때문에 누적합이 n보다 작을 수 있어서 고려함
        int limit = Math.max(sequence[n], n) + 1;
        boolean[] isNotPrime = new boolean[limit];
        setNotPrimeArray(isNotPrime, limit);

        // 정답으로 출력할 변수
        int count = 0;
        // 배열의 길이 또한 소수여야 하기에 n + 1에서 2를 뺸 값 까지 탐색
        // 변수화 한 이유는 for문의 범위가 계산식으로 되어 있으면 느림
        // 속도를 줄이기 위한 잡기술 1
        int last1 = n - 1;
        for (int i = 0; i < last1; i++) {
            // j = i + 2; j <= last1으로 하지 않은 이유는
            // 내부의 첫 번째 조건문이 i - j로 계산식이 세워지면 느려지기에
            // j = 2 부터 출발하여 첫 번째 조건문에서 바로 값이 쓰이도록 함
            // 대신 limit의 경우 따로 계산하여 변수화
            // 속도를 줄이기 위한 잡기술 2
            int last2 = n - i;
            for (int j = 2; j <= last2; j++) {
                // 길이가 소수가 아니라면 skip
                if (isNotPrime[j])
                    continue;

                // 합이 소수가 아니라면 skip
                int sum = sequence[i + j] - sequence[i];
                if (isNotPrime[sum])
                    continue;

                count++;
            }
        }

        System.out.println(count);
    }

    /**
     * 에라토스테네스의 체를 생성하는 함수
     * @param isNotDecimal 에라토스테네스의 체
     * @param limit 한계 값
     */
    private static void setNotPrimeArray(boolean[] isNotDecimal, int limit) {
        // 제곱된 값부터 탐색을 시작하기에 한계 값의 제곱근 까지 범위 고려
        int sqrt = (int) Math.sqrt(limit);

        // 이 문제의 경우 0도 포함되어 있어서 0과 1을 사전에 true로 초기화
        isNotDecimal[0] = true;
        isNotDecimal[1] = true;
        // 소수의 제곱부터 한계 값까지 합성수로 지정
        for (int i = 2; i <= sqrt; i++)
            if (!isNotDecimal[i])
                for (int j = i * i; j < limit; j += i)
                    isNotDecimal[j] = true;
    }
}
