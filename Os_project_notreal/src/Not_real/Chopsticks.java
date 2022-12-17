package Not_real;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Chopsticks {
    private int id;
    private Lock lock;

    Chopsticks(int id) {
        this.id = id;
        this.lock = new ReentrantLock(true);
    }

    public boolean pickUp(Philosopher philosopher, States state) throws InterruptedException {
        if (lock.tryLock(10, TimeUnit.MILLISECONDS)) {
            System.out.println(philosopher + " picked up " + state.toString() + " " + this);
            return true;
        }
    
        return false;
    }

    public void putDown(Philosopher philosopher, States state) {
        lock.unlock();
        System.out.println(philosopher + " put down " + state.toString() + " " + this);
    }

    @Override
    public String toString() {
        return "Chopsticks{" +
                "id=" + id +
                '}';
    }
}
