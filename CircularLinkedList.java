import java.util.Iterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

public class CircularLinkedList<AnyType> implements List<AnyType>
{
  private static class Node<AnyType>
  {
    private AnyType data;
    private Node<AnyType> next;

    public Node(AnyType d, Node<AnyType> n)
    {
      setData(d);
      setNext(n);
    }

    public AnyType getData() { return data; }

    public void setData(AnyType d) { data = d; }

    public Node<AnyType> getNext() { return next; }

    public void setNext(Node<AnyType> n) { next = n; }
  }

  private int theSize;
  private int modCount;
  private Node<AnyType> tail;

  public CircularLinkedList()
  {
	    tail = new Node<AnyType>(null, null);
	    modCount = 0;
	    clear();
  }
  
//Remove all values from the list, creating an empty list.
  public void clear()
  {
	  tail.setNext(tail);
	  theSize = 0;
  }
  
//Return the number of data values currently in the list.
  public int size()
  {
	  return theSize;
  }
  
//Return true if the list is empty; otherwise return false 
  public boolean isEmpty()
  {
	  return (size() == 0);
  }
  
//Return the data value at position index in the list.
  public AnyType get(int index)
  {
	  Node<AnyType> indexNode = getNode(index);
	  return indexNode.getData();
  }
  
//Return the old data value at position index and replace it with the data value newValue.
  public AnyType set(int index, AnyType newValue)
  {
	  Node<AnyType> indexNode = getNode(index);
	  AnyType oldValue = indexNode.getData();

	  indexNode.setData(newValue);
	  return oldValue;
  }
  
// Adds new node to the end of the circularLinkedList
  public boolean add(AnyType newValue)
  {
      add(size(), newValue);
      return true;
  }
  
//Add data value newValue at the position specified by index; 
//shifting to the right by one position all the values from position index to the end of the list.
  public void add(int index, AnyType newValue)
  {
	  Node<AnyType> nextNode = getNode(index, 0, size());
	  Node<AnyType> prevNode = tail;
	  Node<AnyType> newNode = new Node<>(newValue, nextNode);

	  if(index > 0)
	  {prevNode = getNode(index-1, 0, size());}
	
	  prevNode.setNext(newNode);
	  theSize++;
	  modCount++;
  }

//Remove and return the data value at position index in the list.  
  public AnyType remove(int index)
  {
	  return remove(getNode(index));
  }
  
//Remove and return the data value at position index in the list.  
  private AnyType remove(Node<AnyType> currNode)
  {
	  Node<AnyType> searchNode = tail.getNext();
	  Node<AnyType> prevNode = tail;
	  Node<AnyType> nextNode = currNode.getNext();
	  
	  //finds previous node
	  for(int i=0;i<size();i++)
	  {
		  if(searchNode.getData() == currNode.getData())
		  {i=size();}
		  else
		  {
			prevNode=searchNode;
			searchNode = searchNode.getNext();
		  }
	  }
	  
	  prevNode.setNext(nextNode);
	  theSize--;
	  modCount++;

      return currNode.getData();
  }
  
//Moves the value at the head of the list to the tail of the list without removing and adding anything to the list.
  public void rotate()
  {   
	  Node<AnyType> beforeTailNode = getNode(size()-2, 0, size());
	  Node<AnyType> oldTailNode = getNode(size()-1, 0, size());
	  Node<AnyType> newTailNode = tail.getNext(); //the node after the tail
	  
	  tail.setNext(newTailNode.getNext());
	  beforeTailNode.setNext(oldTailNode);
	  oldTailNode.setNext(newTailNode);
	  newTailNode.setNext(tail);
	  
  }
  
  public Iterator<AnyType> iterator()
  {
    return new LinkedListIterator();    
  }

  private Node<AnyType> getNode(int index)
  {
    return (getNode(index, 0, size()-1));
  }
  
  //Return the pointer to the node at position index in the list.
  private Node<AnyType> getNode(int index, int lower, int upper)
  {
	  Node<AnyType> currNode;

	    if (index < lower || index > upper)
	        {throw new IndexOutOfBoundsException();}

	    currNode = tail.getNext(); //this is like the head
	    for (int i = 0; i < index; i++) 
	    	{currNode = currNode.getNext();}
	    
	    return currNode;
  }

//Iterator Class
  private class LinkedListIterator implements Iterator<AnyType>
  {
    private Node<AnyType> previous;
    private Node<AnyType> current;
    private int expectedModCount;
    private boolean okToRemove;

    LinkedListIterator()
    {
    	previous = tail;
    	current = tail.getNext();
        expectedModCount = modCount;
        okToRemove = false;
    }
    
// Returns true if the iteration has more elements.
    public boolean hasNext()
    {
        return (current != tail);

    }
    
// Returns the next element in the iteration.
    public AnyType next()
    {
        if (modCount != expectedModCount)
            throw new ConcurrentModificationException();
          if (!hasNext())
            throw new NoSuchElementException();

          AnyType nextValue = current.getData();
          previous = current;
          current = current.getNext();
          okToRemove = true;
          return nextValue;
    }
    
// Removes from the underlying list the last element returned by the Iterator.
    public void remove()
    {
    	if (modCount != expectedModCount)
            throw new ConcurrentModificationException();
          if (!okToRemove)
            throw new IllegalStateException();

          CircularLinkedList.this.remove(previous);
          expectedModCount++;
          okToRemove = false;
    }
  }
  
}
