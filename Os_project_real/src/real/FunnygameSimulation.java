package real;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DiningPhilosopherSimulation {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = null;
        Friends friends[] = null;
        dice_turn dice_turns[];

        try {
            dice_turns = new dice_turn[Constants.NUMBER_OF_Dice];
            friends = new Friends[Constants.NUMBER_OF_Friends];
            executorService = Executors.newFixedThreadPool(Constants.NUMBER_OF_Friends);

            for (int i = 0; i < Constants.NUMBER_OF_Dice; i++) {
                dice_turns[i] = new dice_turn(i);
            }

            for (int i = 0; i < Constants.NUMBER_OF_Friends; i++) {
                friends[i] = new Friends(i, dice_turns[i], dice_turns[(i + 1) % Constants.NUMBER_OF_Dice]);
                executorService.execute(friends[i]);
            }

            Thread.sleep(Constants.SIMULATION_TIME);
            for (Friends friendss : friends) {
                friendss.setFull(true);
            }

        } finally {
            executorService.shutdown();

            while (!executorService.isTerminated())
                Thread.sleep(1000);

            for (Friends friendss : friends) {
                System.out.println(friendss + " Sitting# " + friendss.getEatingCounter());
            }
        }
    }
}
