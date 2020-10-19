package JJ;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public abstract class BaseScreen implements Screen, InputProcessor {

    protected Stage mainStage;
    protected Stage uiStage;
    protected Table uiTable;

    protected boolean paused;


    public BaseScreen() {

        this.mainStage = new Stage();
        this.uiStage   = new Stage();

        uiTable = new Table();
        uiTable.setFillParent(true);
        uiStage.addActor(uiTable);

        paused = false;

        initialize();
    }

    public abstract void initialize();

    public abstract void update(float dt);



    @Override
    public void render(float dt) {

        uiStage.act(dt);

        if (!paused) {
            mainStage.act(dt);

            update(dt);


        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mainStage.draw();
        uiStage.draw();
    }

    //methods required by Screen interface
    @Override
    public void show() {

        InputMultiplexer inputMultiplexer = (InputMultiplexer) Gdx.input.getInputProcessor();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(uiStage);
        inputMultiplexer.addProcessor(mainStage);

    }
    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

        paused = true;


    }

    @Override
    public void resume() {

        paused = false;

    }

    @Override
    public void hide() {

        InputMultiplexer inputMultiplexer = (InputMultiplexer)Gdx.input.getInputProcessor();
        inputMultiplexer.removeProcessor(this);
        inputMultiplexer.removeProcessor(uiStage);
        inputMultiplexer.removeProcessor(mainStage);
    }

    @Override
    public void dispose() {

    }

    public boolean isTouchDownEvent(Event e){

        return (e instanceof InputEvent) && ((InputEvent)e).getType().equals(Type.touchDown);
    }

    //methods required by InputProcessor interface

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
