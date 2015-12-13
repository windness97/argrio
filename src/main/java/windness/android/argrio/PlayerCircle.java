package windness.android.argrio;

import org.andengine.engine.handler.IUpdateHandler;

/**
 * Created by windness on 2015/12/13.
 */
public class PlayerCircle extends ControlledCircle {

    private static final float SPEED_SHOOT = 500;
    private static final float SPEED_JUMP = 500;

    public static final float WEIGHT_PLAYER = 20;
    //    private static final float WEIGHT_BULLET = 5;
    private static final float WEIGHT_BULLET = 5;
    private static final float WEIGHT_MIN = 50;


    public PlayerCircle(float x, float y, float weight, boolean animate) {
        this(x, y, weight, 1, 1, 1, animate);
    }

    public PlayerCircle(float x, float y, float weight, float red, float green, float blue, boolean animate) {
        super(x, y, weight, red, green, blue, animate);
    }

    private float getWeightToCut(float weight) {
        if (weight < 500) return 0;
        if (weight < 1000) return weight * 2 / 50 - 20;
        return weight / 50;
    }

    @Override
    public void createOtherHandler() {
        super.createOtherHandler();

        getSprite().registerUpdateHandler(
                new IUpdateHandler() {
                    float timer = 0;
                    @Override
                    public void onUpdate(float pSecondsElapsed) {
                        timer++;
                        if (timer >= 60) {
                            timer = 0;
                            if (getWeight() < 500) return;
                            scale(- getWeightToCut(getWeight()));
                        }
                    }

                    @Override
                    public void reset() {

                    }
                }
        );
    }

    public ControlledCircle shoot(float ax, float ay) {
        float weight = getWeight();
        if (weight - WEIGHT_BULLET <= WEIGHT_MIN) return null;

        float bulletWeight;
        if (weight > WEIGHT_BULLET * 100) bulletWeight = weight / 100;
        else bulletWeight = WEIGHT_BULLET;

        scale(- bulletWeight);

        float r1 = getR();
        float r2 = getR(bulletWeight);
        float deltaX = calculateA(ax, ay, r1 + r2);
        float deltaY = calculateB(ax, ay, r1 + r2);
        float x = getX() + deltaX;
        float y = getY() + deltaY;

        ControlledCircle bullet = new ControlledCircle(x, y, bulletWeight, getRed(), getGreen(), getBlue(), false);

        float vx = calculateA(ax, ay, SPEED_SHOOT);
        float vy = calculateB(ax, ay, SPEED_SHOOT);
        bullet.getPhysicsHandler().setVelocity(vx, vy);

        return bullet;
    }

    public boolean jump() {
        if (getWeight() * 0.8f <= WEIGHT_MIN) return false;
//        scale(- getWeight() * 0.2f);
//        float vx = getPhysicsHandler().getVelocityX();
//        float vy = getPhysicsHandler().getVelocityY();
//        float jvx = calculateA(vx, vy, SPEED_JUMP);
//        float jvy = calculateB(vx, vy, SPEED_JUMP);
//        getPhysicsHandler().setVelocity(jvx, jvy);
        return true;
    }
}
