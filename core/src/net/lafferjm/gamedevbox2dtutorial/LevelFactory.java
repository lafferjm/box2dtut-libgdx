package net.lafferjm.gamedevbox2dtutorial;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import net.lafferjm.gamedevbox2dtutorial.entity.components.B2dBodyComponent;
import net.lafferjm.gamedevbox2dtutorial.entity.components.CollisionComponent;
import net.lafferjm.gamedevbox2dtutorial.entity.components.PlayerComponent;
import net.lafferjm.gamedevbox2dtutorial.entity.components.StateComponent;
import net.lafferjm.gamedevbox2dtutorial.entity.components.TextureComponent;
import net.lafferjm.gamedevbox2dtutorial.entity.components.TransformComponent;
import net.lafferjm.gamedevbox2dtutorial.entity.components.TypeComponent;
import net.lafferjm.gamedevbox2dtutorial.simplexnoise.SimplexNoise;

/**
 * Created by laffe on 3/21/2018.
 */

public class LevelFactory {
    private BodyFactory bodyFactory;
    public World world;
    private PooledEngine engine;
    private SimplexNoise sim;
    public int currentLevel = 0;
    private TextureRegion floorTex;

    public LevelFactory(PooledEngine en, TextureRegion floorTexture) {
        engine = en;
        floorTex = floorTexture;
        world = new World(new Vector2(0, -10f), true);
        world.setContactListener(new B2dContactListener());
        bodyFactory = BodyFactory.getInstance(world);
        sim = new SimplexNoise(512, 0.85f, 1);
    }

    public void generateLevel(int ylevel) {
        while(ylevel > currentLevel) {
            float noise1 = (float)sim.getNoise(1, currentLevel, 0);
            float noise2 = (float)sim.getNoise(1, currentLevel, 100);
            float noise3 = (float)sim.getNoise(1, currentLevel, 200);
            float noise4 = (float)sim.getNoise(1, currentLevel, 300);

            if(noise1 > 0.2f) {
                createPlatform(noise2 * 25 + 2, currentLevel * 2);
            }

            if (noise3 > 0.2f) {
                createPlatform(noise4 * 25 + 2, currentLevel * 2);
            }
            currentLevel++;
        }
    }

    public void createPlatform(float x, float y) {
        Entity entity = engine.createEntity();
        B2dBodyComponent b2dbody = engine.createComponent(B2dBodyComponent.class);
        b2dbody.body = bodyFactory.makeBoxPolyBody(x, y, 1.5f, 0.2f, BodyFactory.STONE, BodyDef.BodyType.StaticBody);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = floorTex;
        TypeComponent type = engine.createComponent(TypeComponent.class);
        type.type = TypeComponent.SCENERY;
        b2dbody.body.setUserData(entity);
        entity.add(b2dbody);
        entity.add(texture);
        entity.add(type);
        engine.addEntity(entity);
    }

    public void createFloor(TextureRegion tex) {
        Entity entity = engine.createEntity();
        B2dBodyComponent b2dbody = engine.createComponent(B2dBodyComponent.class);
        b2dbody.body = bodyFactory.makeBoxPolyBody(0, 0, 100, 0.2f, BodyFactory.STONE, BodyDef.BodyType.StaticBody);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = tex;
        TypeComponent type = engine.createComponent(TypeComponent.class);
        type.type = TypeComponent.SCENERY;

        b2dbody.body.setUserData(entity);

        entity.add(b2dbody);
        entity.add(texture);
        entity.add(type);

        engine.addEntity(entity);
    }

    public void createPlayer(TextureRegion tex, OrthographicCamera cam) {
        Entity entity = engine.createEntity();
        B2dBodyComponent b2dbody = engine.createComponent(B2dBodyComponent.class);
        TransformComponent position = engine.createComponent(TransformComponent.class);
        TextureComponent texture = engine.createComponent(TextureComponent.class);
        PlayerComponent player = engine.createComponent(PlayerComponent.class);
        CollisionComponent colComp = engine.createComponent(CollisionComponent.class);
        TypeComponent type = engine.createComponent(TypeComponent.class);
        StateComponent stateCom = engine.createComponent(StateComponent.class);

        player.cam = cam;
        b2dbody.body = bodyFactory.makeCirclePolyBody(10, 1, 1, BodyFactory.STONE, BodyDef.BodyType.DynamicBody, true);

        position.position.set(10, 1, 0);
        texture.region = tex;
        type.type = TypeComponent.PLAYER;
        stateCom.set(StateComponent.STATE_NORMAL);
        b2dbody.body.setUserData(entity);

        entity.add(b2dbody);
        entity.add(position);
        entity.add(texture);
        entity.add(player);
        entity.add(colComp);
        entity.add(type);
        entity.add(stateCom);

        engine.addEntity(entity);
    }
}
