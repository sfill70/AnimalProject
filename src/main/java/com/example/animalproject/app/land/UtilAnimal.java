package com.example.animalproject.app.land;

import com.example.animalproject.app.land.resident.Animal;
import com.example.animalproject.app.land.resident.herbivore.Herbivore;
import com.example.animalproject.app.land.resident.predator.Predator;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

//import org.reflections.Reflections;

public class UtilAnimal {
    private static final String root = System.getProperty("user.dir");
    private static final String CLAZZ_PATH = String.join(File.separator, root, "src",
            "main", "java", "com", "example", "animalproject", "app", "land", "resident");
    private static final String animalPath = String.join(File.separator, root, "src",
            "main", "java", "com", "example", "animalproject", "app", "land", "resident", "Animal.java");
    private static final ConcurrentHashMap<Class<?>, Integer> mapAmountAnimal = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Class<?>, Integer> mapSpeedAnimal = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Class<?>, Integer> abilityToReproduce = new ConcurrentHashMap<>();

    /**
     * Нужен для заполнения Map из статики животных, закоментированный медод
     * для работы с библиотекой import org.reflections.Reflections
     * закоментирован в низу
     */
    static {
        try {
            animalsInitialization();

//            animalsInitializationClass();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
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

    /**
     * Создает по одному экземнляру животного для инициализации из статики
     */
    private static void animalsInitialization() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<String> listClazzName = getListClazzName();
        String baseClazzName = Animal.class.getName().replace(".Animal", "");
        for (String clazzName : listClazzName
        ) {
            initializationAnimal(baseClazzName + clazzName);
        }
    }

    /**
     * Тут после сборки наверно надо собирать не java файлы а class но примерно так
     */
    private static List<String> getListClazzName() {
        List<String> result = null;
        try (Stream<Path> walk = Files.walk(Paths.get(CLAZZ_PATH))) {
            result = walk.filter(Files::isRegularFile).filter(path -> !path.endsWith("Animal.java")).
                    filter(path -> !path.endsWith("Herbivore.java")).filter(path -> !path.endsWith("Predator.java"))
                    .map(path -> Paths.get(animalPath).relativize(path)).map(x -> x.toString()
                            .replace(".java", "").replace(".", "")
                            .replace(File.separator, ".")).toList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static void initializationAnimal(String clazzName) throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clazz = Class.forName(clazzName);
        Constructor<?> animalConstructor = clazz.getConstructor();
        Animal animalAny = (Animal) animalConstructor.newInstance();
    }

    /**
     * Методы для работы с библиотекой - import org.reflections.Reflections
     * */
    /*private static void animalsInitializationClass() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Set<Class<? extends Animal>> setClazz = getSetClazz();
        for (Class<? extends Animal> clazzName : setClazz
        ) {
            initializationAnimalFoClass(clazzName);
        }
    }

    private static Set<Class<? extends Animal>> getSetClazz() {
        Reflections reflections = new Reflections("com.example.animalproject");
        Set<Class<? extends Animal>> classes = reflections.getSubTypesOf(Animal.class);
        return classes;
    }

    static void initializationAnimalFoClass(Class<? extends Animal> animalClazz) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        if (animalClazz.equals(Animal.class) || animalClazz.equals(Predator.class) || animalClazz.equals(Herbivore.class)) {
            return;
        }
        Class<? extends Animal> clazz = animalClazz;
        Constructor<?> animalConstructor = clazz.getConstructor();
        Animal animalAny = (Animal) animalConstructor.newInstance();
    }*/
}
