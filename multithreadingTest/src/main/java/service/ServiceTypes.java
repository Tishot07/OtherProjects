package service;

import types.Types;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceTypes implements Runnable{

    private List<Types> list;
    private ExecutorService pool;
    private Queue<Types> queue;
    private ExecutorService poolForList;

    public ServiceTypes() {
        list = new ArrayList<Types>();
        pool = Executors.newFixedThreadPool(10);
        poolForList = Executors.newFixedThreadPool(10);
        queue = new ConcurrentLinkedQueue<Types>();
        System.out.println("run going");
    }

    public void addTypes(Types t) {
        boolean flag = false;
        if (list.isEmpty()) {
            pool.submit(new WorkTypes(t));
            list.add(t);
            System.out.println("Список пуст. Вы добавили первый объект " + t + " на исполнение");
        } else {
            for (Types temp:
                 list) {
                if (temp.getType() == t.getType()) {
                    flag = true;
                }
            }
            if (flag) {
                queue.add(t);
                System.out.println("Объект " + t + " с данным типов выполняется. Вы поставлены в очередь");
            } else {
                pool.submit(new WorkTypes(t));
                list.add(t);
                System.out.println("Объект " + t + " отправлен на выполнение");
            }
        }
    }

    public void run() {

        while (true) {

            final Iterator<Types> iter = list.iterator();
            while(iter.hasNext()) {
                final Types t = iter.next();
                poolForList.submit(new Runnable() {
                    public void run() {
                        try {
                            System.out.println("Объект " + t + " обрабатывается");
                            //t.wait();
                            Thread.sleep(t.getTime());
                            iter.remove();
                            System.out.println("Объект " + t + " обработался. Удаляем из списка");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            if (!queue.isEmpty()) {

                boolean flag = false;
                for (Types temp:
                        list) {
                    if (temp.getType() == queue.peek().getType()) {
                        flag = true;
                    }
                }
                if (!flag) {
                    Types temp = queue.poll();
                    pool.submit(new WorkTypes(temp));
                    list.add(temp);
                    System.out.println("Объект " + temp + " из очереди отправлен на выполнение");
                }
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
