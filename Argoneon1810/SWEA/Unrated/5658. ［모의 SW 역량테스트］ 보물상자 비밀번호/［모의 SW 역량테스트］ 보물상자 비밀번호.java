import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 16진수 숫자 배열 (글)
 * N = [8, 28] & 4^i (글)
 * K는 배열에서 나올 수 있는 모든 수의 종류보다 크게 주어지지 않음 (글)
 * 0~9 + A~F (대문자)
 * 
 * 시계방향 순서대로 높은 자리 숫자 (글)
 * 
 * 회전 가능 (글)
 * 회전은 변 단위가 아니라 숫자 단위 (그림 보고 판단)
 * 회전하다 보면 이전에 이미 본 숫자가 나올 수 있으며 (글)
 * 서로 같은 수로 취급 (번역) (주의하라고만 되어 있음)
 *
 * 자물쇠 정의 (글)
 * - 비밀번호 있음
 * - 비밀번호는 다이얼의 숫자로 만들 수 있는 모든 수 중 K번째로 큰 수를
 *   10진수로 한 수
 *   - 즉 숫자 하나
 * 
 * 입력
 * - N개의 16진수 숫자
 * - 찾아야 할 K
 * 
 * 해석
 * - 각 변에 숫자가 4개 있다면, 4번 회전했다면 이미 옆 변으로 모든 숫자가
 *   넘어가 이전과 같은 숫자가 나옴
 *   즉 다이얼에서 형성 가능한 모든 수 조합은 각 변 길이 만큼의 회전으로 나옴
 * - N는 4의 배수로 8 이상 28 이하라 했으므로, 한 변의 길이는 최소 2, 최대 7
 *   주어진 N을 4로 나누면 반드시 최대 회전 개수를 구할 수 있다.
 */
class Solution {
	public static void main(String args[]) throws IOException {
		// TODO
		AutoStringTokenizer st = new AutoStringTokenizer(
			new BufferedReader(new InputStreamReader(System.in))
		);
		int T = Integer.parseInt(st.nextToken());
		StringBuilder sb = new StringBuilder();
		for(int tc=1; tc<=T; ++tc)
			sb	.append('#')
				.append(tc)
				.append(' ')
				.append(solve(st))
				.append('\n');
		System.out.print(sb.toString());
	}

	static String solve(AutoStringTokenizer st) throws IOException {
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		char[] raw = st.nextToken().toCharArray();
		int sideLen = N/4;
		Set<Integer> s = new HashSet<>();
		for(int i=0; i<sideLen; ++i) {
			char[][] sides = fourSides(raw, N, sideLen);
			for (char[] side : sides)
				s.add(toNumber(side));
			next(raw, N);
		}
		PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
		for(Integer i : s)
			pq.offer(i);
		for(int i=0; i<K-1; ++i)
			pq.poll();
		return Integer.toString(pq.poll());
	}

	static void next(char[] prev, int N) {
		char t = prev[N-1];
		for(int i=N-1; i>=1; --i)
			prev[i] = prev[i-1];
		prev[0] = t;
	}

	static char[][] fourSides(char[] raw, int N, int sideLen) {		
		char split[][] = new char[4][sideLen];
		for(int i=0; i<raw.length; ++i)
			split[i/sideLen][i%sideLen] = raw[i];
		return split;
	}

	static int toNumber(char[] cArr) {
		int val = 0;
		for(int i=0; i<cArr.length; ++i)
			val = (val<<4) + hex2dec(cArr[i]);
		return val;
	}

	static int hex2dec(char c) {
		if(c>='A' && c<='F')
			return 10+(int)(c-'A');
		return (int)(c-'0');
	}

	static class AutoStringTokenizer {
		BufferedReader br;
		StringTokenizer st;
		AutoStringTokenizer(BufferedReader br) throws IOException {
			this.br = br;
		}
		String nextToken() throws IOException {
			if(st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}
	}
}
