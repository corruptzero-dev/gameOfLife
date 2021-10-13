package task;


import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final int SIZE = 25;
    private static final String LIVE = "⬛";
    private static final String DEAD = "◻";
    private static final int DELAY = 250;

    public static void main(String[] args) throws InterruptedException {
        boolean[][] field = new boolean[SIZE][SIZE];
        Random random = new Random();
        for (int i=0; i<field.length; i++) {
            for (int j=0; j<field[i].length; j++) {
                boolean ceil = random.nextBoolean();
                field[i][j]=ceil;
            }
        }
        boolean[][] buffer;
        boolean[][] buffer2 = new boolean[SIZE][SIZE];
        System.out.println();

        boolean isStopped = false;
        boolean isLooped = false;
        boolean isDead = false;
        short i = 0;
        while (!isLooped && !isDead && !isStopped){
            buffer = field;
            if (i % 2 == 0) buffer2 = field;
            field = nextGen(field);
            if (Arrays.deepEquals(field,buffer)){
                System.out.println("Игра окончена (Эволюция остановилась). \nПоследнее поколение");
                showField(field);
                isStopped = true;
                break;
            }
            if (Arrays.deepEquals(field,buffer2)){
                System.out.println("Игра окончена (Осцилляторы). \nПоследнее поколение");
                showField(field);
                isLooped = true;
                break;
            }
            if (Arrays.deepEquals(field,new boolean[SIZE][SIZE])){
                System.out.println("Игра окончена (Смерть клеток). \nПоследнее поколение");
                showField(field);
                isDead = true;
                break;
            }
            System.out.printf("Поколение %d\n", i);
            showField(field);
            TimeUnit.MILLISECONDS.sleep(DELAY);
            System.out.println(System.lineSeparator().repeat(50));
            i++;
        }

    }
    static boolean[][] nextGen(boolean[][] field) {
        boolean[][] nextField = new boolean[SIZE][SIZE];
        for (int l = 1; l < SIZE - 1; l++) {
            for (int m = 1; m < SIZE - 1; m++) {
                int alive = 0;
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        alive += (field[l + i][m + j]?1:0);
                    }
                }
                alive -= (field[l][m]?1:0);
                if ((field[l][m]) && (alive < 2)) nextField[l][m] = false;
                else if ((field[l][m]) && (alive > 3)) nextField[l][m] = false;
                else if ((!field[l][m]) && (alive == 3)) nextField[l][m] = true;
                else nextField[l][m] = field[l][m];
            }
        }
        return nextField;
    }
    public static void showField(boolean[][] field){
        for (boolean[] x : field){
            for (boolean y : x){
                System.out.print(y?LIVE:DEAD);
            }
            System.out.println();
        }
    }
}
