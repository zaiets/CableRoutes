package app.repository.dao.business.implsql;

import app.repository.dao.AbstractDao;
import app.repository.dao.business.ILineDao;
import app.repository.entities.business.Cable;
import app.repository.entities.business.JoinPoint;
import app.repository.entities.business.Line;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class LineDaoImpl extends AbstractDao<Integer, Line> implements ILineDao {

    static final Logger logger = LoggerFactory.getLogger(LineDaoImpl.class);

    @Override
    public boolean create(Line line) {
        logger.info("Creating new line in DB: {}", line.toString());
        persist(line);
        return getByKey(line.getId()) != null;
    }

    @Override
    public boolean createOrUpdate(Line line) {
        logger.info("Create or update line {} in DB!", line.toString());
        saveOrUpdate(line);
        return getByKey(line.getId()).equals(line);
    }


    @Override
    public boolean change(Line line) {
        logger.info("Updating line: {}", line.toString());
        super.update(line);
        return getByKey(line.getId()).equals(line);
    }

    @Override
    public Line read(int id) {
        logger.info("Reading line by ID: {}", id);
        Line line = getByKey(id);
        if (line != null) {
            Hibernate.initialize(line.getStartPoint());
            Hibernate.initialize(line.getEndPoint());
        }
        return line;
    }

    @Override
    public boolean erase(Line line) {
        logger.info("Erasing line: {}", line.toString());
        delete(line);
        return readAllByTwoJoinPoints(line.getStartPoint(), line.getEndPoint()).isEmpty();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Line> getAll() {
        logger.info("Reading all lines");
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("ID"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Line> lines = (List<Line>) criteria.list();
        for (Line line : lines) {
            Hibernate.initialize(line.getStartPoint());
            Hibernate.initialize(line.getEndPoint());
        }
        return lines;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Line> readAllByJoinPoint(JoinPoint joinPoint) {
        List<Line> lines = new ArrayList<>();
        logger.info("Reading all lines by join point {}", joinPoint.getUniqueName());
        Criteria criteria1 = createEntityCriteria().addOrder(Order.asc("ID"));
        criteria1.add(Restrictions.like("START_JOIN_POINT_KKS", joinPoint.getUniqueName()));
        criteria1.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        lines.addAll((List<Cable>) criteria1.list());
        Criteria criteria2 = createEntityCriteria().addOrder(Order.asc("KKS"));
        criteria2.add(Restrictions.like("END_JOIN_POINT_KKS", joinPoint.getUniqueName()));
        criteria2.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        lines.addAll((List<Cable>) criteria2.list());
        for (Line line : lines) {
            Hibernate.initialize(line.getStartPoint());
            Hibernate.initialize(line.getEndPoint());
        }
        return lines;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Line> readAllByTwoJoinPoints(JoinPoint pointOne, JoinPoint pointTwo) {
        List<Line> lines = new ArrayList<>();
        logger.info("Reading all lines by join points {} and {}", pointOne.getUniqueName(), pointTwo.getUniqueName());
        Criteria criteria1 = createEntityCriteria().addOrder(Order.asc("KKS"));
        criteria1.add(Restrictions.like("START_JOIN_POINT_KKS", pointOne.getUniqueName()));
        criteria1.add(Restrictions.like("END_JOIN_POINT_KKS", pointTwo.getUniqueName()));
        criteria1.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        lines.addAll((List<Cable>) criteria1.list());
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("KKS"));
        criteria.add(Restrictions.like("START_JOIN_POINT_KKS", pointTwo.getUniqueName()));
        criteria.add(Restrictions.like("END_JOIN_POINT_KKS", pointOne.getUniqueName()));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        lines.addAll((List<Cable>) criteria.list());
        for (Line line : lines) {
            Hibernate.initialize(line.getStartPoint());
            Hibernate.initialize(line.getEndPoint());
        }
        return lines;
    }

}
