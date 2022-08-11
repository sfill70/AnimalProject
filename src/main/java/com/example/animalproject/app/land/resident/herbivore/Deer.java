package com.example.animalproject.app.land.resident.herbivore;

import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.resident.Animal;

import java.util.concurrent.atomic.AtomicInteger;

public class Deer extends Herbivore {

    static {
        UtilAnimal.putMapAmountAnimal(Deer.class, 170);
        UtilAnimal.putSpeedAnimal(Deer.class, 5);
        UtilAnimal.putAbilityToReproduce(Deer.class, 1);
    }

    static volatile AtomicInteger count = new AtomicInteger(0);

    public Deer() {
        count.incrementAndGet();
        this.weight = 300;
        this.foodConsumption = 20;
        this.degreeOfSaturation = 0;
    }

    public void decrement() {
        Deer.count.decrementAndGet();
        Herbivore.count.decrementAndGet();
        Animal.getCount().decrementAndGet();
    }

    public static AtomicInteger getCount() {
        return count;
    }

    public static void setCount(AtomicInteger count) {
        count = count;
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
