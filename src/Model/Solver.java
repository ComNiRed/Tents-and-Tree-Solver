package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class Solver {

    private Board board;
    private int size;
    private int[] x; //x = une ligne
    private int[] y; //y = une colonne
    private HashSet<Coord> trees;
    private HashSet<Coord> tents;
    private HashMap<Integer, HashSet<Integer>> emptyCaseX;
    private HashMap<Integer, HashSet<Integer>> emptyCaseY;

    public void init(String sizeStr) {
        /*//size = Integer.parseInt(sizeStr);
        //game = new Case[size][size];
        //x = new int[size];
        //y = new int[size];
        //createGame();

        //code en dur
        size = 10;
        game = new Case[][] {
                {Case.Empty,Case.Tree,Case.Empty,Case.Empty,Case.Empty,Case.Empty,Case.Empty,Case.Empty,Case.Tree,Case.Empty},
                {Case.Tree,Case.Empty,Case.Empty,Case.Empty,Case.Empty,Case.Tree,Case.Empty,Case.Empty,Case.Empty,Case.Empty},
                {Case.Empty,Case.Empty,Case.Empty,Case.Empty,Case.Empty,Case.Empty,Case.Empty,Case.Empty,Case.Tree,Case.Empty},
                {Case.Empty,Case.Tree,Case.Tree,Case.Empty,Case.Empty,Case.Empty,Case.Empty,Case.Empty,Case.Empty,Case.Empty},
                {Case.Empty,Case.Empty,Case.Empty,Case.Empty,Case.Empty,Case.Empty,Case.Empty,Case.Empty,Case.Empty,Case.Empty},
                {Case.Empty,Case.Tree,Case.Empty,Case.Empty,Case.Empty,Case.Tree,Case.Empty,Case.Empty,Case.Empty,Case.Empty},
                {Case.Tree,Case.Tree,Case.Empty,Case.Empty,Case.Tree,Case.Empty,Case.Tree,Case.Empty,Case.Empty,Case.Tree},
                {Case.Empty,Case.Empty,Case.Empty,Case.Empty,Case.Empty,Case.Tree,Case.Empty,Case.Empty,Case.Empty,Case.Empty},
                {Case.Tree,Case.Empty,Case.Empty,Case.Empty,Case.Empty,Case.Empty,Case.Empty,Case.Empty,Case.Tree,Case.Empty},
                {Case.Empty,Case.Empty,Case.Empty,Case.Tree,Case.Empty,Case.Empty,Case.Empty,Case.Tree,Case.Empty,Case.Tree}};
        y = new int[] {4,1,2,1,3,1,3,0,2,3};
        oy = new int[] {4,1,2,1,3,1,3,0,2,3};
        x = new int[] {3,0,2,2,0,5,0,4,0,4};
        ox = new int[] {3,0,2,2,0,5,0,4,0,4};*/
    }

    public Board solveGame(Board board) {
        // Init
        this.board = board;
        size = board.getSize();
        x = board.getX();
        y = board.getY();
        trees = new HashSet<>();
        tents = new HashSet<>();
        emptyCaseX = new HashMap<>();
        emptyCaseY = new HashMap<>();

        /*//cocher les case qui sont focement de la pelouse
        for (int i = 0; i < size; i++) for (int j = 0; j < size; j++) if(board.getCase(i,j) == Case.Empty && isGarden(i,j)) board.setCase(i,j,Case.Garden);

        //ligne 0
        for (int i = 0; i < size; i++) if(x[i] == 0) for (int j = 0; j < size; j++) if(board.getCase(i,j) == Case.Empty) board.setCase(i,j,Case.Garden);
        for (int i = 0; i < size; i++) if(y[i] == 0) for (int j = 0; j < size; j++) if(board.getCase(j,i) == Case.Empty) board.setCase(j,i,Case.Garden);

        //tree and empty case in variable
        for (int i = 0; i < size; i++) {
            emptyCaseX.put(i,new HashSet<>());
            emptyCaseY.put(i,new HashSet<>());
        }*/

        // cocher les case qui sont focement de la pelouse + ligne 0
        for (int i = 0; i < size; i++) {
            emptyCaseX.put(i,new HashSet<>());
            emptyCaseY.put(i,new HashSet<>());
            if(x[i] == 0) {
                for (int j = 0; j < size; j++) if(board.getCase(i,j) == Case.Empty) board.setCase(i,j,Case.Garden);
            } else {
                for (int j = 0; j < size; j++) if(board.getCase(i,j) == Case.Empty && isGarden(i,j)) board.setCase(i,j,Case.Garden);
            }
            if(y[i] == 0) for (int j = 0; j < size; j++) if(board.getCase(j,i) == Case.Empty) board.setCase(j,i,Case.Garden);
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(board.getCase(i,j) == Case.Tree) trees.add(new Coord(i,j));
                else if(board.getCase(i,j) == Case.Empty) {
                    emptyCaseX.get(i).add(j);
                    emptyCaseY.get(j).add(i);
                }
            }
        }

        //boolean done = true;
        while (!trees.isEmpty()/*done*/) {
            //System.out.println("-");
            //bonus : si un arbre dans coin (la tentes ne peut être que sur les 3 case d'un coin), alors la case en diagonale est garden
            //todo_ : check les cases qui ont été placée entre temps

            //1. cochez case blocante (qui rendrait impossible une autre colonne)
            //faire gaffe au double/triple case
            //faire gaffe detecter si on peut mettre tente (si on peut mettre tente => case garden autour)
            //faire gaffe des fois on sait qu'un arbre serra forcément présent sur 1 entre 2 case et du coup le reste des case peuvent devenir garden si plus d'autre place
            checkLinesEmpty();
            checkColumnsEmpty();
            
            //2. regarder ligne full (ex: 6 -> 6 places restante)
            //faire gaffe : 6 -> 7 place dont 5 sur 100% et la dernier 1 chance sur 2
            //faire gaffe au double/triple case
            checkLinesTree();
            checkColumnsTree();

            //3. check arbre 1 case
            //faire gaffe: au arbre ou la tente peut apartenir a un autre arbre
            checkLonelyTree();

            //done = false;
        }
        return board;
    }

    private void checkLinesEmpty() {
        for (int x = 0; x < size; x++) {
            //count tree for line
            //int possibilities = countPossibilitiesLine(x,new HashSet<>(emptyCaseX.get(x)));
            //System.out.println("==========");
            //System.out.println("Line : " + x + " / Possibilities : " + possibilities);

            //check for each possibility
            if(this.x[x] > 0) {
                if(x != 0)
                    for (int y : new HashSet<>(emptyCaseX.get(x-1)))
                        if(this.x[x] > getPotentialTreesPossibilitiesLine(x,y,emptyCaseX.get(x))) {
                            updateCase(x-1, y, Case.Garden);
                            //System.out.println("Placement : x = "+x+" / y = "+y);
                        }
                if(x != size-1)
                    for (int y : new HashSet<>(emptyCaseX.get(x+1)))
                        if(this.x[x] > getPotentialTreesPossibilitiesLine(x,y,emptyCaseX.get(x))) {
                            updateCase(x+1, y, Case.Garden);
                            //System.out.println("Placement : x = "+x+" / y = "+y);
                        }
            }
        }
    }

    private void checkColumnsEmpty() {
        for (int y = 0; y < size; y++) {
            //count tree for line
            //int possibilities = countPossibilitiesColumn(y,new HashSet<>(emptyCaseY.get(y)));
            //System.out.println("==========");
            //System.out.println("Column : " + y + " / Possibilities : " + possibilities);

            //check for each possibility
            if(this.y[y] > 0) {
                if(y != 0)
                    for (int x : new HashSet<>(emptyCaseY.get(y-1)))
                        if(this.y[y] > getPotentialTreesPossibilitiesColumn(x,y,emptyCaseY.get(y))) {
                            updateCase(x, y-1, Case.Garden);
                            //System.out.println("Placement : x = "+x+" / y = "+y);
                        }
                if(y != size-1)
                    for (int x : new HashSet<>(emptyCaseY.get(y+1)))
                        if(this.y[y] > getPotentialTreesPossibilitiesColumn(x,y,emptyCaseY.get(y))){
                            updateCase(x, y+1, Case.Garden);
                            //System.out.println("Placement : x = "+x+" / y = "+y);
                        }
            }
        }
    }

    private int countPossibilitiesLine(int x, HashSet<Integer> list) {
        int possibilities = 0;
        Coord passed = new Coord(-1,-1);
        Coord treeDone = new Coord(-1,-1);
        for (int y : list) {
            if(list.contains(y-1) && !new Coord(x,y-1).equals(passed) && !new Coord(x,y-1).equals(treeDone) && !new Coord(x,y-2).equals(treeDone)) {
                passed = new Coord(x,y);
            } else if(trees.contains(new Coord(x-1,y)) || trees.contains(new Coord(x+1,y))) {
                possibilities++;
            } else if(trees.contains(new Coord(x,y-1)) && !new Coord(x,y-1).equals(treeDone)) {
                possibilities++;
            } else if(trees.contains(new Coord(x,y+1))) {
                possibilities++;
                treeDone = new Coord(x,y+1);
            }
        }
        return possibilities;
    }

    private int countPossibilitiesColumn(int y, HashSet<Integer> list) {
        int possibilities = 0;
        Coord passed = new Coord(-1,-1);
        Coord treeDone = new Coord(-1,-1);
        for (int x : list) {
            if(list.contains(x-1) && !new Coord(x-1,y).equals(passed) && !new Coord(x-1,y).equals(treeDone) && !new Coord(x-2,y).equals(treeDone)) {
                passed = new Coord(x,y);
            } else if(trees.contains(new Coord(x,y-1)) || trees.contains(new Coord(x,y+1))) {
                possibilities++;
            } else if(trees.contains(new Coord(x-1,y)) && !new Coord(x-1,y).equals(treeDone)) {
                possibilities++;
            } else if(trees.contains(new Coord(x+1,y))) {
                possibilities++;
                treeDone = new Coord(x+1,y);
            }
        }
        return possibilities;
    }

    private int getPotentialTreesPossibilitiesLine(int x, int y, HashSet<Integer> emptyCase) {
        HashSet<Integer> copy = new HashSet<>(emptyCase);
        copy.remove(y-1);
        copy.remove(y);
        copy.remove(y+1);

        int count = countPossibilitiesLine(x,copy);
        //System.out.println("x = "+x+" / y = "+y+ " / Size : "+count);
        return count;
    }

    private int getPotentialTreesPossibilitiesColumn(int x, int y, HashSet<Integer> emptyCase) {
        HashSet<Integer> copy = new HashSet<>(emptyCase);
        copy.remove(x-1);
        copy.remove(x);
        copy.remove(x+1);

        int count = countPossibilitiesColumn(y,copy);
        //System.out.println("x = "+x+" / y = "+y+ " / Size : "+count);
        return count;
    }

    private void checkLinesTree() {
        //pour chaque ligne
        //  si nombre case libre == nombre todo_
        //      tente par tout
        //  si possibilities == nombre todo_
        //      si case seul (pas de double/triple/...)
        //          si arbre sur une des case a cote => tente
        //          si pas d'arbre a cote mais devant ou derriere
        //              si case de l'autre cote de l'abre = vide et arbre a coté => tente
        //      si case pas seul => compter - (pour les 2 case extrémiter) les cases qui sont contre un arbre sans U/D
        //          si paire => rien
        //          si impair => check
        //              regarder 1ere normalement puis sauter la 2ème etc.
        for (int x = 0; x < this.x.length; x++) {
            HashSet<Integer> emptyCase = new HashSet<>(emptyCaseX.get(x));
            if(this.x[x] == emptyCase.size()) {
                for (int y : emptyCase) placeTent(x, y);
            } else if(this.x[x] == countPossibilitiesLine(x, emptyCase)) {
                int begin = 0;
                for (int y : emptyCase) {
                    if(emptyCase.contains(y-1) || emptyCase.contains(y+1)) {
                        if(!emptyCase.contains(y-1) && emptyCase.contains(y+1)) {
                            begin = y;
                        } else if(emptyCase.contains(y-1) && !emptyCase.contains(y+1)) {
                            int end = y;
                            if(trees.contains(new Coord(x,begin-1)) && emptyCase.contains(begin-2) && (trees.contains(new Coord(x+1, begin-2)) || trees.contains(new Coord(x-1, begin-2)))) begin++;
                            if(trees.contains(new Coord(x,end+1)) && emptyCase.contains(end+2) && (trees.contains(new Coord(x+1, end+2)) || trees.contains(new Coord(x-1, end+2)))) end--;
                            if(end-begin+1%2 == 1) {
                                for (int i = begin; i < end; i=i+2) {
                                    if(trees.contains(new Coord(x-1, i)) || trees.contains(new Coord(x+1, i)) ||
                                            trees.contains(new Coord(x,i-1)) && (!emptyCase.contains(i-2) || trees.contains(new Coord(x+1, i-2)) || trees.contains(new Coord(x-1, i-2))) ||
                                            trees.contains(new Coord(x,i+1)) && (!emptyCase.contains(i+2) || trees.contains(new Coord(x+1, i+2)) || trees.contains(new Coord(x-1, i+2)))) {
                                        placeTent(x,i);
                                    }
                                }
                            }
                        }
                    } else if(trees.contains(new Coord(x-1, y)) || trees.contains(new Coord(x+1, y)) ||
                            trees.contains(new Coord(x,y-1)) && (!emptyCase.contains(y-2) || trees.contains(new Coord(x+1, y-2)) || trees.contains(new Coord(x-1, y-2))) ||
                            trees.contains(new Coord(x,y+1)) && (!emptyCase.contains(y+2) || trees.contains(new Coord(x+1, y+2)) || trees.contains(new Coord(x-1, y+2)))) {
                        //if lonely case => if tree up or down OR if tree right or left with a not empty case at the opposite of the tree OR if tree L or R with empty case at opposite with tree up or down
                        //amélioration possible : quand arbre L/R et pas arbre U/D, avancé jusqu'a trouver un arbre U/D (=> tente) sinon rien
                        placeTent(x,y);
                    }
                }
            }
        }
    }

    private void checkColumnsTree() {
        for (int y = 0; y < this.y.length; y++) {
            HashSet<Integer> emptyCase = new HashSet<>(emptyCaseY.get(y));
            if(this.y[y] == emptyCase.size()) {
                for (int x : emptyCase) placeTent(x, y);
            } else if(this.y[y] == countPossibilitiesColumn(y, emptyCase)) {
                int begin = 0;
                for (int x : emptyCase) {
                    if(emptyCase.contains(x-1) || emptyCase.contains(x+1)) {
                        if(!emptyCase.contains(x-1) && emptyCase.contains(x+1)) {
                            begin = x;
                        } else if(emptyCase.contains(x-1) && !emptyCase.contains(x+1)) {
                            int end = x;
                            if(trees.contains(new Coord(begin-1,y)) && emptyCase.contains(begin-2) && (trees.contains(new Coord(begin-2, y+1)) || trees.contains(new Coord(begin-2, y-1)))) begin++;
                            if(trees.contains(new Coord(end+1,y)) && emptyCase.contains(end+2) && (trees.contains(new Coord(end+2, y+1)) || trees.contains(new Coord(end+2, y-1)))) end--;
                            if((end-begin+1)%2 == 1) {
                                for (int i = begin; i < end; i=i+2) {
                                    if(trees.contains(new Coord(i, y-1)) || trees.contains(new Coord(i, y+1)) ||
                                            trees.contains(new Coord(i-1, y)) && (!emptyCase.contains(i-2) || trees.contains(new Coord(i-2, y+1)) || trees.contains(new Coord(i-2, y+1))) ||
                                            trees.contains(new Coord(i+1, y)) && (!emptyCase.contains(i+2) || trees.contains(new Coord(i+2, y+1)) || trees.contains(new Coord(i+2, y+1)))) {
                                        placeTent(i,y);
                                    }
                                }
                            }
                        }
                    } else if(trees.contains(new Coord(x, y-1)) || trees.contains(new Coord(x, y+1)) ||
                            trees.contains(new Coord(x-1,y)) && (!emptyCase.contains(x-2) || trees.contains(new Coord(x-2, y+1)) || trees.contains(new Coord(x-2, y-1))) ||
                            trees.contains(new Coord(x+1,y)) && (!emptyCase.contains(x+2) || trees.contains(new Coord(x+2, y+1)) || trees.contains(new Coord(x+2, y-1)))) {
                        placeTent(x,y);
                    }
                }
            }
        }
    }

    private void checkLonelyTree() {
        for (Coord tree : new HashSet<Coord>(trees)) {
            ArrayList<Coord> emptyCases = getEmptyCase(tree.x, tree.y);
            if(emptyCases.size() != 0 && emptyCases.size()+getAdjacentTypeCase(tree.x,tree.y,tents).size() == 1) {
                //System.out.println("Tent ( "+tent.getX()+" : "+tent.getY()+" )");
                this.placeTent(emptyCases.get(0).x,emptyCases.get(0).y);
            }
        }
    }

    private ArrayList<Coord> getEmptyCase(int x, int y) {
        //System.out.println("============ ( "+x+" : "+y+" ) ============");
        ArrayList<Coord> emptyCases = new ArrayList<>();
        if(emptyCaseY.get(y).contains(x-1)) {
            //System.out.println("X : "+ (x-1) + " ; Y: " + (y));
            emptyCases.add(new Coord(x-1,y));
        }
        if(emptyCaseY.get(y).contains(x+1)) {
            //System.out.println("X : "+ (x+1) + " ; Y: " + (y));
            emptyCases.add(new Coord(x+1,y));
        }
        if(emptyCaseX.get(x).contains(y+1)) {
            //System.out.println("X : "+ (x) + " ; Y: " + (y+1));
            emptyCases.add(new Coord(x,y+1));
        }
        if(emptyCaseX.get(x).contains(y-1)) {
            //System.out.println("X : "+ (x) + " ; Y: " + (y-1));
            emptyCases.add(new Coord(x,y-1));
        }
        return emptyCases;
    }

    private boolean isGarden(int i, int j) {
        if(i > 0 && board.getCase(i-1,j) == Case.Tree) return false;
        if(i < size -1 && board.getCase(i+1,j) == Case.Tree) return false;
        if(j > 0 && board.getCase(i,j-1) == Case.Tree) return false;
        return j >= size - 1 || board.getCase(i,j+1) != Case.Tree;
    }

    private void placeTent(int x, int y) {
        //placer tente + add in list
        updateCase(x,y,Case.Tent);
        tents.add(new Coord(x, y));

        //mettre garden autour (=> -1 dans le nombre de tente sur la ligne + colonne -> si la ligne tombe à 0 -> remplir de blanc)
        gardenAround(x,y);

        //faire un check des cases forcement vide (cas ou une tente a été placée et ducoup l'arbe a une cas a cote de lui qui n'est plus connectée a aucun arbre)
        //      verifier pour les 4 cases autour de l'arbre via les list tents/trees si il y a un arbre autour d'elle (si non => garden)
        //      attention si case garden n'a pas d'abre a cote
        //      + si infini, il faut enlever les tentes/arbre dans la boucle
        //  puis enlever garden d'un arbre pris
        ArrayList<ArrayList<Coord>> pile = new ArrayList<>();
        Coord currentCoord = new Coord(x, y);
        Coord origin = currentCoord;
        HashSet<Coord> alreadyDone = new HashSet<>();

        //System.out.println("======");
        //System.out.println("Départ : x = "+x+" / y = "+y);

        do {
            //System.out.println("x = "+currentCoord.x+" / y = "+currentCoord.y);
            alreadyDone.add(currentCoord);
            ArrayList<Coord> currentCoordList = getAdjacentTypeCase(currentCoord.x, currentCoord.y, (tents.contains(currentCoord)) ? trees : tents);
            currentCoordList.removeAll(alreadyDone);
            if(currentCoordList.size() > 0) {
                pile.add(currentCoordList);
                if(pile.size() > 1) if(pile.get(pile.size()-2).size() == 1) pile.remove(pile.size()-2); else pile.get(pile.size()-2).remove(0);
            } else {
                //System.out.println("End : x = "+currentCoord.x+" / y = "+currentCoord.y);
                // ========================
                //si arbre pas seul => pas boucle
                //tant que arrive pas au point de départ ou que l'on arrive pas a un embranchement a minimum 2
                //  si tente => association avec l'abre a cote de lui
                //  si arbre seul => association avec la tente

                //System.out.println("\tx = "+currentCoord.x+" / y = "+currentCoord.y);
                Coord dCurrentCoord = currentCoord;

                Case currType = (tents.contains(dCurrentCoord)) ? Case.Tent : Case.Tree;
                ArrayList<Coord> dCurrentCoordList = getAdjacentTypeCase(dCurrentCoord.x, dCurrentCoord.y, (currType == Case.Tent) ? trees : tents);
                if(dCurrentCoordList.size() > 0) {

                    if(!getEmptyCase(dCurrentCoord.x, dCurrentCoord.y).isEmpty()) {
                        dCurrentCoord = origin;
                        currType = Case.Tent;
                        dCurrentCoordList = getAdjacentTypeCase(dCurrentCoord.x, dCurrentCoord.y, trees);
                    }

                    if(dCurrentCoordList.size() == 2) dCurrentCoordList.remove(origin);

                    if(dCurrentCoordList.size() == 1) {

                        do {
                            //System.out.println("\tx = "+dCurrentCoord.x+" / y = "+dCurrentCoord.y);
                            if(tents.contains(dCurrentCoord)) {
                                tents.remove(dCurrentCoord);
                                trees.remove(dCurrentCoordList.get(0));
                                //System.out.println("\t=> Association: "+dCurrentCoord.x+"/"+dCurrentCoord.y+ " - "+dCurrentCoordList.get(0).x+"/"+dCurrentCoordList.get(0).y);
                            } else if(trees.contains(dCurrentCoord)) {
                                tents.remove(dCurrentCoordList.get(0));
                                trees.remove(dCurrentCoord);
                                //System.out.println("\t=> Association: "+dCurrentCoord.x+"/"+dCurrentCoord.y+ " - "+dCurrentCoordList.get(0).x+"/"+dCurrentCoordList.get(0).y);
                            }
                            currType = (currType == Case.Tree) ? Case.Tent : Case.Tree;
                            dCurrentCoord = dCurrentCoordList.get(0);
                            dCurrentCoordList = getAdjacentTypeCase(dCurrentCoord.x, dCurrentCoord.y, (currType == Case.Tent) ? trees : tents);

                        } while(dCurrentCoordList.size() == 1 && !dCurrentCoord.equals(origin));
                        if(currType == Case.Tree && !trees.contains(dCurrentCoord)) {
                            ArrayList<Coord> ec = getEmptyCase(dCurrentCoord.x, dCurrentCoord.y);
                            if(!ec.isEmpty() && getAdjacentTypeCase(ec.get(0).x, ec.get(0).y, trees).isEmpty()) {
                                updateCase(ec.get(0).x, ec.get(0).y, Case.Garden);
                                //System.out.println("\t+G");
                            }
                        }
                    }
                }

                // ========================
                pile.get(pile.size()-1).remove(0);
                if(pile.get(pile.size()-1).size() == 0) pile.remove(pile.size()-1);
            }
            if(!pile.isEmpty()) currentCoord = pile.get(pile.size()-1).get(0);
        } while(!pile.isEmpty());

    }

    private ArrayList<Coord> getAdjacentTypeCase(int x, int y, HashSet<Coord> list) {
        ArrayList<Coord> caseList = new ArrayList<>();
        if(list.contains(new Coord(x-1,y))) caseList.add(new Coord(x-1,y));
        if(list.contains(new Coord(x+1,y))) caseList.add(new Coord(x+1,y));
        if(list.contains(new Coord(x,y+1))) caseList.add(new Coord(x,y+1));
        if(list.contains(new Coord(x,y-1))) caseList.add(new Coord(x,y-1));
        return caseList;
    }

    private void gardenAround(int x, int y) {
        if(emptyCaseY.get(y).contains(x-1)) updateCase(x-1,y, Case.Garden);
        if(emptyCaseY.get(y).contains(x+1)) updateCase(x+1,y, Case.Garden);
        if(emptyCaseX.get(x).contains(y-1)) updateCase(x,y-1, Case.Garden);
        if(emptyCaseX.get(x).contains(y+1)) updateCase(x,y+1, Case.Garden);
        if(x != 0) {
            if(emptyCaseX.get(x-1).contains(y-1)) updateCase(x-1,y-1, Case.Garden);
            if(emptyCaseX.get(x-1).contains(y+1)) updateCase(x-1,y+1, Case.Garden);
        }
        if(x != size -1) {
            if(emptyCaseX.get(x+1).contains(y-1)) updateCase(x+1,y-1, Case.Garden);
            if(emptyCaseX.get(x+1).contains(y+1)) updateCase(x+1,y+1, Case.Garden);
        }
    }

    private void updateCase(int x, int y, Case typeCase) {
        //update
        board.setCase(x,y,typeCase);
        //System.out.println("Placement: x = "+x+" / y = "+y+" - "+typeCase.toString());
        //enlever case de x et y
        emptyCaseX.get(x).remove(y);
        emptyCaseY.get(y).remove(x);

        //-1 dans le nombre de tente sur la ligne + colonne -> si la ligne tombe à 0 -> remplir de garden
        if(typeCase == Case.Tent) {
            this.x[x]--;
            if(this.x[x] == 0) this.emptyX(x);
            this.y[y]--;
            if(this.y[y] == 0) this.emptyY(y);
        }
    }

    private void emptyX(int x) {
        for (int y: new HashSet<>(emptyCaseX.get(x))) updateCase(x,y,Case.Garden);
    }

    private void emptyY(int y) {
        for (int x: new HashSet<>(emptyCaseY.get(y))) updateCase(x,y,Case.Garden);
    }

}

class Coord {
    public int x;
    public int y;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coord coord = (Coord) o;
        return x == coord.x && y == coord.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
