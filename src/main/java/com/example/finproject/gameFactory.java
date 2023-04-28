package com.example.finproject;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.example.finproject.Components.*;
import com.example.finproject.Data.buildingData;
import com.example.finproject.Data.enemyData;
import javafx.scene.paint.Color;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGL.entityBuilder;


public class gameFactory implements EntityFactory{

    @Spawns("Player")
    public Entity spawnBucket(SpawnData data){//spawns player
        var texture = texture("player.png").outline(Color.web("blue", 1), 5);
        return entityBuilder()
                .type(TDGameApp.Type.PLAYER)
                .at(100, 100)
                .viewWithBBox(texture)
                .collidable()
                .with(new PlayerComponent())
                .build();
    }

    @Spawns("enemy")
    public Entity spawnDrops(SpawnData data ){//asdf
        enemyData enemyData = data.get("enemyData");

        return entityBuilder(data)
                .type(TDGameApp.Type.ENEMY)
                .at(FXGLMath.random(0, getAppWidth()-64), 0)
                .viewWithBBox("droplet.png")
                .collidable()
                .with(new EnemyComponent(enemyData))
                .build();
    }

    @Spawns("building")
    public Entity spawnBuildings(SpawnData data){
//        buildingData buildingData = data.get("buildingData");

        return entityBuilder(data)
                .type(TDGameApp.Type.BUILDING)
                .at(FXGLMath.random(0, getAppWidth()-64), FXGLMath.random(0, getAppHeight()-64))
                .viewWithBBox("bucket.png")
                .collidable()
                .with(new BuildingComponent())
//                .with(new BuildingComponent(buildingData))
                .build();
    }

}
