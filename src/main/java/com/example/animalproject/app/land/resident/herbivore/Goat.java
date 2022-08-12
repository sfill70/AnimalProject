package com.example.animalproject.app.land.resident.herbivore;

import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.resident.Animal;

public class Goat extends Herbivore {

    static {
        UtilAnimal.putMapAmountAnimal(Goat.class, 400);
        UtilAnimal.putSpeedAnimal(Goat.class, 3);
        UtilAnimal.putAbilityToReproduce(Goat.class, 9);
    }

    public Goat(){
        this.weight = 60;
        this.foodConsumption = 10;
        this.degreeOfSaturation = 0;
    }

    @Override
    public Animal madeNewAnimal() {
        return new Goat();
    }
}
