import java.util.*;

public class A3Q2 {

    // stack will get elemtns added when its finished, therefore when we pop the stack for
    // the second dfs we go in decreasing order from the last elements added
    static Stack<String> stack = new Stack<>();

    static Set<String> list = new HashSet<>();

    // track visited status of each node
    static Map<String, String> colorMap = new HashMap<>();


    public static String[] time_pass(String[][] itinerary, String[] cities) {
        String[] answer = new String[cities.length];

        // building adjacency list. to traverse easily and find SCCs
        Map<String, List<String>> adjList = buildAdjacencyList(itinerary);

        for(int i = 0; i< cities.length; i++){
            colorMap.clear();
            if(dfs3(adjList, cities[i])){
                answer[i] = "succeed";
            }
            else{answer[i] = "failed";
            }
        }
    return answer;
    }

    public static boolean dfs3(Map<String, List<String>> graph, String city){
            colorMap.put(city, "Grey");

            for (String neighbor : graph.get(city)){
                String neighborColor;
                if(colorMap.containsKey(neighbor)){
                    neighborColor = colorMap.get(neighbor);
                } else {
                    neighborColor = "White";
                }

                if (neighborColor.equals("Grey")){
                    return true;
                }
                if(neighborColor.equals("White")){
                    if(dfs3(graph, neighbor)){
                        return true;
                    }
                }
            }
            colorMap.put(city, "Black");
            return false;
    }

    public static void dfs0(Map<String, List<String>> graph, String city){
        // exploring neighbors
        colorMap.put(city, "Grey");
        // adding to stack
        stack.push(city);

        // iterating through neighbors
        for (String neighbor : graph.get(city)) {
            String neighborColor;
            // Back edge, add all current nodes on path
            if(colorMap.containsKey(neighbor)){
            neighborColor = colorMap.get(neighbor);
            } else {
                neighborColor = "White";
                }

            if (neighborColor.equals("White")) {
                dfs0(graph, neighbor);
            }
            // else explore the neighbor node's neighbors
            else if (neighborColor.equals("Grey")) {
                list.addAll(stack);
                list.add(neighbor);
            }
        }

        // done exploring path
        stack.pop();
        // done exploring
        colorMap.put(city, "Black");
    }

    // second search of graph (named dfs but its a bfs)
    // to check if a specific city can reach a node thats part of a cycle. if it can then its a sucess
    public static boolean dfs1(Map<String, List<String>> graph, String city){

        Queue<String> queue = new LinkedList<>();

        queue.add(city);
        
        while(!queue.isEmpty()){
            String c = queue.remove();
            // if poopped element is part of a cycle
            if(list.contains(c)){
                return true;
            }
            // else get neighbors and add to queue
            for(String dest : graph.get(c)){
                queue.add(dest);
            }
        }
        return false;
    }


    public static Map<String, List<String>> buildAdjacencyList (String[][] itinerary){
        Map<String, List<String>> adjList = new HashMap<>();

        for(String[] flight : itinerary){
            String src = flight[0];
            String dst = flight[1];

            if (!adjList.containsKey(src)) {
                adjList.put(src, new ArrayList<>());
            }
            if (!adjList.containsKey(dst)) {
                adjList.put(dst, new ArrayList<>());
            }

            adjList.get(src).add(dst);
        }
        return adjList;
    }
}
