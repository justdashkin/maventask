import java.util.*;

/**
 * Created by Daria_Ivanova2 on 10/24/2016.
 */
public class ArraySet<T> implements Set<T> {
    private int size;
    private Object[] data;
    private static final int DEFAULT_CAPACITY = 10;
    private static final float MIN_FILLING_INDEX = 0.5f;
    private long version = 0L;

    public ArraySet() {
        this(DEFAULT_CAPACITY);
    }

    public ArraySet(int capacity) {
        if (capacity < 0)
            throw new IllegalArgumentException();
        data = new Object[capacity];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(data[i], (o))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArraySetIterator();
    }

    public T getElement(int index) {
        checkOutOfBoundsIndex(index);
        return (T) data[index];
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(data, size);
    }

    @Override
    public <T> T[] toArray(T[] elements) {
        if (elements.length < size) {
            return (T[]) Arrays.copyOf(data, size, elements.getClass());
        }
        if (elements.length > size) {
            System.arraycopy(data, 0, elements, 0, size);
            elements[size] = null;
        }
        return elements;
    }

    @Override
    public boolean add(T element) {
        if (!contains(element)) {
            prepareSet();
            data[size] = element;
            size++;
            version++;
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object object) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(object)) {
                remove(i);
                optimizeSet();
                version++;
                return true;
            }
        }
        return false;
    }

    private void remove(int index) {
        checkOutOfBoundsIndex(index);
        System.arraycopy(data, 0, data, 0, index);
        System.arraycopy(data, index + 1, data, index, (data.length - index) - 1);
        size--;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object element : collection) {
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        for (Object element : collection) {
            if (contains(element)) {
                return false;
            }
        }
        prepareSet(size + collection.size());
        addAllToSet(collection);
        size += collection.size();
        version++;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean wasRemoval = false;
        for (int i = size - 1; i >= 0; i--) {
            if (collection.contains(data[i])) {
                remove(data[i]);
                wasRemoval = true;
            }
        }
        optimizeSet();
        version++;
        return wasRemoval;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        boolean wasRemoval = false;
        for (int i = size - 1; i >= 0; i--) {
            if (!collection.contains(data[i])) {
                remove(data[i]);
                wasRemoval = true;
            }
        }
        optimizeSet();
        version++;
        return wasRemoval;
    }

    @Override
    public void clear() {
        data = null;
        size = 0;
        version++;
    }

    private void addAllToSet(Collection<? extends T> products) {
        System.arraycopy(products.toArray(), 0, data, size, products.size());
    }

    private void prepareSet() {
        prepareSet(size + 1);
    }

    private void prepareSet(int neededSize) {
        if (!doesSetExist()) {
            createSet();
        }
        if (!hasFreeSpace(neededSize)) {
            increaseCapacity(neededSize);
        }
    }

    private boolean doesSetExist() {
        return data != null;
    }

    private void increaseCapacity(int neededSize) {
        int newCapacity = ((data.length + neededSize) * 3) / 2 + 1;
        data = increaseSet(newCapacity);
    }

    private Object[] increaseSet(int capacity) {
        Object[] temporaryData = new Object[capacity];
        System.arraycopy(data, 0, temporaryData, 0, size);
        return temporaryData;
    }

    private void createSet() {
        data = new Object[DEFAULT_CAPACITY];
    }

    private boolean hasFreeSpace(int neededSize) {
        int current = data.length;
        return neededSize <= current;
    }

    private void checkOutOfBoundsIndex(int index) {
        if (index < 0 || index > this.size)
            throw new IndexOutOfBoundsException("Index out: " + index);
    }

    private void optimizeSet() {
        if (isListShouldBeTrim()) {
            trim();
        }
    }

    private boolean isListShouldBeTrim() {
        float currentFillingIndex = (float) size / (float) data.length;
        return currentFillingIndex >= MIN_FILLING_INDEX
                || data.length <= DEFAULT_CAPACITY;
    }

    private void trim() {
        int newCapacity = ((size) * 3) / 2 + 1;
        Object[] trimmedSet = new Object[newCapacity];
        System.arraycopy(data, 0, trimmedSet, 0, size);
        data = trimmedSet;
    }

    private class ArraySetIterator <T> implements Iterator<T> {
        private int index;
        private final long startVersion;
        private boolean nextWasCalled = false;

        public ArraySetIterator() {
            startVersion = version;
        }

        public boolean hasNext() {
            return index < size;
        }

        public T next() {
            if (hasNext()) {
                checkModifications();
                T element = (T) data[index];
                index++;
                nextWasCalled = true;
                return element;
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            if (nextWasCalled) {
                checkModifications();
                System.arraycopy(data, 0, data, 0, index);
                System.arraycopy(data, index + 1, data, index, (data.length - index) - 1);
                size--;
                nextWasCalled = false;
            }
            throw new IllegalStateException();
        }

        private void checkModifications() {
            if (startVersion != version) {
                throw new ConcurrentModificationException();
            }
        }

    }

    @Override
    public String toString() {
        String start = "[";
        String end = "]";
        String string = "";

        for (int i = 0; i < size; i++) {
            string += data[i];
            if (i != size - 1) {
                string = string + ", ";
            }
        }
        return start + string + end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArraySet<?> arraySet = (ArraySet<?>) o;
        return Arrays.equals(data, arraySet.data);

    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }
}
