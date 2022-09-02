package com.example.animalproject.app.land;

import com.example.animalproject.app.land.resident.Animal;
import com.example.animalproject.app.land.resident.herbivore.*;
import com.example.animalproject.app.land.resident.predator.*;

import java.util.concurrent.ConcurrentHashMap;

public class UtilAnimal {
    private static final ConcurrentHashMap<Class<?>, Integer> mapAmountAnimal = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Class<?>, Integer> mapSpeedAnimal = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Class<?>, Integer> abilityToReproduce = new ConcurrentHashMap<>();

    /**
     * Нужен для заполнения Map из статики животных,
     */
    static {
        new Wolf();
        new Fox();
        new Eagle();
        new Boa();
        new Bear();
        new Sheep();
        new Rabbit();
        new Mouse();
        new Horse();
        new Goat();
        new Duck();
        new Deer();
        new Caterpillar();
        new Buffalo();
        new Boar();
        new Chupacabra();
    }

    public static void putMapAmountAnimal(Class<?> clazz, int amount) {
        mapAmountAnimal.put(clazz, amount);
    }

    public static ConcurrentHashMap<Class<?>, Integer> getMapAmountAnimal() {
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

    public static <T extends Animal> boolean isNotFull(T animal, int amount) {
        return amount < mapAmountAnimal.get(animal.getClass());
    }

}
