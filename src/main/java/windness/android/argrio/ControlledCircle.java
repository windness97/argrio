package windness.android.argrio;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.sprite.Sprite;

/**
 * Created by windness on 2015/12/7.
 */
public class ControlledCircle extends Circle {

    public static final int FLAG_UNCONTROLLED = 0;
    public static final int FLAG_CONTROLLING = 1;
    public static final int FLAG_OUT_OF_CONTROL = 2;
    private int controlledState = FLAG_UNCONTROLLED;

    public int getControlledState() {return controlledState;}
    public void setControlledState(int controlledState) {this.controlledState = controlledState;}

    private PhysicsHandler physicsHandler;

    public ControlledCircle(float x, float y, float weight, float red, float green, float blue, boolean animate) {
        super(x, y, weight, red, green, blue, animate);
        createPhysicsHandler();
        createOtherHandler();
    }

    private void createPhysicsHandler() {
        physicsHandler = new PhysicsHandler(getSprite());

        getSprite().registerUpdateHandler(physicsHandler);

        this.getSprite().registerUpdateHandler(
                new IUpdateHandler() {
                    @Override
                    public void onUpdate(float pSecondsElapsed) {
                        if (controlledState == FLAG_UNCONTROLLED) {

                            float vx = physicsHandler.getVelocityX();
                            float vy = physicsHandler.getVelocityY();

                            if (vx * vx + vy * vy <= 30) {
                                physicsHandler.setAcceleration(0, 0);
                                physicsHandler.setVelocity(0, 0);
                                return;
                            }

                            float a = - 300;
                            float ax = calculateA(vx, vy, a);
                            float ay = calculateB(vx, vy, a);
                            physicsHandler.setAcceleration(ax, ay);
                        }
                    }

                    @Override
                    public void reset() {}
                }
        );
    }

    public void createOtherHandler() {

    }

    @Override
    public Sprite getSprite() {
        return super.getSprite();
    }

    public PhysicsHandler getPhysicsHandler() {
        return physicsHandler;
    }

    @Override
    public float getWeight() {
        return super.getWeight();
    }

    @Override
    public void scale(float n) {
        super.scale(n);
    }

    public boolean ableToEat(Circle c) {
        if (c.getState() != Circle.FLAG_CREATED) return false;

        if (getWeight() >= 1.2 * c.getWeight()) {

            float r1 = this.getR();
            float r2 = c.getR();
            float deltaX = this.getX() - c.getX();
            float deltaY = this.getY() - c.getY();

            if (deltaX * deltaX + deltaY * deltaY <= r1 * r1 - r2 * r2) {
                return true;
            }
        }
        return false;
    }

    public boolean collidedWithCircle(Circle c) {
        float r1 = this.getR();
        float r2 = c.getR();
        float deltaX = this.getX() - c.getX();
        float deltaY = this.getY() - c.getY();

        if ((r1 + r2) * (r1 + r2) >= deltaX * deltaX + deltaY * deltaY) {
            return true;
        }else {
            return false;
        }
    }

    protected float calculateB(float x, float y, float c) {
        float b = c * y / (float)Math.sqrt(x * x + y * y);
        return b;
    }

    protected float calculateA(float x, float y, float c) {
        return calculateB(y, x, c);
    }

    public static float getSpeed(float ratio, float weight) {
        if (weight <= 40) return (750 - weight * 10f) * ratio;
        else return (10000 / weight + 100) * ratio;
    }
}
