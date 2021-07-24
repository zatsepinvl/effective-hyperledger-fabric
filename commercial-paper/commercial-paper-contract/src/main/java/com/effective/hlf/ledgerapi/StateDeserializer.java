/*
SPDX-License-Identifier: Apache-2.0
*/

package com.effective.hlf.ledgerapi;

@FunctionalInterface
public interface StateDeserializer {
    State deserialize(byte[] buffer);
}
