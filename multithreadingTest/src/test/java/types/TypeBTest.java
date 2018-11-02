package types;

import org.junit.Test;

import static org.junit.Assert.*;

public class TypeBTest {

    @Test
    public void getTime() {
        TypeB b = new TypeB(1, 10, 200);
        assertEquals(b.getTime(), 200);
    }

    @Test
    public void getType() {
        TypeB b = new TypeB(1, 20, 200);
        assertEquals(b.getType(), 20);
    }
}