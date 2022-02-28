import java.util.*;

/**
 * A simple model of a hellhound which is a subclass of Predator class
 * Hellhounds age, move, sleep, hunt prey, die and spread diseases.
 *
 * @author David J. Barnes and Michael Kölling,
 * and Areeba Safdar(K20045738) and Sabeeka Ahmad(K20012890)
 * @version 2016.02.29 (2)
 */
public class HellHound extends Predator {
    // Characteristics shared by all dragons (class variables).

    //The energy points if a hellhound is eaten
    private static final int FOOD_LEVEL = 10;
    // The age at which a hellhound can start to breed.
    private static final int BREEDING_AGE = 6;
    // The age to which a hellhound can live.
    private static final int MAX_AGE = 200;
    // The likelihood of a hellhound breeding.
    private static final double BREEDING_PROBABILITY = 0.16;
    // The maximum number of births.
    private static final int BIRTH_LIMIT = 4;
    //The energy level of a baby hellhound
    private static final int NEW_BORN_ENERGY_LEVEL = 10;
    //The maximum energy a hellhound can have
    private static final int MAX_ENERGY_LEVEL = 50;
    //Determines if the hellhounds sleep at night
    private static final boolean DOES_SLEEP = false;


    /**
     * Create a hellhound and add the preys they hunt to the hashmap
     *
     * @param randomAge If true, the zombie will have random age and energy level.
     * @param field     The field currently occupied.
     * @param location  The location within the field.
     */
    public HellHound(boolean randomAge, Field field, Location location) {
        super(randomAge, field, location);

        addPrey(Zombie.class);
    }

    /**
     * @return the age at which hellhounds can breed
     */
    public int getBreedingAge() {
        return BREEDING_AGE;
    }

    /**
     * @return the maximum age hellhounds can live until
     */
    public int getMaxAge() {
        return MAX_AGE;
    }

    /**
     * @return the probability of a hellhound breeding
     */
    public double getBreedingProbability() {
        return BREEDING_PROBABILITY;
    }

    /**
     * @return the maximum energy a hellhound can have
     */
    public int getMaxEnergyLevel() {
        return MAX_ENERGY_LEVEL;
    }

    /**
     * @return the maximum births a hellhound can give at a time
     */
    public int getBirthLimit() {
        return BIRTH_LIMIT;
    }

    /**
     * @return the energy level of a newborn hellhound
     */
    public int getNewBornEnergyLevel() {
        return NEW_BORN_ENERGY_LEVEL;
    }

    /**
     * @retrun if hellhound sleep
     */
    public boolean getDoesSleep() {
        return DOES_SLEEP;
    }

    /**
     * @retrun the food level of hellhounds
     */
    public int getFoodLevel() {
        return FOOD_LEVEL;
    }

    /**
     * Creates a newBorn hellhound, with randomAge set to false. Allowing the
     * hellhound to have a newBorn energy level and age 0.
     * Adds the new hellhound to the list, which will be used in simulator
     * to store all new babies with existing species
     *
     * @param newHellHounds The list which contains new species
     * @param field         The grid in which the zombie will be born
     * @param loc           The location of the new zombie's birth
     */
    public void addBaby(List<Species> newHellHounds, Field field, Location loc) {
        HellHound young = new HellHound(false, field, loc);
        //Adding new dragons, which is referencing newAnimals in Simulator
        newHellHounds.add(young);
    }

}
