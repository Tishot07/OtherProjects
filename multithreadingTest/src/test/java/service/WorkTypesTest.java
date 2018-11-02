package service;

import org.junit.Test;
import types.TypeA;
import types.TypeB;

import static org.junit.Assert.assertEquals;


public class WorkTypesTest {

    @Test
    public void runTypeA() {
        WorkTypes workTypes = new WorkTypes(new TypeA(1, 10, 200));
        Thread thread = new Thread(workTypes);
        thread.start();
        assertEquals(thread.getState(), Thread.State.RUNNABLE);
    }

    @Test
    public void runTypeB() {
        WorkTypes workTypes = new WorkTypes(new TypeB(2, 20, 200));
        Thread thread = new Thread(workTypes);
        thread.start();
        assertEquals(thread.getState(), Thread.State.RUNNABLE);

    }

    @Test
    public void runTimeWait() {
        WorkTypes workTypes = new WorkTypes(new TypeB(3, 20, 2000));
        Thread thread = new Thread(workTypes);
        thread.start();
        try {
            thread.join();
            assertEquals(thread.getState(), Thread.State.TERMINATED);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void runExpected() {
        WorkTypes workTypes = new WorkTypes(new TypeA(4, 80, 2000));
        Thread thread = new Thread(workTypes);
        thread.start();
        thread.interrupt();

        /*
        Throwable thrown = assertThrows(InterruptedException.class, () -> {
            thread.interrupt();
        });
        assertNotNull(thrown.getMessage());
        */
    }
}