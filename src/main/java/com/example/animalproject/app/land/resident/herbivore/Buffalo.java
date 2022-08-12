package com.example.animalproject.app.land.resident.herbivore;

import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.resident.Animal;

public class Buffalo extends Herbivore {

    static {
        UtilAnimal.putMapAmountAnimal(Buffalo.class, 10);
        UtilAnimal.putSpeedAnimal(Buffalo.class, 3);
        UtilAnimal.putAbilityToReproduce(Buffalo.class, 1);
    }

    public Buffalo() {
        this.weight = 700;
        this.foodConsumption = 100;
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
        return new Buffalo();
    }
}
