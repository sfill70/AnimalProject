package com.example.animalproject.app.land.resident.herbivore;

import com.example.animalproject.app.land.Cell;
import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.resident.Animal;
import com.example.animalproject.app.land.resident.predator.Eagle;

public class Deer extends Herbivore {

    static {
        UtilAnimal.putMapAmountAnimal(Deer.class, 170);
        UtilAnimal.putSpeedAnimal(Deer.class, 5);
        UtilAnimal.putAbilityToReproduce(Deer.class, 1);
    }

    public Deer() {
        this.weight = 300;
        this.foodConsumption = 20;
        this.degreeOfSaturation = 0;
    }
    @Override
    public Animal reproduce(Cell cell) {
        Deer animal = new Deer();
        animal.setCell(cell);
        cell.add(animal);
        return animal;
    }
}
