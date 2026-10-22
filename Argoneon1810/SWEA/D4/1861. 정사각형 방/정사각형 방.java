import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 방 N*N개
 * N = [1, 10^3]
 * 왼쪽 위가 0,0
 * 각 방에 숫자 A=[1, N^2]
 * 4방탐색; 단 인접한 칸의 숫자가 나보다 딱 1 클때만
 * 어느 칸에서 출발해야 가장 많은 방으로 가 볼 수 있는가
 * 
 * 입력 일반적
 * 출력 출발 방 번호와 거기서 갈 수 있는 방 수. 동등하면 기록된 수가 작은 쪽
 * 
 * 분석
 * N이 100이므로 단순 탐색만 10_000
 * 실제로 각각 bfs를 해본다고 치면, bfs가 각각 최악 N^2 = 1_00_000_000
 * 사이클이 없는 조건이므로 루프는 걱정 안해도 된다
 * 탐색을 할 때 N^2, 그걸 역으로 타고 올라올 때 N^2. 단, 합연산
 * 계산상 2초가 나오므로, 완탐 안된다.
 * 
 * dp로 가야한다
 * - PQ에 모든 인덱스를 박는다. 정렬 순서는 그 칸에 기록된 값
 * - 그 칸이 이전에 방문된 적 있는 경우, 그 칸을 경유해 갈 수 있는 칸은 이미
 *   방문되었을 것이므로 생략한다.
 * - 그 칸이 이전에 방문된 적 없는 경우 그 칸이 갈 수 있는 칸은 없다 = 0
 * - 방문된 적 없는 칸을 시작으로 bfs하면서,
 *   방문할 때 마다 '앞으로 갈 수 있는 칸'을 1씩 갱신한다.
 *   이 때 그 칸이 이미 기록된 값이 있는 경우,
 *   내가 새로 가지고 간 '앞으로 갈 수 있는 칸'이 더 작은 경우에만 덮어쓰고
 *   내가 더 크거나 같으면 무시한다.
 */

class Solution {
	static final int DATA = 0;
	static final int ACCUM = 1;
	static int dt[] = new int[4];

	static final int INDEX = 0;
	static final int VALUE = 1;

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
		int N = Integer.parseInt(st.nextToken());
		int[][] grid = new int[2][N*N];
		PriorityQueue<int[]> pq = new PriorityQueue<>((lhs, rhs) -> {
			int compare = -Integer.compare(
				lhs[VALUE],
				rhs[VALUE]
			);
			if (compare!=0) return compare;
			return Integer.compare(lhs[INDEX], rhs[INDEX]);
		});
		for(int i=0; i<N*N; ++i) {
			grid[DATA][i] = Integer.parseInt(st.nextToken());
			grid[ACCUM][i] = Integer.MAX_VALUE;
			pq.offer(new int[] {i, grid[DATA][i]});
		}
		dt[0] = N; dt[1] = -N; dt[2] = 1; dt[3] = -1;
		// === 입력 받기 끝 ===
		Carrier carrier = new Carrier();
		while(!pq.isEmpty()) {
			int[] current = pq.poll();
			if(grid[ACCUM][current[INDEX]] != Integer.MAX_VALUE)
				// 방문된 적 있음 = 그 칸엔 이미 우월한 값이 기록됨
				continue;
			else {
				// 방문된 적 없음
				// 현재 칸을 출발점으로 bfs
				bfs(carrier, grid, N, current[INDEX]);
			}
		}
		return carrier.toString();
	}

	static void bfs(Carrier carrier, int grid[][], int N, int start) {
		Queue<Integer> q = new ArrayDeque<>();
		int tier = 2;
		q.offer(start);
		grid[ACCUM][start] = 1;
		carrier.tryUpdate(start, 0, grid[DATA][start]);
		while(!q.isEmpty()) {
			int currTierLen = q.size();
			for(int i=0; i<currTierLen; ++i) {
				int curr = q.poll();
				for(int j=0;j<4;++j) {
					int next = curr + dt[j];
					if(isOffgrid(N, curr, next)) continue;
					if(grid[DATA][next] != grid[DATA][curr]-1) continue;
					if(grid[ACCUM][next] < tier+1) continue;
					grid[ACCUM][next] = tier;
					q.offer(next);
					carrier.tryUpdate(next, tier, grid[DATA][next]);
				}
			}
			++tier;
		}
	}

	static boolean isOffgrid(int N, int prev, int curr) {
		if(curr<0) return true;
		if(curr>=N*N) return true;
		if((prev%N != curr%N) && (prev/N != curr/N)) return true;
		return false;
	}

	static class Carrier {
		private int idx;
		private int depth;
		private int value;
		Carrier() {
			idx = -1;
			depth = Integer.MIN_VALUE;
			value = Integer.MAX_VALUE;
		}
		private void set(int idx, int depth, int value) {
			this.idx = idx;  
			this.depth = depth;
			this.value = value;
		}         
		void tryUpdate(int idx, int depth, int value) {
			if(this.depth > depth) return;
			if(this.depth == depth && this.value < value) return;
			// 더 많이 갈 수 있으면서 기록된 값이 작음
			set(idx, depth, value);
		}
		public String toString() {
			StringBuilder sb = new StringBuilder();
			return sb   .append(value)
						.append(' ')
						.append(depth)
						.append(' ')
						.toString();
		}                         
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
