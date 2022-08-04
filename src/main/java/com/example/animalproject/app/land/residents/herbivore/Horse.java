package com.example.animalproject.app.land.residents.herbivore;

import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.residents.Animal;

import java.util.concurrent.atomic.AtomicInteger;

public class Horse extends Herbivore {

    static {
        UtilAnimal.putMapAmountAnimal(Horse.class, 40);
        UtilAnimal.putSpeedAnimal(Horse.class, 4);
        UtilAnimal.putAbilityToReproduce(Horse.class, 1);
    }
    static volatile AtomicInteger count = new AtomicInteger(0);

    public Horse() {
        count.incrementAndGet();
        this.weight = 400;
        this.foodConsumption = 30;
        this.degreeOfSaturation = 0;
    }

    public void decrement(){
        Horse.count.decrementAndGet();
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
        return new Horse();
    }

    /*@Override
    public Horse reproduce() {
        return new Horse();
    }*/
}
