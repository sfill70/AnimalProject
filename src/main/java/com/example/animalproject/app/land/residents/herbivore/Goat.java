package com.example.animalproject.app.land.residents.herbivore;

import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.residents.Animal;

import java.util.concurrent.atomic.AtomicInteger;

public class Goat extends Herbivore {

    static {
        UtilAnimal.putMapAmountAnimal(Goat.class, 1400);
        UtilAnimal.putSpeedAnimal(Goat.class, 3);
        UtilAnimal.putAbilityToReproduce(Goat.class, 9);
    }
    static volatile AtomicInteger count = new AtomicInteger(0);

//    public int foodConsumption;

    public Goat(){
        count.incrementAndGet();
        this.weight = 60;
        this.foodConsumption = 10;
        this.degreeOfSaturation = 0;
    }

    public void decrement(){
       Goat.count.decrementAndGet();
        Herbivore.count.decrementAndGet();
        Animal.getCount().decrementAndGet();
    }

    public static AtomicInteger getCount() {
        return count;
    }

    public static void setCount(AtomicInteger count) {
        count = count;
    }

    @Override
    public Animal madeNewAnimal() {
        return new Goat();
    }




   /* @Override
    public Animal reproduce() {
        return new Goat();
    }*/
}
