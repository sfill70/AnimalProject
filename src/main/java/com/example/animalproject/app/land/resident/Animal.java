package com.example.animalproject.app.land.resident;

import com.example.animalproject.PlayingField;
import com.example.animalproject.app.land.Cell;
import com.example.animalproject.app.land.UtilAnimal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Animal implements Comparable<Animal> {
    boolean isAive = true;
    boolean isMove = true;
    /**
     * Переменная указывает на то, что животное в данный момент свободно для оперций с ним
     */
    boolean isFree = true;
    /**
     * общее количество животных данного вида
     * оставлено для создания быстрой общей статистики.
     */
    static private volatile AtomicInteger count = new AtomicInteger(0);
    public int weight;
    /**
     * потребление пищи
     */
    public int foodConsumption;
    /**
     * степень насыщения
     */
    public int degreeOfSaturation = 0;
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
        this.name = animal.getClass().getSimpleName() + "-" + count.get() *
                31 * ThreadLocalRandom.current().nextInt(50, 100);
    }

    /**
     * Метод получения координат, при выходе за край острова, переносит координату
     * на противоположную сторону игрового поля, то есть получаем шар
     */
    public static int getCoordinates(int x, int size) {
        x = Math.abs(size + x) % size;
        return x;
    }

    /**
     * Метод получения доступных локаций на расстоянии speed от текщей локации,
     * квадратная канва из локаций размером = speed+1, возвращает отсортированный по food в порядке убывания
     * List.
     * Не используется
     */
    public List<Cell> getListCellToMoves(int speed) {
        Comparator<Cell> cellComparator = (o1, o2) -> Integer.compare(o2.getFoodHerbivore().get(),
                o1.getFoodHerbivore().get());
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
     * возвращает отсортированный в по food List (сортировка по убыванию) или в случайном порядке.
     * Используется!.
     */
    public List<Cell> getListCellToMoves(Cell previousCell, Cell homeCell) {
        Comparator<Cell> cellComparator = new Comparator<Cell>() {
            @Override
            public int compare(Cell o1, Cell o2) {
                return Integer.compare(o2.getFoodHerbivore().get(), o1.getFoodHerbivore().get());
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
        cellList.remove(previousCell);
        cellList.remove(homeCell);
        cellList.sort(cellComparator);
        /**Закоментировать следующую строку для сортировки по убыванию*/
        Collections.shuffle(cellList);
        return cellList;
    }

    /**
     * Животное попадает в локацию со свободными местами,
     * если наелось остается, если голодное идет дальше, если свободных локаций в близи нет,
     * остается на месте. Для хищников не переопределялся.
     * Животное сразу удаляется из локации, что бы не быть захваченым другими потоками.
     * и по окончании кормления добовляется в выбранную локацию.
     * излишний контроль, в Cell add() и remove() все уже есть
     */
    public void move() {
        if (!this.isMove || !this.isAive) {
            return;
        }
        Cell homeCell = this.getCell();
        homeCell.remove(this, "Animal move Home");
        int speed = UtilAnimal.getMapSpeedAnimal().get(this.getClass());
        if (speed == 0) {
            eat(animal.getCell());
            return;
        }
        Cell currentCell = homeCell;
        Cell nextCell = null;
        for (int i = 0; i <= speed; i++) {
            List<Cell> listCellToMoves = getListCellToMoves(currentCell, homeCell);
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
            this.isMove = false;
            nextCell.add(this);
            this.isFree = true;
        } else {
            this.isMove = false;
            homeCell.add(this);
            this.isFree = true;
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
        if (this.cell.getFoodHerbivore().get() >= difference) {
            this.cell.setFoodHerbivore(this.cell.getFoodHerbivore().get() - difference);
            setDegreeOfSaturation(getFoodConsumption());
        } else {
            setDegreeOfSaturation(getDegreeOfSaturation() + this.cell.getFoodHerbivore().get());
            this.cell.setFoodHerbivore(0);
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

    public <T extends Animal> T reproduce(Cell cell) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?> animalConstructor = animal.getClass().getConstructor();
        T animalAny = (T) animalConstructor.newInstance();
        animalAny.setCell(animal.getCell());
        animal.getCell().add(animalAny);
        return animalAny;
    }

    /**
     * Другой вариант создания животного. Реализован в наследниках.
     * Не используется
     */
    public abstract Animal madeNewAnimal();

    /**
     * Смерть животного удаляет животное из локации
     * String method в аргументах метода, для будущего логирования и удобства настройки
     */
    public <T extends Animal> void dead(T animal, String method) {
        isAive = false;
        isMove = false;
        Animal.count.decrementAndGet();
        animal.getCell().remove(animal, "Animal dead() ");
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    /**
     * уменьшение обшего количества животных данного вида
     * оставлено для создания быстрой общей статистики.
     */
    public void decrement() {
    }

    public static AtomicInteger getCount() {
        return count;
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

    public int getFoodConsumption() {
        return foodConsumption;
    }

    public void setFoodConsumption(int foodConsumption) {
        this.foodConsumption = foodConsumption;
    }

    public String getName() {
        return name;
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

    /*
     * Требуется имплиметация Comparable для возможности использования коллекций ConcurrentSet <Animal> */
    @Override
    public int compareTo(Animal o) {
        return Integer.compare(this.hashCode(), o.hashCode());
    }

}
