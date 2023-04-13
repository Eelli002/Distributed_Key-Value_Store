package consensus.raft;

public class AtomixCluster {
    Address[] clusterAddresses = {
        new Address("localhost", 5000),
        new Address("localhost", 5001),
        new Address("localhost", 5002)
    };
    
    Atomix.Builder builder = Atomix.builder()
        .withMemberId("node-1")
        .withAddress(new Address("localhost", 5000))
        .withMembershipProvider(BootstrapDiscoveryProvider.builder()
            .withNodes(clusterAddresses)
            .build());
    
    Atomix atomix = builder.build();
    atomix.start().join();
}
