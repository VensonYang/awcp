package org.szcloud.framework.metadesigner.core.generator.util.paranamer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.AccessibleObject;

public class DefaultParanamer implements Paranamer {

    private static final String COMMA = ",";
    private static final String SPACE = " ";

    public static final String __PARANAMER_DATA = "v1.0 \n"
        + "lookupParameterNames java.lang.AccessibleObject methodOrConstructor \n"
        + "lookupParameterNames java.lang.AccessibleObject,boolean methodOrCtor,throwExceptionIfMissing \n"
        + "getParameterTypeName java.lang.Class cls\n";

    public DefaultParanamer() {
    }

    public String[] lookupParameterNames(AccessibleObject methodOrConstructor) {
        return lookupParameterNames(methodOrConstructor, true);
    }

    public String[] lookupParameterNames(AccessibleObject methodOrCtor, boolean throwExceptionIfMissing) {
        // Oh for some commonality between Constructor and Method !!
        Class<?>[] types = null;
        Class<?> declaringClass = null;
        String name = null;
        if (methodOrCtor instanceof Method) {
            Method method = (Method) methodOrCtor;
            types = method.getParameterTypes();
            name = method.getName();
            declaringClass = method.getDeclaringClass();
        } else {
            Constructor<?> constructor = (Constructor<?>) methodOrCtor;
            types = constructor.getParameterTypes();
            declaringClass = constructor.getDeclaringClass();
            name = "<init>";
        }

        if (types.length == 0) {
            return EMPTY_NAMES;
        }
        final String parameterTypeNames = getParameterTypeNamesCSV(types);
        final String[] names = getParameterNames(declaringClass, parameterTypeNames, name + SPACE);
        if ( names == null ){
            if (throwExceptionIfMissing) {
            throw new ParameterNamesNotFoundException("No parameter names found for class '"+declaringClass+"', methodOrCtor " + name
                    +" and parameter types "+parameterTypeNames);
            } else {
                return Paranamer.EMPTY_NAMES;
            }
        }
        return names;
    }

    private static String[] getParameterNames(Class<?> declaringClass, String parameterTypes, String prefix) {
        String data = getParameterListResource(declaringClass);
        String line = findFirstMatchingLine(data, prefix + parameterTypes + SPACE);
        String[] parts = line.split(SPACE);
        // assumes line structure: constructorName parameterTypes parameterNames
        if (parts.length == 3 && parts[1].equals(parameterTypes)) {
            String parameterNames = parts[2];
            return parameterNames.split(COMMA);
        }
        return Paranamer.EMPTY_NAMES;
    }

    static String getParameterTypeNamesCSV(Class<?>[] parameterTypes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < parameterTypes.length; i++) {
            sb.append(getParameterTypeName(parameterTypes[i]));
            if (i < parameterTypes.length - 1) {
                sb.append(COMMA);
            }
        }
        return sb.toString();
    }

    private static String getParameterListResource(Class<?> declaringClass) {
        try {
            Field field = declaringClass.getDeclaredField("__PARANAMER_DATA");
            if(!Modifier.isStatic(field.getModifiers()) || !field.getType().equals(String.class)) {
                return null;
            }
            return (String) field.get(null);
        } catch (NoSuchFieldException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    /**
     * Filter the mappings and only return lines matching the prefix passed in.
     * @param data the data encoding the mappings
     * @param prefix the String prefix
     * @return A list of lines that match the prefix
     */
    private static String findFirstMatchingLine(String data, String prefix) {
        if (data == null) {
            return "";
        }
        int ix = data.indexOf(prefix);
        if (ix >= 0) {
            int iy = data.indexOf("\n", ix);
            if(iy >0) {
                return data.substring(ix,iy);
            }
        }
        return "";
    }


    private static String getParameterTypeName(Class<?> cls){
        String parameterTypeNameName = cls.getName();
        int arrayNestingDepth = 0;
        int ix = parameterTypeNameName.indexOf("[");
        while (ix>-1){
            arrayNestingDepth++;
            parameterTypeNameName=parameterTypeNameName.replaceFirst("(\\[\\w)|(\\[)","");
            ix = parameterTypeNameName.indexOf("[");
        }
        parameterTypeNameName =parameterTypeNameName.replaceFirst(";","");
        for (int k=0;k<arrayNestingDepth;k++){
            parameterTypeNameName = parameterTypeNameName+"[]";
        }
        return    parameterTypeNameName;

    }
}
