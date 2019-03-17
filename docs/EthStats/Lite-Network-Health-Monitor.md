description: Lite Network Health Monitor
<!--- END of page meta data -->

# Lite Network Health Monitor

Use the EthStats Lite Health Monitor to monitor the health of private networks by displaying real time and 
historical statistics about the network nodes.

The lite version supports in-memory persistence or using Redis to persist a fixed number of blocks (by default,
the last 3000. The full version requires PostgreSQL or Cassandra to support persistence storage. The full 
version has a more complex configuration to run the services in cluster mode.

View the Network Health Monitor for the [Ethereum MainNet](https://net.ethstats.io)

!!! note 
     The EthStats Lite Block Explorer is an [Alethio product](https://aleth.io/).

## Statistics Overview

Statistics displayed by the Network Health Explorer: 

* Nodes in the network with specific metrics about the last received block such as block number, 
block hash, transaction count, and uncle count, block time and propagation time 
* Node information including connected peers, whether the node is  mining, hash rate, latency, and uptime
* Charts for Block Time, Block Difficulty, Block Gas Limit, Block Uncles, Block Transactions, Block Gas Used, 
Block Propagation Histogram, and Top Miners
* IP based geolocation overview
* Node logs. Node logs display the data sent by a node
* Block history.  Block history provides the ability to go back in time and playback the block propagation
 throughout the nodes

### Pre-requisities 

[Docker](https://docs.docker.com/install/)

!!! tip
    The Network Health Monitor has a number of dependencies. Using Docker is the easiest way to demonstrate
    the using the Network Health Monitor with Pantheon. The [EthStats CLI](https://github.com/Alethio/ethstats-cli) 
    and [EthStats Network Server](https://github.com/Alethio/ethstats-network-server) documentation describe how 
    to install the Network Heath Explorer tools. 

## Installation 

### Pre-requisites 

[Docker](https://docs.docker.com/install/)

### Server

Git clone ethstats-network-server 

Go to /ethstats-network-server/docker/lite-mode/memory-persistence 

`docker-compose up`

### Pantheon 

With Websockets enabled in Docker:

docker run -p 8546:8546 --mount type=bind,source=/tmp/datadir,target=/var/lib/pantheon pegasyseng/pantheon:latest --miner-enabled --miner-coinbase fe3b557e8fb62b89f4916b721be55ceb828dbd73 --rpc-http-cors-origins="all" --rpc-ws-enabled --network=dev

### Client 

docker run -it --restart always --net host -v /Users/madelinemurray/opt/ethstats-cli/:/root/.config/configstore/ alethio/ethstats-cli --register --account-email madeline.murray@consensys.net --node-name your_node_name --server-url http://localhost:3000 --client-url ws://127.0.0.1:8546

docker stop etc 

----------------------------------------

## Installation

The installation process is done through multiple steps. First the server needs to be installed with its dependencies and then for every node in the network the client app `ethstats-cli` is needed.

#### Server

In the server's repo there are 2 examples of `docker-compose` files to set it up in lite mode with:
* [Memory persistence](https://github.com/Alethio/ethstats-network-server/blob/master/docker/lite-mode/memory-persistence/docker-compose.yml) - the consumed data will be persisted in memory in a json data model and in case of a crash/restart the data is lost
* [Redis persistence](https://github.com/Alethio/ethstats-network-server/blob/master/docker/lite-mode/redis-persistence/docker-compose.yml) - the consumed data will be persisted into Redis 

The examples contains all the needed dependencies like: 
* [Deepstream](https://github.com/deepstreamIO/deepstream.io) - for sending event based data to the dashboard in real time
* [ethstats-network-dashboard](https://github.com/Alethio/ethstats-network-dashboard) - front end dashboard
* [Redis](https://redis.io/) - if persistence is needed

More details about installing and running are available in the github [repository](https://github.com/Alethio/ethstats-network-server).

#### EthStats-CLI

After the server is up and running the next step is to install `ethstats-cli` for the nodes in the network. This can be done through NPM or Docker.

IMPORTANT: Make sure when you run `ethstats-cli` for the first time to add the `--server-url` flag to connect to your server like in the examples bellow. By default it will connect to the server that consumes data for the Ethereum mainnet.
 
* [NPM](https://github.com/Alethio/ethstats-cli#install) - the package needs to be installed globally and also can be used as a [daemon](https://github.com/Alethio/ethstats-cli#daemon)
  ```sh
  npm install -g ethstats-cli
  ```
  the run the app:
  ```sh
  $ ethstats-cli --server-url http://your-server:port
  ```  

  On the first run of the app the first thing it does is to register the Pantheon node to your server. 
  For this you will be asked about an email address and node name.
  

* [Docker](https://github.com/Alethio/ethstats-cli#docker) - images available on [docker hub](https://hub.docker.com/r/alethio/ethstats-cli)
  ```sh
  docker run -d \
  --restart always \
  --net host \
  -v /opt/ethstats-cli/:/root/.config/configstore/ \
  alethio/ethstats-cli --register --account-email your@email.com --node-name your_node_name --server-url http://your-server:port
  ```

  This is a non interactive method, thus the registration of the Pantheon node is done automatically through the `--register` flag.  

The app is configured by default to connect to the Pantheon node on your local host (http://localhost:8545).
To connect to a node running on a different host see `--client-url` under [CLI Options](https://github.com/Alethio/ethstats-cli#cli-options).

More details about installing and running are available in the github [repository](https://github.com/Alethio/ethstats-cli).  

## Github repositories
* [ethstats-cli](https://github.com/Alethio/ethstats-cli) - client app that extracts data from a node and sends it to the server 
* [ethstats-network-server](https://github.com/Alethio/ethstats-network-server) - service that consumes the data received from the nodes through the client app 
* [ethstats-network-dashboard](https://github.com/Alethio/ethstats-network-dashboard) - front end dashboard  
