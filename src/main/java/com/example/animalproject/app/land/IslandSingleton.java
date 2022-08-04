package com.example.animalproject.app.land;

import com.example.animalproject.PlayingField;
import com.example.animalproject.app.land.residents.Animal;
import com.example.animalproject.app.land.ThreadIsland.ThreadMoveMaster;
import com.example.animalproject.app.land.ThreadIsland.TreadInitialization;
import com.example.animalproject.app.land.residents.predator.Bear;


import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class IslandSingleton {
    private static volatile IslandSingleton instance;

    static int sizeX = PlayingField.getSizeX();
    static int sizeY = PlayingField.getSizeY();
    private final Cell[][] arrayCell = new Cell[sizeY][sizeX];

    public static IslandSingleton getInstance() {
        if (instance == null) {
            synchronized (IslandSingleton.class) {
                if (instance == null) {
                    instance = new IslandSingleton();
                }
            }
        }
        return instance;
    }

    public Cell[][] getArrayCell() {
        return arrayCell;
    }

    public void initialization() {
        for (int i = 0; i < arrayCell.length; i++) {
            for (int j = 0; j < arrayCell[0].length; j++) {
                arrayCell[i][j] = new Cell(i, j);
            }
        }
    }
/**многпоточная инициализация*/
    public void initializationThread() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < arrayCell.length; i++) {
            TreadInitialization treadInitialization = new TreadInitialization(arrayCell[i], i);
            executorService.execute(treadInitialization);
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        executorService.shutdown();
        final boolean done;
        try {
            done = executorService.awaitTermination(PlayingField.getTimeOut(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (done) {
            executorService.shutdownNow();
        }
    }

    /**
     * однопоточный метод перемешения животных в локации*/
    public void moves() {
        for (int i = 0; i < arrayCell.length; i++) {
            for (int j = 0; j < arrayCell[0].length; j++) {
                Set<? extends Animal> animalSet = arrayCell[i][j].getSetResidents();
                for (Animal an : animalSet
                ) {
                    if (an.isMove()) {
                        an.move();
                    }
                }
            }
        }
    }
/**
 * многопоточный метод перемешения животных в локации*/
    public void movesThread() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < arrayCell.length; i++) {
            ThreadMoveMaster threadMoveMaster = new ThreadMoveMaster(arrayCell[i], i);
            executorService.execute(threadMoveMaster);
        }
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        executorService.shutdown();
        boolean done;
        try {
            done = executorService.awaitTermination(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (done) {
            executorService.shutdownNow();
        }
    }


    /**
     * Голодные животные умирают, живые размножаются, isMove -> true, умирание отключено
     */
    public void clearAndReproduce() {
        for (int i = 0; i < arrayCell.length; i++) {
            for (int j = 0; j < arrayCell[0].length; j++) {
                arrayCell[i][j].setFood(4000 * 100 / ThreadLocalRandom.current().nextInt(50, 100));
                Set<? extends Animal> animalSet = arrayCell[i][j].getSetResidents();
                for (Animal an : animalSet
                ) {
                    if (an.getDegreeOfSaturation() <= 0) {
//                        an.dead(an);
                    }
                    an.setMove(true);
                    an.setDegreeOfSaturation(1);
                }
                arrayCell[i][j].reproduceCellAnimal();
            }
        }
    }
}
