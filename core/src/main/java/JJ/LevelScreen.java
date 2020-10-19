package JJ;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/** First screen of the application. Displayed after the application is created. */
public class LevelScreen extends BaseScreen {

	Koala jack;

	boolean gameOver;
	int coins;
	float time;
	Label coinLabel;
	Table keyTable;
	Label timeLabel;
	Label messageLabel;

	@Override
	public void initialize() {

		TilemapActor tma = new TilemapActor("assets/map.tmx", mainStage);

		for (MapObject obj : tma.getRectangleList("Solid")){

			MapProperties props = obj.getProperties();
			new Solid( (float)props.get("x"), (float)props.get("y"),
					(float)props.get("width"), (float)props.get("height"),
					mainStage);
		}

		MapObject startPoint = tma.getRectangleList("start").get(0);
		MapProperties startProps = startPoint.getProperties();
		jack = new Koala( (float) startProps.get("x"), (float)startProps.get("y"), mainStage);

		gameOver = false;
		coins = 0;
		time = 60;
		coinLabel = new Label("Coins: " + coins, BaseGame.labelStyle);
		coinLabel.setColor(Color.GOLD);
		keyTable = new Table();
		timeLabel = new Label("Time: " + (int)time, BaseGame.labelStyle);
		timeLabel.setColor(Color.LIGHT_GRAY);
		messageLabel = new Label("Message", BaseGame.labelStyle);
		messageLabel.setVisible(false);

		for (MapObject obj : tma.getTileList("Flag")) {

			MapProperties props = obj.getProperties();
			new Flag( (float)props.get("x"), (float)props.get("y"), mainStage);
		}

		for (MapObject obj : tma.getTileList("Coin")){

			MapProperties props = obj.getProperties();
			new Coin( (float)props.get("x"), (float)props.get("y"), mainStage );
		}

		uiTable.pad(20);
		uiTable.add(coinLabel);
		uiTable.add(keyTable).expandX();
		uiTable.add(timeLabel);
		uiTable.row();
		uiTable.add(messageLabel).colspan(3).expandY();

	}

	@Override
	public void update(float dt) {

		if (gameOver)
			return;

		for (BaseActor coin : BaseActor.getList(mainStage, "JJ.Coin")) {

			if ( jack.overlaps(coin) ){

				coins++;
				coinLabel.setText("Coins: " + coins);
				coin.remove();
			}
		}

		for (BaseActor flag : BaseActor.getList(mainStage, "JJ.Flag")) {

			if ( jack.overlaps(flag)) {

				messageLabel.setText("You Win!");
				messageLabel.setColor(Color.LIME);
				messageLabel.setVisible(true);
				jack.remove();
				gameOver = true;
			}
		}

		for (BaseActor actor : BaseActor.getList(mainStage, "JJ.Solid")) {

			Solid solid = (Solid) actor;

			if ( jack.overlaps(solid) && solid.isEnabled()){

				Vector2 offset = jack.preventOverlap(solid);

				if (offset != null) {

					// collided in X direction
					if ( Math.abs(offset.x) > Math.abs(offset.y))
						jack.velocityVec.x = 0;
					else // collided in Y direction
						jack.velocityVec.y = 0;
				}
			}
		}
	}

	@Override
	public boolean keyDown(int keyCode) {

		if (keyCode == Keys.SPACE) {

			if ( jack.isOnSolid() ) {

				jack.jump();
			}
		}
		return false;
	}
}