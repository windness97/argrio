package windness.android.argrio.managers;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSCounter;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import windness.android.argrio.MainActivity;

/**
 * Created by windness on 2015/12/1.
 */
public class SceneManager {

    float WIDTH;
    float HEIGHT;

    private FPSCounter fpsCounter;

    Scene mScene;
    Camera mCamera;

    MainActivity mContext;
    ResourceManager mResourceManager;
    Background mParallaxBackground;

    public SceneManager(MainActivity context, ResourceManager resourceManager, Background background){
        this.mContext = context;
        WIDTH = context.getWIDTH();
        HEIGHT = context.getHEIGHT();
        this.mCamera = context.mCamera;
        this.mResourceManager = resourceManager;
        this.mParallaxBackground = background;
    }

    public Scene createScene(){
        mScene = new Scene();
        mScene.setBackground(new Background(0.2f, 0.7f, 0.8f));
        VertexBufferObjectManager vbo = mContext.getVertexBufferObjectManager();

//        final float centerX = (mContext.getWIDTH() - mContext.mResourceManager.mFaceTextureRegion.getWidth()) / 2;
//        final float centerY = (mContext.getHEIGHT() - mContext.mResourceManager.mFaceTextureRegion.getHeight()) / 2;
//        final Sprite circle = new Sprite(centerX, centerY, mContext.mResourceManager.mFaceTextureRegion, mContext.mResourceManager.mContext.getVertexBufferObjectManager());
//
//        final PhysicsHandler physicsHandler = new PhysicsHandler(circle);
//        circle.registerUpdateHandler(physicsHandler);

//        mScene.attachChild(circle);


        final Text text1 = new Text(0, 0, mContext.mResourceManager.mFont, "FPS:\nTime:\n", "FPS: XXXXXXXXXXXXX\nTime: XXXXXXXXXXXXX".length(), mContext.getVertexBufferObjectManager());

//        + "X:\nY:\nSpeedX:\nSpeedY"
//        X: XXXXXXXXXXXX
//        Y: XXXXXXXXXXXX
//        SpeedX: XXXXXXXXXXXX
//        SpeedY: XXXXXXXXXXXX

        final Text text2 = new Text(0, 100, mContext.mResourceManager.mFont, "M:\n", "M: XXXXXXXXXXXXX".length(), mContext.getVertexBufferObjectManager());

        mScene.attachChild(text1);
        mScene.attachChild(text2);

        fpsCounter = mContext.fpsCounter;

        mScene.registerUpdateHandler(new TimerHandler(0.05f, true, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                text1.setText("FPS: " + String.valueOf(fpsCounter.getFPS()) + "\n" +
                                "Time: " + String.valueOf(mContext.getEngion().getSecondsElapsedTotal())
//                                + "\n"
//                                + "X: " + (String.valueOf(face.getX())) + "\n"
//                                + "Y: " + (String.valueOf(face.getY())) + "\n"
//                                + "SpeedX: " + (String.valueOf(body.getLinearVelocity().x)) + "\n"
//                                + "SpeedY: " + (String.valueOf(body.getLinearVelocity().y))

//                                + "SpeedX: " + (String.valueOf(physicsHandler.getVelocityX())) + "\n"
//                                + "SppedY: " + (String.valueOf(physicsHandler.getVelocityY()))
                );
//                text2.setText("R: " + String.valueOf(Circle.mFaceTextureRegion.getHeight()) + " * " + String.valueOf(mContext.circle.getSprite().getScaleX()));
                text2.setText("M: " + String.valueOf(mContext.circle.getWeight()));
            }
        }));

        return mScene;
    }

//    public static void centerSprite(Sprite sprite){
//    }
//
//    public void displayCurrentScore(int score){
//    }
//
//    public void displayBestScore(int score){
//    }
//
//    private void centerText(Text text){
//    }
}
