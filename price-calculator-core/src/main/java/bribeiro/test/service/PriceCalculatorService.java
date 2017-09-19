package bribeiro.test.service;

import org.nuxeo.ecm.core.api.DocumentModel;

public interface PriceCalculatorService {

    Double computePrice(DocumentModel doc);
}
