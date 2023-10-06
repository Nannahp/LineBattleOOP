public class Troop {

    private final PlayerId playerId;
    private int firePower;
    private int soldiers;
    private Bomb bomb = new Bomb();
    private Bomb[] inventory = new Bomb[1];
    private int position;
    private Scout scout = new Scout();

    //Creates a troop and places them outside the field to
    public Troop(PlayerId playerId) {
        this.playerId = playerId;
        setSoldiers(25);
        setFirePower(2500);
        inventory[0] = bomb;
        switch (playerId) {
            case PLAYER -> this.position = 11; //Start outside the battle-line
            case ENEMY -> this.position = -11;
        }
    }

    public PlayerId getPlayerId() {
        return playerId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        switch (playerId) {   //Should not be able to move past the limit of the line
            case PLAYER -> this.position = Math.min((this.position + position), 10);
            case ENEMY -> this.position = Math.max((this.position + position), -10);
        }
    }

    public void setSoldiers(int soldiers) {
        this.soldiers = soldiers;
    }
    public int getSoldiers(){
        return soldiers;
    }

    public Bomb getBomb() {
        return bomb;
    }

    public Bomb getInventory() {
        return inventory[0];
    }

    public void setFirePower(int firePower) {
        this.firePower = firePower;
    }

    public int getFirePower() {
        return firePower;
    }

    public Scout getScout() {
        return scout;
    }

    public void moveForward(Die dice) {
        int roll = (dice.rollDice() % 2 == 0) ? 2 : 1; //number of fields determined by even or odd roll
        switch (playerId) {
            case PLAYER -> {
                setPosition(-roll);
                UI.printString("Player moves to field " + getPosition());
            }
            case ENEMY -> {
                setPosition(+roll);
                UI.printString("Enemy moves to field " + getPosition());
            }
        }
    }

    public void moveBack(Die dice) {
        int roll=0;
        switch (dice.rollDice()){
            case 1,2 -> roll =1;
            case 3,4 -> roll =2;
            case 5,6 -> roll =3;
        }
        switch (playerId) {
            case PLAYER -> {
                setPosition(+roll);
                UI.printString("Player moves to field " + getPosition());
            }
            case ENEMY -> {
                setPosition(-roll);
                UI.printString("Enemy moves to field " + getPosition());
            }

        } // troop receives firepower when retrieving.
        setFirePower(this.firePower+250);
        reachedCamp(); //checks if troop has reached their camp and adds a bomb
    }
    public void reachedCamp(){
        if(playerId == PlayerId.PLAYER && getPosition() == 10) addBomb();
        else if (playerId == PlayerId.ENEMY && getPosition() ==-10) { addBomb();
        }
    }

    public void attack(Die dice, Troop adversary){
        int roll = dice.rollDice();
        if ((this.firePower-(roll*100)) >=0){ //Can only attack if firepower is sufficient.
            UI.printString(("ATTACK!"));
            int enemySoldiers = adversary.getSoldiers();
            int distance = Battlefield.getDistance(adversary.getPosition(), this.position);
            int deadSoldiers = Math.max(6 - distance, 0);
            if (deadSoldiers>0) UI.printString("Hit! " + deadSoldiers + " soldier(s) fallen");
            else UI.printString("MISS!");
            adversary.setSoldiers(enemySoldiers-deadSoldiers);
            setFirePower(Math.max(this.firePower-(roll*100),0));
        }else UI.printString("Not enough amo! Retreat to gather more!");
        // Could have been handled by not giving the player the option to attack?
    }

    public void addBomb(){
        if (getInventory() == null && bomb.getDetonated()) {
            this.bomb = new Bomb();
            inventory[0] = this.bomb;
            UI.printString("Received a bomb!");
        }}

    public void removeBomb(){   //Removes bomb from inventory
        inventory[0] = null;
    }

    public boolean surrender() {
        switch (playerId) {
            case PLAYER -> {UI.printString("\nWe surrender!");
                return true;}
            case ENEMY -> {UI.printString("\nEnemy surrenders!");
                return true;}
            default -> {
                return false;
            }
        }
    }
}
