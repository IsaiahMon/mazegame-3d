package downloads.gamecreate.game.level;

import java.util.ArrayList;
import java.util.List;

import downloads.gamecreate.game.graphics.Sprite;

public class Block {

	public boolean solid = false;

	public static Block solidWall = new SolidBlock();
	
	public List<Sprite> sprites = new ArrayList<Sprite>();
	
	public void addSprite(Sprite sprite)
	{
		sprites.add(sprite);
	}

}


//WORK ON THE VISIBILITY OF BLOCK ISSUE IN RENDER3D
