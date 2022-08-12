package com.example.animalproject.app.land.resident.predator;

import com.example.animalproject.app.land.Cell;
import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.resident.Animal;
import com.example.animalproject.app.land.resident.herbivore.*;

public class Wolf extends Predator {

    static {
        UtilAnimal.putMapAmountAnimal(Wolf.class, 30);
        Wolf.chanceSuccessfulHunt.put(Horse.class, 30);
        Wolf.chanceSuccessfulHunt.put(Deer.class, 35);
        Wolf.chanceSuccessfulHunt.put(Rabbit.class, 50);
        Wolf.chanceSuccessfulHunt.put(Mouse.class, 60);
        Wolf.chanceSuccessfulHunt.put(Goat.class, 60);
        Wolf.chanceSuccessfulHunt.put(Sheep.class, 70);
        UtilAnimal.putSpeedAnimal(Wolf.class, 3);
        UtilAnimal.putAbilityToReproduce(Wolf.class, 1);
    }

    public Wolf() {
        this.weight = 50;
        this.icon = "\uD83D\uDC3B";
        this.foodConsumption = 8;
        this.degreeOfSaturation = 0;
    }

    @Override
    public Animal reproduce(Cell cell) {
        Wolf wolf = new Wolf();
        wolf.setCell(cell);
        cell.add(wolf);
        return wolf;
    }
}
