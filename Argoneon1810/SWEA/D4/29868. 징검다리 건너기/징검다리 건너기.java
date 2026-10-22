import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
 
public class Solution {
    public static void main(String...args) throws IOException {
        BufferedReader br = new BufferedReader(
            new InputStreamReader(System.in)
        );
        StringTokenizer st = new StringTokenizer(br.readLine());
        int T = Integer.parseInt(st.nextToken());
        StringBuilder sb = new StringBuilder();
        for(int tc=1; tc<=T; ++tc)
            sb.append('#')
                .append(tc)
                .append(' ')
                .append(solve(br))
                .append('\n');
        System.out.print(sb.toString());
    }
    static String solve(BufferedReader br) throws IOException { 
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        return Integer.toString(f(N));
    }
    static final int[] memo = new int[40+1];
    static int f(int n) {
        int t = memo[n]; 
        if(t>0) return t;
        if(n<2) return 1;
        return memo[n] = f(n-1) + f(n-2);
    }
}