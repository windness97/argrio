package windness.android.argrio;

import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.sprite.Sprite;

/**
 * Created by windness on 2015/12/7.
 */
public class ControlledCircle extends Circle {

    private static final float firstWeight = 20;

    private PhysicsHandler physicsHandler;

    public ControlledCircle() {
        super(firstWeight);
        physicsHandler = new PhysicsHandler(getSprite());
        getSprite().registerUpdateHandler(physicsHandler);
    }

    public ControlledCircle(float x, float y, float w) {
        super(x, y, w);
        physicsHandler = new PhysicsHandler(getSprite());
        getSprite().registerUpdateHandler(physicsHandler);
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
        if (getWeight() >= 1.2 * c.getWeight()) {

            float r1 = this.getSprite().getHeight() / 2 * this.getSprite().getScaleX();
            float r2 = c.getSprite().getHeight() / 2 * c.getSprite().getScaleX();
            float deltaX = this.getSprite().getX() - c.getSprite().getX();
            float deltaY = this.getSprite().getY() - c.getSprite().getY();

            if (deltaX * deltaX + deltaY * deltaY <= r1 * r1 - r2 * r2) {
                return true;
            }
        }
        return false;
    }

    public boolean collidedWithCircle(Circle c) {
        float r1 = this.getSprite().getHeight() / 2 * this.getSprite().getScaleX();
        float r2 = c.getSprite().getHeight() / 2 * c.getSprite().getScaleX();
        float deltaX = this.getSprite().getX() - c.getSprite().getX();
        float deltaY = this.getSprite().getY() - c.getSprite().getY();

        if ((r1 + r2) * (r1 + r2) >= deltaX * deltaX + deltaY * deltaY) {
            return true;
        }else {
            return false;
        }
    }

}
