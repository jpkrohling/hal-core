/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package org.jboss.as.console.client.shared.subsys.jca.wizard;

import java.util.List;

import org.jboss.as.console.client.Console;
import org.jboss.as.console.client.shared.subsys.jca.model.DataSource;
import org.jboss.as.console.client.widgets.forms.items.JndiNameItem;

/**
* @author Harald Pehl
*/
class DataSourceJndiItem<T extends DataSource> extends JndiNameItem {

    private final List<T> existingDataSources;
    private final String defaultErrMessage;

    public DataSourceJndiItem(final List<T> existingDataSources) {
        super("jndiName", "JNDI Name");
        this.existingDataSources = existingDataSources;
        this.defaultErrMessage = getErrMessage();
    }

    @Override
    public boolean validate(final String value) {
        boolean duplicateJndiName = false;
        boolean parentValid = super.validate(value);
        if (parentValid) {
            for (T dataSource : existingDataSources) {
                if (dataSource.getJndiName().equals(value)) {
                    duplicateJndiName = true;
                    setErrMessage(Console.CONSTANTS.duplicate_data_source_jndi());
                    break;
                }
            }
        } else {
            setErrMessage(defaultErrMessage);
        }
        return parentValid && !duplicateJndiName;
    }
}
