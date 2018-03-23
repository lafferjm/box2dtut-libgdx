package net.lafferjm.gamedevbox2dtutorial.entity.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import net.lafferjm.gamedevbox2dtutorial.LevelFactory;
import net.lafferjm.gamedevbox2dtutorial.entity.components.PlayerComponent;
import net.lafferjm.gamedevbox2dtutorial.entity.components.TransformComponent;

/**
 * Created by laffe on 3/22/2018.
 */

public class LevelGenerationSystem extends IteratingSystem {
    private ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
    private LevelFactory lf;

    public LevelGenerationSystem(LevelFactory lvlFactory) {
        super(Family.all(PlayerComponent.class).get());
        lf = lvlFactory;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent trans = tm.get(entity);
        int currentPosition = (int) trans.position.y;
        if ((currentPosition + 7) > lf.currentLevel) {
            lf.generateLevel(currentPosition + 7);
        }
    }
}
