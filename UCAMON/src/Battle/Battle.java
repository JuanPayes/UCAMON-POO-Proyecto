package Battle;

import Battle.Animations.AnimationBattleEvent;
import Battle.Animations.FaintingAnimation;
import Battle.Animations.HPAnimationEvent;
import Battle.Animations.PokeballAnimation;
import Battle.TextEvent;
import Entities.Entity;
import Entities.Player;
import PokemonFactory.Pokemon.Pokemon;



public class Battle implements BattleEventQueuer{
    public enum STATE {
        READY_TO_PROGRESS,
        SELECT_NEW_POKEMON,
        RAN,
        WIN,
        LOSE,
        ;
    }

    private STATE state;



    private Pokemon player;
    private Pokemon opponent;

    private Player playerTrainer;
    private Entity opponentTrainer;

    private BattleEventPlayer eventPlayer;

    public Battle(Player player, Pokemon opponent) {
        this.playerTrainer = player;
        this.player = player.getPokemons().get(0);
        this.opponent = opponent;
        this.state = STATE.READY_TO_PROGRESS;
    }

    /**
     * Plays appropiate animation for starting a battle
     */
    public void beginBattle() {
       // queueEvent(new PokeSpriteEvent(opponent.getSprite(), BATTLE_PARTY.OPPONENT));
        queueEvent(new TextEvent("Go "+player.getName()+"!", 1f));
        //queueEvent(new PokeSpriteEvent(player.getSprite(), BATTLE_PARTY.PLAYER));
        queueEvent(new AnimationBattleEvent(BATTLE_PARTY.PLAYER, new PokeballAnimation()));
    }


    /**
     * Progress the battle one turn.
     * @param input		Index of the move used by the player
     */
    public void progress(int input) {
        if (state != STATE.READY_TO_PROGRESS) {
            return;
        }

        /*
         * XXX: Status effects go here.
         */
    }

    /**
     * Sends out a new Pokemon, in the case that the old one fainted.
     * This will NOT take up a turn.
     * @param pokemon	Pokemon the trainer is sending in
     */
    public void chooseNewPokemon(Pokemon pokemon) {
        this.player = pokemon;
        queueEvent(new HPAnimationEvent(
                BATTLE_PARTY.PLAYER,
                pokemon.getHp(),
                pokemon.getHp(),
                200,
                0f));
        //queueEvent(new PokeSpriteEvent(pokemon.getSprite(), BATTLE_PARTY.PLAYER));
        queueEvent(new NameChangeEvent(pokemon.getName(), BATTLE_PARTY.PLAYER));
        queueEvent(new TextEvent("Go get 'em, "+pokemon.getName()+"!"));
        queueEvent(new AnimationBattleEvent(BATTLE_PARTY.PLAYER, new PokeballAnimation()));
        this.state = STATE.READY_TO_PROGRESS;
    }

    /**
     * Attempts to run away
     */
    public void attemptRun() {
        queueEvent(new TextEvent("Got away successfully...", true));
        this.state = STATE.RAN;
    }

    private void playTurn(BATTLE_PARTY user, int input) {
        BATTLE_PARTY target = BATTLE_PARTY.getOpposite(user);

        Pokemon pokeUser = null;
        Pokemon pokeTarget = null;
        if (user == BATTLE_PARTY.PLAYER) {
            pokeUser = player;
            pokeTarget = opponent;
        } else if (user == BATTLE_PARTY.OPPONENT) {
            pokeUser = opponent;
            pokeTarget = player;
        }

        //Move move = pokeUser.getMove(input);

        /* Broadcast the text graphics */
        //  queueEvent(new TextEvent(pokeUser.getName()+" used\n"+move.getName().toUpperCase()+"!", 0.5f));



        if (player.isFainted()) {
            queueEvent(new AnimationBattleEvent(BATTLE_PARTY.PLAYER, new FaintingAnimation()));
            boolean anyoneAlive = false;
            for (int i = 0; i < getPlayerTrainer().getPokemons().size(); i++) {
                if (!getPlayerTrainer().getPokemons().get(i).isFainted()) {
                    anyoneAlive = true;
                    break;
                }
            }
            if (anyoneAlive) {
                queueEvent(new TextEvent(player.getName()+" fainted!", true));
                this.state = STATE.SELECT_NEW_POKEMON;
            } else {
                queueEvent(new TextEvent("Unfortunately, you've lost...", true));
                this.state = STATE.LOSE;
            }
        } else if (opponent.isFainted()) {
            queueEvent(new AnimationBattleEvent(BATTLE_PARTY.OPPONENT, new FaintingAnimation()));
            queueEvent(new TextEvent("Congratulations! You Win!", true));
            this.state = STATE.WIN;
        }
    }

    public Pokemon getPlayerPokemon() {
        return player;
    }

    public Pokemon getOpponentPokemon() {
        return opponent;
    }

    public Entity getPlayerTrainer() {
        return playerTrainer;
    }

    public Entity getOpponentTrainer() {
        return opponentTrainer;
    }

    public STATE getState() {
        return state;
    }

    public void setEventPlayer(BattleEventPlayer player) {
        this.eventPlayer = player;
    }
    @Override
    public void queueEvent(BattleEvent event) {

    }
}
