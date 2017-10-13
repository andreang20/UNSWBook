package dao;

public class GraphEdge {
    private static final int UNINIT = -1;

    private int edge_id;
    private int from;
    private int to;
    private String relationship;

    public int getEdge_id() {
        return edge_id;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public String getRelationship() {
        return relationship;
    }

    public GraphEdge(int edge_id, int from, int to, String relationship) {
        this.edge_id = edge_id;
        this.from = from;
        this.to = to;
        this.relationship = relationship;
    }

    public GraphEdge(int from, int to, String relationship) {
        this(UNINIT, from, to, relationship);
    }

    @Override
    public boolean equals(Object obj) {
        GraphEdge e = (GraphEdge) obj;
        if (e.getEdge_id() != edge_id)
            return false;

        if (e.getFrom() != from)
            return false;

        if (e.getTo() != to)
            return false;

        if (e.getRelationship() == null && relationship == null)
            return true;

        if (e.getRelationship().equals(relationship))
            return true;

        return false;
    }
}
