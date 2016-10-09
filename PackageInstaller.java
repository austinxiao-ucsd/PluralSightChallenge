/**
 * The PackageInstaller program implements an 
 * topological sort approach to a coding challenge 
 * that lists the order of dependencies. 
 * 
 * @author hongdaxiao
 * @version 1.0
 * @since 2016-10-09
 */
import java.util.*;
public class PackageInstaller {
	// adjacency list representation
	private List<String> [] graph;
	// resulting path
	private List<String> res;
	// each string hash to an index
	private Map<String, Integer> packageMap;
	// track visited neighbors
	private Map<String, Integer> visited;
	// result in string format
	public String [] result;
	
	/* topological sort and check cycles
	 * @param name Current string to perform depth-first search. 
	 * @return boolean Check whether a cycle exists.
	 */
	private boolean dfs(String name) {
		// check if visited before
		if(visited.containsKey(name) && visited.get(name) == 1){
			return true;
		}
		// if still on stack
		if(visited.containsKey(name) && visited.get(name) == -1){
			return false;
		}
		visited.put(name, -1);
		// run dfs on neighbors
		int idx = packageMap.get(name);
		for(int i = 0; i < graph[idx].size(); i++){
			if(!dfs(graph[idx].get(i))){
				return false;
			}
		}
		res.add(name);
		visited.put(name, 1);
		return true;
	}
	/*
	 * This program parses the input, builds the graph,
	 * and performs the actual sorting and listing step.
	 * 
	 * @param input The input string that is yet to be parsed.
	 * @exception IllegalArgumentException Check for invalid inputs.
	 */
	public void installPackage(String [] input) {
		res = new ArrayList<String>();
		graph = new List[input.length * 2];
		packageMap = new HashMap<String, Integer>();
		visited = new HashMap<String, Integer>();
		int idx = 0;	// track current index
		for(int i = 0; i < graph.length; i++){
			graph[i] = new ArrayList<String>();
		}
		// construct graph
		for(String in: input){
			String [] curr = in.split(": ");
			if(curr.length > 2){
				throw new IllegalArgumentException();
			}
			// no dependency
			if(curr.length == 1){
				packageMap.put(curr[0], idx);
				idx ++;
				continue;
			}
			// add pair to graph
			if(!packageMap.containsKey(curr[0])){
				packageMap.put(curr[0], idx);
				idx ++;
			}
			if(!packageMap.containsKey(curr[1])){
				packageMap.put(curr[1], idx);
				idx ++;
			}
			graph[packageMap.get(curr[1])].add(curr[0]);
		}
		// run topological sort and check cycles
		for(String key: packageMap.keySet()){
			if(!dfs(key)){
				throw new IllegalArgumentException("Contains Cycle!");
			}
		}
		// result in string array format
		result = new String[packageMap.size()];
		for(int k = 0; k < res.size(); k++){
		    result[k] = res.get(res.size() - k - 1);
		}
		for(String curr: result){
			System.out.print(curr);
			if(!curr.equals(result[result.length-1]))
			System.out.print(",");
		}
		System.out.println("");
		return;
	}
	/*
	 * Main method that uses the two examples given in the
	 * coding challenge instructions.
	 * @param args Unused.
	 */
	public static void main(String [] args) {
		PackageInstaller installer = new PackageInstaller();
		// example 1
		System.out.println("Running example 1 with valid input...");
		String [] in1 = {"KittenService: ","Leetmeme: Cyberportal",
			"Cyberportal: Ice","CamelCaser: KittenService","Fraudstream: Leetmeme","Ice: "};
		installer.installPackage(in1);

		// example 2
		System.out.println("Running example 2 that contains a cycle... Should throw exception");
		String [] in2 = {"KittenService: ","Leetmeme: Cyberportal","Cyberportal: Ice","CamelCaser: KittenService","Fraudstream: ","Ice: Leetmeme"};
		installer.installPackage(in2);
	}
}