package com.example.animalproject.app.land.residents.herbivore;

import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.residents.Animal;

import java.util.concurrent.atomic.AtomicInteger;

public class Buffalo extends Herbivore {
    static volatile AtomicInteger count = new AtomicInteger(0);

    static {
        UtilAnimal.putMapAmountAnimal(Buffalo.class, 10);
        UtilAnimal.putSpeedAnimal(Buffalo.class, 3);
        UtilAnimal.putAbilityToReproduce(Buffalo.class, 1);
    }

    public Buffalo() {
        count.incrementAndGet();
        this.weight = 700;
        this.foodConsumption = 100;
        this.degreeOfSaturation = 0;
    }

    public void decrement(){
        Buffalo.count.decrementAndGet();
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
        return new Buffalo();
    }
}
