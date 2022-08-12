package com.example.animalproject.app.land.resident.predator;

import com.example.animalproject.app.land.Cell;
import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.resident.Animal;
import com.example.animalproject.app.land.resident.herbivore.Mouse;
import com.example.animalproject.app.land.resident.herbivore.Rabbit;

public class Fox extends Predator {

    static {
        UtilAnimal.putMapAmountAnimal(Fox.class, 40);
        chanceSuccessfulHunt.put(Rabbit.class, 50);
        chanceSuccessfulHunt.put(Mouse.class, 50);
        UtilAnimal.putSpeedAnimal(Fox.class, 5);
        UtilAnimal.putAbilityToReproduce(Fox.class, 1);
    }

    public Fox() {
        this.foodConsumption = 2;
        this.degreeOfSaturation = 0;
    }

    @Override
    public Animal reproduce(Cell cell) {
        Fox animal = new Fox();
        animal.setCell(cell);
        cell.add(animal);
        return animal;
    }
}
