import java.io.*;
import java.util.*;

class Solution {
    public int solution(int[][] targets) {
        int answer = 0;
    
        // 순서대로 정렬
        Arrays.sort(targets, (t1, t2) -> {
            if(t1[1] == t2[1]) {
                 return Integer.compare(t1[0], t2[0]);
            }
            
            return Integer.compare(t1[1], t2[1]);
        });
            
        
        int end = Integer.MIN_VALUE;
        
        // 개구간(열린 구간이므로 *10 해서 범위 늘려줌)
        for(int[] target: targets){
            target[0] = target[0]*10;
            target[1] = target[1]*10;
        }
        
        // 요격가능 범위 하기 위해 +1, -1;
        for (int[] target : targets) {
            int s = target[0] + 1;
            int e = target[1] - 1;
            
            // 요격 했는지 확인, 안했으면 answer +1, 마지막 요격 위치 설정
            if (s > end) {
                answer++;
                end = e;
            }
        }
        
        return answer;
    }
}