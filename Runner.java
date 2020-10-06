import java.util.ArrayList;

public class Runner {
    public static void main(String[] args)
    {
        ArrayList<Integer> priorities = new ArrayList<>(100);
        for(int i = 0; i < 100; i++) {
            priorities.add(i);
        }
        BinaryHeapPriorityQueue<Integer> tester = new BinaryHeapPriorityQueue<>();
        VEBPQStruct<Integer> tester2 = new VEBPQStruct<Integer>(256);
        for(int i = 0; i < 100; i++)
        {
            Integer value = ((int)(Math.random() * 100));
            int priority = priorities.remove(0);
            //((int)((Math.random() * priorities.size())))
            System.out.println(priority);
            tester.Insert(value, priority);
            tester2.insert(value, priority);
        }
        //tester.heapify(true);
        //tester.print();
        //tester.ExtractMax();
        System.out.println();
        //tester.print();
        tester2.print(0);
        System.out.println("\n" + tester2.treeMax());
        System.out.println(tester2.treeMin());
        tester2.delete(tester2.treeMin());
        tester2.delete(tester2.treeMin());
        //tester2.print(0);

        tester2.delete(tester2.treeMax());

        tester2.delete(tester2.treeMax());

        tester2.delete(tester2.treeMax());

        tester2.delete(tester2.treeMax());


        tester2.delete(tester2.treeMax());

        tester2.delete(tester2.treeMax());

        tester2.delete(tester2.treeMax());

        tester2.delete(tester2.treeMax());

        tester2.delete(tester2.treeMax());

        tester2.delete(tester2.treeMax());

        tester2.delete(tester2.treeMax());

        tester2.delete(tester2.treeMax());

        tester2.delete(tester2.treeMax());


        tester2.delete(tester2.treeMax());

        tester2.delete(tester2.treeMax());

        tester2.delete(tester2.treeMax());

        tester2.delete(tester2.treeMax());

        tester2.delete(tester2.treeMax());
        tester2.print(0);

    }
}
