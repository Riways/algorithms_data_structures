package data_structures.graphs;



import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;

class UnorientedUnweightGraphTest {
	
	UnorientedUnweightGraph graph;
	
	
	private void generateGraphWithSevenVertexes() {
		graph = new UnorientedUnweightGraph(7, 0.3);
	}
	private void generateGraphWithFortyVertexes() {
		graph = new UnorientedUnweightGraph(40, 0.1);
	}
	private void generateGraphWithTenThousandVertexes() {
		graph = new UnorientedUnweightGraph(10000, 0.3);
	}

	@Test
	void printMatrixTest() {
		generateGraphWithSevenVertexes();
		graph.printAdjacencyMatrix();
	}
	
	@Test
	void bfsTest() {
		System.out.println("-----------------");
		generateGraphWithSevenVertexes();
		graph.printAdjacencyMatrix();
		graph.bfs('A', 'G');
		System.out.println("-----------------");
	}
	@Test
	void dfsTest() {
		System.out.println("-----------------");
		generateGraphWithSevenVertexes();
		graph.printAdjacencyMatrix();
		graph.dfs('A', 'G');
		System.out.println("-----------------");
	}
	
//	@Test
//	void bfsStressTest() {
//		int bfsWins = 0;
//		int dfsWins = 0;
//		
//		for(int i = 0; i<10; i++) {
//			generateGraphWithTenThousandVertexes();
//			int lastCharIndex = 'A' + graph.size();
//			char from = (char) ThreadLocalRandom.current().nextInt(65,lastCharIndex);
//			char to = (char) ThreadLocalRandom.current().nextInt(65,lastCharIndex);
//			System.out.println("-----------------");
//			System.out.println("Path from " + from + " to "  +to);
//			long start = System.currentTimeMillis();
//			graph.bfs(from, to);
//			long end = System.currentTimeMillis();
//			
//			System.out.println("-----------------");
//		}
//	}
	@Test
	void bfsDfsBenchmark() {
		int bfsWins = 0;
		int dfsWins = 0;
		
		for(int i = 0; i<10; i++) {
			generateGraphWithTenThousandVertexes();
//			generateGraphWithFortyVertexes();
//			graph.printAdjacencyMatrix();
			int lastCharIndex = 'A' + graph.size();
			char from = (char) ThreadLocalRandom.current().nextInt(65,lastCharIndex);
			char to = (char) ThreadLocalRandom.current().nextInt(65,lastCharIndex);
			System.out.println("-----------------");
			System.out.println("Path from " + from + " to "  +to);
			long bfsTime;
			long dfsTime;
			long start = System.currentTimeMillis();
			graph.bfs(from, to);
			long end = System.currentTimeMillis();
			bfsTime = end-start;
			start=System.currentTimeMillis();
			graph.dfs(from, to);
			end = System.currentTimeMillis();
			dfsTime = end-start;
			System.out.println("BFS time: " + (double)bfsTime/1000 + " seconds");
			System.out.println("DFS time: " + (double)dfsTime/1000 + " seconds");
			if(bfsTime>dfsTime) {
				dfsWins++;
			}else if(bfsTime<dfsTime) {
				bfsWins++;
			}
			System.out.println("-----------------");
		}
		if(bfsWins==dfsWins) {
			System.out.println("DRAW!");
		}else if(bfsWins>dfsWins) {
			System.out.println("BFS WIN!");
		}else {
			System.out.println("DFS WIN!");
		}
		System.out.println("DFS: " + dfsWins);
		System.out.println("BFS: " + bfsWins);
		
	}
	
	

}
