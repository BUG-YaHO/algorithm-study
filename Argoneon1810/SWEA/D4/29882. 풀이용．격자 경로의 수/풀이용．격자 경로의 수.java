import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Solution {
	public static void main(String args[]) throws Exception {
		new Solution().solve();
	}

	void solve() throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in));
		int T;
		T = Integer.parseInt(br.readLine().trim());
		StringBuilder sb = new StringBuilder();
		for (int test_case = 1; test_case <= T; test_case++)
			sb.append('#').append(test_case)
					.append(' ').append(solveInner(br))
					.append('\n');
		System.out.print(sb);
	}

	String solveInner(BufferedReader br) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int[] m = new int[N*M];
		m[0] = 1;
		for(int r=0; r<N; ++r) {
			for(int c=0; c<M; ++c) {
				if(m[r*M+c]>0) continue;
				m[r*M+c] = (r-1 >= 0 ? m[(r-1)*M+c] : 0)
						+ (c-1 >= 0 ? m[r*M+c-1] : 0);
			}
		}
		return Integer.toString(m[N*M-1]);
	}

	String print(int[] m, int N, int M) {
		StringBuilder sb = new StringBuilder();
		for(int r=0; r<N; ++r) {
			if (r != 0) sb.append('\n');
			for(int c=0; c<M; ++c) {
				if (c != 0) sb.append(' ');
				sb.append(m[r*M+c]);
			}
		}
		return sb.toString();
	}
}
