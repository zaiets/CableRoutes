package model.db.impl;

import model.db.IDao;
import model.db.InMemoryDB;
import model.entities.Equipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Scope(value = "prototype")
public class EquipmentDao implements IDao<Equipment> {
    @Autowired
    InMemoryDB inMemoryDB;


    @Override
    public boolean create(Equipment equipment) {
        if (equipment == null) return false;
        for (Equipment o : inMemoryDB.getEquipments()) {
            if (o.getEquipmentName().equals(equipment.getEquipmentName())) {
                throw new RuntimeException("Duplicates in equipment list");
            }
        }
        return inMemoryDB.getEquipments().add(equipment);
    }

    @Override
    public boolean createOrUpdate(Equipment equipment) {
        try {
            if (!create(equipment)) {
                return update(equipment);
            }
        } catch (RuntimeException ex) {
            return update(equipment);
        }
        return true;
    }

    @Override
    public Equipment read(String uniqueName) {
        Equipment equipment = null;
        for (Equipment o : inMemoryDB.getEquipments()) {
            if (o.getEquipmentName().equals(uniqueName)) {
                if (equipment == null) {equipment = o;}
                else throw new RuntimeException("Duplicates in equipment list");
            }
        }
        return equipment;
    }

    @Override
    public boolean update(Equipment equipment) {
        if (equipment == null) return false;
        for (Equipment o : inMemoryDB.getEquipments()) {
            if (o.getEquipmentName().equals(equipment.getEquipmentName())) {
                o.setJoinPoint(equipment.getJoinPoint());
                o.setKksName(equipment.getKksName());
                o.setXyz(equipment.getXyz());
                o.setCableConnectionAddLength(equipment.getCableConnectionAddLength());
            } else {
                throw new RuntimeException("Can't find equipment in list:" + equipment.getKksName());
            }
        }
        return true;
    }

    @Override
    public boolean delete(String uniqueName) {
        Equipment equipment = null;
        for (Equipment o : inMemoryDB.getEquipments()) {
            if (o.getEquipmentName().equals(uniqueName)) {
                if (equipment == null) {equipment = o;}
                else throw new RuntimeException("Duplicates in equipment list");
            }
        }
        return inMemoryDB.getEquipments().remove(equipment);
    }

    @Override
    public List<Equipment> getAll(){
        return inMemoryDB.getEquipments();
    }
}
