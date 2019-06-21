package org.study.threads;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ExpanseWong
 * 某个工厂打造两条生产线，生产咖啡豆，供应给咖啡，生产土豆，供应给土豆泥
 * 输出样例：咖啡豆--咖啡
 * 土豆--土豆泥
 */
@SuppressWarnings("unchecked")
public class ProductorConumerTest {
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition full = lock.newCondition();
    private static Condition empty = lock.newCondition();

    public static void main(String[] args) {
        LinkedList linkedList = new LinkedList();
        ExecutorService service = Executors.newFixedThreadPool(15);
        service.submit(new Productor(linkedList,lock));
        service.submit(new Consumer(linkedList,lock));
    }

    static class Productor implements Runnable {
        private LinkedList<String> list;
        private Lock lock;

        public Productor(LinkedList list, Lock lock) {
            this.list = list;
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                lock.lock();
                for (int i = 0; i < 50; i++) {
                    if (!list.isEmpty()){
                        full.await();
                    }
                    if (i%2==1){
                       list.add("咖啡豆--咖啡");
                    }else {
                        list.add("土豆--土豆泥");
                    }
                    empty.signal();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    static class Consumer implements Runnable {
        private Lock lock;
        private LinkedList<String> list;

        public Consumer(LinkedList list, Lock lock) {
            this.list = list;
            this.lock = lock;
        }

        @Override
        public void run() {

            try {
                lock.lock();
                for (int i = 0; i < 50; i++) {
                    if (list.isEmpty()){
                        empty.await();
                    }
                    String element = list.remove(0);
                    System.out.println(element);
                    full.signal();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
