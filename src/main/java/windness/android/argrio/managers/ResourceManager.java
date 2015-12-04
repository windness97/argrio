package windness.android.argrio.managers;

import android.graphics.Typeface;

import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import windness.android.argrio.Circle;
import windness.android.argrio.MainActivity;

/**
 * Created by windness on 2015/12/1.
 */
public class ResourceManager {

    MainActivity mContext;

//    public BitmapTextureAtlas mBitmapTextureAtlas;
//    public ITextureRegion mFaceTextureRegion;

    public BitmapTextureAtlas mOnScreenControlTexture;
    public ITextureRegion mOnScreenControlBaseTextureRegion;
    public ITextureRegion mOnScreenControlKnobTextureRegion;
    public Font mFont;


    public ResourceManager(MainActivity context){
        this.mContext = context;
    }

    public void createResources() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("img/");

        Circle.onCreateResources(mContext);
//        this.mBitmapTextureAtlas = new BitmapTextureAtlas(mContext.getTextureManager(), 64, 64, TextureOptions.BILINEAR);
//        this.mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, mContext, "circle.png", 0, 0);
//        this.mBitmapTextureAtlas.load();

        this.mOnScreenControlTexture = new BitmapTextureAtlas(mContext.getTextureManager(), 256, 128, TextureOptions.BILINEAR);
        this.mOnScreenControlBaseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mOnScreenControlTexture, mContext, "onscreen_control_base.png", 0, 0);
        this.mOnScreenControlKnobTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mOnScreenControlTexture, mContext, "onscreen_control_knob.png", 128, 0);
        this.mOnScreenControlTexture.load();
//        mOnScreenControlBaseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset()

        mFont = FontFactory.create(mContext.getFontManager(), mContext.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD_ITALIC), 32f / 1280 * mContext.WIDTH);
        mFont.load();
    }
}
