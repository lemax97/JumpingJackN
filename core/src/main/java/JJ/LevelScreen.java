package JJ;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Input.Keys;

/** First screen of the application. Displayed after the application is created. */
public class LevelScreen extends BaseScreen {

	Koala jack;

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

	}

	@Override
	public void update(float dt) {

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