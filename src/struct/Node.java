package struct;

public class Node {
    private double []p;
    private Node left;
    private Node right;

    public void setP(double p[]){
        this.p = p;
    }

    public void setLeft(Node left){
        this.left = left;
    }

    public void setRight(Node right){
        this.right = right;
    }

    public double [] getP(){
        return p;
    }

    public Node getLeft(){
        return left;
    }

    public Node getRight(){
        return right;
    }
}
