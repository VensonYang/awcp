package org.szcloud.framework.metadesigner.core.generator.util.paranamer;

import java.lang.reflect.AccessibleObject;
import java.util.WeakHashMap;

public class CachingParanamer implements Paranamer {

    public static final String __PARANAMER_DATA = "v1.0 \n"
        + "com.thoughtworks.paranamer.CachingParanamer <init> com.thoughtworks.paranamer.Paranamer delegate \n"
        + "com.thoughtworks.paranamer.CachingParanamer lookupParameterNames java.lang.AccessibleObject methodOrConstructor \n"
        + "com.thoughtworks.paranamer.CachingParanamer lookupParameterNames java.lang.AccessibleObject, boolean methodOrCtor,throwExceptionIfMissing \n";

    private final Paranamer delegate;
    private final WeakHashMap<AccessibleObject,String[]> methodCache = new WeakHashMap<AccessibleObject,String[]>();

    /**
     * Uses a DefaultParanamer as the implementation it delegates to.
     */
    public CachingParanamer() {
        this(new DefaultParanamer());
    }

    /**
     * Specify a Paranamer instance to delegates to.
     * @param delegate the paranamer instance to use
     */
    public CachingParanamer(Paranamer delegate) {
        this.delegate = delegate;
    }

    public String[] lookupParameterNames(AccessibleObject methodOrConstructor) {
        return lookupParameterNames(methodOrConstructor, true);
    }

    public String[] lookupParameterNames(AccessibleObject methodOrCtor, boolean throwExceptionIfMissing) {
        if(methodCache.containsKey(methodOrCtor)) {
            return methodCache.get(methodOrCtor);
        }

        String[] names = delegate.lookupParameterNames(methodOrCtor, throwExceptionIfMissing);
        methodCache.put(methodOrCtor, names);

        return names;
    }

}
