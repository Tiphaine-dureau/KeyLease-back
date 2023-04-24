package com.infeco.keylease;

import com.infeco.keylease.entity.AddressEntity;
import com.infeco.keylease.entity.PropertyEntity;
import com.infeco.keylease.entity.PropertyTypeEntity;
import com.infeco.keylease.entity.RentalManagementFeesEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EntityUtil {

    public static PropertyEntity createPropertyEntity() {
        PropertyEntity propertyEntity = new PropertyEntity();
        propertyEntity.setId(UUID.randomUUID());
        propertyEntity.setArea("110");
        propertyEntity.setRoomsNumber("5");
        propertyEntity.setDescription("Maison de 5 pièces mesurant 110m2 en plein centre ville");
        PropertyTypeEntity propertyTypeEntity = new PropertyTypeEntity();
        propertyTypeEntity.setName("Maison");
        propertyEntity.setPropertyType(propertyTypeEntity);
        propertyEntity.setAddress(createAddressEntity());
        return propertyEntity;
    }

    public static AddressEntity createAddressEntity() {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(UUID.randomUUID());
        addressEntity.setStreet("1 rue des Lauriers");
        addressEntity.setZipCode("75000");
        addressEntity.setTown("Paris");
        return addressEntity;
    }

    public static List<RentalManagementFeesEntity> createFeeListEntities() {
        List<RentalManagementFeesEntity> fees = new ArrayList<>();
        RentalManagementFeesEntity fee = new RentalManagementFeesEntity();
        fee.setId(0L);
        fee.setFeeRate(0.08);
        fees.add(fee);
        return fees;
    }
}
