package com.example.animalproject.app.land.resident.herbivore;

import com.example.animalproject.app.land.Cell;
import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.resident.Animal;

public class Horse extends Herbivore {

    static {
        UtilAnimal.putMapAmountAnimal(Horse.class, 40);
        UtilAnimal.putSpeedAnimal(Horse.class, 4);
        UtilAnimal.putAbilityToReproduce(Horse.class, 1);
    }

    public Horse() {
        this.weight = 400;
        this.foodConsumption = 30;
        this.degreeOfSaturation = 0;
    }

    @Override
    public Animal reproduce(Cell cell) {
        Horse animal = new Horse();
        animal.setCell(cell);
        cell.add(animal);
        return animal;
    }
}
