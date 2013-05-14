package com.playtech.cm.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

/**
 User: Denis Veselovskiy
 * Date: 07.02.12 19:41
 */
abstract public class ConfigLoader {
    public <T,P> void load(T object, P properties) {
        Class clazz = object.getClass();
        T dest = object;

        if (object instanceof Class) {
            clazz = (Class) object;
            dest = null;
        }

        for (Field field : clazz.getDeclaredFields()) {
            for (Annotation annotation : field.getDeclaredAnnotations()) {
                if (annotation instanceof ConfigParam) {
                    String configField = ((ConfigParam) annotation).value();
                    String configSplitBy = ((ConfigParam) annotation).splitBy();
                    String configValue = getConfigValue(properties, configField);
                    if (configValue == null) continue;
                    try {
                        field.setAccessible(true);
                        if (field.getType().isPrimitive()) setPrimitiveValue(field, dest, configValue);
                        else if (field.getType().isArray()) setArray(field, dest, configValue, configSplitBy);
                        else setObject(field, dest, configValue);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private <T> void setPrimitiveValue(Field field, T dest, String value) throws IllegalAccessException {
        String fieldType = field.getType().getSimpleName();
        if (fieldType.equals("int")) field.setInt(dest, Integer.parseInt(value));
        else if (fieldType.equals("boolean")) field.setBoolean(dest, Boolean.parseBoolean(value));
        else if (fieldType.equals("byte")) field.setByte(dest, Byte.parseByte(value));
        else if (fieldType.equals("char")) field.setChar(dest, value.charAt(0));
        else if (fieldType.equals("double")) field.setDouble(dest, Double.parseDouble(value));
        else if (fieldType.equals("float")) field.setFloat(dest, Float.parseFloat(value));
        else if (fieldType.equals("long")) field.setLong(dest, Long.parseLong(value));
        else if (fieldType.equals("short")) field.setShort(dest, Short.parseShort(value));
        else throw new IllegalStateException();
    }

    private <T> void setArray(Field field, T dest, String str, String splitBy) throws Exception {
        String[] values = str.split(splitBy);
        Object arr = Array.newInstance(field.getType().getComponentType(), values.length);
        String arrayType = field.getType().getCanonicalName().replace("[", "").replace("]", "");
        for (int i = 0; i < values.length; i++) {
            if (arrayType.equals("int")) Array.setInt(arr, i, Integer.parseInt(values[i]));
            else if (arrayType.equals("boolean")) Array.setBoolean(arr, i, Boolean.parseBoolean(values[i]));
            else if (arrayType.equals("byte")) Array.setByte(arr, i, Byte.parseByte(values[i]));
            else if (arrayType.equals("char")) Array.setChar(arr, i, values[i].charAt(0));
            else if (arrayType.equals("double")) Array.setDouble(arr, i, Double.parseDouble(values[i]));
            else if (arrayType.equals("float")) Array.setFloat(arr, i, Float.parseFloat(values[i]));
            else if (arrayType.equals("long")) Array.setLong(arr, i, Long.parseLong(values[i]));
            else if (arrayType.equals("short")) Array.setShort(arr, i, Short.parseShort(values[i]));
            else Array.set(arr, i, Class.forName(arrayType).getConstructor(String.class).newInstance(values[i]));
        }
        field.set(dest, arr);
    }

    private <T> void setObject(Field field, T dest, String value) throws Exception {
        field.set(dest, Class.forName(field.getType().getName()).getConstructor(String.class).newInstance(value));
    }

    abstract public <T> String getConfigValue(T source, String configField);
}
