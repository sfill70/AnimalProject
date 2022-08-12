package com.example.animalproject.app.land.resident.herbivore;

import com.example.animalproject.app.land.Cell;
import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.resident.Animal;
import com.example.animalproject.app.land.resident.predator.Eagle;

public class Sheep extends Herbivore {

    static {
        UtilAnimal.putMapAmountAnimal(Sheep.class, 40);
        UtilAnimal.putSpeedAnimal(Sheep.class, 3);
        UtilAnimal.putAbilityToReproduce(Sheep.class, 5);
    }

    public Sheep() {
        this.weight = 70;
        this.foodConsumption = 15;
        this.degreeOfSaturation = 0;
    }

    @Override
    public Animal reproduce(Cell cell) {
        Sheep animal = new Sheep();
        animal.setCell(cell);
        cell.add(animal);
        return animal;
    }
}
