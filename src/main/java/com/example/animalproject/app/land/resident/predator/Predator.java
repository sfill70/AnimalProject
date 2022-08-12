package com.example.animalproject.app.land.resident.predator;

import com.example.animalproject.app.land.Cell;
import com.example.animalproject.app.land.resident.Animal;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class Predator extends Animal {

    /**
     * Метод еды для хищника. В закоментированном коде можно увидеть, что туда попадают значения false
     */
    @Override
    public void eat(Cell cell) {
        if (getDegreeOfSaturation() >= getFoodConsumption()) {
            return;
        }
        int difference = getFoodConsumption() - getDegreeOfSaturation();
        if (difference > cell.getCarrion().get()) {
            difference = difference - cell.getCarrion().get();
            cell.setCarrion(new AtomicInteger(0));
        } else {
            setDegreeOfSaturation(getFoodConsumption());
            cell.setCarrion(new AtomicInteger(cell.getCarrion().get() - difference));
            return;
        }
        for (Animal animal : cell.getSetResidents()
        ) {
            if (this.isEat(animal) && animal.isFree()) {
                if (difference > animal.getWeight()) {
                    difference = difference - animal.getWeight();
                    animal.dead(animal, "Predator eat_1");
                    this.setDegreeOfSaturation(getDegreeOfSaturation() + animal.getWeight());
                } else {
                    setDegreeOfSaturation(getFoodConsumption());
                    cell.setCarrion(new AtomicInteger(cell.getCarrion().get() + animal.getWeight() - difference));
                    animal.dead(animal, "Predator eat_2");
                    break;
                }
            }
        }
    }
}
