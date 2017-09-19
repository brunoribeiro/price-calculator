package bribeiro.test;

import bribeiro.test.service.PriceCalculatorService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.automation.test.AutomationFeature;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.IdRef;
import org.nuxeo.runtime.RuntimeService;
import org.nuxeo.runtime.api.Framework;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(FeaturesRunner.class)
@Features(AutomationFeature.class)
@Deploy({"bribeiro.test.price-calculator-core","studio.extensions.bribeiro-SANDBOX"})
public class TestPriceCalculatorService {

    @Inject
    protected PriceCalculatorService service;

    @Inject
    protected CoreSession coreSession;

    @Test
    public void isFrameworkAvailable(){

        assertNotNull("No Runtime!", Framework.getRuntime());
    }


    @Test
    public void isComponentLoaded(){

        RuntimeService runtime = Framework.getRuntime();
        assertNotNull(runtime.getComponent("test.bribeiro.price-calculator"));
    }

    @Test
    public void isPriceCalculatorAvailable(){

        PriceCalculatorService service = Framework.getService(PriceCalculatorService.class);
        assertNotNull(service);
    }


    @Test
    public void testComputePrice() {

        DocumentModel model = coreSession.createDocumentModel("/", "Product 1", "Product");

        Double price = 5d;

        ProductAdapter product = model.getAdapter(ProductAdapter.class);

        product.setPrice(price);
        model = coreSession.createDocument(product.doc);
        coreSession.save();

        model = coreSession.getDocument(new IdRef(model.getId()));
        Assert.assertNotNull(model);


        Assert.assertEquals("no match!",product.getPrice(), 5, 0);

        product.setPrice(price);

        coreSession.save();
        Double computed = service.computePrice(model);

        Assert.assertEquals("no match!",price * price, computed, 0);

    }

}
