import java.util.Comparator;
import java.util.Iterator;

public class MyList<T> implements Iterable<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elements;
    private int size;

    public MyList() {
        this.elements = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public void add(T element) {
        if (size == elements.length) {
            resize();
        }
        elements[size++] = element;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (T) elements[index];
    }

    public int size() {
        return size;
    }

    public boolean contains(T element) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(element)) {
                return true;
            }
        }
        return false;
    }

    public void set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        elements[index] = element;
    }

    private void resize() {
        Object[] newElements = new Object[elements.length * 2];
        System.arraycopy(elements, 0, newElements, 0, size);
        elements = newElements;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public T next() {
                return get(currentIndex++);
            }
        };
    }

    // Добавляем метод sort
    public void sort(Comparator<T> comparator) {
        for (int i = 1; i < size; i++) {
            T current = get(i); // Текущий элемент для вставки
            int j = i - 1;

            // Сдвигаем элементы в отсортированной части списка вправо,
            // пока не найдем позицию для вставки текущего элемента
            while (j >= 0 && comparator.compare(get(j), current) > 0) {
                set(j + 1, get(j)); // Сдвигаем элемент вправо
                j--;
            }

            // Вставляем текущий элемент в правильную позицию
            set(j + 1, current);
        }
    }
}