package com.example.animalproject.app.land.resident.predator;

import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.resident.Animal;
import com.example.animalproject.app.land.resident.herbivore.*;

public class Bear extends Predator {

    static {
        UtilAnimal.putMapAmountAnimal(Bear.class, 7);
        Bear.chanceSuccessfulHunt.put(Horse.class, 50);
        Bear.chanceSuccessfulHunt.put(Deer.class, 50);
//        Bear.chanceSuccessfulHunt.put(Rabbit.class, 60);
//        Bear.chanceSuccessfulHunt.put(Mouse.class, 60);
        Bear.chanceSuccessfulHunt.put(Goat.class, 60);
        Bear.chanceSuccessfulHunt.put(Sheep.class, 60);
        Bear.chanceSuccessfulHunt.put(Boa.class, 30);
        UtilAnimal.putSpeedAnimal(Bear.class, 5);
        UtilAnimal.putAbilityToReproduce(Bear.class, 1);
    }

    public Bear() {
        this.foodConsumption =30;
        this.degreeOfSaturation = 0;
    }

    @Override
    public Animal madeNewAnimal() {
        return new Bear();
    }
}
