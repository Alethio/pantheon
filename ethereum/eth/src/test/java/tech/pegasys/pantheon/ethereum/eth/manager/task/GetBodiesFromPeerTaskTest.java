/*
 * Copyright 2018 ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package tech.pegasys.pantheon.ethereum.eth.manager.task;

import static org.assertj.core.api.Assertions.assertThat;

import tech.pegasys.pantheon.ethereum.core.Block;
import tech.pegasys.pantheon.ethereum.core.BlockBody;
import tech.pegasys.pantheon.ethereum.core.BlockHeader;
import tech.pegasys.pantheon.ethereum.eth.manager.ethtaskutils.PeerMessageTaskTest;
import tech.pegasys.pantheon.ethereum.eth.manager.task.AbstractPeerTask.PeerTaskResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GetBodiesFromPeerTaskTest extends PeerMessageTaskTest<List<Block>> {

  @Override
  protected List<Block> generateDataToBeRequested() {
    final List<Block> requestedBlocks = new ArrayList<>();
    for (long i = 0; i < 3; i++) {
      final BlockHeader header = blockchain.getBlockHeader(10 + i).get();
      final BlockBody body = blockchain.getBlockBody(header.getHash()).get();
      requestedBlocks.add(new Block(header, body));
    }
    return requestedBlocks;
  }

  @Override
  protected EthTask<PeerTaskResult<List<Block>>> createTask(final List<Block> requestedData) {
    final List<BlockHeader> headersToComplete =
        requestedData.stream().map(Block::getHeader).collect(Collectors.toList());
    return GetBodiesFromPeerTask.forHeaders(
        protocolSchedule, ethContext, headersToComplete, metricsSystem);
  }

  @Override
  protected void assertPartialResultMatchesExpectation(
      final List<Block> requestedData, final List<Block> partialResponse) {
    assertThat(partialResponse.size()).isLessThanOrEqualTo(requestedData.size());
    assertThat(partialResponse.size()).isGreaterThan(0);
    for (final Block block : partialResponse) {
      assertThat(requestedData).contains(block);
    }
  }
}
