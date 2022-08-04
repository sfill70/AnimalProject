package com.example.animalproject.app.land.ThreadIsland;

import com.example.animalproject.PlayingField;
import com.example.animalproject.app.land.Cell;
import com.example.animalproject.app.land.IslandSingleton;
import com.example.animalproject.app.land.residents.Animal;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
            ExecutorService executorService = Executors.newFixedThreadPool(5);
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
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            executorService.shutdown();
        }
    }
}
