package com.example.animalproject.app.land.threadIsland;

import com.example.animalproject.app.land.resident.Animal;
import com.example.animalproject.app.land.resident.predator.Bear;

public class ThreadMoveSlave implements Runnable {
    static int count;
    int intX;
    String name;
    Animal animal;

    public ThreadMoveSlave(Animal animal) {
        this.animal = animal;
        this.name = "Slaver-" + count;
    }

    @Override
    public void run() {
        animal.move();
    }
}

/*if (animal.getClass().getSimpleName().equals(Bear.class.getSimpleName())) {
        System.out.println(animal.getName() + "-" + animal.getCell());
        }*/
