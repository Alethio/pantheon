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
package tech.pegasys.pantheon.ethereum.transaction;

import tech.pegasys.pantheon.ethereum.core.Address;
import tech.pegasys.pantheon.ethereum.core.Wei;
import tech.pegasys.pantheon.util.bytes.BytesValue;

import com.google.common.base.Objects;

// Represents parameters for a eth_call or eth_estimateGas JSON-RPC methods.
public class CallParameter {

  private final Address from;

  private final Address to;

  private final long gasLimit;

  private final Wei gasPrice;

  private final Wei value;

  private final BytesValue payload;

  public CallParameter(
      final Address from,
      final Address to,
      final long gasLimit,
      final Wei gasPrice,
      final Wei value,
      final BytesValue payload) {
    this.from = from;
    this.to = to;
    this.gasLimit = gasLimit;
    this.gasPrice = gasPrice;
    this.value = value;
    this.payload = payload;
  }

  public Address getFrom() {
    return from;
  }

  public Address getTo() {
    return to;
  }

  public long getGasLimit() {
    return gasLimit;
  }

  public Wei getGasPrice() {
    return gasPrice;
  }

  public Wei getValue() {
    return value;
  }

  public BytesValue getPayload() {
    return payload;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final CallParameter that = (CallParameter) o;
    return gasLimit == that.gasLimit
        && Objects.equal(from, that.from)
        && Objects.equal(to, that.to)
        && Objects.equal(gasPrice, that.gasPrice)
        && Objects.equal(value, that.value)
        && Objects.equal(payload, that.payload);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(from, to, gasLimit, gasPrice, value, payload);
  }
}
