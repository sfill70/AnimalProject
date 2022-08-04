package com.example.animalproject.app.land.ThreadIsland;

import com.example.animalproject.PlayingField;
import com.example.animalproject.app.land.Cell;
import com.example.animalproject.app.land.IslandSingleton;
import com.example.animalproject.app.land.residents.Animal;

import java.util.Set;

public class ThreadMove implements Runnable{

    static int count;
    int intX;
    String name;
    Cell[] arrayCell;
    IslandSingleton islandSingleton = PlayingField.getIsland();

    public ThreadMove(Cell[] arrayCell, int x){
        this.arrayCell = arrayCell;
        this.intX = x;
        this.name = "Worker-" + count;
    }

    @Override
    public void run() {
        for (int j = 0; j < arrayCell.length; j++) {
            Set<? extends Animal> animalSet = islandSingleton.getArrayCell()[intX][j].getSetResidents();
            for (Animal an : animalSet
            ) {
                if (an.isMove()) {
                    an.move();
                }
            }
        }
    }
}
