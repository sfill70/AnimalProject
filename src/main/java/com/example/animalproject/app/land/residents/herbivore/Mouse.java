package com.example.animalproject.app.land.residents.herbivore;

import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.residents.Animal;

import java.util.concurrent.atomic.AtomicInteger;

public class Mouse extends Herbivore {

    static {
        UtilAnimal.putMapAmountAnimal(Mouse.class, 500);
        UtilAnimal.putSpeedAnimal(Mouse.class, 1);
        UtilAnimal.putAbilityToReproduce(Mouse.class, 8);
    }

    static volatile AtomicInteger count = new AtomicInteger(0);

    public Mouse() {
        count.incrementAndGet();
        this.weight = 1;
        this.foodConsumption = 1;
        this.degreeOfSaturation = 0;
    }

    public void decrement(){
        Mouse.count.decrementAndGet();
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
        return new Mouse();
    }

    /*@Override
    public Mouse reproduce() {
        return new Mouse();
    }*/
}
