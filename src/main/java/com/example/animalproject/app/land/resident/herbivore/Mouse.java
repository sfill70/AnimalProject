package com.example.animalproject.app.land.resident.herbivore;

import com.example.animalproject.app.land.Cell;
import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.resident.Animal;
import com.example.animalproject.app.land.resident.predator.Eagle;

public class Mouse extends Herbivore {

    static {
        UtilAnimal.putMapAmountAnimal(Mouse.class, 500);
        UtilAnimal.putSpeedAnimal(Mouse.class, 3);
        UtilAnimal.putAbilityToReproduce(Mouse.class, 2);
    }

    public Mouse() {
        this.weight = 1;
        this.foodConsumption = 1;
        this.degreeOfSaturation = 0;
    }

    @Override
    public Animal reproduceAnimal(Cell cell) {
        Mouse animal = new Mouse();
        animal.setCell(cell);
        cell.add(animal);
        return animal;
    }
}
