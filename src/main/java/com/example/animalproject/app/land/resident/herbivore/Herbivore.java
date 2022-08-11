package com.example.animalproject.app.land.resident.herbivore;

import com.example.animalproject.app.land.resident.Animal;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Herbivore extends Animal {
    public static volatile AtomicInteger count = new AtomicInteger(0);
    public Herbivore(){
        Herbivore.count.incrementAndGet();
    }
    public static AtomicInteger getCount() {
        return count;
    }

    public <T extends Animal> void dead(T animal) {
        super.dead(animal, "Herbivore dead");
        Herbivore.count.decrementAndGet();
        animal.setAive(false);
        try {
            Field field = animal.getClass().getDeclaredField("count");
            AtomicInteger atomicInteger = (AtomicInteger) field.get(animal);
            atomicInteger.decrementAndGet();
            field.set(animal, atomicInteger);
        } catch (NoSuchFieldException e) {
            System.out.println(animal.getClass().getSimpleName() + " нехочет dead");
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
