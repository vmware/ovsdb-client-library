/*
 * Copyright (c) 2018 VMware, Inc. All Rights Reserved.
 *
 * This product is licensed to you under the BSD-2 license (the "License").
 * You may not use this product except in compliance with the BSD-2 License.
 *
 * This product may include a number of subcomponents with separate copyright
 * notices and license terms. Your use of these subcomponents is subject to the
 * terms and conditions of the subcomponent's license, as noted in the LICENSE
 * file.
 *
 * SPDX-License-Identifier: BSD-2-Clause
 */

package com.vmware.ovsdb.protocol.methods;

import static org.junit.Assert.assertEquals;


import com.google.common.collect.ImmutableMap;
import com.google.common.testing.EqualsTester;
import com.vmware.ovsdb.jsonrpc.v1.util.JsonUtil;
import com.vmware.ovsdb.protocol.methods.RowUpdate;
import java.io.IOException;
import java.util.Map;

import com.vmware.ovsdb.protocol.operation.notation.Atom;
import com.vmware.ovsdb.protocol.operation.notation.Row;
import com.vmware.ovsdb.protocol.operation.notation.Value;
import org.junit.Test;

public class RowUpdateTest {

  @Test
  public void testDeserialization() throws IOException {
    RowUpdate expectedResult = new RowUpdate();
    // Only new row
    Map<String, Value> newColumns = ImmutableMap.of(
        "name", Atom.string("ls1"),
        "description", Atom.string("First logical switch"),
        "tunnel_key", Atom.integer(5001)
    );
    Row newRow = new Row(newColumns);
    expectedResult.setOld(null);
    expectedResult.setNew(newRow);

    String jsonNewRow = JsonUtil.serialize(newRow);
    String textRowUpdate = "{\"new\":" + jsonNewRow + "}";

    assertEquals(
        expectedResult,
        JsonUtil.deserialize(textRowUpdate, RowUpdate.class)
    );

    // Only old row
    Map<String, Value> oldColumns = ImmutableMap.of(
        "name", Atom.string("ls2"),
        "description", Atom.string("Second logical switch"),
        "tunnel_key", Atom.integer(5002)
    );
    Row oldRow = new Row(oldColumns);
    expectedResult.setOld(oldRow);
    expectedResult.setNew(null);

    String jsonOldRow = JsonUtil.serialize(oldRow);
    textRowUpdate = "{\"old\":" + jsonOldRow + "}";

    assertEquals(
        expectedResult,
        JsonUtil.deserialize(textRowUpdate, RowUpdate.class)
    );

    // Both old and new
    expectedResult.setOld(oldRow);
    expectedResult.setNew(newRow);

    textRowUpdate = "{\"old\":" + jsonOldRow + ","
        + "\"new\":" + jsonNewRow + "}";

    assertEquals(
        expectedResult,
        JsonUtil.deserialize(textRowUpdate, RowUpdate.class)
    );

  }

  @Test
  public void testEquals() {
    Row oldRow1 = new Row().stringColumn("name", "old_name");
    Row oldRow2 = new Row(ImmutableMap.of("name", Atom.string("old_name")));

    Row newRow1 = new Row().stringColumn("name", "new_name");
    Row newRow2 = new Row(ImmutableMap.of("name", Atom.string("new_name")));

    new EqualsTester()
        .addEqualityGroup(new RowUpdate(), new RowUpdate(null, null))
        .addEqualityGroup(new RowUpdate(oldRow1, null), new RowUpdate().setOld(oldRow2))
        .addEqualityGroup(new RowUpdate(null, newRow1), new RowUpdate().setNew(newRow2))
        .testEquals();

  }
}
