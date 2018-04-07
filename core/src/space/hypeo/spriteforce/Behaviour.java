package space.hypeo.spriteforce;

/**
 * Created by pichlermarc on 06.04.2018.
 */


/**
 * Class specifies the behaviour of a GameObject.
 */
public abstract class Behaviour {

    protected GameObject gameObject;

    /**
     * Sets the GameObject that is controlled by this behaviour.
     * @param gameObject
     */
    public void setGameObject(GameObject gameObject)
    {
        this.gameObject = gameObject;
    }

    /**
     * Initializes the behaviour.
     */
    public Behaviour()
    {
        initialize();
    }

    /**
     * Initialize method, run on creation of the Behaviour class.
     */
    public abstract void initialize();

    /**
     * Update method, run every frame. Contains updates to the appearance, etc...
     * @param deltaTime
     */
    public abstract void update(float deltaTime);
}
