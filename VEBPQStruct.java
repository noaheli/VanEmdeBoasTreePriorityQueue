import java.util.ArrayList;

public class VEBPQStruct<T>  {

    int currSize = 0;
    int universe;
    //Values to indicate the storage of
    int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
    T minContainer = null, maxContainer = null;
    ArrayList<VEBPQStruct<T>> clusters;
    VEBPQStruct<T> summary = null;
    public VEBPQStruct() {
        universe = 2;
    }
    public VEBPQStruct(int universe) {
        if(universe != 2) {
            clusters = new ArrayList<VEBPQStruct<T>>(universe);
            for(int i = 0; i < Math.sqrt(universe); i++) {
                clusters.add(new VEBPQStruct((int)(Math.sqrt(universe))));
            }
            summary = new VEBPQStruct((int)(Math.sqrt(universe)));
        }
        this.universe = universe;
    }
    public int treeMin() { return min; }
    public int treeMax() { return max; }
    public T treeMinVal() { return minContainer; }
    public T treeMaxVal() { return maxContainer; }
    public boolean treeMember(int x) {
        if(x == min || x == max) return true;
        else if (universe == 2) return false;
        else return clusters.get(high(x)).treeMember(low(x));
    }
    private void emptyTreeInsert(T val, int x) {
        minContainer = val;
        maxContainer = val;
        this.min = x;
        this.max = x;
    }
    public void insert(T val, int x) {
        if(min == Integer.MAX_VALUE) {
            emptyTreeInsert(val, x);
            insert(val, x);
        }
        else {
            if (x < min) {
                min = x;
                minContainer = val;
            }
            if (universe > 2) {
                if (clusters.get(high(x)).treeMin() == Integer.MAX_VALUE) {
                    summary.insert(val, high(x));
                    clusters.get(high(x)).emptyTreeInsert(val, low(x));
                    clusters.get(high(x)).insert(val, low(x));
                } else clusters.get(high(x)).insert(val, low(x));
            }
            if (x > max) {
                max = x;
                maxContainer = val;
            }
        }
    }
    public void delete(int x) {
        if(min == max) {
            min = Integer.MAX_VALUE;
            max = Integer.MIN_VALUE;
            if(universe != 2) {
                clusters.get(high(x)).delete(low(x));
            }
        }
        else if(universe == 2) {
            if(x == 0) {
                min = 1;
                minContainer = maxContainer;
            }
            else {
                min = 0;
            }
            max = min;
            maxContainer = minContainer;
        }
        else {
            if(x == min) {
                int cluster = summary.treeMin();
                x = index(cluster, clusters.get(cluster).treeMin());
                T minVal = clusters.get(cluster).treeMinVal();
                min = x;
                minContainer = minVal;

            }
            clusters.get(high(x)).delete(low(x));
            if(clusters.get(high(x)).treeMin() == Integer.MAX_VALUE) {
                summary.delete(high(x));
                if(x == max) {
                    int sumMax = summary.treeMax();
                    if(sumMax == Integer.MIN_VALUE) {
                        max = min;
                        maxContainer = minContainer;
                    }
                    else {
                        max = index(sumMax, clusters.get(sumMax).treeMax());
                    }
                }
            }
            else if(x == max) {
                max = index(high(x), clusters.get(high(x)).treeMax());
            }
        }
    }
    private int high(int x) {
        return (int)Math.floor(x / Math.floor((Math.pow(2, (Math.log(universe) / Math.log(2)) / 2))));
    }

    /**
     * @method: low
     * @param x
     * @returns int
     */
    private int low(int x) {
        return (x % (int)(Math.pow(2, Math.floor((Math.pow(2, (Math.log(universe) / Math.log(2)) / 2))))));
    }
    /**
     * @method: index
     * @param x
     * @param y
     * @returns int
     * @description The calculated index which indicates a position in a higher level VEBStruct
     *          based on the parameters given.
     */
    private int index(int x, int y) {
        return ((x * (int)Math.floor((Math.pow(2, (Math.log(universe) / Math.log(2)) / 2))) + y));
    }
    /** @method print
     *  @params none
     *  @returns void
     *  @description prints the tree for viewing after manipulations, to which it prints in a very lackluster form.
     */
    public void print(int offset) {
        int off = (int)Math.sqrt(universe);
        if(universe != 2) {
            for(int i = 0; i < clusters.size(); i++) {
                clusters.get(i).print(offset + (off * i));
            }
        }
        else
        {
            if(min == Integer.MAX_VALUE) System.out.print(" -1 | -1 ");
            else System.out.print(" " + (offset + min) + " | " + (offset + max) + " ");
        }
    }

    /**
     * @method: ExtractMax
     * @returns int
     * @description the current maximum after it has been successfully removed from the tree,
     *          -1 if there is not value in the tree.
     */
    public int ExtractMax() {
        if(max != Integer.MIN_VALUE) {
            int ret = max;
            this.delete(max);
            return ret;
        }
        return -1;
    }

    /**
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
    }*/



    }

