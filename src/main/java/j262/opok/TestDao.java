package j262.opok;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

@Service
@RequiredArgsConstructor
public class TestDao {
    private final EntityManager em;

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveManyRequired(int count) {
        for(int i=0; i<count; i++) {
            TestEntity t = new TestEntity();
            t.setName("saveManyRequired " + i);
            em.persist(t);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveManyRequiresNew(int count) {
        for(int i=0; i<count; i++) {
            TestEntity t = new TestEntity();
            t.setName("saveManyRequiresNew " + i);
            em.persist(t);
        }
    }

    public void saveManualCommitTransaction(int count) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            for (int i = 0; i < count; i++) {
                TestEntity t = new TestEntity();
                t.setName("saveManualCommitTransaction " + i);
                em.persist(t);
            }
        } finally {
            tr.commit();
        }
    }

    public void saveManualRollbackTransaction(int count) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            for (int i = 0; i < count; i++) {
                TestEntity t = new TestEntity();
                t.setName("saveManualRollbackTransaction " + i);
                em.persist(t);
            }
        } finally {
            tr.rollback();
        }
    }


}
