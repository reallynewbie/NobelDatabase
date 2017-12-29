package nobeldatabase;

/**
 * ModifiedString -- A series of helper string manipulation functions that we
 * use for getting the correct URL for photos.
 *
 * @author Quyen Tang
 * @author Bruce Leung
 */
public class ModifiedString {

    /**
     * countSpaces -- Counts the spaces in the given string and returns the
     * count.
     *
     * @param lastName - the string to be counted
     * @return spaceCount - the counted number of spaces in the string.
     */
    public int countSpaces(String lastName) {
        int spaceCount = 0;
        for (char c : lastName.toCharArray()) {
            if (c == ' ') {
                spaceCount++;
            }
        }
        return spaceCount;
    }

    /**
     * capitalize -- To capitalize the first letter of the input word
     *
     * @param word - a string representing the input word
     * @return output - a string with the first letter capitalized
     */ 
   public static String capitalize(String word) {
        String output;
        if (word.length() >= 2) {
            output = word.substring(0, 1).toUpperCase() + word.substring(1);
        } else {
            output = word;
        }
        return output;
    }

    /**
     * removeAllQuotations -- Removes all quotations from a string.
     *
     * @param word - the original word to have all quotation marks removed.
     * @return newWord - The quotation-less string.
     */
    public static String removeAllQuotations(String word) {
        String newWord = word.replace("\"", "");
        return newWord;
    }

    /*
    
    public static String removeQuotationFirst(String word) {
        StringBuilder builder = new StringBuilder(word); // removing first character 
        builder.deleteCharAt(0); 
        return builder.toString();
    }

    public static String removeQuotationLast(String word) {
        StringBuilder builder = new StringBuilder(word); // removing last character from String 
        builder.deleteCharAt(word.length() - 1);
        return builder.toString();
    }*/
}
