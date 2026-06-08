package flooringmastery.dao;

import flooringmastery.model.Tax;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Stub of the Taxes DAO. Knows about a single state (CA) so the service
 * layer can validate states and pull a tax rate without reading a file.
 */
public class FlooringMasteryTaxesDaoStubImpl implements FlooringMasteryTaxesDao {

    public Tax onlyTax;

    public FlooringMasteryTaxesDaoStubImpl() {
        onlyTax = new Tax("CA");
        onlyTax.setStateName("California");
        onlyTax.setTaxRate(new BigDecimal("25.00"));
    }

    public FlooringMasteryTaxesDaoStubImpl(Tax testTax) {
        this.onlyTax = testTax;
    }

    @Override
    public List<Tax> getAllTaxes()
            throws FlooringMasteryPersistenceException {
        List<Tax> taxes = new ArrayList<>();
        taxes.add(onlyTax);
        return taxes;
    }

    @Override
    public Tax getTax(String stateAbbreviation)
            throws FlooringMasteryPersistenceException {
        if (stateAbbreviation.equals(onlyTax.getStateAbbr())) {
            return onlyTax;
        }
        return null;
    }
}
