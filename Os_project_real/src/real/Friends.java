package real;

import java.util.Random;

public class Friends implements Runnable {
    // Every philosopher is going to be different thread.
    private int id;
    private dice_turn First_Dice;
    private dice_turn second_Dice;
    private int Counter;
    private volatile boolean full=false;
    private Random random;

    Friends(int id, dice_turn First_Dice, dice_turn second_Dice) {
        this.id = id;
        this.First_Dice = First_Dice;
        this.second_Dice = second_Dice;
        random = new Random();
    }

    @Override
    public void run() {

        try {
            while (!full) {

                think();
                if (First_Dice.pickUp(this, States.LEFT)) {
                    //able to acquire leftChopstick
                    if (second_Dice.pickUp(this, States.RIGHT)) {
                        //able to acquire rightChopstick
                        eat();
                        second_Dice.putDown(this, States.RIGHT);
                    }
                    First_Dice.putDown(this, States.LEFT);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void think() throws InterruptedException {
        System.out.println(this + " is Running...");
        Thread.sleep(random.nextInt(1000));
    }

    public void eat() throws InterruptedException {
        System.out.println(this + " is Sitting..");
        Counter++;
        Thread.sleep(random.nextInt(1000));
    }

    public void setFull(boolean full) {
        this.full = full;
    }

    public boolean isFull() {
        return this.full;
    }

    public int getEatingCounter() {
        return Counter;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "id=" + id +
                '}';
    }
}
