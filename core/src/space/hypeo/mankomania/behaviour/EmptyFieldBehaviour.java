package space.hypeo.mankomania.behaviour;

import space.hypeo.mankomania.GameState;
import space.hypeo.networking.IPlayerConnector;

public class EmptyFieldBehaviour extends FieldBehaviour {

    public EmptyFieldBehaviour(IPlayerConnector connector, GameState state)
    {
        super(connector,state);
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
