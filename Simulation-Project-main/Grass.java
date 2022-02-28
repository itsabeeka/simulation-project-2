import java.util.List;
import java.util.Random;

public class Grass extends Plant{

    //The energy points if a plant is eaten
    public static final int FOOD_LEVEL = 10;
    // The age at which grass can conceive
    private static final int BREEDING_AGE = 10;
    // The age to which a grass lives up to
    private static final int MAX_AGE = 15;
    // The likelihood of having a new grass plant.
    private static final double BREEDING_PROBABILITY = 0.15;
    // The maximum number of births.
    private static final int BIRTH_LIMIT = 2;
    //The energy level of a new-born child
    private static final int NEW_BORN_ENERGY_LEVEL = 10;
    //The maximum energy level grass can have
    private static final int MAX_ENERGY_LEVEL = 15;
    //Determines if grass sleep at night
    private static final boolean DOES_SLEEP = true;

    public Grass(boolean randomAge, Field field, Location location){
        super(randomAge, field, location);

    }

    /**
     * @return The maximum age grass will live till.
     */
    public int getMaxAge() {
        return MAX_AGE;
    }

    /**
     * @return The probability of grass breeding
     */
    public double getBreedingProbability() {
        return BREEDING_PROBABILITY;
    }

    /**
     * @return The maximum number of births
     */
    public int getBirthLimit() {
        return BIRTH_LIMIT;
    }

    /**
     * @retrun if grass plants sleep
     */
    public boolean getDoesSleep() {
        return DOES_SLEEP;
    }

    /**
     * @retrun the food level of grass
     */
    public int getFoodLevel() {
        return FOOD_LEVEL;
    }

    /**
     * @return the maximum energy grass can have
     */
    public int getMaxEnergyLevel() {
        return MAX_ENERGY_LEVEL;
    }

    /**
     * @return The energy level of newborn grass plants
     */
    public int getNewBornEnergyLevel() {
        return NEW_BORN_ENERGY_LEVEL;
    }

    /**
     * @return The age at which grass starts to breed.
     */
    public int getBreedingAge() {
        return BREEDING_AGE;
    }

    /**
     * Creates a new grass plant, with randomAge set to false. Allowing the
     * grass to have a newBorn energy level and age 0.
     * Adds the new grass to the list, which will be used in simulator
     * to store all new plants with existing species
     *
     * @param newGrass The list which contains new species
     * @param field      The grid in which the dragon will be born
     * @param loc        The location of the new dragon's birth
     */
    public void addBaby(List<Species> newGrass, Field field, Location loc) {
        Grass babyPlant = new Grass(false, field, loc);
        //Adding new plants, which is referencing newSpecies in Simulator
        newGrass.add(babyPlant);
    }
}
