import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class boj_2138_전구와스위치 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        char[] standard = br.readLine().toCharArray();
        char[] target = br.readLine().toCharArray();

        System.out.println(process(n, standard, target));
    }

    private static int process(int n, char[] standard, char[] target) {
        if (Arrays.equals(standard, target))
            return 0;

        int last = n - 1;

        int answer1 = switchCount(standard.clone(), target, last, 0);
        int answer2 = switchCount(pressTheSwitch(standard.clone()), target, last, 1);

        if (Math.min(answer1, answer2) == -1)
            return Math.max(answer1, answer2);
        else
            return Math.min(answer1, answer2);
    }

    private static int switchCount(char[] electricBulbs, char[] target, int end, int answer) {
        int secondFromTheEnd = end - 1;
        for (int i = 0; i < secondFromTheEnd; i++) {
            if (electricBulbs[i] != target[i]) {
                pressTheSwitch(i + 1, electricBulbs);
                answer++;
            }
        }
        if (electricBulbs[end] != target[end]) {
            pressTheSwitch(end, secondFromTheEnd, electricBulbs);
            answer++;
        }

        if (Arrays.equals(electricBulbs, target))
            return answer;
        else
            return -1;
    }

    private static char[] pressTheSwitch(char[] electricBulbs) {
        changeStatus(0, electricBulbs);
        changeStatus(1, electricBulbs);

        return electricBulbs;
    }

    private static void pressTheSwitch(int i, char[] electricBulbs) {
        changeStatus(i - 1, electricBulbs);
        changeStatus(i, electricBulbs);
        changeStatus(i + 1, electricBulbs);
    }

    private static void pressTheSwitch(int end, int secondFromTheEnd, char[] electricBulbs) {
        changeStatus(secondFromTheEnd, electricBulbs);
        changeStatus(end, electricBulbs);
    }

    private static void changeStatus(int i, char[] electricBulbs) {
        if (electricBulbs[i] == '1')
            electricBulbs[i] = '0';
        else
            electricBulbs[i] = '1';
    }
}