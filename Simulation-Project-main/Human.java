import java.util.List;
import java.util.Random;

/**
 * A simple model of a Human which is a subclass of Predator class
 * Humans age, move, sleep, eat plants, die and spread diseases.
 *
 * @author David J. Barnes and Michael Kölling,
 * and Areeba Safdar(K20045738) and Sabeeka Ahmad(K20012890)
 * @version 2016.02.29 (2)
 */
public class Human extends Predator {
    // Characteristics shared by all humans (class variables).

    //The energy points if a human is eaten
    private static final int FOOD_LEVEL = 15;
    // The age at which humans can conceive
    private static final int BREEDING_AGE = 20;
    // The age to which a human lives up to
    private static final int MAX_AGE = 80;
    // The likelihood of having a child.
    private static final double BREEDING_PROBABILITY = 0.2;
    // The maximum number of births.
    private static final int BIRTH_LIMIT = 2;
    //The energy level of a new-born child
    private static final int NEW_BORN_ENERGY_LEVEL = 1;
    //The maximum energy level a human can have
    private static final int MAX_ENERGY_LEVEL = 50;
    //Determines if humans sleep at night
    private static final boolean DOES_SLEEP = true;


    /**
     * Create a Human and add plants to the hashmap -as that's their food source
     *
     * @param randomAge If true, the human will have random age and energy level.
     * @param field     The field currently occupied.
     * @param location  The location within the field.
     */
    public Human(boolean randomAge, Field field, Location location) {
        super(randomAge, field, location);
        addPrey(Human.class);
    }

    /**
     * @return the age at which humans can breed
     */
    public int getBreedingAge() {
        return BREEDING_AGE;
    }

    /**
     * @return the maximum age humans can live until
     */
    public int getMaxAge() {
        return MAX_AGE;
    }

    /**
     * @return the probability of a human breeding
     */
    public double getBreedingProbability() {
        return BREEDING_PROBABILITY;
    }

    /**
     * @return the maximum energy a human can have
     */
    public int getMaxEnergyLevel() {
        return MAX_ENERGY_LEVEL;
    }

    /**
     * @return the maximum births a human can give at a time
     */
    public int getBirthLimit() {
        return BIRTH_LIMIT;
    }

    /**
     * @return the energy level of a newborn human
     */
    public int getNewBornEnergyLevel() {
        return NEW_BORN_ENERGY_LEVEL;
    }

    /**
     * @retrun if humans sleep
     */
    public boolean getDoesSleep() {
        return DOES_SLEEP;
    }

    /**
     * @retrun the food level of humans
     */
    public int getFoodLevel() {
        return FOOD_LEVEL;
    }


    /**
     * Creates a new baby, with randomAge set to false. Allowing the
     * baby to have a newBorn energy level and age 0.
     * Adds the new baby to the list, which will be used in simulator
     * to store all new babies with existing species
     *
     * @param newBabies The list which contains new species
     * @param field     The grid in which the zombie will be born
     * @param loc       The location of the new zombie's birth
     */
    public void addBaby(List<Species> newBabies, Field field, Location loc) {
        Human baby = new Human(false, field, loc);
        //Adding new baby, which is referencing newAnimals in Simulator
        newBabies.add(baby);
    }

}
