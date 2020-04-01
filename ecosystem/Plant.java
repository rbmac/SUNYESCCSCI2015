import java.util.List;

/**
 * A class representing shared characteristics of plants.
 * 
 * @author Rebecca McCranie
 *         (adapted from Foxes and Rabbits 
 *         by David J. Barnes and Michael Kölling, 2016.02.29 (2))
 * @version 2020.04.01
 */
public abstract class Plant
{
    // Whether the plant is alive or not.
    private boolean alive;
    // The plant's field.
    private Field field;
    // The plant's position in the field.
    private Location location;
    
    /**
     * Create a new plant at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Plant(Field field, Location location)
    {
        alive = true;
        this.field = field;
        setLocation(location);
    }
    
    /**
     * Make this plant act - that is: make it do
     * whatever it wants/needs to do.
     * @param newPlants A list to receive newly born plants.
     */
    abstract public void act(List<Plant> newPlants);

    /**
     * Check whether the plant is alive or not.
     * @return true if the plant is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the plant is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the plant's location.
     * @return The plant's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * The plant will spread after a certain number of turns.
     * It will spread to the first adjacent square with available space.
     */
    public Location spread()
    {
       // plant needs to be alive, ready to reproduce (# of turns) 
       // square needs to have fewer than 10 plants, BUT a tree can kill 
       // a grass plant
    }
    
    
    /**
     * Return the plant's field.
     * @return The plant's field.
     */
    protected Field getField()
    {
        return field;
    }
    
    /**
     * Count the number of grasses in a square. 
     * 
     * @return number of grasses
     */
    private int countGrasses()//issues...see comment below in countTrees()
    {
        int grasses = 0;
        Field field = getField();
        Iterator<Location> it;
        List<Object> plantList = new List<>();
        while(it.hasNext()) {
            plantList.add(field.getObjectAt(currant));
            if(plant instanceof Grass) {
                    grasses++;
                }
        }
        return grasses;
    }
    
    /**
     * Count the number of trees in a square. 
     * 
     * @return number of trees
     */
    private int countTrees()//has issues!! Should the counting happen in the 
                            //plant class? Or in the field? Or new grid class?
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
     * Count total number of plants in a square and determine whether the 
     * square is available for a new plant.
     */
    public boolean availableSquare(Location location)
    {
        boolean available = false;
        if(countTrees() + countGrasses() <= 10) {
            available = true;
        }
        return available;
    }
}
