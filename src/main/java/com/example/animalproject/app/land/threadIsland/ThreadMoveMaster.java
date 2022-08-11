package com.example.animalproject.app.land.threadIsland;

import com.example.animalproject.PlayingField;
import com.example.animalproject.Setup;
import com.example.animalproject.app.land.Cell;
import com.example.animalproject.app.land.IslandSingleton;
import com.example.animalproject.app.land.resident.Animal;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadMoveMaster implements Runnable {

    static int count;
    int intX;
    String name;
    Cell[] arrayCell;
    IslandSingleton islandSingleton = PlayingField.getIsland();

    public ThreadMoveMaster(Cell[] arrayCell, int x) {
        this.arrayCell = arrayCell;
        this.intX = x;
        this.name = "Worker-" + count;
    }

    @Override
    public void run() {
        for (int j = 0; j < arrayCell.length; j++) {
            ExecutorService executorService = Executors.newFixedThreadPool(Setup.getCORES());
            Set<? extends Animal> animalSet = islandSingleton.getArrayCell()[intX][j].getSetResidents();
            for (Animal an : animalSet
            ) {
                if (an.isMove()) {
                    Cell cell = islandSingleton.getArrayCell()[intX][j];
                    ThreadMoveSlave threadMoveSlave = new ThreadMoveSlave(an);
                    executorService.execute(threadMoveSlave);
                }
            }
            executorService.shutdown();
            boolean done;
            try {
                done = executorService.awaitTermination(Setup.getTIME_OUT(), TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (done) {
                executorService.shutdownNow();
            }
        }
    }
}
