package net.lafferjm.gamedevbox2dtutorial.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;

import net.lafferjm.gamedevbox2dtutorial.controller.KeyboardController;
import net.lafferjm.gamedevbox2dtutorial.entity.components.B2dBodyComponent;
import net.lafferjm.gamedevbox2dtutorial.entity.components.PlayerComponent;
import net.lafferjm.gamedevbox2dtutorial.entity.components.StateComponent;

/**
 * Created by laffe on 3/20/2018.
 */

public class PlayerControlSystem extends IteratingSystem {

    ComponentMapper<PlayerComponent> pm;
    ComponentMapper<B2dBodyComponent> bodm;
    ComponentMapper<StateComponent> sm;
    KeyboardController controller;

    @SuppressWarnings("unchecked")
    public PlayerControlSystem(KeyboardController keyCon) {
        super(Family.all(PlayerComponent.class).get());
        controller = keyCon;
        pm = ComponentMapper.getFor(PlayerComponent.class);
        bodm = ComponentMapper.getFor(B2dBodyComponent.class);
        sm = ComponentMapper.getFor(StateComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        B2dBodyComponent b2body = bodm.get(entity);
        StateComponent state = sm.get(entity);
        PlayerComponent player = pm.get(entity);
        player.cam.position.y = b2body.body.getPosition().y;

        if (b2body.body.getLinearVelocity().y > 0) {
            state.set(StateComponent.STATE_FALLING);
        }

        if (b2body.body.getLinearVelocity().y == 0) {
            if (state.get() == StateComponent.STATE_FALLING) {
                state.set(StateComponent.STATE_NORMAL);
            }
            if(b2body.body.getLinearVelocity().x != 0) {
                state.set(StateComponent.STATE_MOVING);
            }
        }

        if (b2body.body.getLinearVelocity().y < 0 && state.get() == StateComponent.STATE_FALLING) {
            if (player.onPlatform) {
                b2body.body.setLinearVelocity(b2body.body.getLinearVelocity().x, 0f);
            }
        }

        if (player.onSpring) {
            b2body.body.applyLinearImpulse(0, 175f, b2body.body.getWorldCenter().x, b2body.body.getWorldCenter().y, true);
            state.set(StateComponent.STATE_JUMPING);
            player.onSpring = false;
        }

        if (controller.left) {
            b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, -5f, 0.2f), b2body.body.getLinearVelocity().y);
        }

        if (controller.right) {
            b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, 5f, 0.2f), b2body.body.getLinearVelocity().y);
        }

        if (!controller.left && !controller.right) {
            b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, 0, 0.1f), b2body.body.getLinearVelocity().y);
        }

        if (controller.up &&
                (state.get() == StateComponent.STATE_NORMAL || state.get() == StateComponent.STATE_MOVING)) {
            b2body.body.applyLinearImpulse(0, 8.25f, b2body.body.getWorldCenter().x, b2body.body.getWorldCenter().y, true);
            state.set(StateComponent.STATE_JUMPING);
        }
    }
}
