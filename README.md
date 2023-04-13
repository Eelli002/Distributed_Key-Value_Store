**Defining The Architecture**

A simple distributed key-value store should have the following components:

- A cluster of nodes to store and manage data
- A partitioning scheme to distribute data across nodes
- A replication strategy to ensure fault tolerance
- A simple client API for basic CRUD operations

![High Level Design](images/Distributed%20Key-Value%20Store%20HLD.jpg)

Here's a summary of the proposed architecture:

1. Load Balancer: Distributes incoming requests to Request Managers for efficient handling and load distribution.
1. Request Manager: Handles client requests, communicates with Metadata Manager to locate the data, and forwards the request to the appropriate Replication Group leader.
1. Metadata Manager: Stores and provides information about data location (ownership of tables and partitions) within the system.
1. Replication Groups: Consists of 3 to 5 nodes for redundancy, with each group having a leader responsible for coordinating CRUD operations within the group.
1. Controller: Monitors the system's state, including Replication Group load and table sizes. The controller can trigger table partitioning and redistribution when necessary to maintain balance and performance.

This architecture follows a distributed approach, with separation of concerns and redundancy built-in for fault tolerance. The Controller and Metadata Manager components serve as the control plane, while the Replication Groups handle the actual data storage and management.

**Basic operations:**

- **CreateTable()**: Create a new table in the system. This operation should update the Metadata Manager with the new table information and assign it to a Replication Group.
- **PUT(Table, Key, Value)**: Store the key-value pair in the specified table. The Request Manager should locate the responsible Replication Group leader and forward the request. The leader ensures high consistency before acknowledging the operation.
- **GET(Table, Key)**: Retrieve the value associated with the specified key from the given table. The Request Manager should locate the responsible Replication Group leader and forward the request. The leader returns the value from its local replica, ensuring high consistency.
- **Delete(Table, Key)**: Remove the key-value pair from the specified table. The Request Manager should locate the responsible Replication Group leader and forward the request. The leader ensures high consistency before acknowledging the operation.
- **List(Keys in table in sorted form)**: Retrieve a sorted list of keys in the specified table. The Request Manager should locate the responsible Replication Group leader and forward the request. The leader returns the sorted list of keys from its local replica, ensuring high consistency.
- **DeleteTable(optional)**: Remove the specified table from the system. This operation should update the Metadata Manager and remove table data from all the replicas in the Replication Group.
