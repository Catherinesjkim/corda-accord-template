package net.corda.accord;

import io.bluebank.braid.corda.BraidConfig;
import io.vertx.core.http.HttpServerOptions;
import net.corda.accord.flow.PromissoryNoteIssueFlow;
import net.corda.core.node.AppServiceHub;
import net.corda.core.node.ServiceHub;
import net.corda.core.node.services.CordaService;
import net.corda.core.serialization.SingletonSerializeAsToken;

@CordaService
public class BraidServer extends SingletonSerializeAsToken {

    private AppServiceHub appServiceHub;

    public BraidServer(AppServiceHub serviceHub) {
        this.appServiceHub = serviceHub;
//
//        new BraidConfig()
//                .withService("myService", new BraidService(appServiceHub))
//                .withPort((Integer) serviceHub.getAppContext().getConfig().get("braid"))
//                .withHttpServerOptions(new HttpServerOptions().setSsl(false))
//                .bootstrapBraid(appServiceHub);
    }

}
