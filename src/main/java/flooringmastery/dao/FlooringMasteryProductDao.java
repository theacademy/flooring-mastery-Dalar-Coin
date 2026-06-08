package flooringmastery.dao;

import flooringmastery.model.Product;

import java.util.List;

public interface FlooringMasteryProductDao {
    /**
     * Returns a List of all Products.
     *
     * @return Product List containing all products.
     * @throws FlooringMasteryPersistenceException
     */
    List<Product> getAllProducts()
            throws FlooringMasteryPersistenceException;

    /**
     * Returns the product object associated with the given date and product number.
     * Returns null if no such product exists
     *
     * @param productType productType of the order to retrieve
     * @return the Product object associated with the given productType.
     * null if no such product exists
     * @throws FlooringMasteryPersistenceException
     */
    Product getProduct(String productType)
            throws FlooringMasteryPersistenceException;
}
