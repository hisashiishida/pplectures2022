// Implementation of a subset of the java.util.concurrent.BlockingQueue interface 
public class BlockingQueue {

    // queue and dequeue string data -- not objects -- makes it easier to read
    private String [] queue;

    // queue metadata  
    private int limit = 10;
    private int head = 0;
    private int qlen = 0;

    // Create an array of strings as the queue
    public BlockingQueue(int limit){
        this.limit = limit;
        this.queue = new String [limit];
    }

    public synchronized void put(String item)
    throws InterruptedException  {
    
        // variable for slot item goes in
        int slot;

        // wait and don't add if the queue is full
        // TODO
        if(this.qlen == this.limit) {
            wait();
        }

        //Add new eleent to the head
        slot = this.head;
        
        // get slot and update head and length
        // TODO
        this.qlen+= 1;
        this.head += 1;

        //Cycle back to the first element if it reaches to the limit
        if (this.head == this.limit){
            this.head = 0;
        }
       
        // notify takers if this is the first item in queue
        // TODO
        if (this.qlen == 1){
            notifyAll();
        }

        // add the item
        this.queue[slot] = item;
    }

    public synchronized String take()
    throws InterruptedException {

        // slot to be taken and deleted
        int tail;

        // don't take from an empty queue
        // TODO 
        if(this.qlen == 0) {
            wait();
        }
        
    
        //get slot(tail) 
        // TODO
        if (this.head >= this.qlen){
            tail = this.head - this.qlen;
        }
        
        else{
            tail = this.limit + (this.head - this.qlen);
        }

        // if taking from a full queue, notify putters
        if(this.qlen == this.limit){
            notifyAll();
        }
        
        // update queue length
        this.qlen = this.qlen - 1;

        // take the item and dereference pointer for garbage collection
        String ret_obj = this.queue[tail];
        queue[tail]=null;

        // return item
        return ret_obj;
    }
}
