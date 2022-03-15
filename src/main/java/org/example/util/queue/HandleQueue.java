package org.example.util.queue;

public class HandleQueue<T> {
    //队列
    private final Item<T>[] queue;
    //锁
    private final Object lock = new Object();
    //存放的指针
    private int putIndex = 0;
    //读取的指针
    private int sendIndex;
    //队列是否满了
    private boolean full = false;
    //队列是否空了
    private boolean queueNull = true;
    //是否处于等待
    private boolean sendWait = false;
    private boolean putWait = false;

    public HandleQueue(int size){
        queue = new Item[size];
        sendIndex = queue.length - 1;
    }
    /**
     * 获取
     *
     * @return
     */
    public T get() throws InterruptedException {
        synchronized (lock) {
            moveSend();
            Item<T> cur;
            while((cur = queue[sendIndex]) == null){
                sendWait = true;
                lock.wait();
            }
            queue[sendIndex] = null;
            full = false;
            //通知可以继续放置了
            if(putWait) {
                putWait = false;
                lock.notify();
            }
            //队列空了
            int next = sendIndex == queue.length - 1 ? 0 : sendIndex + 1;
            if(queue[next] == null){
                queueNull = true;
            }

            return cur.t;
        }
    }

    /**
     * 放置
     *
     * @param fileTmp
     * @throws InterruptedException
     */
    public void put(T fileTmp) throws InterruptedException {
        synchronized (lock) {
            if (fileTmp == null) {
                return;
            }
            Item<T> item = new Item<>();
            item.t = fileTmp;
            queue[putIndex] = item;
            movePut();
            full = queue[putIndex] != null;
            //通知可以继续发送
            if(sendWait) {
                sendWait = false;
                lock.notify();
            }
            //队列不为空
            if(queueNull){
                queueNull = false;
            }
            if (full) {
                //满了，等待
                putWait = true;
                lock.wait();
            }
        }
    }

    /**
     * 放置的指针移动
     */
    private void movePut() {
        if (putIndex == queue.length - 1) {
            putIndex = 0;
        } else {
            putIndex++;
        }
    }

    /**
     * 获取的指针移动
     */
    private void moveSend() {
        if (sendIndex == queue.length - 1) {
            sendIndex = 0;
        } else {
            sendIndex ++;
        }
    }

    public boolean isEmpty(){
        synchronized (lock){
            return queueNull;
        }
    }

    private static class Item<T>{
        T t;
    }
}
