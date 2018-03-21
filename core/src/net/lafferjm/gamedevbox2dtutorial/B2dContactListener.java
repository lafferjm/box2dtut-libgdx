package net.lafferjm.gamedevbox2dtutorial;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import net.lafferjm.gamedevbox2dtutorial.entity.components.CollisionComponent;

/**
 * Created by laffe on 3/19/2018.
 */

public class B2dContactListener implements ContactListener {


    public B2dContactListener() {

    }

    @Override
    public void beginContact(Contact contact) {
       System.out.println("Contact");
       Fixture fa = contact.getFixtureA();
       Fixture fb = contact.getFixtureB();
       System.out.println(fa.getBody().getType() + " has hit " + fb.getBody().getType());

       if (fa.getBody().getUserData() instanceof Entity) {
           Entity ent = (Entity) fa.getBody().getUserData();
           entityCollision(ent, fb);
           return;
       } else if (fb.getBody().getUserData() instanceof Entity) {
           Entity ent = (Entity) fb.getBody().getUserData();
           entityCollision(ent, fa);
           return;
       }
    }

    @Override
    public void endContact(Contact contact) {
        System.out.println("Contact end");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private void entityCollision(Entity ent, Fixture fb) {
        if (fb.getBody().getUserData() instanceof Entity) {
            Entity colEnt = (Entity) fb.getBody().getUserData();

            CollisionComponent col = ent.getComponent(CollisionComponent.class);
            CollisionComponent colb = colEnt.getComponent(CollisionComponent.class);

            if (col != null) {
                col.collisionEntity = colEnt;
            } else if (colb != null) {
                colb.collisionEntity = ent;
            }
        }
    }
}
