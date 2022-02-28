import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing Dragons, Zombies, Hell-hounds, Humans and plants.
 * It also simulates different diseases and weather conditions - effecting
 * different species
 *
 * @author David J. Barnes, Michael Kölling,
 * Areeba Safdar(K20045738) and Sabeeka Ahmad(K20012890)
 * @version 2016.02.29 (2)
 */
public class Simulator {
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    //The probability of plant being created in any given grid position
    private static final double GRASS_CREATION_PROBABILITY = 0.09;
    // The probability that a zombie will be created in any given grid position.
    private static final double HUMAN_CREATION_PROBABILITY = 0.08;
    // The probability that a hell-hound will be created in any given grid position.
    private static final double HELLHOUND_CREATION_PROBABILITY = 0.06;
    // The probability that a zombie will be created in any given grid position.
    private static final double ZOMBIE_CREATION_PROBABILITY = 0.04;
    // The probability that a dragon will be created in any given grid position.
    private static final double DRAGON_CREATION_PROBABILITY = 0.02;
    //Randomizer to generate random disease and weather
    private static final Random RAND = Randomizer.getRandom();
    //Used to slow down and speed up simulation time
    private static int delay;
    // The current step of the simulation.
    public int step;
    //List of diseases in the simulation
    private List<Disease> diseases;
    //List of weather in the simulation
    private List<Weather> weathers;
    // List of animals in the field.
    private List<Species> species;
    // The current state of the field.
    private Field field;
    // A graphical view of the simulation.
    private SimulatorView view;
    //Text showing which disease and weather are currently occurring
    private String weatherText;
    private String diseaseText;
    //Fields holding the active weather and disease
    private Weather currentWeather;
    private Disease currentDisease;
    private boolean isDay;

    /**
     * Construct a simulation field with default size and initially set delay
     * to 500 milliseconds.
     */
    public Simulator() {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
        delay = 500;
    }

    /**
     * Create a simulation field with the given size.
     *
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width) {
        if (width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }

        currentWeather = null;
        currentDisease = null;
        diseases = new ArrayList<>();
        weathers = new ArrayList<>();
        species = new ArrayList<>();
        field = new Field(depth, width);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width, this);

        view.setColor(Grass.class, new Color(85, 147, 100)); //green
        view.setColor(Human.class, new Color(232, 166, 40)); //yellow
        view.setColor(Dragon.class, new Color(228, 81, 73)); //red
        view.setColor(Zombie.class, new Color(222, 18, 250)); //purple
        view.setColor(HellHound.class, new Color(1, 211, 252)); //blue


        // Setup a valid starting point.
        reset();
    }

    public static void main(String[] args) {
        Simulator s = new Simulator();
//        s.simulateOneStep();
//        s.simulate(10);
        s.runLongSimulation();
    }

    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation() {
        simulate(4000);
    }

    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * End of each step is delayed by the time stored in delay field
     *
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps) {
        for (int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
            delay(delay);   // Used to change the speed of the simulation
        }
    }

    /**
     * Change boolean value of isDay to it's opposite
     */
    public void changeDayTime() {
        isDay = !isDay;
    }

    /**
     * Checks if there's a currentDisease.
     * If no currentDisease, a random disease is generated and if
     * creation requirements met - currentDisease is updated and activated.
     * If currentDisease holds a disease - it's duration is decremented (each
     * step causes to duration to go down).
     * If the duration is now 0 - the current step is stored as the
     * last step the disease was active for and the disease is deactivated.
     */
    public void handleDiseases() {
        if (currentDisease == null) {
            Disease randomDisease = diseases.get(RAND.nextInt(diseases.size()));
            if (step > (randomDisease.getLastDiseaseStep() + randomDisease.getNumberOfStepsBeforeNextOutbreak() - 1) && RAND.nextDouble() <= randomDisease.getDiseaseCreationProbability()) {
                currentDisease = randomDisease;
                diseaseText = currentDisease.getName();
                currentDisease.activate(field);
            }
        } else {
            currentDisease.decrementDuration();
            diseaseText = currentDisease.getName(); //display disease is still spreading
            if (currentDisease.getDuration() == 0) {
                currentDisease.changeLastDiseaseStep(step);
                currentDisease.deactivate(field);
                currentDisease = null;
                diseaseText = "none";
            }
        }
    }

    /**
     * Checks if there's a currentWeather.
     * If no currentWeather, a random weather is generated and if
     * creation requirements met - currentWeather is updated and activated.
     * If currentWeather holds a disease - it's duration is decremented (each
     * step causes to duration to go down).
     * If the duration is now 0 - the current step is stored as the
     * last step the weather was active for and the weather is deactivated.
     */
    public void handleWeather() {
        if (currentWeather == null) {
            Weather randomWeather = weathers.get(RAND.nextInt(weathers.size()));
            if (step > (randomWeather.getLastWeatherStep() + randomWeather.getNumberOfStepsBeforeNextWeather()) && RAND.nextDouble() <= randomWeather.getWeatherOccurringProbability()) {
                currentWeather = randomWeather;
                weatherText = currentWeather.getName();
                currentWeather.activate();
            }
        } else {
            currentWeather.decrementDuration();
            weatherText = currentWeather.getName(); //display weather is still occurring
            if (currentWeather.getDuration() == 0) {
                currentWeather.changeLastWeatherStep(step);
                currentWeather.deactivate();
                currentWeather = null;
                weatherText = "clear";
            }
        }
    }

    /**
     * Run the simulation from its current state for a single step.
     * Change day value if 5 steps have passed, handles disease & weather
     * simulation and displays if any weather condition or disease is occurring.
     * Iterate over the whole field, spreading diseases,
     * applying effects of weather on different species and updating
     * the state of each specie in the simulation.
     */
    public void simulateOneStep() {
        step++;
        weatherText = "clear";
        diseaseText = "none";
        if (step % 5 == 0) {
            changeDayTime();
        }

        handleDiseases();
        handleWeather();

        // Provide space for newborn animals.
        List<Species> newAnimals = new ArrayList<>();
        // Let all species act
        for (Iterator<Species> it = species.iterator(); it.hasNext(); ) {
            Species species = it.next();

            species.act(newAnimals, isDay, currentDisease, currentWeather);
            if (!species.isAlive()) {
                it.remove();
            }
        }
        // Add the newly born species objects to the main lists.
        species.addAll(newAnimals);

        view.showStatus(step, field, isDay, diseaseText, weatherText);
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset() {
        step = 0;
        isDay = true;
        species.clear();
        populate();
        weatherText = "clear";
        diseaseText = "none";
        // Show the starting state in the view.
        view.showStatus(step, field, isDay, diseaseText, weatherText);
    }

    /**
     * Generate all diseases and weather and randomly populate the field will
     * different types of species.
     */
    private void populate() {
        //Creating diseases
        Disease ebola = new Ebola();
        diseases.add(ebola);

        //Creating weathers
        Weather rain = new Rain();
        weathers.add(rain);

        field.clear();
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {

                if (RAND.nextDouble() <= DRAGON_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Dragon dragon = new Dragon(true, field, location);
                    species.add(dragon);
                } else if (RAND.nextDouble() <= ZOMBIE_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Zombie zombie = new Zombie(true, field, location);
                    species.add(zombie);
                } else if (RAND.nextDouble() <= HELLHOUND_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    HellHound hellHound = new HellHound(true, field, location);
                    species.add(hellHound);
                } else if (RAND.nextDouble() <= HUMAN_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Human human = new Human(true, field, location);
                    species.add(human);
                } else if (RAND.nextDouble() <= GRASS_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Grass plant = new Grass(true, field, location);
                    species.add(plant);
                }
                // else leave the location empty.
            }
        }
    }

    /**
     * Pause for a given time.
     *
     * @param millisec The time to pause for, in milliseconds
     */
    private void delay(int millisec) {
        try {
            Thread.sleep(millisec);
        } catch (InterruptedException ie) {
            System.out.println(ie);
        }
    }

    /**
     * Slows down the simulation by adding a certain amount of milliseconds to
     * the delay value
     *
     * @param delayValue the amount delay is being increased
     */
    public void incrementDelay(int delayValue) {
        delay += delayValue;
    }

    /**
     * Speeds down the simulation by decreasing a certain amount of milliseconds
     * from the delay value
     *
     * @param delayValue the amount delay is being decreased
     */
    public void decrementDelay(int delayValue) {
        if (delay - delayValue >= 0) {
            delay -= delayValue;
        }
    }
}
