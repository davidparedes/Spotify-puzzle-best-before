/**
 * @author David Paredes (davidparedes.es)
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.concurrent.CopyOnWriteArrayList;

public class Bestbefore {
    
    static final int YEAR = 0;
    static final int MONTH = 1;
    static final int DAY = 2;
    
    private static int a, b, c;
    private static String str;
    private static CopyOnWriteArrayList<int[]> possDates = new CopyOnWriteArrayList<int[]>();
   
    
    // Check if the year can be possible and convert years to 4 digits format
    private static void checkYears() {
        for (int[] pd : possDates) {
            if (pd[YEAR]>=0 && pd[YEAR]<100) {
                pd[YEAR] = pd[YEAR] + 2000;
            } else if (pd[YEAR]<0 || pd[YEAR]<2000 || pd[YEAR]>2999) {
                illegal();
            }
        }
    }
    
    // Check if the month can be possible and remove the others
    private static void checkMonths() {
        for (int[] pd : possDates) {
            if (pd[MONTH]<1 || pd[MONTH]>12) {
                possDates.remove(pd);
            }
        }
    }
    
    // Check if the day can be possible and remove the others
    private static void checkDays() {
        for (int[] pd : possDates) {
            if (pd[DAY]<1 || pd[DAY]>31) {
                possDates.remove(pd);
            // Check if month should have 30 days
            } else if ((pd[MONTH]==4) | (pd[MONTH]==6) | (pd[MONTH]==9) | (pd[MONTH]==11)) {
                if (pd[DAY] > 30) {
                    possDates.remove(pd);
                }
            // Check if it's February
            } else if (pd[MONTH]==2) {
                if (isLeapYear(pd[YEAR])) {
                    if (pd[DAY] > 29) {
                        possDates.remove(pd);
                    }
                } else if (pd[DAY] > 28) {
                    possDates.remove(pd);
                }
            }
        }
    }
    
    //Return the earliest date among all possible dates
    private static int[] earliestDate() {
        int minYear = 3000;
        int minMonth = 13;
        int minDay = 32;
        if (possDates.size()>1) {
            for (int[] py : possDates) {
                if (py[YEAR]<minYear) {
                    minYear = py[YEAR];
                }
            }
            for (int[] pym : possDates) {
                if (pym[YEAR]>minYear) {
                    possDates.remove(pym);
                }
            }
            if (possDates.size()>1) {                
                for (int[] pm : possDates) {
                if (pm[MONTH]<minMonth) {
                    minYear = pm[MONTH];
                    }
                }
                for (int[] pmm : possDates) {
                    if (pmm[MONTH]>minYear) {
                        possDates.remove(pmm);
                    }
                }
                /* If this point is reached, not need to compare days,
                   just take the first one */
                return possDates.get(0);               
            } else {
                return possDates.get(0);
            }
            
        } else {
            return possDates.get(0);
        }
    }
    
    // Print date with format YYYY/MM/DD
    private static void print(int[] earliestDate) {
        System.out.print(earliestDate[0] + "-");
        if (earliestDate[1]<10) {
            System.out.print("0" + earliestDate[1] + "-");
        } else {
            System.out.print(earliestDate[1] + "-");
        }
        if (earliestDate[2]<10) {
            System.out.println("0" + earliestDate[2]);
        } else {
            System.out.println(earliestDate[2]);
        }
    }
    
    /* A year is a leap year (has 366 days) if the year is divisible by 4,
       unless it is divisible also by 100 but not by 400 (so 2000 is a
       leap year, 2100 is not a leap year, and 2012 is a leap year) */
    private static boolean isLeapYear(int i) {
        if (i % 4 == 0) {
            if (i % 100 == 0) {
                if (i % 400 == 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    
    // Print the illegal input and exit
    private static void illegal() {
        System.out.println( str + " is illegal" );
        System.exit(0);
    }
    
    public static void main(String[] args) throws IOException {
        
        InputStreamReader inp = new InputStreamReader(System.in); 
        BufferedReader br = new BufferedReader(inp);
        
        // Wait for user input
        str = br.readLine();
        
        // Input splits into a, b and c.
        StringTokenizer tokens = new StringTokenizer(str,"/");
        
        try {
            a = Integer.parseInt(tokens.nextToken());
            b = Integer.parseInt(tokens.nextToken());
            c = Integer.parseInt(tokens.nextToken());
        } catch (Exception e) {
            illegal();
        }
        
        // Possible date combinations
        int[] pd1 = {a, b, c}; possDates.add(pd1);
        int[] pd2 = {a, c, b}; possDates.add(pd2);
        int[] pd3 = {b, a, c}; possDates.add(pd3);
        int[] pd4 = {b, c, a}; possDates.add(pd4);
        int[] pd5 = {c, b, a}; possDates.add(pd5);
        int[] pd6 = {c, a, b}; possDates.add(pd6);
        
        checkYears();
        checkMonths();
        checkDays();
        
        if (possDates.isEmpty()) {
            illegal();
        } else {
            print(earliestDate());
        }
    }
}