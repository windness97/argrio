package windness.android.argrio;

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

    public static final int STANDARD_WEIGHT = 20;

    private float firstWeight;
    private float weight;   public float getWeight() {return weight;}

    //    private float

    private static MainActivity mContext;

    private Scene mScene;

    private Sprite circle;  public Sprite getSprite() {return circle;}
//    private PhysicsHandler physicsHandler;  public PhysicsHandler getPhysicsHandler() {return physicsHandler;}

    public static BitmapTextureAtlas mBitmapTextureAtlas;
    public static ITextureRegion mFaceTextureRegion;

    public static void onCreateResources(MainActivity context) {
        mContext = context;

        mBitmapTextureAtlas = new BitmapTextureAtlas(mContext.getTextureManager(), 64, 64, TextureOptions.BILINEAR);
        mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, mContext, "circle.png", 0, 0);
        mBitmapTextureAtlas.load();
    }

    public Circle(float firstWeight) {
        final float centerX = (mContext.getWIDTH() - mFaceTextureRegion.getWidth()) / 2;
        final float centerY = (mContext.getHEIGHT() - mFaceTextureRegion.getHeight()) / 2;
        circle = new Sprite(centerX, centerY, mFaceTextureRegion, mContext.getVertexBufferObjectManager());

//        physicsHandler = new PhysicsHandler(circle);
//        circle.registerUpdateHandler(physicsHandler);

//        firstWeight = weight = mFaceTextureRegion.getHeight();
        this.firstWeight = weight = firstWeight;
    }

    public Circle(float x, float y, float firstWeight) {
        circle = new Sprite(x, y, mFaceTextureRegion, mContext.getVertexBufferObjectManager());
//        circle.setScale((float)Math.sqrt((double)(firstWeight / STANDARD_WEIGHT)));
        circle.setScale((float) Math.cbrt((double) (firstWeight / STANDARD_WEIGHT)));

        circle.setColor((float)(Math.random()), (float)(Math.random()), (float)(Math.random()));

//        physicsHandler = new PhysicsHandler(circle);
//        circle.registerUpdateHandler(physicsHandler);

//        firstWeight = weight = mFaceTextureRegion.getHeight();
        this.firstWeight = weight = firstWeight;
    }

    public void scale(float n) {
        weight += n;
//        circle.setScale((float)Math.sqrt((double)(weight / STANDARD_WEIGHT)));
        circle.setScale((float)Math.cbrt((double) (weight / STANDARD_WEIGHT)));
    }

//    public boolean collidedWithPoint(Sprite point) {
//        if (checkIfcollides(circle, point));
//        return false;
//    }
//
//    private boolean checkIfcollides(Sprite s1, Sprite s2) {
//        if ((s1.getHeight() / 2 * s1.getScaleX() + s2.getHeight() / 2 * s2.getScaleX()) * (s1.getHeight() / 2 * s1.getScaleX() + s2.getHeight() / 2 * s2.getScaleX()) >=
//                (s1.getX() - s2.getX()) * (s1.getX() - s2.getX()) +
//                        (s1.getY() - s2.getY()) * (s1.getY() - s2.getY())
//                ) {
//            return true;
//        }else {
//            return false;
//        }
//    }

}
