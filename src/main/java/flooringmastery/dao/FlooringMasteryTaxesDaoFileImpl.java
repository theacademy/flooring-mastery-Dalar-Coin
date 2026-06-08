package flooringmastery.dao;

import flooringmastery.model.Tax;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.*;

public class FlooringMasteryTaxesDaoFileImpl implements FlooringMasteryTaxesDao {

    public static final String TAXES_FILE = "SampleFileData/Data/Taxes.txt";
    public static final String DELIMITER = ",";

    private Map<String, Tax> taxes = new HashMap<>();

    public FlooringMasteryTaxesDaoFileImpl() {
    }

    @Override
    public List<Tax> getAllTaxes()
            throws FlooringMasteryPersistenceException {
        loadTaxes();
        return new ArrayList<>(taxes.values());
    }

    @Override
    public Tax getTax(String stateAbbreviation)
            throws FlooringMasteryPersistenceException {
        loadTaxes();
        return taxes.get(stateAbbreviation);
    }

    private Tax unmarshallTax(String taxAsText) {
        String[] tokens = taxAsText.split(DELIMITER);
        Tax tax = new Tax();
        tax.setStateAbbr(tokens[0]);
        tax.setStateName(tokens[1]);
        tax.setTaxRate(new BigDecimal(tokens[2]));
        return tax;
    }

    private void loadTaxes()
            throws FlooringMasteryPersistenceException {
        taxes.clear();
        Scanner scanner;
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(TAXES_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException(
                    "-_- Could not load tax data into memory.", e);
        }
        if (scanner.hasNextLine()) {
            scanner.nextLine(); // header
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.trim().isEmpty()) continue;
            Tax currentTax = unmarshallTax(line);
            taxes.put(currentTax.getStateAbbr(), currentTax);
        }
        scanner.close();
    }
}