import heapq
from typing import List, Dict

MAXIMUM_WEIGHT: int = 10

def solve():
	V: int = None 
	E: int = None
	V, E = map(int, input().split())
	nodeCnt: int = V+1;
	edges: List[Dict[List[int]]] = []
	for i in range(nodeCnt):
		edges.append({})
	for i in range(E):
		origin: int = None
		destination: int = None
		weight: int = None
		origin, destination, weight = map(int, input().split())
		edges[origin][destination] = min(edges[origin].get(destination, MAXIMUM_WEIGHT), weight)
		edges[destination][origin] = min(edges[destination].get(origin, MAXIMUM_WEIGHT), weight)

	pq = []
	heapq.heappush(pq, [0, 0])
	confirmedCnt: int = 0
	confirmed: List[bool] = [];
	for i in range(nodeCnt):
		confirmed.append(False)
	distances: int = 0
	while pq and (confirmedCnt != nodeCnt):
		weight, destination = heapq.heappop(pq)
		if confirmed[destination]: continue
		confirmedCnt += 1
		confirmed[destination] = True
		distances += weight
		for nextDestination, nextWeight in edges[destination].items():
			if confirmed[nextDestination]: continue
			heapq.heappush(pq, [
				nextWeight,
				nextDestination
			])
	return distances

def main():
	T: int = int(input())
	for tc in range(1, T + 1):
		print(f"#{tc} {solve()}");

if __name__ == "__main__":
	main()