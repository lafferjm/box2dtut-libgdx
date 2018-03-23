package net.lafferjm.gamedevbox2dtutorial.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Body;

import net.lafferjm.gamedevbox2dtutorial.entity.components.B2dBodyComponent;
import net.lafferjm.gamedevbox2dtutorial.entity.components.WaterFloorComponent;

/**
 * Created by laffe on 3/22/2018.
 */

public class WaterFloorSystem extends IteratingSystem {
    private Entity player;
    private ComponentMapper<B2dBodyComponent> bm = ComponentMapper.getFor(B2dBodyComponent.class);

    public WaterFloorSystem(Entity player) {
        super(Family.all(WaterFloorComponent.class).get());
        this.player = player;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        float currentLevel = player.getComponent(B2dBodyComponent.class).body.getPosition().y;

        Body bod = bm.get(entity).body;

        float speed = (currentLevel / 300);

        speed = speed > 1 ? 1 : speed;

        bod.setTransform(bod.getPosition().x, bod.getPosition().y + speed, bod.getAngle());
    }
}
