package edu.gvsu.CIS163.Fall_2015.Andrew_Sully.BankingProgram;

import java.util.Objects;

/**
 * Created by Andrew on 11/2/15.
 */
public class HeaderName {
    private String namespace;
    private String fieldName;

    public HeaderName(Object parent, String fieldName){
        this(parent.getClass(), fieldName);
    }

    public HeaderName(Class parent, String fieldName){
        namespace = parent.getCanonicalName();
        this.fieldName = fieldName;
    }

    public String getFullName(){
        return namespace + "." + fieldName;
    }

    public String getFieldName(){
        return fieldName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HeaderName that = (HeaderName) o;

        if (!namespace.equals(that.namespace)) return false;
        return fieldName.equals(that.fieldName);

    }

    @Override
    public int hashCode() {
        int result = namespace.hashCode();
        result = 31 * result + fieldName.hashCode();
        return result;
    }
}
