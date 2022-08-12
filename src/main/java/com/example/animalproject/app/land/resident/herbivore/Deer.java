package com.example.animalproject.app.land.resident.herbivore;

import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.resident.Animal;

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

    public int getFoodConsumption() {
        return foodConsumption;
    }

    public void setFoodConsumption(int foodConsumption) {
        this.foodConsumption = foodConsumption;
    }

    @Override
    public Animal madeNewAnimal() {
        return new Deer();
    }
}
