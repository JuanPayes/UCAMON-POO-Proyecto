package Controller;

import Battle.DialogueBox;
import Battle.MoveSelectBox;
import Battle.OptionBox;
import Battle.Battle;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

import java.awt.event.TextEvent;

public class BattleController extends InputAdapter {
    public enum STATE {
        USE_NEXT_POKEMON, 	// Text displayed when Pokemon faints
        SELECT_ACTION,		// Moves, Items, Pokemon, Run
        DEACTIVATED,		// Do nothing, display nothing
        ;
    }

    private STATE state = STATE.DEACTIVATED;

    private Battle battle;

    private DialogueBox dialogue;
    private OptionBox optionBox;
    private MoveSelectBox moveSelect;

    public BattleController(Battle battle,  DialogueBox dialogue, MoveSelectBox options, OptionBox optionBox) {
        this.battle= battle;
        this.dialogue = dialogue;
        this.moveSelect = options;
        this.optionBox = optionBox;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (this.state == STATE.DEACTIVATED) {
            return false;
        }
        if (this.state == STATE.USE_NEXT_POKEMON && optionBox.isVisible()) {
            if (keycode == Input.Keys.UP) {
                optionBox.moveUp();
            } else if (keycode == Input.Keys.DOWN) {
                optionBox.moveDown();
            } else if (keycode == Input.Keys.X) {
                if (optionBox.getIndex() == 0) { // YES selected


                    /*
                     * WRONG
                     */
                    /*for (int i = 0; i < battle.getPlayerTrainer().getTeamSize(); i++) {
                        if (!battle.getPlayerTrainer().getPokemon(i).isFainted()) {
                            battle.chooseNewPokemon(battle.getPlayerTrainer().getPokemon(i));
                            optionBox.setVisible(false);
                            this.state = STATE.DEACTIVATED;
                            break;
                        }
                    }*/
                } else if (optionBox.getIndex() == 1) { // NO selected

                    optionBox.setVisible(false);
                    this.state = STATE.DEACTIVATED;
                }
            }
        }

        /*if (moveSelect.isVisible()) {
            if (keycode == Input.Keys.X) {
                int selection = moveSelect.getSelection();
                if (battle.getPlayerPokemon().getMove(selection) == null) {
                    queue.add(new TextEvent("No such move...", 0.5f));
                } else {
                    battle.progress(moveSelect.getSelection());
                    endTurn();
                }
            } else if (keycode == Keys.UP) {
                moveSelect.moveUp();
                return true;
            } else if (keycode == Keys.DOWN) {
                moveSelect.moveDown();
                return true;
            } else if (keycode == Keys.LEFT) {
                moveSelect.moveLeft();
                return true;
            } else if (keycode == Keys.RIGHT) {
                moveSelect.moveRight();
                return true;
            }
        }*/
        return false;
    }

    public STATE getState() {
        return state;
    }

    public void update(float delta) {
        if (isDisplayingNextDialogue() && dialogue.isFinished() && !optionBox.isVisible()) {
            optionBox.clearChoices();
            optionBox.addOption("YES");
            optionBox.addOption("NO");
            optionBox.setVisible(true);
        }
    }

    /**
     * Displays the UI for a new turn
     */
    public void restartTurn() {
        this.state = STATE.SELECT_ACTION;
        dialogue.setVisible(false);
        for (int i = 0; i <= 3; i++) {
            String label = "------";

            moveSelect.setLabel(i, label.toUpperCase());
        }
        moveSelect.setVisible(true);
    }

    /**
     * Displays UI for selecting a new Pokemon
     */
    public void displayNextDialogue() {
        this.state = STATE.USE_NEXT_POKEMON;
        dialogue.setVisible(true);
        dialogue.animateText("Send out next pokemon?");
    }

    public boolean isDisplayingNextDialogue() {
        return this.state == STATE.USE_NEXT_POKEMON;
    }

    private void endTurn() {
        moveSelect.setVisible(false);
        this.state = STATE.DEACTIVATED;
    }
}



