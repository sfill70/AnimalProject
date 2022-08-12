package com.example.animalproject.app.land.resident.herbivore;

import com.example.animalproject.app.land.Cell;
import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.resident.Animal;

public class Duck extends Herbivore {
    static {
        UtilAnimal.putMapAmountAnimal(Duck.class, 200);
        chanceSuccessfulHunt.put(Caterpillar.class, 100);
        UtilAnimal.putSpeedAnimal(Duck.class, 4);
        UtilAnimal.putAbilityToReproduce(Duck.class, 1);
    }


    public Duck() {
        this.weight = 2;
        this.foodConsumption = 1;
        this.degreeOfSaturation = 0;
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
                    animal.dead(animal, "Duck eat");
                } else {
                    setDegreeOfSaturation(getFoodConsumption());
                    animal.dead(animal, "Duck eat");
                    break;
                }
            }
        }
        super.eat(cell);
    }
}