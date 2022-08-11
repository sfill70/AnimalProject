package com.example.animalproject.app.land;

import com.example.animalproject.app.land.resident.Animal;
import com.example.animalproject.app.land.resident.herbivore.*;
import com.example.animalproject.app.land.resident.predator.*;

import java.util.concurrent.ConcurrentHashMap;

public class UtilAnimal {
    private static final ConcurrentHashMap<Class<?>, Integer> mapAmountAnimal = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Animal, Integer> mapAnimal = new ConcurrentHashMap<>();

    private static final ConcurrentHashMap<Class<?>, Integer> mapSpeedAnimal = new ConcurrentHashMap<>();

    private static final ConcurrentHashMap<Class<?>, Integer> abilityToReproduce = new ConcurrentHashMap<>();

    /**
     * Нужен для заполнения Map из статики животных
     */
    public UtilAnimal() {
        new Wolf().decrement();
        new Fox().decrement();
        new Eagle().decrement();
        new Boa().decrement();
        new Bear().decrement();
        new Sheep().decrement();
        new Rabbit().decrement();
        new Mouse().decrement();
        new Horse().decrement();
        new Goat().decrement();
        new Duck().decrement();
        new Deer().decrement();
        new Caterpillar().decrement();
        new Buffalo().decrement();
        new Boar().decrement();
    }

    public static void putMapAmountAnimal(Class<?> clazz, int amount) {
        mapAmountAnimal.put(clazz, amount);
    }

    public ConcurrentHashMap<Class<?>, Integer> getMapAmountAnimal() {
        return mapAmountAnimal;
    }

    public static void putSpeedAnimal(Class<?> clazz, int speed) {
        mapSpeedAnimal.put(clazz, speed);
    }

    public static void putAbilityToReproduce(Class<?> clazz, int ability) {
        abilityToReproduce.put(clazz, ability);
    }

    public static ConcurrentHashMap<Class<?>, Integer> getMapSpeedAnimal() {
        return mapSpeedAnimal;
    }

    public static ConcurrentHashMap<Class<?>, Integer> getAbilityToReproduce() {
        return abilityToReproduce;
    }

    public ConcurrentHashMap<Animal, Integer> getMapAnimal() {
        return mapAnimal;
    }

    public void setMapAnimal(ConcurrentHashMap<Animal, Integer> mapAnimal) {
        this.mapAnimal = mapAnimal;
    }

    public <T extends Animal> boolean isNotFull(T animal, int amount) {
        return amount < mapAmountAnimal.get(animal.getClass());
    }

    public static String getStatistic() {
        String st = Animal.class.getSimpleName() + "=" + Animal.getCount() + ", " + System.lineSeparator() +
                Predator.class.getSimpleName() + "=" + Predator.getCount() + ", " + System.lineSeparator() +
                Herbivore.class.getSimpleName() + "=" + Herbivore.getCount() + ", " + System.lineSeparator() +
                Wolf.class.getSimpleName() + "=" + Wolf.getCount() + ", " + System.lineSeparator() +
                Fox.class.getSimpleName() + "=" + Fox.getCount() + ", " + System.lineSeparator() +
                Eagle.class.getSimpleName() + "=" + Eagle.getCount() + ", " + System.lineSeparator() +
                Boa.class.getSimpleName() + "=" + Boa.getCount() + ", " + System.lineSeparator() +
                Bear.class.getSimpleName() + "=" + Bear.getCount() + ", " + System.lineSeparator() +
                Sheep.class.getSimpleName() + "=" + Sheep.getCount() + ", " + System.lineSeparator() +
                Rabbit.class.getSimpleName() + "=" + Rabbit.getCount() + ", " + System.lineSeparator() +
                Mouse.class.getSimpleName() + "=" + Mouse.getCount() + ", " + System.lineSeparator() +
                Horse.class.getSimpleName() + "=" + Horse.getCount() + ", " + System.lineSeparator() +
                Goat.class.getSimpleName() + "=" + Goat.getCount() + ", " + System.lineSeparator() +
                Duck.class.getSimpleName() + "=" + Duck.getCount() + ", " + System.lineSeparator() +
                Deer.class.getSimpleName() + "=" + Deer.getCount() + ", " + System.lineSeparator() +
                Caterpillar.class.getSimpleName() + "=" + Caterpillar.getCount() + ", " + System.lineSeparator() +
                Buffalo.class.getSimpleName() + "=" + Buffalo.getCount() + ", " + System.lineSeparator() +
                Boar.class.getSimpleName() + "=" + Boar.getCount() + ", " + System.lineSeparator();
        return st;
    }

}
