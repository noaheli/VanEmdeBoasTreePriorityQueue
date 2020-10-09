import java.util.ArrayList;

public class VEBPQStruct<T>  {
    int universe;
    double truniverse = 2;
    int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
    T minContainer = null, maxContainer = null;
    VEBPQStruct<T>[] clusters;
    VEBPQStruct<T> summary = null;

    /**
     * @method VEBPQStruct
     * @returns constructor
     * @description Default constructor, initalizes universe size to be 2.
     *              Unlikely to be used.
     */
    public VEBPQStruct() {

        universe = 2;
    }

    /**
     * @method VEBPQStruct
     * @param universe
     * @returns constructor
     * @description Overloaded constructor for the VEB Priority Queue Structure.
     *              Generates a list of empty clusters and initializes this cluster's
     *              summary.
     */
    public VEBPQStruct(int universe) {
        double size = 2;
        if(universe != 2) {

            for(int i = 1; i < 6; i++)
            {
                size = Math.pow(2, Math.pow(2, i));
                if(size >= universe) {
                    universe = (int)size;
                    truniverse = size;
                    break;
                }
            }
            clusters = new VEBPQStruct[((int)Math.sqrt(size))];
            summary = new VEBPQStruct((int)(Math.sqrt(size)));
        }
        this.universe = universe;
    }

    /**
     * @method treeMin
     * @returns int
     * @description Returns the Tree's minimum priority.
     */
    public int treeMin() { return min; }

    /**
     * @nmethod treeMax
     * @returns int
     * @description Returns the Tree's maximum priority.
     */
    public int treeMax() { return max; }

    /**
     * @method treeMinVal
     * @returns T
     * @description Returns the generic value stored in the minimum priority's node
     */
    public T treeMinVal() { return minContainer; }

    /**
     * @method treeMaxVal
     * @returns T
     * @description Returns the generic value stored in the maximum priority's node
     */
    public T treeMaxVal() { return maxContainer; }

    /**
     * @method high
     * @param x
     * @returns int
     * @description Returns the index of which cluster the current variable (passed by x) would be located within.
     */
    private int high(int x) {
        return (int)Math.floor(x / (int)(((Math.pow(2, Math.floor((Math.log(truniverse) / Math.log(2)) / 2))))));
    }
    /**
     * @method low
     * @param x
     * @returns int
     * @description Returns the index to which x belongs to in the deeper clusters.
     */
    private int low(int x) {
        return (x % (int)(((Math.pow(2, Math.floor((Math.log(truniverse) / Math.log(2)) / 2))))));
    }
    /**
     * @method index
     * @param x
     * @param y
     * @returns int
     * @description The calculated index which indicates a position in a higher level VEBStruct
     *          based on the parameters given.
     */
    private int index(int x, int y) {
        return ((x * (int)Math.floor((Math.pow(2, (Math.log(truniverse) / Math.log(2)) / 2))) + y));
    }

    /**
     * @method treeMember
     * @param x
     * @returns boolean
     * @description Returns whether or not a value with the indicated priority exists
     *              within the tree
     */
    public boolean treeMember(int x) {
        if(x == min || x == max) return true;
        else if (universe == 2) return false;
        else return clusters[high(x)].treeMember(low(x));
    }

    /**
     * @method emptyTreeInsert
     * @param val
     * @param x
     * @description Inserts the value and the priority into the max and min. Only called
     *              on an empty cluster.
     */
    private void emptyTreeInsert(T val, int x) {
        minContainer = val;
        maxContainer = val;
        this.min = x;
        this.max = x;
    }

    /**
     * @method insert
     * @param val
     * @param x
     * @returns void
     * @description Inserts the value (val) into the index (x) within the tree. Called recursively
     *              on deeper clusters.
     *
     */
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
                int h = high(x);
                if(clusters[h] == null) clusters[h] = new VEBPQStruct<>(high(universe));
                if(summary == null) summary = new VEBPQStruct<>(high(universe));
                if (clusters[h].treeMin() == Integer.MAX_VALUE) {
                    summary.insert(val, high(x));
                    clusters[high(x)].emptyTreeInsert(val, low(x));
                    clusters[high(x)].insert(val, low(x));
                } else clusters[high(x)].insert(val, low(x));
            }
            if (x > max) {
                max = x;
                maxContainer = val;
            }
        }
    }

    /**
     * @method delete
     * @param x
     * @returns void
     * @description Deletes the value at the given index (x) from every iteration of the
     *              Van Emde Boas Structure.
     */

    public T delete(int x) {
        T ret = null;
        if(min == max) {
            ret = minContainer;
            min = Integer.MAX_VALUE;
            max = Integer.MIN_VALUE;
            if(universe != 2) {
                clusters[high(x)].delete(low(x));
            }
        }
        else if(universe == 2) {
            if(x == 0) {
                ret = minContainer;
                min = 1;
                minContainer = maxContainer;
            }
            else {
                ret = maxContainer;
                min = 0;
            }
            max = min;
            maxContainer = minContainer;
        }
        else {
            if(x == min) {
                int cluster = summary.treeMin();
                x = index(cluster, clusters[cluster].treeMin());
                T minVal = clusters[cluster].treeMinVal();
                min = x;
                ret = minContainer;
                minContainer = minVal;
            }
            clusters[high(x)].delete(low(x));
            if(clusters[high(x)].treeMin() == Integer.MAX_VALUE) {
                summary.delete(high(x));
                if(x == max) {
                    int sumMax = summary.treeMax();
                    ret = maxContainer;
                    if(sumMax == Integer.MIN_VALUE) {
                        max = min;
                        maxContainer = minContainer;
                    }
                    else {
                        max = index(sumMax, clusters[sumMax].treeMax());
                        maxContainer = clusters[sumMax].treeMaxVal();
                    }
                }
            }
            else if(x == max) {
                ret = maxContainer;
                max = index(high(x), clusters[high(x)].treeMax());
                maxContainer = clusters[high(x)].treeMaxVal();
            }
        }
        return ret;
    }

    /**
     * @method treeSuccessor
     * @param x
     * @returns int, the index if successor exists, -1 otherwise.
     * @description Returns the index of the next highest priority, given priority x.
     */
    public int treeSuccessor(int x) {
        if(universe == 2) {
            if(x == 0 && max == 1) return 1;
            else return -1;
        }
        else if(min != Integer.MAX_VALUE && x < min) return min;
        else {
            int maxLow = clusters[high(x)].treeMax();
            if(maxLow != Integer.MIN_VALUE && low(x) < maxLow) {
                int offset = clusters[high(x)].treeSuccessor(low(x));
                return index(high(x), offset);
            }
            else {
                int succCluster = summary.treeSuccessor(high(x));
                if(succCluster == -1) return -1;
                else {
                    int offset = clusters[succCluster].treeMin();
                    return index(succCluster, offset);
                }
            }
        }
    }

    /**
     * @method treePredecessor
     * @param x
     * @returns int, the index if predecessor exists, -1 otherwise.
     * @description Returns the index of the next lowest priority, given priority x.
     */
    public int treePredecessor(int x) {
        if(universe == 2) {
            if(x == 1 && min == 0) return 0;
            else return -1;
        }
        else if(max == Integer.MIN_VALUE && x > max) return max;
        else {
            int minLow = clusters[high(x)].treeMin();
            if(minLow == -1 && low(x) > minLow) {
                int offset = clusters[high(x)].treePredecessor(low(x));
                return index(high(x), offset);
            }
            else {
                int predCluster = summary.treePredecessor(high(x));
                if(predCluster == -1) {
                    if(min == Integer.MAX_VALUE && x > min) return min;
                    else return -1;
                }
                else {
                    int offset = clusters[predCluster].treeMax();
                    return index(predCluster, offset);
                }
            }
        }
    }
    /**
     * @method: ExtractMax
     * @returns int
     * @description the current maximum after it has been successfully removed from the tree,
     *          -1 if there is not value in the tree.
     */
    public int ExtractMax() {
        if(max > 0 && min < universe) {
            int ret = max;
            this.delete(max);
            return ret;
        }
        return -1;
    }

    /**
     * @method IncreaseKey
     * @param value
     * @param i
     * @param priority
     * @returns boolean
     * @description Actually not quite sure what this one does haha
     */
    public boolean IncreaseKey(int i) {
        if(this.treeMember(i)) {
            if(this.max != universe - 1) {
                T val = delete(i);
                this.insert(val, max + 1);
                return true;
            }
        }
        return false;
    }

    /** @method print
     *  @params none
     *  @returns void
     *  @description prints the tree for viewing after manipulations, to which it prints in a very lackluster form.
     */
    public void print(int offset) {
        int off = (int)Math.sqrt(universe);
        if(universe != 2) {
            for(int i = 0; i < clusters.length; i++) {
                clusters[i].print(offset + (off * i));
            }
        }
        else
        {
            if(min == Integer.MAX_VALUE) System.out.print(" -1 | -1 ");
            else System.out.print(" " + (offset + min) + " | " + (offset + max) + " ");
        }
    }
}

