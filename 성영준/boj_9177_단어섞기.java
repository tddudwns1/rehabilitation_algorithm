import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class boj_9177_단어섞기 {
    static final String HEAD = "Data set ";
    static final String YES = ": yes\n";
    static final String NO = ": no\n";

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int n = Integer.parseInt(br.readLine());

        for (int i = 1; i <= n; i++) {
            sb.append(HEAD).append(i);

            StringTokenizer st = new StringTokenizer(br.readLine());
            char[] s1 = st.nextToken().toCharArray();
            char[] s2 = st.nextToken().toCharArray();
            char[] s3 = st.nextToken().toCharArray();

            int s1len = s1.length;
            int s2len = s2.length;
            int min = Math.min(s1len, s2len);

            // 조합이 가능한지 여부를 저장할 dp배열
            boolean[][] dp = new boolean[s1len + 1][s2len + 1];
            // 첫 번째 줄 처리
            dp[0][0] = true;
            for (int x = 0; x < s2len; x++)
                // 현재 문자열 위치와 비교 문자열 위치가 같다면 true 아니면 break
                if (s2[x] == s3[x])
                    dp[0][x + 1] = true;
                else
                    break;

            for (int y = 0; y < s1len; y++)
                if (s1[y] == s3[y])
                    dp[y + 1][0] = true;
                else
                    break;

            // 두 번째 줄부터 나머지 처리
            int before = 0;
            for (int depth = 1; depth <= min; depth++) {
                // 가로 부터
                for (int x = 1; x <= s2len; x++)
                    // 위쪽 true && 추가할 세로 문자 == 비교 문자
                    if (dp[before][x] && s1[before] == s3[before + x])
                        dp[depth][x] = true;
                    // 왼쪽 true && 추가할 가로 문자 == 비교 문자
                    else if (dp[depth][x - 1] && s2[x - 1] == s3[before + x])
                        dp[depth][x] = true;

                // 세로 과정 위와 동일
                for (int y = 1; y <= s1len; y++)
                    if (dp[y][before] && s2[before] == s3[before + y])
                        dp[y][depth] = true;
                    else if (dp[y - 1][depth] && s1[y - 1] == s3[before + y])
                        dp[y][depth] = true;

                before = depth;
            }

            if (dp[s1len][s2len])
                sb.append(YES);
            else
                sb.append(NO);
        }

        System.out.println(sb);
    }
}
