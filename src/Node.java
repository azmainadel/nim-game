
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node {

    private List<Integer> piles;

    private int value;

    private List<Node> childList;

    public Node(List<Integer> piles) {
        this.piles = piles;
        this.childList = new ArrayList<>();
    }

    public Node(Integer... piles) {
        this.piles = new ArrayList<>(Arrays.asList(piles));
        this.childList = new ArrayList<>();
    }

    public Node(Node parent, List<Integer> piles) {
        parent.childList.add(this);
        this.piles = new ArrayList<>(piles);
        this.childList = new ArrayList<>();
    }

    public List<Integer> getPiles() {
        return new ArrayList<>(piles);
    }

    public List<Node> getChildList() {
        return childList;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isEmpty() {
        return piles.stream().mapToInt(i -> i).sum() == 0;
    }

    public String getPileState(){
        return piles.toString();
    }

    @Override
    public String toString() {
        return piles.toString() + " [value = " + value + " ] ";
    }

}
