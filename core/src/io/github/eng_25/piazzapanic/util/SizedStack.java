package io.github.eng_25.piazzapanic.util;

import java.util.Stack;

/**
 * A stack of type T, limited in size
 *
 * @param <T> Type of element in stack
 */
public class SizedStack<T> extends Stack<T> {

    private final int maxSize;

    /**
     * @param size max size of stack
     */
    public SizedStack(int size) {
        super();
        this.maxSize = size;
    }

    /**
     * Will only push onto stack if the stack is not full.
     *
     * @param item the item to be pushed onto this stack.
     * @return the item on the top of the stack after attempting to push
     */
    @Override
    public T push(T item) {
        if (this.size() < maxSize) {
            return super.push(item);
        }
        return super.peek();
    }

    /**
     * Checks if the stack is full
     * @return true if full, false otherwise
     */
    public boolean isFull() {
        return this.size() == maxSize;
    }

    @Override
    public synchronized T peek() {
        if (isEmpty()) {
            return null;
        }
        return super.peek();
    }
}
