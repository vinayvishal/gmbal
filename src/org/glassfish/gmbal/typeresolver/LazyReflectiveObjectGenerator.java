/*
 * Copyright 2003 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */

package org.glassfish.gmbal.typeresolver;

import sun.reflect.generics.factory.GenericsFactory;
import sun.reflect.generics.visitor.Reifier;


/**
 * Common infrastructure for things that lazily generate reflective generics
 * objects.
 * <p> In all these cases, one needs produce a visitor that will, on demand,
 * traverse the stored AST(s) and reify them into reflective objects.
 * The visitor needs to be initialized with a factory, which will be
 * provided when the instance is initialized.
 * The factory should be cached.
 *
*/
public abstract class LazyReflectiveObjectGenerator {
    private GenericsFactory factory; // cached factory

    protected LazyReflectiveObjectGenerator(GenericsFactory f) {
        factory = f;
    }

    // accessor for factory
    private GenericsFactory getFactory() {
        return factory;
    }

    // produce a reifying visitor (could this be typed as a TypeTreeVisitor?
    protected Reifier getReifier(){return Reifier.make(getFactory());}

}