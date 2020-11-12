package sumDistances;

import java.util.HashMap;
import java.util.LinkedList;

public class Node {
	LinkedList<Node> childs;
	Node parent;
	int id, numChilds, sum;
	Node(int id){
		this.id = id;
		this.childs = new LinkedList<>();
		this.numChilds = 0;
		this.sum = 0;
	}
}


public class SumDistances {
	
	public static void main(String[] args) {
		int[] res = sumDistances(new int[][] {{0,1},{0,2},{2,3},{2,4},{2,5}});
		for(int i: res) System.out.println(i);
	}
	
	// this function calculates the sum distance
	private static int[] sumDistances(int[][] edges) {
		Node root = new Node(edges[0][0]);
		HashMap<Integer,Node> map = new HashMap<>();
		map.put(edges[0][0], root);
		int numOfNodes = 1;
		for(int[] edge : edges) {
			if(!map.containsKey(edge[0])) {
				map.put(edge[0], new Node(edge[0]));
				numOfNodes++;
			}
			if(!map.containsKey(edge[1])) {
				map.put(edge[1], new Node(edge[1]));
				numOfNodes++;
			}
			Node n0 = map.get(edge[0]);
			Node n1 = map.get(edge[1]);
			n0.childs.add(n1);
			n1.parent = n0;
		}
		visitChilds(root);
		calculateSumDistances(root,numOfNodes);
		int[] res = new int[numOfNodes];
		for(int id : map.keySet()) res[id] = map.get(id).sum;
		return res;
	}
	
	private static void visitChilds(Node node) {
		for(Node child : node.childs) {
			visitChilds(child);
			node.sum += child.sum + child.numChilds+1;
			node.numChilds += child.numChilds+1;
		}
	}
	
	// this is comment from Tung
	private static void calculateSumDistances(Node node, int numOfNodes) {
		if(node.parent!= null) node.sum = node.parent.sum + numOfNodes - (1+node.numChilds)*2;
		for(Node child : node.childs) calculateSumDistances(child,numOfNodes);
	}
}
