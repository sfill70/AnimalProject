package com.example.animalproject.app.land.resident.herbivore;

import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.resident.Animal;

import java.util.concurrent.atomic.AtomicInteger;

public class Sheep extends Herbivore {

    static {
        UtilAnimal.putMapAmountAnimal(Sheep.class, 40);
        UtilAnimal.putSpeedAnimal(Sheep.class, 3);
        UtilAnimal.putAbilityToReproduce(Sheep.class, 5);
    }

    static volatile AtomicInteger count = new AtomicInteger(0);

    public Sheep() {
        count.incrementAndGet();
        this.weight = 70;
        this.foodConsumption = 15;
        this.degreeOfSaturation = 0;
    }

    public void decrement() {
        Sheep.count.decrementAndGet();
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
        return new Sheep();
    }
}
