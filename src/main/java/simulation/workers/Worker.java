package simulation.workers;

import simulation.bank.Bank;
import simulation.client.Client;
import simulation.client.OpType;

import java.util.*;

public class Worker implements Runnable {
    private final String name;
    private final Queue<Client> clients;

    public Worker(String name) {
        this.name= name;
        this.clients = new LinkedList<>();
    }

    @Override
    public void run() {


        while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            Thread.yield();

            if (clients.size() > 0) {
                var client = clients.remove();
                Bank.lock();
                if (client.getOpType().equals(OpType.PUT)) {
                    Bank.setCash(client.getMoney(), 1);
                    System.out.println("Client put " + client.getMoney());
                    System.out.println("Bank cash " + Bank.getCash());
                    System.out.println("clients = " + clients.size());
                    System.out.println("\n");
                } else {
                    if (Bank.getCash() >= client.getMoney()) {
                        System.out.println("Client has drawn out " + client.getMoney());
                        client.setMoney(client.getMoney());
                        Bank.setCash(client.getMoney(), 0);
                        System.out.println("Bank cash = " + Bank.getCash());
                        System.out.println("\n");
                    } else {
                        System.out.println("Client denied service. ");
                        System.out.println("Bank cash = " + Bank.getCash());
                        System.out.println("Client wants draw out " + client.getMoney());
                        System.out.println("Client has removed.\n\n");
                    }
                }

                Bank.unlock();
            }
        }
    }

    public String getName() {
        return name;
    }

    public Queue<Client> getClients() {
        return clients;
    }
}