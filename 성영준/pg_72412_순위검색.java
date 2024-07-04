import java.util.Arrays;
import java.util.StringTokenizer;

class Info implements Comparable<Info> {
    int info;
    int score;

    public Info(int info, int score) {
        this.info = info;
        this.score = score;
    }

    @Override
    public int compareTo(Info o) {
        return score - o.score;
    }
}

public class pg_72412_순위검색 {
    public int[] solution(String[] info, String[] query) {
        int[] answer = {};

        String[] lang = {"cpp", "java", "python"};
        String[] posi = {"backend", "frontend"};
        String[] care = {"junior", "senior"};
        String[] food = {"chicken", "pizza"};
        String[][] infoArray = {lang, posi, care, food};

        int n = info.length;
        Info[] infos = new Info[n];
        for (int i = 0; i < n; i++) {
            int nowInfo = 0;
            StringTokenizer st = new StringTokenizer(info[i]);

            for (int j = 0; j < 4; j++) {
                int nowNum = Arrays.asList(infoArray[j]).indexOf(st.nextToken());
                nowInfo = (nowInfo << 2) + (1 << nowNum);
            }

            int nowScore = Integer.parseInt(st.nextToken());
            infos[i] = new Info(nowInfo, nowScore);
        }

        Arrays.sort(infos);

        int m = query.length;
        answer = new int[m];
        for (int i = 0; i < m; i++) {
            int nowInfo = 0;
            String[] st = query[i].split(" ");

            for (int j = 0; j < 4; j++) {
                int nowNum = Arrays.asList(infoArray[j]).indexOf(st[j * 2]);

                if (nowNum == -1)
                    for (int k = 0; k < infoArray[j].length; k++)
                        nowInfo = (nowInfo << 1) + 1;
                else
                    nowInfo = (nowInfo << 2) + (1 << nowNum);
            }

            int nowScore = Integer.parseInt(st[7]);

            int count = 0;

            int index = findIndex(infos, nowScore);
            for (; index < n; index++)
                if ((infos[index].info & nowInfo) == infos[index].info)
                    count++;

            answer[i] = count;
        }

        return answer;
    }

    private static int findIndex(Info[] infos, int target) {
        int left = 0;
        int right = infos.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;

            if (target <= infos[mid].score)
                right = mid - 1;
            else if (target > infos[mid].score)
                left = mid + 1;
        }

        return left;
    }
}