package co.donebyme.matching.model;


import io.vlingo.common.Completes;
import io.vlingo.lattice.model.sourcing.EventSourced;
import io.vlingo.lattice.model.sourcing.Sourced;

public class ProposalEntity extends EventSourced implements Proposal {

    static {
        Sourced.registerConsumer(ProposalEntity.class, ProposalSubmitted.class, ProposalEntity::whenProposalSubmitted);
    }

    private final String id;
    private boolean submitted;

    public ProposalEntity(String id) {
        this.id = id;
        this.submitted = false;
    }


    @Override
    public Completes<String> submitFor() {
        apply(new ProposalSubmitted(id));
        return completes().with(id);
    }

    @Override
    protected String streamName() {
        return id;
    }


    private void whenProposalSubmitted(ProposalSubmitted source) {
        this.submitted = true;
        System.out.println("yeah it works !!!!");
    }
}
