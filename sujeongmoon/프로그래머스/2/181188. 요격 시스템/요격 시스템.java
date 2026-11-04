// A나라가 B나라의 군사기지에 융단폭격.
// B나라가 A나라의 폭격 미사일을 요격하고자 함.
// B나라의 미사일을 최소로 사용해서 모든 폭격 미사일을 요격.

// A가 발사한 폭격 미사일은 x축에 평행한 직선 형태의 모양, 개구간을 나타내는 정수 쌍 (s,e) // 개구간이머지
// 발사된 미사일은 해당 x좌표에 걸쳐있는 모든 폭격 미사일을 관통하여 한 번에 요격 가능(row 모두, col 고정)
// 단, 개구간 s,e로 표현되는 폭격 미사일은 s와 e에서 발사하는 요격 미사일로는 요격할 수 없음(먼소리지?)
// 요격 미사일은 실수인 x 좌표에서도 발사할 수 있다 (정수가 아닐 수도 있다? 뭔소리야)

// 각 폭격 미사일의 x좌표 범위 목록 targets이 매개변수
// 모든 폭격 미사일을 요격하기 위해 필요한 요격 미사일 수의 최솟값을 리턴!!
import java.util.*;

class Solution {
    public int solution(int[][] targets) {
        int answer = 0;
        
        // 일단 두 배로 좌표를 늘려서 생각해야함. -> 걍 이상이하 둘 다 포함하면 되려나?
        // pq?
        // 가장 빨리 끝나는 순으로 정렬해보면.
        /*
        1, 4 (쏘기, 포인터 4로 설정)
        4, 5 (o)
        3, 7 (o)
        5, 8 (쏘기, 포인터 8로 설정)
        5, 12 (o)
        11, 13 (쏘기, 포인터 13으로 설정)
        10, 14 (o)
        
        이렇게 하고 boolean배열을 만들어서 이게 쏴졌는지 안쏴졌는지 찾기? (포인터를 따로 두기)
        */
        
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1[1], o2[1]));
        int shot = -1;
        
        for (int[] now : targets) {
            pq.offer(now);
        }
        
        while (!pq.isEmpty()) {
            int[] now = pq.poll();
            // System.out.println(now[1]);
            
            if (now[0] >= shot) {
                answer++;
                // System.out.println("답: " + answer);
                shot = now[1];
            }
        }

        return answer;
    }
}