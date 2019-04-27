import org.junit.Test;

import static org.junit.Assert.*;

public class RecorderTest {
    @Test
    public void loadDataFromFile() {
        Recorder recorder = new Recorder();
    }

    @Test
    public void listenUserInput() {
        Recorder recorder = new Recorder();
    }

    @Test
    public void loadLine() {
        Recorder recorder = new Recorder();
        recorder.loadLine("USD 200");
        assertEquals("USD", recorder.getRecords().get(0).getCurrency());
        assertEquals(200, recorder.getRecords().get(0).getAmount(), .01);
    }

    @Test
    public void loadLine1() {
        Recorder recorder = new Recorder();
        recorder.loadLine("ewqkjeklqw e");
        assertEquals(recorder.getRecords().toArray().length, 0);
    }

    @Test
    public void addRecord() {
        Recorder recorder = new Recorder();
        recorder.addRecord("HKD", "100.999");
        recorder.addRecord("usd", "321ewjkql");
        recorder.addRecord("ejwql", "666");
        assertEquals(recorder.getRecords().toArray().length, 1);
        assertEquals("HKD", recorder.getRecords().get(0).getCurrency());
        assertEquals(100.99, recorder.getRecords().get(0).getAmount(), .01);
    }

    @Test
    public void isCurrencyExist() {
        Recorder recorder = new Recorder();
        recorder.addRecord("HKD", "100.999");
        recorder.addRecord("USD", "200.999");
        assertNotEquals(-1, recorder.isCurrencyExist("HKD"));
        assertNotEquals(-1, recorder.isCurrencyExist("USD"));
        assertEquals(-1, recorder.isCurrencyExist("AUD"));
    }

    @Test
    public void formatAmount() {
        Recorder recorder = new Recorder();
        assertEquals(300.321, recorder.formatAmount("300.321798"), .01);
        assertEquals(300.88f, recorder.formatAmount("300.888"), .01);
        assertEquals(300.3, recorder.formatAmount("300.3"), .01);
        assertEquals(-1, recorder.formatAmount("-1"), .01);
    }

    @Test
    public void isValid() {
        Recorder recorder = new Recorder();
        assertTrue(recorder.isValid("USD"));
        assertTrue(recorder.isValid("AUD"));
        assertFalse(recorder.isValid("LLL"));
        assertFalse(recorder.isValid("usd"));
    }
}