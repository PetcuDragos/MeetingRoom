package ro.dragos.model;

public abstract class BaseModel<ID> {

    private final ID id;

    public BaseModel(ID id) {
        this.id = id;
    }

    public ID getId() {
        return id;
    }
}
