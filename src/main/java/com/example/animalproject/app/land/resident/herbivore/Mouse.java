package com.example.animalproject.app.land.resident.herbivore;

import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.resident.Animal;

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


    public int getFoodConsumption() {
        return foodConsumption;
    }

    public void setFoodConsumption(int foodConsumption) {
        this.foodConsumption = foodConsumption;
    }

    @Override
    public Animal madeNewAnimal() {
        return new Mouse();
    }
}
