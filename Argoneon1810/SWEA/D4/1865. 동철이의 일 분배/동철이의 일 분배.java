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
		int N = Integer.parseInt(st.nextToken());
		double[][] P = new double[N][N];
		for(int r=0; r<N; ++r) {
			st = new StringTokenizer(br.readLine());
			for(int c=0; c<N; ++c)
				P[r][c] = Double.parseDouble(st.nextToken()) / 100;
		}
		int[][] PAMax = new int[N][N];
		for(int i=0; i<N; ++i) argmax(P, PAMax, N, i);
		boolean[] selected = new boolean[N];
		double result = dfsPermutation(P, selected, PAMax, N, 1, 0, 0);
		return String.format("%6f", result*100);
	}

	static double dfsPermutation(
		double[][] P, boolean[] selected,
		int[][] PAMax, int N,
		double accum, double best,
		int depth
	) {
		if(depth==N)
			return Double.max(best, roundFn(accum));
		double localBest = best;
		for(int i : PAMax[depth]) {
			if(selected[i]) continue;
			double curr = accum * P[depth][i];
			if(curr <= localBest) break;
			selected[i] = true;
			localBest = dfsPermutation(
				P, selected, 
				PAMax, N, 
				curr, localBest, 
				depth+1
			);

			selected[i] = false;
		}
		return localBest;
	}

	static double roundFn(double raw) {
		int t = (int)(raw*1_000_000_000);
		int tt = t%10;
		t-=tt;
		if(tt>=5) t+=10;
		return ((double)t)/1_000_000_000;
	}

	static void argmax(double[][] P, int[][] toFill, int N, int id) {
		Double last = 1.0;
		boolean[] selected = new boolean[N];
		for(int j=0; j<N; ++j) {
			double localBest = -1.0;
			int localBestIdx = -1;
			for(int i=0; i<N; ++i) {
				double curr = P[id][i];
				if (curr <= last && !selected[i]) {
					if (P[id][i] > localBest) {
						localBest = P[id][i];
						localBestIdx = i;
					}
				}
			}
			toFill[id][j] = localBestIdx;
			selected[localBestIdx] = true;
			last = localBest;
		}
	}
}
