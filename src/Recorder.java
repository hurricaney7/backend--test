import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Recorder implements Runnable {
    final static String[] CURRENCIES = {
            "AUD", "BRL", "CAD", "CNY", "CZK", "DKK", "EUR",
            "HKD", "HUF", "ILS", "JPY", "MYR", "MXN", "TWD",
            "NZD", "NOK", "PHP", "PLN", "GBP", "RUB", "SGD",
            "KRW", "SEK", "CHF", "THB", "TRY", "USD"
    };
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private ArrayList<PaymentRecord> records = new ArrayList<PaymentRecord>();
    private String filename;
    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public ArrayList<PaymentRecord> getRecords() {
        return records;
    }


    /**
    * Add an monitor as the observer
    * */
    private void addMonitor(ScheduledExecutorService executorService) {
        executorService.scheduleAtFixedRate(new Monitor(records), 0, 10, TimeUnit.SECONDS);
    }

    /**
     * Read data from a file
     * extensions: .txt
     * */
    public void loadDataFromFile(String filename) {
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader bfr = new BufferedReader(fr);
            String line = null;
            while ((line = bfr.readLine()) != null) {
                loadLine(line, filename);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Listen user input from the console
     * */
    public void listenUserInput() {
        System.out.println("Please enter your payment below (e.g. HKD 300): ");
        String[] strs = {};
        String line = null;
        try {
            while (!(line = br.readLine()).equals("quit")) {
                loadLine(line);
            }
            System.out.println("Program exit");
            Thread.currentThread().interrupt();
            executorService.shutdown();
        } catch (IOException e) {
            System.out.println("An IO exception occurred");
            System.out.println(e);
        }
    }

    /**
     * Load payment record content from a line
     * */
    public void loadLine(String line) {
        String[] strs = line.trim().split("\\s+");
        if (strs.length == 2) {
            addRecord(strs[0], strs[1]);
        } else {
            System.out.println("Error: Invalid input!");
        }
    }

    public void loadLine(String line, String filename) {
        String[] strs = line.trim().split("\\s+");
        if (strs.length == 2) {
            addRecord(strs[0], strs[1]);
        } else {
            System.out.println("Error: Invalid input from file " + filename);
        }
    }

    /**
    * Add a new record
    * */
    public void addRecord(String currency, String amount) {
        float amountInFloat = formatAmount(amount);
        int currencyIndex = isCurrencyExist(currency);
        if (isValid(currency) && amountInFloat != 0) {
            if (currencyIndex != -1) {
                records.get(currencyIndex).addAmount(amountInFloat);
            } else {
                records.add(new PaymentRecord(currency, amountInFloat));
            }
        } else {
            System.out.println("Invalid currency or amount");
        }
    }

    /**
    * Check if a input currency is already exist,
     * if exists, return its current index
    * */
    public int isCurrencyExist(String currency) {
        int index = -1;
        for (int i = 0; i < records.toArray().length; i++) {
            index = records.get(i).getCurrency().equals(currency) ? i : index;
        }
        return index;
    }


    /**
    * Format amount to two fixed points
    * */
    public float formatAmount(String amount) {
        try {
            BigDecimal bd = new BigDecimal(amount);
            return bd.setScale(2, RoundingMode.FLOOR).floatValue();
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount format");
            return 0;
        }
    }

    /**
     * Check if a currency is valid
     * */
    public boolean isValid(String currency) {
        int counter = 1;
        for (int i = 0; i < CURRENCIES.length; i++) {
            counter -= currency.equals(CURRENCIES[i]) ? 1 : 0;
        }
        return counter == 0;
    }


    @Override
    public void run() {
        // Load data from two different files
        loadDataFromFile("records1.txt");
        loadDataFromFile("records2.txt");
        // Add an observer to watch the payment records
        addMonitor(executorService);
        // Start to listen user input
        listenUserInput();
    }
}
