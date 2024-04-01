//Стартовый класс Main
public class Main {
    public static void main(String[] args) {

        //Создаем новое дерево
        Tree tree = new Tree();

        //Наполняем дерево узлами
        for (int i = 1 ; i <= 10 ; i++) {
            tree.insert(i);
        }
    }
}
