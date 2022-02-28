/**
 * This class in a subclass os wetaher. It returns all of it's weather specific values including:
 * the maximum weather length, minimum interval before next occurance, and the probability of it happening.
 * The plant class is dependent on rain for energy, so the effect of rain is the increment its energy.
 *
 * @author Areeba Safdar(K20045738) and Sabeeka Ahmad(K20012890)
 */
public class Rain extends Weather
{
    // maximum time it can rain for at a time
    private static final int MAX_WEATHER_LENGTH = 30;
    private static final int NUMBER_OF_STEPS_BEFORE_NEXT_WEATHER = 5;   // min interval before next occurance
    private static final double WEATHER_OCCURRING_PROBABILITY = 0.8;

    /**
     * Constructor holds the name of the weather (to be shown on the simulator view when it's raining).
     * The classes that are affected by rain are added to the speciesAffected ArrayList.
     */
    public Rain()
    {
        name = "Raining";
        addSpeciesAffected(Plant.class);
    }

    /**
     * @return the minimum interval before the weather can reoccur.
     */
    public int getNumberOfStepsBeforeNextWeather(){
        return NUMBER_OF_STEPS_BEFORE_NEXT_WEATHER;
    }

    /**
     * @return the mavimum number of steps it can rain for.
     */
    public int getMaxWeatherLength(){
        return MAX_WEATHER_LENGTH;
    }

    /**
     * @return the probability of it raining.
     */
    public double getWeatherOccurringProbability(){
        return WEATHER_OCCURRING_PROBABILITY;
    }

    /**
     * Rain restores plants energy to their maximum
     * @param specie
     */
    public  void effectOfWeather(Species specie){
        if(speciesAffected.contains(specie.getClass())){
            specie.changeEnergyLevel(specie.getMaxEnergyLevel());
        }
    }
}