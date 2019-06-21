package co.donebyme.matching.model;

import io.vlingo.actors.Stage;
import io.vlingo.common.Completes;

import java.util.UUID;

public interface Proposal {

    static String submitFor(Stage stage) {
        String id = UUID.randomUUID().toString();

        Proposal proposal = stage.actorFor(Proposal.class, ProposalEntity.class, id);
        proposal.submitFor();

        return id;
    }

    Completes<String> submitFor();
}
