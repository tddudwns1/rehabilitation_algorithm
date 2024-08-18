import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * union find 문제
 */
public class boj_3830_교수님은기다리지않는다 {
    /**
     * 샘플의 정보
     *  1. 같은 그룹의 부모를 저장, 사실 상 root임
     *  2. 루트와의 차이를 저장
     *      만약 1과 2가 100 차이고
     *      2와 3이 100 차이면
     *          루트인 1과 3은 200차이를 저장하기 위함
     */
    static class Sample {
        int parent;
        long diffFromRoot = 0;

        public Sample(int parent) {
            this.parent = parent;
        }
    }

    final static String UNKNOWN = "UNKNOWN\n";
    static Sample[] samples;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int n = Integer.parseInt(st.nextToken());
            if (n == 0)
                break;

            int m = Integer.parseInt(st.nextToken());

            // 초기화
            samples = new Sample[n + 1];
            for (int i = 1; i <= n; i++)
                samples[i] = new Sample(i);

            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());

                char signal = st.nextToken().charAt(0);
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                if (signal == '!') {
                    int w = Integer.parseInt(st.nextToken());
                    union(a, b, w);
                } else {
                    if (find(a) == find(b))
                        // 루트와의 차이를 출력
                        //   만약 1과 2가 100 차이고
                        //   2와 3이 100 차이면
                        //       루트인 1과 3은 200차이를 저장하기 위함
                        sb.append(samples[b].diffFromRoot - samples[a].diffFromRoot).append("\n");
                    else
                        // 서로 비교한 적이 없고, 비교할 대상이 없는 경우
                        sb.append(UNKNOWN);
                }
            }
        }

        System.out.println(sb);
    }

    /**
     * 두 샘플을 비교하는 메서드
     *
     *  두 샘플의 기준 샘플(root)를 확인
     *  같을 경우 find 과정에서 계산한 값으로 진행
     *  다를 경우 한 샘플의 기준 샘플을 다른 샘플의 기준 샘플로 통일하고,
     *      해당 샘플에 루트 노드와의 차이를 계산하기 위해
     *      현재 두 샘플 무게 차이에 기준 샘플과의 차이를 합산
     * @param a 샘플 1
     * @param b 샘플 2
     * @param w 샘플 1 2 무게 차이
     */
    static void union(int a, int b, int w) {
        int newA = find(a);
        int newB = find(b);

        if (newA != newB) {
            samples[newB].parent = newA;
            samples[newB].diffFromRoot = w + samples[a].diffFromRoot - samples[b].diffFromRoot;
        }
    }

    /**
     * 기준 샘플(root) 초기화 및 압축하고, 기준 샘플과 차이를 부모 노드와 단계적으로 계산하는 메서드
     *
     * 당장 샘플의 기준 샘플까지 거슬러 올라가며, 현재 저장 된(union에서) 기준 노드와의 차이를 갱신
     * 기준 샘프롤 부터 내려 오며 당장 부모와 차이를 갱신
     * 현재 샘플의 기준 노드를 초기화
     * @param n 확인 할 샘플
     * @return 기준 샘플
     */
    static int find(int n) {
        if (n != samples[n].parent) {
            int parent = find(samples[n].parent);
            samples[n].diffFromRoot += samples[samples[n].parent].diffFromRoot;
            samples[n].parent = parent;
        }
        return samples[n].parent;
    }
}
