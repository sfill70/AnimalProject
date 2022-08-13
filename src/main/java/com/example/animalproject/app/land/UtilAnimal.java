package com.example.animalproject.app.land;

import com.example.animalproject.app.land.resident.Animal;
import com.example.animalproject.app.land.resident.herbivore.*;
import com.example.animalproject.app.land.resident.predator.*;
import io.github.classgraph.*;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class UtilAnimal {
    private static String root = System.getProperty("user.dir");
    private static final String CLAZZ_PATH = String.join(File.separator, root, "src",
            "main","java","com","example","animalproject","app","land","resident");
    private static final ConcurrentHashMap<Class<?>, Integer> mapAmountAnimal = new ConcurrentHashMap<>();

    private static final ConcurrentHashMap<Class<?>, Integer> mapSpeedAnimal = new ConcurrentHashMap<>();

    private static final ConcurrentHashMap<Class<?>, Integer> abilityToReproduce = new ConcurrentHashMap<>();

    /**
     * Нужен для заполнения Map из статики животных
     */
    private static void initClazz() {
        System.out.println("init");
        try (ScanResult scanResult = new ClassGraph()
                .whitelistPackages(CLAZZ_PATH)      // Сканирует пакет javax.persistence и все его подпакеты
                .scan()) {
            for (ClassInfo classInfo : scanResult.getAllClasses()) {
                System.out.println(classInfo.getName());
            }
        }
    }

   /* private static List<Class<?>> getAllClassesFrom(String packageName) {
        return new Reflections(packageName, new SubTypesScanner(false))
                .getAllTypes()
                .stream()
                .map(name -> {
                    try {
                        return Class.forName(name);
                    } catch (ClassNotFoundException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }*/
    /**
     * Нужен для заполнения Map из статики животных
     */
    static {
        System.out.println(CLAZZ_PATH);
        initClazz();
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
