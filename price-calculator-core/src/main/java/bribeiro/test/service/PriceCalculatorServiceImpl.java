package bribeiro.test.service;

import bribeiro.test.ProductAdapter;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.runtime.model.DefaultComponent;

public class PriceCalculatorServiceImpl extends DefaultComponent implements PriceCalculatorService {

    @Override
    public Double computePrice(DocumentModel model) {

        ProductAdapter product = model.getAdapter(ProductAdapter.class);
        Double price = product.getPrice();

        return price * price;
    }
}
