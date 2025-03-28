import java.util.ArrayList;
import java.util.List;

class CSVParser {
    private List<String[]> data;
    private int currentRowIndex;

    public CSVParser(List<String[]> data) {
        this.data = data;
        this.currentRowIndex = 0;
    }

    public String[] getRow() {
        if (currentRowIndex < data.size()) {
            return data.get(currentRowIndex++);
        }
        return null;
    }

    public void reset() {
        this.currentRowIndex = 0;
    }
}

class FileResource {
    private String filename;
    private List<String[]> data;

    public FileResource() {
        this.filename = "exports_small.csv"; // Default to small CSV if no name provided
        this.data = readFile();
    }

    public FileResource(String filename) {
        this.filename = filename;
        this.data = readFile();
    }

    private List<String[]> readFile() {
        List<String[]> records = new ArrayList<>();
        if (filename.equals("exports_small.csv")) {
            records.add(new String[]{"Germany", "motor vehicles, machinery, chemicals", "$1,547,000,000,000"});
            records.add(new String[]{"Macedonia", "textiles, footwear, iron and steel", "$3,421,000,000"});
            records.add(new String[]{"Malawi", "tobacco, sugar, tea", "$1,332,000,000"});
            records.add(new String[]{"Malaysia", "electronics, machinery, chemicals", "$231,300,000,000"});
            records.add(new String[]{"Namibia", "diamonds, copper, gold", "$4,597,000,000"});
            records.add(new String[]{"Peru", "copper, gold, minerals", "$36,430,000,000"});
            records.add(new String[]{"South Africa", "gold, diamonds, platinum", "$97,900,000,000"});
            records.add(new String[]{"United States", "soybeans, corn, aircraft", "$1,610,000,000,000"});
            records.add(new String[]{"China", "electronics, textiles, machinery", "$3,360,000,000,000"});
            records.add(new String[]{"India", "gems, jewelry, textiles", "$437,000,000,000"});
        } else if (filename.equals("exportdata.csv")) {
            // For demonstration, using the same data as small
            records.add(new String[]{"Germany", "motor vehicles, machinery, chemicals", "$1,547,000,000,000"});
            records.add(new String[]{"Macedonia", "textiles, footwear, iron and steel", "$3,421,000,000"});
            records.add(new String[]{"Malawi", "tobacco, sugar, tea", "$1,332,000,000"});
            records.add(new String[]{"Malaysia", "electronics, machinery, chemicals", "$231,300,000,000"});
            records.add(new String[]{"Namibia", "diamonds, copper, gold", "$4,597,000,000"});
            records.add(new String[]{"Peru", "copper, gold, minerals", "$36,430,000,000"});
            records.add(new String[]{"South Africa", "gold, diamonds, platinum", "$97,900,000,000"});
            records.add(new String[]{"United States", "soybeans, corn, aircraft", "$1,610,000,000,000"});
            records.add(new String[]{"China", "electronics, textiles, machinery", "$3,360,000,000,000"});
            records.add(new String[]{"India", "gems, jewelry, textiles", "$437,000,000,000"});
        }
        return records;
    }

    public CSVParser getCSVParser() {
        return new CSVParser(data);
    }
}

public class ExportAnalyzer {

    public String countryInfo(CSVParser parser, String country) {
        parser.reset();
        String[] row;
        while ((row = parser.getRow()) != null) {
            if (row[0].equals(country)) {
                return row[0] + ": " + row[1] + ": " + row[2];
            }
        }
        return "NOT FOUND";
    }

    public void listExportersTwoProducts(CSVParser parser, String exportItem1, String exportItem2) {
        parser.reset();
        String[] row;
        while ((row = parser.getRow()) != null) {
            String[] exports = row[1].split(", ");
            boolean hasItem1 = false;
            boolean hasItem2 = false;
            for (String export : exports) {
                if (export.equals(exportItem1)) {
                    hasItem1 = true;
                }
                if (export.equals(exportItem2)) {
                    hasItem2 = true;
                }
            }
            if (hasItem1 && hasItem2) {
                System.out.println(row[0]);
            }
        }
    }

    public int numberOfExporters(CSVParser parser, String exportItem) {
        parser.reset();
        int count = 0;
        String[] row;
        while ((row = parser.getRow()) != null) {
            String[] exports = row[1].split(", ");
            for (String export : exports) {
                if (export.equals(exportItem)) {
                    count++;
                    break; // Count each country only once
                }
            }
        }
        return count;
    }

    public void bigExporters(CSVParser parser, String amount) {
        parser.reset();
        String[] row;
        while ((row = parser.getRow()) != null) {
            if (row[2].length() > amount.length()) {
                System.out.println(row[0] + " " + row[2]);
            }
        }
    }

    public void tester() {
        FileResource fr = new FileResource(); // Starts with the exact code from the prompt

        // Part 2: countryInfo
        CSVParser parser = fr.getCSVParser();
        String countryInfoGermany = countryInfo(parser, "Germany");
        System.out.println("\nCountry Info for Germany: " + countryInfoGermany);

        parser = fr.getCSVParser();
        String countryInfoUnknown = countryInfo(parser, "Unknown Country");
        System.out.println("Country Info for Unknown Country: " + countryInfoUnknown);

        // Part 3: listExportersTwoProducts
        System.out.println("\nCountries exporting both gold and diamonds:");
        parser = fr.getCSVParser();
        listExportersTwoProducts(parser, "gold", "diamonds");

        System.out.println("\nCountries exporting both machinery and chemicals:");
        parser = fr.getCSVParser();
        listExportersTwoProducts(parser, "machinery", "chemicals");

        // Part 4: numberOfExporters
        parser = fr.getCSVParser();
        int goldExportersCount = numberOfExporters(parser, "gold");
        System.out.println("\nNumber of countries exporting gold: " + goldExportersCount);

        parser = fr.getCSVParser();
        int electronicsExportersCount = numberOfExporters(parser, "electronics");
        System.out.println("Number of countries exporting electronics: " + electronicsExportersCount);

        // Part 5: bigExporters
        System.out.println("\nCountries with export value greater than $999,999,999:");
        parser = fr.getCSVParser();
        bigExporters(parser, "$999,999,999");
    }

    public static void main(String[] args) {
        ExportAnalyzer analyzer = new ExportAnalyzer();
        analyzer.tester();
    }
}