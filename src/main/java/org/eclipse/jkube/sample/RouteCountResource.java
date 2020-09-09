package org.eclipse.jkube.sample;

import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.openshift.api.model.RouteList;
import io.fabric8.openshift.client.DefaultOpenShiftClient;
import io.fabric8.openshift.client.OpenShiftClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/getroutes")
public class RouteCountResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return getNumberOfRoutes() + " Routes found in myproject namespace";
    }

    private String getNumberOfRoutes() {
        String result;
        try (OpenShiftClient oc = new DefaultOpenShiftClient()) {
            RouteList routeList = oc.routes().inNamespace("myproject").list();
            result =  "" + routeList.getItems().size();
        } catch (KubernetesClientException exception) {
            exception.printStackTrace();
            result = exception.getMessage();
        }
        return result;
    }

}
