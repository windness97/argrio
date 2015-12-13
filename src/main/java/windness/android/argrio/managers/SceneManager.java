package windness.android.argrio.managers;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
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

    private float WIDTH;
    private float HEIGHT;

    private float SCENE_WIDTH;
    private float SCENE_HEIGHT;

    private FPSCounter fpsCounter;

    Scene mScene;
    Camera mCamera;

    MainActivity mContext;
    ResourceManager mResourceManager;
    Background mParallaxBackground;

    private VertexBufferObjectManager vbo;


    public SceneManager(MainActivity context, ResourceManager resourceManager, Background background){
        this.mContext = context;
        WIDTH = context.getWIDTH();
        HEIGHT = context.getHEIGHT();
        SCENE_WIDTH = context.getSCENE_WIDTH();
        SCENE_HEIGHT = context.getSCENE_HEIGHT();
        this.mCamera = context.mCamera;
        this.mResourceManager = resourceManager;
        this.mParallaxBackground = background;

        vbo = context.getVertexBufferObjectManager();
    }

    public Scene createScene(){
        mScene = new Scene();

        float thick = 5;

        final Rectangle roof = new Rectangle(0, -thick, SCENE_WIDTH, thick, vbo);
        final Rectangle ground = new Rectangle(0, SCENE_HEIGHT, SCENE_WIDTH, thick, vbo);
        final Rectangle left = new Rectangle(-thick, 0, thick, SCENE_HEIGHT, vbo);
        final Rectangle right = new Rectangle(SCENE_WIDTH, 0, thick, SCENE_HEIGHT, vbo);

        mScene.attachChild(roof);
        mScene.attachChild(ground);
        mScene.attachChild(left);
        mScene.attachChild(right);

        createTextScene();
        return mScene;
    }

    private void createTextScene() {
        final Scene textScene = new Scene();
        textScene.setBackgroundEnabled(false);
//        mScene.setChildScene(textScene);
        mScene.attachChild(textScene);

        mScene.setBackground(new Background(0.2f, 0.7f, 0.8f));

        final Text text1 = new Text(0, 0, mContext.mResourceManager.mFont, "FPS:\nTime:\n", "FPS: XXXXXXXXXXXXX\nTime: XXXXXXXXXXXXX".length(), vbo);
        final Text text2 = new Text(0, 90, mContext.mResourceManager.mFont, "M:\n", "M: XXXXXXXXXXXXX".length(), vbo);
        final Text text3 = new Text(0, 120, mContext.mResourceManager.mFont, "R1:", "R1: XXXXXXXXXXXXX".length(), vbo);
        final Text text5 = new Text(0, 150, mContext.mResourceManager.mFont, "points:", "points: XXXXXX".length(), vbo);
        final Text text6 = new Text(0, 180, mContext.mResourceManager.mFont, "X:\nY:", "X: XXXXXXXXXXXXX\nY: XXXXXXXXXXXXX".length(), vbo);

        textScene.attachChild(text1);
        textScene.attachChild(text2);
        textScene.attachChild(text3);
        textScene.attachChild(text5);
        textScene.attachChild(text6);

        fpsCounter = mContext.fpsCounter;

        textScene.registerUpdateHandler(new TimerHandler(0.05f, true, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                textScene.setX(mCamera.getCenterX() - mContext.getWIDTH() / 2);
                textScene.setY(mCamera.getCenterY() - mContext.getHEIGHT() / 2);

                text1.setText("FPS: " + String.valueOf(fpsCounter.getFPS()) + "\n" +
                                "Time: " + String.valueOf(mContext.getEngion().getSecondsElapsedTotal())
                );
                text2.setText("M: " + String.valueOf(mContext.playerCircle.getWeight()));
                text3.setText("R1: " + String.valueOf(mContext.playerCircle.getR()));
                text5.setText("points: " + String.valueOf(mContext.points.size()));
                text6.setText("X: " + String.valueOf(mContext.playerCircle.getX()) + "\n" + "Y: " + String.valueOf(mContext.playerCircle.getY()));
            }
        }));
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
