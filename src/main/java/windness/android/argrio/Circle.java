package windness.android.argrio;

import android.graphics.Typeface;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.modifier.IModifier;

/**
 * Created by windness on 2015/12/2.
 */
public class Circle {
    //    private static float TEXT_RATIO = (float)Math.sqrt(2000 / STANDARD_WEIGHT);
    private Text text;
    public static Font mFont;

    private static final float RATIO = 1f;

    public static final float STANDARD_WEIGHT = 2000;

    public static final int FLAG_CREATING = 0;
    public static final int FLAG_CREATED = 1;

    public static final int FLAG_DYING = 2;

    public static final int FLAG_DEAD = 3;
    private int state = FLAG_CREATED;  public int getState() {return state;}
    private float firstWeight;
    private float weight;

    public float getWeight() {return weight;}

    public void setWeight(float weight) {this.weight = weight;}

    public float getR() {
        return getSprite().getHeight() / 2 * getSprite().getScaleX();
    }
    static public float getR(float weight) {
        float standardR = REGION.getHeight() / 2;
        float r = standardR * rRatioByWeight(weight);
        return r;
    }
    private float red = -1;
    private float green = -1;
    private float blue = -1;
    public float getRed() {return red;}
    public float getGreen() {return green;}

    public float getBlue() {return blue;}

    private static MainActivity mContext; public MainActivity getContext() {return mContext;}
    static private Scene mScene;
    private Sprite circle;  public Sprite getSprite() {return circle;}

    public static BitmapTextureAtlas mBitmapTextureAtlas;
    public static ITextureRegion mFaceTextureRegion;
    public static BitmapTextureAtlas bigBitmapTextureAtlas;
    public static ITextureRegion bigFaceTextureRegion;
    private static ITextureRegion REGION;

    public static void onCreateResources(MainActivity context) {
        mContext = context;
        mScene = context.getScene();

        mBitmapTextureAtlas = new BitmapTextureAtlas(mContext.getTextureManager(), 64, 64, TextureOptions.BILINEAR);
        mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, mContext, "circle.png", 0, 0);
        mBitmapTextureAtlas.load();

        bigBitmapTextureAtlas = new BitmapTextureAtlas(mContext.getTextureManager(), 500, 500, TextureOptions.BILINEAR);
        bigFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bigBitmapTextureAtlas, mContext, "my circle.png", 0, 0);
        bigBitmapTextureAtlas.load();

        REGION = bigFaceTextureRegion;

        mFont = FontFactory.create(mContext.getFontManager(), mContext.getTextureManager(), 1024, 1024, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32 / 1280 * mContext.getWIDTH());
        mFont.load();
    }

    //with random color
    public Circle(float x, float y, float firstWeight) {
        createSprite(x, y, firstWeight, true);

        this.firstWeight = weight = firstWeight;
    }

    //ask for particular color
    public Circle(float x, float y, float firstWeight, float red, float green, float blue, boolean animate) {
        this.red = red;
        this.green = green;
        this.blue = blue;

        createSprite(x, y, firstWeight, animate);

        this.firstWeight = weight = firstWeight;
    }

    private void createSprite(float x, float y, float firstWeight, boolean animate) {
        circle = new Sprite(x - REGION.getWidth() / 2, y - REGION.getHeight() / 2, REGION, mContext.getVertexBufferObjectManager());

//        text = new Text(REGION.getWidth() / 2, REGION.getHeight() / 2, mContext.mResourceManager.mFont, String.valueOf((int)firstWeight), 8, mContext.getVertexBufferObjectManager());
//        text = new Text(REGION.getWidth() / 2, REGION.getHeight() / 2, mFont, String.valueOf((int)firstWeight), 8, mContext.getVertexBufferObjectManager());
//        text.setScale(TEXT_RATIO * (float)Math.sqrt(firstWeight / STANDARD_WEIGHT));
//        text.setColor(1, 1, 1, 0.5f);
//        circle.attachChild(text);


        float ratio = rRatioByWeight(firstWeight);
        circle.setScale(ratio);
        circle.setAlpha(0.5f);

        if (animate) {
            state = FLAG_CREATING;
            final SequenceEntityModifier circleModifier = new SequenceEntityModifier(
                    new IEntityModifier.IEntityModifierListener() {
                        @Override
                        public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
                            state = FLAG_CREATING;
                        }

                        @Override
                        public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                            state = FLAG_CREATED;
                        }
                    },
                    new ScaleModifier(0.2f, 0, ratio * 1.5f),
                    new ScaleModifier(0.15f, ratio * 1.5f, ratio * 0.8f),
                    new ScaleModifier(0.15f, ratio * 0.8f, ratio)
            );
            circle.registerEntityModifier(circleModifier);
        }

        if (red == -1) {
            red = (float) (Math.random());
            green = (float) (Math.random());
            blue = (float) (Math.random());
        }

        circle.setColor(red, green, blue);
    }

    public void destroyCircle() {

        float ratio = rRatioByWeight(weight);

        final SequenceEntityModifier circleModifier = new SequenceEntityModifier(
                new IEntityModifier.IEntityModifierListener() {
                    @Override
                    public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
                        state = FLAG_DYING;
                    }

                    @Override
                    public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                        state = FLAG_DEAD;
                    }
                },
                new ScaleModifier(0.3f, ratio, 0)
        );
        circle.registerEntityModifier(circleModifier);
    }

    public boolean ableToDetach() {
        return state == FLAG_DEAD;
    }

    public void scale(float n) {
        weight += n;
        circle.setScale(rRatioByWeight(weight));
//        text.setText(String.valueOf((int) weight));
//        text.setX(circle.getX() - REGION.getWidth() / 2);
//        text.setY(circle.getY() - REGION.getHeight() / 2);
//        text.setScale(rRatioByWeight((int) weight));
//        text.setScale(TEXT_RATIO * (float)Math.sqrt(weight / STANDARD_WEIGHT));

//        text.setText(String.valueOf((int)weight));
    }

    static private float rRatioByWeight(float weight) {
        return (float)Math.sqrt(weight / STANDARD_WEIGHT) * RATIO;
    }

    public float getX() {
        return getSprite().getX() + REGION.getWidth() / 2;
    }

    public float getY() {
        return getSprite().getY() + REGION.getHeight() / 2;
    }
}
