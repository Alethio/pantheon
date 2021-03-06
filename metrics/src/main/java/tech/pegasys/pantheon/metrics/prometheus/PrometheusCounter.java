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
package tech.pegasys.pantheon.metrics.prometheus;

import tech.pegasys.pantheon.metrics.Counter;
import tech.pegasys.pantheon.metrics.LabelledMetric;

class PrometheusCounter implements LabelledMetric<Counter> {

  private final io.prometheus.client.Counter counter;

  public PrometheusCounter(final io.prometheus.client.Counter counter) {
    this.counter = counter;
  }

  @Override
  public Counter labels(final String... labels) {
    return new UnlabelledCounter(counter.labels(labels));
  }

  private static class UnlabelledCounter implements Counter {
    private final io.prometheus.client.Counter.Child counter;

    private UnlabelledCounter(final io.prometheus.client.Counter.Child counter) {
      this.counter = counter;
    }

    @Override
    public void inc() {
      counter.inc();
    }

    @Override
    public void inc(final long amount) {
      counter.inc(amount);
    }
  }
}
