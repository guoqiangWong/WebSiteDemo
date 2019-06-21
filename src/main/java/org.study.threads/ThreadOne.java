package org.study.threads;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ExpanseWong
 */
public class ThreadOne {

    public static void main(String[] args) {
        final ThreadOne.DataPrint dataPrint = new ThreadOne.DataPrint();
        new Thread(() -> {
            while (dataPrint.letterFlag){
                dataPrint.printLetter();
            }
        }).start();

        new Thread(() -> {
            while(dataPrint.numFlag){
                dataPrint.printNum();
            }
        }).start();
    }

    static class DataPrint {
        /**
         * 线程结束标记
         */
        private boolean letterFlag = true;
        private boolean numFlag = true;
        /**
         * 数字的初始值
         */
        int num = 1;
        /**
         * 字母的初始值
         * 这里A~Z的字母对应的阿拉伯数字为65~90
         */
        int letter = 65;
        /**
         * 线程的等待标记
         */
        boolean flag = true;
        /**
         * java 线程并发库中的锁，相当于synchronize
         */
        Lock lock = new ReentrantLock();
        /**
         * 线程并发库中用于线程之间通讯的类，相当于wait(),notify();
         */
        Condition condLetter = lock.newCondition();
        Condition condNum = lock.newCondition();


        private void printLetter() {
            //如果打印到Z则结束
            if (letter > 90) {
                letterFlag = false;
                return;
            }
            //锁定代码块，锁定时其他线程不能访问其中的内容
            lock.lock();
            try {
                if (flag) {
                    condLetter.await();
                }
                System.out.print((char) letter);
                letter++;
                flag = true;
                condNum.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }


        private void printNum(){
            //如果打印到52则结束线程并停止
            if(num>=52){
                numFlag = false;
                return;
            }
            lock.lock();
            try {
                if (!flag){
                    condNum.await();
                }
                System.out.print(num);
                num++;
                System.out.print(num);
                num++;
                flag =false;
                condLetter.signal();

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }

    }
}
