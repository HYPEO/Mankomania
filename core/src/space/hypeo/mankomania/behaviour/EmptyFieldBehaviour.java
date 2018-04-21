package space.hypeo.mankomania.behaviour;

import space.hypeo.mankomania.GameState;
import space.hypeo.networking.IPlayerConnector;

/**
 * EmptyFieldBehaviour, a Placeholder Field.
 */
public class EmptyFieldBehaviour extends FieldBehaviour {

    /**
     * Creates a new instance of the EmptyFieldBehaviour Class.
     *
     * @param connector Connector to the other players.
     * @param state     Current GameState.
     */
    public EmptyFieldBehaviour(IPlayerConnector connector, GameState state) {
        super(connector, state);
    }

    @Override
    public void trigger(PlayerBehaviour player) {

    }

    @Override
    public void initialize() {

    }

    @Override
    public void update(float deltaTime) {
        //Does nothing and just stands there.
    }
}
