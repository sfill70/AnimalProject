package com.example.animalproject.app.land.resident.herbivore;

import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.resident.Animal;

public class Rabbit extends Herbivore {

    static {
        UtilAnimal.putMapAmountAnimal(Rabbit.class, 550);
        UtilAnimal.putSpeedAnimal(Rabbit.class, 2);
        UtilAnimal.putAbilityToReproduce(Rabbit.class, 1);
    }

    public Rabbit() {
        this.weight = 3;
        this.foodConsumption = 2;
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
        return new Rabbit();
    }
}
