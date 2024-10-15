package practice.플로이드워셜;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class boj_11403_경로찾기 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        int[][] costs = new int[n][n];
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            for (int j = 0; j < n; j++) {
                costs[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int center = 0; center < n; center++) {
            for (int start = 0; start < n; start++) {
                for (int end = 0; end < n; end++) {
                    if (costs[start][center] != 1)
                        continue;
                    if (costs[center][end] != 1)
                        continue;
                    costs[start][end] = 1;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int start = 0; start < n; start++) {
            for (int end = 0; end < n; end++) {
                sb.append(costs[start][end]).append(" ");
            }
            sb.append("\n");
        }

        System.out.println(sb);
    }
}
