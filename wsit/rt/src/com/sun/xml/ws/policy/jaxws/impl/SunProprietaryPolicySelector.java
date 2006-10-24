/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License).  You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the license at
 * https://glassfish.dev.java.net/public/CDDLv1.0.html.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at https://glassfish.dev.java.net/public/CDDLv1.0.html.
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * you own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2006 Sun Microsystems Inc. All Rights Reserved
 */

package com.sun.xml.ws.policy.jaxws.impl;

import com.sun.xml.ws.policy.jaxws.encoding.FastInfosetModelConfiguratorProvider;
import com.sun.xml.ws.policy.spi.PolicySelector;
import java.util.ArrayList;
import javax.xml.namespace.QName;

/**
 * Implements SPI for selecting wsit related SUN's proprietary assertions.
 *
 * @author japod
 */
public class SunProprietaryPolicySelector extends PolicySelector{
    
    private static final ArrayList<QName> supportedAssertions = new ArrayList<QName>();
    
    static {
        supportedAssertions.add(new QName(
                "http://java.sun.com/xml/ns/wsit/2006/09/policy/optimizedfastinfosetserialization",
                "OptimizedFastInfosetSerialization"));
        supportedAssertions.add(new QName(
                "http://java.sun.com/xml/ns/wsit/2006/09/policy/automaticallyselectfastinfoset",
                "AutomaticallySelectFastInfoset"));
        supportedAssertions.add(new QName(
                "http://java.sun.com/xml/ns/wsit/2006/09/policy/soaptcp/service",
                "OptimizedTCPTransport"));
        supportedAssertions.add(new QName(
                "http://java.sun.com/xml/ns/wsit/2006/09/policy/transport/client",
                "AutomaticallySelectOptimalTransport"));
        supportedAssertions.add(new QName(
                "http://sun.com/2006/03/rm",
                "Ordered"));
        supportedAssertions.add(new QName(
                "http://sun.com/2006/03/rm",
                "AllowDuplicates"));
        supportedAssertions.add(new QName(
                "http://sun.com/2006/03/rm/client",
                "AckRequestInterval"));
        supportedAssertions.add(new QName(
                "http://sun.com/2006/03/rm/client",
                "ResendInterval"));
        supportedAssertions.add(new QName(
                "http://schemas.sun.com/2006/03/wss/server",
                "CertStore"));
        supportedAssertions.add(new QName(
                "http://schemas.sun.com/2006/03/wss/server",
                "KeyStore"));
        supportedAssertions.add(new QName(
                "http://schemas.sun.com/2006/03/wss/server",
                "TrustStore"));
        supportedAssertions.add(new QName(
                "http://schemas.sun.com/2006/03/wss/server",
                "CallbackHandlerConfiguration"));
        supportedAssertions.add(new QName(
                "http://schemas.sun.com/2006/03/wss/server",
                "CallbackHandler"));
        supportedAssertions.add(new QName(
                "http://schemas.sun.com/2006/03/wss/server",
                "ValidatorConfiguration"));
        supportedAssertions.add(new QName(
                "http://schemas.sun.com/2006/03/wss/server",
                "Validator"));
        supportedAssertions.add(new QName(
                "http://schemas.sun.com/2006/03/wss/server",
                "Timestamp"));
        supportedAssertions.add(new QName(
                "http://schemas.sun.com/2006/03/wss/client",
                "CertStore"));
        supportedAssertions.add(new QName(
                "http://schemas.sun.com/2006/03/wss/client",
                "KeyStore"));
        supportedAssertions.add(new QName(
                "http://schemas.sun.com/2006/03/wss/client",
                "TrustStore"));
        supportedAssertions.add(new QName(
                "http://schemas.sun.com/2006/03/wss/client",
                "CallbackHandlerConfiguration"));
        supportedAssertions.add(new QName(
                "http://schemas.sun.com/2006/03/wss/client",
                "CallbackHandler"));
        supportedAssertions.add(new QName(
                "http://schemas.sun.com/2006/03/wss/client",
                "ValidatorConfiguration"));
        supportedAssertions.add(new QName(
                "http://schemas.sun.com/2006/03/wss/client",
                "Validator"));
        supportedAssertions.add(new QName(
                "http://schemas.sun.com/2006/03/wss/client",
                "Timestamp"));
        supportedAssertions.add(new QName(
                "http://schemas.sun.com/ws/2006/05/sc/server",
                "SCConfiguration"));
        supportedAssertions.add(new QName(
                "http://schemas.sun.com/ws/2006/05/sc/server",
                "Lifetime"));
        supportedAssertions.add(new QName(
                "http://schemas.sun.com/ws/2006/05/sc/client",
                "SCClientConfiguration"));
        supportedAssertions.add(new QName(
                "http://schemas.sun.com/ws/2006/05/sc/client",
                "LifeTime"));
        supportedAssertions.add(new QName(
                "http://schemas.sun.com/ws/2006/05/trust/server",
                "STSConfiguration"));
        supportedAssertions.add(new QName(
                "http://schemas.sun.com/ws/2006/05/trust/server",
                "Contract"));
        supportedAssertions.add(new QName(
                "http://schemas.sun.com/ws/2006/05/trust/server",
                "ServiceProviders"));
        supportedAssertions.add(new QName(
                "http://schemas.sun.com/ws/2006/05/trust/server",
                "ServiceProvider"));
        supportedAssertions.add(new QName(
                "http://schemas.sun.com/ws/2006/05/trust/server",
                "CertAlias"));
        supportedAssertions.add(new QName(
                "http://schemas.sun.com/ws/2006/05/trust/server",
                "TokenType"));
        supportedAssertions.add(new QName(
                "http://schemas.sun.com/ws/2006/05/trust/server",
                "KeyType"));
        supportedAssertions.add(new QName(
                "http://schemas.sun.com/ws/2006/05/trust/server",
                "Issuer"));
        supportedAssertions.add(new QName(
                "http://schemas.sun.com/ws/2006/05/trust/server",
                "LifeTime"));
        supportedAssertions.add(new QName(
                "http://schemas.sun.com/ws/2006/05/trust/client",
                "PreconfiguredSTS"));
        
    }
    
    /** Creates a new instance of SunProprietaryPolicySelector */
    public SunProprietaryPolicySelector() {
        super(supportedAssertions);
    }
    
}
