/**
 * Represents the disease ebola and extends the abstract class Disease.
 * Contains the static final variables specific for this disease
 *
 * @author Areeba Safdar(K20045738) and Sabeeka Ahmad(K20012890)
 */

public class Ebola extends Disease {

    //name of disease
    private static final String name = "Ebola";
    //the amount species energy-level is decreased
    private static final int DAMAGE_POINTS = -10;
    //The number of steps before another outbreak can happen again
    private static final int NUMBER_OF_STEPS_BEFORE_NEXT_OUTBREAK = 10;
    //Probability of the disease occurring
    private static final double DISEASE_CREATION_PROBABILITY = 0.8;
    //The  number of steps the disease will be active
    private static final int MAX_DISEASE_LENGTH = 20;

    /**
     * Adds the species Ebola will affect to the list
     */
    public Ebola() {
        addSpeciesAffected(Human.class);
        addSpeciesAffected(Zombie.class);

    }

    /**
     * @return the name of the disease
     */
    public String getName() {
        return name;
    }

    /**
     * @return the number of energy points the disease decreases
     */
    public int getDamagePoints() {
        return DAMAGE_POINTS;
    }

    /**
     * @return the probability of the disease occurring
     */
    public double getDiseaseCreationProbability() {
        return DISEASE_CREATION_PROBABILITY;
    }

    /**
     * @return the number of steps the disease will run for
     */
    public int getMaxDiseaseLength() {
        return MAX_DISEASE_LENGTH;
    }

    /**
     * @return the number of steps before the next outbreak
     */
    public int getNumberOfStepsBeforeNextOutbreak() {
        return NUMBER_OF_STEPS_BEFORE_NEXT_OUTBREAK;
    }

}




