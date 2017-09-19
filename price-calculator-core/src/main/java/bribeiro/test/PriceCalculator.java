package bribeiro.test;

import bribeiro.test.service.PriceCalculatorService;
import org.nuxeo.ecm.automation.core.Constants;
import org.nuxeo.ecm.automation.core.annotations.Context;
import org.nuxeo.ecm.automation.core.annotations.Operation;
import org.nuxeo.ecm.automation.core.annotations.OperationMethod;
import org.nuxeo.ecm.automation.core.annotations.Param;
import org.nuxeo.ecm.automation.core.collectors.DocumentModelCollector;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.NuxeoException;
import org.nuxeo.runtime.api.Framework;

/**
 *
 */
@Operation(id=PriceCalculator.ID, category=Constants.CAT_DOCUMENT, label="Price Calculator", description="Describe here what your operation does.")
public class PriceCalculator {

    public static final String ID = "Document.PriceCalculator";

    @Context
    protected CoreSession session;

    @Param(name = "path", required = false)
    protected String path;

    static final String PRODUCT_TYPE = "Product";


    @OperationMethod(collector = DocumentModelCollector.class)
    public DocumentModel run(DocumentModel input) throws NuxeoException {
        if (!(PRODUCT_TYPE.equals(input.getType()))) {
            throw new NuxeoException("Operation works only with "
                    + PRODUCT_TYPE + " document type.");
        }

        PriceCalculatorService service = Framework.getService(PriceCalculatorService.class);

        ProductAdapter product = input.getAdapter(ProductAdapter.class);

        Double result = service.computePrice(input);

        product.setPrice(result);

        return input;
    }

}
