import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution {
	public static void main(String...args) throws IOException {
		BufferedReader br = new BufferedReader(
			new InputStreamReader(System.in)
		);
		StringTokenizer st = new StringTokenizer(br.readLine());
		int T = Integer.parseInt(st.nextToken());
		StringBuilder sb = new StringBuilder();
		for(int tc=1; tc<=T; ++tc) {
			sb
				.append('#')
				.append(tc)
				.append(' ')
				.append(solve(br))
				.append('\n');
		}
		System.out.print(sb.toString());
	}

	static String solve(BufferedReader br) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N, B;
		N = Integer.parseInt(st.nextToken());
		B = Integer.parseInt(st.nextToken());
		int[] H = new int[N];
		st = new StringTokenizer(br.readLine());
		for (int i=0; i<N; ++i)
			H[i] = Integer.parseInt(st.nextToken());
		Arrays.sort(H);
		int result = dfs(H, N, B, 0, Integer.MAX_VALUE, 0);
		return Integer.toString(result - B);
	}
    
	static int dfs(int[] H, int N, int B, int accum, int best, int depth) {
		if(depth==N)
			return accum < B ? best : Integer.min(accum, best);
		int curr = accum + H[depth];
		if (curr > B)
			return Integer.min(curr, best);
		int resultOnPresent = dfs(H, N, B, curr, best, depth+1);
		int resultOnAbsent = dfs(H, N, B, accum, resultOnPresent, depth+1);
		return resultOnAbsent;
	}
}
