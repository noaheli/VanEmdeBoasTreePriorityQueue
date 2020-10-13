import java.util.ArrayList;

public class Runner {
    public static void main(String[] args)
    {
        int n = 100;
        while(n < 10000000) {
            System.out.println("\nStarting cycle with n = "+ n);
            ArrayList<Integer> priorities = new ArrayList<>(n);
            for(int i = 0; i < n; i++) {
                priorities.add(i);
            }
            long startTime = System.nanoTime();
            BinaryHeapPriorityQueue<Integer> tester = new BinaryHeapPriorityQueue<>();
            VEBPQStruct<Integer> tester2 = new VEBPQStruct<>(n);
            for(int i = 0; i < n; i++)
            {
                Integer value = ((int)(Math.random() * n));
                int priority = priorities.remove((int)((Math.random() * priorities.size())));
                //tester.Insert(value, priority);
                tester2.insert(value, priority);
            }

            long stopTime = System.nanoTime();
            System.out.println("Build time for n size " + n + " is " + (stopTime - startTime));

/**
             startTime = System.nanoTime();
             for(int i = 0; i < (int)((Math.random() * n) + n); i++) {
                int operation = (int)(Math.random() * 3);
                switch(operation) {
                    case 2: {
                        tester.ExtractMax();
                        break;
                    }
                    case 1: {
                        tester.Insert(i, i);
                        break;
                    }
                    default: {
                        int rand = (int)(Math.random() * tester.size());
                        tester.IncreaseKey(rand);
                        break;
                    }
                }
             }
             stopTime = System.nanoTime();
             System.out.println("Runtime for " + n + " operations on the Binary Heap is: " + (stopTime - startTime));
*/
            startTime = System.nanoTime();
            for(int i = 0; i < (int)((Math.random() * n) + n); i++) {
                int operation = (int)(Math.random() * 3);
                switch(operation) {
                    case 2: {
                        tester2.ExtractMax();
                        break;
                    }
                    case 1: {
                        tester2.insert(i, i);
                        break;
                    }
                    default: {
                        int rand = (int)(Math.random() * n);
                        tester2.IncreaseKey(rand);
                        break;
                    }
                }
            }
            stopTime = System.nanoTime();
            System.out.println("Runtime for " + n + " operations on the Van Emde Boas Tree is: " + (stopTime - startTime));


            n *= 10;
        }

    }
}
