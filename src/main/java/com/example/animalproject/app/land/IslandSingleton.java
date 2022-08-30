package com.example.animalproject.app.land;

import com.example.animalproject.PlayingField;
import com.example.animalproject.Setup;
import com.example.animalproject.app.land.resident.Animal;
import com.example.animalproject.app.land.threadIsland.ThreadClean;
import com.example.animalproject.app.land.threadIsland.ThreadMoveMaster;
import com.example.animalproject.app.land.threadIsland.TreadInitialization;

import java.util.Set;
import java.util.concurrent.*;

public class IslandSingleton {
    private static volatile IslandSingleton instance;

    static int sizeX = PlayingField.getSizeX();
    static int sizeY = PlayingField.getSizeY();
    private final Cell[][] arrayCell = new Cell[sizeY][sizeX];
    public static final ConcurrentHashMap<Class<?>, Integer> mapStatisticResidents = new ConcurrentHashMap<>();
    String lineSeparator = System.lineSeparator();

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

    /**
     * метод добавления статистики из локации в оющую статистику
     */
    public static void addStatistic(Class<?> clazz, int amount) {
        synchronized (mapStatisticResidents) {
            if (mapStatisticResidents.containsKey(clazz)) {
                mapStatisticResidents.put(clazz,
                        mapStatisticResidents.get(clazz) + amount);
            } else {
                mapStatisticResidents.put(clazz, amount);
            }
        }
    }

    /**
     * метод получения общей статистики
     */
    public String viewStatistic() {
        StringBuffer sb = new StringBuffer();
        for (Class<?> clazz : mapStatisticResidents.keySet()
        ) {
            sb.append(clazz.getSimpleName()).append(" = ").append(mapStatisticResidents.get(clazz)).append("; ").append(lineSeparator);

        }
        return sb.toString();
    }

    public void initialization() {
        mapStatisticResidents.clear();
        for (int i = 0; i < arrayCell.length; i++) {
            for (int j = 0; j < arrayCell[0].length; j++) {
                arrayCell[i][j] = new Cell(i, j);
            }
        }
    }

    /**
     * многпоточная инициализация
     */
    public void initializationThread() {
        mapStatisticResidents.clear();
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
            done = executorService.awaitTermination(Setup.getTIME_OUT(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (done) {
            executorService.shutdownNow();
        }
    }

    /**
     * однопоточный метод перемешения животных в локации
     */
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
     * многопоточный метод перемешения животных в локации
     */
    public void movesThread() {
        ExecutorService executorService = Executors.newFixedThreadPool(Setup.getCORES());
        for (int i = 0; i < arrayCell.length; i++) {
            ThreadMoveMaster threadMoveMaster = new ThreadMoveMaster(arrayCell[i], i);
            executorService.execute(threadMoveMaster);
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


    /**
     * Голодные животные умирают, живые размножаются, isMove -> true, проверку
     * на сытость надо перенести в Cell reproduceCellAnimal()
     */
    public void clearAndReproduce() {
        mapStatisticResidents.clear();
        for (int i = 0; i < arrayCell.length; i++) {
            for (int j = 0; j < arrayCell[0].length; j++) {
                arrayCell[i][j].reproduceCellAnimal();
                for (Class<?> animalClazz : arrayCell[i][j].getMapCountResidents().keySet()) {
                    addStatistic(animalClazz, arrayCell[i][j].getMapCountResidents().get(animalClazz));
                }
            }
        }
    }

    /**
     * Голодные животные умирают, живые размножаются, isMove -> true.
     */
    public void clearAndReproduceThread() {
        mapStatisticResidents.clear();
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < arrayCell.length; i++) {
            ThreadClean threadClean = new ThreadClean(arrayCell[i], i);
            executorService.execute(threadClean);
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
