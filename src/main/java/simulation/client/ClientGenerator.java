package simulation.client;

import simulation.workers.Worker;

import java.util.Comparator;
import java.util.List;

public class ClientGenerator implements  Runnable {
    private final int delay;
    private final List<Worker> workers;

    public ClientGenerator(int CLIENTS_PER_MINUTE, List<Worker> workers) {
        this.delay = 60 * 1000 / CLIENTS_PER_MINUTE;
        this.workers = workers;
        System.out.println("here");
    }

    @Override
    public void run() {
        Client client;
        Worker worker;
        while (true) {
            client = new Client();
            worker = workers.stream().min(Comparator.comparingInt(o -> o.getClients().size())).get();
            worker.getClients().add(client);

            System.out.println("Client has born.");
            System.out.println(worker.getName() + " has got client");
            System.out.println("client wants " + client.getOpType());
            System.out.println(worker.getName() + " has clients " + worker.getClients().size() + "\n");
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
