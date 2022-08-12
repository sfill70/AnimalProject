package com.example.animalproject.app.land.resident.herbivore;

import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.resident.Animal;

public class Boar extends Herbivore {
    static {
        UtilAnimal.putMapAmountAnimal(Boar.class, 150);
        chanceSuccessfulHunt.put(Mouse.class, 50);
        UtilAnimal.putSpeedAnimal(Boar.class, 2);
        UtilAnimal.putAbilityToReproduce(Boar.class, 1);
    }

    public Boar() {
        this.weight = 50;
        this.foodConsumption = 50;
        this.degreeOfSaturation = 0;
    }

    public int getFoodConsumption() {
        return foodConsumption;
    }

    public void setFoodConsumption(int foodConsumption) {
        this.foodConsumption = foodConsumption;
    }

    @Override
    public Animal madeNewAnimal() {
        return new Boar();
    }
}
