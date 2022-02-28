import java.util.*;

/**
 * This class represents weather and holds all things all weathers need.
 * Weather is activated in the simulator by passing in a randomly generated ineteger value.
 * This is stored into the weather's 'duration' field.
 * The number of steps the weather remains active is the duration value.
 * This is kept under the limit of each subclass's MaxWeatherLength.
 * Each weather will have its own name to be passed to the simulator view.
 * They also have thier own probability of occuring.
 *
 * @author Areeba Safdar(K20045738) and Sabeeka Ahmad(K20012890)
 */
public abstract class Weather {
    private static final Random rand = Randomizer.getRandom();
    // Each weather subclass holds an ArrayList of classes it affects
    protected List<Class<?>> speciesAffected;
    protected String name;
    // last step number on which the weather was active
    private int lastWeatherStep;
    private boolean isActive;
    // number of steps weather will be active for
    private int duration;

    /**
     * Setting up initial values of the weather.
     * Initially it's not active, it's duration is 0, and
     * it's lastWeatherStep is 0.
     */
    public Weather() {
        // initialise instance variables
        speciesAffected = new ArrayList<>();
        lastWeatherStep = 0;
        duration = 0;
        isActive = false;
    }

    /**
     * Abstract method to enable each weather subclass to have its own effect.
     * @ param Species specie to be chcked that it's a class effected by the weather.
     */
    public abstract void effectOfWeather(Species specie);

    /**
     * @return int the minimum interval between occurance of the weather.
     */
    public abstract int getNumberOfStepsBeforeNextWeather();

    /**
     * @return int the maximum number of steps a weather can be active for.
     */
    public abstract int getMaxWeatherLength();

    /**
     * @return double the probability of the weather happening.
     */
    public abstract double getWeatherOccurringProbability();

    /**
     * @param the species to add to the list of species that the weather affects.
     */
    public void addSpeciesAffected(Class<?> specie) {
        speciesAffected.add(specie);
    }

    /**
     * This method stored the step number of the last weather occurance.
     * @param the step number on which the weather was last active.
     */
    public void changeLastWeatherStep(int time) {
        lastWeatherStep = time;
    }

    /**
     * When the weather is active, this method is called on each step.
     * The duration is decreased by 1.
     */
    public void decrementDuration() {
        if (duration-- <= 0) {
            duration = 0;
            return;
        }
        duration--;
    }

    /**
     * @return int the last step number on which the weather was active.
     */
    public int getLastWeatherStep() {
        return lastWeatherStep;
    }

    /**
     * This method returns the name of the weather to be shown on the simulator view when it's active.
     * @return The String value of the weather name.
     */
    public String getName() {
        return name;
    }

    /**
     * @return the number of duration steps left.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * @return whether the weather is currently active.
     */
    public boolean getIsActive() {
        return isActive;
    }

    /**
     * This method activates the weather.
     * A random integer value is assigned to duration.
     */
    public void activate() {
        isActive = true;
        duration = rand.nextInt(getMaxWeatherLength()) + 5;
        System.out.println(duration);
    }

    /**
     * This method deactivates the weather.
     * The duration value is reset to 0.
     */
    public void deactivate() {
        isActive = false;
        duration = 0;
    }
}
