package com.example.animalproject.app.land.residents.predator;

import com.example.animalproject.app.land.Cell;
import com.example.animalproject.app.land.residents.Animal;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Predator extends Animal {
    public static volatile AtomicInteger count = new AtomicInteger(0);

    public Predator() {
        Predator.count.incrementAndGet();
        this.name = this.getClass().getSimpleName() + "_" + count;
    }

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

    public <T extends Animal> void dead(T animal) {
        super.dead(animal, "Predator dead");
        Predator.count.decrementAndGet();
        animal.setAive(false);
        try {
            Field field = animal.getClass().getDeclaredField("count");
            AtomicInteger atomicInteger = (AtomicInteger) field.get(animal);
            atomicInteger.decrementAndGet();
            field.set(animal, atomicInteger);
        } catch (NoSuchFieldException e) {
            System.out.println(animal.getClass().getSimpleName() + " нехочет dead " + e.getClass());
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            System.out.println(animal.getClass().getSimpleName() + " нехочет dead " + e.getClass());
            throw new RuntimeException(e);
        }
    }

    public static AtomicInteger getCount() {
        return count;
    }

    public String getName() {
        return name;
    }

    public static void setCount(AtomicInteger count) {
        Predator.count = count;
    }
}
