package com.example.animalproject.app.land.residents.herbivore;
import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.residents.Animal;

import java.util.concurrent.atomic.AtomicInteger;

public class Boar extends Herbivore {
    static {
        UtilAnimal.putMapAmountAnimal(Boar.class, 50);
        chanceSuccessfulHunt.put(Mouse.class, 50);
        UtilAnimal.putSpeedAnimal(Boar.class, 2);
        UtilAnimal.putAbilityToReproduce(Boar.class, 3);
    }
    static volatile AtomicInteger count = new AtomicInteger(0);



    public Boar() {
        count.incrementAndGet();
        this.weight = 50;
        this.foodConsumption = 50;
        this.degreeOfSaturation = 0;
    }

    public void decrement(){
        Boar.count.decrementAndGet();
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
        return new Boar();
    }




}
