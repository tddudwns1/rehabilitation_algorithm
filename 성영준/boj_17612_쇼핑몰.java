import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * Priority Queue + implementation
 */
public class boj_17612_쇼핑몰 {
    /**
     * 카운터 현황 저장용
     * 나가는 순서의 우선순위
     *  1. 소요 시간 적은 순
     *  2. 카운터 번호 높은 순
     */
    static class Counter implements Comparable<Counter> {
        int number;
        Customer customer;

        public Counter(int number, Customer customer) {
            this.number = number;
            this.customer = customer;
        }

        @Override
        public int compareTo(Counter o) {
            if (customer.w != o.customer.w)
                return Integer.compare(customer.w, o.customer.w);
            return Integer.compare(o.number, number);
        }
    }

    /**
     * 고객 정보 저장용
     * 번호와 소요시간 저장
     */
    static class Customer {
        int id;
        int w;

        public Customer(int id, int w) {
            this.id = id;
            this.w = w;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 빈 카운터 현황
        // 낮은 번호 우선순위
        Queue<Integer> empty = new PriorityQueue<>();
        for (int i = 1; i <= k; i++)
            empty.add(i);

        // 계산대 앞에 줄 선 고객 목록
        Queue<Customer> waiting = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());

            int id = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            waiting.add(new Customer(id, w));
        }

        System.out.println(process(empty, waiting));
    }

    /**
     * 고객이 빠져나가는 순서에 따른 계산을 하는 메서드
     *
     * 1. 빈 계산대가 있고, 줄 선 사람이 있다면, 낮은 계산대부터 부여
     * 2. 계산이 가장 짧게 걸리는 사람 기준으로 시간 소요
     * 3. 소요된 시간 기준 같은 시간을 가진 사람들 제거 후
     *  빈 계산대 업데이트 + 정답 변수 업데이트
     * 4. 1번 반복
     *
     * @param empty 빈 계산대 현황
     * @param waiting 계산대에 줄 현황
     * @return 정답 변수(고객 N명의 회원번호를 쇼핑몰을 빠져나가는 순서대로 r1, r2, ..., rN이라 할 때, 1×r1 + 2×r2 + ... + N×rN)
     */
    private static long process(Queue<Integer> empty, Queue<Customer> waiting) {
        Queue<Counter> paying = new PriorityQueue<>();

        long answer = 0;

        int time = 0;
        int count = 0;

        while (!waiting.isEmpty() || !paying.isEmpty()) {
            // empty 가 있다면 부여
            while (!empty.isEmpty() && !waiting.isEmpty()) {
                Customer now = waiting.poll();
                now.w += time;
                paying.add(new Counter(empty.poll(), now));
            }

            // 현재 시각과 계산중인 사람의 시간이 일치하지 않으면 시간 흐름
            if (!paying.isEmpty())
                time = paying.peek().customer.w;

            // 흐른 시간과 일치하는 사람 제거 후
            // count 증가 answer 에 추가
            while (!paying.isEmpty() && paying.peek().customer.w == time) {
                Counter now = paying.poll();

                answer += (long) ++count * now.customer.id;
                empty.add(now.number);
            }
        }

        return answer;
    }
}
