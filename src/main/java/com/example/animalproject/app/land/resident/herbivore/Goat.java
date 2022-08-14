package com.example.animalproject.app.land.resident.herbivore;

import com.example.animalproject.app.land.Cell;
import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.resident.Animal;
import com.example.animalproject.app.land.resident.predator.Eagle;

public class Goat extends Herbivore {

    static {
        UtilAnimal.putMapAmountAnimal(Goat.class, 100);
        UtilAnimal.putSpeedAnimal(Goat.class, 3);
        UtilAnimal.putAbilityToReproduce(Goat.class, 3);
    }

    public Goat(){
        this.weight = 60;
        this.foodConsumption = 10;
        this.degreeOfSaturation = 0;
    }

    @Override
    public Animal reproduceAnimal(Cell cell) {
        Goat animal = new Goat();
        animal.setCell(cell);
        cell.add(animal);
        return animal;
    }
}
