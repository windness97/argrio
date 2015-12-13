package windness.android.argrio;

import android.opengl.GLES20;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.entity.scene.Scene;

import java.util.ArrayList;

import windness.android.argrio.managers.ResourceManager;

/**
 * Created by windness on 2015/12/10.
 */
public class AnalogController {

    MainActivity context;
    Scene mScene;

    public AnalogController(MainActivity context, Scene mScene) {
        this.context = context;
        this.mScene = mScene;
    }

    public void createAnalogController() {
        float WIDTH = context.getWIDTH();
        float HEIGHT = context.getHEIGHT();
        ResourceManager mResourceManager = context.mResourceManager;
        Camera mCamera = context.mCamera;
        final PlayerCircle controlledCircle = context.playerCircle;
        final ArrayList<Circle> points = context.points;

        float controllerDefultWidth = mResourceManager.mOnScreenControlBaseTextureRegion.getWidth();
        float controllerDefultHeight = mResourceManager.mOnScreenControlBaseTextureRegion.getHeight();

        final AnalogOnScreenControl moveController = new AnalogOnScreenControl(
                0,
                HEIGHT - controllerDefultHeight,
                mCamera,
                mResourceManager.mOnScreenControlBaseTextureRegion, mResourceManager.mOnScreenControlKnobTextureRegion,
                0.1f, 200, context.getVertexBufferObjectManager(),
                new AnalogOnScreenControl.IAnalogOnScreenControlListener() {
                    @Override
                    public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {
//                        circle.getPhysicsHandler().setVelocity(pValueX * 10000 / circle.getWeight(), pValueY * 10000 / circle.getWeight());
                        if (pValueX == 0 && pValueY == 0) return;
                        if (controlledCircle.getState() == Circle.FLAG_CREATING) return;
//                        playerCircle.getPhysicsHandler().setVelocity(pValueX * 10000 / playerCircle.getWeight(), pValueY * 10000 / playerCircle.getWeight());
                        controlledCircle.getPhysicsHandler().setVelocity(
                                ControlledCircle.getSpeed(pValueX, controlledCircle.getWeight()),
                                ControlledCircle.getSpeed(pValueY, controlledCircle.getWeight())
                        );
//                        playerCircle.setControlledState(ControlledCircle.FLAG_CONTROLLING);
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
//                        circle.scale(5);
//                        playerCircle.scale(5);
//                        circle.registerEntityModifier();
                    }
                });
//        analogOnScreenControl.setScaleCenter(0, HEIGHT * 2 / 3);
//        analogOnScreenControl.setScale(HEIGHT / 3 / mResourceManager.mOnScreenControlBaseTextureRegion.getHeight());
        moveController.getControlBase().setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        moveController.getControlBase().setAlpha(0.5f);
        moveController.getControlKnob().setAlpha(0.3f);
        moveController.getControlBase().setScaleCenter(0, controllerDefultHeight);
//        analogOnScreenControl.getControlBase().setScale(1.25f);
//        analogOnScreenControl.getControlKnob().setScale(1.25f);
        moveController.getControlBase().setScale(2f);
        moveController.getControlKnob().setScale(2f);
        moveController.refreshControlKnobPosition();

        mScene.setChildScene(moveController);

        final AnalogOnScreenControl bulletController = new AnalogOnScreenControl(
                WIDTH - controllerDefultWidth,
                HEIGHT - controllerDefultHeight,
                mCamera,
                mResourceManager.mOnScreenControlBaseTextureRegion, mResourceManager.mOnScreenControlKnobTextureRegion,
                0.1f, 200, context.getVertexBufferObjectManager(),
                new AnalogOnScreenControl.IAnalogOnScreenControlListener() {
                    @Override
                    public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {
                        if (pValueX == 0 && pValueY == 0) return;

                        ControlledCircle bullet = controlledCircle.shoot(pValueX, pValueY);
                        if (bullet != null) {
                            points.add(bullet);
                            mScene.attachChild(bullet.getSprite());
                        }
                    }

                    @Override
                    public void onControlClick(final AnalogOnScreenControl pAnalogOnScreenControl) {
                        controlledCircle.jump();
                    }
                });
        bulletController.getControlBase().setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        bulletController.getControlBase().setAlpha(0.5f);
        bulletController.getControlKnob().setAlpha(0.3f);
        bulletController.getControlBase().setScaleCenter(controllerDefultWidth, controllerDefultHeight);
        bulletController.getControlBase().setScale(2f);
        bulletController.getControlKnob().setScale(2f);
        bulletController.refreshControlKnobPosition();

//        mScene.attachChild(bulletController);
        moveController.setChildScene(bulletController);

    }

}
