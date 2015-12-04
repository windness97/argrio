package windness.android.argrio;

import android.opengl.GLES20;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.util.FPSCounter;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import windness.android.argrio.managers.ResourceManager;
import windness.android.argrio.managers.SceneManager;

public class MainActivity extends BaseGameActivity {

    public Engine getEngion() {return this.mEngine;}

    public float WIDTH = 1280;
    float HEIGHT = 720;

    public float getWIDTH() {return WIDTH;}
    public float getHEIGHT() {return HEIGHT;}

    public FPSCounter fpsCounter;

    public SceneManager mSceneManager;
    public ResourceManager mResourceManager;

    public Camera mCamera;
    private EngineOptions mEngineOptions;

    private Scene mScene;

    public Circle circle;

    //resources

    private BitmapTexture circleTexture;
    private TextureRegion circleRegion;


    @Override
    public EngineOptions onCreateEngineOptions() {
        mCamera = new Camera(0, 0, WIDTH, HEIGHT) {

            private int mPipeSpawnCounter;

            @Override
            public void onUpdate(float pSecondsElapsed) {
            }

            private void ready() {
            }

            private void die() {
            }

            private void play() {
            }
        };
        mEngineOptions = new EngineOptions(
                true,
                ScreenOrientation.LANDSCAPE_FIXED,
                new RatioResolutionPolicy(WIDTH, HEIGHT),
                mCamera
        );
        mEngineOptions.getAudioOptions().setNeedsMusic(true);
        mEngineOptions.getAudioOptions().setNeedsSound(true);

        return mEngineOptions;
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("img/");

        mResourceManager = new ResourceManager(this);
        mResourceManager.createResources();
//        circleTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset()
//        circleTexture = new BitmapTexture()
        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {
        Background mBackground = new Background(0.2f, 0.6f, 0.8f);

        fpsCounter = new FPSCounter();

        mSceneManager = new SceneManager(this, mResourceManager, mBackground);
        mScene = mSceneManager.createScene();

        pOnCreateSceneCallback.onCreateSceneFinished(mScene);
    }

    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
        circle = new Circle();
        mScene.attachChild(circle.getSprite());

        final AnalogOnScreenControl analogOnScreenControl = new AnalogOnScreenControl(
                0,
                HEIGHT - mResourceManager.mOnScreenControlBaseTextureRegion.getHeight(),
                this.mCamera,
                mResourceManager.mOnScreenControlBaseTextureRegion, mResourceManager.mOnScreenControlKnobTextureRegion,
                0.1f, 200, getVertexBufferObjectManager(),
                new AnalogOnScreenControl.IAnalogOnScreenControlListener() {
                    @Override
                    public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {
                        circle.getPhysicsHandler().setVelocity(pValueX * 10000 / circle.getWeight(), pValueY * 10000 / circle.getWeight());
//                physicsHandler.setAcceleration(pValueX * 100, pValueY * 100);

//                        final Body faceBody = (Body) faces.getUserData();
//                        faceBody.applyForce(pValueX * 50, pValueY * 50, faceBody.getWorldCenter().x, faceBody.getWorldCenter().y);

//                if (physicsHandler.getVelocityX() * physicsHandler.getVelocityX() + physicsHandler.getVelocityY() * physicsHandler.getVelocityY() >= 10 * 10) {
//                    faceBody.setLinearVelocity(new Vector2(physicsHandler.getVelocityX(), physicsHandler.getVelocityY()));
//                } else {
//                    faceBody.applyForce(pValueX * 50, pValueY * 50, faceBody.getWorldCenter().x, faceBody.getWorldCenter().y);
//                }

//                faceBody.applyForce(pValueX * 50, pValueY * 50, faceBody.getWorldCenter().x, faceBody.getWorldCenter().y);

//                Vector2 vector2 = faceBody.getLinearVelocity().add(physicsHandler.getAccelerationX() + pValueX * 50, physicsHandler.getAccelerationY() + pValueY * 50);
//                faceBody.setLinearVelocity(50, 50);
                    }

                    @Override
                    public void onControlClick(final AnalogOnScreenControl pAnalogOnScreenControl) {
//                        circle.registerEntityModifier(new SequenceEntityModifier(new ScaleModifier(0.25f, 1, 1.5f), new ScaleModifier(0.25f, 1.5f, 1)));
                        circle.scale(5);
//                        circle.registerEntityModifier();
                    }
                });
//        analogOnScreenControl.setScaleCenter(0, HEIGHT * 2 / 3);
//        analogOnScreenControl.setScale(HEIGHT / 3 / mResourceManager.mOnScreenControlBaseTextureRegion.getHeight());
        analogOnScreenControl.getControlBase().setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        analogOnScreenControl.getControlBase().setAlpha(0.5f);
        analogOnScreenControl.getControlBase().setScaleCenter(0, 128);
//        analogOnScreenControl.getControlBase().setScale(1.25f);
//        analogOnScreenControl.getControlKnob().setScale(1.25f);
        analogOnScreenControl.getControlBase().setScale(2f);
        analogOnScreenControl.getControlKnob().setScale(2f);
        analogOnScreenControl.refreshControlKnobPosition();

//        mScene.attachChild(analogOnScreenControl);
        mScene.setChildScene(analogOnScreenControl);


        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }
}
