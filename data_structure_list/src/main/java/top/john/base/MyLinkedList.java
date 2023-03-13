package top.john.base;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author by RacerZ
 * @date 2023/3/12.
 * 底层用双链表实现
 */
public class MyLinkedList<E> implements Iterable<E> {

    // 虚拟头尾节点
    final private Node<E> head, tail;

    private int size;

    // 双链表节点
    private static class Node<E> {
        E val;
        Node<E> next;
        Node<E> prev;

        public Node(E val) {
            this.val = val;
        }
    }

    // 构造函数初始化首尾节点
    public MyLinkedList() {
        this.head = new Node<>(null);
        this.tail = new Node<>(null);
        head.next = tail;
        tail.prev = head;

        this.size = 0;
    }

    // 增
    public void addLast(E e) {
        Node<E> x = new Node<>(e);
        Node<E> temp = tail.prev;

        temp.next = x;
        tail.prev = x;

        x.next = tail;
        x.prev = temp;

        size++;
    }

    public void addFirst(E e) {
        Node<E> x = new Node<>(e);
        Node<E> temp = head.next;

        temp.prev = x;
        x.next = temp;

        head.next = x;
        x.prev = head;

        size++;
    }

    public void add(int index, E element) {
        checkPositionIndex(index);

        if (index == size) {
            addLast(element);
            return;
        }

        // 找到 index 对应的 Node
        Node<E> p = getNode(index);
        Node<E> temp = p.prev;

        Node<E> x = new Node<>(element);

        p.prev = x;
        x.next = p;

        x.prev = temp;
        temp.next = x;

        size++;
    }

    // 删
    public E removeFirst() {
        if (size < 1) {
            throw new NoSuchElementException();
        }
        Node<E> x = head.next;
        Node<E> temp = x.next;

        head.next = temp;
        temp.prev = head;

        x.prev = null;
        x.next = null;

        size--;
        return x.val;
    }

    public E removeLast() {
        if (size < 1) {
            throw new NoSuchElementException();
        }
        Node<E> x = tail.prev;
        Node<E> temp = x.prev;

        tail.prev = temp;
        temp.next = tail;

        x.prev = null;
        x.next = null;

        size--;
        return x.val;
    }

    public E remove(int index) {
        checkElementIndex(index);
        // 先定位目标节点
        Node<E> x = getNode(index);
        Node<E> prev = x.prev;
        Node<E> next = x.next;

        prev.next = next;
        next.prev = prev;

        x.prev = x.next = null;

        size--;
        return x.val;
    }

    // 查
    public E get(int index) {
        checkElementIndex(index);

        Node<E> p = getNode(index);

        return p.val;
    }

    public E getFirst() {
        if (size < 1) {
            throw new NoSuchElementException();
        }

        return head.next.val;
    }

    public E getLast() {
        if (size < 1) {
            throw new NoSuchElementException();
        }

        return tail.prev.val;
    }

    // 改
    public E set(int index, E val) {
        checkElementIndex(index);

        Node<E> p = getNode(index);

        E oldVal = p.val;
        p.val = val;

        return oldVal;
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

    // tool
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private Node<E> getNode(int index) {

        Node<E> p = null;
        // TODO: 优化，通过 index 判断从 head 还是 tail 开始遍历
        if (index <= size / 2) {
            p = head.next;
            for (int i = 0; i < index; i++) {
                p = p.next;
            }
        } else {
            p = tail.prev;
            for(int i = size - 1; i > index; i--) {
                p = p.prev;
            }
        }

        return p;
    }

    private void display() {
        System.out.println("Size = " + size);
        for (Node<E> p = head.next; p != tail; p = p.next) {
            System.out.print(p.val + " -> ");
        }
        System.out.println("null");
        System.out.println();
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Node<E> p = head.next;

            @Override
            public boolean hasNext() {
                return p != tail;
            }

            @Override
            public E next() {
                E val = p.val;
                p = p.next;
                return val;
            }
        };
    }

    public static void main(String[] args) {
        MyLinkedList<Integer> arr = new MyLinkedList<>();

        for (int i = 0; i < 5; i++) {
            arr.addLast(i);
        }

        arr.remove(3);
        arr.add(1, 9);
        arr.addFirst(100);
        int val = arr.removeLast();

        System.out.println(arr.get(2));
        System.out.println(arr.get(4));

        arr.display();

    }
}
