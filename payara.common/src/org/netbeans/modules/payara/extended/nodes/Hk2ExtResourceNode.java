/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// Portions Copyright [2017] [Payara Foundation and/or its affiliates]

package org.netbeans.modules.payara.extended.nodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.Action;
import org.netbeans.modules.payara.common.nodes.Hk2ResourceNode;
import org.netbeans.modules.payara.common.ui.ConnectionPoolCustomizer;
import org.netbeans.modules.payara.extended.nodes.actions.ConnectionPoolAdvancedAttributesAction;
import org.netbeans.modules.payara.spi.ResourceDecorator;
import org.netbeans.modules.payara.spi.ResourceDesc;
import org.openide.util.Lookup;
import org.openide.util.actions.SystemAction;

/**
 *
 * @author jGauravGupta
 */
public class Hk2ExtResourceNode extends Hk2ResourceNode {

    private final Class customizer;

    public Hk2ExtResourceNode(Lookup lookup, ResourceDesc resource, ResourceDecorator decorator, Class customizer) {
        super(lookup, resource, decorator, customizer);
        this.customizer = customizer;
        if (customizer == ConnectionPoolCustomizer.class) {
            // add the ConnectionPoolAdvancedAttributes cookie
            getCookieSet().add(new Hk2ExtCookie.ConnectionPoolAdvancedAttributes(
                    lookup, getDisplayName(),
                    resource.getCommandType(), customizer));
        }
    }

    @Override
    public Action[] getActions(boolean context) {
        List<Action> actions = new ArrayList<>(Arrays.asList(super.getActions(context)));
        if (customizer == ConnectionPoolCustomizer.class) {
            actions.add(SystemAction.get(ConnectionPoolAdvancedAttributesAction.class));
        }
        return actions.toArray(new Action[actions.size()]);
    }

}
