/*
 * FXGL - JavaFX Game Library. The MIT License (MIT).
 * Copyright (c) AlmasB (almaslvl@gmail.com).
 * See LICENSE for details.
 */

package com.example.finproject;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.example.finproject.Components.EnemyComponent;
import com.example.finproject.Components.PlayerComponent;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.util.Objects;

import static com.almasb.fxgl.dsl.FXGL.*;

public class TDGameApp extends GameApplication {
    //debug stuff because I am lazy
    String left = "left";
    String right = "right";
    String up = "up";
    String down = "down";
//end lazy debugging
    public enum Type {
        PLAYER,ENEMY,BUILDING,RESOURCE,
    }
    boolean downPress, upPress, leftPress, rightPress;
    private Entity player;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Drop");
        settings.setVersion("1.0");
        settings.setWidth(1200);
        settings.setHeight(800);
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new gameFactory());
        player = spawn("Player");

//        run(() -> spawn("enemy"), Duration.seconds(3));
        run(()-> spawn("building"), Duration.seconds(5));

//        playerComponent = player.getComponent(PlayerComponent.class);
    }
    @Override
    protected void initInput(){
        getInput().addAction(new UserAction("Left"){
            @Override
            protected void onAction(){
                player.getComponent(PlayerComponent.class).left();
                leftPress = true;
            }
            @Override
            protected void onActionEnd(){
                leftPress = false;
            }
        }, KeyCode.A);

        getInput().addAction(new UserAction("Right"){
            @Override
            protected void onAction(){
                player.getComponent(PlayerComponent.class).right();
                rightPress = true;
            }

            @Override
            protected void onActionEnd(){
                rightPress = false;
            }
        }, KeyCode.D);

        getInput().addAction(new UserAction("Down"){
            @Override
            protected void onAction(){
                player.getComponent(PlayerComponent.class).down();
                downPress = true;
            }
            @Override
            protected void onActionEnd(){
                downPress = false;
            }
        }, KeyCode.S);

        getInput().addAction(new UserAction("Up"){
            @Override
            protected void onAction(){
                player.getComponent(PlayerComponent.class).up();
                upPress = true;
            }

            @Override
            protected void onActionEnd(){
                upPress = false;
            }
        }, KeyCode.W);
    }

    @Override
    protected void initPhysics() {
        /*player enemy - can attack
        *
        * player building - stop movement
        *
        * player resource - stop movement, able to mine
        *
        * enemy building - stop movement, attack
        *
        * */
        onCollision(Type.PLAYER, Type.BUILDING, (player, nonPlayer) -> {
            player.setPosition(checkCollisionLocation(player, nonPlayer));
//            play("drop.wav");
        });
        onCollision(Type.PLAYER, Type.ENEMY, (player, enemy) -> {
            run(Objects.requireNonNull(damagePlayer(player, enemy)), Duration.seconds(1));
        });
    }
    //what things could I do to counteract the movement
    private Point2D checkCollisionLocation(Entity thing1, Entity thing2){//building
        //left and down work but right and up don't
        double xLoc = thing1.getX(), yLoc = thing1.getY();
        if(leftPress&&
                (thing1.getY()<thing2.getBottomY()-5&&thing1.getBottomY()>thing2.getY()+5)){//compare to y value (HOW?)
            //pressed "A"
            xLoc = thing2.getRightX();
//            player.getComponent(PlayerComponent.class).right();
        }
        if(rightPress&&
                (thing1.getY()<thing2.getBottomY()-5&&thing1.getBottomY()>thing2.getY()+5)){// compare to the y value (HOW?)
            //pressed "D"
            xLoc = thing2.getX()-thing1.getWidth();
        }
        if(downPress&&
                (thing1.getX()<thing2.getRightX()-5&&thing1.getRightX()>thing2.getX()+5)){//compare to x value
            //pressed "S"
            yLoc = thing2.getY()-thing1.getHeight();
        }
        if(upPress&&
                (thing1.getX()<thing2.getRightX()-5&&thing1.getRightX()>thing2.getX()+5)){//compare to x value
            //pressed "W"
            yLoc = thing2.getBottomY();
        }
        Point2D newPoint = new Point2D(xLoc, yLoc);
        System.out.println("new point:" + newPoint + "\n playerLocation:" + thing1.getPosition() + " player Dimensions: " + thing1.getWidth()+ " height:" + thing1.getHeight()+ "\n objectLocation:" + thing2.getPosition() +"object dimensions:" + thing2.getWidth() + "height: " + thing2.getHeight());
        return newPoint;
    }
    private Runnable damagePlayer(Entity player, Entity opponent){
        double damage = opponent.getComponent(EnemyComponent.class).getDamage();
        player.getComponent(PlayerComponent.class).damage(damage);
        return null;
    }

    @Override
    protected void onUpdate(double tpf) {
        getGameWorld().getEntitiesByType(Type.ENEMY).forEach(droplet -> droplet.translateY(150 * tpf));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
