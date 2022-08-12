package com.example.animalproject.app.land.resident.herbivore;

import com.example.animalproject.app.land.Cell;
import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.resident.Animal;
import com.example.animalproject.app.land.resident.predator.Eagle;

public class Caterpillar extends Herbivore {

    static {
        UtilAnimal.putMapAmountAnimal(Caterpillar.class, 1000);
        UtilAnimal.putSpeedAnimal(Caterpillar.class, 0);
        UtilAnimal.putAbilityToReproduce(Caterpillar.class, 1);
    }

    public Caterpillar() {
        this.weight = 1;
        this.foodConsumption = 1;
        this.degreeOfSaturation = 1;
    }

    @Override
    public void move() {
        eat(this.getCell());
    }

    @Override
    public void eat(Cell cell) {
        if (cell.getFoodHerbivore().get() > 1) {
            degreeOfSaturation = foodConsumption;
        }
    }

    @Override
    public Animal reproduce(Cell cell) {
        Caterpillar animal = new Caterpillar();
        animal.setCell(cell);
        cell.add(animal);
        return animal;
    }

}
