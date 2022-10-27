package Cards;

import Game.GameManager;

public class ExplodingKitten extends Card {

    public ExplodingKitten() {
        setName("ExplodingKitten");
    }

    @Override
    public void drawEffect(GameManager gameManager) {

        Card drawCard = gameManager.getCurrentPlayer().getHand().getCardFromCardName("Defuse");

        if (drawCard == null) {
            cannotDefuse(gameManager);
            return;
        }

        gameManager.getCurrentPlayer().getHand().removeCard(drawCard);
        gameManager.getCurrentPlayer().sendMessage("You defused the kitten. Where in the deck do you wish to place the ExplodingKitten? [0.." + (gameManager.getPile().getSize()-1) + "]");
        gameManager.getPile().addCardAtIndex(this, Integer.valueOf(gameManager.getCurrentPlayer().readMessage(false)));
        gameManager.getPlayerManager().sendMessageToAllPlayers("Player " + gameManager.getCurrentPlayer().playerID + " successfully defused a kitten");
    }

    private void cannotDefuse(GameManager gameManager) {
        gameManager.getDiscard().addCard(this);
        gameManager.getDiscard().addDeck(gameManager.getCurrentPlayer().getHand());
        gameManager.getCurrentPlayer().exploded = true;
        gameManager.getPlayerManager().movePlayerToSpectators(gameManager.getCurrentPlayer());
        gameManager.getPlayerManager().sendMessageToAllPlayersAndSpectators("Player " + gameManager.getCurrentPlayer().playerID + " exploded");

        if (isThereAWinner(gameManager))
            gameManager.getEventQueue().add(gameManager.getEventFactory().makeEvent("AnnounceWinner"));
    }

    private boolean isThereAWinner(GameManager gameManager) {
        return (gameManager.getPlayerManager().getPlayers().size() == 1);
    }
}
