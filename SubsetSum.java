import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.IntStream;

public class SubsetSum {
    
    public static void main (String args[]){
        /*Initializing the sets with any value,
          for easier initalization I filled all
          of them with the same value(5)*/
        int[][] sets = new int[3][];
        sets[0] = new int[32];
        sets[1] = new int[34];
        sets[2] = new int[3500];
        for (int[] set : sets) {
            for (int j = 0; j < set.length; j++) {
                set[j] = 5;
            }
        }
        for(int i=0;i<sets.length;i++){
            int[] x = sets[i];
            int xSize = x.length;
            int sum = IntStream.of(x).sum();
            int k = sum-1;
            System.out.println("\nSet(x) = "+Arrays.toString(x)+"\nSet Size(xSize) = "+xSize
                              +"\nSet Total Sum(s) = "+sum+"\nChosen Integer(k) = "+k);
            /*don't run the recursive algorithm on
              the last set(it takes forever)*/
            if(i<2)
                run("Recursive", x, k);
            run("Memoized", x, k);
            run("Dynamic P.", x, k);
        }
    }
    //method to call an algorithm and calculate execution time
    static void run(String a, int[] x, int k){
        int xSize = x.length;
        System.out.print("Aglorithm = "+a+"\tOutput = ");
        long startTime = System.currentTimeMillis();
        switch(a){
            case "Recursive": System.out.print(R(x,xSize,k)+"\t");
                break;
            case "Memoized":HashMap<String,Integer> cache = new HashMap<>();
                            System.out.print(Memo(x,xSize,k,cache)+"\t");
                break;
            case "Dynamic P.": System.out.print(DP(x,xSize,k)+"\t");
                break;  
        }
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Runtime = "+elapsedTime/1000);
    }
    
    static int R(int[] x,int xSize, int k){
        // Base Cases 
        if (xSize == 0 || k == 0) 
            return 0; 
        // If last element is greater than  
        // sum, then ignore it 
        if (x[xSize-1] > k){
            return R(x, xSize-1, k); 
        }
        /*else, check if sum can be obtained
          by either including the last element 
          or excluding the last element*/
        return Math.max(x[xSize-1]+R(x, xSize-1, k-x[xSize-1]), R(x, xSize-1, k)); 
    }
    
    static int Memo(int[] x,int xSize, int k, HashMap<String,Integer> memo){
        // Initialize key and check memo for value
        String key = xSize+"|"+k;
        if(memo.containsKey(key)) return memo.get(key);
        // Base Cases 
        if (xSize == 0 || k == 0) return 0;
        /* If last element is greater than  
           sum, then ignore it */
        if (x[xSize-1] > k){
            /*perform recursive call and
              store the result in memo*/
            int value = Memo(x, xSize-1, k, memo);
            memo.put(key, value);
            return value;
        }
        /*else, check if sum can be obtained
          by either including the last element 
          or excluding the last element*/
        else{
            /*perform recursive call and
              store the result in memo*/
            int value = Math.max(x[xSize-1]+Memo(x, xSize-1, k-x[xSize-1], memo), Memo(x, xSize-1, k, memo));
            memo.put(key, value);
            return value;
        }
    }
    
    static int DP(int[] x,int xSize, int k){
        /*Initializing 2d array with the
          following dimenstions*/
        int[][] table = new int[xSize+1][k+1];
        for(int j=1;j<=xSize;j++)
            for(int i=0;i<=k;i++)
                /*if statement with same conditions
                  in the previous algorithms*/
                if(x[j-1]>i)
                    table[j][i] = table[j-1][i];
                else
                    table[j][i] = Math.max(x[j-1]+table[j-1][i-x[j-1]], table[j-1][i]);
        return table[xSize][k];
    }
}
