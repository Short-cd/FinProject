package com.example.finproject.Components;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.example.finproject.Data.enemyData;

public class EnemyComponent extends Component {

    enemyData data;

    public EnemyComponent(enemyData data){
        this.data = data;
    }

    public double getDamage(){
        return data.damage();
    }

    private Entity getEnemy(){
        return entity;
    }
    int speed;
}
