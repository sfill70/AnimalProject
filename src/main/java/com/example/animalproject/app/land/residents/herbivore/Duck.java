package com.example.animalproject.app.land.residents.herbivore;

import com.example.animalproject.app.land.Cell;
import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.residents.Animal;

import java.util.concurrent.atomic.AtomicInteger;

public class Duck extends Herbivore {
    static volatile AtomicInteger count = new AtomicInteger(0);
    static {
        UtilAnimal.putMapAmountAnimal(Duck.class, 200);
        chanceSuccessfulHunt.put(Caterpillar.class.getClass(), 100);
        UtilAnimal.putSpeedAnimal(Duck.class, 4);
        UtilAnimal.putAbilityToReproduce(Duck.class, 8);

    }


    public Duck(){
        Duck.count.incrementAndGet();
        this.weight = 2;
        this.foodConsumption = 1;
        this.degreeOfSaturation = 0;
    }

    public void decrement(){
        Duck.count.decrementAndGet();
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
        return new Duck();
    }
   @Override
   public void eat(Cell cell) {
       if (getDegreeOfSaturation() >= getFoodConsumption()) {
           return;
       }
       int difference = getFoodConsumption() - getDegreeOfSaturation();
       for (Animal animal : cell.getSetResidents()
       ) {
           if (this.isEat(animal)) {
               if (difference > animal.getWeight()) {
                   difference = difference - animal.getWeight();
                   animal.dead(animal);
               } else {
                   setDegreeOfSaturation(getFoodConsumption());
                   animal.dead(animal);
                   break;
               }
           }
       }
       super.eat(cell);
   }

    /*@Override
    public Duck reproduce() {
        return new Duck();
    }*/
}