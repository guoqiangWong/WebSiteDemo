package org.study.threads;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author ExpanseWong
 * 解决生产者消费者问题，通过blockqueue
 */
public class ProductorConsumerQueue {

    private static LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

    public static  void main(String[] args){
        ExecutorService service = Executors.newFixedThreadPool(15);
        for (int i = 0;i<5;i++){
            service.submit(new Productor(queue));
        }
        for (int i = 0;i<10;i++){
            service.submit(new Concusmer(queue));
        }
    }

    static class Productor implements Runnable{
        private BlockingQueue queue;

        public Productor(BlockingQueue queue){
            this.queue = queue;
        }
        @Override
        public void run() {
            try {
                while (true){
                    Random random = new Random();
                    int i = random.nextInt();
                    System.out.println("生产者" + Thread.currentThread().getName() + "生产数据" + i);
                    queue.add(i);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    static class Concusmer implements Runnable{
        private BlockingQueue queue;

        public Concusmer(BlockingQueue queue){
            this.queue = queue;
        }
        @Override
        public void run() {
            try {
                while(true){
                    Integer element = (Integer) queue.take();
                    System.out.println("消费者" + Thread.currentThread().getName() + "正在消费数据" + element);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
