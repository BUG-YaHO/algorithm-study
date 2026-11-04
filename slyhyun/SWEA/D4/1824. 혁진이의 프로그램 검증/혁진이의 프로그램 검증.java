/*
 * [문제]
 * - R*C 격자에 적힌 명령을 따라 이동하며 프로그램을 실행한다.
 * - 격자 밖으로 이동하면 반대편으로 이어진다.
 * - 메모리에는 0 ~ 15의 값이 저장되고, ?에서는 네 방향 중 하나를 선택할 수 있다.
 * - 명령 @에 도달해 프로그램을 멈출 수 있는지 구한다.
 *
 * [입력]
 * - 격자의 세로 R, 가로 C: 2 ~ 20
 * - 각 칸에는 명령 문자 하나가 주어진다.
 *
 * [설계]
 * - 같은 위치라도 이동 방향이나 메모리 값이 다르면 이후 실행 결과가 달라진다.
 * - (행, 열, 방향, 메모리 값)을 하나의 상태로 보고 BFS로 가능한 상태를 탐색한다.
 * - ?를 만나면 네 방향으로 각각 이동하고, 이미 방문한 상태는 다시 탐색하지 않는다.
 * - @에 도달하면 YES, 모든 상태를 탐색해도 도달하지 못하면 NO를 출력한다.
 */
import java.io.*;
import java.util.*;

public class Solution {
    static int R, C;
    static char[][] map;
    static boolean[][][][] visited;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            st = new StringTokenizer(br.readLine());
            R = Integer.parseInt(st.nextToken());
            C = Integer.parseInt(st.nextToken());

            map = new char[R][C];
            visited = new boolean[R][C][4][16];

            for (int i = 0; i < R; i++) {
                map[i] = br.readLine().toCharArray();
            }

            sb.append("#").append(tc).append(" ");
            sb.append(bfs() ? "YES" : "NO").append("\n");
        }

        System.out.print(sb);
    }

    static boolean bfs() {
        Queue<int[]> q = new ArrayDeque<>();

        q.offer(new int[]{0, 0, 1, 0});
        visited[0][0][1][0] = true;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int r = cur[0];
            int c = cur[1];
            int dir = cur[2];
            int memory = cur[3];
            char cmd = map[r][c];

            if (cmd == '@') return true;

            switch (cmd) {
                case '<': dir = 3; break;
                case '>': dir = 1; break;
                case '^': dir = 0; break;
                case 'v': dir = 2; break;
                case '_': dir = (memory == 0) ? 1 : 3; break;
                case '|': dir = (memory == 0) ? 2 : 0; break;
                case '+': memory = (memory + 1) % 16; break;
                case '-': memory = (memory + 15) % 16; break;
                default:
                    if (cmd >= '0' && cmd <= '9') memory = cmd - '0';
                    break;
            }

            for (int d = 0; d < 4; d++) {
                if (cmd != '?' && d != dir) continue;

                int nr = (r + dr[d] + R) % R;
                int nc = (c + dc[d] + C) % C;

                if (visited[nr][nc][d][memory]) continue;

                visited[nr][nc][d][memory] = true;
                q.offer(new int[]{nr, nc, d, memory});
            }
        }

        return false;
    }
}
