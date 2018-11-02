package service;

import types.Types;

public class WorkTypes implements Runnable {

    private Types type;

    WorkTypes(Types t) {
        this.type = t;
    }

    public void run() {
        try {
            Thread.sleep(type.getTime());
            System.out.println("Current thread: " + Thread.currentThread() + ", объект " + type.getType() + " выполнился в submit");
            //type.notify();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
