package types;

public class TypeB implements Types {

    private int index;
    private int type;
    private long time;

    public TypeB(int i, int type, long time) {
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
        return "typeB{" +
                "index=" + index +
                ", type=" + type +
                '}';
    }
}
