import java.util.*;

public abstract class Plant extends Species {

    // Characteristics shared by all plants (class variables).
    private static final Random RAND = Randomizer.getRandom();

    /**
     * Create a new plant. A plant may be created with age
     * zero (a newborn) or with a random age.
     * (Random Age factor is handled in superclass Species)
     *
     * @param randomAge If true, the human will have a random age.
     * @param field     The field currently occupied.
     * @param location  The location within the field.
     */
    public Plant(boolean randomAge, Field field, Location location) {
        super(randomAge, field, location);
    }

    /**
     * Override plants breeding as don't need to check if they have mates and
     * have random number of births.
     * Plants grow at a constant rate - so their births will be their maximum
     * births (BIRTH_LIMIT)
     *
     * @return the number of births
     */
    public int breed() {
        int births = 0;
        if (canBreed() && RAND.nextDouble() <= getBreedingProbability()) {
            births = getBirthLimit();
        }
        return births;
    }

    /**
     * This is what the plant does most of the time - it ages, breeds, responds
     * to weather and diseases.
     * If a disease is being spread and a plant carries this disease - it
     * spreads to any neighboring plants of the same subclass.
     * If a weather event is not null, the effect of that weather is called and if
     * it's not raining effectOfNoRain is called.
     * If the simulation is in nighttime, plants only age.
     *
     * @param newPlants          A list to return newly born predators.
     * @param isDay              Determines if the simulation is in day or night t(ime
     * @param diseaseBeingSpread The disease being spread (can be null)
     * @param weatherOccurring   The weather event occurring (can be null)
     */
    public void act(List<Species> newPlants, boolean isDay, Disease diseaseBeingSpread, Weather weatherOccurring) {
        incrementAge();

        if (getDoesSleep() && !isDay) {
            return;
        }

        if (isAlive()) {
            giveBirth(newPlants);
            //If infected - spread the disease
            if (diseaseBeingSpread != null && doesCarryDisease(diseaseBeingSpread)) {
                diseaseBeingSpread.spread(this, getField());
            }

            if (weatherOccurring != null) {
                weatherOccurring.effectOfWeather(this);
                //Response to no rain
            }
            
            //  || !(weatherOccurring.getName().equals("Raining"))
            if (weatherOccurring == null || !(weatherOccurring.getClass() == Rain.class)) {
                effectOfNoRain();
            }
        }
    }

    /**
     * Decreases the energy level of the plant by 1.
     * Called when it's not raining in the simulation
     */
    private void effectOfNoRain() {
        changeEnergyLevel(-1);
    }
}