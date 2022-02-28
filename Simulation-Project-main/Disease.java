import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A class to represent diseases in the simulation. The simulation will
 * activate a disease if none are active & a certain number of steps
 * have passed since the disease was last active.
 * Each disease will keep track of when it was last active, the species it
 * affects, and the duration it will spread for.
 * Disease can be activated & infects species in a random block of locations,
 * spread for a random duration and deactivated.
 * When random species get the disease their energy levels
 * decrease by a certain amount
 *
 * @author Areeba Safdar(K20045738) and Sabeeka Ahmad(K20012890)
 */

public abstract class Disease {
    //Randomizer to help generate random length of the disease
    private static final Random RAND = Randomizer.getRandom();

    private int lastDiseaseStep;
    private List<Class<?>> speciesAffected;
    private boolean isActive;
    private int duration; //no of steps the disease will run for


    /**
     * Setting up initial values of the disease.
     * Initially it's not active, it's duration is 0, and
     * it's lastDiseaseStep is 0.
     */
    public Disease() {
        speciesAffected = new ArrayList<>();
        lastDiseaseStep = 0;
        isActive = false;
        duration = 0;
    }

    /**
     * Abstract method used to return the name of the disease. Will be used
     * to display the disease in the gui
     *
     * @return name of disease
     */
    abstract public String getName();

    /**
     * @return the amount a specie's energy level will decrease when becoming
     * infected with the disease
     */
    abstract public int getDamagePoints();

    /**
     * @return the probability of a disease being created
     */
    abstract public double getDiseaseCreationProbability();

    /**
     * @return the number of steps before the disease can become active again
     */
    abstract public int getNumberOfStepsBeforeNextOutbreak();

    /**
     * @return the maximum number of steps the disease wil run for
     */
    abstract public int getMaxDiseaseLength();

    /**
     * @return the lists of species which this disease will affect
     */
    public List<Class<?>> getSpeciesAffected() {
        return speciesAffected;
    }

    ;

    /**
     * @return the step at which the last disease was active for
     */
    public int getLastDiseaseStep() {
        return lastDiseaseStep;
    }

    /**
     * @return if the disease is currently active
     */
    public boolean getIsActive() {
        return isActive;
    }

    /**
     * @return the number if steps the disease will run for
     */
    public int getDuration() {
        return duration;
    }


    /**
     * Changes the step at which the disease was last active to the given step
     *
     * @param step the new step which the disease wass last active
     */
    public void changeLastDiseaseStep(int step) {
        lastDiseaseStep = step;
    }

    /**
     * Add the species given to the list of species the disease will affect
     *
     * @param specie the class being added
     */
    public void addSpeciesAffected(Class<?> specie) {
        speciesAffected.add(specie);
    }

    /**
     * Sets the disease to be active and a random duration of disease (minimum
     * length = 10 steps)
     * Loops through the random block of the field and infects species that can
     * be affected by this disease.
     * Each being affected will carry this disease, by calling addDisease()
     *
     * @param field used to generate a random block from the field
     */
    public void activate(Field field) {
        duration = RAND.nextInt(getMaxDiseaseLength()) + 10;
        isActive = true;

        List<Location> diseaseLocations = field.getRandomBlockOfField();

        for (Location location : diseaseLocations) {
            Species speciesInCell = (Species) field.getObjectAt(location);
            if (speciesInCell != null && getSpeciesAffected().contains(speciesInCell.getClass())) {
                //Give the disease
                speciesInCell.addDisease(this);
                speciesInCell.changeEnergyLevel(getDamagePoints());
            }
        }
    }

    /**
     * Sets isActive to false and removes the disease from all species
     * carrying it
     */
    public void deactivate(Field field) {
        isActive = false;

        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Species specie = (Species) field.getObjectAt(row, col);

                if (specie != null && specie.getDiseasesCarried().contains(this)) {
                    specie.removeDisease(this);
                }
            }
        }
    }

    /**
     * spreading the disease to neighbouring cells of an infected specie
     *
     * @param specie the infected specie
     * @param field  the field used to find the neighbouring cells of the specie
     */
    public void spread(Species specie, Field field) {

        List<Location> adjacent = field.adjacentLocations(specie.getLocation(), 1);
        for (Location where : adjacent) {
            Species speciesInNextCell = (Species) field.getObjectAt(where);
            if (speciesInNextCell != null) {
                speciesInNextCell.changeEnergyLevel((getDamagePoints()));
            }
        }
    }

    /**
     * Reduces the duration by 1.
     * If duration becomes negative, it is set to zero.
     */
    public void decrementDuration() {
        if (duration-- <= 0) {
            duration = 0;
            return;
        }
        duration--;
    }
}

