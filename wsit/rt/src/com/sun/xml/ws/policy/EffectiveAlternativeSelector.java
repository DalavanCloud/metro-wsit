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

package com.sun.xml.ws.policy;

import com.sun.xml.ws.policy.privateutil.PolicyLogger;
import com.sun.xml.ws.policy.privateutil.LocalizationMessages;
import com.sun.xml.ws.policy.spi.PolicyAssertionValidator.Fitness;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Contains static methods for policy alternative selection. Given policy map is changed so that
 * each effective policy contains at most one policy alternative. Uses domain
 * specific @see com.sun.xml.ws.policy.spi.PolicySelector
 * to find out whether particular policy assertion is actually supported.
 *
 * @author japod
 */
public class EffectiveAlternativeSelector {
    private enum AlternativeFitness {
        UNEVALUATED {
            AlternativeFitness combine(final Fitness assertionFitness) {
                switch (assertionFitness) {
                    case UNKNOWN:
                        return UNKNOWN;
                    case UNSUPPORTED:
                        return UNSUPPORTED;
                    case SUPPORTED:
                        return SUPPORTED;
                    case INVALID:
                        return INVALID;
                    default:
                        return UNEVALUATED;
                }
            }
        },
        INVALID {
            AlternativeFitness combine(final Fitness assertionFitness) {
                return INVALID;
            }
        },
        UNKNOWN {
            AlternativeFitness combine(final Fitness assertionFitness) {
                switch (assertionFitness) {
                    case UNKNOWN:
                        return UNKNOWN;
                    case UNSUPPORTED:
                        return UNSUPPORTED;
                    case SUPPORTED:
                        return PARTIALLY_SUPPORTED;
                    case INVALID:
                        return INVALID;
                    default:
                        return UNEVALUATED;
                }
            }
        },
        UNSUPPORTED {
            AlternativeFitness combine(final Fitness assertionFitness) {
                switch (assertionFitness) {
                    case UNKNOWN:
                    case UNSUPPORTED:
                        return UNSUPPORTED;
                    case SUPPORTED:
                        return PARTIALLY_SUPPORTED;
                    case INVALID:
                        return INVALID;
                    default:
                        return UNEVALUATED;
                }
            }
        },
        PARTIALLY_SUPPORTED {
            AlternativeFitness combine(final Fitness assertionFitness) {
                switch (assertionFitness) {
                    case UNKNOWN:
                    case UNSUPPORTED:
                    case SUPPORTED:
                        return PARTIALLY_SUPPORTED;
                    case INVALID:
                        return INVALID;
                    default:
                        return UNEVALUATED;
                }
            }
        },
        SUPPORTED_EMPTY {
            AlternativeFitness combine(final Fitness assertionFitness) {
                // will not localize - this exception may not occur if there is no programatic error in this class
                throw new UnsupportedOperationException("Combine operation was called unexpectedly on 'SUPPORTED_EMPTY' alternative fitness enumeration state.");
            }            
        },
        SUPPORTED {
            AlternativeFitness combine(final Fitness assertionFitness) {
                switch (assertionFitness) {
                    case UNKNOWN:
                    case UNSUPPORTED:
                        return PARTIALLY_SUPPORTED;
                    case SUPPORTED:
                        return SUPPORTED;
                    case INVALID:
                        return INVALID;
                    default:
                        return UNEVALUATED;
                }
            }
        };
        
        abstract AlternativeFitness combine(Fitness assertionFitness);
    }
    
    private static final PolicyLogger LOGGER = PolicyLogger.getLogger(EffectiveAlternativeSelector.class);
    
    /**
     *
     * Does the selection for policy map bound to given modifier using only the given validators
     *
     *
     * @param modifier @see EffectivePolicyModifier which the map is bound to
     * @param validators to be used
     */
    public static final void doSelection(final EffectivePolicyModifier modifier) throws PolicyException {
        final PolicyMap map = modifier.getMap();
        AssertionValidationProcessor validationProcessor = AssertionValidationProcessor.getInstance();
        
        for (PolicyMapKey mapKey : map.getAllServiceScopeKeys()) {
            Policy oldPolicy = map.getServiceEffectivePolicy(mapKey);
            modifier.setNewEffectivePolicyForServiceScope(mapKey, selectBestAlternative(oldPolicy, validationProcessor));
        }
        for (PolicyMapKey mapKey : map.getAllEndpointScopeKeys()) {
            Policy oldPolicy = map.getEndpointEffectivePolicy(mapKey);
            modifier.setNewEffectivePolicyForEndpointScope(mapKey, selectBestAlternative(oldPolicy, validationProcessor));
        }
        for (PolicyMapKey mapKey : map.getAllOperationScopeKeys()) {
            Policy oldPolicy = map.getOperationEffectivePolicy(mapKey);
            modifier.setNewEffectivePolicyForOperationScope(mapKey, selectBestAlternative(oldPolicy, validationProcessor));
        }
        for (PolicyMapKey mapKey : map.getAllInputMessageScopeKeys()) {
            Policy oldPolicy = map.getInputMessageEffectivePolicy(mapKey);
            modifier.setNewEffectivePolicyForInputMessageScope(mapKey, selectBestAlternative(oldPolicy, validationProcessor));
        }
        for (PolicyMapKey mapKey : map.getAllOutputMessageScopeKeys()) {
            Policy oldPolicy = map.getOutputMessageEffectivePolicy(mapKey);
            modifier.setNewEffectivePolicyForOutputMessageScope(mapKey, selectBestAlternative(oldPolicy, validationProcessor));
        }
        for (PolicyMapKey mapKey : map.getAllFaultMessageScopeKeys()) {
            Policy oldPolicy = map.getFaultMessageEffectivePolicy(mapKey);
            modifier.setNewEffectivePolicyForFaultMessageScope(mapKey, selectBestAlternative(oldPolicy, validationProcessor));
        }
    }
    
    private static Policy selectBestAlternative(final Policy policy, final AssertionValidationProcessor validationProcessor) throws PolicyException {
        AssertionSet bestAlternative = null;
        AlternativeFitness bestAlternativeFitness = AlternativeFitness.UNEVALUATED;
        for (AssertionSet alternative : policy) {           
            AlternativeFitness alternativeFitness = (alternative.isEmpty()) ? AlternativeFitness.SUPPORTED_EMPTY : AlternativeFitness.UNEVALUATED;
            for ( PolicyAssertion assertion : alternative ) {
                
                final Fitness assertionFitness = validationProcessor.validateClientSide(assertion);
                switch(assertionFitness) {
                    case UNKNOWN:
                    case UNSUPPORTED:
                    case INVALID:
                        LOGGER.warning(LocalizationMessages.WSP_0075_PROBLEMATIC_ASSERTION_STATE(assertion.getName(), assertionFitness));
                        break;
                }
                
                alternativeFitness = alternativeFitness.combine(assertionFitness);
            }
            
            if (bestAlternativeFitness.compareTo(alternativeFitness) < 0) {
                // better alternative found
                bestAlternative = alternative;
                bestAlternativeFitness = alternativeFitness;
            }
            
            if (bestAlternativeFitness == AlternativeFitness.SUPPORTED) {
                // all assertions supported by at least one selector
                break;
            }
        }
        
        switch (bestAlternativeFitness) {
            case INVALID:
                throw LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0053_INVALID_CLIENT_SIDE_ALTERNATIVE()));
            case UNKNOWN:
            case UNSUPPORTED:
            case PARTIALLY_SUPPORTED:
                LOGGER.warning(LocalizationMessages.WSP_0019_SUBOPTIMAL_ALTERNATIVE_SELECTED(bestAlternativeFitness));
        }
        
        Collection<AssertionSet> alternativeSet = null;
        if (bestAlternative != null) {
            // return a policy containing just the picked alternative
            alternativeSet = new LinkedList<AssertionSet>();
            alternativeSet.add(bestAlternative);
        }
        return Policy.createPolicy(policy.getName(), policy.getId(), alternativeSet);
    }
}
