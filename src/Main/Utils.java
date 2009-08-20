
package Main;

/**
 * A container class for general purpose utility method(s).
 */
public class Utils
{
    /**
     * Creates a String presentation of double value that is formatted to have a
     * certain number of decimals. Formatted output value does not include any
     * rounding rules. Output value is just truncated on the place that is
     * defined by received <i>decimals</i> parameter.
     * 
     * @param value -
     *            double value to be converted.
     * @param decimals -
     *            number of decimals in the String presentation.
     * @return a string representation of the argument.
     */
    public static String formatDouble(double value, int decimals)
    {
        String doubleStr = "" + value;
        int index = doubleStr.indexOf(".") != -1 ? doubleStr.indexOf(".")
                : doubleStr.indexOf(",");
        // Decimal point can not be found...
        if (index == -1) return doubleStr;
        // Truncate all decimals
        if (decimals == 0)
        {
            return doubleStr.substring(0, index);
        }

        int len = index + decimals + 1;
        if (len >= doubleStr.length()) len = doubleStr.length();

        double d = Double.parseDouble(doubleStr.substring(0, len));
        return String.valueOf(d);
    }
}
