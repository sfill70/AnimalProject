package com.example.animalproject.app.land;

import javafx.scene.shape.Rectangle;
import com.example.animalproject.app.land.residents.Animal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Cell extends Rectangle {
    int intX;
    int intY;
    String lineSeparator = System.lineSeparator();
    /**
     * Вес растений (корм для Травоядных)
     */
    volatile AtomicInteger food;

    /**
     * Общий вес Травоядных
     */
    int weightHerbivores;
    final static UtilAnimal UTIL_ANIMAL = new UtilAnimal();

    public final ConcurrentHashMap<String, Integer> mapResidents = new ConcurrentHashMap<>();
    /**
     * Map количество особей каждого вида
     */
    public ConcurrentHashMap<Class<?>, Integer> mapCountResidents = new ConcurrentHashMap<>();

    /**
     * Спмсок всеж животных в Локации
     */
    static final Set<Animal> setResidents = ConcurrentHashMap.newKeySet();

    public Cell() {
    }

    public Cell(int x, int y) {
        this.intX = x;
        this.intY = y;
        initialization();
    }


    /**
     * Метод возвращает String информацию о Локации. Подгружается в JavaFX при инициализации потому,
     * что выводит информацию при клике на Локацию?
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
        this.food = new AtomicInteger(3000);
        for (Class<?> animalClazz : UTIL_ANIMAL.getMapAmountAnimal().keySet()
        ) {
            try {
                Class<? extends Animal> aClass = (Class<? extends Animal>) animalClazz;
                Constructor<?> animalConstructor = aClass.getConstructor();
                Animal animalAny = (Animal) animalConstructor.newInstance();
                animalAny.setCell(this);
                this.add(animalAny);
                int amount = UTIL_ANIMAL.getMapAmountAnimal().get(animalClazz) / 6
                        * ThreadLocalRandom.current().nextInt(70, 100) / 100;
                for (int i = 0; i < amount; i++) {
                    animalAny.reproduce(animalAny);
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

    public void reproduceCellAnimal() {
        for (Class<?> animalClazz : mapCountResidents.keySet()
        ) {
            try {
                Class<? extends Animal> aClass = (Class<? extends Animal>) animalClazz;
                Constructor<?> animalConstructor = aClass.getConstructor();
                Animal animalAny = (Animal) animalConstructor.newInstance();
                animalAny.decrement();
                animalAny.setCell(this);
                int amount = mapCountResidents.get(animalClazz) / 2 / UtilAnimal.getAbilityToReproduce().get(animalClazz);
                for (int i = 0; i < amount; i++) {
                    animalAny.reproduce(animalAny);
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

    public AtomicInteger getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = new AtomicInteger(food);
    }

    public int getWeightHerbivores() {
        return weightHerbivores;
    }

    public void setWeightHerbivores(int weightHerbivores) {
        this.weightHerbivores = weightHerbivores;
    }


    public void add(Animal animal) {
        synchronized (setResidents) {
            animal.setCell(this);
            setResidents.add(animal);
            if (mapCountResidents.containsKey(animal.getClass())) {
                mapCountResidents.put(animal.getClass(),
                        mapCountResidents.get(animal.getClass()) + 1);
            } else {
                mapCountResidents.put(animal.getClass(), 1);
            }
        }
    }

    /**
     * метод удаления живонтого из локации. В закоментированном коде можно увидеть, что животное не всегда
     * удаляется, особенно в многопотоке, переписал 100500 hashCode и equals .
     */
    public void remove(Animal animal) {
        synchronized (setResidents) {
            setResidents.remove(animal);
            if (mapCountResidents.containsKey(animal.getClass())) {
                mapCountResidents.put(animal.getClass(),
                        mapCountResidents.get(animal.getClass()) - 1);
            }
        }
       /* if (setResidents.contains(animal)) {
                System.out.print("Cell remove !!!!" + animal.getName() + animal.equals(animal) + " / ");
            removeWithIterator(animal);
                System.out.println(setResidents.contains(animal) + "-" + animal.getName());
        }*/
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

    public void setMapCountResidents(ConcurrentHashMap<Class<?>, Integer> mapCountResidents) {
        this.mapCountResidents = mapCountResidents;
    }

    /**
     * Метод проверки свободного места в Локации для животного
     */
    public boolean isNotFull(Animal animal) {
        int amount = 0;
        if (mapCountResidents.containsKey(animal.getClass())) {
            amount = mapCountResidents.get(animal.getClass());
        }
        UTIL_ANIMAL.isNotFull(animal, amount);
        return UTIL_ANIMAL.isNotFull(animal, amount);
    }


    @Override
    public String toString() {
        return "Cell{" +
                "x=" + intX +
                ", y=" + intY + ", food = " + food + "} "   /*+ ", Set=" + setResidents +
                '}'*/;
    }

}
