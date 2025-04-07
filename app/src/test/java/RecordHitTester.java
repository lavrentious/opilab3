import org.junit.jupiter.api.Test;
import ru.lavrent.weblab3.models.Record;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RecordHitTester {

    @Test
    public void testForth() {
        ru.lavrent.weblab3.models.Record record = new ru.lavrent.weblab3.models.Record(1, -1, 4, new Date());
        assertTrue(record.isHit());
    }

    @Test
    public void testThird() {
        ru.lavrent.weblab3.models.Record record = new ru.lavrent.weblab3.models.Record(-1, -1, 1, new Date());
        assertFalse(record.isHit());
    }

    @Test
    public void testFirst() {
        ru.lavrent.weblab3.models.Record record = new Record(1, -2, 1, new Date());
        assertFalse(record.isHit());
    }


}
