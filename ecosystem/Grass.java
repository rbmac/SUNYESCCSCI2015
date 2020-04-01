import java.util.List;
import java.util.Iterator;
import java.util.Random;
import java.util.ArrayList;

/**
 * A simple model of grass.
 * Grass spreads, never ages, is eaten by deer, and has a 75% chance
 * of dying if a fire comes.
 * 
 * @author Rebecca McCranie
 *         (adapted from Foxes and Rabbits 
 *         by David J. Barnes and Michael Kölling, 2016.02.29 (2))
 * @version 2020.04.01
 */
public class Grass extends Plant
{
    // Characteristics shared by all grasses (class variables).
    
    // How many turns must pass before grass spreads to an adjacent square.
    private static final int NEW_GRASS = 2;
    // The likelihood of a grass dying in a fire.
    private static final double FIRE_DEATH_PROBABILITY = 0.75;
    // A shared random number generator to control direction of spread.
    private static final Random rand = Randomizer.getRandom();
    
    // Individual characteristics (instance fields).
    private int age;
    
    /**
     * Create grass in one square. 
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Grass(Field field, Location location)
    {
        super(field, location);
        age = 0;
    }
    
    /**
     * Plants spread to an adjacent square every ? turns, depending on the 
     * plant, as long as it is alive. Squares can contain up to 10 plants.
     * Trees can replace grass plants.
     * 
     * @param field The field currently occupied.
     * @param newPlants A list to return newly born plants.
     */
    public void act(List<Plant> newPlants)
    {
        if(isAlive() && canSpread()) {
            spread(newPlants);            
            // Move towards available square (up to 10 plants per square).
            // has issues...based on the findFood method for the Fox class
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }
    
    /**
     * Grass can reproduce if its age is a multiple of 2.
     */
    private boolean canSpread()
    {
        return age % NEW_GRASS == 0;
    }
   
}
