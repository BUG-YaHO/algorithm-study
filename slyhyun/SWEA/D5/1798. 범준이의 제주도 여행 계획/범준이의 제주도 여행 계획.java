/*
 * [문제]
 * - M일 동안 관광포인트를 방문해 만족도의 합을 최대로 만든다.
 * - 관광포인트는 한 번만 방문할 수 있다.
 * - 하루에 이동과 관광을 합쳐 9시간(540분)을 넘기면 안 된다.
 * - 마지막 날 전에는 호텔에서 쉬고, 마지막 날에는 공항으로 돌아온다.
 *
 * [입력]
 * - 장소 개수 N: 3 ~ 35
 * - 여행 기간 M: 1 ~ 5
 * - 장소 종류: 공항 A, 호텔 H, 관광포인트 P(소요 시간, 만족도)
 *
 * [설계]
 * - 여행 경로 탐색 (DFS)
 *   - 날짜, 현재 장소, 사용한 시간, 방문한 관광포인트와 만족도를 관리한다.
 *   - 관광포인트를 방문하거나 호텔로 이동하고, 마지막 날에는 공항으로 돌아간다.
 *
 * - 시간 조건
 *   - 관광포인트까지의 이동·관광 시간에 호텔(마지막 날은 공항)까지 돌아갈 시간을 더해 540분 이내인지 확인한다.
 *   - 호텔은 그날 시간이 허용하는 곳을 모두 확인한다.
 *
 * - 가지치기와 정답 갱신
 *   - 같은 날짜, 장소, 방문 집합이면 더 일찍 도착한 경로만 탐색한다.
 *   - 남은 시간으로 얻을 수 있는 만족도의 상한이 현재 최댓값보다 작으면 탐색을 멈춘다.
 *   - 마지막 날 공항으로 돌아왔을 때 최대 만족도와 경로를 갱신한다.
 */
import java.io.*;
import java.util.*;

public class Solution {
	static class Place {
		int id, time, score, minHotel, minVisit;

		Place(int id, int time, int score) {
			this.id = id;
			this.time = time;
			this.score = score;
		}
	}

	static int N, M, airport, max, total, minExit;
	static int[][] dist;
	static List<Place> points;
	static List<Integer> hotels;
	static int[] route, answer;
	static HashMap<Long, Integer> memo;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;

		int T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());

			dist = new int[N][N];
			for (int i = 0; i < N - 1; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = i + 1; j < N; j++) {
					dist[i][j] = dist[j][i] = Integer.parseInt(st.nextToken());
				}
			}

			points = new ArrayList<>();
			hotels = new ArrayList<>();
			total = 0;

			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				char type = st.nextToken().charAt(0);

				if (type == 'A') airport = i;
				else if (type == 'H') hotels.add(i);
				else {
					int time = Integer.parseInt(st.nextToken());
					int score = Integer.parseInt(st.nextToken());
					points.add(new Place(i, time, score));
					total += score;
				}
			}

			minExit = Integer.MAX_VALUE;
			for (Place p : points) {
				p.minHotel = Integer.MAX_VALUE;
				p.minVisit = Integer.MAX_VALUE;

				for (int h : hotels) {
					p.minHotel = Math.min(p.minHotel, dist[p.id][h]);
				}

				for (int i = 0; i < N; i++) {
					if (i == p.id) continue;
					p.minVisit = Math.min(p.minVisit, dist[i][p.id] + p.time);
				}

				minExit = Math.min(minExit, dist[p.id][airport]);
				if (M > 1) minExit = Math.min(minExit, p.minHotel);
			}
			if (points.isEmpty()) minExit = 0;

			points.sort((a, b) -> {
				long left = (long) a.score * b.minVisit;
				long right = (long) b.score * a.minVisit;
				if (left != right) return Long.compare(right, left);
				return Integer.compare(b.score, a.score);
			});

			max = 0;
			route = new int[N + M];
			answer = new int[0];
			memo = new HashMap<>();

			dfs(1, airport, 0, 0L, 0, 0);

			sb.append("#").append(tc).append(" ").append(max);
			if (max > 0) {
				for (int place : answer) {
					sb.append(" ").append(place + 1);
				}
			}
			sb.append("\n");
		}

		System.out.print(sb);
	}

	static void dfs(int day, int cur, int time, long visited, int score, int depth) {
		if (max == total) return;

		long state = (visited << 9) | ((long) day << 6) | cur;
		Integer prev = memo.get(state);
		if (prev != null && prev <= time) return;
		memo.put(state, time);

		if (score + upperBound(day, time, visited) < max) return;

		for (Place p : points) {
			long bit = 1L << p.id;
			if ((visited & bit) != 0) continue;

			int nextTime = time + dist[cur][p.id] + p.time;
			int back = (day == M) ? dist[p.id][airport] : p.minHotel;
			if (nextTime + back > 540) continue;

			route[depth] = p.id;
			dfs(day, p.id, nextTime, visited | bit, score + p.score, depth + 1);
		}

		if (day < M) {
			for (int h : hotels) {
				if (time + dist[cur][h] > 540) continue;

				route[depth] = h;
				dfs(day + 1, h, 0, visited, score, depth + 1);
			}
		}
		else if (time + dist[cur][airport] <= 540 && score > max) {
			route[depth] = airport;
			max = score;
			answer = Arrays.copyOf(route, depth + 1);
		}
	}

	static double upperBound(int day, int time, long visited) {
		int remain = (M - day + 1) * (540 - minExit) - time;
		if (remain <= 0) return 0;

		double sum = 0;
		for (Place p : points) {
			if ((visited & (1L << p.id)) != 0) continue;

			if (p.minVisit <= remain) {
				remain -= p.minVisit;
				sum += p.score;
			}
			else {
				sum += (double) p.score * remain / p.minVisit;
				break;
			}
		}

		return sum;
	}
}
