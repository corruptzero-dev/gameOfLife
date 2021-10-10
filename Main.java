package task;


import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final int SIZE = 20; // оптимально: 20
    private static final String LIVE = "⬛";
    private static final String DEAD = "◻";
    private static final int DELAY = 250; // оптимально: 500
    private static final int GENERATIONS = 100; 

    public static void main(String[] args) throws InterruptedException {
        int[][] field = new int[SIZE][SIZE];
        Random random = new Random();
        for (int i=0; i<field.length; i++) {
            for (int j=0; j<field[i].length; j++) {
                boolean ceil = random.nextBoolean();
                field[i][j]=ceil?1:0;
            }
        }
        int[][] buffer;
        int[][] buffer2 = new int[SIZE][SIZE];
        System.out.println();

        boolean isStopped = false;
        boolean isLooped = false;
        boolean isDead = false;
        for (int i = 0; i <= GENERATIONS; i++) {
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
            if (Arrays.deepEquals(field,new int[SIZE][SIZE])){
                System.out.println("Игра окончена (Смерть клеток). \nПоследнее поколение");
                showField(field);
                isDead = true;
                break;
            }
            System.out.printf("Поколение %d\n", i);
            showField(field);
            TimeUnit.MILLISECONDS.sleep(DELAY);
            System.out.println(System.lineSeparator().repeat(50));
        }
        if (!isStopped && !isLooped && !isDead){
            System.out.println("Игра окончена. Последнее поколение");
            showField(field);
        }

    }
    static int[][] nextGen(int[][] field) {
        int[][] nextField = new int[SIZE][SIZE];
        for (int l = 1; l < SIZE - 1; l++) {
            for (int m = 1; m < SIZE - 1; m++) {
                int alive = 0;
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        alive += field[l + i][m + j];
                    }
                }
                alive -= field[l][m];
                if ((field[l][m] == 1) && (alive < 2)) nextField[l][m] = 0;
                else if ((field[l][m] == 1) && (alive > 3)) nextField[l][m] = 0;
                else if ((field[l][m] == 0) && (alive == 3)) nextField[l][m] = 1;
                else nextField[l][m] = field[l][m];
            }
        }
        return nextField;
    }
    public static void showField(int[][] field){
        for (int[] x : field){
            for (int y : x){
                System.out.print(y==1?LIVE:DEAD);
            }
            System.out.println();
        }
    }
}
