package space.hypeo.mankomania;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Map extends ApplicationAdapter {
    SpriteBatch batch;
    int[] row1;
    int[] row2;
    int[] row3;
    int[] row4;
    int celllength = 9;
    Texture tile1;
    Texture tile2;
    Texture tile3;
    int row1_height = 200;
    int row1_width = 8;
    int cellheight = 35;
    int cellwidth = 35;
    private OrthographicCamera camera;
    long starttime;
    int playerfield = 0;
    int stage = 0;


    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();

        tile1 = new Texture(Gdx.files.internal("wall.jpg"));

        tile2 = new Texture(Gdx.files.internal("tile.png"));

        generateMap();
        row1[0] = 2;

        starttime = System.currentTimeMillis();

    }

    private void generateMap() {
        row1 = new int[celllength];
        row2 = new int[celllength];
        row3 = new int[celllength];
        row4 = new int[celllength];

        for (int i = 0; i < celllength; i++) {
            row1[i] = 1;
            row2[i] = 1;
            row3[i] = 1;
            row4[i] = 1;
        }


    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (((System.currentTimeMillis() - starttime) / 1000) > 1) {
            starttime = System.currentTimeMillis();
            goNext((int) (6.0 * Math.random()) + 1);
        }

        camera.update();
        batch.setProjectionMatrix(camera.combined);


        batch.begin();


        for (int i = 0; i < celllength; i++) {
            if (row1[i] == 1 | row1[i] == 0) {
                batch.draw(tile1, row1_height, row1_width + 45 * i + 1 * i, cellwidth, cellheight);
            } else if (row1[i] == 2) {
                batch.draw(tile2, row1_height + cellheight / 4, row1_width + 45 * i + 1 * i + cellwidth / 4, 17, 17);
            }
        }

        for (int i = 0; i < celllength; i++) {
            if (row2[i] == 1 | row1[i] == 0) {
                batch.draw(tile1, row1_height + 45 * i + 1 * i, 480 - row1_width - 45, cellwidth, cellheight);
            } else if (row2[i] == 2) {
                batch.draw(tile2, row1_height + 45 * i + 1 * i, 480 - row1_width - 45, 17, 17);
            }
        }
        for (int i = 0; i < celllength; i++) {
            if (row3[i] == 1 | row3[i] == 0) {
                batch.draw(tile1, 800 - row1_height + 15, 480 - row1_width - 45 * i - 1 * i - 45, cellwidth, cellheight);
            } else if (row3[i] == 2) {
                batch.draw(tile2, 800 - row1_height + 15, 480 - row1_width - 45 * i - 1 * i - 45, 17, 17);
            }
        }

        for (int i = 0; i < celllength; i++) {
            if (row4[i] == 1 | row4[i] == 0) {
                batch.draw(tile1, 800 - row1_height + 15 - 45 * i - 1 * i, row1_width, cellwidth, cellheight);
            } else if (row4[i] == 2) {
                batch.draw(tile2, 800 - row1_height + 15 - 45 * i - 1 * i, row1_width, 17, 17);
            }
        }

        batch.end();

    }

    private void goNext(int number) {
        int playerfieldNew = playerfield;
        playerfieldNew += number;
        int stageNew = stage;
        while (true) {
            if (playerfieldNew > 8) {
                playerfieldNew = 0;
                stageNew++;
                if (stageNew > 3)
                    stageNew = 0;
            } else {
                System.out.println(playerfield + " " + stage + " " + playerfieldNew + " " + stageNew);
                newPosition(playerfield, stage, playerfieldNew, stageNew);
                break;
            }
        }

    }

    private void newPosition(int playerfield, int stage, int playerfieldNew, int stageNew) {
        switch (stage) {
            case 0:
                System.out.println("0");
                row1[playerfield] = 1;
                break;
            case 1:
                System.out.println("1");
                row2[playerfield] = 1;
                break;
            case 2:
                System.out.println("2");
                row3[playerfield] = 1;
                break;
            case 3:
                System.out.println("3");
                row4[playerfield] = 1;
                break;
        }
        switch (stageNew) {
            case 0:
                row1[playerfieldNew] = 2;
                System.out.println("0 Set");
                break;
            case 1:
                row2[playerfieldNew] = 2;
                System.out.println("1 Set");
                break;
            case 2:
                row3[playerfieldNew] = 2;
                System.out.println("2 Set");
                break;
            case 3:
                row4[playerfieldNew] = 2;
                System.out.println("3 Set");
                break;
        }
        this.playerfield = playerfieldNew;
        this.stage = stageNew;
        render();

    }

    @Override
    public void dispose() {
        batch.dispose();
    }


}




