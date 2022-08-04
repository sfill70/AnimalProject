package com.example.animalproject.app.land.ThreadIsland;

import com.example.animalproject.PlayingField;
import com.example.animalproject.app.land.Cell;
import com.example.animalproject.app.land.IslandSingleton;
import com.example.animalproject.app.land.residents.Animal;

public class ThreadMoveSlave implements Runnable{
    static int count;
    int intX;
    String name;
    Animal animal;

    public ThreadMoveSlave(Animal animal){
        this.animal = animal;
        this.name = "Slaver-" + count;
    }

    @Override
    public void run() {
        animal.move();
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
