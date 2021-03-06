package space.hypeo.mankomania;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.mockito.Mockito;

public abstract class GameTest {
    private static Application application;

    @BeforeClass
    public static void init() {
        // Note that we don't need to implement any of the listener's methods
        application = new HeadlessApplication(new ApplicationListener() {
            @Override
            public void create() {
                //Emtpy method, application should run Headless.
            }

            @Override
            public void resize(int width, int height) {
                //Emtpy method, application should run Headless.
            }

            @Override
            public void render() {
                //Emtpy method, application should run Headless.
            }

            @Override
            public void pause() {
                //Emtpy method, application should run Headless.
            }

            @Override
            public void resume() {
                //Emtpy method, application should run Headless.
            }

            @Override
            public void dispose() {
                //Emtpy method, application should run Headless.
            }
        });

        Gdx.gl20 = Mockito.mock(GL20.class);
        Gdx.gl = Gdx.gl20;
    }

    @AfterClass
    public static void cleanUp() {
        // Exit the application first
        application.exit();
        application = null;
    }
}