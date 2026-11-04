
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution {

    /**
     *
     * 문제 설명:
     * 인수가 사는 마을에는 N개의 집이 있고, 각 집에는 한 명의 사람이 살고 있다
     * N개의 집을 정점으로 볼 때, 도로는 어떤 집에서 다른 집으로 이동이 가능한 단방향 간선
     * 각각에 대해서 이동하는데 시간이 정해져있다
     * 도로는 모든 집들 간에 이동이 가능하도록 구성
     *
     * 집에 1번에서 N번까지의 번호를 붙일 때, 인수의 집은 X번 집이다
     *
     * 자신에 집에서 X번 집으로 오고가기 위해 최단 시간으로 이동한다
     * 도로가 단방향이기 때문에, 이동하는 도로는 오고 갈 떄 다르다
     *
     * => 가장 시간이 오래 걸리는 최소 루트를 구해라
     *
     *
     * 입력:
     * 0: T
     * 첫 줄: N(N개의 집, 1~1000), M(간선 개수, 1~10000), X(가야하는 집, 1~1000)이 한 줄에 주어짐
     * M개의 줄: x(출발하는 집), y(도착하는 집), c(걸리는 시간)인 단방향 도로
     *
     * 출력:
     * 오고 가는데 걸리는 시간이 가장 긴 거리
     *
     * 풀이방법:
     *
     *
     */

    static int N;
    static int M;
    static int X;

    static int answer;

    static class Node {
        int to;
        int weight;

        Node(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    // 노드그래프 만들기
    // 목적지랑 가중치 (리스트 배열에 담음)
    // 양방향 그래프 -> 양쪽으로 만들면 됨

    // 기준점 노드에서 출발하는 두 개 구해서
    // 더한거 최소값 구하면 됨(리턴값 배열)


    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {

            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            X = Integer.parseInt(st.nextToken());

            List<Node>[] graph = new List[N+1];
            List<Node>[] reverse = new List[N+1];

            for (int i = 1; i <= N; i++) {
                graph[i] = new ArrayList<>();
                reverse[i] = new ArrayList<>();
            }

            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                int weight = Integer.parseInt(st.nextToken());

                graph[from].add(new Node(to, weight));
                reverse[to].add(new Node(from, weight));

            }

            int[] fromArr = dijikstra(graph);
            int[] toArr = dijikstra(reverse);

            answer = 0;

            for (int i = 1; i <= N; i++) {
                int sum = fromArr[i] + toArr[i];
                answer = Math.max(answer, sum);
            }


            sb.append("#").append(t).append(" ").append(answer).append("\n");

        }


        System.out.println(sb);
    }

    static int[] dijikstra(List<Node>[] list){

        int[] arr = new int[N+1];
        Arrays.fill(arr, Integer.MAX_VALUE);

        Queue<Node> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.weight, o2.weight));
        arr[X] = 0;
        pq.offer(new Node(X, arr[X]));
        
        while (!pq.isEmpty()) {
            Node node = pq.poll();
            int to = node.to;
            int weight = node.weight;
            
            if (weight > arr[to]) {
                continue;
            }

            for (Node next : list[to]) {
                int nextTo = next.to;
                int nextWeight = arr[to] +  next.weight;

                if (nextWeight < arr[nextTo]) {
                    arr[nextTo] = nextWeight;
                    pq.offer(new Node(nextTo, nextWeight));
                }

            }
        }
        return arr;


    }



}

