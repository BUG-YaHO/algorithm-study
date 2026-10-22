import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution {
	static final int FROM = 0;
	static final int TO = 1;
	static final int WEIGHT = 2;

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
		long[] X = new long[N];
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<N; ++i)
			X[i] = Integer.parseInt(st.nextToken());
		long[] Y = new long[N];
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<N; ++i)
			Y[i] = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());
		double E = Double.parseDouble(st.nextToken());
		long[][] edges = new long[N*(N-1)/2][];
		int tails = 0;
		for(int r=0; r<N; ++r)
			for(int c=r+1; c<N; ++c)
				edges[tails++] = new long[] {r, c, SQL2(X, Y, r, c)};
		Arrays.sort(
			edges,
			(lhs, rhs) -> Long.compare(lhs[WEIGHT], rhs[WEIGHT])
		);
		int[] parents = new int[N];
		for(int i=0; i<N; ++i) parents[i] = i;
		int i = 0;
		long weightSum = 0L;
		int selected = 0;
		while(selected < N-1) {
			long[] currentEdge = edges[i++];
			int from = (int)currentEdge[FROM];
			int to = (int)currentEdge[TO];
			if(!tryUnion(parents, from, to))
				continue;
			weightSum += currentEdge[WEIGHT];
			++selected;
		}
		return String.format("%d", Math.round(E*weightSum));
	}

	static long SQL2(long[] X, long[] Y, int r, int c) {
		long xDiff = X[c] - X[r];
		long yDiff = Y[c] - Y[r];
		return xDiff*xDiff + yDiff*yDiff;
	}

	static boolean tryUnion(int[] parents, int toBeParent, int toBeChild) {
		int rootA = find(parents, toBeParent);
		int rootB = find(parents, toBeChild);
		if(rootA == rootB)
			return false;
		parents[rootB] = rootA;
		return true;
	}
	static int find(int[] parents, int toFindAncestry) {
		int current = toFindAncestry;
		int parent = parents[current];
		if(parent == current)
			return current;
		return parents[current] = find(parents, parent);
	}
}
