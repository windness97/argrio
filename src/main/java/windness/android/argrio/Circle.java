package windness.android.argrio;

import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

/**
 * Created by windness on 2015/12/2.
 */
public class Circle {

    private float firstWeight;
    private float weight;   public float getWeight() {return weight;}

    //    private float

    private static MainActivity mContext;

    private Scene mScene;

    private Sprite circle;  public Sprite getSprite() {return circle;}
    private PhysicsHandler physicsHandler;  public PhysicsHandler getPhysicsHandler() {return physicsHandler;}

    public static BitmapTextureAtlas mBitmapTextureAtlas;
    public static ITextureRegion mFaceTextureRegion;

    public static void onCreateResources(MainActivity context) {
        mContext = context;

        mBitmapTextureAtlas = new BitmapTextureAtlas(mContext.getTextureManager(), 64, 64, TextureOptions.BILINEAR);
        mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, mContext, "circle.png", 0, 0);
        mBitmapTextureAtlas.load();
    }

    public Circle() {
        final float centerX = (mContext.getWIDTH() - mFaceTextureRegion.getWidth()) / 2;
        final float centerY = (mContext.getHEIGHT() - mFaceTextureRegion.getHeight()) / 2;
        circle = new Sprite(centerX, centerY, mFaceTextureRegion, mContext.getVertexBufferObjectManager());

        physicsHandler = new PhysicsHandler(circle);
        circle.registerUpdateHandler(physicsHandler);

//        firstWeight = weight = mFaceTextureRegion.getHeight();
        firstWeight = weight = 20;
    }

    public void scale(float n) {
        weight += n;
        circle.setScale((float)Math.sqrt((double)(weight / firstWeight)));
    }
}
