package com.example.animalproject.app.land.threadIsland;

import com.example.animalproject.PlayingField;
import com.example.animalproject.app.land.Cell;
import com.example.animalproject.app.land.IslandSingleton;

public class ThreadClean implements Runnable{

    static int count;
    int intX;
    String name;
    Cell[] arrayCell;
    IslandSingleton islandSingleton = PlayingField.getIsland();

    public ThreadClean(Cell[] arrayCell, int x){
        this.arrayCell = arrayCell;
        this.intX = x;
        this.name = "Worker-" + count;
    }

    @Override
    public void run() {
        for (int j = 0; j < arrayCell.length; j++) {
            islandSingleton.getArrayCell()[intX][j].reproduceCellAnimal();
            for (Class<?> animalClazz :islandSingleton.getArrayCell()[intX][j].getMapCountResidents().keySet()){
                islandSingleton.addStatistic(animalClazz,islandSingleton.getArrayCell()[intX][j].getMapCountResidents().get(animalClazz));
            }
        }
    }
}
