package space.hypeo.mankomania;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.Stack;

public class StageManager {
    private Stack<Stage> stages;

    public StageManager()
    {
        stages = new Stack<Stage>();
    }

    private Stage pop()
    {
        Stage stage = stages.pop();
        Gdx.input.setInputProcessor(getCurrentStage());
        return stage;
    }

    public void push(Stage stage)
    {
        stages.push(stage);
        Gdx.input.setInputProcessor(getCurrentStage());
    }

    public Stage getCurrentStage()
    {
        return stages.peek();
    }

    public boolean remove(Stage stage)
    {
        if(stage == stages.peek())
        {
            pop();
            return true;
        }
        else
        {
            return false;
        }
    }
}
