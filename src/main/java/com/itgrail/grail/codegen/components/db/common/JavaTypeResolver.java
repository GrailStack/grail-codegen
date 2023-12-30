package com.itgrail.grail.codegen.components.db.common;

import com.itgrail.grail.codegen.components.db.enums.JavaTypeEnum;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

public class JavaTypeResolver {

    /**
     * java 字段类型映射集合
     */
    private static Map<Integer, BiFunction<Integer, String, JavaTypeEnum>> typeMap = new HashMap<>();

    static {
        typeMap.put(Types.ARRAY, wrapToBiFunction(JavaTypeEnum.TYPE_OBJECT));

        typeMap.put(Types.BIGINT, new BiFunction<Integer, String, JavaTypeEnum>() {
            @Override
            public JavaTypeEnum apply(Integer dataType, String definedDataTypeName) {
                if (definedDataTypeName.toUpperCase().contains("UNSIGNED")) {
                    return JavaTypeEnum.TYPE_BIG_INTEGER;
                }
                return JavaTypeEnum.TYPE_LONG;
            }
        });
        typeMap.put(Types.INTEGER, new BiFunction<Integer, String, JavaTypeEnum>() {
            @Override
            public JavaTypeEnum apply(Integer dataType, String definedDataTypeName) {
                if (definedDataTypeName.toUpperCase().contains("UNSIGNED")) {
                    return JavaTypeEnum.TYPE_LONG;
                }
                return JavaTypeEnum.TYPE_INTEGER;
            }
        });

        typeMap.put(Types.SMALLINT, new BiFunction<Integer, String, JavaTypeEnum>() {
            @Override
            public JavaTypeEnum apply(Integer dataType, String definedDataTypeName) {
                if (definedDataTypeName.toUpperCase().contains("UNSIGNED")) {
                    return JavaTypeEnum.TYPE_INTEGER;
                }
                return JavaTypeEnum.TYPE_SHORT;
            }
        });

        typeMap.put(Types.TINYINT, new BiFunction<Integer, String, JavaTypeEnum>() {
            @Override
            public JavaTypeEnum apply(Integer dataType, String definedDataTypeName) {
                if (definedDataTypeName.toUpperCase().contains("UNSIGNED")) {
                    return JavaTypeEnum.TYPE_SHORT;
                }
                return JavaTypeEnum.TYPE_BYTE;
            }
        });

        typeMap.put(Types.BIT, new BiFunction<Integer, String, JavaTypeEnum>() {
            @Override
            public JavaTypeEnum apply(Integer dataType, String definedDataTypeName) {
                if (definedDataTypeName.toLowerCase().contains("tinyint")) {
                    return JavaTypeEnum.TYPE_BYTE;
                }
                return JavaTypeEnum.TYPE_BOOLEAN;
            }
        });

        typeMap.put(Types.BINARY, wrapToBiFunction(JavaTypeEnum.TYPE_BYTE_ARRAY));
        typeMap.put(Types.BLOB, wrapToBiFunction(JavaTypeEnum.TYPE_BYTE_ARRAY));
        typeMap.put(Types.BOOLEAN, wrapToBiFunction(JavaTypeEnum.TYPE_BOOLEAN));
        typeMap.put(Types.CHAR, wrapToBiFunction(JavaTypeEnum.TYPE_STRING));
        typeMap.put(Types.CLOB, wrapToBiFunction(JavaTypeEnum.TYPE_STRING));
        typeMap.put(Types.DATALINK, wrapToBiFunction(JavaTypeEnum.TYPE_STRING));
        typeMap.put(Types.DISTINCT, wrapToBiFunction(JavaTypeEnum.TYPE_OBJECT));
        typeMap.put(Types.DOUBLE, wrapToBiFunction(JavaTypeEnum.TYPE_DOUBLE));
        typeMap.put(Types.FLOAT, wrapToBiFunction(JavaTypeEnum.TYPE_DOUBLE));
        typeMap.put(Types.JAVA_OBJECT, wrapToBiFunction(JavaTypeEnum.TYPE_OBJECT));
        typeMap.put(Jdbc4Types.LONGNVARCHAR, wrapToBiFunction(JavaTypeEnum.TYPE_STRING));
        typeMap.put(Types.LONGVARBINARY, wrapToBiFunction(JavaTypeEnum.TYPE_BYTE_ARRAY));
        typeMap.put(Types.LONGVARCHAR, wrapToBiFunction(JavaTypeEnum.TYPE_STRING));
        typeMap.put(Jdbc4Types.NCHAR, wrapToBiFunction(JavaTypeEnum.TYPE_STRING));
        typeMap.put(Jdbc4Types.NCLOB, wrapToBiFunction(JavaTypeEnum.TYPE_STRING));
        typeMap.put(Jdbc4Types.NVARCHAR, wrapToBiFunction(JavaTypeEnum.TYPE_STRING));
        typeMap.put(Types.VARBINARY, wrapToBiFunction(JavaTypeEnum.TYPE_BYTE_ARRAY));
        typeMap.put(Types.VARCHAR, wrapToBiFunction(JavaTypeEnum.TYPE_STRING));
        typeMap.put(Types.NULL, wrapToBiFunction(JavaTypeEnum.TYPE_OBJECT));
        typeMap.put(Types.OTHER, wrapToBiFunction(JavaTypeEnum.TYPE_OBJECT));
        typeMap.put(Types.REAL, wrapToBiFunction(JavaTypeEnum.TYPE_FLOAT));
        typeMap.put(Types.REF, wrapToBiFunction(JavaTypeEnum.TYPE_OBJECT));
        typeMap.put(Types.STRUCT, wrapToBiFunction(JavaTypeEnum.TYPE_OBJECT));

        typeMap.put(Types.DATE, wrapToBiFunction(JavaTypeEnum.TYPE_DATE));
        typeMap.put(Types.TIME, wrapToBiFunction(JavaTypeEnum.TYPE_DATE));
        typeMap.put(Types.TIMESTAMP, wrapToBiFunction(JavaTypeEnum.TYPE_TIMESTAMP));

        typeMap.put(Types.DECIMAL, wrapToBiFunction(JavaTypeEnum.TYPE_BIG_DECIMAL));
        typeMap.put(Types.NUMERIC, wrapToBiFunction(JavaTypeEnum.TYPE_BIG_DECIMAL));
    }

    /**
     * 获取java数据类型
     *
     * @param jdbcType 数据库类型
     * @return java数据类型全路径
     */
    public static String getJavaType(int jdbcType, String definedDataTypeName) {
        return Optional.ofNullable(typeMap.get(jdbcType)).orElse(wrapToBiFunction(JavaTypeEnum.TYPE_STRING))
                .apply(jdbcType, definedDataTypeName).getShortName();
    }

    public static String getJavaFullType(int jdbcType, String definedDataTypeName) {
        return Optional.ofNullable(typeMap.get(jdbcType)).orElse(wrapToBiFunction(JavaTypeEnum.TYPE_STRING))
                .apply(jdbcType, definedDataTypeName).getFullName();
    }

    protected static BiFunction<Integer, String, JavaTypeEnum> wrapToBiFunction(JavaTypeEnum javaTypeEnum) {
        return new BiFunction<Integer, String, JavaTypeEnum>() {
            @Override
            public JavaTypeEnum apply(Integer dataType, String definedDataTypeName) {
                return javaTypeEnum;
            }
        };
    }

}