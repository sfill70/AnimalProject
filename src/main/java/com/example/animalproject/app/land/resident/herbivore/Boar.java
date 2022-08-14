package com.example.animalproject.app.land.resident.herbivore;

import com.example.animalproject.app.land.Cell;
import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.resident.Animal;
import com.example.animalproject.app.land.resident.predator.Eagle;

public class Boar extends Herbivore {
    static {
        UtilAnimal.putMapAmountAnimal(Boar.class, 150);
        chanceSuccessfulHunt.put(Mouse.class, 50);
        UtilAnimal.putSpeedAnimal(Boar.class, 2);
        UtilAnimal.putAbilityToReproduce(Boar.class, 2);
    }

    public Boar() {
        this.weight = 50;
        this.foodConsumption = 50;
        this.degreeOfSaturation = 0;
    }

    @Override
    public Animal reproduceAnimal(Cell cell) {
        Boar animal = new Boar();
        animal.setCell(cell);
        cell.add(animal);
        return animal;
    }
}
