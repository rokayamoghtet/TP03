package PROJECT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class BranchBound
{
static int[][]        p;                            
     static   String[]        c;                          
   
       static int  n;                              
 
    static ArrayList<Tour> soln    = new ArrayList<Tour>();
    static int             bestTour;                      
     
   static int             blocked;                      
   static boolean         DEBUG   = true;                

    static boolean         VERBOSE = true;              
   

   
    @SuppressWarnings("rawtypes")
    private static class Tour implements Comparable
    {
        int[]          soln;
        int            index;        
        int            dist;
        static int     nTours = 0;
       
        static boolean DFS    = true;
        static boolean DBG    = true;
 
        /*
         * Presumable edges up to [index-1] have been verified before
         * this constructor has been called. So compute the fixed
         * distance from [0] up to [index-1] as dist.
         */
        private Tour(int[] vect, int index, int[][] p)
        {
            dist = 0;
            for (int i = 1; i < index; i++)
                // Add edges
                dist += p[vect[i - 1]][vect[i]];
            if (index == n)
                dist += p[vect[n - 1]][vect[0]]; // Return edge
            soln = new int[n]; // Deep copy
            System.arraycopy(vect, 0, soln, 0, n);
            this.index = index; // Index to permute
            nTours++; // Count up # of tours
            if (DBG)
                System.out.printf("Idx %d: %s\n", index, toString());
        }
        public int compareTo(Object o)
        {
            Tour rt = (Tour) o;
            int x1 = rt.index - this.index, x2 = this.dist - rt.dist;
            if (DFS)
                return x1 == 0 ? x2 :x1;
            else
                return x2;
        }
 
        public String toString()
        {
            StringBuilder val = new StringBuilder(c[soln[0]]);
            for (int i = 1; i < n; i++)
                val.append(", " + c[soln[i]]);
            val.append(", " + c[soln[0]]);
            val.append(String.format(" for %d", dist));
            return val.toString();
        }
    }
 
    private static void init(Scanner inp)
    {
        int sub1, sub2;
        String line;
        n = inp.nextInt();
        p = new int[n][n];
        c = new String[n];
       
        for (sub1 = 0; sub1 < n; sub1++)
            Arrays.fill(p[sub1], -1);
        inp.nextLine();
        for (sub1 = 0; sub1 < n; sub1++)
            c[sub1] = inp.nextLine();
        Arrays.sort(c);
        inp.nextLine();
        blocked = 0;
        while (inp.hasNext())
        {
            int head, tail;
            int dist;
            String src, dst;
            line = inp.nextLine(); // E.g.: "George" "Pasco" 91
            // Chop out the double-quoted substrings.
            head = line.indexOf('"') + 1;
            tail = line.indexOf('"', head);
            src = line.substring(head, tail);
            head = line.indexOf('"', tail + 1) + 1;
            tail = line.indexOf('"', head);
            dst = line.substring(head, tail);
            dist = Integer.parseInt(line.substring(tail + 1).trim());
            sub1 = Arrays.binarySearch(c, src);
            sub2 = Arrays.binarySearch(c, dst);
            p[sub1][sub2] = p[sub2][sub1] = dist;
            blocked += dist;
        }
        blocked += blocked; // Double the total
        bestTour = blocked; // And initialize bestTour
    }
 
    // Used below in generating permutations.
    private static void swap(int[] x, int p, int q)
    {
        int tmp = x[p];
        x[p] = x[q];
        x[q] = tmp;
    }
 
    public static void tour()
    {
        int[] vect = new int[n];
        int start;
        Queue<Tour> work = new PriorityQueue<Tour>();
        // First permutation vector.
        for (int i = 0; i < n; i++)
            vect[i] = i;
        start = Arrays.binarySearch(c, "Spokane");
        if (start >= 0)
        {
            vect[start] = 0;
            vect[0] = start;
        }
        work.add(new Tour(vect, 1, p));
        while (!work.isEmpty()) // Branch-and-bound loop
        {
            Tour current = work.poll();
            int index = current.index;
            vect = current.soln;
            if (index == n) // I.e., Full permutation vector
            {
                if (p[vect[n - 1]][vect[0]] > 0) // Return edge?
                {
                    if (current.dist < bestTour) // Better than earlier?
                    {// Save the state in the list
                        bestTour = current.dist;
                        soln.add(current);
                        if (DEBUG)
                            System.out.println("Accept " + current);
                    }
                    else if (DEBUG)
                        System.out.println("Too long:  " + current);
                }
                else if (DEBUG)
                    System.out.println("Invalid:   " + current);
            }
            else
            // Continue generating permutations
            {
                int i; // Loop variable
                int hold; // Used in regenerating the original state
                for (i = index; i < n; i++)
                {
                    swap(vect, index, i);
                    if (p[vect[index - 1]][vect[index]] < 0)
                        continue;
                    work.add(new Tour(vect, index + 1, p));
                }
                // Restore original permutation
                hold = vect[index];
                for (i = index + 1; i < n; i++)
                    vect[i - 1] = vect[i];
                vect[n - 1] = hold;
            }
        }
    }
 
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception
    {
        String filename = args.length == 0 ? "RoadSet.txt" : args[0];
        Scanner inp = new Scanner(new java.io.File(filename));
        System.out.println("Data read from file " + filename);
        init(inp);
        tour();
        if (VERBOSE)
        {
            System.out.println("Tours discovered:");
            for (Tour opt : soln)
                System.out.println(opt);
        }
        if (soln.size() == 0)
        {
            System.out.println("NO tours discovered.  Exiting.");
            System.exit(0);
        }
        System.out.println(Tour.nTours + " Tour objects generated.");
        Collections.sort(soln);
        System.out.println("Best tour:  ");
        System.out.println(soln.get(0));
    }
}

