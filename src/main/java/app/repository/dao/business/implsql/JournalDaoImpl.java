package app.repository.dao.business.implsql;

import app.repository.dao.AbstractDao;
import app.repository.dao.business.IJournalDao;
import app.repository.entities.business.Journal;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class JournalDaoImpl extends AbstractDao<String, Journal> implements IJournalDao {

    static final Logger logger = LoggerFactory.getLogger(JournalDaoImpl.class);

    @Override
    public boolean create(Journal journal) {
        logger.info("Creating new journal in DB: {}", journal.getUniqueName());
        persist(journal);
        return getByKey(journal.getUniqueName()) != null;
    }

    @Override
    public boolean createOrUpdate(Journal journal) {
        logger.info("Create or update journal {} in DB!", journal.getUniqueName());
        saveOrUpdate(journal);
        return getByKey(journal.getUniqueName()).equals(journal);
    }

    @Override
    public Journal read(String kks) {
        logger.info("Reading journal by KKS: {}", kks);
        return getByKey(kks);
    }

    @Override
    public boolean update(String kks, Journal journal) {
        logger.info("Updating journal: {}", journal.getUniqueName());
        update(journal);
        return getByKey(kks).equals(journal);
    }

    @Override
    public boolean delete(String kks) {
        logger.info("Deleting journal: {}", kks);
        Journal journal = getByKey(kks);
        if (journal == null) return true;
        else {
            super.delete(journal);
            return getByKey(kks) == null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Journal> getAll() {
        logger.info("Reading all journals");
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("KKS"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (List<Journal>) criteria.list();
    }

}
