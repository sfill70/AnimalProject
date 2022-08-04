package com.example.animalproject.app.land.residents.herbivore;

import com.example.animalproject.app.land.residents.Animal;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Herbivore extends Animal {
    boolean isAive;
    public static volatile AtomicInteger count = new AtomicInteger(0);


    public Herbivore(){
        Herbivore.count.incrementAndGet();
    }


    public static AtomicInteger getCount() {
        return count;
    }


    public <T extends Animal> void dead(T animal) {
        super.dead(animal);
        Herbivore.count.decrementAndGet();
        animal.setAive(false);
        try {
            Field field = animal.getClass().getDeclaredField("count");
            AtomicInteger atomicInteger = (AtomicInteger) field.get(animal);
            atomicInteger.decrementAndGet();
//            System.out.println(animal.getClass().getSimpleName() + " " +atomicInteger.get());
            field.set(animal, atomicInteger);
        } catch (NoSuchFieldException e) {
            System.out.println(animal.getClass().getSimpleName() + " нехочет dead");
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /*@Override
    public void eat(Cell cell) {
    super.eat(cell);
    }*/





}
