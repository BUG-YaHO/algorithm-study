import java.util.Arrays;

class Solution {
	static final int FROM = 0;
	static final int TO = 1;
	static final int WEIGHT = 2;
    public int solution(int n, int[][] costs) {
        int answer = 0;
		Arrays.sort(
			costs, 
			(lhs, rhs) -> Integer.compare(lhs[WEIGHT], rhs[WEIGHT])
		);
		int[] parents = new int[n];
		for(int i=0; i<n; ++i) parents[i]=i;
		boolean[] visited = new boolean[n];
		int costSum = 0;
		for(int i=0; i<costs.length; ++i) {
			if(isVisitedAll(visited)) break;
			int[] edge = costs[i];
			if(!tryUnion(parents, edge[FROM], edge[TO]))
				continue;
			costSum += edge[WEIGHT];
		}
		answer = costSum;
        return answer;
    }
	boolean tryUnion(int[] parents, int toBeParent, int toBeChild) {
		int pa = find(parents, toBeParent);
		int pb = find(parents, toBeChild);
		if (pa == pb) return false;
		parents[pb] = pa;
		return true;
	}
	int find(int[] parents, int toFindAncestry) {
		int lastParent = -1;
		int currentParent = toFindAncestry;
		do {
			lastParent = currentParent;
			currentParent = parents[currentParent];
		} while(lastParent != currentParent);
		return currentParent;
	}
	boolean isVisitedAll(boolean[] visited) {
		for(int i=0; i<visited.length; ++i)
			if (!visited[i]) return false;
		return true;
	}
}
