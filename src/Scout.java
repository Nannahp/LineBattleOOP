public class Scout {

    //Looks for enemy, 2 fields ahead or 3 fields behind
    public void scout(Troop troop, Troop adversary){
        int distanceToEnemy = troop.getPosition()-adversary.getPosition();
        if (distanceToEnemy >0 && distanceToEnemy <= 2){
            UI.printString("Enemy is " +distanceToEnemy + " field(s) ahead!");
        } else if (distanceToEnemy <0 && distanceToEnemy >= -3) {
            UI.printString("Enemy is " +distanceToEnemy + " field(s) behind us!");
            
        }
    }
}
