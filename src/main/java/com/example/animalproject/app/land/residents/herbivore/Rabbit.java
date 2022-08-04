package com.example.animalproject.app.land.residents.herbivore;

import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.residents.Animal;

import java.util.concurrent.atomic.AtomicInteger;

public class Rabbit extends Herbivore {

    static {
        UtilAnimal.putMapAmountAnimal(Rabbit.class, 150);
        UtilAnimal.putSpeedAnimal(Rabbit.class, 2);
        UtilAnimal.putAbilityToReproduce(Rabbit.class, 5);
    }

    static volatile AtomicInteger count = new AtomicInteger(0);

    public Rabbit() {
        count.incrementAndGet();
        this.weight = 3;
        this.foodConsumption = 2;
        this.degreeOfSaturation = 0;
    }

    public void decrement(){
        Rabbit.count.decrementAndGet();
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
        return new Rabbit();
    }

   /* @Override
    public Rabbit reproduce() {
        return new Rabbit();
    }*/
}