/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2007-2007 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License. You can obtain
 * a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 * or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 * Sun designates this particular file as subject to the "Classpath" exception
 * as provided by Sun in the GPL Version 2 section of the License file that
 * accompanied this code.  If applicable, add the following below the License
 * Header, with the fields enclosed by brackets [] replaced by your own
 * identifying information: "Portions Copyrighted [year]
 * [name of copyright owner]"
 *
 * Contributor(s):
 *
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package org.glassfish.gmbal;

import java.util.Map;
import javax.management.Descriptor;


/** Base interface supported by all AMX MBeans.  All MBeans generated by
 * gmbal comply with this interface, which means that the attributes and
 * operations defined in this Java interface all appear in each
 * MBean generated by calling ManagedObjectManager.register.
 *
 * @author LLoyd Chambers
 * @author Ken Cavanaugh
 *
 */
@ManagedObject
@Description( "Base interface for any MBean that works in the AMX framework" )
public interface AMX {
    /** the JMX domain for all AMX MBeans */
    public static final String DOMAIN = "amx";
    /** the ObjectName name property key */
    public static final String NAME_PROP_KEY = "name";
    /** the ObjectName type property key */
    public static final String TYPE_PROP_KEY = "type";

    /** Usually the same as the ObjectName 'name' property, but can differ
        if the actual name contains characters that must be escaped for an ObjectName and/or
        if the MBean has a mutable name attribute.
       The type property can be obtained from the ObjectName */
    @ManagedAttribute
    @Description( "Return the name of this MBean.")
    public String getName();

    /** boolean indicating that the MBean type can have only 1 instance */
    public static final String META_SINGLETON = "Singleton";

    /** boolean indicating that the MBean contains other MBeans.  See getContained() */
    public static final String META_CONTAINER = "Container";

    /** boolean indicating that MBeanInfo is immutable */
    public static final String META_INVARIANT_MBEANINFO = "InvariantMBeanInfo";

    /** Metadata used to override the type of the AMX MBean.
     * This provides the value used in the ObjectName for the TYPE_PROP_KEY.
     */
    public static final String META_TYPE = "Type" ;

    /** The name part to be used for this item's "path".
        By convention this is the MBean's type, but could be something else,
        such as its XML element type (for config MBeans). */
    public static final String META_PATH_PART = "PathPart";

    /** Get all metadata about this MBean.  See {@link #META_SINGLETON} et al.
     * @return The descriptor, which will be a ModelMBeanInfoSupport instance.
     */
    // This is not mapped to an attribute, because it is always available on a
    // dynamic MBean simply by calling getMBeanInfo().
    public Map<String,?> getMeta();

    /** "go up one level": the MBean containing this one, can be null for root
     * @return The container of this MBean (null if already at root).
     */
    @ManagedAttribute
    @Description( "The container that contains this MBean" )
    public AMX getContainer();

    /** Containment hierarchy:
        Get all AMX contained by this one, in no particular order.
        Valid only if isContainer().
     * Note that using an array sidesteps Map/Set/OpenType issues
     * @return All children of this AMX MBean.
     */
    @ManagedAttribute
    @Description( "All children of this AMX MBean")
    public AMX[] getContained();

    /** get all AMX of the specified types, in no particular order
     * @param type The desired type of children.
     * @return An array of children of the given type.
     */
    @ManagedOperation
    @ParameterNames( "type" )
    @Description( "get all AMX MBeans of the specified type that are children "
        + "this AMX MBean")
    public AMX[] getContained(final String type);
}
