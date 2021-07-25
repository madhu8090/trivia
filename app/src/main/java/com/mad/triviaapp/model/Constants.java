package com.mad.triviaapp.model;

import org.jetbrains.annotations.NotNull;

public class Constants {

    public enum QType {
        SINGLE("Single"), MULTIPLE("Multiple");

        String value;

        QType(String value) {
            this.value = value;
        }

        public static QType fromString(String value) {
            for (QType qType : values()) {
                if (qType.value.equals(value)) {
                    return qType;
                }
            }
            return null;
        }

        @NotNull
        @Override
        public String toString() {
            return value;
        }
    }
}
