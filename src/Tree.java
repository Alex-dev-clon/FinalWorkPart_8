//Класс левостороннего красно-чёрного дерева
public class Tree {

    //Корень дерева
    Node root;

    //Внутренний класс дерева Node "Узел". Содержит значение value, ссылки на дочерний левый узел leftChild и
    //дочерний правый узел rightChild, а также цвет RED или BLACK
    private static class Node {
        int value;
        Node leftChild;
        Node rightChild;
        Color color;
    }

    //Перечисление Color. RED или BLACK
    enum Color {
        BLACK,
        RED
    }

    //Метод вставки с public доступом вызовом балансировки и перекрашиванием
    //корня в чёрный цвет даже если корень стал красным
    public void insert(int value) {
        if (root != null) {
            insert(root, value);
            root = balance(root);
        } else {
            root = new Node();
            root.value = value;
        }
        root.color = Color.BLACK;
    }

    //Private метод вставки с вызовом метода балансировки и присваивания цвета RED новым узлам
    private void insert(Node node, int value) {
        if (node.value != value) {
            if (node.value < value) {
                if (node.rightChild == null) {
                    node.rightChild = new Node();
                    node.rightChild.value = value;
                    node.rightChild.color = Color.RED;
                } else {
                    insert(node.rightChild, value);
                    node.rightChild = balance(node.rightChild);
                }
            } else {
                if (node.leftChild == null) {
                    node.leftChild = new Node();
                    node.leftChild.value = value;
                    node.leftChild.color = Color.RED;
                } else {
                    insert(node.leftChild, value);
                    node.leftChild = balance(node.leftChild);
                }
            }
        }
    }

    //Public метод поиска значения
    public Node find(int value) {
        return find(root, value);
    }

    //Private метод поиска значения рекурсией
    private Node find(Node node, int value) {
        if (node == null) {
            return null;
        }
        if (node.value == value) return node;
        if (node.value < value) {
            return find(node.rightChild, value);
        } else return find(node.leftChild, value);
    }

    //Метод левого поворота. Несмотря на название, поворачивает узлы в правую сторону, поскольку метод подразумевает
    //ротацию с левым потомком переданного узла
    private Node leftRotate(Node node) {
        Node cur = node.rightChild;
        node.rightChild = cur.leftChild;
        cur.leftChild = node;
        cur.color = node.color;
        node.color = Color.RED;
        return cur;
    }

    //Метод правого поворота. Несмотря на название, поворачивает узлы в левую сторону, поскольку метод подразумевает
    //ротацию с правым потомком переданного узла
    private Node rightRotate(Node node) {
        Node cur = node.leftChild;
        node.leftChild = cur.rightChild;
        cur.rightChild = node;
        cur.color = node.color;
        node.color = Color.RED;
        return cur;
    }

    //Метод смены цвета у переданного Node и смена цвета у его потомков на чёрный
    private void swapColors(Node node) {
        node.color = (node.color == Color.RED ? Color.BLACK : Color.RED);
        node.leftChild.color = Color.BLACK;
        node.rightChild.color = Color.BLACK;
    }

    //Метод балансировки
    private Node balance(Node node) {
        boolean flag;
        Node currentNode = node;
        do {
            flag = false;

            //Если правый дочерний элемент красный, а левый черный, то применяем малый правый поворот leftRotate
            if (currentNode.rightChild != null && currentNode.rightChild.color == Color.RED && (currentNode.leftChild == null || currentNode.leftChild.color == Color.BLACK)) {
                currentNode = leftRotate(currentNode);
                flag = true;
            }

            //Если левый дочерний элемент красный и его левый дочерний элемент тоже красный, то применяем малый левый поворот rightRotate
            if (currentNode.leftChild != null && currentNode.leftChild.color == Color.RED && currentNode.leftChild.leftChild != null && currentNode.leftChild.leftChild.color == Color.RED) {
                currentNode = rightRotate(currentNode);
                flag = true;
            }

            //Если оба дочерних элемента красные, то делаем смену цвета swapColors
            if (currentNode.leftChild != null && currentNode.leftChild.color == Color.RED && currentNode.rightChild != null && currentNode.rightChild.color == Color.RED) {
                swapColors(currentNode);
                flag = true;
            }
        } while (flag);
        return currentNode;
    }
}





















