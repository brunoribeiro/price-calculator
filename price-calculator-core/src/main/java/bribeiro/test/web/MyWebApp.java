package bribeiro.test.web;




import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class MyWebApp extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        HashSet<Class<?>> resources = new HashSet<Class<?>>();
        resources.add(PriceCalculatorWS.class);

        return resources;
    }
}