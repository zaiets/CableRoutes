package app.repository.dao.business.implInMemory;

import app.repository.dao.business.IDao;
import app.repository.entities.business.Equipment;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Scope(value = "prototype")
public class EquipmentDaoImpl implements IDao<Equipment> {

    InMemoryDB inMemoryDB = InMemoryDB.INSTANCE;


    @Override
    public boolean create(Equipment equipment) {
        if (equipment == null) return false;
        for (Equipment o : inMemoryDB.getEquipments()) {
            if (o.getFullName().equals(equipment.getFullName())) {
                //throw new RuntimeException("Duplicates in equipment list");
                return false;
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
            ex.printStackTrace();
            return update(equipment);
        }
        return true;
    }

    @Override
    public Equipment read(String uniqueName) {
        Equipment equipment = null;
        for (Equipment o : inMemoryDB.getEquipments()) {
            if (o.getFullName().equals(uniqueName)) {
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
            if (o.getFullName().equals(equipment.getFullName())) {
                o.setJoinPoint(equipment.getJoinPoint());
                o.setCommonKks(equipment.getCommonKks());
                o.setXyz(equipment.getXyz());
                o.setCableConnectionAddLength(equipment.getCableConnectionAddLength());
            } else {
                create(o);
            }
        }
        return true;
    }

    @Override
    public boolean delete(String uniqueName) {
        Equipment equipment = null;
        for (Equipment o : inMemoryDB.getEquipments()) {
            if (o.getFullName().equals(uniqueName)) {
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
