package top.john.base;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author by RacerZ
 * @date 2023/3/12.
 */
public class MyArrayList<E> implements Iterable<E> {

    // 内部数组
    private E[] data;

    // 元素个数
    private int size;

    // 默认初始数量
    private static final int INIT_CAP = 1;

    public MyArrayList() {
        this(INIT_CAP);
    }

    public MyArrayList(int initCapacity) {
        data = (E[]) new Object[initCapacity];
        size = 0;
    }

    // 增
    public void addLast(E e) {
        int cap = data.length;
        // 判断容量
        if (size == cap) {
            resize(2*cap);
        }

        // 尾部插入数据
        data[size] = e;
        size ++;

    }

    public void add(int index, E e) {
        // 检查索引范围
        checkPositionIndex(index);

        int cap = data.length;

        // 判断容量
        if (size == cap) {
            resize(2 * cap);
        }

        // 搬移数据
        System.arraycopy(data, index, data, index + 1, size - index);

        // 插入
        data[index] = e;
        size++;
    }

    public void addFirst(E e) {
        add(0, e);
    }

    // 删
    public E removeLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        int cap = data.length;
        // 缩容
        if (size == cap / 4) {
            resize(cap / 2);
        }

        E deletedVal = data[size - 1];
        size --;

        return deletedVal;
    }

    public E remove(int index) {
        // 检查边界
        checkElementIndex(index);

        int cap = data.length;

        // 缩容
        if (size == cap / 4) {
            resize(cap / 2);
        }

        E deletedVal = data[index];

        System.arraycopy(data, index + 1, data, index, size - index - 1);

        data[size - 1] = null;
        size --;

        return deletedVal;
    }

    public E removeFirst() {
        return remove(0);
    }

    // 查
    public E get(int index) {
        // 检查边界
        checkElementIndex(index);

        return data[index];
    }

    // 改
    public E set(int index, E element) {
        // 检查边界
        checkElementIndex(index);
        // 修改
        E oldVal = data[index];
        data[index] = element;
        return oldVal;
    }

    // 扩容
    private void resize(int newCap) {
        if (size > newCap) {
            return;
        }
        E[] temp = (E[]) new Object[newCap];

        System.arraycopy(data, 0, temp, 0, size);

        data = temp;
    }

    // tool
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /*
    * 检查 index 索引位置是否可以存在元素
    */
    private void checkElementIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    /*
     * 检查 index 索引位置是否可以添加元素
     */
    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int p = 0;

            @Override
            public boolean hasNext() {
                return p != size;
            }

            @Override
            public E next() {
                return data[p++];
            }
        };
    }

    private void display() {
        System.out.println("size = " + size + " cap = " + data.length);
        System.out.println(Arrays.toString(data));
    }

    public static void main(String[] args) {
        MyArrayList<Integer> arr = new MyArrayList<>(3);

        for (int i = 0; i < 5; i++) {
            arr.addLast(i);
        }

        arr.remove(3);
        arr.add(1, 9);
        arr.addFirst(100);
        int val = arr.removeLast();

        for (int i = 0; i < arr.size(); i++) {
            System.out.println(arr.get(i));
        }
        arr.display();
    }
}
