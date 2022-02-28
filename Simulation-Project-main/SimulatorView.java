import java.awt.*;
import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A graphical view of the simulation grid.
 * The view displays a colored rectangle for each location
 * representing its contents. It uses a default background color.
 * Colors for each type of species can be defined using the
 * setColor method.
 * Includes buttons for extra functionality - such as speeding up and slowing
 * down the simulation
 *
 * @author David J. Barnes and Michael Kölling
 * Areeba Safdar(K20045738) and Sabeeka Ahmad(K20012890)
 * @version 2016.02.29
 */
public class SimulatorView extends JFrame {
    // Colors used for empty locations - either day or night
    private static final Color DAY_COLOR = new Color(243,233,231);
    private static final Color NIGHT_COLOR = new Color(182,210,221);
    // Color used for objects that have no defined color.
    private static final Color UNKNOWN_COLOR = Color.gray;
    private final String STEP_PREFIX = "Step: ";
    private final String POPULATION_PREFIX = "Population: ";
    private final String WEATHER_PREFIX = "Weather: ";
    private final String DISEASE_PREFIX = "Disease: ";
    Simulator simulator;
    private JLabel stepLabel, population, infoLabel, diseaseLabel, weatherLabel;
    private JButton slowDownButton, speedUpButton, stopButton, resetButton;
    private FieldView fieldView;

    // A map for storing colors for participants in the simulation
    private Map<Class, Color> colors;
    // A statistics object computing and storing simulation information
    private FieldStats stats;

    /**
     * Create a view of the given width and height.
     *
     * @param height The simulation's height.
     * @param width  The simulation's width.
     */
    public SimulatorView(int height, int width, Simulator sim) {
        simulator = sim;
        stats = new FieldStats();
        colors = new LinkedHashMap<>();

        setTitle("Fox and Rabbit Simulation");

        diseaseLabel = new JLabel(DISEASE_PREFIX, JLabel.CENTER);
        weatherLabel = new JLabel(WEATHER_PREFIX, JLabel.CENTER);
        stepLabel = new JLabel(STEP_PREFIX, JLabel.CENTER);
        infoLabel = new JLabel("  ", JLabel.CENTER);
        population = new JLabel(POPULATION_PREFIX, JLabel.CENTER);

        stopButton = new JButton("Stop");
        stopButton.addActionListener(event -> System.exit(0));

        slowDownButton = new JButton("Slow Down");
        slowDownButton.addActionListener(event -> handleSlowDown());

        speedUpButton = new JButton("Speed Up");
        speedUpButton.addActionListener(event -> handleSpeedUp());

        resetButton = new JButton("Reset");
        resetButton.addActionListener(event -> new Thread(() -> {
            simulator.reset();
        }).start());

        setLocation(100, 50);

        fieldView = new FieldView(height, width);

        Container contents = getContentPane();

        JPanel infoPane = new JPanel();
        infoPane.add(stepLabel);
        infoPane.add(infoLabel);
        infoPane.add(diseaseLabel);
        infoPane.add(weatherLabel);

        //Buttons pane - with buttons arranged in a column
        JPanel buttonsPane = new JPanel();
        buttonsPane.setLayout(new BoxLayout(buttonsPane, BoxLayout.PAGE_AXIS));
        buttonsPane.add(stopButton);
        buttonsPane.add(resetButton);
        buttonsPane.add(slowDownButton);
        buttonsPane.add(speedUpButton);


        contents.add(infoPane, BorderLayout.SOUTH);
        contents.add(buttonsPane, BorderLayout.EAST);
        contents.add(fieldView, BorderLayout.CENTER);
        contents.add(population, BorderLayout.NORTH);
        pack();
        setVisible(true);
    }

    //Button events

    /**
     * Calls the incrementDelay method of the simulator and adds 100 milliseconds
     * to the delay field
     */
    public void handleSlowDown() {
        simulator.incrementDelay(100);
    }

    /**
     * Calls the decrementDelay method of the simulator and reduces 100
     * milliseconds from the delay field
     */
    public void handleSpeedUp() {
        simulator.decrementDelay(100);

    }

    /**
     * Define a color to be used for a given class of animal.
     *
     * @param animalClass The animal's Class object.
     * @param color       The color to be used for the given class.
     */
    public void setColor(Class animalClass, Color color) {
        colors.put(animalClass, color);
    }

    /**
     * Display a short information label at the top of the window.
     */
    public void setInfoText(String text) {
        infoLabel.setText(text);
    }

    /**
     * @return The color to be used for a given class of animal.
     */
    private Color getColor(Class animalClass) {
        Color col = colors.get(animalClass);
        if (col == null) {
            // no color defined for this class
            return UNKNOWN_COLOR;
        } else {
            return col;
        }
    }

    /**
     * Show the current status of the field.
     * If an animal is inducted it's colour changes
     *
     * @param step        Which iteration step it is.
     * @param field       The field whose status is to be displayed.
     * @param isDay       a boolean value to show if it's day or night.
     * @param diseaseText The current disease
     * @param weatherText the current weather
     */
    public void showStatus(int step, Field field, boolean isDay, String diseaseText, String weatherText) {
        if (!isVisible()) {
            setVisible(true);
        }

        stepLabel.setText(STEP_PREFIX + step);
        diseaseLabel.setText(DISEASE_PREFIX + diseaseText);
        weatherLabel.setText(WEATHER_PREFIX + weatherText);
        stats.reset();

        fieldView.preparePaint();

        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Object animal = field.getObjectAt(row, col);
                if (animal != null) {

                    stats.incrementCount(animal.getClass());
//                    fieldView.drawMark(col, row, ((Species) animal).isInfected() ? new Color(getColor(animal.getClass()).getRed() / 2, getColor(animal.getClass()).getGreen() / 2, getColor(animal.getClass()).getBlue() / 2) : getColor(animal.getClass()));
                    fieldView.drawMark(col, row, ((Species) animal).isInfected() ? Color.BLACK : getColor(animal.getClass()));

                } else {
                    fieldView.drawMark(col, row, isDay ? DAY_COLOR : NIGHT_COLOR);
                }
            }
        }
        stats.countFinished();

        population.setText(POPULATION_PREFIX + stats.getPopulationDetails(field));
        fieldView.repaint();
    }

    /**
     * Determine whether the simulation should continue to run.
     *
     * @return true If there is more than one species alive.
     */
    public boolean isViable(Field field) {
        return stats.isViable(field);
    }

    /**
     * Provide a graphical view of a rectangular field. This is
     * a nested class (a class defined inside a class) which
     * defines a custom component for the user interface. This
     * component displays the field.
     * This is rather advanced GUI stuff - you can ignore this
     * for your project if you like.
     */
    private class FieldView extends JPanel {
        private final int GRID_VIEW_SCALING_FACTOR = 6;
        Dimension size;
        private int gridWidth, gridHeight;
        private int xScale, yScale;
        private Graphics g;
        private Image fieldImage;

        /**
         * Create a new FieldView component.
         */
        public FieldView(int height, int width) {
            gridHeight = height;
            gridWidth = width;
            size = new Dimension(0, 0);
        }

        /**
         * Tell the GUI manager how big we would like to be.
         */
        public Dimension getPreferredSize() {
            return new Dimension(gridWidth * GRID_VIEW_SCALING_FACTOR,
                    gridHeight * GRID_VIEW_SCALING_FACTOR);
        }

        /**
         * Prepare for a new round of painting. Since the component
         * may be resized, compute the scaling factor again.
         */
        public void preparePaint() {
            if (!size.equals(getSize())) {  // if the size has changed...
                size = getSize();
                fieldImage = fieldView.createImage(size.width, size.height);
                g = fieldImage.getGraphics();

                xScale = size.width / gridWidth;
                if (xScale < 1) {
                    xScale = GRID_VIEW_SCALING_FACTOR;
                }
                yScale = size.height / gridHeight;
                if (yScale < 1) {
                    yScale = GRID_VIEW_SCALING_FACTOR;
                }
            }
        }

        /**
         * Paint on grid location on this field in a given color.
         */
        public void drawMark(int x, int y, Color color) {
            g.setColor(color);
            g.fillRect(x * xScale, y * yScale, xScale - 1, yScale - 1);
        }

        /**
         * The field view component needs to be redisplayed. Copy the
         * internal image to screen.
         */
        public void paintComponent(Graphics g) {
            if (fieldImage != null) {
                Dimension currentSize = getSize();
                if (size.equals(currentSize)) {
                    g.drawImage(fieldImage, 0, 0, null);
                } else {
                    // Rescale the previous image.
                    g.drawImage(fieldImage, 0, 0, currentSize.width, currentSize.height, null);
                }
            }
        }
    }
}
