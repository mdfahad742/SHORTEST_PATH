import java.util.*;

public class DijkstraSP {
    public DirectedEdge[] edgeTo;
    public double[] distTo;
    public PriorityQueue<Node> pq;

    class Node implements Comparable<Node> {
        int v;
        double cost;

        public Node(int v, double cost) {
            this.v = v;
            this.cost = cost;
        }

        @Override
        public boolean equals(Object o) {
            Node node = (Node) o;
            return this.v == node.v;
        }


        @Override
        public int compareTo(Node that) {
            if (this.cost > that.cost)
                return -1;
            else if (this.cost < that.cost)
                return 1;
            else
                return 0;
        }
    }

    public DijkstraSP(EdgeWeightedDigraph G, int s) {
        edgeTo = new DirectedEdge[G.V];
        distTo = new double[G.V];
        pq = new PriorityQueue<Node>();
        for (int v = 0; v < G.V; v++) {
            distTo[v] = Double.MAX_VALUE;
        }
        distTo[s] = 0.0;
        pq.add(new Node(s, 0.0));

        while (!pq.isEmpty()) {
            int v = pq.poll().v;
            for (DirectedEdge e : G.adj(v)) {
                relax(e);
            }
        }
    }

    private void relax(DirectedEdge e) {
        int v = e.from(), w = e.to();
        if (distTo[w] > distTo[v] + e.weight) {
            distTo[w] = distTo[v] + e.weight;
            edgeTo[w] = e;
            if (pq.contains(new Node(w, distTo[w]))) {
                pq.remove(new Node(w, distTo[w]));
                pq.add(new Node(w, distTo[w]));
            }
            else pq.add(new Node(w, distTo[w]));
        }
    }

    public double distTo(int v) {
        return distTo[v];
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        ArrayList<DirectedEdge> path = new ArrayList<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()])
            path.add(e);
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {

//        8
//        16
//        0 1 5.0
//        0 4 9.0
//        0 7 8.0
//        1 2 12.0
//        1 3 15.0
//        1 7 4.0
//        2 3 3.0
//        2 6 11.0
//        3 6 9.0
//        4 5 4.0
//        4 6 20.0
//        4 7 5.0
//        5 2 1.0
//        5 6 13.0
//        7 5 6.0
//        7 2 7.0
//        0

        Scanner sc = new Scanner(System.in);
        int V = sc.nextInt();
        int E = sc.nextInt();
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(V);
        for (int i = 0; i < E; i++) {
            int v = sc.nextInt();
            int w = sc.nextInt();
            double weight = sc.nextDouble();
            DirectedEdge e = new DirectedEdge(v, w, weight);
            G.addEdge(e);
        }
        int s = sc.nextInt();
        DijkstraSP sp = new DijkstraSP(G, s);
        for (int v = 0; v < G.V; v++) {
            System.out.print(s + " to " + v + " (" + sp.distTo(v) + "): ");
            for (DirectedEdge e : sp.pathTo(v))
                System.out.print(e + " ");
            System.out.println();
        }
    }
}

