package com.example.animalproject.app.land.resident.herbivore;

import com.example.animalproject.app.land.Cell;
import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.resident.Animal;
import com.example.animalproject.app.land.resident.predator.Eagle;

public class Rabbit extends Herbivore {

    static {
        UtilAnimal.putMapAmountAnimal(Rabbit.class, 550);
        UtilAnimal.putSpeedAnimal(Rabbit.class, 2);
        UtilAnimal.putAbilityToReproduce(Rabbit.class, 2);
    }

    public Rabbit() {
        this.weight = 3;
        this.foodConsumption = 2;
        this.degreeOfSaturation = 0;
    }

    @Override
    public Animal reproduce(Cell cell) {
       Rabbit animal = new Rabbit();
        animal.setCell(cell);
        cell.add(animal);
        return animal;
    }
}
