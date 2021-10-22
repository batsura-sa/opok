package j262.opok;

import org.assertj.core.util.Streams;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = TestConfig.class)
class DaoTest {
    @Autowired
    private TestDao testDao;
    @Autowired
    private TestRepository testRepository;

    @Test
    void testSaveAll() {
        int required = 10000;
        int requiesNew = 10000;
        int commit = 10000;
        int rollback = 10000;

        testDao.saveManyRequired(required);
        testDao.saveManyRequiresNew(requiesNew);

        assertThrows(IllegalStateException.class, () -> testDao.saveManualCommitTransaction(commit));
        assertThrows(IllegalStateException.class, () -> testDao.saveManualRollbackTransaction(commit));

        Iterable<TestEntity> all = testRepository.findAll();

        all.forEach(System.out::println);
        assertEquals(required + requiesNew, Streams.stream(all).count());
    }
}