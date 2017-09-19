package bribeiro.test.web;


import bribeiro.test.service.PriceCalculatorService;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.PathRef;
import org.nuxeo.ecm.webengine.WebEngine;
import org.nuxeo.runtime.api.Framework;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;


@Path("/product-price-calculator")
public class PriceCalculatorWS {

    @GET
    @Produces("text/html")
    public Object doGet() {
        return "Use me by adding \"/{workspace}/{product}\" to this page's URL.";
    }

    @GET
    @Path("/{workspace}/{product}")
    @Produces("application/json")
    public Response computePrice(@PathParam("workspace") String ws, @PathParam("product") String p) {
        DocumentModel product = WebEngine.getActiveContext()
                .getCoreSession()
                .getDocument(new PathRef("/default-domain/workspaces/" + ws + "/" + p));
        PriceCalculatorService service = Framework.getService(PriceCalculatorService.class);
        Double price = service.computePrice(product);

        return Response.ok(new Price(price), MediaType.APPLICATION_JSON).build();
    }

    @XmlRootElement
    static class Price {
        private Double price;

        public Price() {
            this(0d);
        }
        public Price(Double price) {
            this.price = price;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }
    }
}

