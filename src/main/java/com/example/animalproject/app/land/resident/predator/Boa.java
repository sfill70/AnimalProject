package com.example.animalproject.app.land.resident.predator;

import com.example.animalproject.app.land.Cell;
import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.resident.Animal;
import com.example.animalproject.app.land.resident.herbivore.*;

public class Boa extends Predator {

    static {
        UtilAnimal.putMapAmountAnimal(Boa.class, 50);
        chanceSuccessfulHunt.put(Caterpillar.class, 80);
        chanceSuccessfulHunt.put(Rabbit.class, 40);
        chanceSuccessfulHunt.put(Mouse.class, 40);
        UtilAnimal.putSpeedAnimal(Boa.class, 1);
        UtilAnimal.putAbilityToReproduce(Boa.class, 2);
    }

    public Boa() {
        this.foodConsumption = 3;
        this.degreeOfSaturation = 0;
    }

    @Override
    public Animal reproduceAnimal(Cell cell) {
        Boa animal = new Boa();
        animal.setCell(cell);
        cell.add(animal);
        return animal;
    }
}
