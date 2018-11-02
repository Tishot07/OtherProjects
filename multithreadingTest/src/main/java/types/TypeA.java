package types;

public class TypeA implements Types {

    private int index;
    private int type;
    private long time;

    public TypeA(int i, int type, long time) {
        this.index = i;
        this.type = type;
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return "typeA{" +
                "index=" + index +
                ", type=" + type +
                '}';
    }
}
