package com.example.animalproject.app.land.resident.herbivore;

import com.example.animalproject.app.land.Cell;
import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.resident.Animal;

public class Caterpillar extends Herbivore {

    static {
        UtilAnimal.putMapAmountAnimal(Caterpillar.class, 1000);
        UtilAnimal.putSpeedAnimal(Caterpillar.class, 0);
        UtilAnimal.putAbilityToReproduce(Caterpillar.class, 1);
    }

    public Caterpillar() {
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
            degreeOfSaturation = foodConsumption;
        }
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
