package com.example.animalproject.app.land.threadIsland;

import com.example.animalproject.PlayingField;
import com.example.animalproject.app.land.Cell;
import com.example.animalproject.app.land.IslandSingleton;

public class TreadInitialization implements Runnable {
    static int count;
    int intX;
    String name;
    Cell[] arrayCell;

    IslandSingleton islandSingleton = PlayingField.getIsland();

    public TreadInitialization(Cell[] arrayCell, int x) {
        this.arrayCell = arrayCell;
        this.intX = x;
        this.name = "Worker-" + count;
    }

    @Override
    public void run() {
        for (int j = 0; j < arrayCell.length; j++) {
            Cell cell = new Cell(intX, j);
            islandSingleton.getArrayCell()[intX][j] = cell;
        }
    }
}
