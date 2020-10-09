import java.util.ArrayList;

public class BinaryHeapPriorityQueue<T> {
    int currSize = 0;
    ArrayList<Pair<T>> arr;
    /**
     * @method BinaryHeapPriorityQueue
     * @description constructor method, creates and populates the data structure with a parametric
     *              collection of n items
     * @returns null
    */
    public BinaryHeapPriorityQueue() {
        arr = new ArrayList(0);
    }

    /**
     * @method BinaryHeapPriorityQueue
     * @param inputs
     * @returns constructor
     * @description Overloaded constructor which takes a list of inputs and inserts them into
     *              the tree.
     */
    public BinaryHeapPriorityQueue(T[] inputs) {
        for(T t : inputs) {
            this.Insert(t, 0);
        }
    }

    /**
     * @method parent
     * @param i
     * @returns int
     * @description delivers the index of the parent of the current index
     */
    private int parent(int i) { return (int)Math.floor(i/2); }

    /**
     * @method left
     * @param i
     * @returns int
     * @description delivers the index of the left child of the current index
     */
    private int left(int i) { return 2*i; }

    /**
     * @method right
     * @param i
     * @returns int
     * @description delivers the index of the right child of the current index
     */
    private int right(int i) { return (2*i + 1); }

    /**
     * @method getSize
     * @returns int
     * @description returns the size of the heap
     */
    public int getSize() { return currSize; }

    /**
     * @method Insert
     * @param value
     * @param priority
     * @returns boolean
     * @description Inserts the given value T with priority into the array. Calls heapify
     *              if insertion is successful.
     */
    public boolean Insert(T value, int priority) {
        //System.out.println(value + "  | " + priority);
        Pair<T> toAdd = new Pair(value, priority);
        if(arr.add(toAdd))
        {
            currSize++;
            return this.heapify(true);
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
        if(r < A.size() && A.get(r).getPriority() > A.get(largest).getPriority()) largest = r;
        if(largest != i) {
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
        return true;
    }

    /**
     * @method EctractMax
     * @returns int
     * @description Removes the root node from the tree and replaces it with the
     *              last one within the array. The tree then rebuilds it's heap
     *              property, then returns the max extracted.
     */
    public int ExtractMax() {
        if(arr.size() < 1) throw new Error("heap underflow!");
        Pair<T> max = arr.get(0);
        arr.set(0, arr.remove(currSize - 1));
        currSize -= 1;
        maxHeapify(this.arr, 0);
        return max.getPriority();
    }

    /**
     * @method IncreaseKey
     * @param value
     * @param i
     * @param priority
     * @return boolean
     * @description Increments the priority of value at the given index i, then loops, swapping
     *              child with parent so long as the newly ranked child is higher than its parent.
     *              Returns true to indicate success.
     */
    public boolean IncreaseKey(int i) {
        arr.get(i).setPriority(this.arr.get(0).getPriority() + 1);
        while(i > 1 && (arr.get(parent(i)).getPriority() < arr.get(i).getPriority())) {
            Pair<T> temp = arr.get(i);
            arr.set(i, arr.get(parent(i)));
            arr.set(parent(i), temp);
        }
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


    /**
     * @class Pair<T>
     * @description A private class to store pairs of each entry's value, and their priority.
     */
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
