# Description

At the moment there are ERROR logs emitted during coordinated shutdown, when a member leaves the cluster.
This is too verbose and triggers too much false positives when scanning the logs for errors.


# Current Output

Message with ERROR severity telling that an AssociationError occurred:
```
[ERROR] [02/21/2018 00:02:53.355] [a-akka.remote.default-remote-dispatcher-4] [akka.tcp://a@localhost:2552/system/endpointManager/endpointWriter-akka.tcp%3A%2F%2Fa%40localhost%3A2553-1] AssociationError [akka.tcp://a@localhost:2552] <- [akka.tcp://a@localhost:2553]: Error [Shut down address: akka.tcp://a@localhost:2553] [
akka.remote.ShutDownAssociation: Shut down address: akka.tcp://a@localhost:2553
Caused by: akka.remote.transport.Transport$InvalidAssociationException: The remote system terminated the association because it is shutting down.
]
[ERROR] [02/21/2018 00:02:53.355] [a-akka.remote.default-remote-dispatcher-13] [akka.tcp://a@localhost:2552/system/endpointManager/reliableEndpointWriter-akka.tcp%3A%2F%2Fa%40localhost%3A2553-0/endpointWriter] AssociationError [akka.tcp://a@localhost:2552] -> [akka.tcp://a@localhost:2553]: Error [Shut down address: akka.tcp://a@localhost:2553] [
akka.remote.ShutDownAssociation: Shut down address: akka.tcp://a@localhost:2553
Caused by: akka.remote.transport.Transport$InvalidAssociationException: The remote system terminated the association because it is shutting down.
]
```


# Expected Output

No error message is emitted, since it is a coordinated shutdown.


# Steps to reproduce

- Clone this repository
- Start two separate shells
- Run `sbt -J-Dakka.remote.netty.tcp.port=2552 run` on shell #1
- Run `sbt -J-Dakka.remote.netty.tcp.port=2553 run` on shell #2
- Hit <kbd>CTRL-C</kbd> on shell #2
- Inspect log of shell #1


# Full Output #1

```
sbt -J-Dakka.remote.netty.tcp.port=2552 run
[info] Loading settings from global.sbt,idea.sbt ...
[info] Loading global plugins from /Users/isaias/.sbt/1.0/plugins
[info] Loading project definition from /Users/isaias/akka-cluster-error-on-coordinatedshutdown/project
[info] Loading settings from build.sbt ...
[info] Set current project to akka-cluster-error-on-coordinatedshutdown (in build file:/Users/isaias/akka-cluster-error-on-coordinatedshutdown/)
[info] Running akka_playground.Main
[INFO] [02/21/2018 00:01:52.786] [run-main-0] [akka.remote.Remoting] Starting remoting
[INFO] [02/21/2018 00:01:52.946] [run-main-0] [akka.remote.Remoting] Remoting started; listening on addresses :[akka.tcp://a@localhost:2552]
[INFO] [02/21/2018 00:01:52.949] [run-main-0] [akka.remote.Remoting] Remoting now listens on addresses: [akka.tcp://a@localhost:2552]
[INFO] [02/21/2018 00:01:52.959] [run-main-0] [akka.cluster.Cluster(akka://a)] Cluster Node [akka.tcp://a@localhost:2552] - Starting up...
[INFO] [02/21/2018 00:01:52.996] [run-main-0] [akka.cluster.Cluster(akka://a)] Cluster Node [akka.tcp://a@localhost:2552] - Registered cluster JMX MBean [akka:type=Cluster]
[INFO] [02/21/2018 00:01:52.996] [run-main-0] [akka.cluster.Cluster(akka://a)] Cluster Node [akka.tcp://a@localhost:2552] - Started up successfully
[INFO] [02/21/2018 00:01:53.024] [a-akka.actor.default-dispatcher-14] [akka.cluster.Cluster(akka://a)] Cluster Node [akka.tcp://a@localhost:2552] - No seed-nodes configured, manual cluster join required
[WARN] [02/21/2018 00:01:53.094] [a-akka.remote.default-remote-dispatcher-13] [akka.tcp://a@localhost:2552/system/endpointManager/reliableEndpointWriter-akka.tcp%3A%2F%2Fa%40localhost%3A2553-0] Association with remote system [akka.tcp://a@localhost:2553] has failed, address is now gated for [5000] ms. Reason: [Association failed with [akka.tcp://a@localhost:2553]] Caused by: [Connection refused: localhost/127.0.0.1:2553]
[WARN] [02/21/2018 00:01:53.094] [New I/O boss #3] [NettyTransport(akka://a)] Remote connection to [null] failed with java.net.ConnectException: Connection refused: localhost/127.0.0.1:2553
[INFO] [02/21/2018 00:01:53.098] [a-akka.actor.default-dispatcher-3] [akka://a/deadLetters] Message [akka.cluster.InternalClusterAction$InitJoin$] from Actor[akka://a/system/cluster/core/daemon/firstSeedNodeProcess-1#975014343] to Actor[akka://a/deadLetters] was not delivered. [1] dead letters encountered. This logging can be turned off or adjusted with configuration settings 'akka.log-dead-letters' and 'akka.log-dead-letters-during-shutdown'.
[INFO] [02/21/2018 00:01:54.048] [a-akka.actor.default-dispatcher-15] [akka://a/deadLetters] Message [akka.cluster.InternalClusterAction$InitJoin$] from Actor[akka://a/system/cluster/core/daemon/firstSeedNodeProcess-1#975014343] to Actor[akka://a/deadLetters] was not delivered. [2] dead letters encountered. This logging can be turned off or adjusted with configuration settings 'akka.log-dead-letters' and 'akka.log-dead-letters-during-shutdown'.
[INFO] [02/21/2018 00:01:55.049] [a-akka.actor.default-dispatcher-15] [akka://a/deadLetters] Message [akka.cluster.InternalClusterAction$InitJoin$] from Actor[akka://a/system/cluster/core/daemon/firstSeedNodeProcess-1#975014343] to Actor[akka://a/deadLetters] was not delivered. [3] dead letters encountered. This logging can be turned off or adjusted with configuration settings 'akka.log-dead-letters' and 'akka.log-dead-letters-during-shutdown'.
[INFO] [02/21/2018 00:01:56.048] [a-akka.actor.default-dispatcher-3] [akka://a/deadLetters] Message [akka.cluster.InternalClusterAction$InitJoin$] from Actor[akka://a/system/cluster/core/daemon/firstSeedNodeProcess-1#975014343] to Actor[akka://a/deadLetters] was not delivered. [4] dead letters encountered. This logging can be turned off or adjusted with configuration settings 'akka.log-dead-letters' and 'akka.log-dead-letters-during-shutdown'.
[INFO] [02/21/2018 00:01:57.048] [a-akka.actor.default-dispatcher-2] [akka://a/deadLetters] Message [akka.cluster.InternalClusterAction$InitJoin$] from Actor[akka://a/system/cluster/core/daemon/firstSeedNodeProcess-1#975014343] to Actor[akka://a/deadLetters] was not delivered. [5] dead letters encountered. This logging can be turned off or adjusted with configuration settings 'akka.log-dead-letters' and 'akka.log-dead-letters-during-shutdown'.
[INFO] [02/21/2018 00:01:58.056] [a-akka.actor.default-dispatcher-14] [akka.cluster.Cluster(akka://a)] Cluster Node [akka.tcp://a@localhost:2552] - Node [akka.tcp://a@localhost:2552] is JOINING, roles []
[INFO] [02/21/2018 00:01:58.065] [a-akka.actor.default-dispatcher-14] [akka.cluster.Cluster(akka://a)] Cluster Node [akka.tcp://a@localhost:2552] - Leader is moving node [akka.tcp://a@localhost:2552] to [Up]
[INFO] [02/21/2018 00:02:21.484] [a-akka.actor.default-dispatcher-15] [akka.cluster.Cluster(akka://a)] Cluster Node [akka.tcp://a@localhost:2552] - Received InitJoin message from [Actor[akka.tcp://a@localhost:2553/system/cluster/core/daemon/joinSeedNodeProcess-1#532236978]] to [akka.tcp://a@localhost:2552]
[INFO] [02/21/2018 00:02:21.484] [a-akka.actor.default-dispatcher-15] [akka.cluster.Cluster(akka://a)] Cluster Node [akka.tcp://a@localhost:2552] - Sending InitJoinAck message from node [akka.tcp://a@localhost:2552] to [Actor[akka.tcp://a@localhost:2553/system/cluster/core/daemon/joinSeedNodeProcess-1#532236978]]
[INFO] [02/21/2018 00:02:21.531] [a-akka.actor.default-dispatcher-20] [akka.cluster.Cluster(akka://a)] Cluster Node [akka.tcp://a@localhost:2552] - Node [akka.tcp://a@localhost:2553] is JOINING, roles []
[INFO] [02/21/2018 00:02:22.037] [a-akka.actor.default-dispatcher-16] [akka.cluster.Cluster(akka://a)] Cluster Node [akka.tcp://a@localhost:2552] - Leader is moving node [akka.tcp://a@localhost:2553] to [Up]
[INFO] [02/21/2018 00:02:53.036] [a-akka.actor.default-dispatcher-19] [akka.cluster.Cluster(akka://a)] Cluster Node [akka.tcp://a@localhost:2552] - Leader is moving node [akka.tcp://a@localhost:2553] to [Exiting]
[INFO] [02/21/2018 00:02:53.330] [a-akka.actor.default-dispatcher-20] [akka.cluster.Cluster(akka://a)] Cluster Node [akka.tcp://a@localhost:2552] - Exiting confirmed [akka.tcp://a@localhost:2553]
[ERROR] [02/21/2018 00:02:53.355] [a-akka.remote.default-remote-dispatcher-4] [akka.tcp://a@localhost:2552/system/endpointManager/endpointWriter-akka.tcp%3A%2F%2Fa%40localhost%3A2553-1] AssociationError [akka.tcp://a@localhost:2552] <- [akka.tcp://a@localhost:2553]: Error [Shut down address: akka.tcp://a@localhost:2553] [
akka.remote.ShutDownAssociation: Shut down address: akka.tcp://a@localhost:2553
Caused by: akka.remote.transport.Transport$InvalidAssociationException: The remote system terminated the association because it is shutting down.
]
[ERROR] [02/21/2018 00:02:53.355] [a-akka.remote.default-remote-dispatcher-13] [akka.tcp://a@localhost:2552/system/endpointManager/reliableEndpointWriter-akka.tcp%3A%2F%2Fa%40localhost%3A2553-0/endpointWriter] AssociationError [akka.tcp://a@localhost:2552] -> [akka.tcp://a@localhost:2553]: Error [Shut down address: akka.tcp://a@localhost:2553] [
akka.remote.ShutDownAssociation: Shut down address: akka.tcp://a@localhost:2553
Caused by: akka.remote.transport.Transport$InvalidAssociationException: The remote system terminated the association because it is shutting down.
]
[INFO] [02/21/2018 00:02:54.035] [a-akka.actor.default-dispatcher-15] [akka.cluster.Cluster(akka://a)] Cluster Node [akka.tcp://a@localhost:2552] - Leader is removing confirmed Exiting node [akka.tcp://a@localhost:2553]
^C[INFO] [02/21/2018 00:03:03.302] [Thread-2] [CoordinatedShutdown(akka://a)] Starting coordinated shutdown from JVM shutdown hook
[INFO] [02/21/2018 00:03:03.312] [a-akka.actor.default-dispatcher-3] [akka.cluster.Cluster(akka://a)] Cluster Node [akka.tcp://a@localhost:2552] - Marked address [akka.tcp://a@localhost:2552] as [Leaving]
[INFO] [02/21/2018 00:03:04.034] [a-akka.actor.default-dispatcher-15] [akka.cluster.Cluster(akka://a)] Cluster Node [akka.tcp://a@localhost:2552] - Exiting (leader), starting coordinated shutdown
[INFO] [02/21/2018 00:03:04.035] [a-akka.actor.default-dispatcher-15] [akka.cluster.Cluster(akka://a)] Cluster Node [akka.tcp://a@localhost:2552] - Leader is moving node [akka.tcp://a@localhost:2552] to [Exiting]
[INFO] [02/21/2018 00:03:04.035] [a-akka.actor.default-dispatcher-15] [akka.cluster.Cluster(akka://a)] Cluster Node [akka.tcp://a@localhost:2552] - Exiting completed
[INFO] [02/21/2018 00:03:04.036] [a-akka.actor.default-dispatcher-15] [akka.cluster.Cluster(akka://a)] Cluster Node [akka.tcp://a@localhost:2552] - Shutting down...
[INFO] [02/21/2018 00:03:04.038] [a-akka.actor.default-dispatcher-15] [akka.cluster.Cluster(akka://a)] Cluster Node [akka.tcp://a@localhost:2552] - Successfully shut down
[INFO] [02/21/2018 00:03:04.043] [a-akka.remote.default-remote-dispatcher-5] [akka.tcp://a@localhost:2552/system/remoting-terminator] Shutting down remote daemon.
[INFO] [02/21/2018 00:03:04.045] [a-akka.remote.default-remote-dispatcher-5] [akka.tcp://a@localhost:2552/system/remoting-terminator] Remote daemon shut down; proceeding with flushing remote transports.
[INFO] [02/21/2018 00:03:04.061] [a-akka.actor.default-dispatcher-2] [akka.remote.Remoting] Remoting shut down
[INFO] [02/21/2018 00:03:04.061] [a-akka.remote.default-remote-dispatcher-4] [akka.tcp://a@localhost:2552/system/remoting-terminator] Remoting shut down.
```


# Full Output #2

```
sbt -J-Dakka.remote.netty.tcp.port=2553 run
[info] Loading settings from global.sbt,idea.sbt ...
[info] Loading global plugins from /Users/isaias/.sbt/1.0/plugins
[info] Loading project definition from /Users/isaias/akka-cluster-error-on-coordinatedshutdown/project
[info] Loading settings from build.sbt ...
[info] Set current project to akka-cluster-error-on-coordinatedshutdown (in build file:/Users/isaias/akka-cluster-error-on-coordinatedshutdown/)
[info] Running akka_playground.Main
[INFO] [02/21/2018 00:02:21.079] [run-main-0] [akka.remote.Remoting] Starting remoting
[INFO] [02/21/2018 00:02:21.234] [run-main-0] [akka.remote.Remoting] Remoting started; listening on addresses :[akka.tcp://a@localhost:2553]
[INFO] [02/21/2018 00:02:21.237] [run-main-0] [akka.remote.Remoting] Remoting now listens on addresses: [akka.tcp://a@localhost:2553]
[INFO] [02/21/2018 00:02:21.247] [run-main-0] [akka.cluster.Cluster(akka://a)] Cluster Node [akka.tcp://a@localhost:2553] - Starting up...
[INFO] [02/21/2018 00:02:21.285] [run-main-0] [akka.cluster.Cluster(akka://a)] Cluster Node [akka.tcp://a@localhost:2553] - Registered cluster JMX MBean [akka:type=Cluster]
[INFO] [02/21/2018 00:02:21.285] [run-main-0] [akka.cluster.Cluster(akka://a)] Cluster Node [akka.tcp://a@localhost:2553] - Started up successfully
[INFO] [02/21/2018 00:02:21.313] [a-akka.actor.default-dispatcher-14] [akka.cluster.Cluster(akka://a)] Cluster Node [akka.tcp://a@localhost:2553] - No seed-nodes configured, manual cluster join required
[INFO] [02/21/2018 00:02:21.632] [a-akka.actor.default-dispatcher-15] [akka.cluster.Cluster(akka://a)] Cluster Node [akka.tcp://a@localhost:2553] - Welcome from [akka.tcp://a@localhost:2552]
^C[INFO] [02/21/2018 00:02:52.278] [Thread-2] [CoordinatedShutdown(akka://a)] Starting coordinated shutdown from JVM shutdown hook
[INFO] [02/21/2018 00:02:52.289] [a-akka.actor.default-dispatcher-3] [akka.cluster.Cluster(akka://a)] Cluster Node [akka.tcp://a@localhost:2553] - Marked address [akka.tcp://a@localhost:2553] as [Leaving]
[INFO] [02/21/2018 00:02:53.323] [a-akka.actor.default-dispatcher-16] [akka.cluster.Cluster(akka://a)] Cluster Node [akka.tcp://a@localhost:2553] - Exiting, starting coordinated shutdown
[INFO] [02/21/2018 00:02:53.325] [a-akka.actor.default-dispatcher-22] [akka.cluster.Cluster(akka://a)] Cluster Node [akka.tcp://a@localhost:2553] - Exiting completed
[INFO] [02/21/2018 00:02:53.328] [a-akka.actor.default-dispatcher-22] [akka.cluster.Cluster(akka://a)] Cluster Node [akka.tcp://a@localhost:2553] - Shutting down...
[INFO] [02/21/2018 00:02:53.329] [a-akka.actor.default-dispatcher-22] [akka.cluster.Cluster(akka://a)] Cluster Node [akka.tcp://a@localhost:2553] - Successfully shut down
[INFO] [02/21/2018 00:02:53.340] [a-akka.remote.default-remote-dispatcher-5] [akka.tcp://a@localhost:2553/system/remoting-terminator] Shutting down remote daemon.
[INFO] [02/21/2018 00:02:53.342] [a-akka.remote.default-remote-dispatcher-5] [akka.tcp://a@localhost:2553/system/remoting-terminator] Remote daemon shut down; proceeding with flushing remote transports.
[INFO] [02/21/2018 00:02:53.368] [a-akka.actor.default-dispatcher-2] [akka.remote.Remoting] Remoting shut down
[INFO] [02/21/2018 00:02:53.368] [a-akka.remote.default-remote-dispatcher-13] [akka.tcp://a@localhost:2553/system/remoting-terminator] Remoting shut down.
```

