import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Nim {

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private static Node state;

    public Nim(Node state) {
        this.state = state;
    }

    public static void main(String[] args) {
        Node node = new Node(1, 2, 4);

        Nim nim = new Nim(node);

        nim.start();

    }

    private static List<Node> generateChildNodes(Node node) {

        List<Node> childNodesList = new ArrayList<>();

        if (node.isEmpty()) {
            return Collections.emptyList();
        }

        List<Integer> piles = node.getPiles();

        for (int i = 0; i < piles.size(); i++) {
            List<Integer> temp = node.getPiles();

            while (temp.get(i) > 0) {
                temp.set(i, temp.get(i) - 1);
                Node child = new Node(node, temp);
                childNodesList.add(child);
            }
        }
        return childNodesList;
    }

    private static int minimax(Node node,  boolean maximizingPlayer) {

        if (node.isEmpty()) {
            return (maximizingPlayer ? -1 : 1);
        }

        if (maximizingPlayer) {
            int bestValue = Integer.MIN_VALUE;

            for (Node child : generateChildNodes(node)) {
                int value = minimax(child,  false);
                child.setValue(value);

                if (value > bestValue) {
                    bestValue = value;
                }
                node.setValue(bestValue);
            }
            return bestValue;

        } else {
            int bestValue = Integer.MAX_VALUE;

            for (Node child : generateChildNodes(node)) {
                int value = minimax(child,  true);
                child.setValue(value);

                if (value < bestValue) {
                    bestValue = value;
                }
                node.setValue(bestValue);
            }
            return bestValue;
        }
    }




    private static int minimaxWithAlphaBeta(Node node, int alpha, int beta, boolean maximizingPlayer) {
        if (node.isEmpty()) {
            return (maximizingPlayer ? -1 : 1);
        }

        if (maximizingPlayer) {
            int bestValue = Integer.MIN_VALUE;

            for (Node child : generateChildNodes(node)) {
                int value = minimaxWithAlphaBeta(child, alpha, beta, false);
                child.setValue(value);

                alpha = Math.max(alpha,value);
                if(alpha >= beta){
                    break;
                }

                if (value > bestValue) {
                    bestValue = value;
                }
                node.setValue(bestValue);
            }
            return bestValue;

        } else {
            int bestValue = Integer.MAX_VALUE;

            for (Node child : generateChildNodes(node)) {
                int value = minimaxWithAlphaBeta(child, alpha, beta, true);
                child.setValue(value);

                beta = Math.min(beta,value);
                if(alpha >= beta){
                    break;
                }

                if (value < bestValue) {
                    bestValue = value;
                }
                node.setValue(bestValue);
            }
            return bestValue;
        }
    }


    public void start() {
        boolean userMove = true;

        minimax(state,false);
//        minimaxWithAlphaBeta(state,Integer.MIN_VALUE,Integer.MAX_VALUE,false);


        while (true) {
            System.out.println("...................");
            System.out.println(state);

            System.out.println(state.getChildList());
            System.out.println("...................");


            if (userMove) {
                userMove();
            } else {
                comupterMove();
            }
            if (state.isEmpty()) {
                System.out.println("............" + (userMove ? "User" : "Computer") + " wins" + "............");
                return;
            }
            userMove = !userMove;
            System.out.println();

        }
    }

    private void userMove() {
        System.out.println("-------User move-------");
        System.out.println("Current state: " + state);

        Node temp = move();
        for(Node iter: state.getChildList()){

            //System.out.println(iter.getPileState()+ "   "+temp.getPileState());
            if(iter.getPileState().equals(temp.getPileState())){
                state=iter;
                break;
            }
        }
        if(state.getValue()==0){
            minimaxWithAlphaBeta(state,Integer.MIN_VALUE,Integer.MAX_VALUE,true);
        }
        System.out.println("After move: " + state);
    }

    private void comupterMove() {
        System.out.println("-------Computer move-------");
        System.out.println("Current state: " + state);

        boolean perfectMoveFound=false;

        for(Node iter: state.getChildList()){

            if(iter.getValue()==1){
                state=iter;
                perfectMoveFound=true;
            }
        }
        if(perfectMoveFound==false){

            state=state.getChildList().get(0);
        }

        System.out.println("After move: " + state);
    }

    private Node move() {
        while (true) {
            int pile = readNum("Choose pile: ") - 1;
            List<Integer> piles = state.getPiles();

            //System.out.println(pile+" check "+piles.size());

            if (pile < 0 || piles.size() < pile) {
                System.out.println("Invalid pile. Choose again.");
                continue;
            }

            int amount = readNum("Pick stones from the pile: ");
            if (amount <= 0 || piles.get(pile) == 0 || piles.get(pile) < amount) {
                System.out.println("Invalid.");
                continue;
            }

            piles.set(pile, piles.get(pile) - amount);
            return new Node(piles);
        }
    }

    private int readNum(String text) {
        boolean finished = false;
        int result = 0;

        while (!finished) {
            try {
                System.out.println(text);
                result = Integer.parseInt(reader.readLine());
                finished = true;
            } catch (Exception e) {
                System.out.println("Invalid character");
            }
        }
        return result;
    }
}