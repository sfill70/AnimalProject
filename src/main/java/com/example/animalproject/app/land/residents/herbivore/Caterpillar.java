package com.example.animalproject.app.land.residents.herbivore;

import com.example.animalproject.app.land.Cell;
import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.residents.Animal;

import java.util.concurrent.atomic.AtomicInteger;

public class Caterpillar extends Herbivore {
    static volatile AtomicInteger count = new AtomicInteger(0);

    static {
        UtilAnimal.putMapAmountAnimal(Caterpillar.class, 1000);
        UtilAnimal.putSpeedAnimal(Caterpillar.class, 0);
        UtilAnimal.putAbilityToReproduce(Caterpillar.class, 1);
    }

    public Caterpillar() {
        count.incrementAndGet();
        this.weight = 1;
        this.foodConsumption = 1;
        this.degreeOfSaturation = 1;
    }

    @Override
    public void move() {
        eat(this.getCell());
    }

    @Override
    public void eat(Cell cell) {
        if(cell.getFoodHerbivore().get()>1){
//            cell.setFood(cell.getFood().get()-1);
            degreeOfSaturation = foodConsumption;
        }
    }

    public void decrement(){
        Caterpillar.count.decrementAndGet();
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
        return new Caterpillar();
    }

}
