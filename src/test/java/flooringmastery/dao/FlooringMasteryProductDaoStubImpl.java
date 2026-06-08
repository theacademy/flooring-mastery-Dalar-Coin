package flooringmastery.dao;

import flooringmastery.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Stub of the Product DAO. Knows about a single product (Tile) so the
 * service layer can validate product types and pull pricing without a file.
 */
public class FlooringMasteryProductDaoStubImpl implements FlooringMasteryProductDao {

    public Product onlyProduct;

    public FlooringMasteryProductDaoStubImpl() {
        onlyProduct = new Product("Tile");
        onlyProduct.setCostPerSquareFoot(new BigDecimal("3.50"));
        onlyProduct.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
    }

    public FlooringMasteryProductDaoStubImpl(Product testProduct) {
        this.onlyProduct = testProduct;
    }

    @Override
    public List<Product> getAllProducts()
            throws FlooringMasteryPersistenceException {
        List<Product> products = new ArrayList<>();
        products.add(onlyProduct);
        return products;
    }

    @Override
    public Product getProduct(String productType)
            throws FlooringMasteryPersistenceException {
        if (productType.equals(onlyProduct.getProductType())) {
            return onlyProduct;
        }
        return null;
    }
}
