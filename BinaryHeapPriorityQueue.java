import java.util.ArrayList;

public class BinaryHeapPriorityQueue<T> {

    int currSize = 0;
    /**
     * @method BinaryHeapPriorityQueue
     * @description constructor method, creates and populates the data structure with a parametric
     *              collection of n items
     * @returns null
    */
    ArrayList<Pair<T>> arr;
    public BinaryHeapPriorityQueue() {
        arr = new ArrayList(0);
    }
    public BinaryHeapPriorityQueue(T[] inputs) {
        for(T t : inputs) {
            this.Insert(t, 0);
        }
    }
    private int parent(int i) { return (int)Math.floor(i/2); }
    private int left(int i) { return 2*i; }
    private int right(int i) { return (2*i + 1); }
    public int ExtractMax() {
        if(arr.size() < 1) throw new Error("heap underflow!");
        Pair<T> max = arr.get(0);
        arr.set(0, arr.remove(currSize - 1));
        currSize -= 1;
        maxHeapify(this.arr, 0);
        return max.getPriority();
    }
    public boolean Insert(T value, int priority) {
        //System.out.println(value + "  | " + priority);
        Pair<T> toAdd = new Pair(value, priority);
        if(arr.add(toAdd))
        {
            currSize++;
            return true;
        }
        return false;
    }
    public boolean IncreaseKey(T value, int i, int priority) {
        if(priority < arr.get(i).getPriority())
            throw new Error("new key is smaller than current key");
        arr.get(i).setPriority(priority);
        while(i > 1 && (arr.get(parent(i)).getPriority() < arr.get(i).getPriority())) {
            Pair<T> temp = arr.get(i);
            arr.set(i, arr.get(parent(i)));
            arr.set(parent(i), temp);
        }
        return false;
    }
    /**
     * @method heapify
     * @param maxMin defines whether the heap is to be a max (true) or min heap (false)
     * @returns the return parameter indicating success on calling buildMaxHeap.
     */
    public boolean heapify(boolean maxMin) {
        return (maxMin) ? this.buildMaxHeap(this.arr) : false;
    }
    /**
     * @method maxHeapify
     * @param A the arraylist of <value, priority> pairs which constitutes the heap
     * @param i The integer index from which we will be manipulating the array from
     * @returns void
     * @description Applies the heap structure property to the array which is passed into the function
     */
    private void maxHeapify(ArrayList<Pair<T>> A, int i) {
        int l = left(i);
        int r = right(i);
        int largest = i;
        if(l < A.size() && A.get(l).getPriority() > A.get(i).getPriority()) largest = l;
        else largest = i;
        if(r <= A.size() && A.get(r).getPriority() > A.get(largest).getPriority()) largest = r;
        if(largest != i) {
            //System.out.println("Swapping " + A.get(largest).getPriority() + " and " + A.get(i).getPriority() + " and calling heapify on " + largest);
            Pair<T> temp = A.get(i);
            A.set(i, A.get(largest));
            A.set(largest, temp);
            maxHeapify(A, largest);
        }
    }

    /**
     * @method buildMaxHeap
     * @param A
     * @return success of building the heap
     */
    private boolean buildMaxHeap(ArrayList<Pair<T>> A) {
        for(int i = (A.size() / 2); i >= 0; i--) {
            maxHeapify(A, i);
        }
        System.out.println("done.");
        return true;
    }

    /** @method print
     *  @params none
     *  @returns void
     *  @description prints the tree for viewing after manipulations, to which it prints in a very lackluster form.
     */
    public void print() {
        int currRow = 0;
        int height = (int)Math.ceil(Math.log(currSize) / Math.log(2));
        loop:
        {
            for (int i = 0; i < currSize; i++) {
                int rowAmnt = (int) Math.pow(2.0, currRow);
                String rowModifier = "";
                for(int j = currSize - i; j > 1; j--)
                {
                    System.out.print(" ");
                }
                for (int j = 0; j < rowAmnt; j++) {
                    if (i + j == currSize) break loop;
                    System.out.print(" " + arr.get(i + j).getPriority() + ((arr.get(i + j).getPriority() >= 10) ? "" : " ") + rowModifier);
                    if (j == rowAmnt - 1) i += j;
                }
                currRow++;
                System.out.println("");
            }
        }
    }
    public int getSize() { return currSize; }
    private class Pair<T> {
        private T key = null;
        private int priority;
        public Pair(){}
        public Pair(T key, int priority) {
            this.key = key;
            this.priority = priority;
        }
        public T getKey() { return key; }
        public int getPriority() { return priority; }
        public void setKey(T key) { this.key = key; }
        public void setPriority(int priority) { this.priority = priority; }
    }
}
