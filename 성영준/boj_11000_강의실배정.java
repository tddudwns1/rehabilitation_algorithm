import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class boj_11000_강의실배정 {
    static class Class implements Comparable<Class> {
        int start;
        int end;

        public Class(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Class o) {
            return start - o.start;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        Class[] classes = new Class[n];

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());

            classes[i] = new Class(start, end);
        }
        Arrays.sort(classes);

        Queue<Integer> runningClass = new PriorityQueue<>();
        runningClass.add(0);

        for (Class now : classes) {
            if (now.start >= runningClass.peek())
                runningClass.poll();
            runningClass.add(now.end);
        }

        System.out.println(runningClass.size());
    }
}
