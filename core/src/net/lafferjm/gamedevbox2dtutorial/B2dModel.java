package net.lafferjm.gamedevbox2dtutorial;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import net.lafferjm.gamedevbox2dtutorial.controller.KeyboardController;

/**
 * Created by laffe on 3/19/2018.
 */

public class B2dModel {
    public World world;
    private Body bodyd;
    private Body bodys;
    private Body bodyk;
    private Body player;
    private KeyboardController controller;
    private OrthographicCamera camera;

    public boolean isSwimming = false;

    public B2dModel(KeyboardController controller, OrthographicCamera camera) {
        this.camera = camera;
        this.controller = controller;
        world = new World(new Vector2(0, -10f), true);
        world.setContactListener(new B2dContactListener(this));

        createFloor();
        // createObject();
        // createMovingObject();

        BodyFactory bodyFactory = BodyFactory.getInstance(world);

        player = bodyFactory.makeBoxPolyBody(1, 1, 2, 2, BodyFactory.RUBBER, BodyDef.BodyType.DynamicBody, false);
        Body water = bodyFactory.makeBoxPolyBody(1, -8, 40, 4, BodyFactory.RUBBER, BodyDef.BodyType.StaticBody, false);
        water.setUserData("IAMTHESEA");

        bodyFactory.makeAllFixturesSensors(water);
    }

    public void logicStep(float delta) {
        if (controller.isMouse1Down && pointIntersecsBody(player, controller.mouseLocation)) {
            System.out.println("Player was clicked");
        }

        if(controller.left) {
            player.applyForceToCenter(-10, 0, true);
        } else if (controller.right) {
            player.applyForceToCenter(10, 0, true);
        } else if (controller.up) {
            player.applyForceToCenter(0, 10, true);
        } else if (controller.down) {
            player.applyForceToCenter(0, -10, true);
        }

        if (isSwimming) {
            player.applyForceToCenter(0, 40, true);
        }
        world.step(delta, 3, 3);
    }

    private void createObject() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0, 0);

        bodyd = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1, 1);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        bodyd.createFixture(shape, 0.0f);

        shape.dispose();
    }

    private void createFloor() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, -10);

        bodys = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(50, 1);

        bodys.createFixture(shape, 0.0f);

        shape.dispose();
    }

    private void createMovingObject() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(0, -12);

        bodyk = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1 ,1);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        bodyk.createFixture(shape, 0.0f);

        shape.dispose();

        bodyk.setLinearVelocity(0, 0.75f);
    }

    public boolean pointIntersecsBody(Body body, Vector2 mouseLocation) {
        Vector3 mousePos = new Vector3(mouseLocation, 0);

        camera.unproject(mousePos);
        if (body.getFixtureList().first().testPoint(mousePos.x, mousePos.y)) {
            return true;
        }

        return false;
    }
}
