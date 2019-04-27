import java.util.ArrayList;
import java.util.TimerTask;

public class Monitor extends TimerTask {
    private ArrayList<PaymentRecord> records;

    public Monitor(ArrayList<PaymentRecord> records) {
            this.records = records;
    }

    /*
    * Display all the payment records
    * */
    private void displayRecords() {
        System.out.println(" ");
        System.out.println("Your current payment record is: ");
        records.forEach((record) -> {
            if (record.getAmount() != 0) {
                System.out.println(record.getCurrency() + " " + record.getAmount());
            }
        });
        System.out.println(" ");
    }

    @Override
    public void run() {
        // After 5 seconds, display records
        displayRecords();
    }
}
