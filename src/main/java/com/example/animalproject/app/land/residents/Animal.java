package com.example.animalproject.app.land.residents;

import com.example.animalproject.PlayingField;
import com.example.animalproject.app.land.Cell;
import com.example.animalproject.app.land.UtilAnimal;
import com.example.animalproject.app.land.residents.predator.Bear;
import com.example.animalproject.app.land.residents.predator.Predator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Animal implements Comparable<Animal> {
    boolean isAive = true;
    boolean isMove = true;
    /**
     * общее количество животных данного вида
     */
    static volatile AtomicInteger count = new AtomicInteger(0);
    public int weight;
    public int speed;

    /**
     * потребление пищи
     */
    public int foodConsumption;
    /**
     * степень насыщения
     */
    public int degreeOfSaturation = 0;

    /**
     * Срок жизни
     */
    public int longevity;
    public String icon;
    public String name;
    public Cell cell;
    public Animal animal = this;
    public int sizeX = PlayingField.getSizeX();
    public int sizeY = PlayingField.getSizeY();

    /**
     * Map для хранения Шанс съесть животное
     */
//    public static ConcurrentHashMap<String, Integer> chanceSuccessfulHunt = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Class<?>, Integer> chanceSuccessfulHunt = new ConcurrentHashMap<>();


    public Animal() {
        count.incrementAndGet();
        this.name = animal.getClass().getSimpleName() + "-" + count;
    }

    /**
     * Метод получения координат, при выходе за край острова, переносит координату
     * на противоположную сторону игрового поля, то есть получаем шар
     */
    public static int getCoordinates(int x, int size) {
        x = Math.abs(size + x) % size;
        return x;
    }


    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {


    }

    /**
     * Метод получения доступных локаций на расстоянии speed от текщей локации,
     * квадратная канва из локаций размером = speed+1, возвращает отсортированный по food в порядке убывания
     * List.
     * Не используется
     */
    public List<Cell> getListCellToMoves(int speed) {
        Comparator<Cell> cellComparator = (o1, o2) -> Integer.compare(o2.getFood().get(),
                o1.getFood().get());
        List<Cell> cellList = new ArrayList<>();
        Cell cell = this.cell;
        int x = cell.getIntX();
        int y = cell.getIntY();
        for (int i = x - speed; i <= x + speed; i++) {
            cellList.add(PlayingField.getIsland().getArrayCell()[getCoordinates(i, sizeY)][getCoordinates(x - speed, sizeX)]);
            cellList.add(PlayingField.getIsland().getArrayCell()[getCoordinates(i, sizeY)][getCoordinates(x + speed, sizeX)]);
        }
        for (int j = y + 1 - speed; j <= y - 1 + speed; j++) {
            cellList.add(PlayingField.getIsland().getArrayCell()[getCoordinates(y - speed, sizeY)][getCoordinates(j, sizeX)]);
            cellList.add(PlayingField.getIsland().getArrayCell()[getCoordinates(y + speed, sizeY)][getCoordinates(j, sizeX)]);
        }
        cellList.remove(cell);
        cellList.sort(cellComparator);
        return cellList;
    }

    /**
     * Метод получения окружающих Локации нужен если реализовать многоходовый режим животного
     * возвращает отсортированный по food List (сортировка по убыванию).
     * Используется!.
     */
    public List<Cell> getListCellToMoves(Cell previousCell) {

        Comparator<Cell> cellComparator = new Comparator<Cell>() {
            @Override
            public int compare(Cell o1, Cell o2) {
                return Integer.compare(o2.getFood().get(), o1.getFood().get());
            }
        };
        List<Cell> cellList = new ArrayList<>();
        int x = previousCell.getIntX();
        int y = previousCell.getIntY();
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                cellList.add(PlayingField.getIsland().getArrayCell()[getCoordinates(i, sizeY)][getCoordinates(j, sizeX)]);
            }
        }
        cellList.remove(this.getCell());
        if (cellList.contains(this.getCell())) {
            cellList.remove(this.getCell());
        }
        cellList.sort(cellComparator);
        return cellList;
    }

    /**
     * Животное попадает в локацию со свободными местами и самым большим количеством food,
     * если наелось остается, если голодное идет дальше, если свободных локаций в близи нет,
     * остается на месте. Для хищников не переопределялся, где больше травоядных лучше охотиться
     */
    public void move() {
        int speed = UtilAnimal.getMapSpeedAnimal().get(this.getClass());
        if (speed == 0) {
            eat(animal.getCell());
            return;
        }
        Cell currentCell = this.getCell();
        Cell nextCell = null;
        for (int i = 0; i <= speed; i++) {
            List<Cell> listCellToMoves = getListCellToMoves(currentCell);
            for (Cell cell : listCellToMoves) {
                if (cell.isNotFull(this)) {
                    nextCell = cell;
                    currentCell = nextCell;
                    break;
                }
            }
            eat(currentCell);
            if (getDegreeOfSaturation() >= getFoodConsumption()) {
                break;
            }
        }
        if (nextCell != null) {
            this.cell.remove(this);
            this.setCell(nextCell);
            nextCell.add(this);
            this.isMove = false;
        }
    }

    /**
     * Метод еды Травоядных, у Хищников переопределен
     */
    public void eat(Cell cell) {
        if (getDegreeOfSaturation() >= getFoodConsumption()) {
            return;
        }
        int difference = getFoodConsumption() - getDegreeOfSaturation();
        if (this.cell.getFood().get() >= difference) {
            this.cell.setFood(this.cell.getFood().get() - difference);
            setDegreeOfSaturation(getFoodConsumption());
        } else {
            setDegreeOfSaturation(getDegreeOfSaturation() + this.cell.getFood().get());
            this.cell.setFood(0);
        }
    }

    /**
     * Шанс удачной охоты на полученное животное
     */
    public Integer chanceHunt(Animal animal) {
        int chance = -1;
        if (chanceSuccessfulHunt.containsKey(animal.getClass())) {
            chance = chanceSuccessfulHunt.get(animal.getClass());
        }
        return chance;
    }

    /**
     * Удалось съесть животное или нет
     */
    public boolean isEat(Animal animal) {
        int chance = chanceHunt(animal);
        int percent = ThreadLocalRandom.current().nextInt(0, 100);
        return chance >= percent;
    }

    /**
     * Базовый вариант создания животного. Не используется
     */
    public <T extends Animal> T reproduce() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        Constructor<?> animalConstructor = animal.getClass().getConstructor();
        return (T) animalConstructor.newInstance();
    }

    /**
     * Создание животного и добавление его в локацию
     */
    public <T extends Animal> T reproduce(T animal) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?> animalConstructor = animal.getClass().getConstructor();
        T animalAny = (T) animalConstructor.newInstance();
        animalAny.setCell(animal.getCell());
        animal.getCell().add(animalAny);
        return animalAny;
    }

    /**
     * Другой вариант создания животного. Реализован в наследниках.
     * Используется при размножении.
     */
    public abstract Animal madeNewAnimal();

    public void decrement() {
    }

    public static AtomicInteger getCount() {
        return count;
    }

    public static void decrementCount() {
        Animal.count.decrementAndGet();
    }


    public <T extends Animal> void dead(T animal) {
        Animal.count.decrementAndGet();
        animal.getCell().remove(animal);
    }

    public int getDegreeOfSaturation() {
        return degreeOfSaturation;
    }

    public void setDegreeOfSaturation(int degreeOfSaturation) {
        this.degreeOfSaturation = degreeOfSaturation;
    }

    public boolean isAive() {
        return isAive;
    }

    public void setAive(boolean aive) {
        isAive = aive;
    }

    public boolean isMove() {
        return isMove;
    }

    public void setMove(boolean move) {
        isMove = move;
    }

    public static void setCount(AtomicInteger count) {
        Animal.count = count;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getFoodConsumption() {
        return foodConsumption;
    }

    public void setFoodConsumption(int foodConsumption) {
        this.foodConsumption = foodConsumption;
    }

    public int getLongevity() {
        return longevity;
    }

    public void setLongevity(int longevity) {
        this.longevity = longevity;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountAnimal() {
        return count.get();
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    @Override
    public boolean equals(Object o) {
        Animal an = (Animal) o;
        if (this == o) return true;
        if (o == null) return false;
        if (weight != an.weight) return false;
        return name.equals(an.name);
    }

    @Override
    public int hashCode() {
        int result = weight;
        result = 31 * result + name.hashCode();
        return result;
    }



    @Override
    public String toString() {
        return animal.getClass().getSimpleName() + " {" + "icon='" + icon + '\'' + ", name='" + name + '\'' + '}';
    }

    @Override
    public int compareTo(Animal o) {
        int result = Integer.compare(this.hashCode(), o.hashCode());
        return result;
    }

    /*  @Override
    public int hashCode() {
        int result = weight ;
        result = 31 * result + foodConsumption;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;}*/
}
