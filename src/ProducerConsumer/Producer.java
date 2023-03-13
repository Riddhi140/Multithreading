package ProducerConsumer;

import java.util.Random;

public class Producer implements Runnable {
    private final DataQueue dataQueue;
    private volatile boolean runFlag;

    public Producer(DataQueue dataQueue) {
        this.dataQueue = dataQueue;
        runFlag = true;
    }

    @Override
    public void run() {
        produce();
    }

    // Other methods

    public void produce() {
        while (runFlag) {
            Message message = generateMessage();
            while (dataQueue.isFull()) {
                try {
                    dataQueue.waitOnFull();
                } catch (InterruptedException e) {
                    break;
                }
            }
            if (!runFlag) {
                break;
            }
            dataQueue.add(message);
            dataQueue.notifyAllForEmpty();
        }
    }

    private Message generateMessage() {
        return new Message(new Random().nextInt(100), new Random().nextDouble());
    }

    public void add(Message message) {
        synchronized (dataQueue) {
            dataQueue.add(message);
        }
    }

    public void stop() {
        runFlag = false;
        dataQueue.notifyAllForFull();
    }
}
