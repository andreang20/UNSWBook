import dao.GraphEdge;
import dao.GraphEntity;

import java.util.ArrayList;

public class testEquals {
    public static void main(String[] args) {
        GraphEntity a = new GraphEntity(1, "HI", "MOO");
        GraphEntity b = new GraphEntity(1, "HI", "MOO");

        GraphEdge c = new GraphEdge(1, 2, 3, "potato");
        GraphEdge d = new GraphEdge(1, 2, 3, "potato");


        ArrayList<GraphEntity> list = new ArrayList<>();

        ArrayList<GraphEdge> list1 = new ArrayList<>();
        list1.add(c);

        list.add(a);

        System.out.println(list1.contains(d));
    }
}
