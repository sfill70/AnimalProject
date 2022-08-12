package com.example.animalproject.app.land.resident.herbivore;

import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.resident.Animal;

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

    public int getFoodConsumption() {
        return foodConsumption;
    }

    public void setFoodConsumption(int foodConsumption) {
        this.foodConsumption = foodConsumption;
    }

    @Override
    public Animal madeNewAnimal() {
        return new Sheep();
    }
}
