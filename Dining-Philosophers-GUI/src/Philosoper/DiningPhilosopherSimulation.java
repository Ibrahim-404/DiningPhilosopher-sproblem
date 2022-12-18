package Philosoper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Random;



public class DiningPhilosopherSimulation implements Runnable {
    


          private int id;
           public static MainFram mf=new MainFram();
    private Chopsticks leftChopSticks;
    private Chopsticks rightChopSticks;
    private int eatingCounter;
    private volatile boolean full=false;
    private Random random;

    DiningPhilosopherSimulation(int id, Chopsticks leftChopSticks, Chopsticks rightChopSticks) {
        this.id = id;
        this.leftChopSticks = leftChopSticks;
        this.rightChopSticks = rightChopSticks;
        random = new Random();
    }
    @Override

    public void run() {


        try {
            while (!full) {
                
                think();
               
                if (leftChopSticks.pickUp(this, States.LEFT)) {
                    //able to acquire leftChopstick
                    if (rightChopSticks.pickUp(this, States.RIGHT)) {
                        //able to acquire rightChopstick
                       
                       mf.setPosition(id,"eat");
                        eat();
                      
                        rightChopSticks.putDown(this, States.RIGHT);
                    }
                    leftChopSticks.putDown(this, States.LEFT);
                }
                  mf.setPosition(id,"think");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
   
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = null;
        DiningPhilosopherSimulation philosopher[] = null;
        Chopsticks chopsticks[];
           mf.show();
           


        try {
            chopsticks = new Chopsticks[Constants.NUMBER_OF_CHOPSTICKS];
            philosopher = new DiningPhilosopherSimulation[Constants.NUMBER_OF_PHILOSOPHER];
            executorService = Executors.newFixedThreadPool(Constants.NUMBER_OF_PHILOSOPHER);

            for (int i = 0; i < Constants.NUMBER_OF_CHOPSTICKS; i++) {
                chopsticks[i] = new Chopsticks(i);
            }

            for (int i = 0; i < Constants.NUMBER_OF_PHILOSOPHER; i++) {
                philosopher[i] = new DiningPhilosopherSimulation(i, chopsticks[i], chopsticks[(i + 1) % Constants.NUMBER_OF_CHOPSTICKS]);
                executorService.execute(philosopher[i]);
            }

            Thread.sleep(Constants.SIMULATION_TIME);
            for (DiningPhilosopherSimulation philosophers : philosopher) {
                philosophers.setFull(true);
            }

        } finally {
            executorService.shutdown();

            while (!executorService.isTerminated())
                Thread.sleep(1000);

            for (DiningPhilosopherSimulation philosophers : philosopher) {
                System.out.println(philosophers + " eat# " + philosophers.getEatingCounter());
            }
        }
    }

    public void think() throws InterruptedException {
        System.out.println(this + " is thinking...");
        Thread.sleep(random.nextInt(1000));
    }

    public void eat() throws InterruptedException {
        System.out.println(this + " is eating..");
        eatingCounter++;
        Thread.sleep(random.nextInt(1000));
    }

    public void setFull(boolean full) {
        this.full = full;
    }

    public boolean isFull() {
        return this.full;
    }

    public int getEatingCounter() {
        return eatingCounter;
    }

    @Override
    public String toString() {
        return "Philosopher{" +
                "id=" + id +
                '}';
    }
}
    

