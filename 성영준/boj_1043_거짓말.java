import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 유니온파인드 이용 문제
 */
public class boj_1043_거짓말 {
    // root node 정보 저장 변수
    static int[] parents;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 각자 본인을 root로 두고 시작
        parents = new int[n + 1];
        for (int i = 1; i <= n; i++)
            parents[i] = i;

        // 추후에 진실을 말해야 하는지 여부를 한번에 조회를 위해 임시 배열에 저장
        st = new StringTokenizer(br.readLine());
        int know = Integer.parseInt(st.nextToken());
        int[] knows = new int[know];
        for (int i = 0; i < know; i++)
            knows[i] = Integer.parseInt(st.nextToken());

        // 각 조의 대표 1명 선정
        int[] groups = new int[m];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int members = Integer.parseInt(st.nextToken());

            // 대표 기준으로 그룹 생성
            int standard = groups[i] = Integer.parseInt(st.nextToken());
            for (int j = 1; j < members; j++)
                union(standard, Integer.parseInt(st.nextToken()));
        }

        // 아까 말한 추후가 와서
        // 진실 말해야 하는 그룹 장을 set에 저장
        Set<Integer> set = new HashSet<>();
        for (int now : knows)
            set.add(find(now));

        // set에 해당 그룹의 대표가 포함되는지 확인
        int answer = m;
        for (int i = 0; i < m; i++)
            if (set.contains(find(groups[i])))
                answer--;

        System.out.println(answer);
    }

    // 하단은 union_find 로직
    private static int find(int now) {
        if (now == parents[now])
            return now;

        return parents[now] = find(parents[now]);
    }

    private static void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX != rootY)
            parents[rootY] = rootX;
    }
}