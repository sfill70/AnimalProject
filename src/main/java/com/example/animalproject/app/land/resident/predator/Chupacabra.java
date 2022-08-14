package com.example.animalproject.app.land.resident.predator;

import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.resident.herbivore.*;

public class Chupacabra extends Wolf{
    static {
        UtilAnimal.putMapAmountAnimal(Chupacabra.class, 30);
        Wolf.chanceSuccessfulHunt.put(Horse.class, 30);
        Wolf.chanceSuccessfulHunt.put(Deer.class, 35);
        Wolf.chanceSuccessfulHunt.put(Rabbit.class, 50);
        Wolf.chanceSuccessfulHunt.put(Mouse.class, 50);
        Wolf.chanceSuccessfulHunt.put(Goat.class, 50);
        Wolf.chanceSuccessfulHunt.put(Sheep.class, 50);
        UtilAnimal.putSpeedAnimal(Chupacabra.class, 3);
        UtilAnimal.putAbilityToReproduce(Chupacabra.class, 4);
    }
    public Chupacabra() {
        super();
        this.foodConsumption = 16;
    }
}
