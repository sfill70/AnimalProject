package com.example.animalproject.app.land.residents.predator;

import com.example.animalproject.app.land.Cell;
import com.example.animalproject.app.land.residents.Animal;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Predator extends Animal {
    boolean isAive;
    public static volatile AtomicInteger count = new AtomicInteger(0);

    public Predator() {
        Predator.count.incrementAndGet();
        this.name = this.getClass().getSimpleName() + "_" + count;
    }

    /** Метод кды для хищника. В закоментированном коде можно увидеть, что туда попадают значения false*/
    @Override
    public void eat(Cell cell) {
        if (getDegreeOfSaturation() >= getFoodConsumption()) {
            return;
        }
        int difference = getFoodConsumption() - getDegreeOfSaturation();
        for (Animal animal : cell.getSetResidents()
        ) {
            if (this.isEat(animal)) {
                /*if (this.getClass().getSimpleName().equals(Bear.class.getSimpleName())) {
                    System.out.println(this.getName() + "-" + this.isEat(animal) + "-" + animal.getName());
                }*/
                if (difference > animal.getWeight()) {
                    difference = difference - animal.getWeight();
                    animal.dead(animal);
                    this.setDegreeOfSaturation(getDegreeOfSaturation() + animal.getWeight());
                } else {
                    setDegreeOfSaturation(getFoodConsumption());
                    animal.dead(animal);
                    break;
                }
            }
        }
    }

    public static AtomicInteger getCount() {
        return count;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean isAive() {
        return isAive;
    }

    @Override
    public void setAive(boolean aive) {
        isAive = aive;
    }

    public static void setCount(AtomicInteger count) {
        Predator.count = count;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
    }


    @Override
    public int getFoodConsumption() {
        return foodConsumption;
    }

    @Override
    public void setFoodConsumption(int foodConsumption) {
        this.foodConsumption = foodConsumption;
    }

    @Override
    public int getLongevity() {
        return longevity;
    }

    @Override
    public void setLongevity(int longevity) {
        this.longevity = longevity;
    }

    @Override
    public String getIcon() {
        return icon;
    }

    @Override
    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public <T extends Animal> void dead(T animal) {
        super.dead(animal);
        Predator.count.decrementAndGet();
        animal.setAive(false);
//        System.out.println("Predator dead() - " + T.getCount() + animal.getClass().getSimpleName());
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


}
