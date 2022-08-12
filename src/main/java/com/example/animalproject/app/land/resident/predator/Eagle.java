package com.example.animalproject.app.land.resident.predator;

import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.resident.Animal;
import com.example.animalproject.app.land.resident.herbivore.Caterpillar;
import com.example.animalproject.app.land.resident.herbivore.Mouse;
import com.example.animalproject.app.land.resident.herbivore.Rabbit;

public class Eagle extends Predator {

    static {
        UtilAnimal.putMapAmountAnimal(Eagle.class, 30);
        chanceSuccessfulHunt.put(Rabbit.class, 90);
        chanceSuccessfulHunt.put(Mouse.class, 90);
        chanceSuccessfulHunt.put(Caterpillar.class, 100);
        UtilAnimal.putSpeedAnimal(Eagle.class, 4);
        UtilAnimal.putAbilityToReproduce(Eagle.class, 1);
    }

    public Eagle() {
        this.foodConsumption = 1;
        this.degreeOfSaturation = 0;
    }

    @Override
    public Animal madeNewAnimal() {
        return new Eagle();
    }
}
