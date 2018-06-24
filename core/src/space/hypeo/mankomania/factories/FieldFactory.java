package space.hypeo.mankomania.factories;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.Random;

import space.hypeo.mankomania.StageFactory;
import space.hypeo.mankomania.StageManager;
import space.hypeo.mankomania.actors.fields.ClickerFieldActor;
import space.hypeo.mankomania.actors.fields.FieldActor;
import space.hypeo.mankomania.actors.map.DetailActor;
import space.hypeo.mankomania.actors.fields.BuildHotel;
import space.hypeo.mankomania.actors.fields.EmptyFieldActor;
import space.hypeo.mankomania.actors.fields.LoseMoneyFieldActor;
import space.hypeo.mankomania.actors.fields.LotteryFieldGet;
import space.hypeo.mankomania.actors.fields.LotteryFieldPay;
import space.hypeo.mankomania.actors.fields.HorseRaceFieldActor;

/**
 * Created by pichlermarc on 31.05.2018.
 */
public class FieldFactory {
    private DetailActor detailActor;
    private StageManager stageManager;
    private StageFactory stageFactory;

    public FieldFactory(DetailActor detailActor, StageManager stageManager, StageFactory stageFactory)
    {
        this.detailActor = detailActor;
        this.stageManager = stageManager;
        this.stageFactory = stageFactory;
    }

    public FieldActor generateField(int fieldIndex, float xDirection, float yDirection, float xMargin, float yMargin, Stage parentStage) {
        Random randomGenerator = new Random();
        int random = randomGenerator.nextInt(9);
        // Create new Field
        FieldActor currentField;

        if (random == 0) {
            currentField = new BuildHotel(xMargin + (fieldIndex * xDirection), yMargin + (fieldIndex * yDirection), new Texture("transparent.png"), randomGenerator.nextInt(10), detailActor, stageManager, stageFactory);
                    } else if (random >= 1 && random < 2) {
                        currentField = new LoseMoneyFieldActor(xMargin + (fieldIndex * xDirection), yMargin + (fieldIndex * yDirection), new Texture("6dice.png"), randomGenerator.nextInt(10), detailActor);
                   } else if (random >= 2 && random < 3) {
                        currentField = new EmptyFieldActor(xMargin + (fieldIndex * xDirection), yMargin + (fieldIndex * yDirection), new Texture("transparent.png"), 0, detailActor);
                    } else if (random >= 3 && random < 4) {
                        currentField = new LotteryFieldGet(xMargin + (fieldIndex * xDirection), yMargin + (fieldIndex * yDirection), randomGenerator.nextInt(10), new Texture("transparent.png"), detailActor, stageManager, stageFactory);
                    } else if (random >= 4 && random < 5) {
                       currentField = new LotteryFieldPay(xMargin + (fieldIndex * xDirection), yMargin + (fieldIndex * yDirection), randomGenerator.nextInt(10), new Texture("transparent.png"), detailActor, stageManager, stageFactory);
                    } else if (random >= 6 && random < 7){
                        currentField = new HorseRaceFieldActor(xMargin + (fieldIndex * xDirection), yMargin + (fieldIndex * yDirection), 0, new Texture("transparent.png"), detailActor, stageManager, stageFactory);
                    } else {
                        currentField = new ClickerFieldActor(xMargin + (fieldIndex * xDirection), yMargin + (fieldIndex * yDirection), new Texture("transparent.png"), randomGenerator.nextInt(10), detailActor, stageManager, stageFactory);
                    }

        return currentField;
    }

    /**
     * Generates fields and links them.
     *
     * @param amount     Amount of fields that should be generated using this pattern.
     * @param xDirection Field spacing w.r.t x-Axis.
     * @param yDirection Field spacing w.r.t y-Axis.
     * @param xMargin    Where field creation should begin w.r.t. x-Axis.
     * @param yMargin    Where field creation should begin w.r.t. y-Axis.
     * @param firstField The row of fields will be linked to this one.
     * @return The last field on this list of fields (to use for linking).
     */
    public FieldActor generateFieldLine(int amount,
                                         float xDirection,
                                         float yDirection,
                                         float xMargin,
                                         float yMargin,
                                         FieldActor firstField,
                                         Stage parentStage) {
        FieldActor previousField = firstField;
        for (int i = 1; i <= amount; i++) {
            FieldActor currentField = this.generateField(i, xDirection, yDirection, xMargin, yMargin, parentStage);
            parentStage.addActor(currentField);

            // Reference the next field from the previous one.
            previousField.setNextField(currentField);

            // Remember the current field for next iteration.
            previousField = currentField;
        }

        return previousField;
    }
}
