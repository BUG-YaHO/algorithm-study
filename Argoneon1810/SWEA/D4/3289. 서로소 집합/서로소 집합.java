import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
	static final int UNION = 0;
	static final int FIND = 1;

	public static void main(String...args) throws IOException {
		AutoStringTokenizer st = new AutoStringTokenizer(
			new BufferedReader(
				new InputStreamReader(System.in)
			)
		);
		int T = Integer.parseInt(st.nextToken());
		StringBuilder sb = new StringBuilder();
		for (int tc=1; tc<=T; ++tc) {
			sb
				.append('#')
				.append(tc)
				.append(' ')
				.append(solve(st))
				.append('\n');
		}
		System.out.print(sb.toString());
	}

	static String solve(AutoStringTokenizer st) throws IOException {
		// === 입력 받기 ===
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		// === 입력 받기 끝 ===
		int arr[] = new int[n+1];
		for(int i=0; i<=n; ++i)
			arr[i] = i;
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<m; ++i) {
			int c = Integer.parseInt(st.nextToken());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			switch(c) {
				case UNION:
					union(arr, a, b);
					break;
				case FIND:
					sb.append(isJoint(arr, a, b) ? 1 : 0);
					break;
			}
		}
		return sb.toString();
	}

	/**
	 * unchecking union
	 */
	static void union(int arr[], int a, int b) {
		int pa = find(arr, a);
		int pb = find(arr, b);
		arr[pb] = pa;
	}

	/**
	 * 경로 축약 find
	 */
	static int find(int arr[], int a) {
		int pa = arr[a];
		if(pa==a) return pa;
		while(true) {
			int c = pa;
			pa = arr[c];
			if(pa==c) break;
		}
		while(true) {
			int n = arr[a];
			if (n==pa) break;
			arr[a] = pa;
			a = n;
		}
		return pa;
    }

	static boolean isJoint(int arr[], int a, int b) {
		int pa = find(arr, a);
		int pb = find(arr, b);
		return pa==pb;
	}

	static class AutoStringTokenizer {
		BufferedReader br;
		StringTokenizer st;
		AutoStringTokenizer(BufferedReader br) throws IOException {
			this.br = br;
			st = new StringTokenizer(br.readLine());
		}
		String nextToken() throws IOException {
			if(!st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}
	}
}
