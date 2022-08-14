package com.example.animalproject.app.land.resident.herbivore;

import com.example.animalproject.app.land.Cell;
import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.resident.Animal;
import com.example.animalproject.app.land.resident.predator.Eagle;

public class Buffalo extends Herbivore {

    static {
        UtilAnimal.putMapAmountAnimal(Buffalo.class, 10);
        UtilAnimal.putSpeedAnimal(Buffalo.class, 3);
        UtilAnimal.putAbilityToReproduce(Buffalo.class, 1);
    }

    public Buffalo() {
        this.weight = 700;
        this.foodConsumption = 100;
        this.degreeOfSaturation = 0;
    }

    @Override
    public Animal reproduceAnimal(Cell cell) {
        Buffalo animal = new Buffalo();
        animal.setCell(cell);
        cell.add(animal);
        return animal;
    }
}
