package real;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class dice_turn {
    private int id;
    private Lock lock;

    dice_turn(int id) {
        this.id = id;
        this.lock = new ReentrantLock(true);
    }

    public boolean pickUp(Friends friend, States state) throws InterruptedException {
        if (lock.tryLock(10, TimeUnit.MILLISECONDS)) {
            System.out.println(friend + " picked up " + state.toString() + " " + this);
            return true;
        }
    
        return false;
    }

    public void putDown(Friends friend, States state) {
        lock.unlock();
        System.out.println(friend + " put down " + state.toString() + " " + this);
    }

    @Override
    public String toString() {
        return "Dice{" +
                "id=" + id +
                '}';
    }
}
