import java.util.*;

/**
 * A class representing shared characteristics of species in the simulation.
 * Allows all species to die, mate, giveBirth, age and carry diseases.
 *
 * @author David J. Barnes, Michael Kölling,
 * Areeba Safdar(K20045738) and Sabeeka Ahmad(K20012890)
 * @version 2016.02.29 (2)
 */
public abstract class Species {
    //Randomizer to help generate random ages and control breeding
    private static final Random RAND = Randomizer.getRandom();
    //The probability of a species being male of female
    private static final double DECIDE_SEX = 0.5;

    // Whether the species is alive or not.
    private boolean alive;
    // The species' field.
    private Field field;
    // The species' position in the field.
    private Location location;

    protected int energyLevel;
    private int age;
    private boolean isFemale;
    //The different diseases carried by the specie
    private List<Disease> diseasesCarried;


    /**
     * Create a new species at location in field, assigns a random age and
     * random energy level if randomAge is true.
     * The random age will be between 0 and the maximum age the species.
     * The random energy level will be between 1 and maximum energy the specie
     * can have.
     * If randomAge is false the age is set to 0 and energy level is set to
     * a newborn's energy.
     *
     * @param field     The field currently occupied.
     * @param location  The location within the field.
     * @param randomAge Determines if a random age should be assigned
     */
    public Species(boolean randomAge, Field field, Location location) {
        diseasesCarried = new ArrayList<>();
        age = 0;
        alive = true;
        this.field = field;
        setLocation(location);

        if (randomAge) {
            setAge(RAND.nextInt(getMaxAge()));
            changeEnergyLevel(RAND.nextInt(getMaxEnergyLevel()) + 1);
        } else {
            changeEnergyLevel(getNewBornEnergyLevel());
        }
        isFemale = (RAND.nextDouble() <= DECIDE_SEX);
    }

    /**
     * An abstract method to make each species act - method should perform all
     * actions each species does
     *
     * @param newSpecies         Holds all new species being born from
     * @param isDay              determines if the simulation is in daytime
     * @param diseaseBeingSpread is the disease currently being spread in the simulation
     * @param weatherOccurring   The weather event occuring
     */
    abstract protected void act(List<Species> newSpecies, boolean isDay, Disease diseaseBeingSpread, Weather weatherOccurring);

    /**
     * An abstract method to create and add a new baby of different species
     *
     * @param newSpecies
     * @param field      The grid in which the animal will be born
     * @param loc        The location of the new animal's birth
     */
    abstract protected void addBaby(List<Species> newSpecies, Field field, Location loc);

    /**
     * @return the age at which the specie can breed
     */
    abstract protected int getBreedingAge();

    /**
     * @return the maximum age at of the specie
     */
    abstract protected int getMaxAge();

    /**
     * @return the probability of the specie breeding
     */
    abstract protected double getBreedingProbability();

    /**
     * @return the maximum births a specie can give at a time
     */
    abstract protected int getBirthLimit();

    /**
     * @retrun the energy level of a specific predator
     */
    abstract protected int getMaxEnergyLevel();

    /**
     * @retrun the energy level of a newborn specie
     */
    abstract protected int getNewBornEnergyLevel();

    /**
     * @retrun of the specie sleeps
     */
    abstract protected boolean getDoesSleep();

    /**
     * @retrun of the food level of the specie
     */
    abstract protected int getFoodLevel();

    /**
     * @return if the instance of this species is female
     */
    protected boolean getIsFemale() {
        return isFemale;
    }


    /**
     * Sets the age of the species to the value specified by
     * the parameter number.
     *
     * @param number the new age of tbe species
     */
    protected void setAge(int number) {
        age = number;
    }

    /**
     * Check whether the instance of a species is alive or not.
     *
     * @return true if the animal is still alive.
     */
    protected boolean isAlive() {
        return alive;
    }

    /**
     * Indicate that the instance of a species is no longer alive.
     * It is removed from the field.
     */
    protected void setDead() {
        alive = false;
        if (location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the location of a species.
     *
     * @return The species' location.
     */
    protected Location getLocation() {
        return location;
    }

    /**
     * Place the species at the new location given.
     *
     * @param newLocation The species' new location.
     */
    protected void setLocation(Location newLocation) {
        if (location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    /**
     * Return the field of a species.
     *
     * @return The species' field.
     */
    protected Field getField() {
        return field;
    }

    /**
     * Adds a disease to the species
     *
     * @param disease the disease it will now carry
     */
    public void addDisease(Disease disease) {
        diseasesCarried.add(disease);
    }

    /**
     * Removes the disease specified - the specie is no longer infected
     *
     * @param disease the disease being removed
     */
    public void removeDisease(Disease disease) {
        diseasesCarried.remove(disease);
    }

    /**
     * Returns if a specie carries a specific disease
     *
     * @param disease the disease being checked
     */
    public boolean doesCarryDisease(Disease disease) {
        return (diseasesCarried.contains(disease));
    }

    /**
     * @return the list of diseases the specie carries
     */
    public List<Disease> getDiseasesCarried() {
        return diseasesCarried;
    }

    /**
     * Returns if a specie is infected. If the lists containing diseases carried
     * is empty then the specie is not infected
     *
     * @return true if it's carrying at least one disease, otherwise false
     */
    public boolean isInfected() {
        return (diseasesCarried.size() != 0);
    }

    /**
     * Method used to change the energyLevel of a species. If the new
     * energy level exceeds the maximum energy a species can have, then the
     * energy level is set to a maximum. If the energy level drops below 0 it
     * is killed.
     *
     * @param newEnergy the value the energyLevel will be updated to
     */
    public void changeEnergyLevel(int newEnergy) {
        int newEnergyLevel = energyLevel + newEnergy;

        if (newEnergyLevel <= 0) {
            setDead();
        } else if (newEnergyLevel > getMaxEnergyLevel()) {
            energyLevel = getMaxEnergyLevel();
            ;
        } else {
            energyLevel = newEnergyLevel;
        }
    }

    /**
     * A species can breed if it has reached the breeding age.
     *
     * @return true if the species can breed, false otherwise.
     */
    public boolean canBreed() {
        return age >= getBreedingAge();
    }

    /**
     * Increments the age of a species. The instance of the species
     * will be killed if it's reached past the maximum age
     */
    public void incrementAge() {
        age++;
        if (age > getMaxAge()) {
            setDead();
        }
    }

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * A species can only breed if it's of age and has a partner in a
     * neighbouring cell
     *
     * @return The number of births may be zero.
     */
    public int breed() {
        int births = 0;
        if (canBreed() && hasMate() && RAND.nextDouble() <= getBreedingProbability()) {
            births = RAND.nextInt(getBirthLimit()) + 1;
        }
        return births;
    }

    /**
     * Used to create new instances of different species.
     * Checks if the species can breed (is of breeding age and has mate).
     * New births will be made into free adjacent locations.
     *
     * @param newSpecies list holding all newborns
     */
    public void giveBirth(List<Species> newSpecies) {
        // New foxes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            addBaby(newSpecies, field, loc);
        }
    }

    /**
     * Checks if the current species has a mate in a neighbouring cell.
     * The mate should be of the same species, opposite gender and in
     * a neighbouring cell.
     *
     * @return true if the species has a mate, false otherwise
     */
    public boolean hasMate() {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation(), 1);

        for (Location where : adjacent) {
            Species speciesInNextCell = (Species) field.getObjectAt(where);

            if (speciesInNextCell != null && this.getClass().equals(speciesInNextCell.getClass()) && this.getIsFemale() && !speciesInNextCell.getIsFemale() && speciesInNextCell.canBreed()) {
                return true;
            }
        }
        return false;
    }
}
