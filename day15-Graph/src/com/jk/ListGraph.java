package com.jk;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class ListGraph<V, E> extends Graph<V, E> {
	private Map<V, Vertex<V, E>> vertices = new HashMap<>();
	private Set<Edge<V, E>> edges = new HashSet<>();
	private Comparator<Edge<V, E>> edgeComparator = (Edge<V, E> e1, Edge<V, E> e2) -> {
		return weightManager.compare(e1.weight, e2.weight);
	};
	
	public ListGraph() {}
	
	public ListGraph(WeightManager<E> weightManager) {
		super(weightManager);
	}

	@Override
	public int edgesSize() {
		return edges.size();
	}

	@Override
	public int verticesSize() {
		return vertices.size();
	}

	@Override
	public void addVertex(V v) {
		if (vertices.containsKey(v)) return;
		vertices.put(v, new Vertex<>(v));
	}

	@Override
	public void addEdge(V from, V to) {
		addEdge(from, to, null);
	}

	@Override
	public void addEdge(V from, V to, E weight) {
		Vertex<V, E> fromVertex = vertices.get(from);
		if (fromVertex == null) {
			fromVertex = new Vertex<>(from);
			vertices.put(from, fromVertex);
		}
		
		Vertex<V, E> toVertex = vertices.get(to);
		if (toVertex == null) {
			toVertex = new Vertex<>(to);
			vertices.put(to, toVertex);
		}
		
		Edge<V, E> edge = new Edge<>(fromVertex, toVertex);
		edge.weight = weight;
		if (fromVertex.outEdges.remove(edge)) {
			toVertex.inEdges.remove(edge);
			edges.remove(edge);
		}
		fromVertex.outEdges.add(edge);
		toVertex.inEdges.add(edge);
		edges.add(edge);
	}

	@Override
	public void removeVertex(V v) {
		Vertex<V, E> vertex = vertices.remove(v);
		if (vertex == null) return;
		
		for (Iterator<Edge<V, E>> iterator = vertex.outEdges.iterator(); iterator.hasNext();) {
			Edge<V, E> edge = iterator.next();
			edge.to.inEdges.remove(edge);
			edges.remove(edge);
			iterator.remove();
		}
		
		for (Iterator<Edge<V, E>> iterator = vertex.inEdges.iterator(); iterator.hasNext();) {
			Edge<V, E> edge = iterator.next();
			edge.from.outEdges.remove(edge);
			edges.remove(edge);
			iterator.remove();
		}
	}

	@Override
	public void removeEdge(V from, V to) {
		Vertex<V, E> fromVertex = vertices.get(from);
		if (fromVertex == null) return;
		
		Vertex<V, E> toVertex = vertices.get(to);
		if (toVertex == null) return;
		
		Edge<V, E> edge = new Edge<>(fromVertex, toVertex);
		if (fromVertex.outEdges.remove(edge)) {
			toVertex.inEdges.remove(edge);
			edges.remove(edge);
		}
	}

	@Override
	public void bfs(V begin, VertexVisitor<V> visitor) {
		if (visitor == null) return;
		Vertex<V, E> begainVertex = vertices.get(begin);
		if (begainVertex == null) return;
		
		Set<Vertex<V, E>> visitedVertex = new HashSet<>();
		Queue<Vertex<V, E>> queue = new LinkedList<>();
		queue.offer(begainVertex);
		visitedVertex.add(begainVertex);
		
		while (!queue.isEmpty()) {
			Vertex<V, E> vertex = queue.poll();
			if (visitor.visit(vertex.value)) return;
			
			for (Edge<V, E> edge: vertex.outEdges) {
				if (visitedVertex.contains(edge.to)) continue;
				queue.offer(edge.to);
				visitedVertex.add(edge.to);
			}
		}
	}

	@Override
	public void dfs(V begin, VertexVisitor<V> visitor) {
		if (visitor == null) return;
		Vertex<V, E> begainVertex = vertices.get(begin);
		if (begainVertex == null) return;
		
		Set<Vertex<V, E>> visitedVertex = new HashSet<>();
		Stack<Vertex<V, E>> stack = new Stack<>();
		stack.push(begainVertex);
		visitedVertex.add(begainVertex);
		if (visitor.visit(begin)) return;
		
		while (!stack.isEmpty()) {
			Vertex<V, E> vertex = stack.pop();
			for (Edge<V, E> edge: vertex.outEdges) {
				if (visitedVertex.contains(edge.to)) continue;
				
				stack.push(edge.from);
				stack.push(edge.to);
				visitedVertex.add(edge.to);
				if (visitor.visit(edge.to.value)) return;
				
				break;
			}
		}
	}

	@Override
	public Set<EdgeInfo<V, E>> mst() {
		return null;
	}

	@SuppressWarnings("unused")
	private Set<EdgeInfo<V, E>> prim() {
		Iterator<Vertex<V, E>> iterator = vertices.values().iterator();
		if (!iterator.hasNext()) return null;
		Vertex<V, E> vertex = iterator.next();
		Set<EdgeInfo<V, E>> infos = new HashSet<>();
		Set<Vertex<V, E>> addedVertex = new HashSet<>();
		addedVertex.add(vertex);
		MinHeap<Edge<V, E>> heap = new MinHeap<>(vertex.outEdges, edgeComparator);
		int size = vertices.size();
		while (!heap.isEmpty() && vertices.size() < size) {
			Edge<V, E> edge = heap.remove();
			if (addedVertex.contains(edge.to)) continue;
			infos.add(edge.info());
			addedVertex.add(vertex);
			heap.addAll(edge.to.outEdges);
		}
		return infos;
	}
	
	private Set<EdgeInfo<V, E>> kruskal() {
		int edgeSize = vertices.size() - 1;
		if (edgeSize == -1) return null;
		Set<EdgeInfo<V, E>> infos = new HashSet<>();
		MinHeap<Edge<V, E>> heap = new MinHeap<>(edges, edgeComparator);
		UnionFind<Vertex<V, E>> unionFind = new UnionFind<>();
		vertices.forEach((V v, Vertex<V, E> vertex) -> {
			unionFind.makeSet(vertex);
		});
		while (!heap.isEmpty() && infos.size() < edgeSize) {
			Edge<V, E> edge = heap.remove();
			if (unionFind.isSame(edge.from, edge.to)) continue;
			infos.add(edge.info());
			unionFind.union(edge.from, edge.to);
		}
		return infos;
	}
	
	@Override
	public List<V> topologicalSort() {
		List<V> list = new ArrayList<>();
		Queue<Vertex<V, E>> queue = new LinkedList<>();
		Map<Vertex<V, E>, Integer> inEdgesCount = new HashMap<>();
		
		vertices.forEach((V v, Vertex<V, E> vertex) -> {
			int inEdgs = vertex.inEdges.size();
			if (inEdgs == 0) {
				queue.offer(vertex);
			} else {
				inEdgesCount.put(vertex,inEdgs);
			}
		});
		
		while (!queue.isEmpty()) {
			Vertex<V, E> vertex = queue.poll();
			list.add(vertex.value);
			for (Edge<V, E> outEdgs: vertex.outEdges) {
				int inEdgs = outEdgs.to.inEdges.size() - 1;
				if (inEdgs == 0) {
					queue.offer(outEdgs.to);
				} else {
					inEdgesCount.put(outEdgs.to, inEdgs);
				}
			}
		}
		
		return list;
	}

	@Override
	public Map<V, Map<V, PathInfo<V, E>>> shortestPath() {
		Map<V, Map<V, PathInfo<V, E>>> paths = new HashMap<>();
		for (Edge<V, E> edge : edges) {
			Map<V, PathInfo<V, E>> map = paths.get(edge.from.value);
			if (map == null) {
				map = new HashMap<>();
				paths.put(edge.from.value, map);
			}
			
			PathInfo<V, E> pathInfo = new PathInfo<>(edge.weight);
			pathInfo.edgeInfos.add(edge.info());
			map.put(edge.to.value, pathInfo);
		}

		vertices.forEach((V v2, Vertex<V, E> vertex2) -> {
			vertices.forEach((V v1, Vertex<V, E> vertex1) -> {
				vertices.forEach((V v3, Vertex<V, E> vertex3) -> {
					if (v1.equals(v2) || v2.equals(v3) || v1.equals(v3)) return;
				
					PathInfo<V, E> path12 = getPathInfo(v1, v2, paths);
					if (path12 == null) return;
					
					PathInfo<V, E> path23 = getPathInfo(v2, v3, paths);
					if (path23 == null) return;
					
					PathInfo<V, E> path13 = getPathInfo(v1, v3, paths);
					
					E newWeight = weightManager.add(path12.weight, path23.weight);
					if (path13 != null && weightManager.compare(newWeight, path13.weight) >= 0) return;
					
					if (path13 == null) {
						path13 = new PathInfo<V, E>();
						paths.get(v1).put(v3, path13);
					} else {
						path13.edgeInfos.clear();
					}
					
					path13.weight = newWeight;
					path13.edgeInfos.addAll(path12.edgeInfos);
					path13.edgeInfos.addAll(path23.edgeInfos);
				});
			});
		});
		
		return paths;
	}
	
	private PathInfo<V, E> getPathInfo(V from, V to, Map<V, Map<V, PathInfo<V, E>>> paths) {
		Map<V, PathInfo<V, E>> map = paths.get(from);
		return map == null ? null : map.get(to);
	}
	
	@SuppressWarnings("unused")
	private Map<V, PathInfo<V, E>> dijkstra(V begin) {
		Vertex<V, E> begainVertex = vertices.get(begin);
		if (begainVertex == null) return null;
		
		Map<V, PathInfo<V, E>> selectedPaths = new HashMap<>();
		Map<Vertex<V, E>, PathInfo<V, E>> paths = new HashMap<>();
		paths.put(begainVertex, new PathInfo<>(weightManager.zero()));
		
		while (!paths.isEmpty()) {
			Entry<Vertex<V, E>, PathInfo<V, E>> min = minumPath(null);
			Vertex<V, E> minVertex = min.getKey();
			PathInfo<V, E> minPathInfo = min.getValue();
			selectedPaths.put(minVertex.value, minPathInfo);
			paths.remove(minVertex);
			for (Edge<V, E> edge: minVertex.outEdges) {
				if (selectedPaths.containsKey(edge.to.value)) continue;
				relaxForDijkstra(edge, minPathInfo, paths);
			}
		}
		selectedPaths.remove(begainVertex);
		return selectedPaths;
	}
	
	private void relaxForDijkstra(Edge<V, E> edge, PathInfo<V, E> fromPath, Map<Vertex<V, E>, PathInfo<V, E>> paths) {
		E newWeight = weightManager.add(fromPath.weight, edge.weight);
		PathInfo<V, E> oldPath = paths.get(edge.to);
		if (oldPath != null && weightManager.compare(newWeight, oldPath.weight) >= 0) return;
		if (oldPath == null) {
			oldPath = new PathInfo<>();
			paths.put(edge.to, oldPath);
		} else {
			oldPath.edgeInfos.clear();
		}
		oldPath.weight = newWeight;
		oldPath.edgeInfos.addAll(fromPath.edgeInfos);
		oldPath.edgeInfos.add(edge.info());
	}
	
	private Map<V, PathInfo<V, E>> bellmanFord(V begin) {
		Vertex<V, E> begainVertex = vertices.get(begin);
		if (begainVertex == null) return null;
		
		Map<V, PathInfo<V, E>> selectedPaths = new HashMap<>();
		selectedPaths.put(begin, new PathInfo<>(weightManager.zero()));
		int count = vertices.size() - 1;
		for (int i = 0; i < count; i++) {
			for (Edge<V, E> edg: edges) {
				PathInfo<V, E> fromPath = selectedPaths.get(edg.from.value);
				if (fromPath == null) continue;
				relaxForBellmanFord(edg, fromPath, selectedPaths);
			}
		}
		selectedPaths.remove(begin);
		return selectedPaths;
	}
	
	private void relaxForBellmanFord(Edge<V, E> edge, PathInfo<V, E> fromPath, Map<V, PathInfo<V, E>> paths) {
		E newWeight = weightManager.add(fromPath.weight, edge.weight);
		PathInfo<V, E> oldPath = paths.get(edge.to);
		if (oldPath != null && weightManager.compare(newWeight, oldPath.weight) >= 0) return;
		if (oldPath == null) {
			oldPath = new PathInfo<>();
			paths.put(edge.to.value, oldPath);
		} else {
			oldPath.edgeInfos.clear();
		}
		oldPath.weight = newWeight;
		oldPath.edgeInfos.addAll(fromPath.edgeInfos);
		oldPath.edgeInfos.add(edge.info());
	}
	
	private Entry<Vertex<V, E>, PathInfo<V, E>> minumPath(Map<Vertex<V, E>, PathInfo<V, E>> paths) {
		Iterator<Entry<Vertex<V, E>, PathInfo<V, E>>> iteator = paths.entrySet().iterator();
		Entry<Vertex<V, E>, PathInfo<V, E>> min = iteator.next();
		while (iteator.hasNext()) {
			Entry<Vertex<V, E>, PathInfo<V, E>> current = iteator.next();
			if (weightManager.compare(min.getValue().weight, current.getValue().weight) < 0) {
				min = current;
			}
		}
		return min;
	}
	
	private static class Vertex<V, E> {
		V value;
		Set<Edge<V, E>> inEdges = new HashSet<>();
		Set<Edge<V, E>> outEdges = new HashSet<>();
		Vertex(V value) {
			this.value = value;
		}
		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object obj) {
			return Objects.equals(value, ((Vertex<V, E>)obj).value);
		}
		@Override
		public int hashCode() {
			return value == null ? 0 : value.hashCode();
		}
		@Override
		public String toString() {
			return value == null ? "null" : value.toString();
		}
	}
	
	private static class Edge<V, E> {
		Vertex<V, E> from;
		Vertex<V, E> to;
		E weight;
		
		Edge(Vertex<V, E> from, Vertex<V, E> to) {
			this.from = from;
			this.to = to;
		}
		
		EdgeInfo<V, E> info() {
			return new EdgeInfo<>(from.value, to.value, weight);
		}
		
		@Override
		public boolean equals(Object obj) {
			@SuppressWarnings("unchecked")
			Edge<V, E> edge = (Edge<V, E>) obj;
			return Objects.equals(from, edge.from) && Objects.equals(to, edge.to);
		}
		@Override
		public int hashCode() {
			return from.hashCode() * 31 + to.hashCode();
		}

		@Override
		public String toString() {
			return "Edge [from=" + from + ", to=" + to + ", weight=" + weight + "]";
		}
	}

	@Override
	public Map<V, PathInfo<V, E>> shortestPath(V begin) {
		// TODO Auto-generated method stub
		return null;
	}
}
