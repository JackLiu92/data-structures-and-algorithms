package com.jk;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ListGraph<V, E> extends Graph<V, E> {

	@Override
	public int edgeSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int edgesSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int verticesSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addVertex(V v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addEdge(V from, V to) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addEdge(V from, V to, E weight) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeVertex(V v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeEdge(V from, V to) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void bfs(V begin, VertexVisitor<V> visitor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dfs(V begin, VertexVisitor<V> visitor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<EdgeInfo<V, E>> mst() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<V> topologicalSort() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<V, PathInfo<V, E>> shortestPath(V begin) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<V, Map<V, PathInfo<V, E>>> shortestPath() {
		// TODO Auto-generated method stub
		return null;
	}

}
