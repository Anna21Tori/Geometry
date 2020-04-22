package struct;

public class KDTree {
    private final int k=2;
    private Node root;
    private double minDisc = -1;
    private Node neighbour = null;

    public boolean _search(double p[], int d){
        return search(root, p, 0);
    }

    public void _insert(double p[], int d){
        root = insert(root,p,d);
    }

    public Node _findNearest(double tab[]){
        minDisc = -1;
        neighbour = null;
        Node x = find(root, tab,0);
        if(x != null) {
            findNearest(root, x);
            if (neighbour != null)
                return neighbour;
        }
        return null;

    }

    public void print(){
        printInorder(root);
    }

    private Node insert(Node root, double p[], int d)
    {
        if (root == null){
            root = new Node();
            root.setP(p);
            root.setLeft(null);
            root.setRight(null);
            return root;
        }
        int m = d % k;
        if (p[m] < root.getP()[m])
            root.setLeft(insert(root.getLeft(), p, d + 1));
        else
            root.setRight(insert(root.getRight(), p, d + 1));

        return root;
    }

    private boolean search(Node root, double p[], int d)
    {
        if (root == null)
            return false;
        if (equalPoints(root.getP(), p))
            return true;
        int m = d % k;
        if (p[m] < root.getP()[m])
            return search(root.getLeft(), p, d + 1);
        return search(root.getRight(), p, d + 1);
    }

    private Node find(Node root, double p[], int d)
    {
        if (root == null || equalPoints(root.getP(), p))
            return root;
        int m = d % k;
        if (p[m] < root.getP()[m])
            return find(root.getLeft(), p, d + 1);
        return find(root.getRight(), p, d + 1);
    }

    private boolean equalPoints(double p1[], double p2[]){
        for (int i = 0; i < k; ++i)
            if (p1[i] != p2[i])
                return false;
        return true;
    }

    private void printInorder(Node root) {
        if (root == null) {
            return;
        }
        printInorder(root.getLeft());
        System.out.print("{"+root.getP()[0] +","+root.getP()[1]+"} ");
        printInorder(root.getRight());
    }

    private double distance(double[] p1, double[] p2, int d)
    {
        return Math.pow((p1[0] - p2[0]),2) + Math.pow((p1[1] - p2[1]),2);
    }

    private void findNearest(Node root, Node x)
    {
        if (root == null)
            return;

        if(!equalPoints(x.getP(),root.getP())) {
            double pom = distance(x.getP(), root.getP(), 2);
            if (minDisc == -1)
                minDisc = pom;
            else if (pom < minDisc) {
                minDisc = pom;
                neighbour = root;
            }
        }

        findNearest(root.getLeft(),x);
        findNearest(root.getRight(),x);
    }


}

