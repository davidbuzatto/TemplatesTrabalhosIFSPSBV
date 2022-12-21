/**
 * A classe GameAction � uma abstra��o para uma a��o iniciada pelo 
 * usu�rio, como pular ou mover. As GameActions podem ser mapeadas 
 * para teclas ou mouse usando o InputManager.
 *
 * @author David Buzatto
 */
public class GameAction {
    
    /**
     * Comportamento normal. O m�todo isPressed() retorna true quando a
     * tecla � mantida pressionada.
     */
    public static final int NORMAL = 0;
    
    /**
     * Comportamento de pressionamento inicial. O m�todo isPressed() 
     * retorna true somente depois que a tecla � pressionada pela 
     * primeira vez, e n�o novamente ate que a tecla seja solta e 
     * pressionada novamente.
     */
    public static final int DETECT_INITAL_PRESS_ONLY = 1;
    
    private static final int STATE_RELEASED = 0;
    private static final int STATE_PRESSED = 1;
    private static final int STATE_WAITING_FOR_RELEASE = 2;
    
    private String name;
    private int behavior;
    private int amount;
    private int state;
    
    /**
     * Cria uma nova GameAction com o comportamento especificado.
     */
    public GameAction( String name, int behavior ) {
        this.name = name;
        this.behavior = behavior;
        reset();
    }
    
    /**
     * Reseta esta GameAction, fazendo parecer que esta n�o foi 
     * pressionada.
     */
    public void reset() {
        state = STATE_RELEASED;
        amount = 0;
    }
    
    /**
     * Sinaliza que a tecla foi pressionada.
     */
    public synchronized void press() {
        press( 1 );
    }
    
    /**
     * Sinaliza que a tecla foi solta.
     */
    public synchronized void release() {
        state = STATE_RELEASED;
    }
    
    /**
     * Retorna se a tecla foi pressionada ou n�o desde a �ltima checagem.
     */
    public synchronized boolean isPressed() {
        return ( getAmount() != 0 );
    }
    
}