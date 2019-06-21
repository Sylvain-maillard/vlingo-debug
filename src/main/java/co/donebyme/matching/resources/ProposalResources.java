package co.donebyme.matching.resources;

import co.donebyme.matching.model.Proposal;
import io.vlingo.actors.Logger;
import io.vlingo.actors.Stage;
import io.vlingo.actors.World;
import io.vlingo.common.Completes;
import io.vlingo.http.Response;
import io.vlingo.http.ResponseHeader;
import io.vlingo.http.resource.Resource;
import io.vlingo.http.resource.ResourceBuilder;

import static io.vlingo.http.Response.Status.Created;

public class ProposalResources  {

    private final Logger logger;
    private Stage stage;

    public ProposalResources(World world) {

        logger = world.defaultLogger();
        this.stage = world.stage();
    }

    public Resource<?> routes() {
//        return ResourceBuilder.resource("Proposal Resources",
//                ResourceBuilder.post("/proposals")
 //                       .body(String.class)
  //                      .handle(this::submitProposal)
   //     );
        return ResourceBuilder.resource("Proposal Resources",
                ResourceBuilder.post("/proposals")
                .body(String.class)
                .handle(this::submitProposal)
        );
    }

    public Completes<Response> submitProposal(String body) {

        logger.log("Submit proposal" + body);
        String proposalId = Proposal.submitFor(stage);
        return Completes.withSuccess(Response.of(Created, ResponseHeader.headers(
                ResponseHeader.of(ResponseHeader.Location, "proposals/" + proposalId)
        )));

    }
}
