import java.util.*;

/**
 * A simple model of a Dragon which is a subclass of Predator class
 * Dragons age, move, sleep, hunt prey, die and spread diseases.
 *
 * @author David J. Barnes and Michael Kölling,
 * and Areeba Safdar(K20045738) and Sabeeka Ahmad(K20012890)
 * @version 2016.02.29 (2)
 */
public class Dragon extends Predator {
    // Characteristics shared by all dragons (class variables).

    // The age at which a dragon can start to breed.
    private static final int BREEDING_AGE = 15;
    // The age to which a dragon can live.
    private static final int MAX_AGE = 300;
    // The likelihood of a dragon breeding.
    private static final double BREEDING_PROBABILITY = 0.09;
    // The maximum number of births.
    private static final int BIRTH_LIMIT = 3;
    //The energy level of a baby dragon
    private static final int NEW_BORN_ENERGY_LEVEL = 20;
    // The maximum energy level a dragon can have.
    private static final int MAX_ENERGY_LEVEL = 100;
    // Determines if dragons sleep at night
    private static final boolean DOES_SLEEP = true;
    //The energy points if a dragon is eaten
    private static final int FOOD_LEVEL = 50;


    /**
     * Create a Dragon and add the preys they hunt to the hashmap
     *
     * @param randomAge If true, the dragon will have random age and energy level.
     * @param field     The field currently occupied.
     * @param location  The location within the field.
     */
    public Dragon(boolean randomAge, Field field, Location location) {
        super(randomAge, field, location);

        //Adding preys for dragons
        // addPrey(Human.class);
        addPrey(Zombie.class);
        addPrey(HellHound.class);
    }

    /**
     * @return the age at which dragons can breed
     */
    public int getBreedingAge() {
        return BREEDING_AGE;
    }

    /**
     * @return the maximum age dragons can live until
     */
    public int getMaxAge() {
        return MAX_AGE;
    }

    /**
     * @return the probability of a dragon breeding
     */
    public double getBreedingProbability() {
        return BREEDING_PROBABILITY;
    }

    /**
     * @return the maximum energy a dragon can have
     */
    public int getMaxEnergyLevel() {
        return MAX_ENERGY_LEVEL;
    }

    /**
     * @return the maximum births a dragon can give at a time
     */
    public int getBirthLimit() {
        return BIRTH_LIMIT;
    }

    /**
     * @return the energy level of a newborn dragon
     */
    public int getNewBornEnergyLevel() {
        return NEW_BORN_ENERGY_LEVEL;
    }

    /**
     * @retrun if dragons sleep
     */
    public boolean getDoesSleep() {
        return DOES_SLEEP;
    }

    /**
     * @retrun the food level of dragons
     */
    public int getFoodLevel() {
        return FOOD_LEVEL;
    }


    /**
     * Creates a newBorn dragon, with randomAge set to false. Allowing the
     * dragon to have a newBorn energy level and age 0.
     * Adds the new dragon to the list, which will be used in simulator
     * to store all new babies with existing species
     *
     * @param newDragons The list which contains new species
     * @param field      The grid in which the dragon will be born
     * @param loc        The location of the new dragon's birth
     */
    public void addBaby(List<Species> newDragons, Field field, Location loc) {
        Dragon young = new Dragon(false, field, loc);
        //Adding new dragons, which is referencing newSpecies in Simulator
        newDragons.add(young);
    }

}
