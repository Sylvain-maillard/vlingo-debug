package co.donebyme.matching.model;

import io.vlingo.lattice.model.DomainEvent;

public class ProposalSubmitted extends DomainEvent {
    private final String proposalId;

    public ProposalSubmitted(String proposalId) {
        this.proposalId = proposalId;
    }

    public String getProposalId() {
        return proposalId;
    }
}
