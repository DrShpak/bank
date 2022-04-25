package simulation.bank;

import simulation.client.ClientGenerator;
import simulation.workers.Worker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {
    private static int cash = 5000;
    private final int CLIENTS_PER_MINUTE = 15;

    // at least 2 workers
    private static final int COUNT_WORKERS = new Random().nextInt(1) + 2;
    private static final List<Worker> workers = new ArrayList<>();
    private static final ReentrantLock locker = new ReentrantLock();

    static {
        for (int i = 0; i < COUNT_WORKERS; i++) {
            workers.add(new Worker("worker " + i));
        }
    }


    public static synchronized int getCash() {
        return cash;
    }

    public static synchronized void setCash(int cash, int type) {
        if (type == 1)
            Bank.cash += cash;
        else
            Bank.cash -= cash;
    }


    public void runWork() {
        System.out.println("workers count " + workers.size());
        System.out.println("clients per minutes " + CLIENTS_PER_MINUTE);
        System.out.println("\n\n");
        ClientGenerator generator = new ClientGenerator(CLIENTS_PER_MINUTE, workers);
        new Thread(generator).start();
        List<Thread> threads = new ArrayList<>();
//        workers.forEach(worker ->threads.add(new Thread(worker)));
//        threads.forEach(Thread::start);
        workers.forEach(worker -> new Thread(worker).start());
        while (true) {
            // live until stop manually
//            try {
//                Thread.sleep(2000);
//                threads.forEach(x -> System.out.println("x is " + x.isAlive()));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }

    public static void lock() {
        locker.lock();
    }

    public static void unlock() {
        locker.unlock();
    }
}