import java.util.List;
import java.util.Iterator;
import java.util.Random;
import java.util.ArrayList;

/**
 * A simple model of a tree.
 * Trees age, reproduce, and have a 60% chance of dying if they
 * encounter fire.
 * 
 * @author Rebecca McCranie
 *         (adapted from Foxes and Rabbits 
 *         by David J. Barnes and Michael Kölling, 2016.02.29 (2))
 * @version 2020.04.01
 */
public class Tree extends Plant
{
    // Characteristics shared by all trees (class variables).
    
    // The number of turns before a tree can reproduce.
    private static final int NEW_TREE = 5;
    // The likelihood of a tree dying if a fire comes.
    private static final double FIRE_DEATH_PROBABILITY = 0.6;
    // A shared random number generator.
    private static final Random rand = Randomizer.getRandom();
    
    // Individual characteristics (instance fields).
    // The tree's age.
    private int age;
    
    /**
     * Create a tree. 
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Tree(Field field, Location location)
    {
        super(field, location);
        age = 0;
    }
    
    /**
     * A tree reproduces every 5 turns. 
     * 
     * @param field The field currently occupied.
     * @param newTrees A list to return newly born trees.
     */
    public void act(List<Plant> newTrees)
    {
        incrementAge();
        if(isAlive()) {
            giveBirth(newTrees);            
            // Move towards a source of food if found.
            Location newLocation = findFood();
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
     * Increase the age. This is only used to determine when a tree
     * can reproduce. Trees do not die of old age.
     */
    private void incrementAge()
    {
        age++;        
    }
    
    /**
     * Count the number of trees in a square. 
     * 
     * @return number of trees
     */
    private int countTrees()
    {
        int trees = 0;
        Field field = getField();
        Location currant = getLocation();
        List<Object> plantList = field.getObjectAt(currant);
        
        while(it.hasNext()) {
            plantList.add(field.getObjectAt(currant));
            if(plant instanceof Tree) {
                    trees++;
                }
        }
        return trees;
    }
    
       

      
       
    /**
     * Look for space for seedlings adjacent to the current location.
     * Move into the first available space.
     * @return Available space.
     */
    private Location findSpace()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object plant = field.getObjectAt(where);
            if(plant) {
                int count = countPlants();
                if(count < MAX_PLANTS ) { 
                    rabbit.setDead();
                    foodLevel = RABBIT_FOOD_VALUE;
                    return where;
                }
            }
        }
        return null;
    }
    
    /**
     * Check whether or not this tree is to reproduce at this step.
     * New trees will be made into free adjacent locations.
     * @param newTrees A list to return new trees.
     */
    private void spread(List<Plant> newTrees)
    {
        // New trees grow in adjacent locations.
        // Squares can have 10 plants on them.
        // Trees can replace grass if there is no where else for them
        // to grow.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int seedlings = reproduce();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Tree young = new Tree(field, loc);
            newTrees.add(young);
        }
    }
        
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    private int reproduce()
    {
        int seedlings = 0;
        if(canBreed()) {
            seedlings++;
        }
        return seedlings;
    }

    /**
     * A tree can reproduce if its age is a multiple of 5.
     */
    private boolean canBreed()
    {
        return age % NEW_TREE == 0;
    }
}
