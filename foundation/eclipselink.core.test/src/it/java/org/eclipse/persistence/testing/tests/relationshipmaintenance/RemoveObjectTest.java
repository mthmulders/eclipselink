/*
 * Copyright (c) 1998, 2021 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */

// Contributors:
//     Oracle - initial API and implementation from Oracle TopLink
package org.eclipse.persistence.testing.tests.relationshipmaintenance;

import java.util.Iterator;
import org.eclipse.persistence.sessions.*;
import org.eclipse.persistence.testing.models.relationshipmaintenance.FieldOffice;
import org.eclipse.persistence.testing.models.relationshipmaintenance.SalesPerson;

public class RemoveObjectTest extends org.eclipse.persistence.testing.framework.AutoVerifyTestCase {
    public FieldOffice fieldOfficeClone;
    public SalesPerson sales = null;

    @Override
    public void setup() {
        getSession().getIdentityMapAccessor().initializeAllIdentityMaps();
        beginTransaction();
    }

    @Override
    public void reset() {
        rollbackTransaction();
        getSession().getIdentityMapAccessor().initializeAllIdentityMaps();
    }

    @Override
    public void test() {
        UnitOfWork uow = getSession().acquireUnitOfWork();
        this.fieldOfficeClone = (FieldOffice)uow.readObject(FieldOffice.class);

        for (Iterator objects = this.fieldOfficeClone.getSalespeople().iterator();
                 objects.hasNext();) {
            this.sales = (SalesPerson)objects.next();
            break;
        }
        this.fieldOfficeClone.getSalespeople().remove(this.sales);
    }

    @Override
    public void verify() {
        if ((this.fieldOfficeClone.getSalespeople().contains(this.sales)) || (this.sales.getFieldOffice() != null)) {
            throw new org.eclipse.persistence.testing.framework.TestErrorException("Failed to set the backPointer information");
        }
    }
}
