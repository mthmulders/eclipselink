/*
 * Copyright (c) 1998, 2022 Oracle and/or its affiliates. All rights reserved.
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
package org.eclipse.persistence.testing.oxm.xmlconversionmanager;

import org.eclipse.persistence.exceptions.ConversionException;
import org.eclipse.persistence.internal.helper.ClassConstants;
import org.eclipse.persistence.internal.oxm.XMLConversionManager;
import org.eclipse.persistence.oxm.XMLConstants;
import org.eclipse.persistence.testing.oxm.OXTestCase;

public class Base64TestCases extends OXTestCase {
    // XML Conversion Manager
    private XMLConversionManager xcm;

    public Base64TestCases(String name) {
        super(name);
    }

    @Override
    public void setUp() {
        xcm = XMLConversionManager.getDefaultXMLManager();
    }

    public void testIntegerToString_base64() {
        try {
            Integer integer = 1;
            xcm.convertObject(integer, ClassConstants.ABYTE, XMLConstants.BASE_64_BINARY_QNAME);
        } catch (ConversionException e) {
            assertTrue("The incorrect exception was thrown", e.getErrorCode() == ConversionException.COULD_NOT_BE_CONVERTED);
        }
    }

    public void testBase64WithNewLines() {
        try {
            String base64 = "PD94bWwgdmVyc2lvbj0iMS4wIj8+PGZhbGw+PG5hbWU+TmlhZ2FyYSBGYWxsczwvbmFtZT48L2Zh\r\n" +
            "bGw+";
            xcm.convertObject(base64, ClassConstants.ABYTE, XMLConstants.BASE_64_BINARY_QNAME);
        } catch(Exception ex) {
            fail("an unexpected exception was thrown " + ex.getMessage());
        }
    }
    
    public void testBase64EncodeToString() {
        // 2 MB
        byte[] big = new byte[2000000];
        // Initialize it with some data
        for (int i = 0; i < big.length; i++) {
            big[i] = (byte) i;
        }
        String base64 = xcm.buildBase64StringFromBytes(big);
        // Verifies the implementation is not changing the expected result.
        // Currently this check is unnecessary because implementation is the same.
        assertEquals(java.util.Base64.getEncoder().encodeToString(big), base64);
    }
}
