package data_structures.graphs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

//case sensitive
public class UnorientedUnweightGraph {

	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_GREEN_BACKGROUND = "\033[42m";;

//	private static final int MAX_VERT_AMOUNT = 10;

	boolean[][] adjacencyMatrix;

	private Vertex[] vertArr;

	private int currentVertCount;

	public UnorientedUnweightGraph(boolean[][] adjacencyMatrix) {
		currentVertCount = 0;
		this.adjacencyMatrix = adjacencyMatrix;
		generateVertArr();
	}

	public int size() {
		return currentVertCount;
	}

	// generates adjacencyMatrix and builds graph;
	public UnorientedUnweightGraph(int amountOfVertexes, double fillingRate) {
		currentVertCount = 0;
		try {
			adjacencyMatrix = generateAdjacencyMaxtrix(amountOfVertexes, fillingRate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		generateVertArr();
	}

	private void generateVertArr() {
		char startChar = 'A';
		vertArr = new Vertex[adjacencyMatrix.length];
		for (int i = 0; i < adjacencyMatrix.length; i++) {
			vertArr[i] = new Vertex((char) ((int) startChar + i));
		}
		currentVertCount = adjacencyMatrix.length;
	}

	private boolean isVertexPresent(char vertexLabel) {
		int sum = 'A' + currentVertCount - vertexLabel;
		if (sum < 0 || sum > currentVertCount)
			return false;
		return true;
	}

	private int getVertexIndexByLabel(char label) {
		return (char) (label - 'A');
	}

	private ArrayList<Vertex> getAdjacentVertexesByLabel(char label) {
		ArrayList<Vertex> adjVer = new ArrayList<UnorientedUnweightGraph.Vertex>();
		int labelIndex = getVertexIndexByLabel(label);
		for (int i = 0; i < adjacencyMatrix.length; i++) {
			if (adjacencyMatrix[labelIndex][i])
				adjVer.add(vertArr[i]);
		}
		return adjVer;
	}

	// breadth first search
	public void bfs(char startLabel, char targetLabel) {
		if (!isVertexPresent(startLabel)) {
			System.out.println("Vertex " + startLabel + " not found");
			return;
		}
		if (!isVertexPresent(targetLabel)) {
			System.out.println("Vertex " + targetLabel + " not found");
			return;
		}
		// We have vertexes in array;
		HashMap<Integer, ArrayList<Vertex>> depthAndVertexes = new HashMap<>();
		LinkedList<Vertex> queue = new LinkedList<Vertex>();
		int startVertexIndex = getVertexIndexByLabel(startLabel);
		vertArr[startVertexIndex].depth = 0;
		queue.add(vertArr[startVertexIndex]);
//		System.out.println(startLabel + " added to stack, depth: 0");
		Vertex findedVertex = null;
		while (!queue.isEmpty()) {
			Vertex currentVertex = queue.pop();
//			System.out.println("Vertex in process: " + currentVertex.label + " depth: " + currentVertex.depth);
			currentVertex.wasVisited = true;
			if (depthAndVertexes.containsKey(currentVertex.depth)) {
				depthAndVertexes.get(currentVertex.depth).add(currentVertex);
			} else {
				ArrayList<Vertex> list = new ArrayList<>();
				list.add(currentVertex);
				depthAndVertexes.put(currentVertex.depth, list);
			}
			if (currentVertex.label == targetLabel) {
				System.out.println("Label found!");
				findedVertex = currentVertex;
				break;
			}
			ArrayList<Vertex> adjVerextes = getAdjacentVertexesByLabel(currentVertex.label);
			for (Vertex vertex : adjVerextes) {
				if (vertex.wasVisited || vertex.depth != -1)
					continue;
				vertex.depth = currentVertex.depth + 1;
				queue.add(vertex);
//				System.out.println(vertex.label + " added to stack, depth: " + vertex.depth);
			}

		}
		if (findedVertex == null) {
			System.out.println("Target vertex doesn't available from start vertex");
			return;
		}
		Vertex pointer;
		pointer = findedVertex;
		for (int i = findedVertex.depth; i >= 0; i--) {
			System.out.print(pointer.label);
			if (pointer.depth != 0) {
				System.out.print("->");
			} else {
				break;
			}
			ArrayList<Vertex> vertexesByDepth = depthAndVertexes.get(pointer.depth - 1);
			for (Vertex vert : vertexesByDepth) {
				if (isVertexAdjacent(pointer.label, vert.label)) {
					pointer = vert;
					continue;
				}
			}
		}
		System.out.println();
		resetVertexes();
	}

	// depth first search
	public void dfs(char startLabel, char targetLabel) {
		if (!isVertexPresent(startLabel)) {
			System.out.println("Vertex " + startLabel + " not found");
			return;
		}
		if (!isVertexPresent(targetLabel)) {
			System.out.println("Vertex " + targetLabel + " not found");
			return;
		}
		// We have vertexes in array;
		HashMap<Integer, ArrayList<Vertex>> depthAndVertexes = new HashMap<>();
		Stack<Vertex> stack = new Stack<>();
		int startVertexIndex = getVertexIndexByLabel(startLabel);
		stack.add(vertArr[startVertexIndex]);
//		System.out.println(startLabel + " added to stack, depth: 0");
		Vertex foundedVertex = null;
		while (!stack.isEmpty()) {
			Vertex currentVertex = stack.pop();
//			System.out.println("Vertex in process: " + currentVertex.label);
			currentVertex.wasVisited = true;
//			printAdjacencyMatrix();
			if (currentVertex.depth == -1)
				currentVertex.depth = 0;
			if (depthAndVertexes.containsKey(currentVertex.depth)) {
				depthAndVertexes.get(currentVertex.depth).add(currentVertex);
			} else {
				ArrayList<Vertex> list = new ArrayList<>();
				list.add(currentVertex);
				depthAndVertexes.put(currentVertex.depth, list);
			}
			if (currentVertex.label == targetLabel) {
				System.out.println("Label found!");
				foundedVertex = currentVertex;
				break;
			}
			ArrayList<Vertex> adjVerextes = getAdjacentVertexesByLabel(currentVertex.label);
			for (Vertex vertex : adjVerextes) {
				if (vertex.wasVisited)
					continue;
				int nextDepth = currentVertex.depth + 1;
				if (vertex.depth != -1) {
					if (vertex.depth <= nextDepth)
						continue;
					depthAndVertexes.get(vertex.depth).remove(vertex);
					depthAndVertexes.get(nextDepth).add(vertex);
					
					System.out.println("SHORT WAY TO " + vertex.label + " FOUND " + vertex.depth +" "  + nextDepth);
					vertex.depth = nextDepth;
					continue;
				}
				vertex.depth = currentVertex.depth + 1;
				stack.push(vertex);
//				System.out.println(vertex.label + " added to stack, depth: " + vertex.depth);
			}

		}
		if (foundedVertex == null) {
			System.out.println("Target vertex doesn't available from start vertex");
			return;
		}
		Vertex pointer;
		pointer = foundedVertex;

		for (int i = foundedVertex.depth; i >= 0; i--) {
			System.out.print(pointer.label);
			if (pointer.depth != 0) {
				System.out.print("->");
			} else {
				break;
			}
			ArrayList<Vertex> vertexesByDepth = depthAndVertexes.get(pointer.depth - 1);
			for (Vertex vert : vertexesByDepth) {
				if (isVertexAdjacent(pointer.label, vert.label)) {
					pointer = vert;
					continue;
				}
			}
		}
		System.out.println();
		resetVertexes();
	}

	private boolean isVertexAdjacent(char labelFrom, char labelTo) {
		int fromIndex = getVertexIndexByLabel(labelFrom);
		int toIndex = getVertexIndexByLabel(labelTo);
		return adjacencyMatrix[fromIndex][toIndex];
	}

	private void resetVertexes() {
		for (int i = 0; i < vertArr.length; i++) {
			vertArr[i].depth = -1;
			vertArr[i].wasVisited = false;
		}
	}

	public void printAdjacencyMatrix() {
		char startChar = 'A';
		for (int i = 0; i < adjacencyMatrix.length; i++) {
			for (int j = 0; j < adjacencyMatrix.length; j++) {
				if (i == 0 && j == 0) {
					System.out.print("  ");
					for (int x = 0; x < adjacencyMatrix.length; x++) {
						System.out.print((char) (startChar + x) + " ");
					}
					System.out.println();
				}
				if (j == 0) {
					System.out.print((char) (startChar + i) + " ");
				}
				if (adjacencyMatrix[i][j])
					if (vertArr[i].wasVisited && vertArr[j].wasVisited) {
						System.out.print(ANSI_GREEN_BACKGROUND + "1 " + ANSI_RESET);
					} else {
						System.out.print("1 ");
					}
				else
					System.out.print("0 ");
				if (j == adjacencyMatrix.length - 1)
					System.out.println();
			}
		}
	}

	private boolean[][] generateAdjacencyMaxtrix(int max, double fillingRate) throws Exception {
		if (fillingRate < 0 || fillingRate > 1)
			throw new Exception("Filling rate should be between 0 and 1");
		boolean[][] adjacencyMatrix = new boolean[max][max];
		int rateToNum = (int) (fillingRate * max);
		for (int i = 0; i < adjacencyMatrix.length; i++) {
			for (int j = i; j < adjacencyMatrix.length; j++) {
				if (i == j || adjacencyMatrix[i][j])
					continue;
				double z = ThreadLocalRandom.current().nextDouble(0, max);

				if (z <= rateToNum) {
					adjacencyMatrix[i][j] = true;
					adjacencyMatrix[j][i] = true;
				}
			}
		}
		return adjacencyMatrix;
	}

	private class Vertex {
		private char label;
		private boolean wasVisited;
		private int depth;

		public Vertex(char label) {
			super();
			depth = -1;
			this.label = label;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Objects.hash(label);
			result = prime * result + Objects.hash(label);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Vertex other = (Vertex) obj;
			return label == other.label;
		}
		
	}
}
