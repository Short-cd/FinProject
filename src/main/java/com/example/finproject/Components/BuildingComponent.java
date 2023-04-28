package com.example.finproject.Components;

import com.almasb.fxgl.entity.component.Component;
import com.example.finproject.Data.buildingData;

public class BuildingComponent extends Component {

    buildingData data;
    //temp
    public BuildingComponent(){

    }

    public BuildingComponent(buildingData data){
        this.data = data;
    }
}
