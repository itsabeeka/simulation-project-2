import java.util.*;

/**
 * An abstract class to represent all the predators in the simulation.
 * Predators can hunt for prey, each prey will have different food levels.
 * The food level is the amount the predator's energy levels will increase by.
 *
 * @author Areeba Safdar(K20045738) and Sabeeka Ahmad(K20012890)
 */
public abstract class Predator extends Species {

    private List<Class<?>> prey;

    /**
     * Constructor calling Species constructor, and if the predator will have
     * a random stating age, then the predator
     */
    public Predator(boolean randomAge, Field field, Location location) {
        super(randomAge, field, location);
        prey = new ArrayList<>();
    }

    /**
     * Adds species to the prey list
     *
     * @param specie the class of the prey
     */
    protected void addPrey(Class<?> specie) {
        prey.add(specie);
    }


    /**
     * Make the predator hungry, reducing it'd energy level by 1;
     */
    public void incrementHunger() {
        changeEnergyLevel(-1);
    }

    /**
     * Looks for all the prey animals in adjacent squares.
     * If the correct animal is there and is not dead - it is killed and eaten.
     * The prey's food value is set as the new energy level of the predator
     *
     * @return Where food was found, or null if it wasn't.
     */
    public Location findFood() {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation(), 1);

        for (Location where : adjacent) {
            Species food = (Species) field.getObjectAt(where);

            if (food != null && prey.contains(food.getClass())) {
                if (food.isAlive()) {
                    food.setDead();
                    changeEnergyLevel(food.getFoodLevel());
                    return where;
                }
            }
        }
        return null;
    }

    /**
     * This is what the predator does most of the time: it hunts for
     * prey, it might breed, die of hunger, overcrowding, and old age.
     * If a disease is being spread and a predator carries this disease - it
     * spreads to any neighboring predators of the same subclass.
     * If a weather event is not null, the effect of that weather is called.
     * If the simulation is in nighttime, predators only age. Otherwise, they
     * continue with all of their activities
     *
     * @param newPradators       A list to return newly born predators.
     * @param isDay              Determines if the simulation is in day or night t(ime
     * @param diseaseBeingSpread The disease being spread (can be null)
     * @param weatherOccurring   The weather event occurring (can be null)
     */
    public void act(List<Species> newPradators, boolean isDay, Disease diseaseBeingSpread, Weather weatherOccurring) {
        incrementAge();

        if (getDoesSleep() && !isDay) {
            return;
        }

        incrementHunger();
        if (isAlive()) {
            //If infected - spread the disease
            if (diseaseBeingSpread != null && doesCarryDisease(diseaseBeingSpread)) {
                diseaseBeingSpread.spread(this, getField());
            }
            if (weatherOccurring != null) {
                weatherOccurring.effectOfWeather(this);
            }

            giveBirth(newPradators);
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if (newLocation == null) {
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if (newLocation != null) {
                setLocation(newLocation);
            } else {
                // Overcrowding.
                setDead();
            }
        }
    }
}
