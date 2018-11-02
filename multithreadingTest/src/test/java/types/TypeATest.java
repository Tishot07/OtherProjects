package types;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TypeATest {

    @Test
    public void getTime() {
        TypeA a = new TypeA(1, 10, 200);
        assertEquals(a.getTime(), 200);
    }

    @Test
    public void getType() {
        TypeA a = new TypeA(1, 20, 200);
        assertEquals(a.getType(), 20);
    }


}