package edu.gvsu.CIS163.Fall_2015.Andrew_Sully.BankingProgram;

/***********************************************************************
 * A simple implementation of a namespace system to allow fields to have
 * the same name in different classes with no conflict
 **********************************************************************/
public class HeaderName {
    /**
     * The package and class name of the class that owns this header
     */
    private String namespace;

    /**
     * The specific name of the field this header represents
     */
    private String fieldName;

    /*******************************************************************
     * Construct this HeaderName with a namespace based on an instance
     * @param parent An instance of the owner of this header
     * @param fieldName The name of the field represented
     ******************************************************************/
    public HeaderName(Object parent, String fieldName){
        this(parent.getClass(), fieldName);
    }

    /*******************************************************************
     * Construct this HeaderName with a namespace based on a class
     * @param parent The class of the owner of this header
     * @param fieldName The name of the field represented
     ******************************************************************/
    public HeaderName(Class parent, String fieldName){
        namespace = parent.getCanonicalName();
        this.fieldName = fieldName;
    }

    /*******************************************************************
     * Get the full name of this HeaderName
     * @return namespace.fieldName for this HeaderName
     ******************************************************************/
    public String getFullName(){
        return namespace + "." + fieldName;
    }

    /*******************************************************************
     * Get just the field name. Useful for displaying to the user
     * @return The fieldName
     ******************************************************************/
    public String getFieldName(){
        return fieldName;
    }

    /*******************************************************************
     * Evaluate the equality of this and o
     * @param o The object to compare this to
     * @return True if namespace and fieldName match
     ******************************************************************/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HeaderName that = (HeaderName) o;

        if (!namespace.equals(that.namespace)) return false;
        return fieldName.equals(that.fieldName);

    }

    /*******************************************************************
     * Generate a hashCode for an instance of this class. Used when this
     * is a key to a HashTable
     * @return The generated hashCode
     ******************************************************************/
    @Override
    public int hashCode() {
        int result = namespace.hashCode();
        result = 31 * result + fieldName.hashCode();
        return result;
    }
}
