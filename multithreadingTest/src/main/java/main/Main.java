package main;

import service.ServiceTypes;
import types.TypeA;
import types.TypeB;
import types.Types;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Random rnd = new Random(System.currentTimeMillis());

        List<Types> types = new ArrayList<Types>();

        for (int i = 0; i < 20; i++) {
            int type = 0 + rnd.nextInt(5 + 1);
            long time = 50 + rnd.nextInt(200 - 50 + 1);

            int j = i;
            if (j%2 == 0) {
                types.add(new TypeA(i,type, time));
            } else {
                types.add(new TypeB(i, type, time));
            }
        }

        ServiceTypes service = new ServiceTypes();
        Thread thread = new Thread(service);
        thread.start();

        for (Types t:
             types) {
            service.addTypes(t);
            System.out.println("start " + t);
        }
    }
}
