package flooringmastery.dao;

import flooringmastery.model.Tax;

import java.util.List;

public interface FlooringMasteryTaxesDao {

    // dao functions for Taxes

    /**
     * Returns a List of all Taxes.
     *
     * @return Tax list containing all taxes on the roster.
     * @throws FlooringMasteryPersistenceException
     */
    List<Tax> getAllTaxes()
            throws FlooringMasteryPersistenceException;

    /**
     * Returns the tax object associated with the state abbreviation.
     * Returns null if no such tax exists
     *
     * @param stateAbbreviation abbreviated state name of the tax to retrieve
     * @return the Tax object associated with the given state abbreviation.
     * null if no such tax exists
     * @throws FlooringMasteryPersistenceException
     */
    Tax getTax(String stateAbbreviation)
            throws FlooringMasteryPersistenceException;
}
