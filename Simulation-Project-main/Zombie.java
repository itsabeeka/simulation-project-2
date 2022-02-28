import java.util.*;

/**
 * A simple model of a Zombie which is a subclass of Predator class
 * Zombies age, move, sleep, hunt prey, die and spread diseases.
 *
 * @author David J. Barnes and Michael Kölling,
 * and Areeba Safdar(K20045738) and Sabeeka Ahmad(K20012890)
 * @version 2016.02.29 (2)
 */
public class Zombie extends Predator {
    // Characteristics shared by all zombies (class variables).

    //The energy points if a zombie is eaten
    private static final int FOOD_LEVEL = 10;
    // The age at which a zombie can start to breed.
    private static final int BREEDING_AGE = 20;
    // The age to which a zombie can live.
    private static final int MAX_AGE = 100;
    // The likelihood of a zombie breeding.
    private static final double BREEDING_PROBABILITY = 0.1;
    // The maximum number of births.
    private static final int BIRTH_LIMIT = 3;
    //The energy level of a baby zombie
    private static final int NEW_BORN_ENERGY_LEVEL = 30;
    //The maximum energy a zombie can have
    private static final int MAX_ENERGY_LEVEL = 50;
    //Determines if the zombies sleep at night
    private static final boolean DOES_SLEEP = false;


    /**
     * Create a Zombie and add the preys they hunt to the hashmap
     *
     * @param randomAge If true, the zombie will have random age and energy level.
     * @param field     The field currently occupied.
     * @param location  The location within the field.
     */
    public Zombie(boolean randomAge, Field field, Location location) {
        super(randomAge, field, location);

        addPrey(Human.class);
    }

    /**
     * @return the age at which zombies can breed
     */
    public int getBreedingAge() {
        return BREEDING_AGE;
    }

    /**
     * @return the maximum age zombies can live until
     */
    public int getMaxAge() {
        return MAX_AGE;
    }

    /**
     * @return the probability of a zombie breeding
     */
    public double getBreedingProbability() {
        return BREEDING_PROBABILITY;
    }

    /**
     * @return the maximum energy a zombie can have
     */
    public int getMaxEnergyLevel() {
        return MAX_ENERGY_LEVEL;
    }

    /**
     * @return the maximum births a zombie can give at a time
     */
    public int getBirthLimit() {
        return BIRTH_LIMIT;
    }

    /**
     * @return the energy level of a newborn zombie
     */
    public int getNewBornEnergyLevel() {
        return NEW_BORN_ENERGY_LEVEL;
    }

    /**
     * @retrun if zombies sleep
     */
    public boolean getDoesSleep() {
        return DOES_SLEEP;
    }

    /**
     * @retrun the food level of zombies
     */
    public int getFoodLevel() {
        return FOOD_LEVEL;
    }


    /**
     * Creates a newBorn zombie, with randomAge set to false. Allowing the
     * dragon to have a newBorn energy level and age 0.
     * Adds the new zombie to the list, which will be used in simulator
     * to store all new babies with existing species
     *
     * @param newZombies The list which contains new species
     * @param field      The grid in which the zombie will be born
     * @param loc        The location of the new zombie's birth
     */
    public void addBaby(List<Species> newZombies, Field field, Location loc) {
        Zombie young = new Zombie(false, field, loc);
        //Adding new dragons, which is referencing newAnimals in Simulator
        newZombies.add(young);
    }

}
