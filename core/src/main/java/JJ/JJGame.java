package JJ;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class JJGame extends BaseGame {
	@Override
	public void create() {
		super.create();
		setScreen(new LevelScreen());
	}
}