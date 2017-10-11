package dao;

public class GraphEntity {
    private static final int UNINIT = -1;
    private int entityId;
    private String attribute;
    private String value;

    public GraphEntity(int entityId, String attribute, String value) {
        this.entityId = entityId;
        this.attribute = attribute;
        this.value = value;
    }

    public GraphEntity(String attribute, String value) {
        this(UNINIT, attribute, value);
    }

    public int getEntityId() {
        return entityId;
    }

    public String getAttribute() {
        return attribute;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        GraphEntity e = (GraphEntity) obj;
        if (entityId != e.getEntityId()) {
            return false;
        }

        if (attribute == null && value == null) {
            return true;
        }

        if(attribute.equals(e.getAttribute())) {
            return true;
        }

        return false;
    }
}
