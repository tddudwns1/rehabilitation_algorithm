import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class boj_19637_IF문좀대신써줘 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        TreeMap<Integer, String> nickNameMap = new TreeMap<>();
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());

            String nickName = st.nextToken();
            int limit = Integer.parseInt(st.nextToken());

            if (nickNameMap.containsKey(limit))
                continue;
            nickNameMap.put(limit, nickName);
        }


        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < m; i++) {
            int power = Integer.parseInt(br.readLine());
            Integer key = nickNameMap.ceilingKey(power);
            sb.append(nickNameMap.get(key)).append("\n");
        }

        System.out.println(sb);
    }
}
