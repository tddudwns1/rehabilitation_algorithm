import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 친구 네트워크 정보를 저장하기 위한 정보
 * 임의의 root node, 그 root 기준 size(친구 수)
 */
class Info {
    String parent;
    int size = 1;

    public Info(String parent) {
        this.parent = parent;
    }
}

/**
 * 기본적인 union_find 문제
 */
public class boj_4195_친구네트워크 {
    static Map<String, Info> infos;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            int f = Integer.parseInt(br.readLine());

            // 사람들이 String으로 무분별하게 주어지므로 map 채택
            infos = new HashMap<>();

            for (int i = 0; i < f; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());

                String person1 = st.nextToken();
                String person2 = st.nextToken();

                // 그 사람들에 대한 정보가 없으면 초기 정보 삽입
                if (!infos.containsKey(person1))
                    infos.put(person1, new Info(person1));
                if (!infos.containsKey(person2))
                    infos.put(person2, new Info(person2));

                // 친구 네트워크 계산 시작
                union(person1, person2);

                // 네트워크 내부 사람 수 출력
                sb.append(infos.get(infos.get(find(person1)).parent).size).append("\n");
            }
        }

        System.out.println(sb);
    }

    /**
     * 해당 사람의 기준 root node를 지정하는 메서드
     * @param person 해당 사람
     * @return root node
     */
    private static String find(String person) {
        Info now = infos.get(person);

        // 자신이 root node라면 recursion 중지
        if (person.equals(now.parent))
            return person;

        // 과정 중에 있는 node들의 부모도 root node로 변경
        return now.parent = find(now.parent);
    }

    /**
     * 두 사람의 네트워킹 여부를 확인하고 아니라면 연결하는 메서드
     * @param person1
     * @param person2
     */
    private static void union(String person1, String person2) {
        String key1 = find(person1);
        String key2 = find(person2);

        // 이미 같은 그룹이라면 중지
        if (key1.equals(key2))
            return;

        // 정당 합당
        Info info1 = infos.get(key1);
        Info info2 = infos.get(key2);

        info1.parent = info2.parent;
        info2.size += info1.size;
    }
}
