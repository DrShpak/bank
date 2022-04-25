package simulation.client;

import java.util.List;
import java.util.Random;

public class Client {
    private OpType opType;
    private int money;
    private List<OpType> opTypes = List.of(OpType.PUT, OpType.DRAWOUT);

    // time in seconds
    private int serviceTime;

    public Client() {
        this.opType = opTypes.get(new Random().nextInt(2));
        this.money = new Random().nextInt(3000) + 1;
        this.serviceTime = new Random().nextInt(4000) + 1000;
    }

    public OpType getOpType() {
        return opType;
    }

    public int getMoney() {
        return money;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setMoney(int money) {
        this.money += money;
    }
}
