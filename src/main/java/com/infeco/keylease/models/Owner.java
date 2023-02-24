package com.infeco.keylease.models;

import com.infeco.keylease.entity.ClientEntity;
import com.infeco.keylease.entity.OwnerEntity;

public class Owner extends Client {
    String rib;

    public Owner(){
    }

    public Owner(OwnerEntity ownerEntity){
        super(ownerEntity);
        this.rib= ownerEntity.getRib();
    }

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }
}
