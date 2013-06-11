package de.tudresden.deexcelerator.data;

/**
 * <span class="de">Datentypen f&uumlr Spalteninformationen.</span>
 * <span class="en">Data types for the col information.</span>
 * @author Christopher Werner
 *
 */
public enum DataType {
    // Note: these are ordered by "specificty", do not change orderHk
    STRING(0),
    EMAIL(1),
    URL(2),
    DATETIME(3),
    DOUBLE(4),
    BIGINT(6),
    INTEGER(7),
    CURRENCY(8);

    public final int Specificity;

    private DataType(int value)
    {
        Specificity = value;
    }
}
