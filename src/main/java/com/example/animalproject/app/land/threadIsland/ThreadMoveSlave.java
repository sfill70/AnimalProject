package com.example.animalproject.app.land.threadIsland;

import com.example.animalproject.app.land.resident.Animal;

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
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
