
public class Bomb {
    private int position;
    private boolean detonated = false;
    private boolean isPlaced = false; //used to check if bomb has a position. Maybe not necessary?
                                      //Better to reset bomb position?

public Bomb(){
}
    public void setPosition(int position) {
        this.position = position;
    }
    public int getPosition() {
        return position;
    }
    public void setDetonated(boolean detonated) {
        this.detonated = detonated;
    }
    public boolean getDetonated(){
    return detonated;
    }
    public void setPlaced(boolean placed) {
        this.isPlaced = placed;
    }
    public boolean getIfPlaced(){
    return isPlaced;
    }

    public void placeBomb(Troop troop){
        if (troop.getInventory() != null) { //makes sure you can't place a bomb if you have none in inventory
            int position = troop.getPosition();
            PlayerId playerId = troop.getPlayerId();
            //Makes sure you can't place a bomb on your own side of the front
            if ((playerId == PlayerId.PLAYER && position < 0) ||
                    (playerId == PlayerId.ENEMY && position > 0)) {
                setPosition(position);
                setPlaced(true);
                troop.removeBomb();  //removes bomb from inventory

                UI.printString("Bomb placed at field: " + getPosition());
            }
        }
}

    public void detonateBomb(Troop troop, Troop adversary){
        switch (getPosition()){
            case -10, 10 -> {adversary.setSoldiers(0);  //if bomb is in camp then all soldiers dead
                UI.printString("CAMP DESTROYED\n");}
            default -> {
        int enemyDistance = Battlefield.getDistance(adversary.getPosition(),getPosition());
        System.out.println(enemyDistance);
        int bombDistance = Battlefield.getDistance(troop.getPosition(), getPosition());
        if (troop.getBomb().getIfPlaced() && bombDistance > 6) {
            //if bomb is placed and your distance to the bomb is at least 6 fields, proceed
            if ((troop.getPlayerId() == PlayerId.PLAYER && troop.getPosition() > 0) ||
                    (troop.getPlayerId() == PlayerId.ENEMY && troop.getPosition() < 0)) {
                // ensures that you can only detonate the bomb if you are on your side of the front line
                int deadSoldiers = Math.max(10 - enemyDistance, 0); //kills soldiers based on distance.
                if (deadSoldiers > 0) UI.printString(troop.getPlayerId() + " BOMB DETONATED! " + deadSoldiers + " soldier(s) fallen" );
                else UI.printString("MISS!");
                adversary.setSoldiers(adversary.getSoldiers() - deadSoldiers);
                setDetonated(true);
                setPlaced(false); //resets bomb
            }
        }}
}}

}
