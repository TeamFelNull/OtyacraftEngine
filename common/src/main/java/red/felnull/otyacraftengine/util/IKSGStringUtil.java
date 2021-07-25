package red.felnull.otyacraftengine.util;

public class IKSGStringUtil {

    public static String decodeUTFEscapeSequence(String unicode) {
        String[] codeStrs = unicode.split("\\\\u");
        int[] codePoints = new int[codeStrs.length - 1];
        for (int i = 0; i < codePoints.length; i++) {
            codePoints[i] = Integer.parseInt(codeStrs[i + 1], 16);
        }
        return new String(codePoints, 0, codePoints.length);
    }

    public static String encodeUTFEscapeSequence(String original) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < original.length(); i++) {
            sb.append(String.format("\\u%04X", Character.codePointAt(original, i)));
        }
        return sb.toString();
    }

    public static String getLogoASCIIArt() {
        return """                             
                                     ==     =-                   
                                     ==    ==                    
                               ==    ========     =-             
                                =============== -=               
                               :==================               
                           ==+=====================   =           #####   ######   ##  ##     ##       ####   ######     ##     #######  ###### 
                            ==========================           ##   ##  # ## #   ##  ##    ####     ##  ##   ##  ##   ####     ##   #  # ## # 
                            ==========     ==========            ##   ##    ##     ##  ##   ##  ##   ##        ##  ##  ##  ##    ## #      ##   
                        +  =========         ========+           ##   ##    ##      ####    ##  ##   ##        #####   ##  ##    ####      ##   
                        -+=========           ========++++       ##   ##    ##       ##     ######   ##        ## ##   ######    ## #      ##   
                          ========+           +========+.        ##   ##    ##       ##     ##  ##    ##  ##   ##  ##  ##  ##    ##        ##   
                          +=======*           *=======*           #####    ####     ####    ##  ##     ####   #### ##  ##  ##   ####      ####  
                          ========*           +=======*               
                       *+=-========           =========                       #######  ##   ##    ####    ####    ##   ##  #######
                           =========         ========= +=                      ##   #  ###  ##   ##  ##    ##     ###  ##   ##   #
                           -=========.     -=========                          ## #    #### ##  ##         ##     #### ##   ## #
                          :=========================-                          ####    ## ####  ##         ##     ## ####   ####
                          =   =======================-                         ## #    ##  ###  ##  ###    ##     ##  ###   ## #
                               ==================    -                         ##   #  ##   ##   ##  ##    ##     ##   ##   ##   #
                               =================+                             #######  ##   ##    #####   ####    ##   ##  #######
                              =.    ========   .=          
                                    =     ==                     
                                   *=     =+                
                """;
    }

}
