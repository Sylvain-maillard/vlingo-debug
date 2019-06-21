package co.donebyme.matching.infrastructure;

import co.donebyme.matching.model.ProposalEntity;
import co.donebyme.matching.model.ProposalSubmitted;
import co.donebyme.matching.resources.ProposalResources;
import io.vlingo.actors.World;
import io.vlingo.http.resource.Configuration;
import io.vlingo.http.resource.Resources;
import io.vlingo.http.resource.Server;
import io.vlingo.http.resource.serialization.JsonSerialization;
import io.vlingo.lattice.model.sourcing.SourcedTypeRegistry;
import io.vlingo.symbio.*;
import io.vlingo.symbio.store.journal.Journal;
import io.vlingo.symbio.store.journal.NoOpJournalListener;
import io.vlingo.symbio.store.journal.inmemory.InMemoryJournal;
import io.vlingo.symbio.store.journal.inmemory.InMemoryJournalActor;

public class Bootstrap {

    private final World world;
    private static Bootstrap instance;
    private final Server server;
    private final Journal journal;

    private Bootstrap() {
        world = World.startWithDefaults("my-world");

        ProposalResources proposalResources = new ProposalResources(world);

        Resources routes = Resources.are(proposalResources.routes());

        server = Server.startWith(world.stage(), routes,
                8081,
                Configuration.Sizing.define(),
                Configuration.Timing.define());

        journal = world.actorFor(Journal.class, InMemoryJournalActor.class, new NoOpJournalListener());

        EntryAdapterProvider.instance(world);

        SourcedTypeRegistry registry = new SourcedTypeRegistry(world);

        registry.register(new SourcedTypeRegistry.Info(journal, ProposalEntity.class, ProposalEntity.class.getSimpleName()));

        journal.registerEntryAdapter(ProposalSubmitted.class, new EntryAdapter<ProposalSubmitted, BaseEntry.TextEntry>() {
            @Override
            public ProposalSubmitted fromEntry(BaseEntry.TextEntry entry) {
                return JsonSerialization.deserialized(entry.entryData(), ProposalSubmitted.class);
            }

            @Override
            public BaseEntry.TextEntry toEntry(ProposalSubmitted source) {
                return toEntry(source, source.getProposalId());
            }

            @Override
            public BaseEntry.TextEntry toEntry(ProposalSubmitted source, String id) {
                String serialized = JsonSerialization.serialized(source);
                return new BaseEntry.TextEntry(id, ProposalSubmitted.class, 1, serialized, Metadata.nullMetadata());
            }
        });


    }

    public static void main(String[] args) {

        instance = new Bootstrap();
    }
}
