package com.example.animalproject.app.land;

import javafx.scene.shape.Rectangle;
import com.example.animalproject.app.land.resident.Animal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Cell extends Rectangle {
    int intX;
    int intY;
    String lineSeparator = System.lineSeparator();
    /**
     * Вес растений (корм для Травоядных)
     */
    volatile AtomicInteger foodHerbivore;
    /**
     * Остатки травоядных после съедания хищником (падаль)
     */
    volatile AtomicInteger carrion;

    /**
     * Общий вес Травоядных
     */
    int weightHerbivores;
    final static UtilAnimal UTIL_ANIMAL = new UtilAnimal();

    private final ConcurrentHashMap<String, Integer> mapResidents = new ConcurrentHashMap<>();
    /**
     * Map количество особей каждого вида
     */
    private final ConcurrentHashMap<Class<?>, Integer> mapCountResidents = new ConcurrentHashMap<>();

    /**
     * Спмсок всеж животных в Локации
     */
    private final Set<Animal> setResidents = ConcurrentHashMap.newKeySet();

    public Cell() {
    }

    public Cell(int x, int y) {
        this.intX = x;
        this.intY = y;
        initialization();
    }


    /**
     * Метод возвращает String информацию о Локации.
     */
    public String viewResidents() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.toString()).append(lineSeparator);
        for (Class<?> clazz : mapCountResidents.keySet()
        ) {
            sb.append(clazz.getSimpleName()).append(" = ").append(mapCountResidents.get(clazz)).append("; ").append(lineSeparator);
        }
        return sb.toString();
    }

    /**
     * Наполнение клетки животными заполняем setResidents Возможно лучьше назвать генератор
     */
    public void initialization() {
        this.foodHerbivore = new AtomicInteger(100000);
        this.carrion = new AtomicInteger(0);
        for (Class<?> animalClazz : UTIL_ANIMAL.getMapAmountAnimal().keySet()
        ) {
            try {
                Class<? extends Animal> aClass = (Class<? extends Animal>) animalClazz;
                Constructor<?> animalConstructor = aClass.getConstructor();
                Animal animalAny = (Animal) animalConstructor.newInstance();
                animalAny.setCell(this);
                this.add(animalAny);
                int amount = UTIL_ANIMAL.getMapAmountAnimal().get(animalClazz) / 4
                        * ThreadLocalRandom.current().nextInt(70, 100) / 100;
                for (int i = 0; i < amount; i++) {
                    Animal animal = animalAny.reproduce(animalAny.getCell());
                }
                IslandSingleton.addStatistic(aClass, 1 + amount);

            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /*
     * Метод удаления голодных животный из локации и размножения
     * излишняя проверка на удаление*/
    public void reproduceCellAnimal() {
        setFoodHerbivore(6000 /** 100 / ThreadLocalRandom.current().nextInt(50, 100)*/);
//        setCarrion(new AtomicInteger(0));
        setCarrion(new AtomicInteger(this.carrion.get()));
        Set<? extends Animal> animalSet = this.setResidents;
        for (Animal an : animalSet
        ) {
            if (an.getDegreeOfSaturation() <= 0) {
                an.dead(an, "Ctll ReproduceCellAnimal()_1");
            }
            if (an.isAive()) {
                an.setMove(true);
                an.setFree(true);
                an.setDegreeOfSaturation(an.degreeOfSaturation / 3);

            } else {
                this.getSetResidents().remove(an);
                an.getCell().remove(an, "clearAndReproduce() is isDead_2");
            }
        }
        for (Class<?> animalClazz : mapCountResidents.keySet()
        ) {
            try {
                Class<? extends Animal> aClass = (Class<? extends Animal>) animalClazz;
                Constructor<?> animalConstructor = aClass.getConstructor();
                Animal animalAny = (Animal) animalConstructor.newInstance();
                animalAny.setCell(this);
                animalAny.decrement();
                int amount = mapCountResidents.get(animalClazz) / 2 / UtilAnimal.getAbilityToReproduce().get(animalClazz);
                for (int i = 0; i < amount; i++) {
                    if (!this.isNotFull(animalAny)) {
                        break;
                    }
                    animalAny.reproduce(animalAny.getCell());
                }


            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int getIntX() {
        return intX;
    }

    public void setIntX(int x) {
        this.intX = x;
    }

    public int getIntY() {
        return intY;
    }

    public void setIntY(int intY) {
        this.intY = intY;
    }

    public AtomicInteger getFoodHerbivore() {
        return foodHerbivore;
    }

    public void setFoodHerbivore(int foodHerbivore) {
        this.foodHerbivore = new AtomicInteger(foodHerbivore);
    }

    public AtomicInteger getCarrion() {
        return carrion;
    }

    public void setCarrion(AtomicInteger carrion) {
        this.carrion = carrion;
    }

    public int getWeightHerbivores() {
        return weightHerbivores;
    }

    public void setWeightHerbivores(int weightHerbivores) {
        this.weightHerbivores = weightHerbivores;
    }

    /**
     * Метод добавления животного в локацию
     */
    public void add(Animal animal) {
        synchronized (setResidents) {
            animal.setCell(this);
            boolean isAdd = setResidents.add(animal);
            if (isAdd) {
                if (mapCountResidents.containsKey(animal.getClass())) {
                    mapCountResidents.put(animal.getClass(),
                            mapCountResidents.get(animal.getClass()) + 1);
                } else {
                    mapCountResidents.put(animal.getClass(), 1);
                }
                animal.setFree(true);
            }
        }
    }

    /**
     * метод удаления живонтого из локации.
     */
    public void remove(Animal animal, String st) {
        synchronized (setResidents) {
            animal.setFree(false);
            animal.setMove(false);
            boolean isRemove = setResidents.remove(animal);
            if (isRemove) {
                if (mapCountResidents.containsKey(animal.getClass())) {
                    mapCountResidents.put(animal.getClass(),
                            mapCountResidents.get(animal.getClass()) - 1);
                }
            }
        }
    }

    /**
     * метод удаления с помощью итератора не используется очень медленный
     */
    private void removeWithIterator(Animal animal) {
        Iterator<Animal> iterator = setResidents.iterator();
        while (iterator.hasNext()) {
            Animal element = iterator.next();
            if (element.equals(animal)) {
                iterator.remove();
            }
        }
    }

    public Set<Animal> getSetResidents() {
        return setResidents;
    }

    public ConcurrentHashMap<Class<?>, Integer> getMapCountResidents() {
        return mapCountResidents;
    }

    /**
     * Метод проверки свободного места в Локации для животного
     */
    public boolean isNotFull(Animal animal) {
        int amount = 0;
        if (mapCountResidents.containsKey(animal.getClass())) {
            amount = mapCountResidents.get(animal.getClass());
        }

        return UTIL_ANIMAL.isNotFull(animal, amount);
    }


    @Override
    public String toString() {
        return "Cell{" +
                "x=" + intX +
                ", y=" + intY + ", foodHerbivore = " + foodHerbivore + ", carrion = " + carrion + "} "   /*+ ", Set=" + setResidents +
                '}'*/;
    }

}
