package windness.android.argrio;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.util.FPSCounter;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.ui.activity.BaseGameActivity;

import java.util.ArrayList;

import windness.android.argrio.managers.ResourceManager;
import windness.android.argrio.managers.SceneManager;

public class MainActivity extends BaseGameActivity {

    public Engine getEngion() {return this.mEngine;}

    private float WIDTH = 1280;
    private float HEIGHT = 720;

    private float SCENE_WIDTH = 2 * WIDTH;
    private float SCENE_HEIGHT = 2 * HEIGHT;

    public float getWIDTH() {return WIDTH;}
    public float getHEIGHT() {return HEIGHT;}
    public float getSCENE_HEIGHT() {return SCENE_HEIGHT;}
    public float getSCENE_WIDTH() {return SCENE_WIDTH;}

    public FPSCounter fpsCounter;

    public SceneManager mSceneManager;
    public ResourceManager mResourceManager;

    public SmoothCamera mCamera;
    private EngineOptions mEngineOptions;

    private Scene mScene;   public Scene getScene() {return mScene;}

    //    public Circle circle;
    public PlayerCircle playerCircle;
//    public Sprite point;
//    public Circle testPoint;
    public ArrayList<Circle> points = new ArrayList<Circle>();

    //resources

    private float getCameraScale(float weight) {
        if (weight <= 500) return 1;
        if (weight <= 2000) return 0.75f;
        return 0.5f;
    }

    @Override
    public EngineOptions onCreateEngineOptions() {
        mCamera = new SmoothCamera(0, 0, WIDTH, HEIGHT, 1000, 1000, 0.25f) {

            private int timer = 0;

            @Override
            public void onUpdate(float pSecondsElapsed) {
                super.onUpdate(pSecondsElapsed);

                mCamera.setZoomFactor(getCameraScale(playerCircle.getWeight()));

                timer++;
                if (timer >= 20) {
                    timer = 0;
                    if (points.size() <= 300) {
                        float x = (float) (Math.random() * SCENE_WIDTH);
                        float y = (float) (Math.random() * SCENE_HEIGHT);
                        float weight = (float) (Math.random() * 15 + 5);
                        Circle point = new Circle(x, y, weight);
                        mScene.attachChild(point.getSprite());
                        points.add(point);
                    }
                }
            }

//            private void ready() {
//            }
//
//            private void die() {
//            }
//
//            private void play() {
//            }
        };
        mEngineOptions = new EngineOptions(
                true,
                ScreenOrientation.LANDSCAPE_FIXED,
                new RatioResolutionPolicy(WIDTH, HEIGHT),
                mCamera
        );
        mEngineOptions.getAudioOptions().setNeedsMusic(true);
        mEngineOptions.getAudioOptions().setNeedsSound(true);
        mEngineOptions.getTouchOptions().setNeedsMultiTouch(true);

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
        playerCircle = new PlayerCircle(getWIDTH() / 2, getHEIGHT() / 2, PlayerCircle.WEIGHT_PLAYER, true);

        mScene.attachChild(playerCircle.getSprite());

        mCamera.setChaseEntity(playerCircle.getSprite());

//        final Rectangle rectangle = new Rectangle(500, 500, 100, 100, getVertexBufferObjectManager());
//        mScene.attachChild(rectangle);
//        mCamera.setChaseEntity(rectangle);
//        final PathModifier.Path path = new PathModifier.Path(5).to(100, 100).to(2000, 100).to(2000, 1400).to(100, 1400).to(100,100);
//        rectangle.registerEntityModifier(new LoopEntityModifier(new PathModifier(30, path)));

//        mCamera.setChaseEntity(playerCircle.getSprite());


        AnalogController controller = new AnalogController(this, mScene);
        controller.createAnalogController();

//        final Sprite fireButton = new Sprite(WIDTH - 160, HEIGHT - 160, mResourceManager.mOnScreenControlKnobTextureRegion, getVertexBufferObjectManager()){
//            @Override
//            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
//                if (pSceneTouchEvent.isActionDown()) {
//                    ControlledCircle bullet = playerCircle.shootA();
//                    mScene.attachChild(bullet.getSprite());
//                    points.add(bullet);
//                    return true;
//                }
//                return false;
//            }
//        };
//        fireButton.setScale(2f, 2f);
//        mScene.attachChild(fireButton);
//        mScene.registerTouchArea(fireButton);
//        mScene.setTouchAreaBindingOnActionDownEnabled(true);

//        mScene.attachChild(analogOnScreenControl);

        //point test

        mScene.registerUpdateHandler(new IUpdateHandler() {
            @Override
            public void onUpdate(float pSecondsElapsed) {
//                if ((circle.getSprite().getHeight() / 2 + point.getHeight() / 2) * (circle.getSprite().getHeight() / 2 + point.getHeight() / 2) >=
//                        (circle.getSprite().getX() - point.getX()) * (circle.getSprite().getX() - point.getX()) +
//                                (circle.getSprite().getY() - point.getY()) * (circle.getSprite().getY() - point.getY())
//                        ) {
//                    point.setColor(0, 0, 1);
//                }else {
//                    point.setColor(1, 0, 0);
//                }


//                if (checkIfcollides(circle.getSprite(), point)) point.setColor(0, 0, 1);
//                else point.setColor(1, 0, 0);

//                if (playerCircle.collidedWithCircle(testPoint)) testPoint.getSprite().setColor(0, 0, 1);
//                else testPoint.getSprite().setColor(1, 0, 0);
                Circle testPoint;
                for (int i = 0; i < points.size(); i++) {
                    testPoint = points.get(i);

                    if (testPoint.ableToDetach()) {
                        mScene.detachChild(testPoint.getSprite());
                        points.remove(i);
                    }

//                    if (playerCircle.collidedWithCircle(testPoint))
//                        if (playerCircle.ableToEat(testPoint))
//                            testPoint.getSprite().setColor(0, 1, 0);
//                        else testPoint.getSprite().setColor(0, 0, 1);
//                    else testPoint.getSprite().setColor(1, 0, 0);
                    if (playerCircle.ableToEat(testPoint)) {
                        testPoint.destroyCircle();
                        playerCircle.scale(testPoint.getWeight());
//                        points.remove(i);
                    }
                }

//                if (playerCircle.collidedWithCircle(testPoint))
//                    if (playerCircle.ableToEat(testPoint)) testPoint.getSprite().setColor(0, 1, 0);
//                    else testPoint.getSprite().setColor(0, 0, 1);
//                else testPoint.getSprite().setColor(1, 0, 0);
            }

            @Override
            public void reset() {
            }
        });

        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

//    mContext.circle.getSprite().getHeight() / 2 * mContext.circle.getSprite().getScaleX()
}


//        final AnalogOnScreenControl analogOnScreenControl = new AnalogOnScreenControl(
//                0,
//                HEIGHT - mResourceManager.mOnScreenControlBaseTextureRegion.getHeight(),
//                this.mCamera,
//                mResourceManager.mOnScreenControlBaseTextureRegion, mResourceManager.mOnScreenControlKnobTextureRegion,
//                0.1f, 200, getVertexBufferObjectManager(),
//                new AnalogOnScreenControl.IAnalogOnScreenControlListener() {
//                    @Override
//                    public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {
////                        circle.getPhysicsHandler().setVelocity(pValueX * 10000 / circle.getWeight(), pValueY * 10000 / circle.getWeight());
//                        if (playerCircle.getState() == Circle.FLAG_CREATING) return;
//                        playerCircle.getPhysicsHandler().setVelocity(pValueX * 10000 / playerCircle.getWeight(), pValueY * 10000 / playerCircle.getWeight());
////                        playerCircle.setControlledState(ControlledCircle.FLAG_CONTROLLING);
////                physicsHandler.setAcceleration(pValueX * 100, pValueY * 100);
//
////                        final Body faceBody = (Body) faces.getUserData();
////                        faceBody.applyForce(pValueX * 50, pValueY * 50, faceBody.getWorldCenter().x, faceBody.getWorldCenter().y);
//
////                if (physicsHandler.getVelocityX() * physicsHandler.getVelocityX() + physicsHandler.getVelocityY() * physicsHandler.getVelocityY() >= 10 * 10) {
////                    faceBody.setLinearVelocity(new Vector2(physicsHandler.getVelocityX(), physicsHandler.getVelocityY()));
////                } else {
////                    faceBody.applyForce(pValueX * 50, pValueY * 50, faceBody.getWorldCenter().x, faceBody.getWorldCenter().y);
////                }
//
////                faceBody.applyForce(pValueX * 50, pValueY * 50, faceBody.getWorldCenter().x, faceBody.getWorldCenter().y);
//
////                Vector2 vector2 = faceBody.getLinearVelocity().add(physicsHandler.getAccelerationX() + pValueX * 50, physicsHandler.getAccelerationY() + pValueY * 50);
////                faceBody.setLinearVelocity(50, 50);
//                    }
//
//                    @Override
//                    public void onControlClick(final AnalogOnScreenControl pAnalogOnScreenControl) {
////                        circle.registerEntityModifier(new SequenceEntityModifier(new ScaleModifier(0.25f, 1, 1.5f), new ScaleModifier(0.25f, 1.5f, 1)));
////                        circle.scale(5);
////                        playerCircle.scale(5);
////                        circle.registerEntityModifier();
//                        ControlledCircle bullet = playerCircle.shootA();
//                        if (bullet != null) {
//                            points.add(bullet);
//                            mScene.attachChild(bullet.getSprite());
//                        }
//                    }
//                });
////        analogOnScreenControl.setScaleCenter(0, HEIGHT * 2 / 3);
////        analogOnScreenControl.setScale(HEIGHT / 3 / mResourceManager.mOnScreenControlBaseTextureRegion.getHeight());
//        analogOnScreenControl.getControlBase().setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
//        analogOnScreenControl.getControlBase().setAlpha(0.5f);
//        analogOnScreenControl.getControlBase().setScaleCenter(0, 128);
////        analogOnScreenControl.getControlBase().setScale(1.25f);
////        analogOnScreenControl.getControlKnob().setScale(1.25f);
//        analogOnScreenControl.getControlBase().setScale(2f);
//        analogOnScreenControl.getControlKnob().setScale(2f);
//        analogOnScreenControl.refreshControlKnobPosition();
//
//        mScene.setChildScene(analogOnScreenControl);