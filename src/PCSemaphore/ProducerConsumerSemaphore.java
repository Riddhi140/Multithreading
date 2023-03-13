package PCSemaphore;
import java.util.concurrent.Semaphore;
public class ProducerConsumerSemaphore {
        private static final int BUFFER_SIZE = 10;
        private static Semaphore emptySlots = new Semaphore(BUFFER_SIZE);
        private static Semaphore fullSlots = new Semaphore(0);
        private static int[] buffer = new int[BUFFER_SIZE];
        private static int nextIn = 0;
        private static int nextOut = 0;

        public static void main(String[] args) {
            Thread producerThread = new Thread(() -> {
                try {
                    while (true) {
                        emptySlots.acquire(); // Wait for an empty slot
                        int item = produceItem();
                        buffer[nextIn] = item;
                        nextIn = (nextIn + 1) % BUFFER_SIZE;
                        System.out.println("Produced item: " + item);
                        fullSlots.release(); // Signal that a slot is filled
                        Thread.sleep(10); // Wait for some time before producing next item
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            Thread consumerThread = new Thread(() -> {
                try {
                    while (true) {
                        fullSlots.acquire(); // Wait for a filled slot
                        int item = buffer[nextOut];
                        nextOut = (nextOut + 1) % BUFFER_SIZE;
                        System.out.println("Consumed item: " + item);
                        emptySlots.release(); // Signal that a slot is empty
                        Thread.sleep(100); // Wait for some time before consuming next item
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            producerThread.start();
            consumerThread.start();
        }

        private static int produceItem() {
            return (int) (Math.random() * 100);
        }
    }


