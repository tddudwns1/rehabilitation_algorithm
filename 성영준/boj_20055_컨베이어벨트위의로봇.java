import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class boj_20055_컨베이어벨트위의로봇 {
    static class Robot {
        int position;

        public Robot(int position) {
            this.position = position;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken()); // 벨트의 절반
        int k = Integer.parseInt(st.nextToken()); // 내구도 0 기준 개수

        int beltLength = 2 * n;
        int[] belt = new int[beltLength];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < beltLength; i++) {
            belt[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(process(n, k, belt, beltLength));
    }

    private static int process(int n, int k, int[] belt, int beltLength) {
        Queue<Robot> positionOfRobot = new ArrayDeque<>();
        int stage = 0;

        int halfBelt = n - 1;
        int doubleOfBelt = beltLength - 1;
        int cursor = 0;

        while (k > 0) {
            stage++;

            cursor = turnTheBelt(doubleOfBelt, cursor);
            int endOfBelt = (halfBelt + cursor) % beltLength;

            removeTheRobotAtTheEnd(positionOfRobot, endOfBelt);

            k -= moveRobots(doubleOfBelt, cursor, n, belt, positionOfRobot);
            removeTheRobotAtTheEnd(positionOfRobot, endOfBelt);

            k -= putRobot(cursor, belt, positionOfRobot);
        }

        return stage;
    }

    private static int turnTheBelt(int doubleOfBelt, int cursor) {
        if (cursor == 0)
            cursor = doubleOfBelt;
        else
            cursor--;

        return cursor;
    }

    private static int moveRobots(int doubleOfBelt, int cursor, int n, int[] belt, Queue<Robot> positionOfRobot) {
        if (positionOfRobot.isEmpty())
            return 0;

        int zeroCount = 0;
        int prevRobot = -1;
        for (Robot now : positionOfRobot) {
            if (isSlotDurabilityEnd(doubleOfBelt, belt, now, prevRobot, zeroCount))
                zeroCount++;
            prevRobot = now.position;
        }

        return zeroCount;
    }

    private static boolean isSlotDurabilityEnd(int doubleOfBelt, int[] belt, Robot now, int prevRobot, int zeroCount) {
        int next = moveTheRobot(doubleOfBelt, now.position);
        if (belt[next] == 0)
            return false;

        if (prevRobot == next)
            return false;

        return --belt[now.position = next] == 0;
    }

    private static int moveTheRobot(int doubleOfBelt, int position) {
        if (position == doubleOfBelt)
            position = 0;
        else
            position++;

        return position;
    }

    private static int putRobot(int cursor, int[] belt, Queue<Robot> positionOfRobot) {
        if (belt[cursor] == 0)
            return 0;

        positionOfRobot.add(new Robot(cursor));
        if (--belt[cursor] == 0)
            return 1;
        return 0;
    }

    private static void removeTheRobotAtTheEnd(Queue<Robot> positionOfRobot, int endOfBelt) {
        if (positionOfRobot.isEmpty())
            return;

        if (positionOfRobot.peek().position == endOfBelt)
            positionOfRobot.poll();
    }
}
