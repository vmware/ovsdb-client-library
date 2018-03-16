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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vmware.ovsdb.protocol.methods.deserializer.TableUpdateDeserializer;
import java.util.Map;
import java.util.UUID;

@JsonDeserialize(using = TableUpdateDeserializer.class)
public class TableUpdate {

    private final Map<UUID, RowUpdate> rowUpdates;

    public TableUpdate(Map<UUID, RowUpdate> rowUpdates) {
        this.rowUpdates = rowUpdates;
    }

    public Map<UUID, RowUpdate> getRowUpdates() {
        return rowUpdates;
    }

    @Override
    public int hashCode() {
        return rowUpdates != null
            ? rowUpdates.hashCode()
            : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TableUpdate)) {
            return false;
        }

        TableUpdate that = (TableUpdate) o;

        return rowUpdates != null
            ? rowUpdates.equals(that.rowUpdates)
            : that.rowUpdates == null;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " ["
            + "rowUpdates=" + rowUpdates
            + "]";
    }
}