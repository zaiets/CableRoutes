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
    InMemoryDB database;

    List<Equipment> equipments = database.getEquipments();


    @Override
    public boolean create(Equipment equipment) {
        if (equipment == null) return false;
        for (Equipment o : equipments) {
            if (o.getEquipmentName().equals(equipment.getEquipmentName())) {
                throw new RuntimeException("Duplicates in equipment list");
            }
        }
        return equipments.add(equipment);
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
        for (Equipment o : equipments) {
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
        for (Equipment o : equipments) {
            if (o.getEquipmentName().equals(equipment.getEquipmentName())) {
                equipments.remove(o);
                equipments.add(equipment);
            } else {
                throw new RuntimeException("Can't find equipment in list:" + equipment.getKksName());
            }
        }
        return true;
    }

    @Override
    public boolean delete(String uniqueName) {
        Equipment equipment = null;
        for (Equipment o : equipments) {
            if (o.getEquipmentName().equals(uniqueName)) {
                if (equipment == null) {equipment = o;}
                else throw new RuntimeException("Duplicates in equipment list");
            }
        }
        return equipments.remove(equipment);
    }

    @Override
    public List<Equipment> getAll(){
        return equipments;
    }
}
