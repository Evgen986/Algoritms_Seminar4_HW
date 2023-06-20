import java.util.ArrayList;
import java.util.List;

public class RBTree {

    private Node root;

    /**
     * Добавление значения в дерево
     * @param value
     * @return
     */
    public boolean add(int value){
        if (root != null){
            boolean result = addNode(root, value);
            root = rebalance(root);
            root.color = Color.BLACK;
            return result;
        }else{
            root = new Node();
            root.color = Color.BLACK;
            root.value = value;
            return true;
        }
    }


    /**
     * Добавление ноды в дерево
     * @param node
     * @param value
     * @return
     */
    private boolean addNode(Node node, int value){
        if (node.value == value){
            return false;
        }else{
            if (node.value > value) {
                if (node.leftChild != null) {
                    boolean result = addNode(node.leftChild, value);
                    node.leftChild = rebalance(node.leftChild);
                    return result;
                } else {
                    node.leftChild = new Node();
                    node.leftChild.color = Color.RED;
                    node.leftChild.value = value;
                    return true;
                }
            }else{
                if (node.rightChild != null){
                    boolean result = addNode(node.rightChild, value);
                    node.rightChild = rebalance(node.rightChild);
                    return result;
                }else{
                    node.rightChild = new Node();
                    node.rightChild.color = Color.RED;
                    node.rightChild.value = value;
                    return true;
                }
            }
        }
    }

    /**
     * Ребаланс дерева
     * @param node
     * @return
     */
    private Node rebalance(Node node){
        Node result = node;
        boolean needRebalance;
        do{
            needRebalance = false;
            if(result.rightChild != null && result.rightChild.color == Color.RED &&
                    (result.leftChild == null || result.leftChild.color == Color.BLACK)){
                needRebalance = true;
                result = rightSwap(result);
            }
            if(result.leftChild != null && result.leftChild.color == Color.RED &&
                    result.leftChild.leftChild != null && result.leftChild.leftChild.color == Color.RED){
                needRebalance = true;
                result = leftSwap(result);
            }
            if (result.leftChild != null && result.leftChild.color == Color.RED &&
                    result.rightChild != null && result.rightChild.color == Color.RED){
                needRebalance = true;
                colorSwap(result);
            }
        }
        while (needRebalance);
        return result;
    }


    /**
     * Правый поворот
     * @param node
     * @return
     */
    private Node rightSwap(Node node){
        Node rightChild = node.rightChild;
        Node betweenChild = rightChild.leftChild;
        rightChild.leftChild = node;
        node.rightChild = betweenChild;
        rightChild.color = node.color;
        node.color = Color.RED;
        return rightChild;
    }

    /**
     * Левый поворот
     * @param node
     * @return
     */
    private Node leftSwap(Node node){
        Node leftChild = node.leftChild;
        Node betweenChild = leftChild.rightChild;
        leftChild.rightChild = node;
        node.leftChild = betweenChild;
        leftChild.color = node.color;
        node.color = Color.RED;
        return leftChild;
    }

    /**
     * Смена цвета
     * @param node
     */
    private void colorSwap(Node node){
        node.rightChild.color = Color.BLACK;
        node.leftChild.color = Color.BLACK;
        node.color = Color.RED;
    }


    /**
     * Общее количество нод в дереве
     * @return
     */
    public int counter() {
        int counter = 0;
        if (root != null) {
            List<Node> line = new ArrayList<>();
            line.add(root);
            while (line.size() > 0) {
                List<Node> nextLine = new ArrayList<>();
                for (Node node : line) {
                    if (node != null) {
                        counter++;
                    }
                    if (node.leftChild != null) nextLine.add(node.leftChild);
                    if (node.rightChild != null) nextLine.add(node.rightChild);
                }
                line = nextLine;
            }
            return counter;
        }
        return -1;
    }

    private class Node {
        private int value;
        private Color color;
        private Node leftChild;
        private Node rightChild;

    }
    private enum Color {
        RED, BLACK
    }
}