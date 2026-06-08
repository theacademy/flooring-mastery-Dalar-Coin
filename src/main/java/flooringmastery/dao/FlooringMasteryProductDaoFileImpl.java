package flooringmastery.dao;

import flooringmastery.model.Product;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.*;

public class FlooringMasteryProductDaoFileImpl implements FlooringMasteryProductDao {

    public static final String PRODUCTS_FILE = "src/main/java/flooringmastery/Data/Products.txt";
    public static final String DELIMITER = ",";

    private Map<String, Product> products = new HashMap<>();

    public FlooringMasteryProductDaoFileImpl() {
    }

    @Override
    public List<Product> getAllProducts()
            throws FlooringMasteryPersistenceException {
        loadProducts();
        return new ArrayList<>(products.values());
    }

    @Override
    public Product getProduct(String productType)
            throws FlooringMasteryPersistenceException {
        loadProducts();
        return products.get(productType);
    }

    private Product unmarshallProduct(String productAsText) {
        String[] tokens = productAsText.split(DELIMITER);
        Product product = new Product();
        product.setProductType(tokens[0]);
        product.setCostPerSquareFoot(new BigDecimal(tokens[1]));
        product.setLaborCostPerSquareFoot(new BigDecimal(tokens[2]));
        return product;
    }

    private void loadProducts()
            throws FlooringMasteryPersistenceException {
        products.clear();
        Scanner scanner;
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(PRODUCTS_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException(
                    "-_- Could not load product data into memory.", e);
        }
        if (scanner.hasNextLine()) {
            scanner.nextLine(); // header
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.trim().isEmpty()) continue;
            Product currentProduct = unmarshallProduct(line);
            products.put(currentProduct.getProductType(), currentProduct);
        }
        scanner.close();
    }
}